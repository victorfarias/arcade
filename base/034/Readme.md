# Trem 2
### Trem com passageiros e carga.
![](figura.jpg)

<!--TOC_BEGIN-->    
- [Funcionalidades](#funcionalidades)
    - [Parte 1 - Trem e Vagões](#parte-1---trem-e-vagões)
    - [Parte 2 - Embarque e Desembarque](#parte-2---embarque-e-desembarque)
    - [Parte 3 - Cadastro de Passeiros e Registro de Embarque](#parte-3---cadastro-de-passeiros-e-registro-de-embarque)
- [Raio X](#raio-x)
<!--TOC_END-->

Vamos ampliar a atividade do trem colocando vagões de carga para carregar bagagens.

## Funcionalidades

### Parte 1 - Trem e Vagões

- Um vagão de pessoas possui uma quantidade de cadeiras.
- Um passageiro possui um identificador.
- Um vagão de carga tem um limite de peso.
- Uma carga possui um peso e um identificador.
- O trem possui um máximo de vagões que ele pode carregar.

```
  # init _maxVagoes  #inicia o trem
  # nwvp _capacidade #novo vagao de pessoa
  # nwvc _pesoMax    #novo vagao de carga
init 3
  done
nwvp 2
  done
la
  Trem [ - - ]
nwvc 350.0
  done
la
  Trem [ - - ]( _350.0 )
nwvp 2
  done
la
  Trem [ - - ]( _350.0 )[ - - ]
nwvc 1
  fail: limite de vagões atingido
```

### Parte 2 - Embarque e Desembarque

- Embarcar um novo item.
    - Ao tentar embarcar, o trem vai procurar o primeiro vagão livre para inserir o item.
    - Se não houver espaço livre em nenhum vagão então não haverá embarque.
    - Não deve ser possível embarcar duas vezes o mesmo passageiro ou a mesma carga no trem.
- Desembarcar um passageiro ou carga
    - Se o item estiver no trem, ele sai do trem liberando sua vaga.

```
# addp _idPass #tenta embarcar uma pessoa
# addc _idCarga _peso #tenta embarcar uma carga

addp goku
la
  Trem [ goku - ]( _350.0 )[ - - ]
addp kate
addp sara
addp goku
  fail: goku já está no trem
la
  Trem [ goku kate ]( _350.0 )[ sara - ]
addp tina
addp james
  fail: trem lotado
la
  Trem [ goku kate ]( _350.0 )[ sara james ]
addc xilitos 200.0
la
  Trem [ goku kate ]( xilitos:200.0 _150.0 )[ sara james ]
addc pipoca 100.0
la
  Trem [ goku kate ]( xilitos:200.0 pipoca:100.0 _50.0 )[ sara james ]
addc cebolitos 100.0
  fail: trem lotado
sair kate
sair sara
sair rufus
  fail: rufus nao esta no trem
sair xilitos
la
  Trem [ goku - ]( pipoca:100.0 _50.0 )[ james ]
```

### Parte 3 - Cadastro de Passeiros e Registro de Embarque

- Mostrar a lista de itens cadastrados.
    - Quando um item tenta embarcar, ele é cadastrado no sistema, independente de conseguir ou não embarcar, independente se é passageiro ou carga.
    - Mostre a lista ordenada pelo id do passageiro.
- Mostrar a sequência de embarque e desembarque.

```
cadastro
  alex
  cebolitos:100.0
  goku
  james
  kate
  pipoca:100.0
  sara
  tina
  xilitos:200.0

movimentacao
  goku in
  kate in
  sara in
  tina in
  james in
  xilitos:200.0 in
  pipoca:100.0 in
  kate out
  sara out
  xilitos:200.0 out
```

***
## Raio X

```java
abstract class Passageiro
# id: string
--
+ constructor(id:string)
+ getId()


class PassageiroPessoa extends Passageiro
  - nome: string
  ---
  + constructor(id:string, nome:string)
  + getName()


class PassageiroCarga extends Passageiro
  - descricao: string
  - peso: number
  ---
  + constructor(id:string, descricao:string, peso:number)
  + getDescricao()
  + getPeso()


abstract class Vagao
  # capacidade:number
  # cadeiras: (Passageiro|null)[]
  --
  + constructor(capacidade: number)  
  + desembarcar(idPass: String)
  + getPassageiros():(Passageiro|null)[]

class VagaoPassageiro extends Vagao
  ---
  + embarcar(pass: PassageiroPessoa):boolean

class VagaoCarga extends Vagao
  - pesoMaximo:number
  ---
  + constructor(capacidade: number, pesoMaximo: number)
  + embarcar(pass: PassageiroCarga):boolean
  + getPesoMaximo():number

class Trem
  - maxVagoes: number
  - vagoes: Vagao[]
  - vagoesPassageiro: VagaoPassageiro[]
  - vagoesCarga: VagaoPassageiro[]
  ---
  + constructor(maxVagoes: number)
  + adicionarVagaoPassageiro(vagao: VagaoPassageiro):boolean
  + adicionarVagao(vagao: VagaoPassageiro):boolean
  + embarcarPessoa(pass: PassageiroPessoa):boolean
  + embarcarCarga(pass: PassageiroCarga):boolean
  + desembarcar(idPass: string)
  + getVagoes(): Vagao[]
  + getVagoesPassageiro(): Vagao[]
  + getVagoesCarga(): Vagao[]
```
