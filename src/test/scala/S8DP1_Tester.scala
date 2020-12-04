import chisel3._
import chisel3.util._
import chisel3.iotesters._

class S8DP1_Tester(c: S8DP1) extends PeekPokeTester(c){

  poke(c.io.in_calculate, false.B)
  println("Begin Test ---------------------------------------------")
  step(1)

  val tag = Array(false, false, true, false, false, true, false, false)
  for (i <- 0 until 8) {
    poke(c.io.in_A(i), 1.S)
    poke(c.io.in_B(i), (i+1).S)
    if(tag(i)){
      poke(c.io.in_tag(i), true.B)
    }
    else{
      poke(c.io.in_tag(i), false.B)
    }
  }

  step(1)
  poke(c.io.in_calculate, true.B)


  var result_exp = 0

  for (i <- 0 until 8){
    if(tag(i)){
      result_exp = result_exp + (i+1)
    }
  }

  step(20)
  println("Expected: " + result_exp.toString + " | Actually: " + peek(c.io.result).toString)

}

object S8DP1_Tester extends App {
  chisel3.iotesters.Driver(() => new S8DP1){c =>
     new S8DP1_Tester(c)
  }
}
