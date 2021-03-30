import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;

class AgException extends RuntimeException {
    public AgException(String message){
        super(message);
    }
}

class Client {
    private String codename;
    private int limite;
    private int balance;

    public Client(String codename, int limite) {
        this.codename = codename;
        this.limite = limite;
        this.balance = 0;
    }
    public String toString() {
        return this.codename + ":" + this.balance + "/" + this.limite;
    }
    String getName() {
        return this.codename;
    }
    void setName(String codename) {
        this.codename = codename;
    }
    int getLimite() {
        return this.limite;
    }
    void setLimite(int limite) {
        this.limite = limite;
    }
    int getBalance() {
        return this.balance;
    }
    void setBalance(int balance) {
        this.balance = balance;
    }
}

class Transaction {
    private int id;
    private String codename;
    private int value;
    public Transaction(int id, String codename, int value) {
        this.id = id;
        this.codename = codename;
        this.value = value;
    }
    public String toString() {
        return "id:" + id + " " + codename + ":" + value;
    }

    int getId() {
        return this.id;
    }
    void setId(int id) {
        this.id = id;
    }
    String getCodename() {
        return this.codename;
    }
    void setCodename(String codename) {
        this.codename = codename;
    }
    int getValue() {
        return this.value;
    }

    void setValue(int value) {
        this.value = value;
    }
}

class Agiota {
    private Map<String, Client> repCli;
    private Map<Integer, Transaction> repTr;
    private int nextTrId;
    private int balance;

    //Guarda a transação e incrementa o nextTrId
    private void pushTransaction(String codename, int value) {
        repTr.put(nextTrId, new Transaction(nextTrId, codename, value));
        nextTrId++;
    }
    //Inicia os atributos
    public Agiota(int balance) {
        this.balance = balance;
        this.repCli = new TreeMap<>();
        this.repTr = new TreeMap<>();
        this.nextTrId = 0;
    }
    //Retorna o cliente ou lança uma exceção se não houver
    public Client getClient(String codename) {
        Client client = repCli.get(codename);
        if(client == null)
            throw new AgException("fail: cliente nao existe");
        return client;
    }
    //Guarda um cliente usando o codenome como chave
    public void addClient(String codename, int limite) {
        Client client = repCli.get(codename);
        if(client != null)
            throw new AgException("fail: cliente ja existe");
        repCli.put(codename, new Client(codename, limite));
    }
    //Empresta dinheiro para o cliente se ainda estiver no limite de crédito do cliente e o agiota possuir
    public void lends(String codename, int value) {
        if(value > this.balance)
            throw new AgException("fail: fundos insuficientes"); 
        Client cliente = this.getClient(codename);
        if(cliente.getBalance() + value > cliente.getLimite())
            throw new AgException("fail: limite excedido");
        cliente.setBalance(cliente.getBalance() + value);
        this.balance -= value;
        pushTransaction(codename, value);
    }
    //Recebe dinheiro do cliente, porém não mais do que a dívida
    public void receive(String codename, int value) {
        Client cliente = getClient(codename);
        if(value > cliente.getBalance())
            throw new AgException("fail: valor maior que a divida");
        cliente.setBalance(cliente.getBalance() - value);
        this.balance += value;
        pushTransaction(codename, -value);
    }
    //Remove o cliente e todas as transações relacionadas a ele
    //Crie uma lista auxiliar com todos os índices a serem removidos
    //e depois remova-os em outro laço. 
    //Sem lista auxiliar, você terá um erro de concorrência
    public void kill(String codename) {
        repCli.remove(codename);
        repTr.entrySet().removeIf(pair -> pair.getValue().getCodename().equals(codename));
    }
    //Utilize os métodos toString de Cliente e Transação para montar a saída
    public String toString() {
        String saida = "clients:\n";
        for(Client client : repCli.values())
            saida += "- " + client + "\n";
        saida += "transactions:\n";
        for(Transaction tr : repTr.values())
            saida += "- " + tr + "\n";
        saida += "balance: " + balance + "\n";
        return saida.substring(0, saida.length() - 1); //remove last \n
    }
    int getBalance(){
        return balance;
    }
}

//!KEEP
class Solver{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Agiota agiota = new Agiota(0);
        while(true){
            String line = scanner.nextLine();
            System.out.println("$" + line);
            String ui[] = line.split(" ");
            try{
                if(ui[0].equals("end")) {
                    break;
                } else if(ui[0].equals("init")) {
                    agiota = new Agiota(Integer.parseInt(ui[1]));
                } else if(ui[0].equals("addCli")) {
                    agiota.addClient(ui[1], Integer.parseInt(ui[2]));
                } else if(ui[0].equals("show")) {
                    System.out.println(agiota);
                } else if(ui[0].equals("kill")) {
                    agiota.kill(ui[1]);
                } else if(ui[0].equals("lend")) {
                    agiota.lends(ui[1], Integer.parseInt(ui[2]));
                } else if(ui[0].equals("receive")) {
                    agiota.receive(ui[1], Integer.parseInt(ui[2]));
                } else {
                    System.out.println("fail: comando invalido");
                }
            } catch (AgException ex) {
                System.out.println(ex.getMessage());
            }
        }
        scanner.close();
    }
}
//!OFF
