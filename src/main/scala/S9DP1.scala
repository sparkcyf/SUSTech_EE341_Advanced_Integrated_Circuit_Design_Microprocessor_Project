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
Version: V3.0 (Test passed)
Date: 3/12/2020
 */


class S9DP1(val tag_width: Int = 9, val w: Int = 32) extends Module{
  val io = IO(new Bundle {
//    val CLK = Input(Bool())

    val in_A = Input(Vec(tag_width, SInt(w.W)))
    val in_B = Input(Vec(tag_width, SInt(w.W)))
    val in_tag = Input(Vec(tag_width, Bool())) //BOOL, NOT Int
    val out_A = Output(Vec(tag_width, SInt(w.W)))
    val out_B = Output(Vec(tag_width, SInt(w.W)))
    val out_tag = Output(Vec(tag_width, Bool()))

    val result = Output(SInt(w.W))

    val in_cal_control = Input(Bool())
    val in_cal_state = Input(Bool())
    val out_cal_control = Output(Bool())
    val out_cal_state = Output(Bool())
  })

  io.out_A := io.in_A
  io.out_B := io.in_B
  io.out_cal_control := io.in_cal_control

  val tag = RegInit(Vec(Seq.fill(tag_width)(false.B)))
  io.out_tag := io.in_tag

  val acc = RegInit(0.S(w.W))
  val acc_temp = RegInit(0.S(w.W))
  io.result := acc

  val cal = RegInit(false.B)
  io.out_cal_state := cal && io.in_cal_state //default value

  val mux = Module(new MUX)
  mux.io.in_tag := tag

  //state
  val calculate :: refresh :: judge :: stop :: Nil = Enum(4)
  val stateReg = RegInit(stop)

  switch(stateReg) {
    is(calculate) {
      acc_temp := acc + io.in_A(mux.io.choice) * io.in_B(mux.io.choice)
      stateReg := refresh
    }
    is(refresh){
      acc := acc_temp
      tag(mux.io.choice) := false.B
      stateReg := judge
    }
    is(judge){
      when (((!tag(0) && !tag(1)) && (!tag(2) && !tag(3))) &&
        ((!tag(4) && !tag(5)) && (!tag(6) && !tag(7))) && !tag(8)) {
        stateReg := stop
        cal := true.B
      }.otherwise{
        stateReg := calculate
      }
    }
    is(stop) {
      tag := io.in_tag
      //begin work
//      when (!io.in_calculate && RegNext(io.in_calculate))
      when ((io.in_cal_control & !RegNext(io.in_cal_control)) && (tag(0) || tag(1) || tag(2) || tag(3) ||
        tag(4) || tag(5) || tag(6) || tag(7) || tag(8)))
      { //pos-edge
        stateReg := calculate
        cal := false.B
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