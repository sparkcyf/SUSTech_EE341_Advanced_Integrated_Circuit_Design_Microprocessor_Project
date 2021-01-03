import chisel3._


// softmax: img2col and sum
class SOFTMAX(IMG_W:Int, CHANNEL_D:Int) extends Module {
  val img_L = IMG_W
  val channel_D = CHANNEL_D

  val io = IO(new Bundle {
    val in_matrix = Input(Vec(img_L, Vec(img_L, SInt(32.W))))
    val in_weight = Input(Vec(img_L*img_L*channel_D, SInt(32.W)))
    val in_bias = Input(Vec(channel_D, SInt(32.W)))
    val in_cal = Input(Bool())
    val out_result = Output(SInt(32.W))
//    val out_cal = Output(Bool())
  })


  //img2col
  io.out_result := RegInit(Vec(Seq.fill(img_L/2)(Vec(Seq.fill(img_L/2)(0.S(32.W))))))
  for (i <- 0 until (img_L/2 - 1)) {
    for (j <- 0 until (img_L/2 - 1)) {
    io.out_result(i)(j) := (io.in_matrix(i*2)(j*2) + io.in_matrix(i*2+1)(j*2) + io.in_matrix(i*2)(j*2+1) + io.in_matrix(i*2+1)(j*2+1))/4.S
      }
    }
}

//object Main {
//  def main(args: Array[String]): Unit = {
//    println("CONV2D for input image")
//    chisel3.Driver.execute(args, () => new MAX_POOL(4,10))
//  }
//}
