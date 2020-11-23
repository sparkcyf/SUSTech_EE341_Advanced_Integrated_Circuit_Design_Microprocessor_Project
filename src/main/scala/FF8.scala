import chisel3._


/*
Description: to store data and tag
    FFB1    FFB2
FFA1    FF00
    FF01    FF10
FFA2    FF11

FFA1    FF00

FFA2    FF11

They are FF8block

Inputs:
  in_data: a quarter of matrices A
Regs:
        M * N
  data: 2 * 8 32 Sint
Outputs:
  out_data: a quarter of matrices A
Function:

Author: YUAN Tong
Version: V0.1
Date: 22/11/2020
*/

object BLOCK_SIZE_FF8 {
  val M_FF8 = 2
  val N_FF8 = 8
}

class FF8(val w: Int = 32, val m: Int = BLOCK_SIZE_FF8.M_FF8, val n: Int = BLOCK_SIZE_FF8.N_FF8) extends Module{
  val io = IO(new Bundle{
    val in_data = Input(Vec(m*n, SInt(w.W)))
    val out_data = Output(Vec(m*n, SInt(w.W)))
  })
  
  val data = RegInit(Vec(m*n, SInt(w.W)))

  for(i <- 0 until m*n) {
    data(i) := io.in_data(i)
    io.out_data(i) := data(i)
  }
}

