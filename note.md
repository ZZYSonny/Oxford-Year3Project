ABC:
(semaphore is way simpler than monitor)
For this object, FDR takes a long time to evaluate the spec process and build a graph for it, because the linearizer makes no assumption on Sync event. So is it acceptable to merge the spec0 process (Sync->Spec0) into the individual linearizer, or the RelatedSync like in test1?

Time to check assert 1:
test0: ~20 mins.
test1: ~1min.

I am worried that there will be much more states in the counter version of FDR.

Barrier:
I am not sure if this is a bug in FDR. In correct0.csp, FDR tells me the variable count reaches values like -1 and N+1, and no useful debugging information is given. In correct.csp, I added `if x==N then div else` trying to catch this error. But the resulting system never diverges and seems to work fine.

BarrierCounter:


Terminating Queue:
The spec0 process will have unlimimited states. Maybe the queue should have a maximum capacity. If the queue is full, a process trying to perform a enqueue operation should only do so after another process performs a dequeue operation.
This also creates another terminating condition: If all threads are trying to enqueue when the queue is full.