import chisel3._
import chisel3.util._
import chisel3.iotesters._

class S9DP1_Tester(c: S9DP1) extends PeekPokeTester(c){

  poke(c.io.in_cal_control, false.B)
  poke(c.io.in_cal_state, true.B)
  println("Begin Test ---------------------------------------------")
  step(1)

  val tag = Array(false, false, true, false, false, true, false, true, true)
  for (i <- 0 until 9) {
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
  poke(c.io.in_cal_control, true.B)
  step(1)
  poke(c.io.in_cal_control, false.B)


  var result_exp = 0

  for (i <- 0 until 9){
    if(tag(i)){
      result_exp = result_exp + (i+1)
    }
  }

  step(20)
  println("Expected: " + result_exp.toString + " | Actually: " + peek(c.io.result).toString)
  println(peek(c.io.out_cal_state).toString)


  step(1)
  poke(c.io.in_cal_control, true.B)
  println(peek(c.io.out_cal_state).toString)
  step(1)
  poke(c.io.in_cal_control, false.B)
  println(peek(c.io.out_cal_state).toString)
  step(20)
  println("Expected: " + (result_exp * 2).toString + " | Actually: " + peek(c.io.result).toString)
  println(peek(c.io.out_cal_state).toString)
}

object S9DP1_Tester extends App {
  chisel3.iotesters.Driver(() => new S9DP1){ c =>
     new S9DP1_Tester(c)
  }
}
