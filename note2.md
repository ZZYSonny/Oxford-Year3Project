- Implemented the three spec for ABC
- For the internal queue of the implementation, I am using two specific queue empty state. The advantage is that it makes qEmpty deterministic and simplfies my concrete implementation.
- At first I am having a input regulator, restricting that parameter of Enqueue Call must be `A*BC*`. But this weakens the test case a bit. With the unrestricted queue, some (N-1) threads can call enqueue(C), but enqueue C into the actual queue only after B is enqueued. (test)
  - So I am strengthening the system in this way
    - Before enqueue(B), there can be at most N-1 enqueue(C).
    - After enqueue(B), if there are still free thread, these thread can still do enqueue(A). But this blocks any other threads doing enqueue(B) and enqueue(C). The input regulator can also choose to stop enqueue(A), so threads doing enqueue(B) and enqueue(C) are released.
    - Enqueue(C)* 
- When I am testing deadlock free of the ABC object, I did not use the input regulator. Instead, the test uses a modified implementation code. After detecting that input is not in the form of `A*BC*`, the thread explicitly diverges without releasing the monitor. So other threads cannot do anything. Similar for the shutting case. (I think it is not a nice way to do this.)

