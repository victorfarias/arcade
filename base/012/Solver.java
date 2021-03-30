import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Pass {
    private String name;
    private int age;

    public Pass(String name, int age) {
        this.name = name;
        this.age = age;
    }
    //return true if pass.age >= 65
    public boolean isPriority() {
        return age >= 65;
    }

    //GETS e SETS

    String getName() {
        return this.name;
    }

    void setName(String name) {
        this.name = name;
    }
    int getAge() {
        return this.age;
    }    
    void setAge(int age) {
        this.age = age;
    }
    public String toString(){
        return this.name + ":" + this.age;
    }

}

class Topic {
    private List<Pass> prioritySeats;
    private List<Pass> normalSeats;
    public Topic(int capacity, int qtdPriority) {
        this.prioritySeats = new ArrayList<>(Collections.nCopies(qtdPriority, null));
        this.normalSeats = new ArrayList<>(Collections.nCopies(capacity - qtdPriority, null));
    }
    //return the first free pos or -1
    private int findFirstFreePos(List<Pass> list) {
        return IntStream.range(0, list.size()).filter(p -> list.get(p) == null).findFirst().orElse(-1);
    }
    //search in list using name and return position or return -1
    private int findByName(String name, List<Pass> list) {
        return IntStream.range(0, list.size())
            .filter(p -> list.get(p) != null && list.get(p).getName().equals(name))
            .findFirst().orElse(-1);
    }
    
    //use the findFirstFreePos to search a free position
    //if exists, insert the pass and return true
    //else return false
    private boolean insertOnList(Pass pass, List<Pass> list) {
        int pos = findFirstFreePos(list);
        if(pos == -1)
            return false;
        list.set(pos, pass);
        return true;
    }
    //use the findByName method to locate pos in list, if found, remore the person
    //setting the pos location to null
    private boolean removeFromList(String name, List<Pass> list) {
        int pos = findByName(name, list);
        if(pos == -1)
            return false;
        list.set(pos, null);
        return true;
    }

    //use findByName to test if the pass is already in the topic
    //use the insertOnList method to insert in the right list based in
    //the pass.isPriority result
    public boolean insert(Pass pass) {
        if(findByName(pass.getName(), this.normalSeats) != -1 || findByName(pass.getName(), this.prioritySeats) != -1){
            System.out.println("fail: pass ja esta na topic");
            return false;    
        }
        if(pass.isPriority()){
            if(!insertOnList(pass, this.prioritySeats) && !insertOnList(pass, this.normalSeats)) {
                System.out.println("fail: topic lotada");
                return false;
            }
        } else if(!insertOnList(pass, this.normalSeats) && !insertOnList(pass, this.prioritySeats)) {
            System.out.println("fail: topic lotada");
            return false;
        }
        return true;
    }
    //use the removeFromList method to try to remove from both lists
    public boolean remove(String name) {
        if(removeFromList(name, this.prioritySeats) || removeFromList(name, this.normalSeats))
            return true;
        System.out.println("fail: pass nao esta na topic");
        return false;
    }
    
    public String toString() {
        return "[" + Stream.concat(
            this.prioritySeats.stream().map(p -> ("@" + ((p == null)?(""):("" + p)))), 
            this.normalSeats.stream().map(p -> ("=" + ((p == null)?(""):("" + p)))))
            .collect(Collectors.joining(" ")) + "]";
    }
}

//!KEEP
class Solver{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Topic topic = new Topic(0, 0);
        while(true){
            String line = scanner.nextLine();
            System.out.println("$" + line);
            String ui[] = line.split(" ");
            if(line.equals("end")) {
                break;
            } else if(ui[0].equals("init")) { //capacity qtdPriority
                topic = new Topic(Integer.parseInt(ui[1]), Integer.parseInt(ui[2]));
            } else if(ui[0].equals("show")) {
                System.out.println(topic);
            } else if(ui[0].equals("in")) {
                topic.insert(new Pass(ui[1], Integer.parseInt(ui[2])));
            } else if(ui[0].equals("out")) {//value value
                topic.remove(ui[1]);
            } else {
                System.out.println("fail: comando invalido");
            }
        }
        scanner.close();
    }
}
//!OFF
