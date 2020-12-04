import chisel3._
import chisel3.util._
import chisel3.iotesters._

class TPU_Tester(c: TPU) extends PeekPokeTester(c){
  /*
  val io = IO(new Bundle {
    val in_A = Input(Vec(2, Vec(8, SInt(32.W))))
    val in_B = Input(Vec(4, Vec(8, SInt(32.W))))
    val in_tag = Input(Vec(4, Vec(8, Bool())))
    val in_cal = Input(Bool())

    val out_A = Output(Vec(2, Vec(8, SInt(32.W))))
    val out_B = Output(Vec(4, Vec(8, SInt(32.W))))
    val out_tag = Output(Vec(4, Vec(8, Bool())))

    val out_result = Output(Vec(2, Vec(4, SInt(32.W))))
    val out_cal = Output(Bool())
    })
   */

  println("matrices A: ")
  for (i <- 0 until 2) {
    print("| ")
    for (j <- 0 until 8) {
      poke(c.io.in_A(i)(j), 1.S)
      print(" 1 ")
    }
    println("| ")
  }

  println("matrices B: ")
  val tag = Array(
    Array(false, true, false, true, false, false, true, false),
    Array(false, false, true, false, false, false, false, false),
    Array(false, true, false, false, false, false, false, false),
    Array(false,  false, false, true, false, false, true, false)
  )
  for (i <- 0 until 4) {
    print("| ")
    for (j <- 0 until 8) {
      if(tag(i)(j)){
        poke(c.io.in_B(i)(j), (j+1).S)
        poke(c.io.in_tag(i)(j), true.B)
        print(" " + (j+1).toString + " ")
      }
      else{
        poke(c.io.in_B(i)(j), 0.S)
        poke(c.io.in_tag(i)(j), false.B)
        print(" " + 0.toString + " ")
      }
    }
    println("| ")
  }

  println("Expected:")
  val result_exp = Array(Array(0, 0, 0, 0), Array(0, 0, 0, 0))
  for (i <- 0 until 2) {
    print("| ")
    for(j <- 0 until 4) {
      for(k <- 0 until 8) {
        if(tag(j)(k)){
          result_exp(i)(j) += k+1
        }
        else{
          result_exp(i)(j) += 0
        }
      }
      print(" " + result_exp(i)(j).toString + " ")
    }
    println("| ")
  }


  poke(c.io.in_cal, false.B)
  step(1)
  poke(c.io.in_cal, true.B)
  step(1)
  poke(c.io.in_cal, false.B)


  step(30)
//  println("Reg A")
//  for (i <- 0 until 2) {
//    print("| ")
//    for (j <- 0 until 8) {
//      print(" " + peek(c.io.out_A(i)(j)) + " ")
//    }
//    println("| ")
//  }
//
//  println("Reg B")
//  for (i <- 0 until 4) {
//    print("| ")
//    for (j <- 0 until 8) {
//      print(" " + peek(c.io.out_B(i)(j)) + " ")
//    }
//    println("| ")
//  }

  println("Actually:")
  for (i <- 0 until 2){
    print("| ")
    for(j <- 0 until 4) {
      print(" " + peek(c.io.out_result(i)(j)).toString + " ")
    }
    println(" |")
  }
}

object TPU_Tester extends App {
  chisel3.iotesters.Driver(() => new TPU){ c=>
    new TPU_Tester(c)
  }
}
