import io.threadcso._
object Main {
  def main(args: Array[String]): Unit = {
    val lock = new Object();
    val a = proc{
      sleepms(100)
      lock.synchronized{
        lock.notifyAll();
        sleepms(1000)
      }
    }
    val b = proc{
      lock.synchronized{
        
        sleepms(200)
        println("b")
      }
    }
    val c = proc{
      sleepms(200)
      lock.synchronized{
        lock.wait();
        println("c")
      }
    }
    run(a||b||c)
  }
}