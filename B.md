Summary of this week
- Amended Monitor Code [link to source code]
- I did not get `import` working. (I tried all kinds of combinations.)
  - So now I still have to copy monitor CSP code every where.
  - But while I was searching for documentation for import, I found `module`, which I think is a perfect way to encapsulate code. (Better than passing channels or monitorID)
- Further simplified parameters of monitor process. [link to source code]
  - Same structure. But only the numbers of waiting threads is kept in parameter (to avoid deadlock produced by `notifyAll + spuriousWake`, or `notify` when there is no waiting threads)
    - In the old version we non-deterministically choose a waiting process to notify with `?x:waiting`. But this is also enforced by the CSP semantics. 
    - If I use sliding operator, maybe I can remove this parameter?
  - But this comes at some costs.
    - Less information when debugging.
    - There is no way to avoid process outside `synchronized` block to run `wait`, `notify` etc.


Some minor questions
- It is probably something on Programming Language side, but I think in CSP-M, there is a simple way to pass variables between "A;B".
  - This may be useful for `ChanCounter`.
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
```
- Is there a style or naming conventions you would suggest? Now I am having channels, variables from parameters, functions names. And it is a bit hard to distinguish them.