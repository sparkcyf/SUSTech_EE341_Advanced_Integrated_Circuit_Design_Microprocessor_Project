import chisel3._
import chisel3.util._
import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}

class RELU(IMG_W:Int, CHANNEL_D:Int) extends Module {
  val img_L = IMG_W
  val channel_D = CHANNEL_D

  val io = IO(new Bundle {
    val in_matrix = Input(Vec(img_L, Vec(img_L, SInt(32.W))))
    val in_cal = Input(Bool())
    val out_result = Output(Vec(img_L, Vec(img_L, SInt(32.W))))
//    val out_cal = Output(Bool())
  })
  io.out_result := RegInit(Vec(Seq.fill(img_L)(Vec(Seq.fill(img_L)(0.S(32.W))))))
  for (i <- 0 until (img_L - 1)) {
    for (j <- 0 until (img_L - 1)) {
      when(io.in_matrix(i)(j) >= 0.S) {
        io.out_result(i)(j) := io.in_matrix(i)(j)
      }.otherwise {
        io.out_result(i)(j) := 0.S
      }
    }
  }
}

//object Main {
//  def main(args: Array[String]): Unit = {
//    println("CONV2D for input image")
//    chisel3.Driver.execute(args, () => new RELU(4,10))
//  }
//}
