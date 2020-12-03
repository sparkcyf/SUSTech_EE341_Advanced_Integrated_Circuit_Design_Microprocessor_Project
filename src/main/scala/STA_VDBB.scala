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
Version: V2.0
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
    val out_C = Output(Vec(row_A, Vec(row_B, SInt(w.W))))
  })

  //Define modules
  /*
  0 1
  2 3

  0 1 2 3
  4 5 6 7
   */

  val taggers = Vec(Seq.fill(4)(Module(new tag).io))
  val muxs = Vec(Seq.fill(4)(Module(new MUX8).io))
  //  io.out_C := Vec(row_A, Vec(row_B, 0.S))
  io.out_C := Vec(Seq.fill(row_A)(Vec(Seq.fill(row_B)(0.S))))
  //  val S8DP1s = Vec(Seq.fill(row_A)(Seq.fill(row_B)(Module(new S8DP1).io)))

  val s8DP1s_1 = Vec(Seq.fill(row_B)(Module(new S8DP1).io))
  val s8DP1s_2 = Vec(Seq.fill(row_B)(Module(new S8DP1).io))
  val s8DP1s_3 = Vec(Seq.fill(row_B)(Module(new S8DP1).io))
  val s8DP1s_4 = Vec(Seq.fill(row_B)(Module(new S8DP1).io))
  val S8DP1s = Vector(s8DP1s_1, s8DP1s_2, s8DP1s_3, s8DP1s_4)

  //Define Regs (FFs)
  /*
      FFB1    FFB2
  FFA1    FF00
      FF01    FF10
  FFA2    FF11
   */
  //ffax left ffbx upper
  val ffb1 = Module(new FF4)
  val ff01 = Module(new FF4)
  val ffb2 = Module(new FF4)
  val ff10 = Module(new FF4)

  ff01.io.in_tag := ffb1.io.out_tag
  ff01.io.in_data := ffb1.io.out_data
  ff10.io.in_tag := ffb2.io.out_tag
  ff10.io.in_data := ffb2.io.out_data

  val ffa1 = Module(new FF8)
  val ff00 = Module(new FF8)
  val ffa2 = Module(new FF8)
  val ff11 = Module(new FF8)

  ff00.io.in_data := ffa1.io.out_data
  ff11.io.in_data := ffa2.io.out_data

  for (i <- 0 until 4) {
    for (j <- 0 until 2) {
      S8DP1s(j)(i).in_B := ffb1.io.out_data(i)
      S8DP1s(j)(i).in_A := ffa1.io.out_data(j)
      S8DP1s(j)(i + 4).in_B := ffb2.io.out_data(i)
      S8DP1s(j)(i + 4).in_A := ff00.io.out_data(j)
      S8DP1s(j + 2)(i).in_B := ff01.io.out_data(i)
      S8DP1s(j + 2)(i).in_A := ffa2.io.out_data(j)
      S8DP1s(j + 2)(i + 4).in_B := ff10.io.out_data(i)
      S8DP1s(j + 2)(j + 4).in_A := ff11.io.out_data(j)
    }
  }

  val zero = RegInit(Vec(Seq.fill(row_B)(Vec(Seq.fill(col_B)(false.B)))))

  // Now we come to the loops

  val ini :: loop1 :: loop2 :: loop3 :: loop4 :: finish :: Nil = Enum(6)
  val stateReg = RegInit(ini)

  switch(stateReg) {
    is(loop1) {
      //Loop1
      //Data fetch
      for (i <- 0 until 8) {
        //input A (left)
        for (j <- 0 until 2) {
          ffa1.io.in_data(j)(i) := io.in_A(j)(i)
        }
        //input and process B (upper)
        for (j <- 0 until 4) {
          ffb1.io.in_data(j)(i) := io.in_B(j)(i)
        }
      }

      //Data tag for B
      for (i <- 0 until 4) {
        taggers(i).in_data := ffb1.io.out_data(i)
        ffb1.io.in_tag(i) := taggers(i).out_tag
      }

      //Begin to compute
      /*
      + -
      - -
      left upper
      */

      when(S8DP1s(0)(0).out_calculate === true.B) {
        for (i <- 0 until 4) {
          for (j <- 0 until 2) {
            S8DP1s(j)(i).in_calculate := false.B
          }
        }
        stateReg := loop2
      }.otherwise {
        for (i <- 0 until 4) {
          for (j <- 0 until 2) {
            S8DP1s(j)(i).in_calculate := true.B
          }
        }
      }


    }
    is(loop2) {
      //Loop2
      //Data fetch
      for (i <- 0 until 8) {
        //input A (left)
        for (j <- 0 until 2) {
          ffa2.io.in_data(j)(i) := io.in_A(j)(i + 8)
        }
        //input and process B (upper)
        for (j <- 0 until 4) {
          ffb2.io.in_data(j)(i) := io.in_B(j)(i + 8)
        }
      }

      //Data tag for B
      for (i <- 0 until 4) {
        taggers(i).in_data := ffb2.io.out_data(i)
        ffb2.io.in_tag(i) := taggers(i).out_tag
      }

      //Begin to compute
      /*
      + -
      - -
      left upper
      */

      when(S8DP1s(0)(0).out_calculate === true.B) {
        for (i <- 0 until 4) {
          for (j <- 0 until 2) {
            S8DP1s(j + 2)(i + 4).in_calculate := false.B
            S8DP1s(j + 2)(i).in_calculate := false.B
            S8DP1s(j)(i + 4).in_calculate := false.B
          }
        }
        stateReg := loop2
      }.otherwise {
        for (i <- 0 until 4) {
          for (j <- 0 until 2) {
            S8DP1s(j + 2)(i).in_calculate := true.B
            S8DP1s(j + 2)(i + 4).in_calculate := true.B
            S8DP1s(j)(i + 4).in_calculate := true.B
          }
        }
      }
    }
    is(loop3) {
      //Loop3
      //Data fetch
      for (i <- 0 until 8) {
        //input A (left)
        for (j <- 0 until 2) {
          ffa1.io.in_data(j)(i) := io.in_A(j + 2)(i)
        }
        //input and process B (upper)
        for (j <- 0 until 4) {
          ffb1.io.in_data(j)(i) := io.in_B(j + 4)(i)
        }
      }

      //Data tag for B
      for (i <- 0 until 4) {
        taggers(i).in_data := ffb1.io.out_data(i)
        ffb1.io.in_tag(i) := taggers(i).out_tag
      }

      //Begin to compute
      /*
      + -
      - -
      left upper
      */

      when(S8DP1s(0)(0).out_calculate === true.B) {
        for (i <- 0 until 4) {
          for (j <- 0 until 2) {
            S8DP1s(j)(i).in_calculate := false.B
          }
        }
        stateReg := loop2
      }.otherwise {
        for (i <- 0 until 4) {
          for (j <- 0 until 2) {
            S8DP1s(j)(i).in_calculate := true.B
          }
        }
      }
    }
    is(loop4) {
      //Loop4
      //Data fetch
      for (i <- 0 until 8) {
        //input A (left)
        for (j <- 0 until 2) {
          ffa2.io.in_data(j)(i) := io.in_A(j + 2)(i + 8)
        }
        //input and process B (upper)
        for (j <- 0 until 4) {
          ffb2.io.in_data(j)(i) := io.in_B(j + 4)(i + 8)
        }
      }

      //Data tag for B
      for (i <- 0 until 4) {
        taggers(i).in_data := ffb2.io.out_data(i)
        ffb2.io.in_tag(i) := taggers(i).out_tag
      }

      //Begin to compute
      /*
      + -
      - -
      left upper
      */

      when(S8DP1s(0)(0).out_calculate === true.B) {
        for (i <- 0 until 4) {
          for (j <- 0 until 2) {
            S8DP1s(j + 2)(i + 4).in_calculate := false.B
            S8DP1s(j + 2)(i).in_calculate := false.B
            S8DP1s(j)(i + 4).in_calculate := false.B
          }
        }
        stateReg := finish
      }.otherwise {
        for (i <- 0 until 4) {
          for (j <- 0 until 2) {
            S8DP1s(j + 2)(i).in_calculate := true.B
            S8DP1s(j + 2)(i + 4).in_calculate := true.B
            S8DP1s(j)(i + 4).in_calculate := true.B
          }
        }
      }
    }
    is(finish) {
      val out_result = RegInit(Vec(Seq.fill(row_A)(VecInit(Seq.fill(row_B)(0.S(32.W))))))

      for (i <- 0 until 8) {
        for (j <- 0 until 4) {
          out_result(j)(i) := S8DP1s(j)(i).result
        }
      }

      io.out_C := out_result
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
