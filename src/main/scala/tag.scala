import chisel3._


/*
Description: tag the input data

Inputs:
  in_data: input data, 8 ints

Regs:
  tag 8 ints

Outputs:
  out_tag
  out_count

Function:

Author: YUAN Tong
Version: V0.1
Date: 22/11/2020
 */

class tag extends Module{
  val io = IO(new Bundle{
    val in_data = Input(SInt(32.W))
    val out_tag = Output(Vec(8, UInt(1.W)))
    val out_count = Output(UInt(4.W))
  })

  val tag = RegInit(Vec(8, UInt(1.W)))
  val count = RegInit(UInt(4.W))

  for (i <- 0 until 7) {
    if (io.in_data != 0.S) {
      tag(i) := 1.U
      count := count + 1.U
    }
  }

  io.out_tag := tag
  io.out_count := count
}
