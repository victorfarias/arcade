import java.util.*;

public class Solver {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Hospital hospital = new Hospital();

		while (true) {
			String line = scanner.nextLine();
			System.out.println("$" + line);
			String ui[] = line.split(" ");
			if (ui[0].equals("end"))
				break;
			else if (ui[0].equals("addPacs")) {
				for(int i = 1; i < ui.length;i++){
					String dados[] = ui[i].split("-");
					hospital.addPaciente(new Paciente(dados[0], dados[1]));
				}
			}else if (ui[0].equals("addMeds")) {
				for(int i = 1; i < ui.length;i++){
					String dados[] = ui[i].split("-");
					hospital.addMedico(new Medico(dados[0], dados[1]));
				}
			}else if (ui[0].equals("seeAll")) {
				System.out.print(hospital.showAll());
			}
		}
	}
}
