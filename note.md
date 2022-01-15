ABC:
(semaphore is way simpler than monitor)
For this object, FDR takes a long time to evaluate the spec process and build a graph for it, because the linearizer makes no assumption on Sync event. So is it acceptable to merge the spec0 process (Sync->Spec0) into the individual linearizer, or the RelatedSync like in test1?

Time to check assert 1:
test0: ~20 mins.
test1: ~1min.

Terminating Queue:
The spec0 process will have unlimimited states. Maybe the queue should have a maximum capacity. If the queue is full, a process trying to perform a enqueue operation should only do so after another process performs a dequeue operation.
This also creates another terminating condition: If all threads are trying to enqueue when the queue is full.