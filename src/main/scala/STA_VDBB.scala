//import chisel3._
//
///*
//Description: in this matrices A and B will be input, the they will be
//  pre-processed, taged and devided into many parts, then we will let
//  data going through in three*X clock circles
//
//Inputs:
//  in_A: matrices A, 4 rows 16 column
//  in_B: matrices B, 16 rows 8 column
//    notion: 矩阵B每半行非零项个数要一样！！！
//Regs:
//  Reg for inputs like A00, A01 ...
//  Reg between S8DP1s, to store tag and
//    FFB1    FFB2
//FFA1    FF00
//    FF01    FF10
//FFA2    FF11
//
//
//Outputs:
//  out_C: matrices C, 4 rows 8 column (just like the structure)
// */
//
//object BLOCK_SIZE_VDBB {
//  //M: Rows
//  //N: Column
//  val M_in_A = 4
//  val N_in_A = 16
//  val M_in_B = 16
//  val N_in_B = 8
//}
//
//class STA_VDBB(val w: Int = 32, val m_in_A: Int = BLOCK_SIZE_VDBB.M_in_A, val n_in_A: Int = BLOCK_SIZE_VDBB.N_in_A,
//               val m_in_B: Int = BLOCK_SIZE_VDBB.M_in_B, val n_in_B: Int = BLOCK_SIZE_VDBB.N_in_B) extends Module{
//  val io = IO(new Bundle{
//    val in_A = Input(Vec(m_in_A, Vec(m_in_B, SInt(w.W))))
//    val in_B = Input(Vec(n_in_A, Vec(n_in_B, SInt(w.W))))
//    val out_C = Output(Vec(m_in_A, Vec(n_in_B, SInt(w.W))))
//  })
//
//  //Define modules
//  /*
//  0 1
//  2 3
//
//  0 1 2 3
//  4 5 6 7
//   */
//
////  val S8DP1s = VecInit(Seq.fill(4)(VecInit(Seq.fill(8)(Module(new S8DP1).io))))
//
//  val S8DP1s = Vec(4, Vec(8, (new S8DP1).io))
//  val taggers = VecInit(Seq.fill(4)(Module(new tag).io))
//  val muxs = VecInit(Seq.fill(4)(Module(new MUX8).io))
//
//  //Define Regs
//  val ffb1 = Module(new ff4)
//  val ffb2 = Module(new ff4)
//
//  val ffa1 = Module(new ff8)
//  val ff00 = Module(new ff8)
//
//  val ff01 = Module(new ff4)
//  val ff10 = Module(new ff4)
//
//  val ffa2 = Module(new ff8)
//  val ff11 = Module(new ff8)
//
//  val count = RegInit(UInt(4.W))
//
//  val zero = RegInit(Vec(4, Vec(0.U, 0.U, 0.U, 0.U, 0.U, 0.U, 0.U, 0.U)))
//
//  for (i <- 0 until 7) {
//    taggers(0).in_data(i) := io.in_B(0)(i)
//  }
//
//  count := taggers(0).out_count
//
//  /*
//  Now we come to the loops
//   */
//
//  //Loop1
//    //Data fetch
//  for (i <- 0 until 7){
//    //input A
//    for (j <- 0 until 1) {
//      //ffa1.io.in_data(j)(i) := io.in_A(j)(i)
//      //ff00.io.in_data(j)(i) := io.in_A(j)(i)
//      ffa1.io.in_data(ffa1.n * j + i + 1) := io.in_A(ffa1.n * j + i + 1)
//      ff00.io.in_data(ff00.n * j + i + 1) := io.in_A(ff00.n * j + i + 1)
//    }
//
//    //input and process B
//    for (j <- 0 until 3) {
//      //ffb1.io.in_data(j)(i) := io.in_B(j)(i)
//      //ff01.io.in_data(j)(i) := io.in_B(j)(i)
//      ffb1.io.in_data(ffb1.data_col * j + i + 1) := io.in_B(ffb1.data_col * j + i + 1)
//      ff01.io.in_data(ff01.data_col * j + i + 1) := io.in_B(ff01.data_col * j + i + 1)
//    }
//  }
//
//    //Data tag for B
//  for (i <- 0 until 3) {
//    taggers(i).in_data := ffb1.io.out_data(i)
//    ffb1.io.in_tag(i) := taggers(i).out_tag
//    ff01.io.in_tag(i) := taggers(i).out_tag
//  }
//
//    //Begin to compute
//    /*
//    + -
//    - -
//    */
//  for (k <- 0 until 7) {
//    if (ffb1.io.out_tag != zero) {
//      for (i <- 0 until 3) {  //column
//        muxs(i).int_in := ffb1.io.out_data(i)
//        muxs(i).tag := ffb1.io.out_tag
//        for (j <-0 until 1) { //row
//          S8DP1s(j)(i).int_in_A := ffa1.io.out_data
//          S8DP1s(j)(i).int_in_B := muxs(i).choice
//          S8DP1s(j)(i).tag := ffb1.io.out_tag(i)
//
//          ffb1.io.in_tag(i) := S8DP1s(j)(i).out_tag
//        }
//      }
//    }
//  }
//
//
//  //Loop2
//  //Data fetch
//  for (i <- 0 until 7){
//    //input A
//    /*
//    - -
//    + -
//    */
//    for (j <- 0 until 1) {
//      ffa2.io.in_data(j)(i) := io.in_A(j+2)(i)
//      ff11.io.in_data(j)(i) := io.in_A(j+2)(i)
//    }
//
//    //input and process B
//    /*
//    - +
//    - -
//    */
//    for (j <- 0 until 3) {
//      ffb2.io.in_data(j)(i) := io.in_B(j+4)(i)
//      ff10.io.in_data(j)(i) := io.in_B(j+4)(i)
//    }
//  }
//
//  //Data tag for B
//  for (i <- 0 until 3) {
//    taggers(i).in_data := ffb1.io.out_data(i)
//    ffb2.io.in_tag(i) := taggers(i).out_tag
//    ff10.io.in_tag(i) := taggers(i).out_tag
//  }
//
//  //Begin to compute
//  /*
//  - 2
//  3 4
//  */
//  for (k <- 0 until 7) {
//    if (ffb1.io.out_tag != zero) {
//      //2
//      for (i <- 0 until 3) {  //column
//        muxs(i).int_in := ffb2.io.out_data(i)
//        muxs(i).tag := ffb2.io.out_tag
//        for (j <-0 until 1) { //row
//          S8DP1s(j)(i+4).int_in_A := ff00.io.out_data
//          S8DP1s(j)(i+4).int_in_B := muxs(i).choice
//          S8DP1s(j)(i+4).tag := ffb2.io.out_tag(i)
//
//          ffb2.io.in_tag(i) := S8DP1s(j)(i+4).out_tag
//        }
//      }
//
//      //3
//      for (i <- 0 until 3) {  //column
//        muxs(i).int_in := ff01.io.out_data(i)
//        muxs(i).tag := ff01.io.out_tag
//        for (j <-0 until 1) { //row
//          S8DP1s(j+2)(i).int_in_A := ff01.io.out_data
//          S8DP1s(j+2)(i).int_in_B := muxs(i).choice
//          S8DP1s(j+2)(i).tag := ff01.io.out_tag(i)
//
//          ff01.io.in_tag(i) := S8DP1s(j+2)(i).out_tag
//        }
//      }
//
//      //4
//      for (i <- 0 until 3) {  //column
//        muxs(i).int_in := ff10.io.out_data(i)
//        muxs(i).tag := ff10.io.out_tag
//        for (j <-0 until 1) { //row
//          S8DP1s(j+2)(i+4).int_in_A := ff11.io.out_data
//          S8DP1s(j+2)(i+4).int_in_B := muxs(i).choice
//          S8DP1s(j+2)(i+4).tag := ff10.io.out_tag(i)
//
//          ffb1.io.in_tag(i) := S8DP1s(j)(i+4).out_tag
//        }
//      }
//    }
//  }
//
//
//
//  //Loop3
//  //Data fetch
//  for (i <- 0 until 7){
//    //input A
//    /*
//    - +
//    - -
//    */
//    for (j <- 0 until 1) {
//      ffa1.io.in_data(j)(i) := io.in_A(j)(i+8)
//      ff00.io.in_data(j)(i) := io.in_A(j)(i+8)
//    }
//
//    //input and process B
//    /*
//    - -
//    + -
//    */
//    for (j <- 0 until 3) {
//      ffb1.io.in_data(j)(i) := io.in_B(j)(i+8)
//      ff01.io.in_data(j)(i) := io.in_B(j)(i+8)
//    }
//  }
//
//  //Data tag for B
//  for (i <- 0 until 3) {
//    taggers(i).in_data := ffb1.io.out_data(i)
//    ffb1.io.in_tag(i) := taggers(i).out_tag
//    ff01.io.in_tag(i) := taggers(i).out_tag
//  }
//
//  //Begin to compute
//  /*
//  + -
//  - -
//  */
//  for (k <- 0 until 7) {
//    if (ffb1.io.out_tag != zero) {
//      for (i <- 0 until 3) {  //column
//        muxs(i).int_in := ffb1.io.out_data(i)
//        muxs(i).tag := ffb1.io.out_tag
//        for (j <-0 until 1) { //row
//          S8DP1s(j)(i).int_in_A := ffa1.io.out_data
//          S8DP1s(j)(i).int_in_B := muxs(i).choice
//          S8DP1s(j)(i).tag := ffb1.io.out_tag(i)
//
//          ffb1.io.in_tag(i) := S8DP1s(j)(i).out_tag
//        }
//      }
//    }
//  }
//
//  //Loop4
//  //Data fetch
//  for (i <- 0 until 7){
//    //input A
//    /*
//    - -
//    - +
//    */
//    for (j <- 0 until 1) {
//      ffa2.io.in_data(j)(i) := io.in_A(j+2)(i+8)
//      ff11.io.in_data(j)(i) := io.in_A(j+2)(i+8)
//    }
//
//    //input and process B
//    /*
//    - -
//    - +
//    */
//    for (j <- 0 until 3) {
//      ffb2.io.in_data(j)(i) := io.in_B(j+4)(i+8)
//      ff10.io.in_data(j)(i) := io.in_B(j+4)(i+8)
//    }
//  }
//
//  //Data tag for B
//  for (i <- 0 until 3) {
//    taggers(i).in_data := ffb1.io.out_data(i)
//    ffb2.io.in_tag(i) := taggers(i).out_tag
//    ff10.io.in_tag(i) := taggers(i).out_tag
//  }
//
//  //Begin to compute
//  /*
//  - 2
//  3 4
//  */
//  for (k <- 0 until 7) {
//    if (ffb1.io.out_tag != zero) {
//      //2
//      for (i <- 0 until 3) {  //column
//        muxs(i).int_in := ffb2.io.out_data(i)
//        muxs(i).tag := ffb2.io.out_tag
//        for (j <-0 until 1) { //row
//          S8DP1s(j)(i+4).int_in_A := ff00.io.out_data
//          S8DP1s(j)(i+4).int_in_B := muxs(i).choice
//          S8DP1s(j)(i+4).tag := ffb2.io.out_tag(i)
//
//          ffb2.io.in_tag(i) := S8DP1s(j)(i+4).out_tag
//        }
//      }
//
//      //3
//      for (i <- 0 until 3) {  //column
//        muxs(i).int_in := ff01.io.out_data(i)
//        muxs(i).tag := ff01.io.out_tag
//        for (j <-0 until 1) { //row
//          S8DP1s(j+2)(i).int_in_A := ff01.io.out_data
//          S8DP1s(j+2)(i).int_in_B := muxs(i).choice
//          S8DP1s(j+2)(i).tag := ff01.io.out_tag(i)
//
//          ff01.io.in_tag(i) := S8DP1s(j+2)(i).out_tag
//        }
//      }
//
//      //4
//      for (i <- 0 until 3) {  //column
//        muxs(i).int_in := ff10.io.out_data(i)
//        muxs(i).tag := ff10.io.out_tag
//        for (j <-0 until 1) { //row
//          S8DP1s(j+2)(i+4).int_in_A := ff11.io.out_data
//          S8DP1s(j+2)(i+4).int_in_B := muxs(i).choice
//          S8DP1s(j+2)(i+4).tag := ff10.io.out_tag(i)
//
//          ffb1.io.in_tag(i) := S8DP1s(j)(i+4).out_tag
//        }
//      }
//    }
//  }
//
//  //output
//  val out_result = RegInit(Vec(4, Vec(8, SInt(32.W))))
//
//  for (i <- 0 until 7) {
//    for (j <- 0 until 3) {
//      out_result(i)(j) := S8DP1s(j)(i).result
//    }
//  }
//
//  io.out_C := out_result
//
//}
