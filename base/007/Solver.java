import java.util.*;

interface Conta{
    void depositar(float value);
    void saque(float valor);
    void tarifa(float valor);
    void extrato();
    void extratoN(int valor);
    void extornar(int valor);
}

public class Solver {
    public static void main(String[] args) {
    Conta conta = new Conta(100);
    System.out.println(conta);
// conta:100 saldo:0
    conta.depositar(100);
    conta.depositar(-10);
// fail: valor invalido

    System.out.println(conta);
// conta:100 saldo:100
    conta.saque(20);
    conta.tarifa(10);
    System.out.println(conta);
// conta:100 saldo:70
    conta.saque(150);
// fail: saldo insuficiente
    conta.saque(30);
    conta.tarifa(5);
    conta.depositar(5);
    conta.tarifa(1);
    System.out.println(conta);
// conta:100 saldo:39
    for(Operacao op : conta.extrato())
        System.out.println(op);
/*
0: abertura:    0:    0
1: deposito:  100:  100
2:    saque:  -20:   80
3:   tarifa:  -10:   70
4:    saque:  -30:   40
5:   tarifa:   -5:   35
6: deposito:    5:   40
7:   tarifa:   -1:   39
*/
    for(Operacao op : conta.extratoN(2))
            System.out.println(op);
/*
6: deposito:    5:   40
7:   tarifa:   -1:   39
*/
    for(int id : Arrays.asList(1, 5, 7, 50))
        conta.extornar(id);
/*
fail: indice 1 nao e tarifa
fail: indice 50 invalido
*/
    for(Operacao op : conta.extrato())
        System.out.println(op);
/*
0: abertura:    0:    0
1: deposito:  100:  100
2:    saque:  -20:   80
3:   tarifa:  -10:   70
4:    saque:  -30:   40
5:   tarifa:   -5:   35
6: deposito:    5:   40
7:   tarifa:   -1:   39
8:  extorno:    5:   44
9:  extorno:    1:   45
*/
    conta.tarifa(5);
    conta.extratoN(2);
/*
9:  extorno:    1:   45
10:   tarifa:  -50:   -5
*/
    }
}