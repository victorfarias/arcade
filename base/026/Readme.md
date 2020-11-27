# Whatsapp I - Chat Grupo

<!--TOC_BEGIN-->
- [Whatsapp I - Chat Grupo](#whatsapp-i---chat-grupo)
  - [Funcionalidades](#funcionalidades)
    - [Seu sistema deverá:](#seu-sistema-deverá)
  - [- Ao pedir a lista de notificações, o usuário vê ao lado de cada grupo se ele possui mensagens não lidas.](#--ao-pedir-a-lista-de-notificações-o-usuário-vê-ao-lado-de-cada-grupo-se-ele-possui-mensagens-não-lidas)
  - [Shell](#shell)
  - [Diagrama](#diagrama)
  - [Main não interativa](#main-não-interativa)
  - [Créditos](#créditos)

<!--TOC_END-->

![](figura.jpg)

## Funcionalidades
### Seu sistema deverá:

- Adicionar usuário passando username.
- Mostrar os usuários cadastrados.
- Criar chat(grupo) para mandar mensagens
    - O nome do Chat deve ser único no sistema.
    - Quando um usuário cria um Chat, o Chat começa com o usuário que criou.
- Ver os Chats de um usuario.
- Adicionar pessoas a um chat.
    - A pessoa que adiciona deve já estar no chat.
- Ver quem está em um chat.
- Sair de um chat.
- Mandar mensagens para um grupo.
    - A pessoa que manda as mensagens, tem que estar no grupo.
    - Apenas quem está no grupo, poderá ler as mensagens.
- Ler as mensagens de um grupo.    
    - Um usuário pode ser as mensagens do grupo se ele está no grupo.
    - Ao pedir as mensagens, o usuário receberá as mensagens não lidas que ele tem no grupo.
    - Ao pedir as mensagens, o usuário não recebe as mensagens que ele mesmo enviou.
- Ao pedir a lista de notificações, o usuário vê ao lado de cada grupo se ele possui mensagens não lidas.
---
## Shell

```
#__case adicionar e mostrar usuários 
# O comando "$addUser nomeUsuario" cria um novo usuário.
# O comando "$allUsers" mostra todos usuários.
$addUser goku
$addUser sara
$addUser tina
$allUsers
  [goku sara tina]
$end
```

```
#__case novo Chat, mostrar chats do usuário
# O comando "$newChat nomeUser nomeChat" cria um novo chat.
# O comando "$chats nomeUser" mostra os chats usuário.

$newChat goku guerreiros
$newChat goku homens
$newChat sara familia

# testando chats duplicados
newChat sara guerreiros
  fail: chat guerreiros ja existe

chats goku
 [guerreiros homens]
chats sara
  [familia]
chats tina
  []
$end
```

```
#__case adicionar, mostrar participantes e sair do chat
# O comando "$invite nomeUserChat nomeUserConvocado nomeChat" primeiro verifica se o "nomeUserChat" esta no chat e adiciona o "nomeUserConvocado" ao chat.
# O comando "$users nomeChat" mostra os usuários do chat.
# O comando "$chats nomeUser" mostras todos os chats de um usuário.
# O comando "$leave user nomeChat" remove o usuário do chat.

$invite goku sara guerreiros
$invite sara tina guerreiros
$invite tina goku familia
  fail: user tina nao esta em chat familia

$chats sara
  [familia guerreiros]
$chats tina
  [guerreiros]
$chats goku
  [guerreiros homens]

$users guerreiros
  [goku sara tina]
$users familia
  [sara tina]

$leave sara guerreiros
$users guerreiros
  [goku tina]
$chats sara
  [familia]
$end
```

```
#__case mandar, ler mensagens e listar notificações
# O comando "$zap nomeUser nomeChat mensagen" manda uma mansagen do chat.
# O comando "$notify nomeUser" mostras os chats e o numero de notificação do usuário.
# O comando "$ler nomeUser nomeChat" mostra a mensagen do chat.

$zap goku guerreiros sou goku galera
$zap tina guerreiros oi goku

$notify goku
  [guerreiros(1) homens]
$notify tina
  [guerreiros(1)]

$ler goku guerreiros
  [tina: oi goku]
$ler tina guerreiros
  [goku: sou goku galera]

$ler sara guerreiros
  fail: user sara nao esta no chat guerreiros

$zap goku guerreiros vamos sair tina?
$zap tina guerreiros voce ta com fome goku?
$zap goku guerreiros to com saudade de voce.

$notify tina
  [guerreiros(2)]
$notify goku
  [guerreiros(1) homens]

$ler goku guerreiros
  [tina: voce ta com fome goku?]
$ler tina guerreiros
  [goku: vamos sair tina?]
  [goku: to com saudade de voce.]
$end
```

- Opcionais:
    - Enviar uma mensagem do sistema avisando quando um usuário entra ou sai de um Chat.

---

## Diagrama

![](./diagrama.png)

---

## Main não interativa
```java
```
---
## Créditos

Fica o agradecimento para turma de POO DD 2017.2 que fez nascer essa atividade comigo.
