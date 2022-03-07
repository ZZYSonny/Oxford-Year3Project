- TerminatingQueue: Use `err!me` instead of blocking.

I modified [another version](https://github.com/ZZYSonny/Oxford-Year3Project/blob/main/csp/TerminatingQueue/correct_err.csp) of terminating queue, and used it in
[test](https://github.com/ZZYSonny/Oxford-Year3Project/blob/main/csp/TerminatingQueue/capacity/test1.csp). I Checked that err event does not occur, and \[T= works as before.
- I think the renaming trick does not work in FDR. 

In the below example, I can only see (a->c) or (b->d), but not (a->c->b->d).
```csp
channel a,b,c,d
A = a -> STOP
B = A[[a<-a,a<-b]]
C = a -> c -> STOP
D = b -> d -> STOP
E = (B [|{a}|] C) [|{b}|] D
```
- Feature: Implemented [failure test](https://github.com/ZZYSonny/Oxford-Year3Project/blob/main/csp/filterchan/test_f.csp) (by counting #Call, #Return) for FilterChan. Running the first test gives a counterexample for two thread
```
T1 calls send(A)
    wait at line 25
T2 calls receive(isA)
    finish the whole process
T2 calls send(A)
    wait at line 25

The system can do nothing, but the spec says it should not refuse `Return.T2.SendReturn`.
```
The same system (System9 in [test](https://github.com/ZZYSonny/Oxford-Year3Project/blob/main/csp/filterchan/test.csp)) can be checked with the old deadlock-free test.
I think this feature is not captured in my previous work because in the general test case (where every thread can do any operation), I always assume these threads run forever. But this error case only occurs when the threads make finite calls. Now I have a new test case (System10 in [test.csp](https://github.com/ZZYSonny/Oxford-Year3Project/blob/main/csp/filterchan/test.csp)), where two threads can repetitively send or receive, or run nop forever. Is `Spec2Thread [T=` Test redundant here?

- TimeoutChannel

I now have a working time-limited version and a general linearizer. In the [linearizer](https://github.com/ZZYSonny/Oxford-Year3Project/blob/main/csp/TimeoutChannel/cap/lin.csp), I have the linearizer in the usual way, running in parallel with SpecThread, which ensures if a call success, the sync event is n-tock within the call event.

But the [system implementation](https://github.com/ZZYSonny/Oxford-Year3Project/blob/main/csp/TimeoutChannel/cap/correct.csp) is a bit faulty. I have a clock event, which releases tock events and increments the time counter in two events. The system implementation uses the time counter, and the spec process uses the tock event. In the [T= test, system implementation timeouts, but the spec process never sees the tock event. I think I will straightly use the variable way next time.