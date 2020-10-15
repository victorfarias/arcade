## Raio X
```java
class Fone
--
+ id: string
+ number: string
--
+ _validate(number):_ bool
+ toString(): string
--
Fone(serial)             <------ NOVO
Fone(id, number)

class Contato
--
- name: string
- fones: Fone[]
--
+ addFone(id: string, number: string) : boolean
+ rmFone(index: int) : boolean
+ toString()
--
Contato(name)
getName(): string
getFones(): Fone[]


class Agenda
--
- contatos: Contato[]
--
- findContato(name: string): int

+ addContato(name: string, fones: Fone[])
+ rmContato(name: string): bool
+ getContato(name: string): Contato
+ search(pattern: string): Contato[]
+ getContatos(): Contato[]
```