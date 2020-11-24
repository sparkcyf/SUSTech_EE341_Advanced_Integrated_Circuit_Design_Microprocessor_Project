//import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec}
//import chisel3._
//
//class STA_VDBB_Tests(c: STA_VDBB) extends PeekPokeTester(c) {
//  //A 4*16
//  val matrices_A = (Vec(4, Vec(1.S ,2.S ,3.S ,4.S ,5.S ,6.S ,7.S ,8.S,
//    1.S ,2.S ,3.S ,4.S ,5.S ,6.S ,7.S ,8.S)))
//  //B 8*16
//  val matrices_B = (Vec(8,
//    Vec(0.S, 0.S, 1.S, 0.S,   0.S, 0.S, 1.S, 0.S,   0.S, 0.S, 1.S, 0.S,   0.S, 0.S, 1.S, 0.S)))
//
//  //expected result 4*8
//  val result = Vec(4, Vec(8, 0.S))
//  for (i <- 0 until 3) {
//    for (j <- 0 until 7) {
//      for (k <- 0 until 15) {
//        result(i)(j) := result(i)(j) + matrices_A(i)(k) * matrices_B(j)(k)
//      }
//    }
//  }
//
//  for (i <- 0 until 4) {
//    for (j <- 0 until 15){
//      poke(c.io.in_A(i)(j), matrices_A(i)(j))
//    }
//  }
//
//  for (i <- 0 until 8) {
//    for (j <- 0 until 15){
//      poke(c.io.in_B(i)(j), matrices_B(i)(j))
//    }
//  }
//
//  step(1)
//
//  for (i <- 0 until 3) {
//    for (j <- 0 until 7) {
//      expect(c.io.out_C(i)(j), result(i)(j))
//    }
//  }
//
//
//}
//
//class STA_VDBB_Tester extends ChiselFlatSpec {
//  behavior of "STA_VDBB"
//  backends foreach {backend =>
//    it should s"correctly add randomly generated numbers and show carry in $backend" in {
//      Driver(() => new STA_VDBB, backend)((c) => new STA_VDBB_Tests(c)) should be (true)
//    }
//  }
//}
