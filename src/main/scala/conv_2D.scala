import chisel3._
import chisel3.util._
import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}

object CONV_2D_SIZE {
  val INPUT_MATRIX_SIZE = 8
  val KERNEL_SIZE = 3
}

class conv_2D extends Module {
  val io = IO(new Bundle {
    val in_matrix = Input(Vec(CONV_2D_SIZE.INPUT_MATRIX_SIZE, Vec(CONV_2D_SIZE.INPUT_MATRIX_SIZE, SInt(32.W))))
    //val in_kernel = Input(Vec(CONV_2D_SIZE.KERNEL_SIZE, Vec(CONV_2D_SIZE.KERNEL_SIZE, SInt(32.W))))
    val in_cal = Input(Bool())

    //val out_matrix = Output(Vec(CONV_2D_SIZE.INPUT_MATRIX_SIZE, Vec(CONV_2D_SIZE.INPUT_MATRIX_SIZE, SInt(32.W))))
    //val out_kernel = Output(Vec(CONV_2D_SIZE.KERNEL_SIZE, Vec(CONV_2D_SIZE.KERNEL_SIZE, SInt(32.W))))

    //val out_result = Output(Vec((CONV_2D_SIZE.INPUT_MATRIX_SIZE*CONV_2D_SIZE.INPUT_MATRIX_SIZE), Vec((CONV_2D_SIZE.KERNEL_SIZE*CONV_2D_SIZE.KERNEL_SIZE), SInt(32.W))))
    val out_result = Output(Vec((CONV_2D_SIZE.INPUT_MATRIX_SIZE*CONV_2D_SIZE.INPUT_MATRIX_SIZE), Vec((CONV_2D_SIZE.KERNEL_SIZE*CONV_2D_SIZE.KERNEL_SIZE), SInt(32.W))))
    //val out_cal = Output(Bool())
  })



//  //construct kernel 1D array (reversed)
//  val kernel_vector_size = CONV_2D_SIZE.KERNEL_SIZE * CONV_2D_SIZE.KERNEL_SIZE
//  val kernel_vector = Vec(kernel_vector_size, SInt(32.W))
//
//  for (i <- 0 until (CONV_2D_SIZE.KERNEL_SIZE - 1)) {
//    for (j <- 0 until (CONV_2D_SIZE.KERNEL_SIZE - 1)) {
//      kernel_vector(((CONV_2D_SIZE.KERNEL_SIZE - 1 - i) * CONV_2D_SIZE.KERNEL_SIZE) + (CONV_2D_SIZE.KERNEL_SIZE - 1 - j)) := io.in_kernel(i)(j)
//    }
//  }




  //zero padding for data matrix
  val input_matrix_padding_size = CONV_2D_SIZE.INPUT_MATRIX_SIZE+2
  val input_matrix_padding = RegInit(Vec(Seq.fill(CONV_2D_SIZE.INPUT_MATRIX_SIZE+2)(Vec(Seq.fill(CONV_2D_SIZE.INPUT_MATRIX_SIZE+2)(0.S(32.W))))))
    for (i <- 1 until (CONV_2D_SIZE.INPUT_MATRIX_SIZE)) {
      for (j <- 1 until (CONV_2D_SIZE.INPUT_MATRIX_SIZE)) {
        input_matrix_padding(i)(j) := io.in_matrix(i-1)(j-1)
      }
    }

  //img2col

  /**
   * e.g.
   * if:
   * CONV_2D_SIZE.INPUT_MATRIX_SIZE = 10
   * input_matrix_padding_size = 10+2 = 12
   * i (0:9) 10x
   * j (0:9) 10x
   * conv_col_matrix: (10*10)*9
   * if we start with i = 0, j = 0
   * corresponding to row 0 , col 0:8 of conv_col_matrix
   * we first start from (1,1) in input_matrix_padding
   * corresponding to (0,0) in original matrix
   * seq (0,0) (0,1) (0,2) (1,0) (1,1) (1,2) (2,0) (2,1) (2,1)
   * center: input_matrix_padding(i+1)(j+1) (while i = 0, j = 0)
   */


