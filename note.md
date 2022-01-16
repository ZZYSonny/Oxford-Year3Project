### Improvement with last meeting
- Used alphabetised parallel for linearizer.
- In Exchanger, checked the system never deadlocks except for spurious wakeup.
### Simpler ones
#### ABC
(semaphore is way simpler than monitor)
For this object, FDR takes a long time to evaluate the spec process. Probably because a function returns a pair instead of a single value. 

For this counterless version, it is easy and possible to move the spec process into the linearizer. Whenever the linearizer uses the Sync event, it obeys the spec process. (But is it acceptable?)

The first assertion involves three threads, but this will not reveal the bug. test0 (which do not write spec into linearizer) uses more time than test1 (which write spec into linearizer).

The second assertion involves four threads, but it is taking forever to evaluate. I think it will be even worse for the counter version. 

#### Barrier:
I am not sure if this is a bug in FDR. In correct0.csp, FDR tells me the variable count reaches values like -1 and N+1, and no useful debugging information is given. In correct.csp, I added `if x==N then div else` trying to catch this error. But the resulting system never diverges and seems to work fine.

`sync` function in `barrier.scala` takes no parameter and returns unit. So this means `Sync` event will simply take a permutation of the indentity of involved thread. Is it OK to further simplify `Sync` event to take nothing. (It feels like moving the Spec process down) 
Or is there a kind of barrier s.t. $N$ threads are involved, but `sync` function exits as soon as $M<N$ threads calls 

#### BarrierCounter:
I think the `SeqNumber` value should be circular ($mod\ M$) instead of incrementing forever. Otherwise there will be unlimited number of states for the variable object.

Similar `Sync` channel definition problem. But I feel for this object it makes more sense not to simplify Sync. 

### Harder ones
#### Terminating Queue:
The spec0 process will have unlimimited states. Maybe the queue should have a maximum capacity. If the queue is full, a process trying to perform a enqueue operation should only do so after another process performs a dequeue operation.

#### Timeout Channel
I think `nanoTime` will be implemented as a modular and self-incrementing variable. (Or maybe terminates after the time reaches the maximum value.)

If the timeout duration is fixed for send and receive, is it enough to check the Call and Return event for the following properties.
- If a send operation timeouts, then all earlier send operations should also return timeout.
- Similar for receive.