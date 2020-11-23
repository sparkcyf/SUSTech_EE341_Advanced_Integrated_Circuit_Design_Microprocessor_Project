import chisel3._
import chisel3.util._
import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}

/*
Description: Set the first 1 tag to 0

Inputs:

Regs:

Outputs:

Function:

Author: YUAN Tong
Version: V0.2
Date: 23/11/2020
*/


class tag_refine(val tag_width: Int = 8) extends Module{
  val io = IO(new Bundle{
    val tag_in = Input(Vec(tag_width, Bool()))
    val tag_out = Output(Vec(tag_width, Bool()))
  })

  val tag = RegInit(Vec(tag_width, Bool()))
  val find = RegInit(Bool())

  find := false.B

  for (i <- 0 until tag_width) {
    when (find === false.B) {
      when (tag(i) === true.B) {
        tag(i) := false.B
        find := true.B
      }
    }
  }

  io.tag_out := tag


}
