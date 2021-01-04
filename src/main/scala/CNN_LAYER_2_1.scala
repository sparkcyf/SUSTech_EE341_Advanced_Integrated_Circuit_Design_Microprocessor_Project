import chisel3._

//matrix: HEIGHT * WIDTH * DEPTH(CHANNEL)

class CNN_LAYER_2_1(IMG_W:Int, CHANNEL_D:Int) extends Module {
  val img_L = IMG_W
  val channel_D = CHANNEL_D

  //IMG_W = 30
  //CHN_D = 10

  val io = IO(new Bundle {
    //val in_matrix = Input(Vec(img_L, Vec(img_L, Vec(channel_D, SInt(32.W)))))
    val in_matrix = Input(Vec(64, Vec(64, Vec(3, SInt(32.W)))))
    //val in_cal = Input(Bool())
    val out_result = Output(Vec(img_L, Vec(img_L, Vec(channel_D, SInt(32.W)))))
    //    val out_cal = Output(Bool())
  })

  //input
  val input_matrix = Module(new CNN_LAYER_1_2(62, 10))

  //connect input
  input_matrix.io.in_matrix := io.in_matrix

  //width
  val W_after_conv = img_L - 2

  //IMG2COL

  val INPUT_COL_0 = Module(new IMG2COL(img_L, 3))
  for (i <- 0 until (img_L - 1)) {
    for (j <- 0 until (img_L - 1)) {
      INPUT_COL_0.io.in_matrix(i)(j) := input_matrix.io.out_result(i)(j)(0)
    }
  }
  val INPUT_COL_1 = Module(new IMG2COL(img_L, 3))
  for (i <- 0 until (img_L - 1)) {
    for (j <- 0 until (img_L - 1)) {
      INPUT_COL_1.io.in_matrix(i)(j) := input_matrix.io.out_result(i)(j)(1)
    }
  }
  val INPUT_COL_2 = Module(new IMG2COL(img_L, 3))
  for (i <- 0 until (img_L - 1)) {
    for (j <- 0 until (img_L - 1)) {
      INPUT_COL_2.io.in_matrix(i)(j) := input_matrix.io.out_result(i)(j)(2)
    }
  }
  val INPUT_COL_3 = Module(new IMG2COL(img_L, 3))
  for (i <- 0 until (img_L - 1)) {
    for (j <- 0 until (img_L - 1)) {
      INPUT_COL_3.io.in_matrix(i)(j) := input_matrix.io.out_result(i)(j)(3)
    }
  }
  val INPUT_COL_4 = Module(new IMG2COL(img_L, 3))
  for (i <- 0 until (img_L - 1)) {
    for (j <- 0 until (img_L - 1)) {
      INPUT_COL_4.io.in_matrix(i)(j) := input_matrix.io.out_result(i)(j)(4)
    }
  }
  val INPUT_COL_5 = Module(new IMG2COL(img_L, 3))
  for (i <- 0 until (img_L - 1)) {
    for (j <- 0 until (img_L - 1)) {
      INPUT_COL_5.io.in_matrix(i)(j) := input_matrix.io.out_result(i)(j)(5)
    }
  }
  val INPUT_COL_6 = Module(new IMG2COL(img_L, 3))
  for (i <- 0 until (img_L - 1)) {
    for (j <- 0 until (img_L - 1)) {
      INPUT_COL_6.io.in_matrix(i)(j) := input_matrix.io.out_result(i)(j)(6)
    }
  }
  val INPUT_COL_7 = Module(new IMG2COL(img_L, 3))
  for (i <- 0 until (img_L - 1)) {
    for (j <- 0 until (img_L - 1)) {
      INPUT_COL_7.io.in_matrix(i)(j) := input_matrix.io.out_result(i)(j)(7)
    }
  }
  val INPUT_COL_8 = Module(new IMG2COL(img_L, 3))
  for (i <- 0 until (img_L - 1)) {
    for (j <- 0 until (img_L - 1)) {
      INPUT_COL_8.io.in_matrix(i)(j) := input_matrix.io.out_result(i)(j)(8)
    }
  }
  val INPUT_COL_9 = Module(new IMG2COL(img_L, 3))
  for (i <- 0 until (img_L - 1)) {
    for (j <- 0 until (img_L - 1)) {
      INPUT_COL_9.io.in_matrix(i)(j) := input_matrix.io.out_result(i)(j)(9)
    }
  }


  io.out_result := RegInit(Vec(Seq.fill(W_after_conv)(Vec(Seq.fill(W_after_conv)(Vec(Seq.fill(channel_D)(0.S(32.W))))))))

  val weight_matrix_0 = RegInit(Vec(Seq.fill(9)(Vec(Seq.fill(channel_D)(0.S(32.W))))))
  val weight_matrix_1 = RegInit(Vec(Seq.fill(9)(Vec(Seq.fill(channel_D)(0.S(32.W))))))
  val weight_matrix_2 = RegInit(Vec(Seq.fill(9)(Vec(Seq.fill(channel_D)(0.S(32.W))))))
  val weight_matrix_3 = RegInit(Vec(Seq.fill(9)(Vec(Seq.fill(channel_D)(0.S(32.W))))))
  val weight_matrix_4 = RegInit(Vec(Seq.fill(9)(Vec(Seq.fill(channel_D)(0.S(32.W))))))
  val weight_matrix_5 = RegInit(Vec(Seq.fill(9)(Vec(Seq.fill(channel_D)(0.S(32.W))))))
  val weight_matrix_6 = RegInit(Vec(Seq.fill(9)(Vec(Seq.fill(channel_D)(0.S(32.W))))))
  val weight_matrix_7 = RegInit(Vec(Seq.fill(9)(Vec(Seq.fill(channel_D)(0.S(32.W))))))
  val weight_matrix_8 = RegInit(Vec(Seq.fill(9)(Vec(Seq.fill(channel_D)(0.S(32.W))))))
  val weight_matrix_9 = RegInit(Vec(Seq.fill(9)(Vec(Seq.fill(channel_D)(0.S(32.W))))))

