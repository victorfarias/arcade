import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.List;

class Tweet{
    int id;
    String username;
    String msg;
    Set<String> likes;
    public Tweet(int id, String username, String msg){
        this.id = id;
        this.username = username;
        this.msg = msg;
        this.likes = new TreeSet<String>();
    }
    public void like(String username){
        for(String user : likes)
            if(user.equals(username))
                return;
        likes.add(username);
    }
    public String toString(){
        String saida = "";
        saida += this.id + ":" + this.username + "( " + this.msg + ")";
        if(likes.size() > 0){
            saida += "[ ";
            for(String user : this.likes)
                saida += user + " ";
            saida += "]";
        }
        return saida;
    }
}

class User{
    String username;
    List<Tweet>   timeline; 
    Map<String, User> followers;
    Map<String, User> following;
    int unreadCount;

    public User(String id){
        this.username = id;
        timeline  = new ArrayList<Tweet>();
        followers = new TreeMap<String, User>();
        following = new TreeMap<String, User>();
        unreadCount = 0;
    }

    public void follow(User other){
        if(following.containsKey(other.username))
            return;
        this.following.put(other.username, other);
        other.followers.put(this.username, this);
    }

    public void unfollow(String username){
        if(!following.containsKey(username))
            return;
        User other = following.get(username);
        this.following.remove(username);
        other.followers.remove(this.username);
    }

    public Tweet getTweet(int id){
        for(Tweet tw : timeline){
            if(tw.id == id)
                return tw;
        }
        throw new RuntimeException("fail: tweet nao existe");
    }

    public void sendTweet(Tweet tw){
        this.timeline.add(tw);
        for(User user : followers.values()){
            user.timeline.add(tw);
            user.unreadCount += 1;
        }
    }

    public String getUnread(){
        String saida = "";
        for(int i = timeline.size() - unreadCount; i < timeline.size(); i++)
            saida += timeline.get(i) + "\n";
        unreadCount = 0;
        return saida;
    }

    public String getTimeline(){
        String saida = "";
        for(Tweet tw : this.timeline)
            saida += tw + "\n";
        return saida;
    }

    public String toString(){
        String saida = username;
        saida += "\n  seguidos   [ ";
        for(User user : following.values())
            saida += user.username + " ";
        saida += "]\n  seguidores [ ";
        for(User user : followers.values())
            saida += user.username + " ";
        saida += "]";
        return saida;
    }
}

class Controller{
    Map<String, User> users;
    Map<Integer, Tweet> tweets;
    int nextTwId = 0;

    public Controller(){
        users = new TreeMap<String, User>();
    }

    public void addUser(String username){
        User user = users.get(username);
        if(user == null){
            users.put(username, new User(username));
        }
    }

    public User getUser(String username){
        User user = users.get(username);
        if(user == null)
            throw new RuntimeException("fail: usuario nao encontrado");
        return user;
    }

    public void sendTweet(String username, String msg){
        User user = this.getUser(username);
        Tweet tw = new Tweet(nextTwId++, username, msg);
        user.sendTweet(tw);
    }

    public String toString(){
        String saida = "";
        for(User user : users.values())
            saida += user + "\n";
        return saida;
    }
}

public class Solver {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Controller sistema = new Controller();
        
        while(true){
            String line = scanner.nextLine();
            System.out.println("$" + line);
            String ui[] = line.split(" ");
            try {
                if (ui[0].equals("end"))
                    break;
                else if (ui[0].equals("addUser")) {
                    sistema.addUser(ui[1]);
                } else if (ui[0].equals("show")) {
                    System.out.print(sistema);
                } else if (ui[0].equals("follow")) {//goku tina
                    User one = sistema.getUser(ui[1]);
                    User two = sistema.getUser(ui[2]);
                    one.follow(two);
                }
                else if (ui[0].equals("twittar")) {//goku msg
                    String username = ui[1];
                    String msg = "";
                    for(int i = 2; i < ui.length; i++)
                        msg += ui[i] + " ";
                    sistema.sendTweet(username, msg);
                }
                else if (ui[0].equals("timeline")) {//goku tina
                    User user = sistema.getUser(ui[1]);
                    System.out.print(user.getTimeline());
                }
                else if (ui[0].equals("like")) {//goku tina
                    User user = sistema.getUser(ui[1]);
                    Tweet tw = user.getTweet(Integer.parseInt(ui[2]));
                    tw.like(ui[1]);
                }else if (ui[0].equals("unfollow")) {//goku tina
                    User user = sistema.getUser(ui[1]);
                    user.unfollow(ui[2]);
                }else{
                    System.out.println("fail: comando invalido");
                }
            }catch(RuntimeException rt){
                System.out.println(rt.getMessage());
            }
        }
        scanner.close();
    }

}
