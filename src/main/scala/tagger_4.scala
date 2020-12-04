import chisel3._
import chisel3.util._
import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}

/*
Description:

Inputs:

Regs:

Outputs:

Function:

Author: YUAN Tong
Version: V2.0
Date: 3/12/2020
*/

class tagger_4 extends Module{
  val io = IO(new Bundle{
    val input = Input(Vec(4, Vec(8, SInt(32.W))))
    val output = Output(Vec(4, Vec(8, Bool())))
  })

  val taggers = Vec(Seq.fill(4)(Module(new tag).io))

//  val default_taggers_in = RegInit(Vec(Seq.fill(4)(Vec(Seq.fill(8)(0.S(32.W))))))
  val default_out = RegInit(Vec(Seq.fill(4)(Vec(Seq.fill(8)(false.B)))))
  io.output := default_out

  for(i <- 0 until 4){
    taggers(i).in_data := io.input(i)
    taggers(i).out_tag := io.output(i)
  }

}
