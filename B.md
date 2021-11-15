Summary of this week
- Amended [Monitor Code](https://github.com/ZZYSonny/Oxford-Year3Project/blob/main/csp/lib/monitor_min.csp)
- I did not get `import` working. (I tried all kinds of combinations.)
  - So now I still have to copy monitor CSP code every where.
  - But while I was searching for documentation for import, I found `module`, which I think is a perfect way to encapsulate code. (Better than passing channels or monitorID)
- Further simplified parameters of monitor process. [Link](https://github.com/ZZYSonny/Oxford-Year3Project/blob/main/csp/lib/monitor_min.csp)
  - Same structure. But only the numbers of waiting threads is kept in parameter (to avoid deadlock produced by `notifyAll + spuriousWake`, or `notify` when there is no waiting threads)
    - In the old version we non-deterministically choose a waiting process to notify with `?x:waiting`. But this is also enforced by the CSP semantics. 
    - If I use sliding operator, maybe I can remove this parameter?
  - But this comes at some costs.
    - Less information when debugging.
    - There is no way to avoid process outside `synchronized` block to run `wait`, `notify` etc.
- Updated [FilterChan](https://github.com/ZZYSonny/Oxford-Year3Project/tree/main/csp/filterchan) and Implemented [Exchange](https://github.com/ZZYSonny/Oxford-Year3Project/tree/main/csp/exchange), with one test for each object.

Some questions
- For FilterChan, I placed `sendStart` and `receiveReturn` in a place so that spec process are easy to write. Do I need more aspect for testing?
- It is probably something on Programming Language side, but I think in CSP-M, there is a simple way to pass variables between "A;B". (I used this is `exchange`)
```hs
P = (in?x:A -> SKIP); (out!x -> STOP)
--variable x not in scope

P = 
  in?x:A -> 
  let xx = x within 
  SKIP;
  out!x->
  STOP
--is OK
--Lambda with x as parameter also works.

P1 = 
  in?x:A ->
  P2(x)

P2(x) =
  SKIP;
  out!x -> 
  STOP
```
- I am thinking about changing the type of `synchronized` to `threadID -> (\MonitorObject -> Proc)`. So `notify` can only happen inside synchronized block. Also, this avoids passing `me` every time I use functions in the monitor. 
- Is there a style or naming conventions you would suggest? Now I am having channels, variables from parameters, functions names. And it is a bit hard to distinguish them.