  weight_matrix_0(0)(0):=(-18).S
  weight_matrix_0(1)(0):=(4).S
  weight_matrix_0(2)(0):=(-10).S
  weight_matrix_0(3)(0):=(-13).S
  weight_matrix_0(4)(0):=(7).S
  weight_matrix_0(5)(0):=(3).S
  weight_matrix_0(6)(0):=(-17).S
  weight_matrix_0(7)(0):=(-14).S
  weight_matrix_0(8)(0):=(0).S
  weight_matrix_0(0)(1):=(-1).S
  weight_matrix_0(1)(1):=(7).S
  weight_matrix_0(2)(1):=(26).S
  weight_matrix_0(3)(1):=(15).S
  weight_matrix_0(4)(1):=(-1).S
  weight_matrix_0(5)(1):=(26).S
  weight_matrix_0(6)(1):=(9).S
  weight_matrix_0(7)(1):=(-6).S
  weight_matrix_0(8)(1):=(18).S
  weight_matrix_0(0)(2):=(-5).S
  weight_matrix_0(1)(2):=(-14).S
  weight_matrix_0(2)(2):=(-16).S
  weight_matrix_0(3)(2):=(-25).S
  weight_matrix_0(4)(2):=(-27).S
  weight_matrix_0(5)(2):=(-27).S
  weight_matrix_0(6)(2):=(-15).S
  weight_matrix_0(7)(2):=(-20).S
  weight_matrix_0(8)(2):=(-19).S
  weight_matrix_0(0)(3):=(-22).S
  weight_matrix_0(1)(3):=(-10).S
  weight_matrix_0(2)(3):=(-5).S
  weight_matrix_0(3)(3):=(-9).S
  weight_matrix_0(4)(3):=(-13).S
  weight_matrix_0(5)(3):=(-17).S
  weight_matrix_0(6)(3):=(-23).S
  weight_matrix_0(7)(3):=(14).S
  weight_matrix_0(8)(3):=(9).S
  weight_matrix_0(0)(4):=(17).S
  weight_matrix_0(1)(4):=(-2).S
  weight_matrix_0(2)(4):=(19).S
  weight_matrix_0(3)(4):=(6).S
  weight_matrix_0(4)(4):=(-13).S
  weight_matrix_0(5)(4):=(17).S
  weight_matrix_0(6)(4):=(-12).S
  weight_matrix_0(7)(4):=(-10).S
  weight_matrix_0(8)(4):=(14).S
  weight_matrix_0(0)(5):=(12).S
  weight_matrix_0(1)(5):=(18).S
  weight_matrix_0(2)(5):=(10).S
  weight_matrix_0(3)(5):=(4).S
  weight_matrix_0(4)(5):=(13).S
  weight_matrix_0(5)(5):=(0).S
  weight_matrix_0(6)(5):=(-13).S
  weight_matrix_0(7)(5):=(1).S
  weight_matrix_0(8)(5):=(7).S
  weight_matrix_0(0)(6):=(-15).S
  weight_matrix_0(1)(6):=(-2).S
  weight_matrix_0(2)(6):=(-5).S
  weight_matrix_0(3)(6):=(-18).S
  weight_matrix_0(4)(6):=(-16).S
  weight_matrix_0(5)(6):=(-3).S
  weight_matrix_0(6)(6):=(7).S
  weight_matrix_0(7)(6):=(-9).S
  weight_matrix_0(8)(6):=(13).S
  weight_matrix_0(0)(7):=(-18).S
  weight_matrix_0(1)(7):=(4).S
  weight_matrix_0(2)(7):=(17).S
  weight_matrix_0(3)(7):=(0).S
  weight_matrix_0(4)(7):=(-13).S
  weight_matrix_0(5)(7):=(0).S
  weight_matrix_0(6)(7):=(-10).S
  weight_matrix_0(7)(7):=(-5).S
  weight_matrix_0(8)(7):=(0).S
  weight_matrix_0(0)(8):=(1).S
  weight_matrix_0(1)(8):=(-6).S
  weight_matrix_0(2)(8):=(1).S
  weight_matrix_0(3)(8):=(-5).S
  weight_matrix_0(4)(8):=(12).S
  weight_matrix_0(5)(8):=(9).S
  weight_matrix_0(6)(8):=(-15).S
  weight_matrix_0(7)(8):=(9).S
  weight_matrix_0(8)(8):=(-4).S
  weight_matrix_0(0)(9):=(-10).S
  weight_matrix_0(1)(9):=(7).S
  weight_matrix_0(2)(9):=(-3).S
  weight_matrix_0(3)(9):=(-12).S
  weight_matrix_0(4)(9):=(-8).S
  weight_matrix_0(5)(9):=(-12).S
  weight_matrix_0(6)(9):=(13).S
  weight_matrix_0(7)(9):=(12).S
  weight_matrix_0(8)(9):=(14).S

  weight_matrix_1(0)(0):=(1).S
  weight_matrix_1(1)(0):=(-16).S
  weight_matrix_1(2)(0):=(9).S
  weight_matrix_1(3)(0):=(7).S
  weight_matrix_1(4)(0):=(-15).S
  weight_matrix_1(5)(0):=(13).S
  weight_matrix_1(6)(0):=(-19).S
  weight_matrix_1(7)(0):=(-8).S
  weight_matrix_1(8)(0):=(9).S
  weight_matrix_1(0)(1):=(23).S
  weight_matrix_1(1)(1):=(2).S
  weight_matrix_1(2)(1):=(23).S
  weight_matrix_1(3)(1):=(-3).S
  weight_matrix_1(4)(1):=(-10).S
  weight_matrix_1(5)(1):=(24).S
  weight_matrix_1(6)(1):=(-5).S
  weight_matrix_1(7)(1):=(19).S
  weight_matrix_1(8)(1):=(0).S
  weight_matrix_1(0)(2):=(-5).S
  weight_matrix_1(1)(2):=(4).S
  weight_matrix_1(2)(2):=(-19).S
  weight_matrix_1(3)(2):=(-14).S
  weight_matrix_1(4)(2):=(-23).S
  weight_matrix_1(5)(2):=(2).S
  weight_matrix_1(6)(2):=(8).S
  weight_matrix_1(7)(2):=(3).S
  weight_matrix_1(8)(2):=(-23).S
  weight_matrix_1(0)(3):=(-21).S
  weight_matrix_1(1)(3):=(-18).S
  weight_matrix_1(2)(3):=(6).S
  weight_matrix_1(3)(3):=(-4).S
  weight_matrix_1(4)(3):=(-2).S
  weight_matrix_1(5)(3):=(-2).S
  weight_matrix_1(6)(3):=(-13).S
  weight_matrix_1(7)(3):=(-6).S
  weight_matrix_1(8)(3):=(16).S
  weight_matrix_1(0)(4):=(14).S
  weight_matrix_1(1)(4):=(-1).S
  weight_matrix_1(2)(4):=(-11).S
  weight_matrix_1(3)(4):=(19).S
  weight_matrix_1(4)(4):=(4).S
  weight_matrix_1(5)(4):=(0).S
  weight_matrix_1(6)(4):=(-10).S
  weight_matrix_1(7)(4):=(4).S
  weight_matrix_1(8)(4):=(8).S
  weight_matrix_1(0)(5):=(7).S
  weight_matrix_1(1)(5):=(-3).S
  weight_matrix_1(2)(5):=(-7).S
  weight_matrix_1(3)(5):=(-3).S
  weight_matrix_1(4)(5):=(-6).S
  weight_matrix_1(5)(5):=(0).S
  weight_matrix_1(6)(5):=(1).S
  weight_matrix_1(7)(5):=(-8).S
  weight_matrix_1(8)(5):=(2).S
  weight_matrix_1(0)(6):=(2).S
  weight_matrix_1(1)(6):=(-16).S
  weight_matrix_1(2)(6):=(17).S
  weight_matrix_1(3)(6):=(-2).S
  weight_matrix_1(4)(6):=(6).S
  weight_matrix_1(5)(6):=(-1).S
  weight_matrix_1(6)(6):=(15).S
  weight_matrix_1(7)(6):=(7).S
  weight_matrix_1(8)(6):=(-8).S
  weight_matrix_1(0)(7):=(11).S
  weight_matrix_1(1)(7):=(3).S
  weight_matrix_1(2)(7):=(6).S
  weight_matrix_1(3)(7):=(6).S
  weight_matrix_1(4)(7):=(13).S
  weight_matrix_1(5)(7):=(-11).S
  weight_matrix_1(6)(7):=(-19).S
  weight_matrix_1(7)(7):=(9).S
  weight_matrix_1(8)(7):=(2).S
  weight_matrix_1(0)(8):=(11).S
  weight_matrix_1(1)(8):=(-8).S
  weight_matrix_1(2)(8):=(14).S
  weight_matrix_1(3)(8):=(-7).S
  weight_matrix_1(4)(8):=(15).S
  weight_matrix_1(5)(8):=(21).S
  weight_matrix_1(6)(8):=(7).S
  weight_matrix_1(7)(8):=(7).S
  weight_matrix_1(8)(8):=(26).S
  weight_matrix_1(0)(9):=(12).S
  weight_matrix_1(1)(9):=(-10).S
  weight_matrix_1(2)(9):=(-7).S
  weight_matrix_1(3)(9):=(-7).S
  weight_matrix_1(4)(9):=(0).S
  weight_matrix_1(5)(9):=(11).S
  weight_matrix_1(6)(9):=(15).S
  weight_matrix_1(7)(9):=(0).S
  weight_matrix_1(8)(9):=(2).S

