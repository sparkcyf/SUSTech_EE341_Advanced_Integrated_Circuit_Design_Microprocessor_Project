
import chisel3._


//matrix: HEIGHT * WIDTH * DEPTH(CHANNEL)

class CNN_LAYER_1_1(IMG_W: Int, CHANNEL_D: Int) extends Module {
  val img_L = IMG_W
  val channel_D = CHANNEL_D

  //IMG_W = 64
  //CHN_D = 10

  val io = IO(new Bundle {
    val in_matrix = Input(Vec(img_L, Vec(img_L, Vec(channel_D, SInt(32.W))))) //64*64*3
    val in_cal = Input(Bool())
    val out_result = Output(Vec(img_L, Vec(img_L, Vec(channel_D, SInt(32.W)))))
    //    val out_cal = Output(Bool())
  })

  //IMG2COL
  //after transfer IMG: (62*62)*9 WEIGHT: 9*10

  //
  //width after conv
  val W_after_conv = img_L - 2

  val INPUT_COL_R = Module(new IMG2COL(img_L, 3))
  for (i <- 0 until (img_L - 1)) {
    for (j <- 0 until (img_L - 1)) {
      INPUT_COL_R.io.in_matrix(i)(j) := io.in_matrix(i)(j)(0)
    }
  }

  val INPUT_COL_G = Module(new IMG2COL(img_L, 3))
  for (i <- 0 until (img_L - 1)) {
    for (j <- 0 until (img_L - 1)) {
      INPUT_COL_G.io.in_matrix(i)(j) := io.in_matrix(i)(j)(1)
    }
  }

  val INPUT_COL_B = Module(new IMG2COL(img_L, 3))
  for (i <- 0 until (img_L - 1)) {
    for (j <- 0 until (img_L - 1)) {
      INPUT_COL_B.io.in_matrix(i)(j) := io.in_matrix(i)(j)(2)
    }
  }

  //weight matrix


  //COL TPU MULTI
  //init out matrix
  io.out_result := RegInit(Vec(Seq.fill(W_after_conv)(Vec(Seq.fill(W_after_conv)(Vec(Seq.fill(channel_D)(0.S(32.W))))))))

  val weight_matrix_R = RegInit(Vec(Seq.fill(9)(Vec(Seq.fill(channel_D)(0.S(32.W))))))
  val weight_matrix_G = RegInit(Vec(Seq.fill(9)(Vec(Seq.fill(channel_D)(0.S(32.W))))))
  val weight_matrix_B = RegInit(Vec(Seq.fill(9)(Vec(Seq.fill(channel_D)(0.S(32.W))))))


