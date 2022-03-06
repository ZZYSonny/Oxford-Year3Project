I think the renaming trick does not work in FDR.
```csp
channel a,b,c,d
A = a -> STOP
B = A[[a<-a,a<-b]]
C = a -> c -> STOP
D = b -> d -> STOP
E = (B [|{a}|] C) [|{b}|] D
```

Implemented failure test (by counting #) for filterchan