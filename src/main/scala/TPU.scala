import chisel3._
import chisel3.util._
import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}

/*
Description: TPU is a part of the structure, each TPU including 8 S8DP1s and FFs, when TPU is enabled it will do the
calculation and load input to the output

Inputs:
  in_A: 2*8
  in_B: 4*8
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

class TPU extends Module{
  val io = IO(new Bundle {
    val in_A = Input(Vec(2, Vec(8, SInt(32.W))))
    val in_B = Input(Vec(4, Vec(8, SInt(32.W))))
    val in_tag = Input(Vec(4, Vec(8, Bool())))
    val in_cal = Input(Bool())

    val out_A = Output(Vec(2, Vec(8, SInt(32.W))))
    val out_B = Output(Vec(4, Vec(8, SInt(32.W))))
    val out_tag = Output(Vec(4, Vec(8, Bool())))

    val out_result = Output(Vec(2, Vec(4, SInt(32.W))))
    val out_cal = Output(Bool())
    })

  //Define
  val reg_A = RegInit(Vec(Seq.fill(2)(Vec(Seq.fill(8)(0.S)))))
  val reg_B = RegInit(Vec(Seq.fill(4)(Vec(Seq.fill(8)(0.S)))))
  val reg_tag = RegInit(Vec(Seq.fill(2)(Vec(Seq.fill(8)(false.B)))))

  val S8DP1_1 = Vec(Seq.fill(4)(Module(new S8DP1).io))
  val S8DP1_2 = Vec(Seq.fill(4)(Module(new S8DP1).io))

  //connect output (default output)
  io.out_A := reg_A
  io.out_B := reg_B
  io.out_tag := reg_tag

  for (i <- 0 until 4) {
    io.out_result(0)(i) := S8DP1_1(i).result
    io.out_result(1)(i) := S8DP1_2(i).result
  }
  io.out_cal := false.B

  //state
  val fetch :: cal :: stop :: Nil = Enum(3)
  val stateReg = RegInit(stop)


  //change state
  switch(stateReg) {
    is(stop){  //only connect outputs

    }
    is(fetch){  //start fetch at the rising edge of in_cal
      //A
      reg_A := io.in_A
      //B
      reg_B := io.in_B
      reg_tag := io.in_tag
      //change state
      if(reg_A != RegNext(reg_A)){
        stateReg := cal
      }
    }
    is(cal){ //connect inputs

    }

  }

}

//object Main {
//  def main(args: Array[String]): Unit = {
//    println("Mux8 main function")
//    chisel3.Driver.execute(args, () => new Mux8)
//  }
//}

//run --target-dir generated --compiler verilog