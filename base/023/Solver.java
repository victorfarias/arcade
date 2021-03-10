import java.util.*;


abstract class Funcionario {
    String nome;
    int bonus;

    Funcionario(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
    public void setBonus(int bonus) {
      this.bonus = bonus;
    }
}

class Professor extends Funcionario {
    String classe;
    int maxDiaria = 2;
    int diarias;

    Professor(String nome, String classe) {
        super(nome);
        this.classe = classe;
    }

    public String getClasse() {
        return classe;
    }

    public int getSalario() {
        int salario;
        switch (classe) {
        case "A":
            salario = 3000;
            break;
        case "B":
            salario = 5000;
            break;
        case "C":
            salario = 7000;
            break;
        case "D":
            salario = 9000;
            break;
        case "E":
            salario = 11000;
            break;
        default:
            salario = 0;
        }
        return salario + (diarias * 100) + bonus;
    }

    void addDiaria() {
        if (diarias < maxDiaria)
            diarias++;
        else
            System.out.println("fail: limite de diarias atingido");
    }

    @Override
    public String toString() {
        return "prof:" + getNome() + ":" + getClasse() + ":" + this.getSalario();
    }
}

class STA extends Funcionario {
    int nivel;
    int maxDiaria = 1;
    int diarias;

    STA(String nome, int nivel) {
        super(nome);
        this.nivel = nivel;
    }

    public int getNivel() {
        return nivel;
    }

    public int getSalario() {
        return 3000 + 300 * this.nivel + (diarias * 100) + bonus;
    }

    void addDiaria() {
        if (diarias < maxDiaria)
            diarias++;
        else
            System.out.println("fail: limite de diarias atingido");
    }

    @Override
    public String toString() {
        return "sta:" + getNome() + ":" + getNivel() + ":" + getSalario();
    }
}

class Tercerizado extends Funcionario {
    int horas;
    boolean isSalubre = false;

    Tercerizado(String nome, int horas, String isSalubre) {
        super(nome);
        this.horas = horas;
        if(isSalubre.equals("sim"))this.isSalubre = true;
    }

    public int getHoras() {
        return horas;
    }

    public String getIsSalubre() {
        if(isSalubre)return "sim";
        return "nao";
    }

    public int getSalario() {
        if (isSalubre)
            return (4 * horas) + 500 + bonus;
        else
            return 4 * horas + bonus;
    }

    @Override
    public String toString() {
        return "ter:" + getNome() + ":" + getHoras() + ":" + getIsSalubre() + ":" + getSalario();
    }
}

class UFC {
    TreeMap<String, Funcionario> funcionarios = new TreeMap<String, Funcionario>();

    public String Show() {
        StringBuilder lista = new StringBuilder();
        for (Funcionario funcionario : funcionarios.values()) {
            lista.append(funcionario.toString() + "\n");
        }
        return lista.toString();
    }

    public String Show(String nome) {
        return funcionarios.get(nome).toString();
    }

    public void addFuncionario(Funcionario funcionario){
        funcionarios.put(funcionario.getNome(), funcionario);
    }

    public void rmFuncionario(String nome){
        funcionarios.remove(nome);
    }

    public void setBonus(int bonus){
        int bonificacao = bonus/funcionarios.size();
        for(Funcionario fuc : funcionarios.values()){
            fuc.setBonus(bonificacao);
        }
    }

    public void addDiaria(String nome) {
        if (funcionarios.get(nome) instanceof Professor) {
            Professor prof = (Professor) funcionarios.get(nome);
            prof.addDiaria();
        } else if (funcionarios.get(nome) instanceof STA) {
            STA sta = (STA) funcionarios.get(nome);
            sta.addDiaria();
        } else {
            System.out.println("fail: terc nao pode receber diaria");
        }
    }
}

class Solver {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UFC ufc = new UFC();

        while (true) {
            String line = scanner.nextLine();
            System.out.println("$" + line);
            String ui[] = line.split(" ");
            if (ui[0].equals("end"))
                break;
            else if (ui[0].equals("addProf")) {
                ufc.addFuncionario(new Professor(ui[1], ui[2]));
            }else if (ui[0].equals("addSta")) {
                ufc.addFuncionario(new STA(ui[1], Integer.parseInt(ui[2])));
            }else if (ui[0].equals("addTer")) {
                ufc.addFuncionario(new Tercerizado(ui[1], Integer.parseInt(ui[2]), ui[3]));
            }else if (ui[0].equals("rm")) {
                ufc.rmFuncionario(ui[1]);
            }else if (ui[0].equals("show")) {
                if(ui.length > 1)System.out.println(ufc.Show(ui[1]));
                else System.out.print(ufc.Show());
            }else if (ui[0].equals("addDiaria")) {
                ufc.addDiaria(ui[1]);
            }else if (ui[0].equals("setBonus")) {
                ufc.setBonus(Integer.parseInt(ui[1]));
            }else{
                System.out.print("comando invalido");
            }
        }
    }
}
                // $addProf david C
                // $show
                // $rm elvis
                // $addDiaria
                // $show david
                // $setBonus 600