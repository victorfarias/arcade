import java.util.ArrayList;
import java.util.List;

class Mensagem{
	String idUser;
	String text;
	public Mensagem(String id, String text) {
		this.idUser = id;
		this.text = text;
	}
}

abstract class BatePapense {
	ArrayList<Mensagem> inbox;
	abstract String getId();
	List<Mensagem> getInbox(){
		List<Mensagem> output = new ArrayList<Mensagem>(this.inbox);
		this.inbox.clear();
		return output;
	}
	void sendMessage(Mensagem msg, IBatePapense batePapense){
		batePapense.addMessage(msg);
	}
	abstract List<BatePapense> getDestinatarios();
}











