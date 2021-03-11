import java.util.Collection;
import java.util.TreeMap;

class Medico implements IMedico{
	String id;
	String classe;
	TreeMap<String, IPaciente> pacientes = new TreeMap<String, IPaciente>();

	Medico(String id, String classe){
		this.id = id;
		this.classe = classe;
	}

	public String getId() {
		return id;
	}

	public void addPaciente(IPaciente paciente) {
		IPaciente mpaciente = pacientes.get(paciente.getId());
		if(mpaciente != null)
			return;
		pacientes.put(paciente.getId(), paciente);
		paciente.addMedico(this);
	}

	public void removerPaciente(String idPaciente) {
		IPaciente mpaciente = pacientes.get(idPaciente);
		if(mpaciente == null)
			return;
		pacientes.remove(idPaciente);
		mpaciente.removerMedico(this.id);
	}

	public Collection<IPaciente> getPacientes(){
		return pacientes.values();
	}

	public String getClasse(){
		return this.classe;
	}

	public String toString(){
		String pacientes = new String();
		for(IPaciente paciente: getPacientes()) pacientes += paciente.getId()+" ";
		return "Med: "+String.format("%-16.16s",getId()+":"+getClasse())+" Pacs: [ "+pacientes+"]\n";
	};

}
