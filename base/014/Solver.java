import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Fone {
    private String id;
    private String number;
    public Fone(String id, String number) {
        this.id = id;
        this.number = number;
    }

    //verifica se o número é um número de telefone válido
    public static boolean validate(String number) {
        String validos = "()-.0123456789";
        for(int i = 0; i < number.length(); i++)
            if(!validos.contains("" + number.charAt(i)))
                return false;
        return true;
    }

    //O resultado deve ficar assim
    //oi:1234
    @Override
    public String toString() {
        return getId()+ ":" + getNumber();
    }

    //GETS e SETS
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getNumber() {
        return this.number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
}

class Contact {
    private String name;
    private List<Fone> fones;

    //Crie um ArrayList para o ATRIBUTO fones
    //Se a variável fones não for null, adicione todos os fones usando o método addFone
    public Contact(String name, List<Fone> fones) {
        this.name = name;
        this.fones = new ArrayList<>();
        if(fones != null)
        //     for(Fone fone : fones)
        //         this.addFone(fone);
            fones.stream().forEach((fone) -> {this.addFone(fone);});
    }

    //Se fone for válido, insira no atributo fones
    //Se não, informe o erro
    public void addFone(Fone fone){
        if(Fone.validate(fone.getNumber()))
            this.fones.add(fone);
        else
            System.out.println("fail: invalid number");
    }

    //Se o índice existir no ArrayList, remova o telefone com esse índice
    public void rmFone(int index){
        if(index < 0 || index >= (int) fones.size())
            return;
        fones.remove(index);
    }

    //Use um contador para mostrar o índice do telefone
    //Use o toString do fone para adicioná-lo à saída
    //O resultado dever ficar assim:
    //david [0:oi:123] [1:tim:9081] [2:claro:5431]
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.getName());
        for(int i = 0; i < fones.size(); i++)
            sb.append( " [" + i + ":" + fones.get(i) + "]");
        return sb.toString();
    }

    //GETS e SETS
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<Fone> getFones() {
        return this.fones;
    }
}

public class Solver {
//STORE_ON
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Contact contact = new Contact("", null);
        while(true){
            String line = scanner.nextLine();
            System.out.println("$" + line);
            String ui[] = line.split(" ");
            if(ui[0].equals("end")) {
                break;
            } else if(ui[0].equals("init")) { //name
                contact = new Contact(ui[1], null);
            } else if(ui[0].equals("add")) {  //id fone
                contact.addFone(new Fone(ui[1], ui[2]));
            } else if(ui[0].equals("rm")) {   //index
                contact.rmFone(Integer.parseInt(ui[1]));
            } else if(ui[0].equals("show")) {
                System.out.println("- " + contact);
            } else {
                System.out.println("fail: invalid command");
            }
        }
        scanner.close();
    }
}
//STORE_OFF
