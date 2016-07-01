import java.util.ArrayList;
public class Pareto {

public Pareto(){
	
}

public static void main(String[] args){
	//Dialog hier einfuegen 

	ArrayList<Solution> lösungen = new ArrayList<Solution>();
	for(double i=0.1;i<10;i+=0.5){
	AntColony colony1 = new AntColony();
	colony1.constructProblem("C:/Users/Adrian/Google Drive/OR Competition/Probleminstanzen/ttp_1_10.txt");
	colony1.setMaxRounds(200);
	colony1.setUseWeight(i);
	Solution s = colony1.run();
	if(take(s,lösungen)){
	lösungen.add(s);
	}
	}
	System.out.println("\n\n\nAlle guten Lösungen:");
	for(Solution l : lösungen){
		System.out.println("Nutzen: " + l.getUse() + " - Weg: " + l.getLength() + " - Gewicht: " + l.getWeight() + " Tour: " + l.print());
	}
}

public static boolean take(Solution s, ArrayList<Solution> a){
	for(Solution b: a){
		if(b.getLength()<=s.getLength() && b.getUse()>=s.getUse() && !(b.getLength()==s.getLength() && b.getUse()==s.getUse())){
			return false;
		}
	}
	return true;
}


}
