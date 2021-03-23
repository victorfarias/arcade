import java.util.Scanner;

class Pessoa {
    String nome;
    int idade;

    Pessoa(String nome, int idade){
        this.nome = nome;
        this.idade = idade;
    }

    public String toString(){
        return "[" + this.nome + ":" + this.idade + "]";
    }
}

class Motoca {
    Pessoa pessoa; //agregacao
    int potencia;
    int tempo;
    //Inicia o atributo potencia, tempo com zero e pessoa com null
    Motoca(int potencia){
        this.potencia = potencia;
        this.tempo = 0;
    }
    //Comprar mais tempo
    void buy(int tempo){
        this.tempo += tempo;
    }
    
    //Se estiver vazio, coloca a pessoa na moto e retorna true
    boolean in(Pessoa pessoa) {
        if(this.pessoa == null){
            this.pessoa = pessoa;
            return true;
        }
        System.out.println("fail: moto ocupada");
        return false;
    }
    //Se houver uma pessoa, retira e retorna
    //Se não, retorna null
    Pessoa out() {
        if(this.pessoa != null){
            Pessoa pessoa = this.pessoa;
            this.pessoa = null;
            return pessoa;
        }else{
            System.out.println("fail: moto vazia");
            return null;
        }
    }

    void drive(int tempo){
        if(this.pessoa == null)
            System.out.println("fail: moto vazia");
        else if(this.pessoa.idade > 10)
            System.out.println("fail: muito grande para andar de moto");
        else if(this.tempo == 0)
            System.out.println("fail: tempo zerado");
        else if(this.tempo < tempo){
            System.out.println("fail: andou " + this.tempo + " min e acabou o tempo");
            this.tempo = 0;
        }else
            this.tempo -= tempo;
    }

    //buzinar
    void honk(){
        if(this.pessoa == null){
            System.out.println("fail: moto vazia");
            return;
        }
        String saida = "";
        for(int i = 0; i < this.potencia; i++)
            saida += "e";
        System.out.println("P" + saida + "m");
    }
    
    public String toString(){
        return "potencia: " + this.potencia + ", minutos: " + this.tempo + ", pessoa: " + this.pessoa;
    }
}
//!KEEP

class Solver{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Motoca motoca  = new Motoca(1);
        while(true) {
            String line = scanner.nextLine();
            String ui[] = line.split(" "); //ui user input eh um vetor de strings
            System.out.println("$" + line);
            if(ui[0].equals("end")) {
                break;
            }else if(ui[0].equals("init")) { //potencia
                Pessoa pessoa = motoca.pessoa;
                motoca = new Motoca(Integer.parseInt(ui[1]));
                motoca.in(pessoa);
            }else if(ui[0].equals("in")) { //in nome idade
                int idade = Integer.parseInt(ui[2]);
                Pessoa pessoa = new Pessoa(ui[1], idade);
                motoca.in(pessoa);
            }else if(ui[0].equals("out")) {
                motoca.out();
            }else if(ui[0].equals("show")) {
                System.out.println(motoca);
            }else if(ui[0].equals("drive")) {
                motoca.drive(Integer.parseInt(ui[1]));
            }else if(ui[0].equals("buy")) {
                motoca.buy(Integer.parseInt(ui[1]));
            }else if(ui[0].equals("honk")) {
                motoca.honk();
            }else {
                System.out.println("Comando invalido");
            }
        }
        scanner.close();
    }
}

class Manual{
    public static void main(String[] args) {
        //case subindo e buzinando
        Motoca moto = new Motoca(1);
        System.out.println(moto);
        //potencia: 1, minutos: 0, pessoa: null
        moto.honk();
        //fail: moto vazia
        moto.in(new Pessoa("marcos", 4));
        System.out.println(moto);
        //potencia: 1, minutos: 0, pessoa: [marcos:4]
        moto.honk();
        //Pem
        moto.in(new Pessoa("marisa", 2));
        //fail: moto ocupada
        System.out.println(moto);
        //potencia: 1, minutos: 0, pessoa: [marcos:4]

        //case subindo e buzinando
        moto = new Motoca(5);
        System.out.println(moto);
        //potencia: 5, minutos: 0, pessoa: null
        moto.in(new Pessoa("marcos", 4));
        System.out.println(moto);
        //potencia: 5, minutos: 0, pessoa: [marcos:4]
        moto.honk();
        //Peeeeem

        //case subindo e trocando
        moto = new Motoca(7);
        moto.in(new Pessoa("heitor", 6));
        System.out.println(moto);
        //potencia: 7, minutos: 0, pessoa: [heitor:6]
        Pessoa heitor = moto.out();
        System.out.println(heitor);
        //[heitor:6]
        moto.out();
        //fail: moto vazia
        moto.in(new Pessoa("suzana", 8));
        System.out.println(moto);
        //potencia: 7, minutos: 0, pessoa: [suzana:8]

        //case passeando
        moto = new Motoca(7);
        moto.in(new Pessoa("suzana", 8));
        moto.drive(10);
        //fail: tempo zerado
        moto.buy(40);
        System.out.println(moto);
        //potencia: 7, minutos: 40, pessoa: [suzana:8]
        moto.drive(20);
        System.out.println(moto);
        //potencia: 7, minutos: 20, pessoa: [suzana:8]

        //case nem grande nem pequeno
        moto = new Motoca(7);
        moto.buy(20);
        moto.in(new Pessoa("andreina", 23));
        moto.drive(15);
        //fail: muito grande para andar de moto
        System.out.println(moto);
        //potencia: 7, minutos: 20, pessoa: [andreina:23]

        //case acabou o tempo
        moto = new Motoca(7);
        moto.buy(20);
        moto.in(new Pessoa("andreina", 6));
        moto.drive(15);
        System.out.println(moto);
        //potencia: 7, minutos: 5, pessoa: [andreina:6]
        moto.drive(10);
        //fail: andou 5 min e acabou o tempo
    }
}
//!OFF



