I think the renaming trick does not work in FDR. In the below example, I can only see (a->c) or (b->d), but not (a->c->b->d).
```csp
channel a,b,c,d
A = a -> STOP
B = A[[a<-a,a<-b]]
C = a -> c -> STOP
D = b -> d -> STOP
E = (B [|{a}|] C) [|{b}|] D
```

Implemented failure test (by counting #Call, #Return) for filterchan. Running `` gives a counterexample for two thread
```
T1 calls send(A)
    wait at line 25
T2 calls receive(isA)
    finish whole process
T2 calls send(A)
    wait at line 25

System can do nothing but spec says it should be able to do `Return.T2.SendReturn`.
```
Same system can be checked with the old deadlock free test.
I think this is not captured in my previous work because in the general test case (where every thread can do any operation), I always assume these threads run forever. But this error case only occurs when threads make finite calls. Now I have a new test case (System10 in test.csp), where two threads can repetitively send or receive, or running nop forever. Is `Spec2Thread [T=` Test redundant here?

Failure check for TerminatingQueue seems 