  //define weight matrix
  //100x
  //R
  weight_matrix_R(0)(0) := (-30).S
  weight_matrix_R(1)(0) := (-21).S
  weight_matrix_R(2)(0) := (7).S
  weight_matrix_R(3)(0) := (-19).S
  weight_matrix_R(4)(0) := (10).S
  weight_matrix_R(5)(0) := (-1).S
  weight_matrix_R(6)(0) := (-4).S
  weight_matrix_R(7)(0) := (-2).S
  weight_matrix_R(8)(0) := (8).S
  weight_matrix_R(0)(1) := (14).S
  weight_matrix_R(1)(1) := (-3).S
  weight_matrix_R(2)(1) := (31).S
  weight_matrix_R(3)(1) := (14).S
  weight_matrix_R(4)(1) := (11).S
  weight_matrix_R(5)(1) := (12).S
  weight_matrix_R(6)(1) := (21).S
  weight_matrix_R(7)(1) := (-31).S
  weight_matrix_R(8)(1) := (-23).S
  weight_matrix_R(0)(2) := (-3).S
  weight_matrix_R(1)(2) := (24).S
  weight_matrix_R(2)(2) := (-5).S
  weight_matrix_R(3)(2) := (1).S
  weight_matrix_R(4)(2) := (-2).S
  weight_matrix_R(5)(2) := (7).S
  weight_matrix_R(6)(2) := (30).S
  weight_matrix_R(7)(2) := (38).S
  weight_matrix_R(8)(2) := (19).S
  weight_matrix_R(0)(3) := (10).S
  weight_matrix_R(1)(3) := (-26).S
  weight_matrix_R(2)(3) := (14).S
  weight_matrix_R(3)(3) := (10).S
  weight_matrix_R(4)(3) := (17).S
  weight_matrix_R(5)(3) := (-3).S
  weight_matrix_R(6)(3) := (-2).S
  weight_matrix_R(7)(3) := (-11).S
  weight_matrix_R(8)(3) := (29).S
  weight_matrix_R(0)(4) := (9).S
  weight_matrix_R(1)(4) := (-1).S
  weight_matrix_R(2)(4) := (8).S
  weight_matrix_R(3)(4) := (-42).S
  weight_matrix_R(4)(4) := (16).S
  weight_matrix_R(5)(4) := (-2).S
  weight_matrix_R(6)(4) := (-32).S
  weight_matrix_R(7)(4) := (37).S
  weight_matrix_R(8)(4) := (-8).S
  weight_matrix_R(0)(5) := (-17).S
  weight_matrix_R(1)(5) := (3).S
  weight_matrix_R(2)(5) := (-6).S
  weight_matrix_R(3)(5) := (24).S
  weight_matrix_R(4)(5) := (9).S
  weight_matrix_R(5)(5) := (3).S
  weight_matrix_R(6)(5) := (11).S
  weight_matrix_R(7)(5) := (14).S
  weight_matrix_R(8)(5) := (-13).S
  weight_matrix_R(0)(6) := (25).S
  weight_matrix_R(1)(6) := (-5).S
  weight_matrix_R(2)(6) := (-11).S
  weight_matrix_R(3)(6) := (-23).S
  weight_matrix_R(4)(6) := (-17).S
  weight_matrix_R(5)(6) := (13).S
  weight_matrix_R(6)(6) := (10).S
  weight_matrix_R(7)(6) := (-4).S
  weight_matrix_R(8)(6) := (-2).S
  weight_matrix_R(0)(7) := (25).S
  weight_matrix_R(1)(7) := (11).S
  weight_matrix_R(2)(7) := (22).S
  weight_matrix_R(3)(7) := (6).S
  weight_matrix_R(4)(7) := (34).S
  weight_matrix_R(5)(7) := (37).S
  weight_matrix_R(6)(7) := (-21).S
  weight_matrix_R(7)(7) := (-13).S
  weight_matrix_R(8)(7) := (6).S
  weight_matrix_R(0)(8) := (-20).S
  weight_matrix_R(1)(8) := (13).S
  weight_matrix_R(2)(8) := (5).S
  weight_matrix_R(3)(8) := (-18).S
  weight_matrix_R(4)(8) := (26).S
  weight_matrix_R(5)(8) := (5).S
  weight_matrix_R(6)(8) := (15).S
  weight_matrix_R(7)(8) := (1).S
  weight_matrix_R(8)(8) := (-3).S
  weight_matrix_R(0)(9) := (18).S
  weight_matrix_R(1)(9) := (4).S
  weight_matrix_R(2)(9) := (20).S
  weight_matrix_R(3)(9) := (-13).S
  weight_matrix_R(4)(9) := (20).S
  weight_matrix_R(5)(9) := (27).S
  weight_matrix_R(6)(9) := (3).S
  weight_matrix_R(7)(9) := (26).S
  weight_matrix_R(8)(9) := (36).S

