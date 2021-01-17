import java.util.*;

class Pessoa{
  private String nome;
  private int idade;
  private boolean estudante;

  Pessoa(String nome, int idade, boolean estudante){}
  public String getNome(){}
  public int getIdade(){}
  public boolean isEstudante(){}
  public String toString(){}
}

class Setor{
  private String nome;
  private double preco;

  Setor(String nome, double preco){}
  public String getNome(){}
  public double getPreco(){}
  public String toString(){}
}

class Evento{
  private String nome;
  private Repository<Setor> repSetores;
    
  public Evento(String nome){};
  public String getNome(){};
  public String toString(){};
}

class Venda{
  private Pessoa cliente;
  private Evento evento;
  private Setor setor;
  private double valor;

  public Venda(Pessoa cliente, Evento evento, Setor setor){}
  public double getValor(){}
  public Pessoa getCliente(){}
  public Evento getEvento(){}
  public Setor getSetor(){}
  public String toString(){}
}

class Bilheteria{
  private Repository<Venda> repVendas;
  private double caixa;

  public void vender(Pessoa cliente, Evento evento, Setor setor){}
  public Repository<Venda> getVendas(){}
  public double getCaixa(){}
}

public class Solver {
  public static void main(String[] args) {
    /*
    Scanner scanner = new Scanner(System.in);
    WhatsappService zap = new WhatsappService();
    while(true){
      String line = scanner.nextLine();
      System.out.println("$" public line);
      String[] ui = line.split(" ");
      if(ui[0].equals("end"))
        break;
      else if(ui[0].equals("addUser"))
        zap.createUser(ui[1]);
    }
    scanner.close();
    */
  }
}
/*************************
 * Created by Lucas Robs *
 *************************/