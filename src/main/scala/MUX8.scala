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
    val tag = Input(Vec(tag_width, Bool()))
    val choice = Output(SInt(32.W))
  })

  if (io.tag(0) == false.B) {
    if (io.tag(1) == false.B) {
      if (io.tag(2) == false.B) {
        if (io.tag(3) == false.B) {
          if (io.tag(4) == false.B) {
            if (io.tag(5) == false.B) {
              if (io.tag(6) == false.B) {
                if (io.tag(7) == false.B) {
                  io.choice := 0.S
                }
                else if (io.tag(0) == true.B) {
                  io.choice := io.int_in(0)
                }
              }
              else if (io.tag(1) == true.B) {
                io.choice := io.int_in(1)
              }
            }
            else if (io.tag(2) == true.B) {
              io.choice := io.int_in(2)
            }
          }
          else if (io.tag(3) == true.B) {
            io.choice := io.int_in(3)
          }
        }
        else if (io.tag(4) == true.B) {
          io.choice := io.int_in(4)
        }
      }
      else if (io.tag(5) == true.B) {
        io.choice := io.int_in(5)
      }
    }
    else if (io.tag(6) == true.B) {
      io.choice := io.int_in(6)
    }
  }
  else if (io.tag(7) == true.B) {
    io.choice := io.int_in(7)
  }

}

//object Main {
//  def main(args: Array[String]): Unit = {
//    println("Mux8 main function")
//    chisel3.Driver.execute(args, () => new Mux8)
//  }
//}

//run --target-dir generated --compiler verilog



