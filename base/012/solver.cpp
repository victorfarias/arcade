#include <iostream>
#include <vector>
#include <sstream>
using namespace std;

struct Pass{
    string id;
    int idade;

    Pass(string id = "", int idade = 0  ){
        this->id = id;
        this->idade = idade;
    }
    friend ostream& operator<<(ostream& os, Pass& p){
        os << p.id << ":" << p.idade;
        return os;
    }
};

struct Topic{
    vector<Pass*> cadeiras;
    int qtdPref;

    Topic(int lotacao = 0, int qtdPref = 0):
        cadeiras(lotacao, nullptr), qtdPref(qtdPref) {
    }

    ~Topic(){
        for(Pass * pass : cadeiras)
            delete(pass);
    }

    int find(string id){
        for(int i = 0; i < (int) cadeiras.size(); i++)
            if(cadeiras[i] != nullptr && cadeiras[i]->id == id)
                return i;
        return -1;
    }

    int findPref(){
        for(int i = 0; i < qtdPref; i++)
            if(cadeiras[i] == nullptr)
                return i;
        return -1;
    }
    
    int findNormal(){
        for(int i = qtdPref; i < (int) cadeiras.size(); i++)
            if(cadeiras[i] == nullptr)
                return i;
        return -1;
    }
    

    bool subir(Pass * pass){
        string id = pass->id;
        int idade = pass->idade;
        int pref = findPref();
        int norm = findNormal();
        if(pref == -1 && norm == -1){
            cout << "fail: topic lotada" << endl;
            return false;
        }
        if(find(pass->id) != -1){
            cout << "fail: pass ja esta na topic" << endl;
            return false;
        }
        int pos = 0;
        if(idade >= 65)
            pos = pref != -1? pref : norm;
        else
            pos = norm != -1? norm : pref;
        cadeiras[pos] = pass;
        return true;
    }

    Pass * descer(string id){
        for(int i = 0; i < (int) cadeiras.size(); i++){
            if(cadeiras[i] != nullptr && cadeiras[i]->id == id){
                Pass * p = cadeiras[i];
                cadeiras[i] = nullptr;
                return p;
            }
        }
        cout << "fail: pass nao esta na topic" << endl;
        return nullptr;
    }

    friend ostream& operator<<(ostream& os, Topic& t){
        os << "[ ";
        for(int i = 0; i < (int) t.cadeiras.size(); i++){
            os << (i < t.qtdPref ? "@" : "=");
            if(t.cadeiras[i] != nullptr)
                os << *t.cadeiras[i];
            os << " ";
        }
        os << "]";
        return os;
    }
};


int main(){
    string line, cmd;
    Topic topic;
    while(true){
        getline(cin, line);
        stringstream ss(line);
        cout << "$" << line << endl;
        ss >> cmd;
        if(line == "end"){
            break;
        }else if(cmd == "show"){
            cout << topic << endl;
        }else if(cmd == "init"){
            int qtd, pref;
            ss >> qtd >> pref;
            topic = Topic(qtd, pref);
        }else if(cmd == "in"){
            string id;
            int idade;
            ss >> id >> idade;
            Pass * pass = new Pass(id, idade);
            if(!topic.subir(pass))
                delete pass;
        }else if(cmd == "out"){
            string id;
            ss >> id;
            Pass * pass = topic.descer(id);
            if(pass != nullptr)
                delete pass;
        }else
            cout << "fail: comando invalido" << endl;
    }
}