import chisel3._

/*
Description: in this matrices A and B will be input, the they will be
  pre-processed, taged and devided into many parts, then we will let
  data going through in three*X clock circles

Inputs:
  in_A: matrices A, 4 rows 16 column
  in_B: matrices B, 16 rows 8 column
    notion: 矩阵B每半行非零项个数要一样！！！
Regs:
  Reg for inputs like A00, A01 ...
  Reg between S8DP1s, to store tag and
    FFB1    FFB2
FFA1    FF00
    FF01    FF10
FFA2    FF11


Outputs:
  out_C: matrices C, 4 rows 8 column (just like the structure)
 */

object BLOCK_SIZE_VDBB {
  //M: Rows
  //N: Column
  val ROW_A = 4
  val COL_A = 16
  val ROW_B = 8
  val COL_B = 16
}

class STA_VDBB(val w: Int = 32, val row_A: Int = BLOCK_SIZE_VDBB.ROW_A, val col_A: Int = BLOCK_SIZE_VDBB.COL_A,
               val row_B: Int = BLOCK_SIZE_VDBB.ROW_B, val col_B: Int = BLOCK_SIZE_VDBB.COL_B) extends Module {
  val io = IO(new Bundle {
    val in_A = Input(Vec(row_A, Vec(col_A, SInt(w.W))))
    val in_B = Input(Vec(row_B, Vec(col_B, SInt(w.W))))
    val out_C = Output(Vec(row_A, Vec(row_B, SInt(w.W))))
  })

  //Define modules
  /*
  0 1
  2 3

  0 1 2 3
  4 5 6 7
   */

  val taggers = Vec(Seq.fill(4)(Module(new tag).io))
  val muxs = Vec(Seq.fill(4)(Module(new MUX8).io))
  //  val S8DP1s = Vec(Seq.fill(row_A)(Seq.fill(row_B)(Module(new S8DP1).io)))

  val s8DP1s_1 = Vec(Seq.fill(row_B)(Module(new S8DP1).io))
  val s8DP1s_2 = Vec(Seq.fill(row_B)(Module(new S8DP1).io))
  val s8DP1s_3 = Vec(Seq.fill(row_B)(Module(new S8DP1).io))
  val s8DP1s_4 = Vec(Seq.fill(row_B)(Module(new S8DP1).io))

  val S8DP1s = Vector(s8DP1s_1, s8DP1s_2, s8DP1s_3, s8DP1s_4)

  //  val S8DP1s = Vec(

  //  val S8DP1s = Seq(1,2,3,4)


  //Define Regs (FFs)
  val ffb1 = Module(new FF4)
  val ffb2 = Module(new FF4)

  val ffa1 = Module(new FF8)
  val ff00 = Module(new FF8)

  val ff01 = Module(new FF4)
  val ff10 = Module(new FF4)

  val ffa2 = Module(new FF8)
  val ff11 = Module(new FF8)

  //  val zero = RegInit()
  val zero = RegInit(Vec(Seq.fill(row_B)(Vec(Seq.fill(col_B)(false.B)))))

  /*
  Now we come to the loops
   */

  //Loop1
  //Data fetch
  for (i <- 0 until 7) {
    //input A
    for (j <- 0 until 1) {
      ffa1.io.in_data(j)(i) := io.in_A(j)(i)
      ff00.io.in_data(j)(i) := io.in_A(j)(i)

    }

    //input and process B
    for (j <- 0 until 3) {
      ffb1.io.in_data(j)(i) := io.in_B(j)(i)
      ff01.io.in_data(j)(i) := io.in_B(j)(i)

    }
  }

  //Data tag for B
  for (i <- 0 until 3) {
    taggers(i).in_data := ffb1.io.out_data(i)
    ffb1.io.in_tag(i) := taggers(i).out_tag
    ff01.io.in_tag(i) := taggers(i).out_tag
  }

  //Begin to compute
  /*
  + -
  - -
  */
  for (k <- 0 until 7) {
    if (ffb1.io.out_tag != zero) {
      for (i <- 0 until 3) { //column
        muxs(i).int_in := ffb1.io.out_data(i)
        muxs(i).tag := ffb1.io.out_tag(i)
        for (j <- 0 until 1) { //row
          S8DP1s(j)(i).int_in_A := ffa1.io.out_data(j)
          S8DP1s(j)(i).int_in_B := muxs(i).choice
          S8DP1s(j)(i).tag := ffb1.io.out_tag(i)

          ffb1.io.in_tag(i) := S8DP1s(j)(i).out_tag
        }
      }
    }
  }


  //Loop2
  //Data fetch
  for (i <- 0 until 7) {
    //input A
    /*
    - -
    + -
    */
    for (j <- 0 until 1) {
      ffa2.io.in_data(j)(i) := io.in_A(j + 2)(i)
      ff11.io.in_data(j)(i) := io.in_A(j + 2)(i)
    }

    //input and process B
    /*
    - +
    - -
    */
    for (j <- 0 until 3) {
      ffb2.io.in_data(j)(i) := io.in_B(j + 4)(i)
      ff10.io.in_data(j)(i) := io.in_B(j + 4)(i)
    }
  }

  //Data tag for B
  for (i <- 0 until 3) {
    taggers(i).in_data := ffb1.io.out_data(i)
    ffb2.io.in_tag(i) := taggers(i).out_tag
    ff10.io.in_tag(i) := taggers(i).out_tag
  }

  //Begin to compute
  /*
  - 2
  3 4
  */
  for (k <- 0 until 7) {
    if (ffb1.io.out_tag != zero) {
      //2
      for (i <- 0 until 3) { //column
        muxs(i).int_in := ffb2.io.out_data(i)
        muxs(i).tag := ffb2.io.out_tag(i)
        for (j <- 0 until 1) { //row
          S8DP1s(j)(i + 4).int_in_A := ff00.io.out_data(j)
          S8DP1s(j)(i + 4).int_in_B := muxs(i).choice
          S8DP1s(j)(i + 4).tag := ffb2.io.out_tag(i)

          ffb2.io.in_tag(i) := S8DP1s(j)(i + 4).out_tag
        }
      }

      //3
      for (i <- 0 until 3) { //column
        muxs(i).int_in := ff01.io.out_data(i)
        muxs(i).tag := ff01.io.out_tag(i)
        for (j <- 0 until 1) { //row
          S8DP1s(j + 2)(i).int_in_A := ff01.io.out_data(j)
          S8DP1s(j + 2)(i).int_in_B := muxs(i).choice
          S8DP1s(j + 2)(i).tag := ff01.io.out_tag(i)

          ff01.io.in_tag(i) := S8DP1s(j + 2)(i).out_tag
        }
      }

      //4
      for (i <- 0 until 3) { //column
        muxs(i).int_in := ff10.io.out_data(i)
        muxs(i).tag := ff10.io.out_tag(i)
        for (j <- 0 until 1) { //row
          S8DP1s(j + 2)(i + 4).int_in_A := ff11.io.out_data(j)
          S8DP1s(j + 2)(i + 4).int_in_B := muxs(i).choice
          S8DP1s(j + 2)(i + 4).tag := ff10.io.out_tag(i)

          ffb1.io.in_tag(i) := S8DP1s(j)(i + 4).out_tag
        }
      }
    }
  }


  //Loop3
  //Data fetch
  for (i <- 0 until 7) {
    //input A
    /*
    - +
    - -
    */
    for (j <- 0 until 1) {
      ffa1.io.in_data(j)(i) := io.in_A(j)(i + 8)
      ff00.io.in_data(j)(i) := io.in_A(j)(i + 8)
    }

    //input and process B
    /*
    - -
    + -
    */
    for (j <- 0 until 3) {
      ffb1.io.in_data(j)(i) := io.in_B(j)(i + 8)
      ff01.io.in_data(j)(i) := io.in_B(j)(i + 8)
    }
  }

  //Data tag for B
  for (i <- 0 until 3) {
    taggers(i).in_data := ffb1.io.out_data(i)
    ffb1.io.in_tag(i) := taggers(i).out_tag
    ff01.io.in_tag(i) := taggers(i).out_tag
  }

  //Begin to compute
  /*
  + -
  - -
  */
  for (k <- 0 until 7) {
    if (ffb1.io.out_tag != zero) {
      for (i <- 0 until 3) { //column
        muxs(i).int_in := ffb1.io.out_data(i)
        muxs(i).tag := ffb1.io.out_tag(i)
        for (j <- 0 until 1) { //row
          S8DP1s(j)(i).int_in_A := ffa1.io.out_data(j)
          S8DP1s(j)(i).int_in_B := muxs(i).choice
          S8DP1s(j)(i).tag := ffb1.io.out_tag(i)

          ffb1.io.in_tag(i) := S8DP1s(j)(i).out_tag
        }
      }
    }
  }

  //Loop4
  //Data fetch
  for (i <- 0 until 7) {
    //input A
    /*
    - -
    - +
    */
    for (j <- 0 until 1) {
      ffa2.io.in_data(j)(i) := io.in_A(j + 2)(i + 8)
      ff11.io.in_data(j)(i) := io.in_A(j + 2)(i + 8)
    }

    //input and process B
    /*
    - -
    - +
    */
    for (j <- 0 until 3) {
      ffb2.io.in_data(j)(i) := io.in_B(j + 4)(i + 8)
      ff10.io.in_data(j)(i) := io.in_B(j + 4)(i + 8)
    }
  }

  //Data tag for B
  for (i <- 0 until 3) {
    taggers(i).in_data := ffb1.io.out_data(i)
    ffb2.io.in_tag(i) := taggers(i).out_tag
    ff10.io.in_tag(i) := taggers(i).out_tag
  }

  //Begin to compute
  /*
  - 2
  3 4
  */
  for (k <- 0 until 7) {
    if (ffb1.io.out_tag != zero) {
      //2
      for (i <- 0 until 3) { //column
        muxs(i).int_in := ffb2.io.out_data(i)
        muxs(i).tag := ffb2.io.out_tag(i)
        for (j <- 0 until 1) { //row
          S8DP1s(j)(i + 4).int_in_A := ff00.io.out_data(j)
          S8DP1s(j)(i + 4).int_in_B := muxs(i).choice
          S8DP1s(j)(i + 4).tag := ffb2.io.out_tag(i)

          ffb2.io.in_tag(i) := S8DP1s(j)(i + 4).out_tag
        }
      }

      //3
      for (i <- 0 until 3) { //column
        muxs(i).int_in := ff01.io.out_data(i)
        muxs(i).tag := ff01.io.out_tag(i)
        for (j <- 0 until 1) { //row
          S8DP1s(j + 2)(i).int_in_A := ff01.io.out_data(j)
          S8DP1s(j + 2)(i).int_in_B := muxs(i).choice
          S8DP1s(j + 2)(i).tag := ff01.io.out_tag(i)

          ff01.io.in_tag(i) := S8DP1s(j + 2)(i).out_tag
        }
      }

      //4
      for (i <- 0 until 3) { //column
        muxs(i).int_in := ff10.io.out_data(i)
        muxs(i).tag := ff10.io.out_tag(i)
        for (j <- 0 until 1) { //row
          S8DP1s(j + 2)(i + 4).int_in_A := ff11.io.out_data(j)
          S8DP1s(j + 2)(i + 4).int_in_B := muxs(i).choice
          S8DP1s(j + 2)(i + 4).tag := ff10.io.out_tag(i)

          ffb1.io.in_tag(i) := S8DP1s(j)(i + 4).out_tag
        }
      }
    }
  }

  //output
  val out_result = RegInit(Vec(Seq.fill(row_A)(Vec.fill(row_B)(0.S(32.W)))))

  for (i <- 0 until 7) {
    for (j <- 0 until 3) {
      out_result(j)(i) := S8DP1s(j)(i).result
    }
  }

  io.out_C := out_result

}


object Main {
  def main(args: Array[String]): Unit = {
    println("STA-VDBB main function")
    chisel3.Driver.execute(args, () => new STA_VDBB())
  }
}

//run --target-dir generated --compiler verilog