  weight_matrix_2(0)(0):=(4).S
  weight_matrix_2(1)(0):=(1).S
  weight_matrix_2(2)(0):=(6).S
  weight_matrix_2(3)(0):=(23).S
  weight_matrix_2(4)(0):=(23).S
  weight_matrix_2(5)(0):=(6).S
  weight_matrix_2(6)(0):=(-4).S
  weight_matrix_2(7)(0):=(3).S
  weight_matrix_2(8)(0):=(11).S
  weight_matrix_2(0)(1):=(10).S
  weight_matrix_2(1)(1):=(12).S
  weight_matrix_2(2)(1):=(-12).S
  weight_matrix_2(3)(1):=(-11).S
  weight_matrix_2(4)(1):=(-12).S
  weight_matrix_2(5)(1):=(6).S
  weight_matrix_2(6)(1):=(-11).S
  weight_matrix_2(7)(1):=(21).S
  weight_matrix_2(8)(1):=(-1).S
  weight_matrix_2(0)(2):=(-16).S
  weight_matrix_2(1)(2):=(-7).S
  weight_matrix_2(2)(2):=(0).S
  weight_matrix_2(3)(2):=(12).S
  weight_matrix_2(4)(2):=(-2).S
  weight_matrix_2(5)(2):=(9).S
  weight_matrix_2(6)(2):=(9).S
  weight_matrix_2(7)(2):=(-9).S
  weight_matrix_2(8)(2):=(-23).S
  weight_matrix_2(0)(3):=(-18).S
  weight_matrix_2(1)(3):=(13).S
  weight_matrix_2(2)(3):=(5).S
  weight_matrix_2(3)(3):=(3).S
  weight_matrix_2(4)(3):=(6).S
  weight_matrix_2(5)(3):=(3).S
  weight_matrix_2(6)(3):=(-4).S
  weight_matrix_2(7)(3):=(13).S
  weight_matrix_2(8)(3):=(-12).S
  weight_matrix_2(0)(4):=(10).S
  weight_matrix_2(1)(4):=(1).S
  weight_matrix_2(2)(4):=(-10).S
  weight_matrix_2(3)(4):=(24).S
  weight_matrix_2(4)(4):=(-4).S
  weight_matrix_2(5)(4):=(-12).S
  weight_matrix_2(6)(4):=(15).S
  weight_matrix_2(7)(4):=(21).S
  weight_matrix_2(8)(4):=(12).S
  weight_matrix_2(0)(5):=(15).S
  weight_matrix_2(1)(5):=(-9).S
  weight_matrix_2(2)(5):=(9).S
  weight_matrix_2(3)(5):=(-12).S
  weight_matrix_2(4)(5):=(-2).S
  weight_matrix_2(5)(5):=(19).S
  weight_matrix_2(6)(5):=(3).S
  weight_matrix_2(7)(5):=(13).S
  weight_matrix_2(8)(5):=(19).S
  weight_matrix_2(0)(6):=(-17).S
  weight_matrix_2(1)(6):=(3).S
  weight_matrix_2(2)(6):=(-1).S
  weight_matrix_2(3)(6):=(4).S
  weight_matrix_2(4)(6):=(3).S
  weight_matrix_2(5)(6):=(-22).S
  weight_matrix_2(6)(6):=(11).S
  weight_matrix_2(7)(6):=(11).S
  weight_matrix_2(8)(6):=(-20).S
  weight_matrix_2(0)(7):=(5).S
  weight_matrix_2(1)(7):=(-6).S
  weight_matrix_2(2)(7):=(14).S
  weight_matrix_2(3)(7):=(-6).S
  weight_matrix_2(4)(7):=(11).S
  weight_matrix_2(5)(7):=(-15).S
  weight_matrix_2(6)(7):=(0).S
  weight_matrix_2(7)(7):=(-6).S
  weight_matrix_2(8)(7):=(-13).S
  weight_matrix_2(0)(8):=(6).S
  weight_matrix_2(1)(8):=(3).S
  weight_matrix_2(2)(8):=(25).S
  weight_matrix_2(3)(8):=(25).S
  weight_matrix_2(4)(8):=(6).S
  weight_matrix_2(5)(8):=(21).S
  weight_matrix_2(6)(8):=(26).S
  weight_matrix_2(7)(8):=(10).S
  weight_matrix_2(8)(8):=(18).S
  weight_matrix_2(0)(9):=(-17).S
  weight_matrix_2(1)(9):=(-3).S
  weight_matrix_2(2)(9):=(-15).S
  weight_matrix_2(3)(9):=(-11).S
  weight_matrix_2(4)(9):=(-2).S
  weight_matrix_2(5)(9):=(-1).S
  weight_matrix_2(6)(9):=(2).S
  weight_matrix_2(7)(9):=(16).S
  weight_matrix_2(8)(9):=(0).S

