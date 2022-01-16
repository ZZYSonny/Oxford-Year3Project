## Simpler ones
### ABC
(semaphore is way simpler than monitor)
For this object, FDR takes a long time to evaluate the spec process and build a graph for it, because the linearizer makes no assumption on Sync event. So is it acceptable to merge the spec0 process (Sync->Spec0) into the individual linearizer, or the RelatedSync like in test1?

Time to check assert 1:
test0: ~20 mins.
test1: ~1min.

I am worried that there will be much more states in the counter version.

### Barrier:
I am not sure if this is a bug in FDR. In correct0.csp, FDR tells me the variable count reaches values like -1 and N+1, and no useful debugging information is given. In correct.csp, I added `if x==N then div else` trying to catch this error. But the resulting system never diverges and seems to work fine.

`sync` function in `barrier.scala` takes no parameter and returns unit. So this means `Sync` event will simply take a permutation of the indentity of involved thread. Is it OK to further simplify `Sync` event to take nothing. (It feels like moving the Spec process down) 
Or is there a kind of barrier s.t. $N$ threads are involved, but `sync` function exits as soon as $M<N$ threads calls 

### BarrierCounter:
I think the `SeqNumber` value should be circular ($mod\ M$) instead of incrementing forever. Otherwise there will be unlimited number of states for the variable object.

Similar `Sync` channel definition problem. But I feel for this object it makes more sense not to simplify Sync. 

## Harder ones
### Terminating Queue:
The spec0 process will have unlimimited states. Maybe the queue should have a maximum capacity. If the queue is full, a process trying to perform a enqueue operation should only do so after another process performs a dequeue operation.
