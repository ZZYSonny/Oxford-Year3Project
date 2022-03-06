
```csp
channel a,b,c

A = a -> STOP
B = A[[a<-a,a<-b]]
C = b -> c -> STOP

D = B [|{b}|] C
```