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
    val in_kernel = Input(Vec(CONV_2D_SIZE.KERNEL_SIZE, Vec(CONV_2D_SIZE.KERNEL_SIZE, SInt(32.W))))
    val in_cal = Input(Bool())

    val out_matrix = Output(Vec(CONV_2D_SIZE.INPUT_MATRIX_SIZE, Vec(CONV_2D_SIZE.INPUT_MATRIX_SIZE, SInt(32.W))))
    val out_kernel = Output(Vec(CONV_2D_SIZE.KERNEL_SIZE, Vec(CONV_2D_SIZE.KERNEL_SIZE, SInt(32.W))))
    val out_tag = Output(Vec(4, Vec(8, Bool())))

    val out_result = Output(Vec(CONV_2D_SIZE.INPUT_MATRIX_SIZE, Vec(CONV_2D_SIZE.INPUT_MATRIX_SIZE, SInt(32.W))))
    val out_cal = Output(Bool())
  })
  val kernel_vector_size = CONV_2D_SIZE.KERNEL_SIZE * CONV_2D_SIZE.KERNEL_SIZE
  val kernel_vector = Vec(kernel_vector_size, SInt(32.W))
  //construct kernel 1D array (reversed)

  for (i <- 0 until (CONV_2D_SIZE.KERNEL_SIZE - 1)) {
    for (j <- 0 until (CONV_2D_SIZE.KERNEL_SIZE - 1)) {
      kernel_vector(((CONV_2D_SIZE.KERNEL_SIZE - 1 - i) * CONV_2D_SIZE.KERNEL_SIZE) + (CONV_2D_SIZE.KERNEL_SIZE - 1 - j)) := io.in_kernel(i)(j)
    }
  }




  //zero padding for data matrix
  val input_matrix_padding_size = CONV_2D_SIZE.INPUT_MATRIX_SIZE+2
  val input_matrix_padding = RegInit(Vec(Seq.fill(CONV_2D_SIZE.INPUT_MATRIX_SIZE+2)(Vec(Seq.fill(CONV_2D_SIZE.INPUT_MATRIX_SIZE+2)(0.S(32.W))))))
    for (i <- 1 until (CONV_2D_SIZE.INPUT_MATRIX_SIZE)) {
      for (j <- 1 until (CONV_2D_SIZE.INPUT_MATRIX_SIZE)) {
        input_matrix_padding(i)(j) := io.in_matrix(i-1)(j-1)
      }
    }

  //img2col

  val conv_col_matrix = RegInit(Vec(Seq.fill(CONV_2D_SIZE.INPUT_MATRIX_SIZE*CONV_2D_SIZE.INPUT_MATRIX_SIZE)(Vec(Seq.fill(CONV_2D_SIZE.KERNEL_SIZE)(0.S(32.W))))))
  for (i <- 0 until (CONV_2D_SIZE.INPUT_MATRIX_SIZE-1)) {
    for (j <- 0 until (CONV_2D_SIZE.INPUT_MATRIX_SIZE-1)) {
      conv_col_matrix(i*input_matrix_padding_size+j)(0) := input_matrix_padding(i-1)(j-1)
      conv_col_matrix(i*input_matrix_padding_size+j)(0) := input_matrix_padding(i-1)(j)
      conv_col_matrix(i*input_matrix_padding_size+j)(0) := input_matrix_padding(i-1)(j+1)
      conv_col_matrix(i*input_matrix_padding_size+j)(0) := input_matrix_padding(i)(j-1)
      conv_col_matrix(i*input_matrix_padding_size+j)(0) := input_matrix_padding(i)(j)
      conv_col_matrix(i*input_matrix_padding_size+j)(0) := input_matrix_padding(i)(j+1)
      conv_col_matrix(i*input_matrix_padding_size+j)(0) := input_matrix_padding(i+1)(j-1)
      conv_col_matrix(i*input_matrix_padding_size+j)(0) := input_matrix_padding(i+1)(j)
      conv_col_matrix(i*input_matrix_padding_size+j)(0) := input_matrix_padding(i+1)(j+1)

    }
  }



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


