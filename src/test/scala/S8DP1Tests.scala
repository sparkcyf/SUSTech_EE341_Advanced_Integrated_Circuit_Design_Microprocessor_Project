import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec}
import chisel3._

class S8DP1Tests(c: S8DP1) extends PeekPokeTester(c) {
  for (t <- 0 until 4) {
    val a   = rnd.nextInt(2)
    val b    = rnd.nextInt(2)
    val cin  = rnd.nextInt(2)
    val res  = a + b + cin
    val sum  = res & 1
    val cout = (res >> 1) & 1
    for(i<-0 until 8){
      poke(c.io.int_in_A(i),10)
    }
    poke(c.io.int_in_B,10)
    poke(c.io.tag(0),true)
    poke(c.io.tag(1),true)
    poke(c.io.tag(2),true)
    poke(c.io.tag(3),true)
    poke(c.io.tag(4),true)
    poke(c.io.tag(5),true)
    poke(c.io.tag(6),true)
    poke(c.io.tag(7),true)

    //expect(c.acc, 100)
    expect(c.io.out_tag(0), false)
    expect(c.io.result, 100)
    //expect(c.acc, 0)
    expect(c.io.out_tag(1),false)
    expect(c.io.out_tag(2),false)
    expect(c.io.out_tag(3),false)
    expect(c.io.out_tag(4),false)
    expect(c.io.out_tag(5),false)
    expect(c.io.out_tag(6),false)
    expect(c.io.out_tag(7),false)


  }
}

class S8DP1Tester extends ChiselFlatSpec {
  behavior of "S8DP1"
  backends foreach {backend =>
    it should s"correctly add randomly generated numbers and show carry in $backend" in {
      Driver(() => new S8DP1, backend)((c) => new S8DP1Tests(c)) should be (true)
    }
  }
}