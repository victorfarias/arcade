import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Client {
    private String id;
    private String fone;
    public Client(String id, String fone) {
        this.id = id;
        this.fone = fone;
    }

    @Override
    public String toString() {
        return id + ":" + fone;
    }

    String getId() {
        return this.id;
    }

    void setId(String id) {
        this.id = id;
    }

    String getFone() {
        return this.fone;
    }

    void setFone(String fone) {
        this.fone = fone;
    }
}

class Sala{
    private List<Client> cadeiras;

    List<Client> getCadeiras() {
        return this.cadeiras;
    }

    public Sala(int capacidade) {
        cadeiras = new ArrayList<Client>();
        for(int i = 0; i < capacidade; i++)
            cadeiras.add(null);
    }

    public boolean reservar(String id, String fone, int ind) {
        if((ind >= this.cadeiras.size()) ||(ind < 0)) {
            System.out.println("fail: indice invalido");
            return false;
        }
        if(this.cadeiras.get(ind) != null) {
            System.out.println("fail: cadeira ja esta ocupada");
            return false;
        }
        for(Client cliente : this.cadeiras) {
            if ((cliente != null) && (cliente.getId().equals(id))) {
                System.out.println("fail: cliente ja esta no cinema");
                return false;
            }
        }

        this.cadeiras.set(ind, new Client(id, fone));
        return true;
    }

    public void cancelar(String id) {
        for(int i = 0; i < this.cadeiras.size(); i += 1) {
            Client cliente = this.cadeiras.get(i);
            if((cliente != null) && (cliente.getId().equals(id))) {
                this.cadeiras.set(i, null);
                return;
            }
        }
        System.out.println("fail: cliente nao esta no cinema");
    }

    @Override
    public String toString() {
        String saida = "[ ";
        for (Client cliente : cadeiras) {
            if(cliente == null)
                saida += "- ";
            else
                saida += cliente + " ";
        }
        return saida + "]";
    }
}

//!KEEP
class Solver {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Sala sala = new Sala(0);
        while(true) {
            String line = scanner.nextLine();
            System.out.println("$" + line);
            String[] ui = line.split(" ");
            if(ui[0].equals("end")) {
                break;
            } else if(ui[0].equals("init")) {
                sala = new Sala(Integer.parseInt(ui[1]));
            } else if(ui[0].equals("show")) {
                System.out.println(sala);
            } else if(ui[0].equals("reservar")) {
                sala.reservar(ui[1], ui[2], Integer.parseInt(ui[3]));
            } else if(ui[0].equals("cancelar")) {
                sala.cancelar(ui[1]);
            } else {
                System.out.println("fail: comando invalido");
            }
        }
    }
}

class Manual {
    public static void main(String[] args) {
        Sala cinema = new Sala(0);
        System.out.println(cinema);
        // [ ]
        cinema = new Sala(5);
        System.out.println(cinema);
        // [ - - - - - ]
        cinema = new Sala(4);
        System.out.println(cinema);
        // [ - - - - ]
        cinema.reservar("davi", "3232", 0);
        cinema.reservar("joao", "3131", 3);
        System.out.println(cinema);
        // [ davi:3232 - - joao:3131 ]
        cinema.reservar("rute", "3030", 0);
        // fail: cadeira ja esta ocupada
        cinema.reservar("davi", "3234", 2);
        // fail: cliente ja esta no cinema
        cinema.cancelar("davi");
        System.out.println(cinema);
        // [ - - - joao:3131 ]
        cinema.cancelar("rita");
        // fail: cliente nao esta no cinema
        System.out.println(cinema);
        // [ - - - joao:3131 ]
    }
}
//!OFF