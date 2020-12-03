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
  val reg_cal = RegInit(false.B)
  for (i <- 0 until 4) {
    S8DP1_1(i).in_calculate := false.B
    S8DP1_2(i).in_calculate := false.B

    S8DP1_1(i).in_A := reg_A(0)
    S8DP1_1(i).in_B := reg_B(0)(i)
    S8DP1_1(i).in_tag := reg_tag(0)(i)
    S8DP1_1(i).in_calculate := reg_cal

    S8DP1_2(i).in_A := reg_A(1)
    S8DP1_2(i).in_B := reg_B(1)(i)
    S8DP1_2(i).in_tag := reg_tag(1)(i)
    S8DP1_2(i).in_calculate := reg_cal

  }

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
    is(stop){  //stop
      when (!io.in_cal && RegNext(io.in_cal)) {
        stateReg := fetch
      }
    }
    is(fetch){  //start fetch at the rising edge of in_cal
      //A
      reg_A := io.in_A
      //B
      reg_B := io.in_B
      reg_tag := io.in_tag
      //change state
      stateReg := cal
    }
    is(cal){ //connect inputs
      //calculate
        reg_cal := true.B

      //change state
      when(S8DP1_1(0).out_calculate && S8DP1_1(1).out_calculate && S8DP1_1(2).out_calculate && S8DP1_1(3).out_calculate &&
        S8DP1_2(0).out_calculate && S8DP1_2(1).out_calculate && S8DP1_2(2).out_calculate && S8DP1_2(3).out_calculate
      ) {
        stateReg := stop
        reg_cal := false.B
      }
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