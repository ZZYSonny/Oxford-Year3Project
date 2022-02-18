- Implemented the [three spec for ABC](https://github.com/ZZYSonny/Oxford-Year3Project/blob/main/csp/TerminatingQueue/abc/lin.csp). I also have a spec (spec4) when the queue shuts down. 
- For the [internal queue of the implementation](https://github.com/ZZYSonny/Oxford-Year3Project/blob/main/csp/TerminatingQueue/abc/queue.csp), I am using two specific empty queue state. The advantage is that it makes qEmpty deterministic and simplifies my concrete implementation.
  - Otherwise, in the last step of abc's dequeue(), the qDequeue needs to be consistent with earlier qEmpty. 
- For [regular trace refinement checking](https://github.com/ZZYSonny/Oxford-Year3Project/blob/main/csp/TerminatingQueue/abc/test.csp), I have an input regulator, which ensures that parameter of Enqueue Call must be `A*BC*`. But this weakens the test case a bit. With the unrestricted queue, some (N-1) threads can call enqueue(C), but enqueue C into the actual queue only after B is enqueued. (test)
  - So I am strengthening the system in this way
    - Before enqueue(B), there can be at most N-1 enqueue(C).
    - After enqueue(B), if there are still free thread, these thread can still do enqueue(A). But this blocks any other threads doing enqueue(B) and enqueue(C). The input regulator can also choose to stop enqueue(A), so threads doing enqueue(B) and enqueue(C) are released.
    - Enqueue(C)* 
- When I am testing deadlock free of the ABC object, I do not use the input regulator. Instead, the test uses a [modified implementation code](https://github.com/ZZYSonny/Oxford-Year3Project/blob/main/csp/TerminatingQueue/abc/correct1.csp). After detecting that input is not in the form of `A*BC*`, the thread explicitly diverges without releasing the monitor. So other threads cannot do any other events. Similar for the shutting case. (Is there a better way? When there is [an extra qErr state for queue](https://github.com/ZZYSonny/Oxford-Year3Project/blob/main/csp/TerminatingQueue/abc/queue1.csp), the system also explicitly diverge with other set of events.)

For the essay, When I analyze why the original linearizer is slow , I did not mention the number of states, because I am not too sure about number of states needed for parallel. If an individual linearizer uses N states, then the parallel of two individual linearizers will use N^2 states before normalizing? 


```
Call Call Return.Fail Return.Fail 
Split DequeueReturn.Fail to another event

enqLP.me.qEnqueue(q,x).x, then renaming to setValue

Spec then could look at enqLP, (get rid of calling and returning)

spec look at enqLP, deqLP
system use setValue, but renamed enqLP, deqLP
Input regulator look at enqLP, deqLP

Spec that checks the system deadlock only when false input.

essay:
Thread -> process, use thread only for worker
getChan: consistent, inline CSP -> small
Put whole code. +Figure -> long block
One \section for every object

one instance of syncA, ... that should synchronize
Say where code come from.

put down value -> store value

P4 ,Because

Explicit 
plurals, 
singular cnt noun, the a

Link to previous paragraph. "2.1.2" we will see a faster version.
latex ignore block for wordcount

P3. Last paragraph before 2.1.1. Separate last paragraph to a new \subsection

P3 2.1.1. Explain thread.
system should refine the specification.
spec is refined by

2.1.3. Merge with earlier ? sentence.
Error in more detail. Give A trace. Explain how it is an error. 
```