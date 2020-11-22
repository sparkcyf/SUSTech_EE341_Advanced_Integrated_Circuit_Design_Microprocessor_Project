import chisel3._

/*
Description: to store data and tag
    FFB1    FFB2
FFA1    FF00
    FF01    FF10
FFA2    FF11

    FFB1    FFB2

    FF01    FF10

They are FF4

Inputs:
  in_data: a quarter of matrices A
  in_tag: their tags
Regs:
  data: 4 * 8 32 Sint
  tag: 4 * 8 1 Uint
Outputs:
  out_data: a quarter of matrices A
  out_tag: their tags
Function:

Author: YUAN Tong
Version: V0.1
Date: 22/11/2020
*/
class FF4 extends Module{
  val io = IO(new Bundle{
    val in_data = Input(Vec(4, Vec(8, SInt(32.W))))
    val in_tag = Input(Vec(4, Vec(8, UInt(1.W))))
    val out_data = Output(Vec(2, Vec(4, SInt(32.W))))
    val out_tag = Output(Vec(4, Vec(8, UInt(1.W))))
  })

  val data = RegInit(Vec(2, Vec(4, SInt(32.W))))
  val tag = RegInit(Vec(4, Vec(8, UInt(1.W))))

  data := io.in_data
  tag := io.in_tag

  io.out_data := data
  io.out_tag := tag
}