  val conv_col_matrix = RegInit(Vec(Seq.fill(CONV_2D_SIZE.INPUT_MATRIX_SIZE*CONV_2D_SIZE.INPUT_MATRIX_SIZE)(Vec(Seq.fill(CONV_2D_SIZE.KERNEL_SIZE*CONV_2D_SIZE.KERNEL_SIZE)(0.S(32.W))))))
  for (i <- 0 until (CONV_2D_SIZE.INPUT_MATRIX_SIZE-1)) {
    for (j <- 0 until (CONV_2D_SIZE.INPUT_MATRIX_SIZE-1)) {
      conv_col_matrix((i)*CONV_2D_SIZE.INPUT_MATRIX_SIZE+j)(0) := input_matrix_padding(i)(j)
      conv_col_matrix((i)*CONV_2D_SIZE.INPUT_MATRIX_SIZE+j)(1) := input_matrix_padding(i)(j+1)
      conv_col_matrix((i)*CONV_2D_SIZE.INPUT_MATRIX_SIZE+j)(2) := input_matrix_padding(i)(j+2)
      conv_col_matrix((i)*CONV_2D_SIZE.INPUT_MATRIX_SIZE+j)(3) := input_matrix_padding(i+1)(j)
      conv_col_matrix((i)*CONV_2D_SIZE.INPUT_MATRIX_SIZE+j)(4) := input_matrix_padding(i+1)(j+1)
      conv_col_matrix((i)*CONV_2D_SIZE.INPUT_MATRIX_SIZE+j)(5) := input_matrix_padding(i+1)(j+2)
      conv_col_matrix((i)*CONV_2D_SIZE.INPUT_MATRIX_SIZE+j)(6) := input_matrix_padding(i+2)(j)
      conv_col_matrix((i)*CONV_2D_SIZE.INPUT_MATRIX_SIZE+j)(7) := input_matrix_padding(i+2)(j+1)
      conv_col_matrix((i)*CONV_2D_SIZE.INPUT_MATRIX_SIZE+j)(8) := input_matrix_padding(i+2)(j+2)

    }
  }


  io.out_result := conv_col_matrix



//  //Zero-pad the filter matrix
//  val zero_pad_size = CONV_2D_SIZE.INPUT_MATRIX_SIZE + CONV_2D_SIZE.KERNEL_SIZE - 1
//  val zero_pad = RegInit(Vec(Seq.fill(zero_pad_size)(Vec(Seq.fill(zero_pad_size)(0.S(32.W))))))
//    for (i <- 0 until (CONV_2D_SIZE.KERNEL_SIZE-1)) {
//      for (j <- 0 until (CONV_2D_SIZE.KERNEL_SIZE-1)) {
//        zero_pad(zero_pad_size-i)(j) := io.in_kernel(i)(j)
//      }
//    }
//  /**
//  F_zero_padded:
//  [[0 0 0 0 0 0 0 0 0 0] R0
// [0 0 0 0 0 0 0 0 0 0]
// [0 0 0 0 0 0 0 0 0 0]
// [0 0 0 0 0 0 0 0 0 0]
// [0 0 0 0 0 0 0 0 0 0]
// [0 0 0 0 0 0 0 0 0 0]
// [0 0 0 0 0 0 0 0 0 0]
// [1 2 3 0 0 0 0 0 0 0]
// [4 5 6 0 0 0 0 0 0 0]
// [7 8 9 0 0 0 0 0 0 0]] R9
//   */
//
//  val toeplitz_matrix_section = RegInit(Vec(Seq.fill(zero_pad_size)(Vec(Seq.fill(zero_pad_size)(Vec(Seq.fill(CONV_2D_SIZE.INPUT_MATRIX_SIZE)(0.S(32.W))))))))
//  //e.g. (8+3-1)*(8+3-1)*8
//  for (i <- 0 until (zero_pad_size-1)) {
//    for (j <- 0 until (zero_pad_size-1)) {
//      for (k <- 0 until (CONV_2D_SIZE.INPUT_MATRIX_SIZE-1)) {
//        toeplitz_matrix_section(i)(j)(k) :=
//      }
//    }
//  }

}

//object Main {
//  def main(args: Array[String]): Unit = {
//    println("CONV2D for input image")
//    chisel3.Driver.execute(args, () => new conv_2D)
//  }
//}

//run --target-dir generated --compiler verilog
