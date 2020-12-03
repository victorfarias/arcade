#include <iostream>
#include <sstream>
#include <vector>
#include <map>
using namespace std;

struct Msg{
    string idSender;
    string text;
    Msg(string sender, string text):
        idSender(sender), text{text}{
    }
    friend ostream& operator<<(ostream& os, Msg msg){
        os << msg.idSender << ":" << msg.text;
        return os;
    }
};

struct User{
    string id;
    vector<Msg> inbox;
    int contadorNaoLidas;
    User(string id = ""): id{id}, contadorNaoLidas{0}{}
    
    void sendMsg(User& destinatario, string text){
        Msg msg(this->id, text);
        destinatario.inbox.push_back(msg);
        destinatario.contadorNaoLidas += 1 ;
    }

    string readMsgs(){
        if(contadorNaoLidas == 0)
            return "- empty -\n";
        stringstream ss;
        for(size_t i = inbox.size() - contadorNaoLidas; i < inbox.size(); i++)
            ss << inbox[i] << "\n";
        contadorNaoLidas = 0;
        return ss.str();
    }
};

struct Sistema{
    map<string, User> pessoas;
    void create(string name){
        if(pessoas.count(name) == 1)
            throw string("") + "usuario com essa chave ja foi cadastrado";
        pessoas[name] = User(name);
    }

    void sendMsg(string sender, string receiver, string text){
        User& enviador = pessoas.at(sender);
        User& receptor = pessoas.at(receiver);
        enviador.sendMsg(receptor, text);
    }

    string readMsgs(string name){
        return pessoas.at(name).readMsgs();
    }
};

template <class T>
T get(stringstream &ss){
    T t;
    ss >> t;
    return t;
}

int main(){
    string line, cmd;
    Sistema sis;
    while(true){
        try{
            getline(cin, line);
            stringstream ss(line);
            ss >> cmd;
            cout << "$" << line << endl;
            if(cmd == "end"){
                break;
            }else if(cmd == "addUser"){
                sis.create(get<string>(ss));
            }else if(cmd == "sendMsg"){
                string sender, receiver, msg;
                ss >> sender >> receiver;
                getline(ss, msg);
                sis.sendMsg(sender, receiver, msg.substr(1)); //descartar o primeiro char que é um ' '
            }else if(cmd == "inbox"){
                cout << sis.readMsgs(get<string>(ss));
            }else{
                cout << "fail: comando invalido" << endl;
            }
        }catch(string s){
            cout << s;
        }
    }
    return 0;
}

int main1(){
    User david("david");
    User celia("celia");
    david.sendMsg(celia, "voce esta com fome?");
    david.sendMsg(celia, "ganhei duas cortesias para comer no McLanches, bora?");
    cout << david.readMsgs();
    //- empty -
    david.sendMsg(celia, "david celia me avisa se quiser ir!");
    cout << celia.readMsgs();
    // david:voce esta com fome?
    // david:ganhei duas cortesias para comer no McLanches, bora?
    // david:david celia me avisa se quiser ir!
    celia.sendMsg(david, "na hora, te encontro la em 10 min");
    cout << david.readMsgs();
    // celia:na hora, te encontro la em 10 min
    david.sendMsg(celia, "ja to aqui");
    cout << celia.readMsgs();
    // david:ja to aqui
    return 0;
}
