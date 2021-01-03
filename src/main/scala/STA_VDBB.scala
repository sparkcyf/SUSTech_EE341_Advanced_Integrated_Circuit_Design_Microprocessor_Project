import chisel3._
import chisel3.util._

import scala.math.{Pi, pow, sin}
import breeze.signal.{OptOverhang, filter}
import breeze.signal.support.{CanFilter, FIRKernel1D}
import breeze.linalg.DenseVector


/*
Description: in this matrices A and B will be input, the they will be
  pre-processed, taged and devided into many parts, then we will let
  data going through in three*X clock circles

Inputs:
  in_A: matrices A, 4 rows 16 column
  in_B: matrices B, 16 rows 8 column
    notion: 矩阵B每半行非零项个数要一样！！！
Regs:
  Reg for inputs like A00, A01 ...
  Reg between S8DP1s, to store tag and
    FFB1    FFB2
FFA1    FF00
    FF01    FF10
FFA2    FF11


Outputs:
  out_C: matrices C, 4 rows 8 column (just like the structure)

Author: YUAN Tong
Version: V3.0
Date: 3/12/2020
 */

object BLOCK_SIZE_VDBB {
  //M: Rows
  //N: Column
  val ROW_A = 4
  val COL_A = 16
  val ROW_B = 8
  val COL_B = 16
}

class STA_VDBB(val w: Int = 32, val row_A: Int = BLOCK_SIZE_VDBB.ROW_A, val col_A: Int = BLOCK_SIZE_VDBB.COL_A,
               val row_B: Int = BLOCK_SIZE_VDBB.ROW_B, val col_B: Int = BLOCK_SIZE_VDBB.COL_B) extends Module {
  val io = IO(new Bundle {
    val in_A = Input(Vec(row_A, Vec(col_A, SInt(w.W))))
    val in_B = Input(Vec(row_B, Vec(col_B, SInt(w.W))))
    val in_cal = Input(Bool())

    val out_C = Output(Vec(row_A, Vec(row_B, SInt(w.W))))
    val out_cal = Output(Bool())
  })

  /*
   registers for blocks
   A:
   1 2
   3 4
   B:
   1 3
   2 4
   */
  val reg_A_1 = RegInit(Vec(Seq.fill(2)(Vec(Seq.fill(8)(1.S(w.W))))))
  val reg_A_2 = RegInit(Vec(Seq.fill(2)(Vec(Seq.fill(8)(1.S(w.W))))))
  val reg_A_3 = RegInit(Vec(Seq.fill(2)(Vec(Seq.fill(8)(1.S(w.W))))))
  val reg_A_4 = RegInit(Vec(Seq.fill(2)(Vec(Seq.fill(8)(1.S(w.W))))))

  val reg_B_1 = RegInit(Vec(Seq.fill(4)(Vec(Seq.fill(8)(1.S(w.W))))))
  val reg_B_2 = RegInit(Vec(Seq.fill(4)(Vec(Seq.fill(8)(1.S(w.W))))))
  val reg_B_3 = RegInit(Vec(Seq.fill(4)(Vec(Seq.fill(8)(1.S(w.W))))))
  val reg_B_4 = RegInit(Vec(Seq.fill(4)(Vec(Seq.fill(8)(1.S(w.W))))))

  val TPUs = Vec(Seq.fill(4)(Module(new TPU).io))
  TPUs(0).in_A := reg_A_1
  TPUs(0).in_B := reg_B_1
  TPUs(2).in_A := reg_A_2
  TPUs(1).in_B := reg_B_2

  TPUs(1).in_A := TPUs(0).out_A
  TPUs(3).in_A := TPUs(2).out_A

  TPUs(2).in_B := TPUs(0).out_B
  TPUs(3).in_B := TPUs(1).out_B

  val cal_state = RegInit(Vec(Seq.fill(3)(false.B)))
  cal_state(0) := TPUs(0).out_cal
  cal_state(1) := TPUs(1).out_cal && TPUs(2).out_cal
  cal_state(2) := TPUs(3).out_cal
  io.out_cal := cal_state(0) && cal_state(1) && cal_state(2)

  for (i <- 0 until 2){
    for (j <- 0 until 4) {
      io.out_C(i)(j) := TPUs(0).out_result(i)(j)
      io.out_C(i)(j+4) := TPUs(1).out_result(i)(j)
      io.out_C(i+2)(j) := TPUs(2).out_result(i)(j)
      io.out_C(i+2)(j+4) := TPUs(3).out_result(i)(j)
    }
  }

  val cal_control = RegInit(Vec(Seq.fill(3)(false.B)))
  TPUs(0).in_cal := cal_control(0)
  TPUs(1).in_cal := cal_control(1)
  TPUs(2).in_cal := cal_control(1)
  TPUs(3).in_cal := cal_control(2)

  val prepare :: change :: load :: restart :: stop :: loop1 :: loop2 :: loop3 :: loop4 :: loop5 :: Nil = Enum(10)
  val stateReg = RegInit(stop)

  switch(stateReg) {
    is(stop){
      when(io.in_cal & !RegNext(io.in_cal)) {
        stateReg := prepare
      }
    }
    is(prepare){
      for (i <- 0 until 2){
        for (j <- 0 until 8) {
          reg_A_1(i)(j) := io.in_A(i)(j)
          reg_A_2(i)(j) := io.in_A(i+2)(j)
          reg_A_3(i)(j) := io.in_A(i)(j+8)
          reg_A_4(i)(j) := io.in_A(i+2)(j+8)
        }
      }
      for (i <- 0 until 4){
        for (j <- 0 until 8) {
          reg_B_1(i)(j) := io.in_B(i)(j)
          reg_B_2(i)(j) := io.in_B(i+4)(j)
          reg_B_3(i)(j) := io.in_B(i)(j+8)
          reg_B_4(i)(j) := io.in_B(i+4)(j+8)
        }
      }
      stateReg := RegNext(loop1)
      cal_control(0) := true.B
    }
    is(loop1) {
      cal_control(0) := false.B
      when(cal_state(0)) {
        cal_control(1) := true.B
        stateReg := loop2
      }
    }
    is(loop2) {
      cal_control(1) := false.B
      when(cal_state(1)) {
        stateReg := change
      }
    }
    is(change){
//      TPUs(0).in_B := reg_B_3
//      TPUs(0).in_A := reg_A_3
//      TPUs(1).in_B := reg_B_4
//      TPUs(2).in_A := reg_A_4
      reg_B_1 := reg_B_3
      reg_A_1 := reg_A_3
      reg_B_2 := reg_B_4
      reg_A_2 := reg_A_4
      stateReg := RegNext(load)
    }
    is(load){
      stateReg := restart
    }
    is(restart){
      cal_control(0) := true.B
      cal_control(2) := true.B
      stateReg := loop3
    }
    is(loop3){
      cal_control(0) := false.B
      cal_control(2) := false.B
      when(cal_state(0) && cal_state(2)) {
        stateReg := loop4
        cal_control(1) := true.B
      }

    }
    is(loop4){
      when(cal_state(1)) {
        cal_control(1) := false.B
        stateReg := loop5
        cal_control(2) := true.B
      }
    }
    is(loop5){
      when(cal_state(2)) {
        cal_control(2) := false.B
        stateReg := stop

      }
  }


  }
}


//object Main {
//  def main(args: Array[String]): Unit = {
//    println("STA-VDBB main function")
//    chisel3.Driver.execute(args, () => new STA_VDBB())
//  }
//}

//run --target-dir generated --compiler verilog
