#include <iostream>
#include <map>
#include <vector>
#include <memory>
#include <sstream>

using namespace std;

struct Conta{
    int id;
    float saldo;
    string idCliente;
    string tipo;

    Conta(int id, string idCliente){
        this->id = id;
        this->idCliente = idCliente;
        this->saldo = 0;
    }

    virtual void atualizarMes() = 0;

    void sacar(float value){
        if(saldo >= value)
            saldo -= value;
        else
            throw string("fail: saldo insuficiente");
    }
    void depositar(float value){
        saldo += value;
    }
    void transferir(Conta& other, float value){
        this->sacar(value);
        other.depositar(value);
    }
    friend ostream& operator<<(ostream& os, Conta& conta){
        char saida[200];
        sprintf(saida, "%d:%s:%.2f:%s", 
            conta.id, conta.idCliente.c_str(), conta.saldo, conta.tipo.c_str());
        os << string(saida);
        return os;
    }
};

struct ContaCorrente : public Conta{

    ContaCorrente(int id, string idClient): Conta(id, idClient){
        this->tipo = "CC";
    }
    virtual void atualizarMes(){
        saldo -= 20;
    }
};

struct ContaPoupanca : public Conta{
    ContaPoupanca(int id, string idClient): Conta(id, idClient){
        this->tipo = "CP";
    }
    virtual void atualizarMes(){
        saldo *= 1.01;
    }
};

struct Cliente{
    string idCliente;
    vector<shared_ptr<Conta>> contas;
    Cliente(string id){
        this->idCliente = id;
    }
};

class Agencia{
    map<string, shared_ptr<Cliente>> clientes;
    map<int, shared_ptr<Conta>> contas;
    int nextContaId {};
    Conta& getConta(int id){
        try{
            return *contas.at(id);
        }catch(exception& e){
            throw string("fail: conta nao encontrada");
        }
    }
public:
    Agencia(){}

    void addCli(string id){
        if(clientes.find(id) == clientes.end()){
            auto client = make_shared<Cliente>(id);
            auto cc = make_shared<ContaCorrente>(nextContaId, id);
            contas[nextContaId++] = cc;
            auto cp = make_shared<ContaPoupanca>(nextContaId, id);
            contas[nextContaId++] = cp;
            client->contas.push_back(cc);
            client->contas.push_back(cp);
            clientes[id] = client;
        }
    }

    void sacar(int idConta, float value){
        getConta(idConta).sacar(value);
    }
    void depositar(int idConta, float value){
        getConta(idConta).depositar(value);
    }
    void transferir(int contaDe, int contaPara, float value){
        getConta(contaDe).transferir(getConta(contaPara), value);
    }
    void atualizarContas(){
        for(auto& [k, v] : this->contas)
            v->atualizarMes();
    }
    friend ostream& operator<<(ostream& os, Agencia& agencia){
        for(auto& pair : agencia.contas)
            os << *pair.second << "\n";
        return os;
    }
};


template <class T>
T get(istream& is){ T t; is >> t; return t;}

int main(){
    string line, cmd;
    Agencia ag;
    while(true){
        try{
            getline(cin, line);
            cout << "$" << line << endl;
            stringstream ss(line);
            ss >> cmd;
            if(cmd == "end")
                break;
            else if(cmd == "addCli"){
                ag.addCli(get<string>(ss));
            }else if(cmd == "show"){
                cout << ag;
            }else if(cmd == "saque"){
                int conta; float value;
                ss >> conta >> value;
                ag.sacar(conta, value);
            }else if(cmd == "deposito"){
                int conta; float value;
                ss >> conta >> value;
                ag.depositar(conta, value);
            }else if(cmd == "transf"){
                int contaDe, contaPara; float value;
                ss >> contaDe >> contaPara >> value;
                ag.transferir(contaDe, contaPara, value);
            }else if(cmd == "update"){
                ag.atualizarContas();
            }else{
                cout << "fail: comando invalido\n";
            }
        }catch(string e){
            cout << e << endl;
        }
    }
}