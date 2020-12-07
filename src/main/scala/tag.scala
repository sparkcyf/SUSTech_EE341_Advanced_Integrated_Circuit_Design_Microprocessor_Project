import chisel3._
import chisel3.util._


/**
 * Description: tag the input data
 *
 * Inputs:
 * in_data: input data, 8 ints
 *
 * Regs:
 * tag 8 ints
 *
 * Outputs:
 *
 * out_tag, out_count
 *
 * Function:
 *
 * Author: YUAN Tong
 * Version: V2.0
 * Date: 3/12/2020
 *
 */

class tag extends Module{
  val io = IO(new Bundle {
    val in_data = Input(Vec(8, SInt(32.W)))
    val out_tag = Output(Vec(8, Bool()))
  })

  val tag = RegInit(Vec(Seq.fill(8)(false.B)))
  io.out_tag := tag

  for (i <- 0 until 7) {
    when (io.in_data(i) =/= 0.S) {
      tag(i) := true.B
    }
  }


}

//object Main {
//  def main(args: Array[String]): Unit = {
//    println("tag main function")
//    chisel3.Driver.execute(args, () => new tag)
//  }
//}

//run --target-dir generated --compiler verilog

