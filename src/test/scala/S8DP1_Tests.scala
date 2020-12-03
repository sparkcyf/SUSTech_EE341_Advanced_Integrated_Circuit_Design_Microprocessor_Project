import chisel3._
import chisel3.iotesters._

class S8DP1_Tester(c: S8DP1) extends PeekPokeTester(c){
  poke(c.io.in_A(0), 1.S)
  poke(c.io.in_A(1), 1.S)
  poke(c.io.in_A(2), 1.S)
  poke(c.io.in_A(3), 1.S)
  poke(c.io.in_A(4), 1.S)
  poke(c.io.in_A(5), 1.S)
  poke(c.io.in_A(6), 1.S)
  poke(c.io.in_A(7), 1.S)

  poke(c.io.in_tag(0), false.B)
  poke(c.io.in_B(0), 0.S)

  poke(c.io.in_tag(1), true.B)
  poke(c.io.in_B(1), 2.S)

  poke(c.io.in_tag(2), true.B)
  poke(c.io.in_B(2), 3.S)

  poke(c.io.in_tag(3), false.B)
  poke(c.io.in_B(3), 0.S)

  poke(c.io.in_tag(4), false.B)
  poke(c.io.in_B(4), 0.S)

  poke(c.io.in_tag(5), false.B)
  poke(c.io.in_B(5), 0.S)

  poke(c.io.in_tag(6), false.B)
  poke(c.io.in_B(6), 0.S)

  poke(c.io.in_tag(7), false.B)
  poke(c.io.in_B(7), 0.S)

  poke(c.io.in_calculate, true.B)

  step(1)

  println("Result is: " + peek(c.io.result).toString)

}

object S8DP1_Tester extends App {
  chisel3.iotesters.Driver(() => new S8DP1){c =>
     new S8DP1_Tester(c)
  }
}
