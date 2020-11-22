import chisel3._


/*
Description: Mux8 is a selector to select 1 bit form 8 bits input according to the TAG (First non-zero bit)
Inputs:
  int_in: 8 ints
  tag: 8 boolean
Regs:
  find: whether the int was found
  out_tag: The modified tag
Outputs:
  choice: 1 int
  out_tag: tag for next loop

Function:
  output the int represented by the first non-zero boolean and turn this TAG into 0

 */

class Mux8 extends Module{
  val io = IO(new Bundle{
    val int_in = Input(Vec(8, SInt(32.W)))
    val tag = Input(Vec(8, UInt(1.W)))
    val choice = Output(SInt(32.W))
    val out_tag = Output(Vec(8, UInt(1.W)))
  })

  val find = RegInit(0.U)
  val in_tag = RegInit(io.tag)

  /*
  assign first input with 1 tag to output
  change the tag to 0 and output the new tag
   */
  for (i <- 0 until 8) {
    when (io.tag(i) === 1.U) {
      io.choice := io.int_in(i)
    } .otherwise {
      io.choice := 0.asSInt
    }
  }

  io.out_tag := in_tag
}
