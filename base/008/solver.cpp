#include <iostream>
#include <map>
#include <vector>
#include <algorithm>
#include <sstream>
#include <iterator>

using namespace std;

class Cliente{
public:
    string name;
    float limite;
    float balance;

    Cliente(string name = "", float limite = 0){
        this->name = name;
        this->limite = limite;
        this->balance = 0;
    }
    friend ostream& operator<<(ostream &os, Cliente cliente){
        os << cliente.name << ":" << cliente.balance << "/" << cliente.limite ;
        return os;
    }
};


class Transaction{
public:
    int id;
    string idCli;
    float value;
    Transaction(int id = 0, string idCli = "", float value = 0.0){
        this->id = id;
        this->idCli = idCli;
        this->value = value;
    }
    friend ostream& operator<<(ostream& os, Transaction tr){
        os << "id:" << tr.id << " " << tr.idCli << ":" << tr.value;
        return os;
    }
};




class Agiota{
    map<string, Cliente> repCli;
    map<int, Transaction> repTr;
    int nextTrId = 0;
    float balance;

    void pushTransacao(string idCli, float value){
        repTr[nextTrId] = Transaction(nextTrId, idCli, value);
        nextTrId++;
    }

public:
    Agiota(float saldo = 0.0){
        this->balance = saldo;
    }

    void addClient(string name, float limite){
        if(repCli.count(name) == 1)
            throw string("fail: cliente ja existe");
        repCli[name] = Cliente(name, limite);
    }

    void lends(string idCli, float value){
        if(value > this->balance)
            throw string("fail: fundos insuficientes");
        if(repCli.count(idCli) == 0)
            throw string("fail: cliente nao existe");
        Cliente& cliente = repCli.at(idCli);
        if(cliente.balance + value > cliente.limite)
            throw string("fail: limite excedido");
        cliente.balance += value;
        this->balance -= value;
        pushTransacao(idCli, value);
    }

    void receive(string idCli, float value){
        Cliente& cliente = repCli.at(idCli);
        if(value > cliente.balance)
            throw string("fail: valor maior que a divida");
        cliente.balance -= value;
        this->balance += value;
        pushTransacao(idCli, -value);
    }



    void kill(string idCli){
        repCli.erase(idCli);
        vector<int> trToRemove;
        for(auto & [k, v] : repTr)
            if(v.idCli == idCli)
                trToRemove.push_back(k);
        for(int k : trToRemove)
            repTr.erase(k);
    }

    friend ostream& operator<<(ostream& os, Agiota& agiota){
        os << "clients:\n";
        for(auto& [k, cli] : agiota.repCli)
            os << "- " << cli << endl;
        os << "transactions:\n";
        for(auto& [k, tr] : agiota.repTr)
            os << "- " << tr << endl;
        os << "balance: " << agiota.balance << endl;
        return os;
    }
};

template<typename T>
T get(stringstream& ss){T t; ss >> t; return t;}

int main(){
    string line, cmd;
    Agiota agiota;
    while(true){
        try{
            getline(cin, line);
            cout << "$" << line << endl;
            stringstream ss(line);
            vector<string> ui(istream_iterator<string>{ss}, istream_iterator<string>());
            string cmd = ui[0];
            if(cmd == "end"){
                break;
            }else if(cmd == "init"){
                agiota = Agiota(stoi(ui[1]));
            }else if(cmd == "addCli"){
                agiota.addClient(ui[1], stoi(ui[2]));
            }else if(cmd == "show"){
                cout << agiota;
            }else if(cmd == "kill"){
                agiota.kill(ui[1]);
            }else if(cmd == "lend"){
                agiota.lends(ui[1], stof(ui[2]));
            }else if(cmd == "receive"){
                agiota.receive(ui[1], stof(ui[2]));
            }else{
                cout << "fail: comando invalido" << endl;
            }
        }catch(string s){
            cout << s << endl;
        }
    }
}


