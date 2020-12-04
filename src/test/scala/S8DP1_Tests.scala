import chisel3._
import chisel3.util._
import chisel3.iotesters._

class S8DP1_Tester(c: S8DP1) extends PeekPokeTester(c){

  poke(c.io.in_calculate, false.B)
  println("Begin: Result is:" + peek(c.io.result).toString)
  step(1)

  poke(c.io.in_calculate, true.B)

  for (i <- 0 until 8) {
    poke(c.io.in_A(i), 1.S)
    poke(c.io.in_B(i), (i+1).S)
  }

  poke(c.io.in_tag(0), true.B)
  poke(c.io.in_tag(1), false.B)
  poke(c.io.in_tag(2), false.B)
  poke(c.io.in_tag(3), false.B)
  poke(c.io.in_tag(4), false.B)
  poke(c.io.in_tag(5), false.B)
  poke(c.io.in_tag(6), false.B)
  poke(c.io.in_tag(7), false.B)
  step(1)

  poke(c.io.in_calculate, false.B)


  step(1)
  println("Result is:" + peek(c.io.result).toString)
  step(1)
  println("Result is:" + peek(c.io.result).toString)
  step(1)
  println("Result is:" + peek(c.io.result).toString)
  step(1)
  println("Result is:" + peek(c.io.result).toString)
  step(1)
  println("Result is:" + peek(c.io.result).toString)
  step(1)
  println("Result is:" + peek(c.io.result).toString)
  step(1)
  println("Result is:" + peek(c.io.result).toString)
  step(1)
  println("Result is:" + peek(c.io.result).toString)
  step(1)
  println("Result is:" + peek(c.io.result).toString)
  step(1)
  println("Result is:" + peek(c.io.result).toString)
  step(1)
  println("Result is:" + peek(c.io.result).toString)
  step(1)
  println("Result is:" + peek(c.io.result).toString)
  step(1)
  println("Result is:" + peek(c.io.result).toString)
  step(1)
  println("Result is:" + peek(c.io.result).toString)
  step(1)
  println("Result is:" + peek(c.io.result).toString)
  step(1)
  println("Result is:" + peek(c.io.result).toString)


}

object S8DP1_Tester extends App {
  chisel3.iotesters.Driver(() => new S8DP1){c =>
     new S8DP1_Tester(c)
  }
}
