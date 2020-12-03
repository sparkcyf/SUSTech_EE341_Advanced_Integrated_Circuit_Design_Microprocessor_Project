import chisel3._
import chisel3.util._

/*
Description: S8DP1 is a processing unit that can read the input from A
then calculate and accumulate according to the tag
Inside the S8DP1, there is a state machine, for each loop, take one bit and calculate.
If tag is equal to 0, turn to state 0

Inputs:
  int_in_A: 8 ints
  int_in_B: the single int form B
  tag: 8 boolean
Regs:
  acc: the accumulated result
Outputs:
  out_tag: tag for next loop
  result: the result stored in acc
signal:
  in: calculate: when set to 1, begin to calculate
  out: cal_finished: set to 1 when calculate finished, set to 0 when calculation signal is 1

Author: YUAN Tong
Version: V2.0
Date: 3/12/2020
 */

class S8DP1(val tag_width: Int = 8, val w: Int = 32) extends Module{
  val io = IO(new Bundle {
//    val CLK = Input(Bool())

    val in_A = Input(Vec(tag_width, SInt(w.W)))
    val in_B = Input(Vec(tag_width, SInt(w.W)))
    val in_tag = Input(Vec(tag_width, Bool())) //BOOL, NOT Int

    val result = Output(SInt(w.W))

    val in_calculate = Input(Bool())
    val out_calculate = Output(Bool())
  })

  val tag = RegInit(Vec(Seq.fill(tag_width)(false.B)))
  val acc = RegInit(0.S(w.W))
  io.result := acc
  io.out_calculate := false.B //default value

  val tag_change = Module(new tag_refresh)
  tag_change.io.tag_in := tag

  //state
  val cal :: stop :: Nil = Enum(2)
  val stateReg = RegInit(stop)

  switch(stateReg) {
    is(cal) {
      acc := acc + PriorityMux(tag, io.in_A) * PriorityMux(tag, io.in_B)

      when (acc =/= RegNext(acc)) {
        tag := tag_change.io.tag_out
        when (!tag(0) && !tag(1) && !tag(2) && !tag(3) &&
          !tag(4) && !tag(5) && !tag(6) && !tag(7)) {
          stateReg := stop
          io.out_calculate := true.B
        }
      }
    }
    is(stop) {
      //begin work
      when (!io.in_calculate && RegNext(io.in_calculate)){ //pos-edge
        stateReg := cal
        tag := io.in_tag
        io.out_calculate := false.B
      }
    }

  }





}

//object Main {
//  def main(args: Array[String]): Unit = {
//    println("S8DP1 main function")
//    chisel3.Driver.execute(args, () => new S8DP1)
//  }
//}

//run --target-dir generated --compiler verilog