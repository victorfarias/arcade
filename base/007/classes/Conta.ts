import { Financas } from "./Financas.ts"
import { Label } from "./Label.ts"

export class Conta {
    //O número da conta
    private id=0;
    private  financas:Financas;
    constructor(id:number){
        this.id = id
        this.financas = new Financas()
    }
    //só realiza a operação se houver dinheiro suficiente na conta
    public sacar(value:number):boolean{return true}
    //retira o dinheiro, mesmo que o saldo fique negativo
    public tarifar(value:number):boolean{return true}
    //se o índice for válido e representar uma operação de tarifa
    //adicione o mesmo valor tarifado, mas com label de extorno
    public extornar(indice:number):boolean{return true;}
    //adiciona valor à conta
    public creditar(label:Label, value:number):boolean{return true}
    public toString():string{return ""}
    getFinancas():Financas{return this.financas}
}