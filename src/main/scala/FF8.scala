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
  val ROW_FF8 = 2
  val COL_FF8 = 8
}

class ff8(val w: Int = 32, val row: Int = BLOCK_SIZE_FF8.ROW_FF8, val col: Int = BLOCK_SIZE_FF8.COL_FF8) extends Module {
  val io = IO(new Bundle {
    val in_data = Input(Vec(row, Vec(col, SInt(w.W))))
    val out_data = Output(Vec(row, Vec(col, SInt(w.W))))
  })

  val data = RegInit(Vec(Seq.fill(row)(Vec(Seq.fill(col)(0.S(w.W))))))

  io.out_data := io.in_data
}

//object Main {
//  def main(args: Array[String]): Unit = {
//    println("FF8 main function")
//    chisel3.Driver.execute(args, () => new FF8)
//  }
//}

//run --target-dir generated --compiler verilog





