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

class Item {
    String descricao;
    int volume;

    Item(String descricao, int volume) {
        this.descricao = descricao;
        this.volume = volume;
    }

    public String toString() {
        return "Descricao:" + descricao + "\nVolume: " + volume + "\n";
    }
}



class Porco{
    String itens = "";
    double valor = 0;
    int volume = 0;
    int volumeMax;
    boolean estaQuebrado = false;
    
    Porco(int volumeMax) {
        this.volumeMax = volumeMax;
    }

    boolean addMoeda(Moeda moeda){
        if(estaQuebrado == true){
            System.out.println("O porco esta quebrado");
            return false;
        }
        if(moeda.volume + this.volume > this.volumeMax){
            System.out.println("Porco esta lotado");
            return false;
        }
        this.valor += moeda.valor;
        this.volume += moeda.volume;
        return true;
    }

    boolean addItem(Item item) {
        if(estaQuebrado == true){
            System.out.println("O porco esta quebrado");
            return false;
        }
        if(this.volume + item.volume > volumeMax) {
            System.out.println("Objeto insuportável no cofre");
            return false;
        }
        this.volume += item.volume;
        if(this.itens.equals(""))
            this.itens = item.descricao;            
        else
            this.itens += ", " + item.descricao;
        return true;
    }   
    boolean quebrar(){
        if(estaQuebrado){
            System.out.println("O cofre ja esta quebrado");
            return false;
        }
        estaQuebrado = true;
        return true;
    }
   double pegarMoedas(){
        if(!estaQuebrado){
            System.out.println("Voce deve quebrar o cofre primeiro");
            return 0.0f;
        }
        double aux = this.valor;
        this.valor = 0;
        return aux;
    }

    String pegarItens(){
        if(!estaQuebrado){
            System.out.println("Voce deve quebrar o cofre primeiro");
            return "";
        }
        String aux = this.itens;
        this.itens = "";
        return aux;
    }
    public String toString() {
        return "I:(" + itens + ") M:" + valor +" V:" + volume + "/" + volumeMax + " EQ:" + estaQuebrado;
    }
}

public class Solver{
    public static void main(String[] args) {
        Porco porco = new Porco(20);
        System.out.println(porco); //I:() M:0 V:0/20 EQ:false
        porco.addMoeda(Moeda.M10);
        porco.addMoeda(Moeda.M50);
        System.out.println(porco); //I:() M:60 V:4/20 EQ:false

        porco.addItem(new Item("ouro", 3));
        System.out.println(porco); //I:(ouro) M:60 V:7/20 EQ:false

        porco.addItem(new Item("passaporte", 2));
        System.out.println(porco); //I:(ouro, passaporte) M:60 V:9/20 EQ:false

        porco.pegarItens();  //Voce deve quebrar o cofre primeiro
        porco.pegarMoedas(); //Voce deve quebrar o cofre primeiro
        System.out.println(porco); //I:(ouro, passaporte) M:60 V:9/20 EQ:false

        porco.quebrar();
        porco.quebrar();

        System.out.println(porco.pegarItens());  //ouro, passaporte
        System.out.println(porco.pegarMoedas()); //60.0
        System.out.println(porco); //I:() M:0 V:9/20 EQ:true
    }
}