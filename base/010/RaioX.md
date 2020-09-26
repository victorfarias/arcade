## Raio X

```java
class Cliente
+ id: string
+ fone: string

class Cinema 
+ cadeiras:Cliente[]
--
+ reservar(id, fone, indice): bool    //reservar
+ cancelar(id): bool   //cancelar
--
+ Cinema(lotacao)
```