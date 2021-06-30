//Essa enumeração guarda possíveis labels para as operações.
export class Label {
    static saque = new Label("saque"); 
    static deposito = new Label("deposito");
    static tarifa = new Label("tarifa");
    static extorno = new Label("extorno");
    static abertura = new Label("abertura");
    
    private name = "";
    //nas enums o Construtor tem que ser privado
    constructor(name:string) {
        this.name = name;
    }
    getName() : string {
        return this.name;
    }
    public toString() : string {
        return this.name;
    }
}