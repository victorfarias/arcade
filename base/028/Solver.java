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

  public int getIdade() {
    return this.idade;
  }

  public boolean isEstudante() {
    return this.estudante;
  }

  public String toString() {
    String stringEstudante = "nao";
    if(estudante == true){stringEstudante = "sim";}
    return "["+ nome +", "+ idade +", "+ stringEstudante +"]";
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
    return nome + " " + preco;
  }
}

class Evento {
  private String nome;
  private HashMap<String, Setor> repSetores = new HashMap<String, Setor>();

  public Evento(String nome) {

  };

  public String getNome() {
    return this.nome;
  };

  public String toString() {
    return null;
  };

  public void addSetor(Setor setor) {
  };

  public HashMap<String, Setor> getSetores() {
    return repSetores;
  }
}

class Venda {
  private Pessoa cliente;
  private Evento evento;
  private Setor setor;
  private double valor;

  public Venda(Pessoa cliente, Evento evento, Setor setor) {
  }

  public double getValor() {
    return this.valor;
  }

  public Pessoa getCliente() {
    return cliente;
  }

  public Evento getEvento() {
    return evento;
  }

  public Setor getSetor() {
    return setor;
  }

  public String toString() {
    return cliente + " " + evento + " " + setor + " " + valor;
  }
}

class Bilheteria {
  private ArrayList<Venda> repVenda = new ArrayList<Venda>();
  private HashMap<String, Pessoa> repPessoas = new HashMap<String, Pessoa>();
  private HashMap<String, Evento> repEvento = new HashMap<String, Evento>();
  private double caixa;

  public void vender(String idCliente, String idEvento, String idSetor) {
    Pessoa cliente = repPessoas.get(idCliente);
    Evento evento = repEvento.get(idEvento);
    Setor setor = evento.getSetores().get(idEvento);
    repVenda.add(new Venda(cliente, evento, setor));
  }

  public double getCaixa() {
    return caixa;
  }

  public String showVendas() {
    return "show";
  }

  public String showPessoas() {
    StringBuilder pessoas = new StringBuilder();
    for(Pessoa pessoa : repPessoas.values())
      pessoas.append(pessoa +",\n");
    if(pessoas.length() > 1){
      pessoas.deleteCharAt(pessoas.length()-2);
      pessoas.deleteCharAt(pessoas.length()-1);
    }
    return pessoas.toString();
  }

  public String showEventos() {
    return "show";
  }

  public String showSetores(String idEvento) {
    return "show";
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
      String line = scanner.nextLine();
      System.out.println("$" + line);
      String[] ui = line.split(" ");
      if (ui[0].equals("end"))
        break;
      else if (ui[0].equals("addPessoa")) {
        boolean estudante = false;
        if (ui[3].equals("sim")) estudante = true;
        bilheteria.addPessoa(new Pessoa(ui[1], Integer.parseInt(ui[2]), estudante));
      } else if (ui[0].equals("showP")) {
        System.out.println(bilheteria.showPessoas());
      }
    }
    scanner.close();
  }
}
/*************************
 * Created by Lucas Robs *
 *************************/