  //G
  weight_matrix_R(0)(0) := (-20).S
  weight_matrix_R(1)(0) := (-13).S
  weight_matrix_R(2)(0) := (-16).S
  weight_matrix_R(3)(0) := (-9).S
  weight_matrix_R(4)(0) := (18).S
  weight_matrix_R(5)(0) := (20).S
  weight_matrix_R(6)(0) := (-22).S
  weight_matrix_R(7)(0) := (-1).S
  weight_matrix_R(8)(0) := (-4).S
  weight_matrix_R(0)(1) := (-4).S
  weight_matrix_R(1)(1) := (-26).S
  weight_matrix_R(2)(1) := (-12).S
  weight_matrix_R(3)(1) := (21).S
  weight_matrix_R(4)(1) := (-2).S
  weight_matrix_R(5)(1) := (12).S
  weight_matrix_R(6)(1) := (-13).S
  weight_matrix_R(7)(1) := (4).S
  weight_matrix_R(8)(1) := (-14).S
  weight_matrix_R(0)(2) := (13).S
  weight_matrix_R(1)(2) := (1).S
  weight_matrix_R(2)(2) := (7).S
  weight_matrix_R(3)(2) := (-30).S
  weight_matrix_R(4)(2) := (-40).S
  weight_matrix_R(5)(2) := (-25).S
  weight_matrix_R(6)(2) := (-10).S
  weight_matrix_R(7)(2) := (14).S
  weight_matrix_R(8)(2) := (-31).S
  weight_matrix_R(0)(3) := (-3).S
  weight_matrix_R(1)(3) := (2).S
  weight_matrix_R(2)(3) := (-20).S
  weight_matrix_R(3)(3) := (4).S
  weight_matrix_R(4)(3) := (11).S
  weight_matrix_R(5)(3) := (24).S
  weight_matrix_R(6)(3) := (8).S
  weight_matrix_R(7)(3) := (-27).S
  weight_matrix_R(8)(3) := (16).S
  weight_matrix_R(0)(4) := (12).S
  weight_matrix_R(1)(4) := (-8).S
  weight_matrix_R(2)(4) := (19).S
  weight_matrix_R(3)(4) := (-19).S
  weight_matrix_R(4)(4) := (3).S
  weight_matrix_R(5)(4) := (-17).S
  weight_matrix_R(6)(4) := (-25).S
  weight_matrix_R(7)(4) := (26).S
  weight_matrix_R(8)(4) := (-25).S
  weight_matrix_R(0)(5) := (30).S
  weight_matrix_R(1)(5) := (-5).S
  weight_matrix_R(2)(5) := (-2).S
  weight_matrix_R(3)(5) := (25).S
  weight_matrix_R(4)(5) := (36).S
  weight_matrix_R(5)(5) := (28).S
  weight_matrix_R(6)(5) := (17).S
  weight_matrix_R(7)(5) := (20).S
  weight_matrix_R(8)(5) := (-12).S
  weight_matrix_R(0)(6) := (15).S
  weight_matrix_R(1)(6) := (13).S
  weight_matrix_R(2)(6) := (11).S
  weight_matrix_R(3)(6) := (10).S
  weight_matrix_R(4)(6) := (-22).S
  weight_matrix_R(5)(6) := (16).S
  weight_matrix_R(6)(6) := (-27).S
  weight_matrix_R(7)(6) := (-14).S
  weight_matrix_R(8)(6) := (0).S
  weight_matrix_R(0)(7) := (12).S
  weight_matrix_R(1)(7) := (-38).S
  weight_matrix_R(2)(7) := (-5).S
  weight_matrix_R(3)(7) := (7).S
  weight_matrix_R(4)(7) := (-16).S
  weight_matrix_R(5)(7) := (4).S
  weight_matrix_R(6)(7) := (-25).S
  weight_matrix_R(7)(7) := (-20).S
  weight_matrix_R(8)(7) := (-10).S
  weight_matrix_R(0)(8) := (-35).S
  weight_matrix_R(1)(8) := (-8).S
  weight_matrix_R(2)(8) := (-25).S
  weight_matrix_R(3)(8) := (11).S
  weight_matrix_R(4)(8) := (24).S
  weight_matrix_R(5)(8) := (-5).S
  weight_matrix_R(6)(8) := (-14).S
  weight_matrix_R(7)(8) := (21).S
  weight_matrix_R(8)(8) := (-23).S
  weight_matrix_R(0)(9) := (-8).S
  weight_matrix_R(1)(9) := (-10).S
  weight_matrix_R(2)(9) := (-21).S
  weight_matrix_R(3)(9) := (-16).S
  weight_matrix_R(4)(9) := (6).S
  weight_matrix_R(5)(9) := (-27).S
  weight_matrix_R(6)(9) := (13).S
  weight_matrix_R(7)(9) := (0).S
  weight_matrix_R(8)(9) := (6).S

