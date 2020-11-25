import chisel3._


/*
Description: S8DP1 is a processing unit that can read the input from A
and select one and do multiplication operation with the int from B

Inputs:
  int_in_A: 8 ints
  int_in_B: the single int form B
  tag: 8 boolean
Regs:
  acc: the accumulated result
Outputs:
  out_tag: tag for next loop
  result: the result stored in acc
 */

class S8DP1(val tag_width: Int = 8, val w: Int = 32) extends Module{
  val io = IO(new Bundle {
    val int_in_A = Input(Vec(tag_width, SInt(w.W)))
    val int_in_B = Input(SInt(w.W))
    val tag = Input(Vec(tag_width, Bool())) //BOOL, NOT Int
    val out_tag = Output(Vec(tag_width, UInt(1.W)))
    val result = Output(SInt(w.W))
  })

  val acc = RegInit(0.S(w.W))
  val mux = Module(new MUX8)
  val tag_change = Module(new tag_refine)

  mux.io.int_in := io.int_in_A
  mux.io.tag := io.tag

  acc := acc + io.int_in_B * mux.io.choice

  tag_change.io.tag_in := io.tag
  io.out_tag := tag_change.io.tag_out

  io.result := acc
}

//object Main {
//  def main(args: Array[String]): Unit = {
//    println("S8DP1 main function")
//    chisel3.Driver.execute(args, () => new S8DP1)
//  }
//}

//run --target-dir generated --compiler verilog