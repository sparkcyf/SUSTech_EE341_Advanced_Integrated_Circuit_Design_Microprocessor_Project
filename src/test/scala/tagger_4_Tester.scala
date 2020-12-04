import chisel3._
import chisel3.util._
import chisel3.iotesters._

class tagger_4_Tester(c:tagger_4) extends PeekPokeTester(c) {

  val input = Array(
    Array(0,1,0,0, 0,0,1,0),
    Array(0,0,1,0, 0,0,0,1),
    Array(0,1,0,0, 0,1,0,0),
    Array(1,0,0,0, 1,0,0,0))

  println("numbers")
  for (i <- 0 until 4) {
    print("| ")
    for (j <- 0 until 8) {
      poke(c.io.input(i)(j), input(i)(j))
      print(" " + input(i)(j) + " ")
    }
    println("| ")
  }

  step(1)

  for (i <- 0 until 4){
    for(j <- 0 until 8){
      poke(c.io.input(i)(j), input(i)(j))
    }
  }

  println("tags")
  for (i <- 0 until 4) {
    print("| ")
    for (j <- 0 until 8) {
//      if(peek(c.io.output(i)(j)) == true.B){
//        print(" " + 1.toString + " ")
//      }
//      else {
//        print(" " + 0.toString + " ")
//      }
      print(" " + peek(c.io.output(i)(j)).toString + " ")

    }
    println("| ")
  }

}

object tagger_4_Tester extends App {
  chisel3.iotesters.Driver(() => new tagger_4){c =>
    new tagger_4_Tester(c)
  }
}