  weight_matrix_3(0)(0):=(14).S
  weight_matrix_3(1)(0):=(2).S
  weight_matrix_3(2)(0):=(9).S
  weight_matrix_3(3)(0):=(0).S
  weight_matrix_3(4)(0):=(2).S
  weight_matrix_3(5)(0):=(-13).S
  weight_matrix_3(6)(0):=(-4).S
  weight_matrix_3(7)(0):=(-14).S
  weight_matrix_3(8)(0):=(1).S
  weight_matrix_3(0)(1):=(-10).S
  weight_matrix_3(1)(1):=(6).S
  weight_matrix_3(2)(1):=(1).S
  weight_matrix_3(3)(1):=(10).S
  weight_matrix_3(4)(1):=(16).S
  weight_matrix_3(5)(1):=(2).S
  weight_matrix_3(6)(1):=(-9).S
  weight_matrix_3(7)(1):=(-1).S
  weight_matrix_3(8)(1):=(21).S
  weight_matrix_3(0)(2):=(16).S
  weight_matrix_3(1)(2):=(13).S
  weight_matrix_3(2)(2):=(3).S
  weight_matrix_3(3)(2):=(1).S
  weight_matrix_3(4)(2):=(-8).S
  weight_matrix_3(5)(2):=(-10).S
  weight_matrix_3(6)(2):=(1).S
  weight_matrix_3(7)(2):=(-7).S
  weight_matrix_3(8)(2):=(-17).S
  weight_matrix_3(0)(3):=(1).S
  weight_matrix_3(1)(3):=(12).S
  weight_matrix_3(2)(3):=(-18).S
  weight_matrix_3(3)(3):=(14).S
  weight_matrix_3(4)(3):=(10).S
  weight_matrix_3(5)(3):=(2).S
  weight_matrix_3(6)(3):=(3).S
  weight_matrix_3(7)(3):=(-5).S
  weight_matrix_3(8)(3):=(-17).S
  weight_matrix_3(0)(4):=(12).S
  weight_matrix_3(1)(4):=(-4).S
  weight_matrix_3(2)(4):=(1).S
  weight_matrix_3(3)(4):=(-8).S
  weight_matrix_3(4)(4):=(-10).S
  weight_matrix_3(5)(4):=(5).S
  weight_matrix_3(6)(4):=(-15).S
  weight_matrix_3(7)(4):=(9).S
  weight_matrix_3(8)(4):=(2).S
  weight_matrix_3(0)(5):=(10).S
  weight_matrix_3(1)(5):=(15).S
  weight_matrix_3(2)(5):=(11).S
  weight_matrix_3(3)(5):=(-6).S
  weight_matrix_3(4)(5):=(11).S
  weight_matrix_3(5)(5):=(-16).S
  weight_matrix_3(6)(5):=(10).S
  weight_matrix_3(7)(5):=(3).S
  weight_matrix_3(8)(5):=(-6).S
  weight_matrix_3(0)(6):=(-18).S
  weight_matrix_3(1)(6):=(-8).S
  weight_matrix_3(2)(6):=(-9).S
  weight_matrix_3(3)(6):=(14).S
  weight_matrix_3(4)(6):=(-2).S
  weight_matrix_3(5)(6):=(2).S
  weight_matrix_3(6)(6):=(-5).S
  weight_matrix_3(7)(6):=(-18).S
  weight_matrix_3(8)(6):=(-8).S
  weight_matrix_3(0)(7):=(-16).S
  weight_matrix_3(1)(7):=(13).S
  weight_matrix_3(2)(7):=(13).S
  weight_matrix_3(3)(7):=(-4).S
  weight_matrix_3(4)(7):=(6).S
  weight_matrix_3(5)(7):=(-15).S
  weight_matrix_3(6)(7):=(-4).S
  weight_matrix_3(7)(7):=(3).S
  weight_matrix_3(8)(7):=(14).S
  weight_matrix_3(0)(8):=(4).S
  weight_matrix_3(1)(8):=(-9).S
  weight_matrix_3(2)(8):=(-5).S
  weight_matrix_3(3)(8):=(-8).S
  weight_matrix_3(4)(8):=(9).S
  weight_matrix_3(5)(8):=(-13).S
  weight_matrix_3(6)(8):=(15).S
  weight_matrix_3(7)(8):=(-8).S
  weight_matrix_3(8)(8):=(17).S
  weight_matrix_3(0)(9):=(2).S
  weight_matrix_3(1)(9):=(17).S
  weight_matrix_3(2)(9):=(9).S
  weight_matrix_3(3)(9):=(-11).S
  weight_matrix_3(4)(9):=(7).S
  weight_matrix_3(5)(9):=(17).S
  weight_matrix_3(6)(9):=(9).S
  weight_matrix_3(7)(9):=(14).S
  weight_matrix_3(8)(9):=(16).S

  weight_matrix_4(0)(0):=(-19).S
  weight_matrix_4(1)(0):=(-17).S
  weight_matrix_4(2)(0):=(-10).S
  weight_matrix_4(3)(0):=(-26).S
  weight_matrix_4(4)(0):=(-19).S
  weight_matrix_4(5)(0):=(-11).S
  weight_matrix_4(6)(0):=(-26).S
  weight_matrix_4(7)(0):=(-25).S
  weight_matrix_4(8)(0):=(-21).S
  weight_matrix_4(0)(1):=(-13).S
  weight_matrix_4(1)(1):=(-16).S
  weight_matrix_4(2)(1):=(19).S
  weight_matrix_4(3)(1):=(-12).S
  weight_matrix_4(4)(1):=(-17).S
  weight_matrix_4(5)(1):=(-15).S
  weight_matrix_4(6)(1):=(-10).S
  weight_matrix_4(7)(1):=(2).S
  weight_matrix_4(8)(1):=(-2).S
  weight_matrix_4(0)(2):=(15).S
  weight_matrix_4(1)(2):=(21).S
  weight_matrix_4(2)(2):=(16).S
  weight_matrix_4(3)(2):=(16).S
  weight_matrix_4(4)(2):=(7).S
  weight_matrix_4(5)(2):=(-9).S
  weight_matrix_4(6)(2):=(4).S
  weight_matrix_4(7)(2):=(-14).S
  weight_matrix_4(8)(2):=(-12).S
  weight_matrix_4(0)(3):=(1).S
  weight_matrix_4(1)(3):=(11).S
  weight_matrix_4(2)(3):=(17).S
  weight_matrix_4(3)(3):=(18).S
  weight_matrix_4(4)(3):=(-6).S
  weight_matrix_4(5)(3):=(1).S
  weight_matrix_4(6)(3):=(7).S
  weight_matrix_4(7)(3):=(20).S
  weight_matrix_4(8)(3):=(-6).S
  weight_matrix_4(0)(4):=(-10).S
  weight_matrix_4(1)(4):=(-13).S
  weight_matrix_4(2)(4):=(-20).S
  weight_matrix_4(3)(4):=(-4).S
  weight_matrix_4(4)(4):=(-10).S
  weight_matrix_4(5)(4):=(-1).S
  weight_matrix_4(6)(4):=(-21).S
  weight_matrix_4(7)(4):=(15).S
  weight_matrix_4(8)(4):=(11).S
  weight_matrix_4(0)(5):=(10).S
  weight_matrix_4(1)(5):=(-5).S
  weight_matrix_4(2)(5):=(10).S
  weight_matrix_4(3)(5):=(21).S
  weight_matrix_4(4)(5):=(9).S
  weight_matrix_4(5)(5):=(13).S
  weight_matrix_4(6)(5):=(7).S
  weight_matrix_4(7)(5):=(-16).S
  weight_matrix_4(8)(5):=(19).S
  weight_matrix_4(0)(6):=(24).S
  weight_matrix_4(1)(6):=(2).S
  weight_matrix_4(2)(6):=(22).S
  weight_matrix_4(3)(6):=(26).S
  weight_matrix_4(4)(6):=(24).S
  weight_matrix_4(5)(6):=(6).S
  weight_matrix_4(6)(6):=(-11).S
  weight_matrix_4(7)(6):=(13).S
  weight_matrix_4(8)(6):=(19).S
  weight_matrix_4(0)(7):=(3).S
  weight_matrix_4(1)(7):=(-2).S
  weight_matrix_4(2)(7):=(5).S
  weight_matrix_4(3)(7):=(6).S
  weight_matrix_4(4)(7):=(13).S
  weight_matrix_4(5)(7):=(21).S
  weight_matrix_4(6)(7):=(8).S
  weight_matrix_4(7)(7):=(-12).S
  weight_matrix_4(8)(7):=(18).S
  weight_matrix_4(0)(8):=(19).S
  weight_matrix_4(1)(8):=(13).S
  weight_matrix_4(2)(8):=(19).S
  weight_matrix_4(3)(8):=(20).S
  weight_matrix_4(4)(8):=(17).S
  weight_matrix_4(5)(8):=(20).S
  weight_matrix_4(6)(8):=(0).S
  weight_matrix_4(7)(8):=(9).S
  weight_matrix_4(8)(8):=(17).S
  weight_matrix_4(0)(9):=(6).S
  weight_matrix_4(1)(9):=(-18).S
  weight_matrix_4(2)(9):=(-17).S
  weight_matrix_4(3)(9):=(12).S
  weight_matrix_4(4)(9):=(-18).S
  weight_matrix_4(5)(9):=(-9).S
  weight_matrix_4(6)(9):=(-7).S
  weight_matrix_4(7)(9):=(-12).S
  weight_matrix_4(8)(9):=(-6).S

