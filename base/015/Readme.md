# Busca - Agenda 2

<!--TOC_BEGIN-->
- [Requisitos Novos](#requisitos-novos)
- [Shell](#shell)
- [Diagrama](#diagrama)
- [Ajuda](#ajuda)
- [Main não interativa](#main-não-interativa)

<!--TOC_END-->
![](figura.jpg)

Sua agenda possui vários contatos e cada contato possui vários telefones.
***
## Requisitos Novos
- Adicionar
    - O contato possui o nome como chave. Portanto, não permita que existam dois contatos com o mesmo nome.
    - Adicionar os novos números de telefone no contato já existente.
- Agenda
    - Mostrar os contatos da agenda pela ordem alfabética.
- Remoção
    - Remover contato pela chave.
- Busca
    - Fazer uma busca por padrão em todos os atributos do contato, nome e telefones.
    - Se o contato tiver qualquer campo que combine com a string pattern de busca, ele deve ser retornado. Se o pattern é maria, devem ser retornados os contatos como "maria julia", "mariana", "ana maria", etc. Também inclua na busca o id do telefone ou o número do telefone.


## Shell

```python
#__case adicionando em lote
$add eva oio:8585 cla:9999
$add ana tim:3434 
$add bia viv:5454

# como ana já existe, não crie um novo contato
# adicione os telefones ao contato existente
$add ana cas:4567 oio:8754

$show
- ana [0:tim:3434] [1:cas:4567] [2:oio:8754]
- bia [0:viv:5454]
- eva [0:oio:8585] [1:cla:9999]


#__case removendo telefone
# remove o elemento indice 0 da ana
$rmFone ana 0

$show
- ana [0:cas:4567] [1:oio:8754]
- bia [0:viv:5454]
- eva [0:oio:8585] [1:cla:9999]

#__case removendo contato
$rm bia

$show
- ana [0:cas:4567] [1:oio:8754]
- eva [0:oio:8585] [1:cla:9999]

$add ava tim:5454
$add rui viv:2222 oio:9991
$add zac rec:3131

$show
- ana [0:cas:4567] [1:oio:8754]
- ava [0:tim:5454]
- eva [0:oio:8585] [1:cla:9999]
- rui [0:viv:2222] [1:oio:9991]
- zac [0:rec:3131]

#__case busca por padrao
$search va
- ava [0:tim:5454]
- eva [0:oio:8585] [1:cla:9999]

$search 999
- eva [0:oio:8585] [1:cla:9999]
- rui [0:viv:2222] [1:oio:9991]

$end

#__end__
```
***
## Diagrama
![](diagrama.png)

***
## Ajuda
- Você pode criar mais métodos auxiliares para lhe ajudar.
- Crie um construtor para o Fone que aceite um único parâmetro, no caso o serial "oi:13123"
- Para receber vários telefones por linha você pode fazer um laço pegando a partir do índice 2.
- O construtor que recebe um serial pode ser utilizado para instanciar um Fone
```java
if(ui[0].equals("add")){ //add julia tim:99 oi:123 vivo:123544
    for(int i = 2; i < ui.length; i++)
        agenda.add(ui[1], new Fone(ui[i]));
}
```

- A sua função add deve criar um contato novo se o contato não existir e então adicionar o telefone
```java
void add(String name, Fone fone){
    Contact contact = this.getContact(name);
    if(contact == null){ //cria e adiciona o contato se ele nao existir
        contact = new Contact(name);
        this.contacts.add(contact);
    }
    contact.addFone(fone);  //aproveita para adicionar o telefone
    //ordene ser vetor
}
```

- Para fazer a busca por padrão você pode criar uma lista auxiliar de contatos.
- Na busca por pattern verifique faça uma busca usando a substring com o valor toString() to contato.

```java
ArrayList<Contact> search(String pattern){
    ArrayList<Contact> result = new ArrayList<>();
    for(Contact contact : this.contacts){
        if ... //se esse contato bater com o padrão
            result.add(contact);
    }
    return result;
}
```



***
## Main não interativa
```java
//case adicionando em lote
Agenda agenda = new Agenda();
agenda.addContact("eva", Arrays.asList(new Fone("oio", 8585), new Fone("cla", 9999)));
agenda.addContact("ana", Arrays.asList(new Fone("Tim", 3434)));
agenda.addContact("bia", Arrays.asList(new Fone("viv", 5454)));
agenda.addContact("ana", Arrays.asList(new Fone("cas", 4567), new Fone("oio", 8754)));
System.out.println(agenda);
/*
- ana [0:tim:3434] [1:cas:4567] [2:oio:8754]
- bia [0:viv:5454]
- eva [0:oio:8585] [1:cla:9999]
*/

//case removendo telefone
agenda.rmFone("ana", 0);
System.out.println(agenda);
/*
- ana [0:cas:4567] [1:oio:8754]
- bia [0:viv:5454]
- eva [0:oio:8585] [1:cla:9999]
*/

//case removendo contato
agenda.rmContact("bia");
System.out.println(agenda);
/*
- ana [0:cas:4567] [1:oio:8754]
- eva [0:oio:8585] [1:cla:9999]
*/
agenda.addContact("ava", Arrays.asList(new Fone("viv", 5454)));
agenda.addContact("rui", Arrays.asList(new Fone("viv", 2222),new Fone("oio", 9991)));
agenda.addContact("zac", Arrays.asList(new Fone("rec", 3131)));
System.out.println(agenda);
/*
- ana [0:cas:4567] [1:oio:8754]
- ava [0:tim:5454]
- eva [0:oio:8585] [1:cla:9999]
- rui [0:viv:2222] [1:oio:9991]
- zac [0:rec:3131]
*/

//case busca por padrao
for(Contato contato : agenda.search("va")){
    System.out.println(contato);
}
/*
- ava [0:tim:5454]
- eva [0:oio:8585] [1:cla:9999]
*/
for(Contato contato : agenda.search("999")){
    System.out.println(contato);
}
/*
- eva [0:oio:8585] [1:cla:9999]
- rui [0:viv:2222] [1:oio:9991]
*/
```