import java.util.Collection;
import java.util.TreeMap;

public class Paciente implements IPaciente{
	protected String id;
	protected String diagnostico;
	protected TreeMap<String, IMedico> medicos = new TreeMap<String, IMedico>();

	Paciente(String id, String diagnostico){
		this.id = id;
		this.diagnostico = diagnostico;
	}

	public String getId() {
		return id;
	}

	public void addMedico(IMedico medico) {
		IMedico pmedico = medicos.get(medico.getId());
		if(pmedico != null)
			return;
		medicos.put(medico.getId(), medico);
		medico.addPaciente(this);
	}

	public void removerMedico(String idMedico) {
		IMedico pmedico = medicos.get(idMedico);
		if(pmedico == null)
			return;
		medicos.remove(idMedico);
		pmedico.removerPaciente(this.id);
	}

	public Collection<IMedico> getMedicos(){
		return medicos.values();
	}

	public String getDiagnostico(){
		return diagnostico;
	}

	public String toString(){
		String ListMedicos = new String();
		for(IMedico medico: getMedicos()) ListMedicos += medico.getId()+" ";
		return "Pac: "+String.format("%-16.16s",getId()+":"+getDiagnostico())+" Meds: [ "+ListMedicos+"]\n";
	};
}