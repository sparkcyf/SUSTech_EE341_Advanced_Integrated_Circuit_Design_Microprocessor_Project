import chisel3._
import chisel3.util._
import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}

/*
Description: TPU is a part of the structure, each TPU including 8 S8DP1s and FFs, when TPU is enabled it will do the
calculation and load input to the output

Inputs:
  in_A: 9*channel_D
  in_B: 9*img_W^2
  in_cal: Bool

Regs:
  reg_A: 2*8
  reg_B: 4*8
  reg_tag: 4*8

Outputs:
  out_A: 2*8
  out_B: 4*8
  out_cal: Bool
  out_result

State machine:
  stop: connect the regs to output
  cal: calculation

S8DP1 Array

Author: YUAN Tong
Version: V2.0
Date: 3/12/2020
*/

class TPU(IMG_W:Int, CHANNEL_D:Int) extends Module{
  val img_L = IMG_W
  val channel_D = CHANNEL_D

  val io = IO(new Bundle {
    val in_A = Input(Vec(9, Vec(channel_D, SInt(32.W))))
    val in_B = Input(Vec(9, Vec(img_L, SInt(32.W))))
    val in_cal = Input(Bool())

    val out_A = Output(Vec(9, Vec(channel_D, SInt(32.W))))
    val out_B = Output(Vec(9, Vec(img_L, SInt(32.W))))

    val out_result = Output(Vec(channel_D, Vec(img_L, SInt(32.W))))
    val out_cal = Output(Bool())
    })

  // Define
  val PEs = Array.ofDim[S9DP1](channel_D, img_L)

  val cal_state = RegInit(false.B)
  val cal_control = RegInit(false.B)

  io.out_A := io.in_A
  io.out_B := io.in_B

  for(row <- 0 until channel_D){
    for(col <- 0 until img_L) {
      PEs(row)(col) = Module(new S9DP1)
    }
  }

  for(row <- 0 until channel_D){
    for(col <- 0 until img_L){
      PEs(row)(col).io.in_cal_control := cal_control
      io.out_result(row)(col) := PEs(row)(col).io.result

      for (i <- 0 until 9){
        PEs(row)(col).io.in_A(i) := io.in_A(i)(row)
        PEs(row)(col).io.in_B(i) := io.in_B(i)(col)

        val sprase = true.B
        when(sprase){
          if(io.in_B(i)(col) == 0){
            PEs(row)(col).io.in_tag(i) := false.B
          }
          else {
            PEs(row)(col).io.in_tag(i) := true.B
          }
        }.otherwise{
          PEs(row)(col).io.in_tag(i) := true.B
        }

      }


      if(row == 0 && col == 0){ //角落的那个
//        for (i <- 0 until 9){
//          PEs(row)(col).io.in_A(i) := io.in_A(i)(row)
//          PEs(row)(col).io.in_B(i) := io.in_B(i)(col)
//          if(io.in_B(i)(col) == 0){
//            PEs(row)(col).io.in_tag(i) := false.B
//          }
//          else {
//            PEs(row)(col).io.in_tag(i) := true.B
//          }
//        }
        PEs(row)(col).io.in_cal_state := PEs(row+1)(col).io.out_cal_state && PEs(row)(col+1).io.out_cal_state
      }
      else if(row == 0){ //第一行
//        for (i <- 0 until 9){
//          PEs(row)(col).io.in_A(i) := PEs(row)(col-1).io.out_A(i)
//          PEs(row)(col).io.in_B(i) := io.in_B(i)(row)
//          if(io.in_B(i)(col) == 0){
//            PEs(row)(col).io.in_tag(i) := false.B
//          }
//          else {
//            PEs(row)(col).io.in_tag(i) := true.B
//          }
//        }
        if(col == img_L - 1){
          PEs(row)(col).io.in_cal_state := true.B
        }
        else {
          PEs(row)(col).io.in_cal_state := PEs(row)(col+1).io.out_cal_state
        }
      }
      else if(col == 0){//第一列
//        for (i <- 0 until 9){
//          PEs(row)(col).io.in_A(i) := io.in_A(i)(row)
//          PEs(row)(col).io.in_B(i) := PEs(row-1)(col).io.out_B(i)
//          PEs(row)(col).io.in_tag(i) := PEs(row-1)(col).io.out_tag(i)
//        }
        if (row == channel_D - 1){
          PEs(row)(col).io.in_cal_state := PEs(row)(col+1).io.out_cal_state
        }
        else {
          PEs(row)(col).io.in_cal_state := PEs(row+1)(col).io.out_cal_state && PEs(row)(col+1).io.out_cal_state
        }
      }
      else {//其他
//        for (i <- 0 until 9){
//          PEs(row)(col).io.in_A(i) := PEs(row)(col-1).io.out_A(i)
//          PEs(row)(col).io.in_B(i) := PEs(row-1)(col).io.out_B(i)
//          PEs(row)(col).io.in_tag(i) := PEs(row-1)(col).io.out_tag(i)
//        }
        if(col == img_L - 1){
          PEs(row)(col).io.in_cal_state := true.B
        }
        else {
          PEs(row)(col).io.in_cal_state := PEs(row)(col+1).io.out_cal_state
        }
      }


    }
  }
  cal_state := PEs(0)(0).io.out_cal_state

  // State
  val fetch :: start :: cal :: stop :: Nil = Enum(4)
  val stateReg = RegInit(stop)
  io.out_cal := cal_state

  switch(stateReg) {
    is(stop){  //stop
      when (io.in_cal & !RegNext(io.in_cal)) { //rising edge
        stateReg := start
      }
    }
    is(start){
      stateReg := RegNext(cal)
      cal_control := true.B
    }
    is(cal){ //connect inputs
      when(cal_state){
        stateReg := RegNext(stop)
        cal_control := false.B
      }
    }
  }
}

//object Main {
//  def main(args: Array[String]): Unit = {
//    println("TPU main function")
//    chisel3.Driver.execute(args, () => new TPU(4,4))
//  }
//}

//run --target-dir generated --compiler verilog