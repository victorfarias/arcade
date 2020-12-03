#include <iostream>
#include <vector>
#include <sstream>
#include <map>

using namespace std;

class Fone {
public:
    string idFone;
    string numero;

    Fone(string id, string numero) {
        idFone = id;
        this->numero = numero;
    }

    static bool validate(string numero) {
        string data = "1234567890()- ";
        for(auto c : numero)
            if(data.find(c) == string::npos)
                return false;
        return true;
    }

    bool operator==(const Fone& other){
        return this->idFone == other.idFone;
    }

    friend ostream& operator<<(ostream& out, Fone fone){
        out << fone.idFone << ":" << fone.numero;
        return out;
    }
};

class Contato {
public:
    string id;
    vector<Fone> fones;
    bool favorited;

    Contato(string id = string(""), vector<Fone> fones= {}) : 
        id{id}, fones{fones}, favorited{false}{
    }

    void addFone(Fone fone){
        fones.push_back(fone);
    }

    void rmFone(int index){
        fones.erase(fones.begin() + index);
    }

    vector<Fone> getAllFones(){
        return fones;
    }

    friend ostream& operator<<(ostream& out, Contato contato){
        out << (contato.favorited ? "@ " : "- ");
        out << contato.id;
        int i = 0;
        for(auto fone: contato.fones)
            out << " [" << i++ << ":" << fone << "]";
        return out;
    }
};

class Agenda {
    map<string, Contato> contatos;
    map<string, Contato*> favoritos;

public:
    void addContato(Contato contato){
        if(contatos.count(contato.id))
            throw "contato " + contato.id + " ja existe";
        contatos[contato.id] = contato;
    }

    void rmContato(string nome) {
        if(contatos.erase(nome))
            favoritos.erase(nome);
        else
            throw "contato " + nome + " nao existe";
    }

    Contato * getContato(string nome){
        auto it = contatos.find(nome);
        if(it == contatos.end())
            throw "contato " + nome + " nao existe";
        return &it->second;
    }

    void favoritar(string nome) {
        Contato * contato = getContato(nome);
        if(contato->favorited)
            return;
        contato->favorited = true;
        favoritos[nome] = contato;
    }

    void desfavoritar(string nome){
        Contato * contato = getContato(nome);
        if(!contato->favorited)
            return;
        contato->favorited = false;
        favoritos.erase(nome);
    }

    vector<Contato> getFavoritos(){
        vector<Contato> resp;
        for(auto par : favoritos)
            resp.push_back(*par.second);
        return resp;
    }

    vector<Contato> getContatos(){
        vector<Contato> resp;
        for(auto par : contatos)
            resp.push_back(par.second);
        return resp;
    }

    vector<Contato> search(string pattern){
        vector<Contato> resp;
        for(auto par : contatos)
            if(par.second.toString().find(pattern) != string::npos)
                resp.push_back(par.second);
        return resp;
    }
};



int main(){
    Agenda agenda;
    while(true){
        string line, cmd;
        getline(cin, line);
        stringstream ss(line);
        ss >> cmd;
        try{
            if(cmd == "help"){
                cout << HELP_TEXT << endl;
            }
            else if(cmd == "addContato"){
                agenda.addContato(Contato(ui[1]));
            }
            else if(cmd == "rmContato"){
                agenda.rmContato(ui[1]);
            }
            else if(cmd == "addFone"){
                Contato * cont = agenda.getContato(ui[1]);
                cont->addFone(Fone(ui[2], ui[3]));
            }
            else if(cmd == "rmFone"){
                Contato * cont = agenda.getContato(ui[1]);
                cont->rmFone(ui[2]);
            }
            else if(cmd == "show"){
                agenda.getContato(ui[1])->toString();
            }
            else if(cmd == "showAll"){
                for(auto elem : agenda.getContatos())
                    cout << elem.toString() + "\n";
            }
            else if(cmd == "search"){
                for(auto elem : agenda.search(ui[1]))
                    cout << elem.toString() + "\n";
            }
            else if(cmd == "fav"){
                agenda.favoritar(ui[1]);
            }
            else if(cmd == "desfav"){
                agenda.desfavoritar(ui[1]);
            }
            else if(cmd == "favorited"){
                for(auto elem : agenda.getFavoritos())
                    cout << elem.toString() + "\n";
            }
            else{
                cout << "comando invalido";
            }
        }catch(string e){
            cout << e << endl;
        }
    }
}