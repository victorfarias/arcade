import java.util.*;

class Pessoa {
    private String nome;
    private int idade;
    private boolean estudante;

    Pessoa(String nome, int idade, boolean estudante) {
        this.nome = nome;
        this.idade = idade;
        this.estudante = estudante;
    }

    public String getNome() {
        return this.nome;
    }

    public boolean isEstudante() {
        return this.estudante;
    }

    public String toString() {
        String stringEstudante = "nao";
        if (estudante == true) {
            stringEstudante = "sim";
        }
        return "[" + nome + ", " + idade + ", " + stringEstudante + "]";
    }
}

class Setor {
    private String nome;
    private double preco;

    Setor(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public String getNome() {
        return this.nome;
    }

    public double getPreco() {
        return this.preco;
    }

    public String toString() {
        return nome + ":" + preco;
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
        this.valor = setor.getPreco();
    }

    public String toString() {
        return "[" + cliente.getNome() + ", " + evento.getNome() + ", " + setor.getNome() + ", "
                + String.format("%.2f", valor) + "]";
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
        caixa += cliente.isEstudante() ? setor.getPreco() / 2 : setor.getPreco();
        repVenda.add(new Venda(cliente, evento, setor));
    }

    public double getCaixa() {
        return caixa;
    }

    public String showVendas() {
        StringBuilder vendas = new StringBuilder();
        for (Venda venda : repVenda)
            vendas.append(venda + ",\n");
        if (vendas.length() > 1) {
            vendas.delete(vendas.length() - 2, vendas.length());
        }
        return vendas.toString();
    }

    public String showPessoas() {
        StringBuilder pessoas = new StringBuilder();
        for (Pessoa pessoa : repPessoas.values())
            pessoas.append(pessoa + ",\n");
        if (pessoas.length() > 1) {
            pessoas.delete(pessoas.length() - 2, pessoas.length());
        }
        return pessoas.toString();
    }

    public String showEventos() {
        StringBuilder eventos = new StringBuilder();
        for (Evento evento : repEvento.values())
            eventos.append("[" + evento.getNome() + "],\n");
        if (eventos.length() > 1) {
            eventos.delete(eventos.length() - 2, eventos.length());
        }
        return eventos.toString();
    }

    public String showSetores(String idEvento) {
        StringBuilder setores = new StringBuilder();
        for (Setor setor : repEvento.get(idEvento).getSetores().values())
            setores.append("[" + setor.getNome() + "],\n");
        if (setores.length() > 1) {
            setores.delete(setores.length() - 2, setores.length());
        }
        return setores.toString();
    }

    public void addPessoa(Pessoa pessoa) {
        if (repPessoas.get(pessoa.getNome()) != null) {
            System.out.println("fail: pessoa " + pessoa.getNome() + " ja existe");
            return;
        }
        repPessoas.put(pessoa.getNome(), pessoa);
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
                    bilheteria.addPessoa(new Pessoa(ui[1], Integer.parseInt(ui[2]), ui[3].equals("sim")));
                } else if (ui[0].equals("addEvento")) {
                    bilheteria.addEvento(new Evento(ui[1]));
                } else if (ui[0].equals("addSetor")) {
                    bilheteria.addSetor(ui[1], new Setor(ui[2], Double.parseDouble(ui[3])));
                } else if (ui[0].equals("vender")) {
                    bilheteria.vender(ui[1], ui[2], ui[3]);
                } else if (ui[0].equals("showE")) {
                    System.out.println(bilheteria.showEventos());
                } else if (ui[0].equals("showS")) {
                    System.out.println(bilheteria.showSetores(ui[1]));
                } else if (ui[0].equals("showP")) {
                    System.out.println(bilheteria.showPessoas());
                } else if (ui[0].equals("showC")) {
                    System.out.println("R$ " + String.format("%.2f", bilheteria.getCaixa()));
                } else if (ui[0].equals("showV")) {
                    System.out.println(bilheteria.showVendas());
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