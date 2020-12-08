# Cofre

![](figura.jpg)

<!--TOC_BEGIN-->
- [Descrição](#descrição)
- [UML](#uml)
- [Enum](#enum)
- [Main](#main)

<!--TOC_END-->

## Descrição
O sistema deverá:

- Gerenciar um cofrinho do tipo Porquinho capaz de guardar moedas e itens.
- As moedas devem ser criadas através de uma `enum`.
-  tem uma descrição.
- Ambos moedas e itens tem um atributo volume.
- O volume do cofre incrementa conforme ele recebe itens e moedas.
- A lógica da utilização do cofre é:
    - Para inserir moedas e itens o cofre deve estar inteiro.
    - Para obter moedas e itens o cofre deve estar quebrado.
    - Ao obter moedas e itens, os atribuitos `valor` e `itens` do porco devem ser zerados.

## UML

```java
enum Moeda
M10(0.10, 1)
M25(0.25, 3)
M50(0.50, 2)
M100(1.00, 4)
--
+ valor: double
+ volume: int
--
Moeda(valor, volume)

class Item
+ descricao: String
+ volume: int
--
Item(descricao, volume)

class Porco
+ itens: String
+ valor: double
+ volume: int
+ volumeMax: int
+ estaQuebrado: boolean
--
+ addMoeda(moeda: Moeda): boolean
+ addItem(item: Item): boolean
+ quebrar(): bool
+ pegarMoedas(): float
+ pegarItens(): String
```

## Enum

Você pode criar as Moedas utilizando a enum do Java:
```java
enum Moeda {
    M10(0.10, 1),
    M25(0.25, 2),
    M50(0.50, 3),
    M100(1.00, 4);

    double valor;
    int volume;

    Moeda(double valor, int volume) {
        this.valor = valor;
        this.volume = volume;
    }

    public String toString() {
        return "Valor: " + valor + " Volume: " + valor;
    }
}
```

## Main

```java
public class Solver{
    public static void main(String[] args) {
        Porco porco = new Porco(20);
        System.out.println(porco); //I:() M:0 V:0/20 EQ:false
        porco.addMoeda(Moeda.M10);
        porco.addMoeda(Moeda.M50);
        System.out.println(porco); //I:() M:0.6 V:4/20 EQ:false

        porco.addItem(new Item("ouro", 3));
        System.out.println(porco); //I:(ouro) M:0.6 V:7/20 EQ:false

        porco.addItem(new Item("passaporte", 2));
        System.out.println(porco); //I:(ouro, passaporte) M:0.6 V:9/20 EQ:false

        porco.pegarItens();  //Voce deve quebrar o cofre primeiro
        porco.pegarMoedas(); //Voce deve quebrar o cofre primeiro
        System.out.println(porco); //I:(ouro, passaporte) M:0.6 V:9/20 EQ:false

        porco.quebrar();
        porco.quebrar(); //O cofre ja esta quebrado

        System.out.println(porco.pegarItens());  //ouro, passaporte
        System.out.println(porco.pegarMoedas()); //0.6
        System.out.println(porco); //I:() M:0.0 V:9/20 EQ:true
    }
}
```