  weight_matrix_5(0)(0):=(-12).S
  weight_matrix_5(1)(0):=(-14).S
  weight_matrix_5(2)(0):=(-14).S
  weight_matrix_5(3)(0):=(-14).S
  weight_matrix_5(4)(0):=(1).S
  weight_matrix_5(5)(0):=(18).S
  weight_matrix_5(6)(0):=(0).S
  weight_matrix_5(7)(0):=(13).S
  weight_matrix_5(8)(0):=(16).S
  weight_matrix_5(0)(1):=(-13).S
  weight_matrix_5(1)(1):=(22).S
  weight_matrix_5(2)(1):=(-9).S
  weight_matrix_5(3)(1):=(12).S
  weight_matrix_5(4)(1):=(12).S
  weight_matrix_5(5)(1):=(33).S
  weight_matrix_5(6)(1):=(21).S
  weight_matrix_5(7)(1):=(32).S
  weight_matrix_5(8)(1):=(23).S
  weight_matrix_5(0)(2):=(6).S
  weight_matrix_5(1)(2):=(14).S
  weight_matrix_5(2)(2):=(-13).S
  weight_matrix_5(3)(2):=(19).S
  weight_matrix_5(4)(2):=(10).S
  weight_matrix_5(5)(2):=(3).S
  weight_matrix_5(6)(2):=(-3).S
  weight_matrix_5(7)(2):=(2).S
  weight_matrix_5(8)(2):=(-26).S
  weight_matrix_5(0)(3):=(4).S
  weight_matrix_5(1)(3):=(1).S
  weight_matrix_5(2)(3):=(17).S
  weight_matrix_5(3)(3):=(3).S
  weight_matrix_5(4)(3):=(21).S
  weight_matrix_5(5)(3):=(18).S
  weight_matrix_5(6)(3):=(21).S
  weight_matrix_5(7)(3):=(17).S
  weight_matrix_5(8)(3):=(25).S
  weight_matrix_5(0)(4):=(-11).S
  weight_matrix_5(1)(4):=(13).S
  weight_matrix_5(2)(4):=(-4).S
  weight_matrix_5(3)(4):=(6).S
  weight_matrix_5(4)(4):=(-9).S
  weight_matrix_5(5)(4):=(8).S
  weight_matrix_5(6)(4):=(0).S
  weight_matrix_5(7)(4):=(20).S
  weight_matrix_5(8)(4):=(22).S
  weight_matrix_5(0)(5):=(6).S
  weight_matrix_5(1)(5):=(9).S
  weight_matrix_5(2)(5):=(7).S
  weight_matrix_5(3)(5):=(16).S
  weight_matrix_5(4)(5):=(19).S
  weight_matrix_5(5)(5):=(-7).S
  weight_matrix_5(6)(5):=(-13).S
  weight_matrix_5(7)(5):=(15).S
  weight_matrix_5(8)(5):=(14).S
  weight_matrix_5(0)(6):=(17).S
  weight_matrix_5(1)(6):=(18).S
  weight_matrix_5(2)(6):=(13).S
  weight_matrix_5(3)(6):=(-12).S
  weight_matrix_5(4)(6):=(-6).S
  weight_matrix_5(5)(6):=(-13).S
  weight_matrix_5(6)(6):=(-21).S
  weight_matrix_5(7)(6):=(7).S
  weight_matrix_5(8)(6):=(5).S
  weight_matrix_5(0)(7):=(-12).S
  weight_matrix_5(1)(7):=(-6).S
  weight_matrix_5(2)(7):=(20).S
  weight_matrix_5(3)(7):=(-9).S
  weight_matrix_5(4)(7):=(-13).S
  weight_matrix_5(5)(7):=(5).S
  weight_matrix_5(6)(7):=(-12).S
  weight_matrix_5(7)(7):=(-14).S
  weight_matrix_5(8)(7):=(10).S
  weight_matrix_5(0)(8):=(19).S
  weight_matrix_5(1)(8):=(-12).S
  weight_matrix_5(2)(8):=(-10).S
  weight_matrix_5(3)(8):=(-7).S
  weight_matrix_5(4)(8):=(-16).S
  weight_matrix_5(5)(8):=(-3).S
  weight_matrix_5(6)(8):=(8).S
  weight_matrix_5(7)(8):=(-5).S
  weight_matrix_5(8)(8):=(-24).S
  weight_matrix_5(0)(9):=(-7).S
  weight_matrix_5(1)(9):=(-10).S
  weight_matrix_5(2)(9):=(12).S
  weight_matrix_5(3)(9):=(-5).S
  weight_matrix_5(4)(9):=(1).S
  weight_matrix_5(5)(9):=(10).S
  weight_matrix_5(6)(9):=(-3).S
  weight_matrix_5(7)(9):=(8).S
  weight_matrix_5(8)(9):=(3).S

