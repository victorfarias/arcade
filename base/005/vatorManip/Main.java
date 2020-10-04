import java.util.ArrayList;
import java.util.Scanner;
import java.text.*;

class ArrayManip {
  ArrayList<Integer> vet;

  public void ArrayManip(){
    vet = new ArrayList<Integer>();
  }

  public void Add(int value){
    vet.add(value);
  }

  public void Add(int[] elements){
    for(int x : elements){
      vet.add(x);
    }
  }

  public void Rmi(int index){
    if(index < 0 || index > vet.size()) System.out.println("fail");
    else vet.remove(index);
  }

  public void Rma(int index){
    for (int i = 0; i < vet.size(); i++) {
        if (index == vet.get(i)) {
            vet.remove(i);
            i--;
        }
    }
  }
  public void AddPos(int index,int element){
    vet.add(index, element);
  }

  public int get(int index){
    return vet.get(index);
  }

  public void set(int index, int element){
    vet.set(index, element);
  }

  public String find(int[] index){
    String string = "[ ";
    for(int i = 0; i < vet.size(); i++)
      string += vet.indexOf(index[i]) + " ";
    string += "]\n";
    return string;
  }

  public String show(){
    String string = "[ ";
    for(Integer value : vet)
      string += value + " ";
    string +="]\n";
    return string;
  }

  public String rshow(){
    String string = "[ ";
    for(int i = vet.size() - 1; i >= 0; --i)
      string += vet.get(i) + " ";
    string += "]\n";
    return string;
  }
}

class Main{
	public static void main(String[] args) {
		
	}
}