import java.util.Scanner;
import java.text.*;
class Calculator {
    public int batteryMax;
    public int battery;
    public float display;

    //Inicia os atributos, battery e display começam com o zero.
    public Calculator(int batteryMax) {
        this.batteryMax = batteryMax;
        this.battery = 0;
        this.display = 0.0f;
    }

    //Aumenta a bateria, porém não além do máximo.
    public void chargeBattery(int value) { 
        if(value < 0)
            return;
        this.battery += value;
        if(this.battery > this.batteryMax)
            this.battery = this.batteryMax;
    }

    //Tenta gastar uma unidade da bateria e emite um erro se não conseguir.
    public boolean useBattery() { 
        if(this.battery == 0){
            System.out.println("fail: bateria insuficiente");
            return false;
        }
        this.battery -= 1;
        return true;
    }

    //Se conseguir gastar bateria, armazene a soma no atributo display.
    public void sum(int a, int b) { 
        if(useBattery())
            this.display = (a + b);
    }

    //Se conseguir gastar bateria e não for divisão por 0.
    public void division(int num, int den) {
        if(!useBattery())
            return;
        if(den == 0){
            System.out.println("fail: divisao por zero");
        }
        else
            this.display = (float) num / den;
    }

    //Retorna o conteúdo do display com duas casas decimais.
    public String toString() { 
        DecimalFormat form = new DecimalFormat("0.00");
        return "display = " + form.format(this.display).replace(",",".") + ", battery = " + this.battery;
    }
}

//!KEEP
class Solver{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Calculator calc = new Calculator(0);
        while(true){
            String line = scanner.nextLine();
            System.out.println("$" + line);
            String ui[] = line.split(" ");
            if(line.equals("end")) {
                break;
            } else if(ui[0].equals("init")) { //batteryMax
                calc = new Calculator(Integer.parseInt(ui[1]));
            } else if(ui[0].equals("show")) {
                System.out.println(calc);
            } else if(ui[0].equals("charge")) {
                calc.chargeBattery(Integer.parseInt(ui[1]));
            } else if(ui[0].equals("sum")) {//value value
                calc.sum(Integer.parseInt(ui[1]), Integer.parseInt(ui[2]));
            } else if(ui[0].equals("div")) {//value value
                calc.division(Integer.parseInt(ui[1]), Integer.parseInt(ui[2]));
            } else {
                System.out.println("fail: comando invalido");
            }
        }
        scanner.close();
    }
}
//!OFF

//!DEL
class Manual{
    public static void main(String[] args) {
        Calculator calc = new Calculator(5);
        System.out.println(calc);
        //display = 0.00, battery = 0
        calc.chargeBattery(3);
        System.out.println(calc);
        //display = 0.00, battery = 3
        calc.chargeBattery(1);
        System.out.println(calc);
        //display = 0.00, battery = 4
        calc.chargeBattery(2);
        System.out.println(calc);
        //display = 0.00, battery = 5

        calc = new Calculator(4);
        calc.chargeBattery(2);
        System.out.println(calc);
        //display = 0.00, battery = 2
        calc.chargeBattery(3);
        System.out.println(calc);
        //display = 0.00, battery = 4

        calc = new Calculator(2);
        calc.chargeBattery(2);
        calc.sum(4, 3);
        System.out.println(calc);
        //display = 7.00, battery = 1
        calc.sum(2, 3);
        System.out.println(calc);
        //display = 5.00, battery = 0
        calc.sum(-4, -1);
        //fail: bateria insuficiente
        calc.chargeBattery(1);
        System.out.println(calc);
        //display = 5.00, battery = 1
        calc.sum(-4, -2);
        System.out.println(calc);
        //display = -6.00, battery = 0

        calc = new Calculator(3);
        calc.chargeBattery(3);
        calc.division(6, 3);
        calc.division(7, 0);
        //fail: divisao por zero
        System.out.println(calc);
        //display = 2.00, battery = 1
        calc.division(7, 2);
        calc.division(10, 2);
        //fail: bateria insuficiente
        System.out.println(calc);
        //display = 3.50, battery = 0
    }
}
//!OFF
