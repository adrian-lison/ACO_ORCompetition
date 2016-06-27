import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class AntColony {

	int numberOfAnts;
	
	int DIESEVARIABLELOESCHEN;
	
	int maxRounds;
	ProblemInstance problem;
	Solution globalBest;
	double maxAdvantageRatio; //Bestmoegliches Nutzen<->Weg Verhaeltnis. Damit koennen spaeter bestimmte Loesungen ausgeschlossen werden.
	
	public AntColony(){
		//TODO
	}
	
	public static void main(String[] args){
		String fileName="";
		//Dialog hier einfuegen 
		constructProblem(fileName);
		//TODO
	}
	
	private void constructProblem(String fileName){
		
		String splitElement="#";
		try {

			FileReader fr=new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			
			
			int status = 0; //represents the kind of data extracted in current line: -1 => Start; 0=>Meta ; 1=>Nodes 
			
			String line=br.readLine();
			while (line != null) {

				switch(line){
					case "#META":
status=0;						
break;
					case "#NODES":
						status=1;						
						break;
						break;
					case "#EOF":
						return;
						
					default:
							String[] splitted = line.split(" ");
							
							
							if(status==0){
								//metadata
								if(splitted[0].equals("name")){
									String name="";
									for(int i=2;i<splitted.length;i++){
										name += splitted[i];
									}
									System.out.println("Name: " + name);
								}
								else if(splitted[0].equals("k"))
									System.out.println("k: " +splitted[2]);
								else if(splitted[0].equals("N"))
									System.out.println("N: " +splitted[2]);
								}
							else if (status ==1){
								//nodes
								
								String coordinates = splitted[1];
								coordinatesString.replace('(', ''); coordinatesString.replace(')', '');
								
								coordinates =split(",");
								System.out.print("x:" + coordinates[0] + " y:" + coordinates[1]);
								
								
								String customerDetailsString = splitted[2];
								
								customerDetailsString.replace('(', ''); customerDetails.replace(')','');
								
								customerDetails = customerDetailsString.split(",");
								System.out.print("weight:" + customerDetails[0] + " profit:" + customerDetails[1]);

							}
							
							}
				
				line=br.readLine();
			}

			
			//XXX Achtung Plagiat
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		
	}
	
	
	
	private Solution execute(){
		//TODO
		return new Solution();
	}
	
	private Solution runRound(){
		//TODO
		return new Solution();
	}
	
	private void updatePheromone(Solution input){
		//TODO
	}
	
	private void calculateHeuristics(){
		//TODO
	}
	
}
