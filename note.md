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
```
Discuss in report test0 and test1
test0 is the normal way of defining but test1 is faster

Further optimize:
    External choice
    have sync.t1.t2.t3.a.b.c -> Actual Sync

Test: Where taking long time (compilation/checking)
{ e | e <- {| Sync |}, ok(e) } - alphabet size
deadlock free in linearizer - evaluating system
refinement by linearizer by linearizer - normalizing?


Use the the ? Window in FDR
```
#### Barrier:
I am not sure if this is a bug in FDR. In correct0.csp, FDR tells me the variable count reaches values like -1 and N+1, and no useful debugging information is given. In correct.csp, I added `if x==N then div else` trying to catch this error. But the resulting system never diverges and seems to work fine.

```
Compiled independently
?x could be N,
So if ... div works as assertion
```

`sync` function in `barrier.scala` takes no parameter and returns unit. So this means `Sync` event will simply take a permutation of the indentity of involved thread. Is it OK to further simplify `Sync` event to take nothing. (It feels like moving the Spec process down) 
Or is there a kind of barrier s.t. $N$ threads are involved, but `sync` function exits as soon as $M<N$ threads calls 
```
Use set rather than sequence for permutationi
```
#### BarrierCounter:
I think the `SeqNumber` value should be circular ($mod\ M$) instead of incrementing forever. Otherwise there will be unlimited number of states for the variable object.

```
mod
note in report: finite state
works in circular case -> work in normal case
```

Similar `Sync` channel definition problem. But I feel for this object it makes more sense not to simplify Sync. 

### Harder ones
#### Terminating Queue:
The spec0 process will have unlimimited states. Maybe the queue should have a maximum capacity. If the queue is full, a process trying to perform a enqueue operation should only do so after another process performs a dequeue operation.
```
Queue modular 

#1
Maximum capacity
Spec look at Sync

#2 Trick
Queue A*BC*
A*BC* A*BC*

finite 
restrict to parse only A*BC*

Check B for 
Missing: B
Duplicate: B
Permu

A: More A or B
B: C
```
#### Timeout Channel
I think `nanoTime` will be implemented as a modular and self-incrementing variable. (Or maybe terminates after the time reaches the maximum value.)

If the timeout duration is fixed for send and receive, is it enough to check the Call and Return event for the following properties.
- If a send operation timeouts, then all earlier send operations should also return timeout.
- Similar for receive.
```
Deadlock free

Tock event
May need lazy abstraction
If it fails, there is at least $duration$ tock events
Bill book: Modelling time chapter
```