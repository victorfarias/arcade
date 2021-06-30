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
    //faz o preenchimento da string com espaços em branco até completar o length
    public static pad(_string:string, _length:number):string {
        return _string.padStart(_length) //String.format("%1$"+length+ "s", string);
    }
    public toString():string {
        return Operacao.pad("" + this.indice, 2) + ":" + Operacao.pad("" + this.label, 9) + ":" + Operacao.pad("" + this.value, 5) + ":" + Operacao.pad("" + this.saldo, 5);
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