import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//!KEEP
//Essa enumeração guarda possíveis labels para as operações.
enum Label {
    saque("saque"), 
    deposito("deposito"), 
    tarifa("tarifa"), 
    extorno("extorno"), 
    abertura("abertura");
    
    private String name;
    //nas enums o Construtor tem que ser privado
    private Label(String name) {
        this.name = name;
    }
    String getName() {
        return this.name;
    }
    public String toString() {
        return this.name;
    }
}
//Operação guarda os dados de uma única operação.
class Operacao {
    private int indice;    
    private Label label;
    //O valor em negativo se for débito
    private int value;
    //O saldo residual
    private int saldo;
    public Operacao(int indice, Label label, int value, int saldo) {
        this.indice = indice;
        this.label = label;
        this.value = value;
        this.saldo = saldo;
    }
    //faz o preenchimento da string com espaços em branco até completar o length
    public static String pad(String string, int length) {
        return String.format("%1$"+length+ "s", string);
    }
    public String toString() {
        return pad("" + indice, 2) + ":" + pad("" + label, 9) + ":" + pad("" + value, 5) + ":" + pad("" + saldo, 5);
    }
    int getIndice() {
        return this.indice;
    }
    Label getLabel() {
        return this.label;
    }
    int getValue() {
        return this.value;
    }
    int getSaldo() {
        return this.saldo;
    }
};
//!OFF

//Finanças registra o saldo e guarda as movimentações financeiras
class Financas {
    //O id da próxima operação dessa conta
    private int nextId;
    //A lista de operações realizadas
    private List<Operacao> extrato;
    private int saldo;

    public Financas() {
        this.extrato = new ArrayList<Operacao>();
    }
    //Adiciona value ao saldo
    //Crie operação e adiciona ao vetor de operações
    //Incrementa o nextId
    public void addOperacao(Label label, int value) {
        saldo += value;
        extrato.add(new Operacao(nextId, label, value, saldo));
        nextId += 1;
    }

    int getSaldo() {
        return saldo;
    }

    List<Operacao> getExtrato() {
        return extrato;
    }

    public List<Operacao> getExtrato(int qtdOp) {
        List<Operacao> saida = new ArrayList<>();
        for(int i = (int) extrato.size() - qtdOp; i < (int) extrato.size(); i++)
            saida.add(extrato.get(i));
        return saida;
    }
}

class Conta {
    //O número da conta
    private int id;
    private Financas financas;

    public Conta(int id) {
        this.id = id;
        this.financas = new Financas();
        this.financas.addOperacao(Label.abertura, 0);
    }
    //só realiza a operação se houver dinheiro suficiente na conta
    public boolean sacar(int value) {
        if(value < 0){
            System.out.println("fail: valor invalido");
            return false;
        }
        if(value > this.financas.getSaldo()) {
            System.out.println("fail: saldo insuficiente");
            return false;
        }
        financas.addOperacao(Label.saque, -value);
        return true;
    }
    //retira o dinheiro, mesmo que o saldo fique negativo
    public boolean tarifar(int value) {
        financas.addOperacao(Label.tarifa, -value);
        return true;
    }
    //se o índice for válido e representar uma operação de tarifa
    //adicione o mesmo valor tarifado, mas com label de extorno
    public boolean extornar(int indice) {
        if(indice < 0 || indice >= (int) financas.getExtrato().size()) {
            System.out.println("fail: indice " + indice + " invalido");
            return false;
        }
        Operacao op = financas.getExtrato().get(indice);
        if(op.getLabel() != Label.tarifa) {
            System.out.println("fail: indice " + indice + " nao e tarifa");
            return false;
        }
        financas.addOperacao(Label.extorno, -op.getValue());
        return true;
    }
    //adiciona valor à conta
    public boolean creditar(Label label, int value) {
        if(value < 0){
            System.out.println("fail: valor invalido");
            return false;
        }
        financas.addOperacao(label, value);
        return true;
    }

    public String toString() {
        return "conta:" + this.id + " saldo:" + this.financas.getSaldo();
    }

    Financas getFinancas() {
        return this.financas;
    }
}

//!KEEP
class Solver{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Conta conta = new Conta(0);
        while(true){
            String line = scanner.nextLine();
            System.out.println("$" + line);
            String ui[] = line.split(" ");
            if(line.equals("end")) {
                break;
            } else if(ui[0].equals("show")) {
                System.out.println(conta);
            } else if(ui[0].equals("init")) {
                conta = new Conta(Integer.parseInt(ui[1]));
            } else if(ui[0].equals("saque")) {
                conta.sacar(Integer.parseInt(ui[1]));
            } else if(ui[0].equals("tarifa")) {
                conta.tarifar(Integer.parseInt(ui[1]));
            } else if(ui[0].equals("deposito")) {
                conta.creditar(Label.deposito, Integer.parseInt(ui[1]));
            } else if(ui[0].equals("extornar")) {
                for(int i = 1; i < ui.length; i++)
                    conta.extornar(Integer.parseInt(ui[i]));
            } else if(ui[0].equals("extrato")) {
                for(Operacao op : conta.getFinancas().getExtrato())
                    System.out.println(op);
            } else if(ui[0].equals("extratoN")) {
                for(Operacao op : conta.getFinancas().getExtrato(Integer.parseInt(ui[1])))
                    System.out.println(op);
            } else {
                System.out.println("fail: comando invalido");
            }
        }
        scanner.close();
    }
}
//!OFF