  weight_matrix_6(0)(0):=(10).S
  weight_matrix_6(1)(0):=(6).S
  weight_matrix_6(2)(0):=(11).S
  weight_matrix_6(3)(0):=(11).S
  weight_matrix_6(4)(0):=(-12).S
  weight_matrix_6(5)(0):=(18).S
  weight_matrix_6(6)(0):=(-17).S
  weight_matrix_6(7)(0):=(-7).S
  weight_matrix_6(8)(0):=(-3).S
  weight_matrix_6(0)(1):=(13).S
  weight_matrix_6(1)(1):=(1).S
  weight_matrix_6(2)(1):=(-9).S
  weight_matrix_6(3)(1):=(14).S
  weight_matrix_6(4)(1):=(12).S
  weight_matrix_6(5)(1):=(-8).S
  weight_matrix_6(6)(1):=(-10).S
  weight_matrix_6(7)(1):=(-15).S
  weight_matrix_6(8)(1):=(-10).S
  weight_matrix_6(0)(2):=(12).S
  weight_matrix_6(1)(2):=(-12).S
  weight_matrix_6(2)(2):=(-2).S
  weight_matrix_6(3)(2):=(-18).S
  weight_matrix_6(4)(2):=(4).S
  weight_matrix_6(5)(2):=(0).S
  weight_matrix_6(6)(2):=(-10).S
  weight_matrix_6(7)(2):=(16).S
  weight_matrix_6(8)(2):=(12).S
  weight_matrix_6(0)(3):=(-8).S
  weight_matrix_6(1)(3):=(-8).S
  weight_matrix_6(2)(3):=(-14).S
  weight_matrix_6(3)(3):=(5).S
  weight_matrix_6(4)(3):=(-11).S
  weight_matrix_6(5)(3):=(-14).S
  weight_matrix_6(6)(3):=(16).S
  weight_matrix_6(7)(3):=(-2).S
  weight_matrix_6(8)(3):=(11).S
  weight_matrix_6(0)(4):=(-11).S
  weight_matrix_6(1)(4):=(4).S
  weight_matrix_6(2)(4):=(-2).S
  weight_matrix_6(3)(4):=(8).S
  weight_matrix_6(4)(4):=(-5).S
  weight_matrix_6(5)(4):=(5).S
  weight_matrix_6(6)(4):=(-7).S
  weight_matrix_6(7)(4):=(12).S
  weight_matrix_6(8)(4):=(-11).S
  weight_matrix_6(0)(5):=(-17).S
  weight_matrix_6(1)(5):=(-8).S
  weight_matrix_6(2)(5):=(13).S
  weight_matrix_6(3)(5):=(2).S
  weight_matrix_6(4)(5):=(4).S
  weight_matrix_6(5)(5):=(16).S
  weight_matrix_6(6)(5):=(-8).S
  weight_matrix_6(7)(5):=(14).S
  weight_matrix_6(8)(5):=(0).S
  weight_matrix_6(0)(6):=(2).S
  weight_matrix_6(1)(6):=(-12).S
  weight_matrix_6(2)(6):=(15).S
  weight_matrix_6(3)(6):=(-10).S
  weight_matrix_6(4)(6):=(-19).S
  weight_matrix_6(5)(6):=(-14).S
  weight_matrix_6(6)(6):=(0).S
  weight_matrix_6(7)(6):=(12).S
  weight_matrix_6(8)(6):=(13).S
  weight_matrix_6(0)(7):=(-9).S
  weight_matrix_6(1)(7):=(-1).S
  weight_matrix_6(2)(7):=(3).S
  weight_matrix_6(3)(7):=(-4).S
  weight_matrix_6(4)(7):=(-2).S
  weight_matrix_6(5)(7):=(-12).S
  weight_matrix_6(6)(7):=(15).S
  weight_matrix_6(7)(7):=(13).S
  weight_matrix_6(8)(7):=(0).S
  weight_matrix_6(0)(8):=(-5).S
  weight_matrix_6(1)(8):=(-12).S
  weight_matrix_6(2)(8):=(17).S
  weight_matrix_6(3)(8):=(-13).S
  weight_matrix_6(4)(8):=(-18).S
  weight_matrix_6(5)(8):=(2).S
  weight_matrix_6(6)(8):=(18).S
  weight_matrix_6(7)(8):=(-14).S
  weight_matrix_6(8)(8):=(14).S
  weight_matrix_6(0)(9):=(16).S
  weight_matrix_6(1)(9):=(11).S
  weight_matrix_6(2)(9):=(15).S
  weight_matrix_6(3)(9):=(5).S
  weight_matrix_6(4)(9):=(-14).S
  weight_matrix_6(5)(9):=(-11).S
  weight_matrix_6(6)(9):=(12).S
  weight_matrix_6(7)(9):=(5).S
  weight_matrix_6(8)(9):=(-4).S

  weight_matrix_7(0)(0):=(14).S
  weight_matrix_7(1)(0):=(12).S
  weight_matrix_7(2)(0):=(-7).S
  weight_matrix_7(3)(0):=(19).S
  weight_matrix_7(4)(0):=(8).S
  weight_matrix_7(5)(0):=(21).S
  weight_matrix_7(6)(0):=(13).S
  weight_matrix_7(7)(0):=(1).S
  weight_matrix_7(8)(0):=(23).S
  weight_matrix_7(0)(1):=(24).S
  weight_matrix_7(1)(1):=(1).S
  weight_matrix_7(2)(1):=(2).S
  weight_matrix_7(3)(1):=(26).S
  weight_matrix_7(4)(1):=(13).S
  weight_matrix_7(5)(1):=(5).S
  weight_matrix_7(6)(1):=(-7).S
  weight_matrix_7(7)(1):=(25).S
  weight_matrix_7(8)(1):=(12).S
  weight_matrix_7(0)(2):=(25).S
  weight_matrix_7(1)(2):=(22).S
  weight_matrix_7(2)(2):=(-3).S
  weight_matrix_7(3)(2):=(15).S
  weight_matrix_7(4)(2):=(20).S
  weight_matrix_7(5)(2):=(-1).S
  weight_matrix_7(6)(2):=(21).S
  weight_matrix_7(7)(2):=(-1).S
  weight_matrix_7(8)(2):=(7).S
  weight_matrix_7(0)(3):=(-22).S
  weight_matrix_7(1)(3):=(-12).S
  weight_matrix_7(2)(3):=(-4).S
  weight_matrix_7(3)(3):=(11).S
  weight_matrix_7(4)(3):=(10).S
  weight_matrix_7(5)(3):=(-13).S
  weight_matrix_7(6)(3):=(11).S
  weight_matrix_7(7)(3):=(4).S
  weight_matrix_7(8)(3):=(-19).S
  weight_matrix_7(0)(4):=(20).S
  weight_matrix_7(1)(4):=(-3).S
  weight_matrix_7(2)(4):=(19).S
  weight_matrix_7(3)(4):=(0).S
  weight_matrix_7(4)(4):=(9).S
  weight_matrix_7(5)(4):=(12).S
  weight_matrix_7(6)(4):=(-7).S
  weight_matrix_7(7)(4):=(15).S
  weight_matrix_7(8)(4):=(-16).S
  weight_matrix_7(0)(5):=(-5).S
  weight_matrix_7(1)(5):=(-13).S
  weight_matrix_7(2)(5):=(9).S
  weight_matrix_7(3)(5):=(-14).S
  weight_matrix_7(4)(5):=(-2).S
  weight_matrix_7(5)(5):=(3).S
  weight_matrix_7(6)(5):=(8).S
  weight_matrix_7(7)(5):=(-3).S
  weight_matrix_7(8)(5):=(1).S
  weight_matrix_7(0)(6):=(4).S
  weight_matrix_7(1)(6):=(2).S
  weight_matrix_7(2)(6):=(11).S
  weight_matrix_7(3)(6):=(8).S
  weight_matrix_7(4)(6):=(-14).S
  weight_matrix_7(5)(6):=(-18).S
  weight_matrix_7(6)(6):=(17).S
  weight_matrix_7(7)(6):=(1).S
  weight_matrix_7(8)(6):=(11).S
  weight_matrix_7(0)(7):=(-1).S
  weight_matrix_7(1)(7):=(-15).S
  weight_matrix_7(2)(7):=(-4).S
  weight_matrix_7(3)(7):=(-23).S
  weight_matrix_7(4)(7):=(3).S
  weight_matrix_7(5)(7):=(9).S
  weight_matrix_7(6)(7):=(-5).S
  weight_matrix_7(7)(7):=(-7).S
  weight_matrix_7(8)(7):=(11).S
  weight_matrix_7(0)(8):=(12).S
  weight_matrix_7(1)(8):=(0).S
  weight_matrix_7(2)(8):=(13).S
  weight_matrix_7(3)(8):=(13).S
  weight_matrix_7(4)(8):=(11).S
  weight_matrix_7(5)(8):=(4).S
  weight_matrix_7(6)(8):=(-14).S
  weight_matrix_7(7)(8):=(18).S
  weight_matrix_7(8)(8):=(2).S
  weight_matrix_7(0)(9):=(-14).S
  weight_matrix_7(1)(9):=(7).S
  weight_matrix_7(2)(9):=(11).S
  weight_matrix_7(3)(9):=(-7).S
  weight_matrix_7(4)(9):=(-14).S
  weight_matrix_7(5)(9):=(13).S
  weight_matrix_7(6)(9):=(16).S
  weight_matrix_7(7)(9):=(3).S
  weight_matrix_7(8)(9):=(-7).S

