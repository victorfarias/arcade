import { Operacao } from "./Operacao.ts"
import { Label } from "./Label.ts"

//Finanças registra o saldo e guarda as movimentações financeiras
export class Financas {
    //O id da próxima operação dessa conta
    private nextId = 0;
    //A lista de operações realizadas
    private extrato:Operacao[] = [];
    private saldo = 0;
    constructor(){}
    //Adiciona value ao saldo
    //Crie operação e adiciona ao vetor de operações
    //Incrementa o nextId
    public addOperacao(label:Label, value:number){}
    getSaldo() : number { return this.saldo; }
    _getExtrato() : Operacao[] { return this.extrato; }
    public getExtrato() : Operacao[]{ return this.extrato; }
    public getExtratoN(qtd:number) : Operacao[]{ return this.extrato; }
}