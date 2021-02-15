# Twitter - Associação reflexiva
![](figura.jpg)

<!--TOC_BEGIN-->
- [Habilidades](#habilidades)
- [Funcionalidades](#funcionalidades)
- [Comandos e Exemplos](#comandos-e-exemplos)
- [Orientações](#orientações)
- [Main Interativa](#main-interativa)
<!--TOC_END-->

Vamos implementar o modelo do twitter. Os usuários se cadastram e podem follow outros usuários do sistema. Ao twittar, a mensagem vai para timeline de todas as pessoas que a seguem.

***
## Habilidades

- Nessa atividade o mesmo objeto User estará armazenado no repositório de usuários, como também nas listas de seguidores e seguidos do próprio usuário. 
- Também o objeto Tweet estará tanto na lista de meusTweets do usuário que postou como na timeline de seus seguidores. Dado que é o mesmo objeto tweet, quando uma usuário dá like e altera o objeto, todos vêem quem deu o like. 
- Tweets são gerados através de uma Classe Geradora de Tweets.
- Também User utiliza um contador de mensagem não lidas para mostrar apenas as novas mensagens.

***
## Funcionalidades
- **[2.0 P] Repositório de Usuários**
    - Adicionar usuário passando username.
    - Mostrar os usuários cadastrados.

- **[2.0 P] Seguir e ser seguido**
    - Seguir um outro usuário cadastrado.
    - Mostrar a lista de seguidores.
    - Mostrar a lista de seguidos.

- **[4.0 P] Voa passaarinho**
    - twittar um tweet com várias palavras.
        - o id de um tweet é único globalmente.
        - o tweet de um usuário vai para o início da timeline de seus seguidores.
        - o mesmo tweet vai também para sua própria lista de minhas mensagens.
    - mostrar as mensagens que você enviou.

- **[1.0 P] Quais as novidades?**
  - Mostrar apenas as mensagens não lidas.

- **[1.0 P] Gostei dei like**
    - Dar like num tweet da sua timeline.

***
## Comandos e Exemplos

```bash
##################################
# Repositório de Usuários
##################################
#__case cadastrar
$addUser goku
$addUser sara
$addUser tina
$show
goku
  seguidos   [ ]
  seguidores [ ]
sara
  seguidos   [ ]
  seguidores [ ]
tina
  seguidos   [ ]
  seguidores [ ]

##################################
# Seguir e ser seguido
##################################
#__case seguir

$follow goku sara
$follow goku tina
$follow sara tina
$show
goku
  seguidos   [ sara tina ]
  seguidores [ ]
sara
  seguidos   [ tina ]
  seguidores [ goku ]
tina
  seguidos   [ ]
  seguidores [ goku sara ]

##################################
# Voa passarinho
##################################
#__case twittar
#twittar _userId _msg

$twittar sara hoje estou triste
$twittar tina ganhei chocolate
$twittar sara partiu ru
$twittar tina chocolate ruim
$twittar goku internet maldita

$timeline goku
0:sara( hoje estou triste )
1:tina( ganhei chocolate )
2:sara( partiu ru )
3:tina( chocolate ruim )
4:goku( internet maldita )

$timeline tina 
1:tina( ganhei chocolate )
3:tina( chocolate ruim )

$timeline sara
0:sara( hoje estou triste )
1:tina( ganhei chocolate )
2:sara( partiu ru )
3:tina( chocolate ruim )

##################################
# Gostei dei like
##################################
#__case like
#like _username _idTw

$like sara 1
$like goku 1
$like sara 3

$timeline sara
0:sara( hoje estou triste )
1:tina( ganhei chocolate )[ goku sara ]
2:sara( partiu ru )
3:tina( chocolate ruim )[ sara ]

$timeline goku
0:sara( hoje estou triste )
1:tina( ganhei chocolate )[ goku sara ]
2:sara( partiu ru )
3:tina( chocolate ruim )[ sara ]
4:goku( internet maldita )

#__case unfollow
$unfollow goku tina
$show
goku
  seguidos   [ sara ]
  seguidores [ ]
sara
  seguidos   [ tina ]
  seguidores [ goku ]
tina
  seguidos   [ ]
  seguidores [ sara ]
##################################
# Errinhos
##################################
#__case erros

# lembre de tratar erros como
$timeline bruno
fail: usuario nao encontrado
$follow goku kuririm
fail: usuario nao encontrado
$like sara 8
fail: tweet nao existe
$end
##################################
# FIM FIM FIM FIM FIM FIM FIM FIM#
##################################
```

***
## Orientações

```python

class Tweet:
    # nao esqueca de nenhum atributo
    def constructor(idTw, username, msg):
        # inicialize tudo direitinho

    def darLike(username):
        # adicione username na lista se ja nao existir

    def toString():
        # saida = _idTw _username: _msg {likes}

class User:
    # adicione o resto dos atributos

    # utilize alguma estrutura de dados de lista ligada
    # que permita inserção no começo da lista

    naoLidos: int

    timeline: Tweet []
    myTweets: Tweet []

    seguidores: Map<str, User>
    seguidos  : Map<str, User>

    def constructor(username):
        # nao esqueca de inicializar nenhum atributo

    def follow(other: User):
        # other entra na minha lista de seguidos
        # this  entra na lista de seguidores de other

    def twittar(tweet):
        # adiciono o tweet na minha lista de myTweets
        # para cada um dos meus seguidores
            # incremente a contagem de nao lidas
            # adicione o tweet na timeline deles

    def darLike(idTw):
        # procure o tweet na sua timeline
        # se existir, chame o tweet.darLike(this.username)

    def get unread():
        # verifica contador de naoLidos
        # pega as mensagens da timeline até a quantidade necessária
        # lembre de zerar a contagem de nao lidos

    def get timeline():
        # retorna a timeline
        # lembre de zerar a contagem de nao lidos

    def toString():
        # retorne username

class Controller:
    repUser : Map<str, User>  # guarda todos os usuário do sistema
    repTweet: Map<int, Tweet> # guarda todos os tweets do sistema
    nextTwId: int # guarda o id para o próximo Tweet a ser gerado

    def sendTweet(username, msg):
        user = this.getUser(username)
        # verifique se o usuário existe
        # crie um objeto Tweet, preencha os dados, armazene no repTweet
        # invoque o método enviar twittar para que os tweets sejam entregues
        user.twittar(tweet)

    def addUser(username):
        # se esse username nao existir
        # crie e adicione o usuario no repUsuarios

    def getUser(username):
        user = repUsers.get(username)
        # se nao existir, lance uma excessão
        return user

    def follow(one: str, two: str):
        getUser(from).follow(getUser(to))

    def unfollow(one: str, two: str):
        getUser(from).unfollow(getUser(to))

```


## Main Interativa

```java
Scanner scanner = new Scanner(System.in);
Controller sistema = new Controller();

while(true){
    String line = scanner.nextLine();
    System.out.println("$" + line);
    String ui[] = line.split(" ");
    try {
        if (ui[0].equals("end"))
            break;
        else if (ui[0].equals("addUser")) {
            sistema.addUser(ui[1]);
        } else if (ui[0].equals("show")) {
            System.out.print(sistema);
        } else if (ui[0].equals("follow")) {//goku tina
            User one = sistema.getUser(ui[1]);
            User two = sistema.getUser(ui[2]);
            one.follow(two);
        }
        else if (ui[0].equals("twittar")) {//goku msg
            String username = ui[1];
            String msg = "";
            for(int i = 2; i < ui.length; i++)
                msg += ui[i] + " ";
            sistema.sendTweet(username, msg);
        }
        else if (ui[0].equals("timeline")) {//goku tina
            User user = sistema.getUser(ui[1]);
            System.out.print(user.getTimeline());
        }
        else if (ui[0].equals("like")) {//goku tina
            User user = sistema.getUser(ui[1]);
            Tweet tw = user.getTweet(Integer.parseInt(ui[2]));
            tw.like(ui[1]);
        }else if (ui[0].equals("unfollow")) {//goku tina
            User user = sistema.getUser(ui[1]);
            user.unfollow(ui[2]);
        }else{
            System.out.println("fail: comando invalido");
        }
    }catch(RuntimeException rt){
        System.out.println(rt.getMessage());
    }
}
scanner.close();

```