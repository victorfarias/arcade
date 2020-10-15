#include <iostream>
#include <sstream>
#include <iomanip>
#include <vector>
using namespace std;

struct Espiral{
    string nome;
    int qtd;
    float preco;
    Espiral(string nome = "empty", int qtd = 0, float preco = 0.0){
        this->nome = nome;
        this->qtd = qtd;
        this->preco = preco;    
    }
    friend ostream& operator<<(ostream& os, Espiral espiral){
        os << "[" << setw(8) << espiral.nome << " :"
                  << setw(2) << espiral.qtd << " U :"
                  << setw(5) << fixed << setprecision(2)
                  << espiral.preco << " RS]";
        return os;
    }
};

class Maquina{
    vector<Espiral> espirais;
    float saldo, lucro;
    int max_produtos;
public:
    Maquina(int qtd = 0, int max_produtos = 0):
        espirais(qtd){
        saldo = 0;
        lucro = 0;
        this->max_produtos = max_produtos;
    }
    Espiral& get(int indice){
        if((indice < 0)||(indice >= (int) espirais.size()))
            throw "fail: indice nao existe";
        return espirais[indice];
    }
    void set(int indice, string name, int qtd, float price){
        Espiral espiral(name, qtd, price);
        if(espiral.qtd > max_produtos)
            throw "fail: limite excedido";
        if(espiral.qtd < 0)
            throw "fail: valores invalidos";
        get(indice) = espiral;
    }
    void limpar(int indice){
        get(indice) = Espiral();
    }
    friend ostream& operator<<(ostream& os, Maquina maquina){
        os << "saldo: " << fixed << setprecision(2) << maquina.saldo << endl;
        int i = 0;
        for(auto& espiral : maquina.espirais){
            os << i << " " << espiral << endl;
            i += 1;
        }
        return os;
    }

    void inserirDinheiro(float value){
        if(value <= 0)
            throw "fail: fail: valores invalidos";
        saldo += value;
    }

    float pedirTroco(){
        auto troco = saldo;
        saldo = 0;
        return troco;
    }

    void comprar(int ind){
        if(saldo < get(ind).preco)
            throw "fail: saldo insuficiente";
        if(espirais[ind].qtd == 0)
            throw "fail: espiral sem produtos";
        saldo -= get(ind).preco;
        get(ind).qtd -= 1;
    }

    float getSaldo(){
        return saldo;
    }
};

template <class T>
T get(stringstream &is){
    T t;
    is >> t;
    return t;
}

int main(){
    Maquina maq(0, 0);
    while(true){
        try{
            string line, cmd;
            getline(cin, line);
            cout << "$" << line << endl;
            stringstream in(line);
            in >> cmd;
            if(cmd == "end"){
                break;
            }else if(cmd == "init"){
                int qtd, max_produtos;
                in >> qtd >> max_produtos;
                maq = Maquina(qtd, max_produtos);
            }else if(cmd == "show"){
                cout << maq;
            }else if(cmd == "set"){
                int ind, qtd; float preco; string desc;
                in >> ind >> desc >> qtd >> preco;
                maq.set(ind, desc, qtd, preco);
            }else if(cmd == "limpar"){
                maq.limpar(get<int>(in));
            }else if(cmd == "dinheiro"){
                maq.inserirDinheiro(get<float>(in));
            }else if(cmd == "troco"){
                cout << "voce recebeu " << fixed << setprecision(2) << maq.pedirTroco() << " RS" << endl;
            }else if(cmd == "comprar"){
                int ind = get<int>(in);
                maq.comprar(ind);
                cout << "voce comprou um " << maq.get(ind).nome << endl;
            }else{
                cout << "fail: comando invalido";
            }
        }catch(const char * msg){
            cout << msg << endl;
        }
    }
}