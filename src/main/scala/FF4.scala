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
Date: 25/11/2020
*/

/**
 * M ROW
 * N COL
 */

object BLOCK_SIZE_FF4 {
  val DATA_ROW_FF4 = 4
  val DATA_COL_FF4 = 8
  val TAG_ROW_FF4 = 4
  val TAG_COL_FF4 = 8
}

/**
 *
 * @param w
 * @param w_tag
 * @param data_row
 * @param data_col
 * @param tag_row
 * @param tag_col
 */

class FF4(val w: Int = 32, w_tag: Int = 8, val data_row: Int = BLOCK_SIZE_FF4.DATA_ROW_FF4, val data_col: Int = BLOCK_SIZE_FF4.DATA_COL_FF4,
          val tag_row: Int = BLOCK_SIZE_FF4.TAG_ROW_FF4, val tag_col: Int = BLOCK_SIZE_FF4.TAG_COL_FF4) extends Module {
  val io = IO(new Bundle {
    val in_data = Input(Vec(data_row, Vec(data_col, SInt(w.W))))
    val in_tag = Input(Vec(tag_row, Vec(tag_col, Bool())))
    val out_data = Output(Vec(data_row, Vec(data_col, SInt(w.W))))
    val out_tag = Output(Vec(tag_row, Vec(tag_col, Bool())))
  })

  val data = RegInit(Vec(Seq.fill(data_row)(Vec(Seq.fill(data_col)(0.S(w.W))))))
  val tag = RegInit(Vec(Seq.fill(tag_row)(Vec(Seq.fill(tag_col)(false.B)))))

  data := io.in_data
  tag := io.in_tag

  io.out_data := data
  io.out_tag := tag
}

//object Main {
//  def main(args: Array[String]): Unit = {
//    println("FF4 main function")
//    chisel3.Driver.execute(args, () => new FF4)
//  }
//}

//run --target-dir generated --compiler verilog