  weight_matrix_8(0)(0):=(-4).S
  weight_matrix_8(1)(0):=(18).S
  weight_matrix_8(2)(0):=(10).S
  weight_matrix_8(3)(0):=(30).S
  weight_matrix_8(4)(0):=(22).S
  weight_matrix_8(5)(0):=(5).S
  weight_matrix_8(6)(0):=(-6).S
  weight_matrix_8(7)(0):=(22).S
  weight_matrix_8(8)(0):=(-4).S
  weight_matrix_8(0)(1):=(24).S
  weight_matrix_8(1)(1):=(9).S
  weight_matrix_8(2)(1):=(1).S
  weight_matrix_8(3)(1):=(7).S
  weight_matrix_8(4)(1):=(-6).S
  weight_matrix_8(5)(1):=(-14).S
  weight_matrix_8(6)(1):=(-12).S
  weight_matrix_8(7)(1):=(15).S
  weight_matrix_8(8)(1):=(9).S
  weight_matrix_8(0)(2):=(22).S
  weight_matrix_8(1)(2):=(-5).S
  weight_matrix_8(2)(2):=(22).S
  weight_matrix_8(3)(2):=(-8).S
  weight_matrix_8(4)(2):=(-6).S
  weight_matrix_8(5)(2):=(-6).S
  weight_matrix_8(6)(2):=(-1).S
  weight_matrix_8(7)(2):=(-11).S
  weight_matrix_8(8)(2):=(3).S
  weight_matrix_8(0)(3):=(-20).S
  weight_matrix_8(1)(3):=(-13).S
  weight_matrix_8(2)(3):=(13).S
  weight_matrix_8(3)(3):=(6).S
  weight_matrix_8(4)(3):=(-10).S
  weight_matrix_8(5)(3):=(-15).S
  weight_matrix_8(6)(3):=(-13).S
  weight_matrix_8(7)(3):=(-17).S
  weight_matrix_8(8)(3):=(-15).S
  weight_matrix_8(0)(4):=(1).S
  weight_matrix_8(1)(4):=(-3).S
  weight_matrix_8(2)(4):=(-18).S
  weight_matrix_8(3)(4):=(-3).S
  weight_matrix_8(4)(4):=(-8).S
  weight_matrix_8(5)(4):=(-18).S
  weight_matrix_8(6)(4):=(12).S
  weight_matrix_8(7)(4):=(-4).S
  weight_matrix_8(8)(4):=(-9).S
  weight_matrix_8(0)(5):=(-7).S
  weight_matrix_8(1)(5):=(1).S
  weight_matrix_8(2)(5):=(6).S
  weight_matrix_8(3)(5):=(18).S
  weight_matrix_8(4)(5):=(-11).S
  weight_matrix_8(5)(5):=(-10).S
  weight_matrix_8(6)(5):=(-2).S
  weight_matrix_8(7)(5):=(-2).S
  weight_matrix_8(8)(5):=(-6).S
  weight_matrix_8(0)(6):=(-9).S
  weight_matrix_8(1)(6):=(15).S
  weight_matrix_8(2)(6):=(-1).S
  weight_matrix_8(3)(6):=(-20).S
  weight_matrix_8(4)(6):=(-4).S
  weight_matrix_8(5)(6):=(-21).S
  weight_matrix_8(6)(6):=(16).S
  weight_matrix_8(7)(6):=(13).S
  weight_matrix_8(8)(6):=(-7).S
  weight_matrix_8(0)(7):=(-15).S
  weight_matrix_8(1)(7):=(-1).S
  weight_matrix_8(2)(7):=(-18).S
  weight_matrix_8(3)(7):=(-5).S
  weight_matrix_8(4)(7):=(-10).S
  weight_matrix_8(5)(7):=(19).S
  weight_matrix_8(6)(7):=(12).S
  weight_matrix_8(7)(7):=(3).S
  weight_matrix_8(8)(7):=(22).S
  weight_matrix_8(0)(8):=(-4).S
  weight_matrix_8(1)(8):=(18).S
  weight_matrix_8(2)(8):=(-13).S
  weight_matrix_8(3)(8):=(11).S
  weight_matrix_8(4)(8):=(13).S
  weight_matrix_8(5)(8):=(-2).S
  weight_matrix_8(6)(8):=(17).S
  weight_matrix_8(7)(8):=(19).S
  weight_matrix_8(8)(8):=(20).S
  weight_matrix_8(0)(9):=(-12).S
  weight_matrix_8(1)(9):=(15).S
  weight_matrix_8(2)(9):=(-15).S
  weight_matrix_8(3)(9):=(12).S
  weight_matrix_8(4)(9):=(6).S
  weight_matrix_8(5)(9):=(16).S
  weight_matrix_8(6)(9):=(-11).S
  weight_matrix_8(7)(9):=(-17).S
  weight_matrix_8(8)(9):=(-8).S

  weight_matrix_9(0)(0):=(13).S
  weight_matrix_9(1)(0):=(-17).S
  weight_matrix_9(2)(0):=(-17).S
  weight_matrix_9(3)(0):=(10).S
  weight_matrix_9(4)(0):=(-14).S
  weight_matrix_9(5)(0):=(-8).S
  weight_matrix_9(6)(0):=(7).S
  weight_matrix_9(7)(0):=(9).S
  weight_matrix_9(8)(0):=(2).S
  weight_matrix_9(0)(1):=(2).S
  weight_matrix_9(1)(1):=(-3).S
  weight_matrix_9(2)(1):=(-25).S
  weight_matrix_9(3)(1):=(2).S
  weight_matrix_9(4)(1):=(-22).S
  weight_matrix_9(5)(1):=(-30).S
  weight_matrix_9(6)(1):=(-12).S
  weight_matrix_9(7)(1):=(-25).S
  weight_matrix_9(8)(1):=(0).S
  weight_matrix_9(0)(2):=(9).S
  weight_matrix_9(1)(2):=(8).S
  weight_matrix_9(2)(2):=(16).S
  weight_matrix_9(3)(2):=(-1).S
  weight_matrix_9(4)(2):=(-11).S
  weight_matrix_9(5)(2):=(-1).S
  weight_matrix_9(6)(2):=(0).S
  weight_matrix_9(7)(2):=(13).S
  weight_matrix_9(8)(2):=(14).S
  weight_matrix_9(0)(3):=(8).S
  weight_matrix_9(1)(3):=(27).S
  weight_matrix_9(2)(3):=(18).S
  weight_matrix_9(3)(3):=(12).S
  weight_matrix_9(4)(3):=(21).S
  weight_matrix_9(5)(3):=(32).S
  weight_matrix_9(6)(3):=(21).S
  weight_matrix_9(7)(3):=(22).S
  weight_matrix_9(8)(3):=(13).S
  weight_matrix_9(0)(4):=(3).S
  weight_matrix_9(1)(4):=(4).S
  weight_matrix_9(2)(4):=(-14).S
  weight_matrix_9(3)(4):=(-17).S
  weight_matrix_9(4)(4):=(-12).S
  weight_matrix_9(5)(4):=(-18).S
  weight_matrix_9(6)(4):=(-3).S
  weight_matrix_9(7)(4):=(-15).S
  weight_matrix_9(8)(4):=(-16).S
  weight_matrix_9(0)(5):=(12).S
  weight_matrix_9(1)(5):=(-5).S
  weight_matrix_9(2)(5):=(-11).S
  weight_matrix_9(3)(5):=(-15).S
  weight_matrix_9(4)(5):=(3).S
  weight_matrix_9(5)(5):=(-2).S
  weight_matrix_9(6)(5):=(12).S
  weight_matrix_9(7)(5):=(-5).S
  weight_matrix_9(8)(5):=(7).S
  weight_matrix_9(0)(6):=(-12).S
  weight_matrix_9(1)(6):=(18).S
  weight_matrix_9(2)(6):=(7).S
  weight_matrix_9(3)(6):=(-4).S
  weight_matrix_9(4)(6):=(5).S
  weight_matrix_9(5)(6):=(-10).S
  weight_matrix_9(6)(6):=(-11).S
  weight_matrix_9(7)(6):=(6).S
  weight_matrix_9(8)(6):=(12).S
  weight_matrix_9(0)(7):=(1).S
  weight_matrix_9(1)(7):=(27).S
  weight_matrix_9(2)(7):=(10).S
  weight_matrix_9(3)(7):=(17).S
  weight_matrix_9(4)(7):=(30).S
  weight_matrix_9(5)(7):=(14).S
  weight_matrix_9(6)(7):=(16).S
  weight_matrix_9(7)(7):=(28).S
  weight_matrix_9(8)(7):=(18).S
  weight_matrix_9(0)(8):=(-1).S
  weight_matrix_9(1)(8):=(-16).S
  weight_matrix_9(2)(8):=(3).S
  weight_matrix_9(3)(8):=(24).S
  weight_matrix_9(4)(8):=(20).S
  weight_matrix_9(5)(8):=(13).S
  weight_matrix_9(6)(8):=(11).S
  weight_matrix_9(7)(8):=(-10).S
  weight_matrix_9(8)(8):=(17).S
  weight_matrix_9(0)(9):=(-2).S
  weight_matrix_9(1)(9):=(-11).S
  weight_matrix_9(2)(9):=(2).S
  weight_matrix_9(3)(9):=(-9).S
  weight_matrix_9(4)(9):=(10).S
  weight_matrix_9(5)(9):=(5).S
  weight_matrix_9(6)(9):=(9).S
  weight_matrix_9(7)(9):=(11).S
  weight_matrix_9(8)(9):=(-6).S



