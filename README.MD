The program is meant to introduce a deepClone functionality written with Java 23.
I used mockito for testing

There are two models Man & Account.
Man is the object provided in the task

The main method illustrates how deep clone works for Man object.
Additionally in the tests package, you can find how the deep clone worked for
Account object. The challenge in the last object is that there is a recursion in objects.
An account has list of other accounts as their friends. When cloning the friends list
algorithm eventually comes to the original object. The reason is simple, if A is friend of B, then
B is friend of A. I solved this issue by introducing a simple caching mechanism that uses
Hash Map. Besides checking the logical equivalence, the tests also check the references, so 
that cloning the object does not result in pointing to the original references in the memory.