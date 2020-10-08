## Raio X
- Utilize funções de get e set para garantir que os atributos permaneçam na faixa permitida.
- Mantenha as funções set como privadas e dê acesso apenas aos métodos de ação como comer, dormir, brincar.

```java
class Pet:
--
- energyMax, hungryMax, cleanMax: int 
- energy, hungry, clean: int
- diamonds: int
- age: int
- alive: bool
---
+ setEnergy(value: int): void
+ setHungry(value: int): void
+ setClean(value: int): void
---
+ Pet(energyMax, hungryMax, cleanMax)
+ allGets


class Jogo:
- Pet pet;
--
+ iniciar(Tamagotchi pet): void
+ play(): void
+ shower(): void
+ eat(): void
+ sleep(): void
+ show(): void
```