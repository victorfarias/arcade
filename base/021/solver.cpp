#include <iostream>
#include <vector>
#include <sstream>
#include <map>
#include <algorithm> //istream_iterator
#include <iterator>
using namespace std;


class Aluno; //forward declaration

class Discp{
    string id;
    map<string, Aluno*> m_aluno;
public:
    Discp(string nome = ""): id {nome} {};
    string getId(){return id;};
    void addAluno(Aluno* aluno);    //after
    void rmAluno(string idAluno);   //after
    friend ostream& operator<<(ostream& os, Discp& discp); //after
};

class Aluno{
    string id;
    map<string, Discp*> m_discp;
public:
    Aluno(string nome = ""): id {nome}{};
    string getId(){return id;};
    vector<Discp*> getDiscps(){
        vector<Discp*> discps;
        for(auto &[k, v] : m_discp) //c++17
            discps.push_back(v);
        return discps;
    }
    friend ostream& operator<<(ostream& os, Aluno& aluno){
        os << "    " << aluno.getId() << " [ ";
        for(auto& [k, v] : aluno.m_discp)
            os << k << " ";
        os << "]";
        return os;
    }
    friend void Discp::addAluno(Aluno*);
    friend void Discp::rmAluno(string);
};

void Discp::addAluno(Aluno* aluno){
    if(this->m_aluno.count(aluno->getId()) == 1) //ja existe
        return;
    this->m_aluno[aluno->getId()] = aluno;
    aluno->m_discp[this->getId()] = this;
}

void Discp::rmAluno(string idAluno){
    auto it = this->m_aluno.find(idAluno);
    if(it == m_aluno.end()) //nao existe
        return;
    it->second->m_discp.erase(this->getId());
    this->m_aluno.erase(it);
}

ostream& operator<<(ostream& os, Discp& discp){
    os << "    " << discp.getId() << " [ ";
    for(auto& [k, v] : discp.m_aluno)
        os << k << " ";
    os << "]";
    return os;
}

template <class T>
T& get(map<string, T>& m, string key){
    try{
        return m.at(key);
    }catch(exception& e){
        throw string("chave " + key + " nao existe");
    }
}

class Sistema {
    map<string, Aluno> m_aluno;
    map<string, Discp> m_discp;

public:
    void addAluno(string idAluno){
        if(m_aluno.count(idAluno) == 0)
            m_aluno[idAluno] = Aluno(idAluno);
    }
    void addDiscp(string idDiscp){
        if(m_discp.count(idDiscp) == 0)
            m_discp[idDiscp] = Discp(idDiscp);
    }
    void matricular(string idAluno, string idDiscp){
        m_discp.at(idDiscp).addAluno(&m_aluno.at(idAluno));
    }
    void desmatricular(string idAluno, string idDiscp){
        m_discp.at(idDiscp).rmAluno(idAluno);
    }
    void rmAluno(string idAluno){
        Aluno& aluno = get(m_aluno, idAluno);
        for(auto* discp : aluno.getDiscps())
            discp->rmAluno(idAluno);
        this->m_aluno.erase(idAluno);
    }
    friend ostream& operator<<(ostream& os, Sistema& sis){
        os << "alunos:\n";
        for(auto& [k, v] : sis.m_aluno)
            os << v << "\n";
        os << "discps:\n";
        for(auto& [k, v] : sis.m_discp)
            os << v << "\n";
        return os;
    }
};


int main(){
    string line, cmd;
    Sistema sistema;
    while(true){
        try{
            getline(cin, line);
            cout << "$" << line << endl;
            stringstream ss(line);
            vector<string> ui(istream_iterator<string>{ss}, istream_iterator<string>());
            string cmd = ui[0];
            if(cmd == "end"){
                break;
            }else if(cmd == "nwalu"){
                for(size_t i = 1; i < ui.size(); i++)
                    sistema.addAluno(ui[i]);
            }else if(cmd == "nwdis"){
                for(size_t i = 1; i < ui.size(); i++)
                    sistema.addDiscp(ui[i]);
            }else if(cmd == "show"){
                cout << sistema;
            }else if(cmd == "tie"){
                for(size_t i = 2; i < ui.size(); i++)
                    sistema.matricular(ui[1], ui[i]);
            }else if(cmd == "untie"){
                for(size_t i = 2; i < ui.size(); i++)
                    sistema.desmatricular(ui[1], ui[i]);
            }else if(cmd == "rmalu"){
                sistema.rmAluno(ui[1]);
            }else{
                cout << "comando invalido " << "[" << cmd << "]\n";
            }
        }catch(string e){
            cout << e << endl;
        }
    }
}

// template <class T>
// T get(istream& is){ T t; is >> t; return t;}

// int main(){
//     string line, cmd;
//     Sistema sistema;
//     while(true){
//         try{
//             getline(cin, line);
//             cout << "$" << line << endl;
//             stringstream ss(line);
//             string cmd, value;
//             ss >> cmd;
//             if(cmd == "end"){
//                 break;
//             }else if(cmd == "nwalu"){
//                 while(ss >> value)
//                     sistema.addAluno(value);
//             }else if(cmd == "nwdis"){
//                 while(ss >> value)
//                     sistema.addDiscp(value);
//             }else if(cmd == "show"){
//                 cout << sistema;
//             }else if(cmd == "tie"){
//                 string aluno = get<string>(ss);
//                 while(ss >> value)
//                     sistema.matricular(aluno, value);
//             }else if(cmd == "untie"){
//                 string aluno = get<string>(ss);
//                 while(ss >> value)
//                     sistema.desmatricular(aluno, value);
//             }else if(cmd == "rmalu"){
//                 sistema.rmAluno(get<string>(ss));
//             }else{
//                 cout << "comando invalido " << "[" << cmd << "]\n";
//             }
//         }catch(string e){
//             cout << e << endl;
//         }
//     }
// }


void main2(){
    Sistema sys;
    for(auto aluno : {"alice", "edson", "bruno"})
        sys.addAluno(aluno);
    for(auto discp : {"fup", "aps", "poo"})
        sys.addDiscp(discp);
    cout << sys;
/*
alunos:
    alice [ ]
    bruno [ ]
    edson [ ]
discps:
    aps [ ]
    fup [ ]
    poo [ ]
*/
    for(auto discp : {"fup", "aps", "poo"})
        sys.matricular("bruno", discp);
    for(auto discp : {"fup", "poo"})
        sys.matricular("alice", discp);
    sys.matricular("edson", "fup");
    cout << sys;
/*
alunos:
    alice [ fup poo ]
    bruno [ aps fup poo ]
    edson [ fup ]
discps:
    aps [ bruno ]
    fup [ alice bruno edson ]
    poo [ alice bruno ]
*/
    sys.desmatricular("bruno", "poo");
    sys.desmatricular("bruno", "aps");
    cout << sys;
/*
alunos:
    alice [ fup poo ]
    bruno [ fup ]
    edson [ fup ]
discps:
    aps [ ]
    fup [ alice bruno edson ]
    poo [ alice ]
*/
    sys.rmAluno("alice");
    cout << sys;
/*
alunos:
    bruno [ fup ]
    edson [ fup ]
discps:
    aps [ ]
    fup [ bruno edson ]
    poo [ ]
*/
}