  //BIAS


  val CONV0 = Module(new TPU((W_after_conv) * (W_after_conv), 10))
  CONV0.io.in_A := INPUT_COL_0.io.out_result
  CONV0.io.in_B := weight_matrix_0
  CONV0.io.in_cal := true.B

  val CONV1 = Module(new TPU((W_after_conv) * (W_after_conv), 10))
  CONV1.io.in_A := INPUT_COL_1.io.out_result
  CONV1.io.in_B := weight_matrix_1
  CONV1.io.in_cal := true.B

  val CONV2 = Module(new TPU((W_after_conv) * (W_after_conv), 10))
  CONV2.io.in_A := INPUT_COL_2.io.out_result
  CONV2.io.in_B := weight_matrix_2
  CONV2.io.in_cal := true.B

  val CONV3 = Module(new TPU((W_after_conv) * (W_after_conv), 10))
  CONV3.io.in_A := INPUT_COL_3.io.out_result
  CONV3.io.in_B := weight_matrix_3
  CONV3.io.in_cal := true.B

  val CONV4 = Module(new TPU((W_after_conv) * (W_after_conv), 10))
  CONV4.io.in_A := INPUT_COL_4.io.out_result
  CONV4.io.in_B := weight_matrix_4
  CONV4.io.in_cal := true.B

  val CONV5 = Module(new TPU((W_after_conv) * (W_after_conv), 10))
  CONV5.io.in_A := INPUT_COL_5.io.out_result
  CONV5.io.in_B := weight_matrix_5
  CONV5.io.in_cal := true.B

  val CONV6 = Module(new TPU((W_after_conv) * (W_after_conv), 10))
  CONV6.io.in_A := INPUT_COL_6.io.out_result
  CONV6.io.in_B := weight_matrix_6
  CONV6.io.in_cal := true.B

  val CONV7 = Module(new TPU((W_after_conv) * (W_after_conv), 10))
  CONV7.io.in_A := INPUT_COL_7.io.out_result
  CONV7.io.in_B := weight_matrix_7
  CONV7.io.in_cal := true.B

  val CONV8 = Module(new TPU((W_after_conv) * (W_after_conv), 10))
  CONV8.io.in_A := INPUT_COL_8.io.out_result
  CONV8.io.in_B := weight_matrix_8
  CONV8.io.in_cal := true.B

  val CONV9 = Module(new TPU((W_after_conv) * (W_after_conv), 10))
  CONV9.io.in_A := INPUT_COL_9.io.out_result
  CONV9.io.in_B := weight_matrix_9
  CONV9.io.in_cal := true.B

  //BIAS

  val bias_matrix = RegInit(Vec(Seq.fill(channel_D)(0.S(32.W))))

  bias_matrix(0) := (99).S
  bias_matrix(1) := (106).S
  bias_matrix(2) := (44).S
  bias_matrix(3) := (27).S
  bias_matrix(4) := (4).S
  bias_matrix(5) := (-17).S
  bias_matrix(6) := (-59).S
  bias_matrix(7) := (151).S
  bias_matrix(8) := (95).S
  bias_matrix(9) := (92).S

  //IMG2COL
  //after transfer IMG: (60*60)*9 WEIGHT: 9*10

  //add bias & colum2img

  val matrix_after_conv = RegInit(Vec(Seq.fill(W_after_conv)(Vec(Seq.fill(W_after_conv)(Vec(Seq.fill(channel_D)(0.S(32.W))))))))

  for (i <- 0 until (W_after_conv - 1)) { //ROW
    for (j <- 0 until (W_after_conv - 1)) { //COL
      for (k <- 0 until (channel_D - 1)) { //channel
        //ROW = i*W_after_conv + j
        matrix_after_conv(i)(j)(k) := (CONV0.io.out_result(k)(i * W_after_conv + j) + CONV1.io.out_result(k)(i * W_after_conv + j) +
          CONV2.io.out_result(k)(i * W_after_conv + j) + CONV3.io.out_result(k)(i * W_after_conv + j) +
          CONV4.io.out_result(k)(i * W_after_conv + j) + CONV5.io.out_result(k)(i * W_after_conv + j) +
          CONV4.io.out_result(k)(i * W_after_conv + j) + CONV5.io.out_result(k)(i * W_after_conv + j) +
          CONV6.io.out_result(k)(i * W_after_conv + j) + CONV7.io.out_result(k)(i * W_after_conv + j) +
          CONV8.io.out_result(k)(i * W_after_conv + j) + CONV9.io.out_result(k)(i * W_after_conv + j)) * bias_matrix(k)
      }
    }
  }

  //RELU

  val matrix_after_relu = RegInit(Vec(Seq.fill(W_after_conv)(Vec(Seq.fill(W_after_conv)(Vec(Seq.fill(channel_D)(0.S(32.W))))))))
  val matrix_relu = Module(new RELU(W_after_conv, 10))
  matrix_relu.io.in_matrix := matrix_after_conv
  matrix_after_relu := matrix_relu.io.out_result

  io.out_result := matrix_after_relu






}

//object Main {
//  def main(args: Array[String]): Unit = {
//    println("CONV2D for input image")
//    chisel3.Driver.execute(args, () => new RELU(4,10))
//  }
//}
