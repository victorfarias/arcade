import {
    assertEquals,
} from "https://deno.land/std@0.99.0/testing/asserts.ts";

import {Conta} from "./classes/Conta.ts"
//import {Financas} from "./classes/Financas.ts"
import {Label} from "./classes/Label.ts"
//import {Operacao} from "./classes/Operacao.ts"

let conta : Conta
  
Deno.test("iniciar", () => {
    conta = new Conta(100);
    const printStr = conta.toString();
    assertEquals(printStr, "conta:100 saldo:0");
    //assertArrayIncludes([1, 2, 3, 4, 5, 6], [3], "Expected 3 to be in the array");
});

Deno.test("depositar", () => {
    conta.creditar(Label.deposito, 100);
    const res2 = conta.creditar(Label.deposito, -10);
    assertEquals(res2, false);
    const printStr2 = conta.toString();
    assertEquals(printStr2, "conta:100 saldo:100");
});


Deno.test("debito", () => {
    conta.sacar(20);
    conta.tarifar(10);

    const printStr3 = conta.toString();
    assertEquals(printStr3, "conta:100 saldo:70");


    const status1 = conta.sacar(150);
    assertEquals(status1, false);

    conta.sacar(30);
    conta.tarifar(5);
    conta.creditar(Label.deposito, 5);
    conta.tarifar(1);

    const printStr4 = conta.toString();
    assertEquals(printStr4, "conta:100 saldo:39");
});


Deno.test("extrato", () => {
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
    const operacoes = [
        " 0: abertura:    0:    0",
        " 1: deposito:  100:  100",
        " 2:    saque:  -20:   80",
        " 3:   tarifa:  -10:   70",
        " 4:    saque:  -30:   40",
        " 5:   tarifa:   -5:   35",
        " 6: deposito:    5:   40",
        " 7:   tarifa:   -1:   39"
    ]
    const extratoArr = conta.getFinancas().getExtrato();
    assertEquals(extratoArr.length, 8);

    extratoArr.forEach((op, id) =>{
        assertEquals(op.toString(), operacoes[id]);
    })

})

Deno.test("extratoN 2", () => {
    const operacoes = [
        " 6: deposito:    5:   40",
        " 7:   tarifa:   -1:   39"
    ]
    const extratoArr = conta.getFinancas().getExtratoN(2);
    assertEquals(extratoArr.length, 2);

    extratoArr.forEach((op, id) =>{
        assertEquals(op.toString(), operacoes[id]);
    })

})

Deno.test("extornar", () => {
    //fail: indice 1 nao e tarifa
    //fail: indice 50 invalido

    const r1 = conta.extornar(1)
    const r50 = conta.extornar(50)

    
    assertEquals(r1, false)
    assertEquals(r50, false)
})


Deno.test("novo extrato", () => {
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
    const operacoes = [
        " 0: abertura:    0:    0",
        " 1: deposito:  100:  100",
        " 2:    saque:  -20:   80",
        " 3:   tarifa:  -10:   70",
        " 4:    saque:  -30:   40",
        " 5:   tarifa:   -5:   35",
        " 6: deposito:    5:   40",
        " 7:   tarifa:   -1:   39",
        " 8:  extorno:    5:   44",
        " 9:  extorno:    1:   45"
    ]
    const extratoArr = conta.getFinancas().getExtrato();
    assertEquals(extratoArr.length, 10);

    extratoArr.forEach((op, id) =>{
        assertEquals(op.toString(), operacoes[id]);
    })

})


Deno.test("novo extratoN 2", () => {
    conta.tarifar(50)

    const operacoes = [
        " 9:  extorno:    1:   45",
        "10:   tarifa:  -50:   -5"
    ]
    const extratoArr = conta.getFinancas().getExtratoN(2);
    assertEquals(extratoArr.length, 2);

    extratoArr.forEach((op, id) =>{
        assertEquals(op.toString(), operacoes[id]);
    })

})