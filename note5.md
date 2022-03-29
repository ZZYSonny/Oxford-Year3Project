After rechecking using blocking, error event and DIV for error handling in Terminating Queue, I find all implementations have the same number of states and transitions, and the total checking time does not differ too much. Now I am using DIV for all other places.

I implemented "renaming trick" for TerminatingQueue ABCLP test [here](https://github.com/ZZYSonny/Oxford-Year3Project/blob/main/csp/TerminatingQueue/abclp/common.csp). I wrote enqLP and deqLP into the Var process because
- Var process is private in my variable module. So I have to create a new module.
- In FDR it seems only one set comprehension can be used. `setValue!emptyQueue` can only be renamed to a deqLP event. `setValue!fullqueue` can only be renamed to enqLP event. I cannot have both in one set comprehension.
- The renamed Var process may use deqLP event that does not match current value of the queue. For example
```
Var(<1,2,3>)= setValue!<2,3> / (renamed to) deqLP!T1!<4,2,3>!4!<2,3> -> Var({2,3})
```
//Not a problem with my usage because I always read the queue variable before writing to it.

I implemented the most generic test (which finds the feature in Filterchan) for [MenWomen](https://github.com/ZZYSonny/Oxford-Year3Project/blob/main/csp/MenWoman/test_chaos.csp) and [ABC](https://github.com/ZZYSonny/Oxford-Year3Project/blob/main/csp/ABC/test_chaos.csp). And I am using failure refinement test (for [MenWomen](https://github.com/ZZYSonny/Oxford-Year3Project/blob/main/csp/MenWoman/test_failure.csp) and [ABC](https://github.com/ZZYSonny/Oxford-Year3Project/blob/main/csp/ABC/test_failure.csp)) to check the system deadlock only when all processes do ManSync/WomanSync.


Exercise ManWoman CP

CSP a separate section. See how it goes after sec1 completes

[10:27] Gavin Lowe
setValue.q <- if q!=<> then deqLP... else enqLP..., setValue.q <- if length(q)!=max then enqLP  else deqLP

If nThread called > nThread required, then sync must be possible.