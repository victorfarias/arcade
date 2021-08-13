# Clinica Veterinária

<!--TOC_BEGIN-->
- [Requisitos Parte 1](#requisitos-parte-1)
- [Raio X](#raio-x)
<!--TOC_END-->

Totó tá com a perna dodói. Faça o sistema da clínica veterinária para cadastrar clientes, animais, servicos e vendas.

![](figura.jpg)

Você deve desenvolver o sistema de uma clínica veterinária que deve ser capaz de:

***

## Requisitos Parte 1

- Cadastrar clientes pelo idCliente
    - Id de cliente deve ser único entre os clientes
    - Cliente tem um id e um nome 

- Cadastrar animais.
    - Animal tem um id, um nome e uma especie e está vinculado a um único cliente.
    - Cada animal cadastrado deve receber um id único inteiro crescente do sistema.
    - Um cliente não pode ter dois animais com o mesmo nome.
- Mostrar todos os animais cadastrados.
- Mostre os clientes com seus animais.

- Adicionar serviços na clínica.
    - Cada serviço tem um id único e um preço.
- Mostrar os serviços cadastrados

```
# addSer _idSer _preco
nwser tosa 30.0
  done
nwser banho 15.0
  done
nwser tingimento 150.0
  done
laser
  [tosa 30.0]
  [banho 15.0]
  [tingimento 150.0]
```

- Vender serviços para um animal passando id do cliente e nome do animal.
- Dê para cada venda um id inteiro único crescente.
- Mostrar vendas.
- Mostrar dinheiro total recebido.
- Trate os possíveis erros.

```
nwven luke rosinha tosa
  done
nwven vader rex banho
  done
nwven luke rosinha tingimento  
  done
nwven r2d2 rex banho
  fail: cliente r2d2 nao existe
nwven luke xuxu banho
  fail: animal xuxu nao existe
nwven luke rosinha castracao
  fail: servico castracao nao existe
laven
  [0 luke rosinha tosa]
  [1 vader rex banho]
  [2 luke rosinha tingimento]
saldo
  Saldo 195 reais
```

***
## Raio X

````c
class Animal
  - id: number
  - nome: string
  - especie: string
  - dono: Cliente
  --
  + constructor(id:number, nome:string, especie:string, dono:Client)
  + getId():number
  + getNome():string
  + getEspecie():string
  + getDono():Cliente

class Cliente
  - id: string
  - nome: string
  - animais: Animal[]
  --
  + constructor (id:number, nome:string)
  + getId():string
  + getNome():string
  + getAnimais():Animal[]
  + addAnimal(animal: Animal): void

class Servico
  - id: number
  - nome: string
  - valor: number
  --
  + constructor(id:number, nome:string)
  + getId(): number
  + getNome(): string
  + getValor(): number

class Venda
  - id_animal: number
  - id_cliente: number
  - id_servico: number
  --
  + constructor(id_cliente: number, id_animal: number, id_servico: number)
  + getIdAnimal(): number
  + getIdCliente(): number
  + getIdServico(): number


class Clinica
  - nextServicoId: number
  - nextAnimalId: number
  - nextVendaId: number
  - nextClienteId: number
  - servicos: Servico[]
  - vendas: Venda[]
  - clientes: Cliente[]
  --
  + constructor()
  + addCliente(nome:string): void -> lançar exceção se nome está repetido
  + addServico(nome: String, valor: number): void -> lançar exceção se nome está repetido
  + addAnimal(idCliente: number, nomeAnimal: String, especie: String): void -> lançar exceção se nome está repetido
  + vender(idCliente: number, idAnimal: number, IdServico: number): void -> lançar exceção de animal não for do cliente
  + saldo(): number
  + getClientes(): Cliente[]
  + getServicos(): Servico[]
  + getVendas(): Animal[]
````
