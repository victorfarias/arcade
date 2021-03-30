import java.util.LinkedList;
import java.util.Scanner;

class Kid {
    private int age;
    private String name;

    public Kid(String name, int age) {
        this.name = name;
        this.age = age;
    }
    int getAge() {
        return age;
    }
    String getName() {
        return name;
    }
    void setAge(int age) {
        this.age = age;
    }
    public void setName(String name) { //gs
        this.name = name;
    }
    public String toString() {
        return name + ":" + age;
    }
}

class Trampoline{
    private LinkedList<Kid> waiting;
    private LinkedList<Kid> playing;
    
    public Trampoline() {
        waiting = new LinkedList<>();
        playing = new LinkedList<>();
    }
    //remove and return the Kid with this name or null
    private Kid remove_kid(String name, LinkedList<Kid> list) {
        for(int i = 0; i < list.size(); i++)
            if(list.get(i).getName().equals(name))
                return list.remove(i);
        return null;
    }
    //insere na lista de espera
    public void arrive(Kid kid) {
        waiting.addFirst(kid);
    }
    //remove da lista de espera e insere na lista playing
    public void in() {
        playing.addFirst(waiting.getLast());
        waiting.removeLast();
    }
    //remove de playing para wainting
    public void out() {
        waiting.addFirst(playing.getLast());
        playing.removeLast();
    }
    //remove do parquinho
    public Kid remove(String name) {
        Kid kid = remove_kid(name, waiting);
        if(kid != null)
            return kid;
        kid = remove_kid(name, playing);
        if(kid != null)
            return kid;
        return null;
    }

    public String toString() {
        String saida = "=> ";
        for(Kid kid : waiting)
            saida += kid + " ";
        saida += "=> [ ";
        for(Kid kid : playing)
            saida += kid + " ";
        return saida + "]";
    }
}

//!KEEP
class Solver {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Trampoline tramp = new Trampoline();
        while(true) {
            String line = scanner.nextLine();
            System.out.println("$"+ line);
            String[] ui = line.split(" ");
            if(ui[0].equals("end")) {
                break;
            } else if(ui[0].equals("arrive")) { // name age
                tramp.arrive(new Kid(ui[1], Integer.parseInt(ui[2]))) ;
            } else if(ui[0].equals("in")) {
                tramp.in();
            } else if(ui[0].equals("out")) {
                tramp.out();
            } else if(ui[0].equals("remove")) {//name
                tramp.remove(ui[1]);
            } else if(ui[0].equals("show")) {
                System.out.println(tramp);
            } else {
                System.out.println("fail: comando invalido");
            }
        }
        scanner.close();
    }
}

class Manual {
    public static void main(String[] args) {
        
        //case unico
        Trampoline trampoline = new Trampoline();
        trampoline.arrive(new Kid("mario", 5));
        trampoline.arrive(new Kid("livia", 4));
        trampoline.arrive(new Kid("luana", 3));
        System.out.println(trampoline);
        //=> luana:3 livia:4 mario:5 => [ ]

        //case entrando
        trampoline.in();
        System.out.println(trampoline);
        //=> luana:3 livia:4 => [ mario:5 ]
        trampoline.in();
        System.out.println(trampoline);
        //=> luana:3 => [ livia:4 mario:5 ]

        //case saindo
        trampoline.out();
        System.out.println(trampoline);
        //=> mario:5 luana:3 => [ livia:4 ]
    }
}
//!OFF