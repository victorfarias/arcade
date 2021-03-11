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

	String showAll(){
		StringBuilder lista = new StringBuilder();
		for(IPaciente paciente : pacientes.values()) lista.append(paciente);
		for(IMedico medico : medicos.values()) lista.append(medico);
		return lista.toString();
	}
}