  //B
  weight_matrix_R(0)(0) := (0).S
  weight_matrix_R(1)(0) := (-12).S
  weight_matrix_R(2)(0) := (-8).S
  weight_matrix_R(3)(0) := (13).S
  weight_matrix_R(4)(0) := (19).S
  weight_matrix_R(5)(0) := (10).S
  weight_matrix_R(6)(0) := (-9).S
  weight_matrix_R(7)(0) := (13).S
  weight_matrix_R(8)(0) := (6).S
  weight_matrix_R(0)(1) := (32).S
  weight_matrix_R(1)(1) := (-14).S
  weight_matrix_R(2)(1) := (8).S
  weight_matrix_R(3)(1) := (-4).S
  weight_matrix_R(4)(1) := (16).S
  weight_matrix_R(5)(1) := (12).S
  weight_matrix_R(6)(1) := (14).S
  weight_matrix_R(7)(1) := (-22).S
  weight_matrix_R(8)(1) := (-7).S
  weight_matrix_R(0)(2) := (-17).S
  weight_matrix_R(1)(2) := (8).S
  weight_matrix_R(2)(2) := (26).S
  weight_matrix_R(3)(2) := (-24).S
  weight_matrix_R(4)(2) := (-18).S
  weight_matrix_R(5)(2) := (14).S
  weight_matrix_R(6)(2) := (22).S
  weight_matrix_R(7)(2) := (26).S
  weight_matrix_R(8)(2) := (-10).S
  weight_matrix_R(0)(3) := (17).S
  weight_matrix_R(1)(3) := (10).S
  weight_matrix_R(2)(3) := (3).S
  weight_matrix_R(3)(3) := (-19).S
  weight_matrix_R(4)(3) := (-11).S
  weight_matrix_R(5)(3) := (-8).S
  weight_matrix_R(6)(3) := (-19).S
  weight_matrix_R(7)(3) := (-3).S
  weight_matrix_R(8)(3) := (10).S
  weight_matrix_R(0)(4) := (36).S
  weight_matrix_R(1)(4) := (25).S
  weight_matrix_R(2)(4) := (23).S
  weight_matrix_R(3)(4) := (-24).S
  weight_matrix_R(4)(4) := (28).S
  weight_matrix_R(5)(4) := (9).S
  weight_matrix_R(6)(4) := (-15).S
  weight_matrix_R(7)(4) := (37).S
  weight_matrix_R(8)(4) := (8).S
  weight_matrix_R(0)(5) := (-25).S
  weight_matrix_R(1)(5) := (-22).S
  weight_matrix_R(2)(5) := (1).S
  weight_matrix_R(3)(5) := (-17).S
  weight_matrix_R(4)(5) := (-26).S
  weight_matrix_R(5)(5) := (-3).S
  weight_matrix_R(6)(5) := (-15).S
  weight_matrix_R(7)(5) := (-2).S
  weight_matrix_R(8)(5) := (-28).S
  weight_matrix_R(0)(6) := (18).S
  weight_matrix_R(1)(6) := (6).S
  weight_matrix_R(2)(6) := (9).S
  weight_matrix_R(3)(6) := (-4).S
  weight_matrix_R(4)(6) := (-19).S
  weight_matrix_R(5)(6) := (-15).S
  weight_matrix_R(6)(6) := (3).S
  weight_matrix_R(7)(6) := (-12).S
  weight_matrix_R(8)(6) := (9).S
  weight_matrix_R(0)(7) := (-3).S
  weight_matrix_R(1)(7) := (-7).S
  weight_matrix_R(2)(7) := (-27).S
  weight_matrix_R(3)(7) := (32).S
  weight_matrix_R(4)(7) := (20).S
  weight_matrix_R(5)(7) := (14).S
  weight_matrix_R(6)(7) := (-31).S
  weight_matrix_R(7)(7) := (-4).S
  weight_matrix_R(8)(7) := (-18).S
  weight_matrix_R(0)(8) := (14).S
  weight_matrix_R(1)(8) := (-7).S
  weight_matrix_R(2)(8) := (-4).S
  weight_matrix_R(3)(8) := (17).S
  weight_matrix_R(4)(8) := (7).S
  weight_matrix_R(5)(8) := (-17).S
  weight_matrix_R(6)(8) := (25).S
  weight_matrix_R(7)(8) := (22).S
  weight_matrix_R(8)(8) := (18).S
  weight_matrix_R(0)(9) := (18).S
  weight_matrix_R(1)(9) := (1).S
  weight_matrix_R(2)(9) := (-7).S
  weight_matrix_R(3)(9) := (-15).S
  weight_matrix_R(4)(9) := (-10).S
  weight_matrix_R(5)(9) := (-29).S
  weight_matrix_R(6)(9) := (16).S
  weight_matrix_R(7)(9) := (-1).S
  weight_matrix_R(8)(9) := (-18).S


