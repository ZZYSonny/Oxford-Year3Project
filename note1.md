- Fix ABC (does turn out to be a typo somewhere)
- Implemented ABCCounter
- ABC running time statistics
  - From the '?' tab, the compliation takes the most amount of time. Once compliation finishes, the BFS finishes very quickly.
  - The number of states of System1 (the simple one) does not differ too much
  - Number of states and 
- A few analysis
  - Replicated external choice is bad (I will change other files next week)
  - Merging Spec into linearizer reduces the number of transition.
  - Simplfing Sync does not reduce the number of transition. I think FDR is able to optimize unused transitions. But maybe this will help FDR run CSP code faster? (Also makes the code simpler)

- [Terminating Queue](https://github.com/ZZYSonny/Oxford-Year3Project/tree/main/csp/TerminatingQueue)
  - There is no faulty version. Maybe I will write one that returns random data on deque.
  - Capacity limited
    - I am simply setting the system to deadlock if the queue is full when enqueueing. 
  - A\*BC\*
    - Could you please check my definition of the internal A\*BC\* queue defitiniton? [queue.csp]([URL here](https://github.com/ZZYSonny/Oxford-Year3Project/blob/main/csp/TerminatingQueue/abc/queue.csp))
    - This definition allows dequeue 'A' when no data enqueues. Or maybe I should have the initial empty queue as one of the state? 
    - Similar for C.

- Plan for next week
  - Change external choices to ?
  - Start working on timeout
  - Focus a bit more on writing.


|proc|state|transition|description|Failure case runnable|
|-|-|-|-|-|
|test0 LinAll({TA,TB,TC})|132651|36819463|Original Spec Linearizer|No|
|test00 LinAll({TA,TB,TC})|6859|5188135|Original Spec Linearizer, ? only|No|
|test1 LinAll({TA,TB,TC})|211189|622741|Merge Spec into linearizer|No|
|test11 LinAll({TA,TB,TC})|3163|8743|Merge Spec into linearizer, ? only|Yes|
|test2 LinAll({TA,TB,TC})|211189|622741|Merge Spec into linearizer, simplified sync event, general external choice|No|
|test3 LinAll({TA,TB,TC})|3163|8743|Merge Spec into linearizer, simplified sync event, ? only|Yes|
|test4 LinAll({TA,TB,TC})|211189|622741|Merge Spec into linearizer, simplified sync event, move external choice down to branch|No|
|test5 LinAll({TA,TB,TC})|3163|8743|Merge Spec into linearizer, simplified sync event, external choice right before variable|Yes|
|System1 (run with 3 thread linearizers above)|261|534||
|System2 (not to run with 3 thread linearizers above)|4072|11695||


```
A*B should accept C
non deterministic when dequeueing
Regulator process to system, insist input is A*BC*. 

Enqueue not in form A*BC*, CHAOS behavior.

Check queue does not lose values.

hold B -> cannot refuse to dequeue

Writing, 
- Common object, share var, 
- Simpler Object with semaphore/monitor impl 
  - what object do
  - Describe impl system, and spec
  - time to do the test
```