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
  val io = IO(new Bundle{
    val int_in_A = Input(Vec(tag_width, SInt(w.W)))
    val int_in_B = Input(SInt(w.W))
    val tag = Input(Vec(tag_width, Bool())) //BOOL, NOT Int
    val out_tag = Output(Vec(tag_width, UInt(1.W)))
    val result = Output(SInt(w.W))
  })

  val acc = RegInit(SInt(32.W), 0.S)
  val mux = Module(new Mux8)

  mux.io.int_in := io.int_in_A
  mux.io.tag := io.tag

  //set the first tag from 1 to 0


  acc := acc + io.int_in_B*mux.io.choice

  io.out_tag := mux.io.out_tag
  io.result := acc
}
