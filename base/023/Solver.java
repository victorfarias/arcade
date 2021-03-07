import java.util.TreeMap;

class Funcionario{
  String nome;
  Funcionario(String nome){
    this.nome = nome;
  }
  public String getNome() {
    return nome;
  }
}
class Professor extends Funcionario{
  String classe;
  int maxDiaria = 2;
  int diarias;
  Professor(String nome, String classe){
    super(nome);
    this.classe = classe;
  }
  public String getClasse() {
    return classe;
  }
  public int getSalario(){
    int salario;
    switch (classe){
      case "A":
        salario = 3000;
      case "B":
        salario = 5000;
      case "C":
        salario = 7000;
      case "D":
        salario = 9000;
      case "E":
        salario = 11000;
      default:
        salario = 0;
    }
    return salario + (diarias * 100);
  }
  void addDiaria(){
    if(diarias < maxDiaria)diarias++;
    else System.out.println("fail: limite de diarias atingido");
  }
  @Override
  public String toString() {
    return "prof:"+getNome()+":"+getClasse()+":"+getSalario();
  }
}
class STA extends Funcionario{
  int nivel;
  int maxDiaria = 1;
  int diarias;
  STA(String nome, int nivel){
    super(nome);
    this.nivel = nivel;
  }
  public int getNivel() {
    return nivel;
  }
  public int getSalario(){
    return 3000 + 300 * this.nivel + (diarias * 100);
  }
  void addDiaria(){
    if(diarias < maxDiaria)diarias++;
    else System.out.println("fail: limite de diarias atingido");
  }
  @Override
  public String toString() {
    return "sta:"+getNome()+":"+getNivel()+":"+getSalario();
  }
}
class Tercerizado extends Funcionario{
  int horas;
  boolean isSalubre;
  Tercerizado(String nome){
    super(nome);
  }
  public int getHoras() {
    return horas;
  }
  public boolean getIsSalubre(){
    return isSalubre;
  }
  public int getSalario(){
    if(isSalubre) return (4 * horas) + 500;
    else return 4 * horas;
  }
  @Override
  public String toString() {
    return "ter:"+getNome()+":"+getHoras()+":"+getIsSalubre()+":"+getSalario();
  }
}
class UFC{
  TreeMap<String, Funcionario> funcionarios = new TreeMap<String, Funcionario>();
  public String Show(){
    String lista = new String();
    for(Funcionario funcionario: funcionarios.values()){
      if(funcionario instanceof Professor){
        lista += funcionario.toString();
      }else if(funcionario instanceof STA){
        lista += funcionario.toString();
      }else if(funcionario instanceof Tercerizado){
        lista += funcionario.toString();
      }
      lista += "\n";
    }
    return lista;
  }
  public void addDiaria(String nome){
    if(funcionarios.get(nome) instanceof Professor){
      Professor prof = (Professor)funcionarios.get(nome);
      prof.addDiaria();
    }else if(funcionarios.get(nome) instanceof STA){
      STA sta = (STA)funcionarios.get(nome);
      sta.addDiaria();
    }else{
      System.out.println("fail: terc nao pode receber diaria");
    }
  }
}
class Solver{

}