import chisel3._


/*
Description: to store data and tag
    FFB1    FFB2
FFA1    FF00
    FF01    FF10
FFA2    FF11

FFA1    FF00

FFA2    FF11

They are FF8

Inputs:
  in_data: a quarter of matrices A
Regs:
  data: 2 * 8 32 Sint
Outputs:
  out_data: a quarter of matrices A
Function:

Author: YUAN Tong
Version: V0.1
Date: 22/11/2020
*/
class FF8 extends Module{
  val io = IO(new Bundle{
    val in_data = Input(Vec(2, Vec(8, SInt(32.W))))
    val out_data = Output(Vec(2, Vec(8, SInt(32.W))))
  })

  val data = RegInit(Vec(2, Vec(8, SInt(32.W))))

  data := io.in_data

  io.out_data := data
}

