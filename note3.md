- (I found a better grammar checker inside my latex editor. The last one only checks for spelling mistakes :)
- ABC Spec only 
- I found why my implementation for TerminatingQueue was slow
  - In my old implementation, I have `if (y==0) then div else` as a assertion for CSP to avoid considering these case at compile stage. (For example, waiting should always >0 when `waiting-=1`)
  - When I move the assertion to `getValue?y:{1..NThread}`, the system has less states and transitions. No difference between the systems.
  - With the old implementation, FDR builds the unncessary transitions. And these transistions creates more unnecessary transititons when running with multiple threads.
- Implemented LP Test for Exchanger
  - There are a few observed advantages of explicit LP testing
    - Apparently the Spec process has fewer states and transitions. (Linear to number of thread)
    - Reduce state and transition for the system. Call/Return events are more complicated than P1/P1'/P2. The first can occur like (Call, Call, Return, Return), or (Call, Return, Call, Return). The second always happen like (P1,P1',P2, then another new round), at least for this object.
    - Comparing is faster. (Small [T= Big is faster than Big [T= Big). You can run `assert Spec [T= System({T1,T2,T3,T4,T5})` and assert `System({T1,T2,T3,T4,T5}) [T= System({T1,T2,T3,T4,T5})`
  - But state and transition of the system still grows exponentially with number of thread. 
|Number of Participating Thread|Lin State|Lin Transition|S 

- LP Test
  - Return value of a function needs to be checked by hand. 
  - Instead of renaming, I think it is equivalent to have LP event after setting global variable value. 
    - For (single) monitor based object, there is at most one thread running at the same time. 
    - For (single) semaphore based object, when one thread update some value, another thread needs to be synchronized to get the updated value. If the implementation is correct then another thread should not read the updated value before the LP event (raise semaphore). If developer forgets about this, this can be captured by P2->P1->P1'.