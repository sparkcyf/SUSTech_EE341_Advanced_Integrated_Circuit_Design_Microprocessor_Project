import chisel3._
import chisel3.util._

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

Author: YUAN Tong
Version: V2.0
Date: 3/12/2020
 */

/**
 * chisel3.util.Mux1H:
 *
 * Builds a Mux tree out of the input signal vector using a one hot encoded select signal.
 * Returns the output of the Mux tree.
 *
 * Check here: https://www.chisel-lang.org/api/latest/chisel3/util/Mux1H$.html
 *
 * WARNING: The data type of io.tag is bool, not int.
 *
 * Check here: https://github.com/chipsalliance/chisel3/wiki/Cookbook#how-do-i-create-a-vec-of-bools-from-a-uint
 */

class MUX8(val tag_width: Int = 8) extends Module {
  val io = IO(new Bundle {
    val int_in = Input(Vec(tag_width, SInt(32.W)))
    val in_tag = Input(Vec(tag_width, Bool()))
    val choice = Output(SInt(32.W))
  })
  io.choice := 0.S

  val not_found :: found :: Nil = Enum(2)
  val stateReg = RegInit(not_found)

  val tag = RegInit(Vec(Seq.fill(tag_width)(false.B)))

  for (i <- 0 until tag_width) {
    switch(stateReg) {
      is(not_found) {
        when(io.in_tag(i)) {
          tag(i) := true.B
          stateReg := found
        }.
          otherwise {
            tag(i) := false.B
          }
      }
      is(found) {
        tag(i) := false.B
      }
    }
  }

  io.choice := chisel3.util.Mux1H(tag, io.int_in)

}

//object Main {
//  def main(args: Array[String]): Unit = {
//    println("Mux8 main function")
//    chisel3.Driver.execute(args, () => new MUX8)
//  }
//}

//run --target-dir generated --compiler verilog



