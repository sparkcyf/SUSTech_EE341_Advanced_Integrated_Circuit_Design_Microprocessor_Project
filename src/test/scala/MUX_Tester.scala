import chisel3._
import chisel3.util._
import chisel3.iotesters._

class MUX_Tester(c: MUX) extends PeekPokeTester(c){

  var tag = Array(false.B, false.B, false.B, false.B, true.B, false.B, false.B, false.B, false.B)

  for (i <- 0 until 8) {
    poke(c.io.in_tag(i), tag(i))
  }
  step(1)
  println("Choice: " + peek(c.io.choice).toString)

}

object MUX_Tester extends App {
  chisel3.iotesters.Driver(() => new MUX){ c =>
    new MUX_Tester(c)
  }
}

