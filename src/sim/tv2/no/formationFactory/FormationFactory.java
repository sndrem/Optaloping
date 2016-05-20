package sim.tv2.no.formationFactory;

import sim.tv2.no.FormationInterface.Formation;

public class FormationFactory {

	public FormationFactory() {
		// TODO Auto-generated constructor stub
	}
	
	public static Formation createFormation(String formationText) {
		Formation formation = null;
		if(formationText.equalsIgnoreCase("4-4-2")) {
			formation = new Formation442();
		} else if(formationText.equalsIgnoreCase("4-2-3-1")) {
			formation = new Formation4231();
		} else if(formationText.equalsIgnoreCase("4-1-4-1")) {
			formation = new Formation4141();
		} else if(formationText.equalsIgnoreCase("4-3-3")) {
			formation = new Formation433();
		} else if (formationText.equalsIgnoreCase("4-3-2-1")) {
			formation = new Formation4321();
		}
		
		return formation;
	}

}
