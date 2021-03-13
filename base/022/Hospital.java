import java.util.Map;
import java.util.TreeMap;

public class Hospital{
	TreeMap<String, IPaciente> pacientes = new TreeMap<String, IPaciente>();
	TreeMap<String, IMedico> medicos = new TreeMap<String, IMedico>();
	
	void removerPaciente(String id) {
		IPaciente paciente = pacientes.get(id);
		if(paciente == null)
			return;
		for(IMedico medico : paciente.getMedicos()) 
			medico.removerPaciente(id);
		pacientes.remove(id);
	}
	
	void removerMedico(String id) {
		IMedico medico = medicos.get(id);
		if(medico == null)
			return;
		for(IPaciente paciente : medico.getPacientes()) 
			paciente.removerMedico(id);
		medicos.remove(id);
	}

	void addPaciente(Paciente paciente){
		pacientes.put(paciente.getId(), paciente);
	}

	void addMedico(Medico medico){
		medicos.put(medico.getId(), medico);
	}

	void vincular(String nomeMedico, String nomePaciente){
		IMedico medico = medicos.get(nomeMedico);
		IPaciente paciente = pacientes.get(nomePaciente);
		if(medico == null)
			return;
		for(IMedico med : paciente.getMedicos()){
			if(medico.getClasse().equals(med.getClasse())){
				System.out.println("fail: ja existe outro medico da especialidade cirurgia");
				return;
			}
		}
		medico.addPaciente(paciente);
		paciente.addMedico(medico);
	}

	String showAll(){
		StringBuilder lista = new StringBuilder();
		for(IPaciente paciente : pacientes.values()) lista.append(paciente);
		for(IMedico medico : medicos.values()) lista.append(medico);
		return lista.toString();
	}
}
