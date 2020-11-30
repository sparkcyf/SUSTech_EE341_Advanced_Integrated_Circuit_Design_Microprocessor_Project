import chisel3._
import chisel3.util._

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


class tag_refresh(val tag_width: Int = 8) extends Module {
  val io = IO(new Bundle {
    val tag_in = Input(Vec(tag_width, Bool()))
    val tag_out = Output(Vec(tag_width, Bool()))
  })
  io.tag_out := Vec(Seq.fill(tag_width)(false.B))

  val not_found :: found :: Nil = Enum(2)
  val stateReg = RegInit(not_found)

  val tag = RegInit(Vec(Seq.fill(tag_width)(false.B)))

  for (i <- 0 until tag_width) {
    switch(stateReg) {
      is(not_found) {
        when(io.tag_in(i)) {
          tag(i) := false.B
          stateReg := found
        }.otherwise {
          tag(i) := false.B
        }
      }
      is(found) {
        tag(i) := io.tag_in(i)
      }
    }
  }

  io.tag_out := tag

}

//object Main {
//  def main(args: Array[String]): Unit = {
//    println("tag_refine main function")
//    chisel3.Driver.execute(args, () => new tag_refine)
//  }
//}

//run --target-dir generated --compiler verilog
