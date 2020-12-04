import chisel3._
import chisel3.util._
import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}

/*
Description:

Inputs:

Regs:

Outputs:

Function:

Author: YUAN Tong
Version: V2.0
Date: 3/12/2020
*/

class tagger_4 extends Module{
  val io = IO(new Bundle{
    val input = Input(Vec(4, Vec(8, SInt(32.W))))
    val output = Output(Vec(4, Vec(8, Bool())))
  })

  for (i <- 0 until 4) {
    for (j <- 0 until 8){
      when (io.input(i)(j) =/= 0.S) {
        io.output(i)(j) := true.B
      }.otherwise{
        io.output(i)(j) := false.B
      }
    }
  }

}
