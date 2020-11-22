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

object BLOCK_SIZE_FF4 {
  val DATA_M_FF4 = 4
  val DATA_N_FF4 = 8
  val TAG_M_FF4 = 4
  val TAG_N_FF4 = 8
}

class FF4(val w: Int = 32, w_tag: Int = 8, val data_m: Int = BLOCK_SIZE_FF4.DATA_M_FF4, val data_n: Int = BLOCK_SIZE_FF4.DATA_N_FF4,
          val tag_m: Int = BLOCK_SIZE_FF4.TAG_M_FF4, val tag_n: Int = BLOCK_SIZE_FF4.TAG_N_FF4) extends Module{
  val io = IO(new Bundle{
    val in_data = Input(Vec(data_m*data_n, SInt(w.W)))
    val in_tag = Input(Vec(tag_m*tag_n, UInt(w_tag.W)))
    val out_data = Output(Vec(data_m*data_n, SInt(w.W)))
    val out_tag = Output(Vec(tag_m*tag_n, UInt(w_tag.W)))
  })


  for(i <- 0 until data_m*data_n) {
    io.out_data(i) := io.in_data(i)
  }

  for(i <- 0 until tag_m*tag_n) {
    io.out_tag(i) := io.in_tag(i)
  }
}

