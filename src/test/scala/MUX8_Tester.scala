import chisel3._
import chisel3.util._
import chisel3.iotesters._

class MUX8_Tester(c: MUX8) extends PeekPokeTester(c){

  poke(c.io.in_tag(0), false.B)
  poke(c.io.in_tag(1), false.B)
  poke(c.io.in_tag(2), false.B)
  poke(c.io.in_tag(3), false.B)
  poke(c.io.in_tag(4), true.B)
  poke(c.io.in_tag(5), true.B)
  poke(c.io.in_tag(6), false.B)
  poke(c.io.in_tag(7), false.B)

  step(1)
  println("Choice: " + peek(c.io.choice).toString)

}

object MUX8_Tester extends App {
  chisel3.iotesters.Driver(() => new MUX8){c =>
    new MUX8_Tester(c)
  }
}