  //conv

  val CONV_1_1_R = Module(new TPU((W_after_conv) * (W_after_conv), 10))
  CONV_1_1_R.io.in_A := INPUT_COL_R.io.out_result
  CONV_1_1_R.io.in_B := weight_matrix_R
  CONV_1_1_R.io.in_cal := true.B

  val CONV_1_1_G = Module(new TPU((W_after_conv) * (W_after_conv), 10))
  CONV_1_1_G.io.in_A := INPUT_COL_G.io.out_result
  CONV_1_1_G.io.in_B := weight_matrix_R
  CONV_1_1_G.io.in_cal := true.B

  val CONV_1_1_B = Module(new TPU((W_after_conv) * (W_after_conv), 10))
  CONV_1_1_B.io.in_A := INPUT_COL_B.io.out_result
  CONV_1_1_B.io.in_B := weight_matrix_R
  CONV_1_1_B.io.in_cal := true.B


  //bias matrix
  //1000x
  val bias_matrix = RegInit(Vec(Seq.fill(channel_D)(0.S(32.W))))

  bias_matrix(0) := (190).S
  bias_matrix(1) := (135).S
  bias_matrix(2) := (45).S
  bias_matrix(3) := (-18).S
  bias_matrix(4) := (254).S
  bias_matrix(5) := (84).S
  bias_matrix(6) := (156).S
  bias_matrix(7) := (63).S
  bias_matrix(8) := (128).S
  bias_matrix(9) := (-5).S


  //IMG2COL
  //after transfer IMG: (62*62)*9 WEIGHT: 9*10

  //add bias & colum2img

  val matrix_after_conv = RegInit(Vec(Seq.fill(W_after_conv)(Vec(Seq.fill(W_after_conv)(Vec(Seq.fill(channel_D)(0.S(32.W))))))))

  for (i <- 0 until (W_after_conv - 1)) { //ROW
    for (j <- 0 until (W_after_conv - 1)) { //COL
      for (k <- 0 until (channel_D - 1)) { //channel
        //ROW = i*W_after_conv + j
        matrix_after_conv(i)(j)(k) := (CONV_1_1_R.io.out_result(k)(i * W_after_conv + j) + CONV_1_1_R.io.out_result(k)(i * W_after_conv + j) + CONV_1_1_R.io.out_result(k)(i * W_after_conv + j)) * bias_matrix(k)
      }
    }
  }


  //RELU

  val matrix_relu = Module(new RELU(W_after_conv, 10))
  matrix_relu.io.in_matrix := matrix_after_conv
  io.out_result := matrix_relu.io.out_result



  //


  //  io.out_result := RegInit(Vec(Seq.fill(img_L)(Vec(Seq.fill(img_L)(Vec(Seq.fill(channel_D)(0.S(32.W))))))))
  //  for (i <- 0 until (img_L - 1)) {
  //    for (j <- 0 until (img_L - 1)) {
  //      for (k <- 0 until (channel_D - 1)) {
  //        when(io.in_matrix(i)(j)(k) >= 0.S) {
  //          io.out_result(i)(j)(k) := io.in_matrix(i)(j)(k)
  //        }.otherwise {
  //          io.out_result(i)(j)(k) := 0.S
  //        }
  //      }
  //    }
  //  }
  //}
}

//object Main {
//  def main(args: Array[String]): Unit = {
//    println("CONV2D for input image")
//    chisel3.Driver.execute(args, () => new CNN_LAYER_1_1(64,10))
//  }
//}
