## Raio X
```c++
class Kid
- name: String
- age: int
--
+ toString()
+ getName(): String
+ getAge(): int
--
Kid(name, age)

class Trampoline:
- kidsWaiting: Kid[]
- kidsPlaying: Kid[]
--
+ toString(): String
+ arrive(kid: Kid)
+ show(): void
+ in(): void
+ out(): void
```