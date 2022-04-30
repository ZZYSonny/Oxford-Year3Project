 package synchronisationObject

import io.threadcso._
import scala.collection.mutable.Queue

trait TerminatingQueueT[A]{ 
  /** Enqueue x.  */
  def enqueue(x: A): Unit

  /** Attempt to dequeue a value.  
    * @return None if the queue has been shut down, or it the queue is empty
    * and all threads are trying to dequeue. */
  def dequeue: Option[A] 

  /** Shut down this queue. */
  // def shutdown(): Unit
}

// ==================================================================

/** A partial queue that terminates if all worker threads are attempting to
  * dequeue, and the queue is empty.  This implementation uses a monitor
  * internally.
  * @param numWorkers the number of worker threads. */
class TerminatingQueue[A](numWorkers: Int) extends TerminatingQueueT[A]{
  /** The queue itself. */
  private val queue = new Queue[A]

  /** The number of threads currently waiting to perform a dequeue. */
  private var waiting = 0

  /** Has the queue been shut down? */
  private var done = false

  /** Enqueue x.  */
  def enqueue(x: A) = synchronized{ 
    if(!done){
      queue.enqueue(x)
      if(waiting > 0) notify()
    }
  }

  /** Attempt to dequeue a value.  
    * @return None if the queue has been shut down, or it the queue is empty
    * and all threads are trying to dequeue. */
  def dequeue: Option[A] = synchronized{
    if(!done && queue.isEmpty){
      if(waiting == numWorkers-1){  // System should terminate
        done = true; notifyAll() 
      }  
      else{
        waiting += 1
        while(queue.isEmpty && !done) wait()
        waiting -= 1
      }
    }
    if(done) None else Some(queue.dequeue)
  }

  /** Shut down this queue. */
  //private def shutdown() = synchronized{ done = true; notifyAll() }
}


