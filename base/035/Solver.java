import java.util.*;

class Pessoa {
    public String nome;
    public boolean meia;

    Pessoa(String nome, boolean meia) {
        this.nome = nome;
        this.meia = meia;
    }

    public String toString() {
        return "[" + nome + ", " + (meia ? "meia" : "inteira") + "]";
    }
}

class Setor {
    private String nome;
    private double preco;
    private int qtd;
    private int capacidade;

    Setor(String nome, double preco, int capacidade) {
        this.nome = nome;
        this.preco = preco;
        this.capacidade = capacidade;
    }

    public String getNome() {
        return this.nome;
    }

    public double getPreco() {
        return this.preco;
    }

    public void vender(){
        this.qtd += 1;
    }

    public String toString() {
        return "[" + nome + ":" + preco + ":" + this.qtd + "/" + capacidade + "]";
    }
}

class Evento {
    private String nome;
    private TreeMap<String, Setor> repSetores = new TreeMap<String, Setor>();

    public Evento(String nome) {
        this.nome = nome;
    };

    public String getNome() {
        return this.nome;
    };

    public void addSetor(Setor setor) {
        repSetores.put(setor.getNome(), setor);
    };

    public TreeMap<String, Setor> getSetores() {
        return repSetores;
    }

    public String toString(){
        String output = this.nome + "\n";
        for(Setor setor : this.repSetores.values())
            output += "- " + setor + "\n";
        return output;
    }
}

class Venda {
    private Pessoa cliente;
    private Evento evento;
    private Setor setor;
    private double valor;

    public Venda(Pessoa cliente, Evento evento, Setor setor) {
        this.cliente = cliente;
        this.evento = evento;
        this.setor = setor;
    }

    public String toString() {
        return "[" + cliente.nome + ", " + evento.getNome() + ", " + setor.getNome() + "]";
    }
}

class Bilheteria {
    private ArrayList<Venda> repVenda = new ArrayList<Venda>();
    private TreeMap<String, Pessoa> repPessoas = new TreeMap<String, Pessoa>();
    private TreeMap<String, Evento> repEvento = new TreeMap<String, Evento>();
    private double caixa;

    private Pessoa getCliente(String idCliente){
        Pessoa pessoa = repPessoas.get(idCliente);
        if(pessoa != null)
            return pessoa;
        throw new IllegalArgumentException("fail: cliente " + idCliente + " nao existe");
    }

    private Evento getEvento(String idEvento){
        Evento evento = repEvento.get(idEvento);
        if(evento != null)
            return evento;
        throw new IllegalArgumentException("fail: evento " + idEvento + " nao existe");
    }

    private Setor getSetor(Evento evento, String idSetor){
        Setor setor = evento.getSetores().get(idSetor);
        if(setor != null)
            return setor;
        throw new IllegalArgumentException("fail: setor " + idSetor + " nao existe");
    }

    public void vender(String idCliente, String idEvento, String idSetor) {
        Pessoa cliente = this.getCliente(idCliente);
        Evento evento = this.getEvento(idEvento);
        Setor setor = this.getSetor(evento, idSetor);
        double valor = cliente.meia ? setor.getPreco() / 2 : setor.getPreco();
        setor.vender();
        caixa += valor;
        repVenda.add(new Venda(cliente, evento, setor));
    }

    public double getCaixa() {
        return caixa;
    }

    public String showVendas() {
        StringBuilder vendas = new StringBuilder();
        for (Venda venda : repVenda)
            vendas.append(venda + "\n");
        return vendas.toString();
    }

    public String showPessoas() {
        StringBuilder pessoas = new StringBuilder();
        for (Pessoa pessoa : repPessoas.values())
            pessoas.append(pessoa + "\n");
        return pessoas.toString();
    }

    public String showEventos() {
        StringBuilder eventos = new StringBuilder();
        for (Evento evento : repEvento.values())
            eventos.append(evento);
        return eventos.toString();
    }

    public void addPessoa(Pessoa pessoa) {
        if (repPessoas.get(pessoa.nome) != null) {
            System.out.println("fail: pessoa " + pessoa.nome + " ja existe");
            return;
        }
        repPessoas.put(pessoa.nome, pessoa);
    }

    public void addEvento(Evento evento) {
        repEvento.put(evento.getNome(), evento);
    }

    public void addSetor(String idEvento, Setor setor) {
        repEvento.get(idEvento).addSetor(setor);
    }
}

public class Solver {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bilheteria bilheteria = new Bilheteria();
        while (true) {
            try{
                String line = scanner.nextLine();
                System.out.println("$" + line);
                String[] ui = line.split(" ");
                if (ui[0].equals("end"))
                    break;
                else if (ui[0].equals("addPessoa")) {
                    bilheteria.addPessoa(new Pessoa(ui[1], ui[2].equals("meia")));
                } else if (ui[0].equals("addEvento")) {
                    bilheteria.addEvento(new Evento(ui[1]));
                } else if (ui[0].equals("addSetor")) {
                    bilheteria.addSetor(ui[1], new Setor(ui[2], Double.parseDouble(ui[3]), Integer.parseInt(ui[4])));
                } else if (ui[0].equals("vender")) {
                    bilheteria.vender(ui[1], ui[2], ui[3]);
                } else if (ui[0].equals("eventos")) {
                    System.out.print(bilheteria.showEventos());
                } else if (ui[0].equals("pessoas")) {
                    System.out.print(bilheteria.showPessoas());
                } else if (ui[0].equals("vendas")) {
                    System.out.print(bilheteria.showVendas());
                    System.out.println("R$ " + bilheteria.getCaixa());
                } else {
                    System.out.println("fail: comando invalido");
                }

            }catch(RuntimeException e){
                System.out.println(e.getMessage());
            }
        }
        scanner.close();
    }
}
/*************************
 * Created by Lucas Robs *
 *************************/