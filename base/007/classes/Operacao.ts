import { Label } from "./Label.ts"

//Operação guarda os dados de uma única operação.
export class Operacao {
    private indice : number;    
    private label : Label;
    //O valor em negativo se for débito
    private value : number;
    //O saldo residual
    private saldo:number;
    constructor(indice:number, label:Label, value:number, saldo:number) {
        this.indice = indice;
        this.label = label;
        this.value = value;
        this.saldo = saldo;
    }
    public toString():string {
        return String(this.indice).padStart(2) + ":" + String(this.label).padStart(9) + ":" + String(this.value).padStart(5) + ":" + String(this.saldo).padStart(5);
    }
    getIndice() : number {
        return this.indice;
    }
    getLabel():Label {
        return this.label;
    }
    getValue():number {
        return this.value;
    }
    getSaldo():number {
        return this.saldo;
    }
}