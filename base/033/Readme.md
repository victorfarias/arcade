# Trem 1
<!-- ### Embarque nesse trem da alegria e descubra que passageiros não morrem quando saem do trem. -->
![](figura.jpg)

<!--TOC_BEGIN-->

- [Funcionalidades](#funcionalidades)
    - [Parte 1 - Trem e Vagões](#parte-1---trem-e-vagões)
    - [Parte 2 - Embarque e Desembarque](#parte-2---embarque-e-desembarque)
    - [Parte 3 - Cadastro de Passeiros e Registro de Embarque](#parte-3---cadastro-de-passeiros-e-registro-de-embarque)
- [Exemplos](#exemplos)
- [Raio X](#raio-x)
<!--TOC_END-->

O objetivo desta atividade é implementar um sistema que aloca passageiros em vagões de um trem. O trem é formado por uma série de vagões. É possível embarcar, desembarcar passageiros, ver quem está embarcado e a lista de passageiros que já passaram pelo nosso trem.

## Funcionalidades

### Parte 1 - Trem e Vagões

- Inicie um novo trem com a quantidade limite de vagões máxima que ele pode carregar.

- Adicionar um novo vagão.
    - Ao adicionar, observe o número máximo de vagões suportados pela locomotiva.
    - O vagão possui uma capacidade que define quantos lugares ele possui.
    - O vagão é adicionado ao final do trem.

### Parte 2 - Embarque e Desembarque

- Embarcar um novo passageiro.
    - Ao tentar embarcar, o trem vai procurar a primeira cadeira livre a partir do primeiro vagão em ordem crescente.
    - Se não houver espaço livre em nenhum vagão então não haverá embarque.
    - Passageiros possuem id e nome.
    - Não deve ser possível embarcar duas vezes o mesmo passageiro no trem.
- Desembarcar um passageiro
    - Se o passageiro estiver no trem, ele sai do trem liberando sua cadeira.



***
## Esqueleto

```
class Passageiro
- id: string
- nome: string
--
+ constructor(id:string, nome:string)
+ getId()
+ getName()

class Vagao
- cadeiras: (Passageiro|null)[]
- capacidade:number
--
+ constructor(capacidade: number)
+ embarcar(pass: Passageiro):boolean
+ desembarcar(idPass: String)
+ exists(idPass): boolean
+ getPassageiros():(Passageiro|null)[]


class Trem
- maxVagoes: number
- vagoes: Vagao[]
--
+ constructor(maxVagoes: number)
+ adicionarVagao(vagao: Vagao): boolean
+ embarcar(pass: Passageiro):boolean
+ desembarcar(idPass: string)
+ exists(idPass): bool
+ getVagoes(): Vagao[]
```



