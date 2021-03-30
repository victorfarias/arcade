import java.util.Scanner;

class Person {
    public String name;
    public int age;

    public Person(String name, int age){
        this.name = name;
        this.age = age;
    }

    public String toString(){
        return "[" + this.name + ":" + this.age + "]";
    }
}

class Motorcycle {
    public Person person; //agregacao
    public int power;
    public int time;
    //Inicia o atributo power, time com zero e person com null
    public Motorcycle(int power){
        this.power = power;
        this.time = 0;
    }
    //Comprar mais tempo
    public void buy(int time){
        this.time += time;
    }
    
    //Se estiver vazio, coloca a pessoa na moto e retorna true
    public boolean in(Person person) {
        if(this.person == null){
            this.person = person;
            return true;
        }
        System.out.println("fail: moto ocupada");
        return false;
    }
    //Se houver uma person, retira e retorna
    //Se não, retorna null
    public Person out() {
        if(this.person != null){
            Person person = this.person;
            this.person = null;
            return person;
        }else{
            System.out.println("fail: moto vazia");
            return null;
        }
    }

    public void drive(int time){
        if(this.person == null)
            System.out.println("fail: moto vazia");
        else if(this.person.age > 10)
            System.out.println("fail: muito grande para andar de moto");
        else if(this.time == 0)
            System.out.println("fail: time zerado");
        else if(this.time < time){
            System.out.println("fail: andou " + this.time + " min e acabou o time");
            this.time = 0;
        }else
            this.time -= time;
    }

    //buzinar
    public void honk(){
        if(this.person == null){
            System.out.println("fail: moto vazia");
            return;
        }
        String saida = "";
        for(int i = 0; i < this.power; i++)
            saida += "e";
        System.out.println("P" + saida + "m");
    }
    
    public String toString(){
        return "power: " + this.power + ", minutos: " + this.time + ", person: " + this.person;
    }
}
//!KEEP

class Solver{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Motorcycle motoca  = new Motorcycle(1);
        while(true) {
            String line = scanner.nextLine();
            String ui[] = line.split(" "); //ui user input eh um vetor de strings
            System.out.println("$" + line);
            if(ui[0].equals("end")) {
                break;
            }else if(ui[0].equals("init")) { //power
                Person person = motoca.person;
                motoca = new Motorcycle(Integer.parseInt(ui[1]));
                motoca.in(person);
            }else if(ui[0].equals("in")) { //in name age
                int age = Integer.parseInt(ui[2]);
                Person person = new Person(ui[1], age);
                motoca.in(person);
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
        Motorcycle moto = new Motorcycle(1);
        System.out.println(moto);
        //power: 1, minutos: 0, person: null
        moto.honk();
        //fail: moto vazia
        moto.in(new Person("marcos", 4));
        System.out.println(moto);
        //power: 1, minutos: 0, person: [marcos:4]
        moto.honk();
        //Pem
        moto.in(new Person("marisa", 2));
        //fail: moto ocupada
        System.out.println(moto);
        //power: 1, minutos: 0, person: [marcos:4]

        //case subindo e buzinando
        moto = new Motorcycle(5);
        System.out.println(moto);
        //power: 5, minutos: 0, person: null
        moto.in(new Person("marcos", 4));
        System.out.println(moto);
        //power: 5, minutos: 0, person: [marcos:4]
        moto.honk();
        //Peeeeem

        //case subindo e trocando
        moto = new Motorcycle(7);
        moto.in(new Person("heitor", 6));
        System.out.println(moto);
        //power: 7, minutos: 0, person: [heitor:6]
        Person heitor = moto.out();
        System.out.println(heitor);
        //[heitor:6]
        moto.out();
        //fail: moto vazia
        moto.in(new Person("suzana", 8));
        System.out.println(moto);
        //power: 7, minutos: 0, person: [suzana:8]

        //case passeando
        moto = new Motorcycle(7);
        moto.in(new Person("suzana", 8));
        moto.drive(10);
        //fail: time zerado
        moto.buy(40);
        System.out.println(moto);
        //power: 7, minutos: 40, person: [suzana:8]
        moto.drive(20);
        System.out.println(moto);
        //power: 7, minutos: 20, person: [suzana:8]

        //case nem grande nem pequeno
        moto = new Motorcycle(7);
        moto.buy(20);
        moto.in(new Person("andreina", 23));
        moto.drive(15);
        //fail: muito grande para andar de moto
        System.out.println(moto);
        //power: 7, minutos: 20, person: [andreina:23]

        //case acabou o time
        moto = new Motorcycle(7);
        moto.buy(20);
        moto.in(new Person("andreina", 6));
        moto.drive(15);
        System.out.println(moto);
        //power: 7, minutos: 5, person: [andreina:6]
        moto.drive(10);
        //fail: andou 5 min e acabou o time
    }
}
//!OFF



