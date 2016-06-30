import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class AntColony {

	private int maxRounds;
	private ProblemInstance problem;
	private Solution globalBest;
	private Logic logic;
	private Heuristic heuristic;
	private double maxAdvantageRatio; //Bestmoegliches Nutzen<->Weg Verhaeltnis. Damit koennen spaeter bestimmte Loesungen ausgeschlossen werden.

	//Pareto values
	private double useWeight=1; //determines the emphasis of the Use of a Solution
	private double lengthWeight=1; //determines the emphasis of the Lenght of a Solution

	//strategic values
	private int numberOfAnts;
	private double evaporationRate;
	private double pbest;
	private double heuristicWeight;
	private double pheromoneWeight;

	public AntColony(){
		logic = new Logic();
		evaporationRate=0.95;
		pbest=0.05;
		maxAdvantageRatio=0;
		
		globalBest = new Solution();
	}

	public static void main(String[] args){
		String fileName="C:/Users/Adrian/Google Drive/OR Competition/Probleminstanzen/ttp_5_4460.txt";
		//Dialog hier einfuegen 


		AntColony colony1 = new AntColony();
		colony1.constructProblem(fileName);
		colony1.problem.increasePheromoneValue(colony1.problem.getCustomers()[2], colony1.problem.getCustomers()[5], 3);
		System.out.println(colony1.maxAdvantageRatio*colony1.problem.getMaxCapacity());
		colony1.numberOfAnts = colony1.problem.getNumberOfCustomers();

	}


	private Solution execute(){
		for(int round=0;round<this.maxRounds;round++){
			problem.setRound(round);
			Solution b = runRound();
			if(calcProfit(b)>calcProfit(this.globalBest)){
				globalBest=b;
			}
			updatePheromone(globalTime(round)? globalBest : b); //if global time reward globalBest, else roundBest
		}
		System.out.println(globalBest.getUse());
		return globalBest;
	}

	/**
	 * Determines whether the global best or the run best ant is rewarded
	 * @param round
	 * @return True for run best, false for global best
	 */
	private boolean globalTime(int round){
		return false;
	}

	private Solution runRound(){
		Solution best=new Solution();
		for(int ameise=1;ameise<=this.numberOfAnts;ameise++){

			Solution s = new Solution();
			//Add artificial customer
			s.addCustomer(problem.getCustomers()[0]);
			logic.setHeuristiscWeight(0);
			//Add start costumer
			Customer next=logic.decideProblem(problem, s, heuristic);
			s.addCustomer(next);
			//Add second customer
			logic.setHeuristiscWeight(this.heuristicWeight);
			next=logic.decideProblem(problem, s, heuristic);
			s.addCustomer(next);

			while(next!=s.getCustomerOrder().get(1)){
				next=logic.decideProblem(problem, s, heuristic);
				s.addCustomer(next);
			}

			if(calcProfit(s)>calcProfit(best)){
				best=s;
			}


		}
		return best;
	}

	private void updatePheromone(Solution input){
		for(int i=0;i<input.getCustomerOrder().size()-1;i++){
			problem.increasePheromoneValue(input.getCustomerOrder().get(i), input.getCustomerOrder().get(i+1), calcProfit(input));
		}
	}

	private void calculateHeuristics(){
		//TODO
	}

	/**
	 * Calculates the maximum pheromone value suitable for MMAS.
	 * <br> Uses evaporationRate, current globalBest
	 * <br> changes over time (due to globalBest)
	 * @return maximum pheromone value
	 */
	private double calcMax(){
		return(1/(1-evaporationRate)-calcProfit(globalBest));
	}

	/**
	 * Calculates the minimum pheromone value suitable for MMAS.
	 * <br> Uses maximum pheromone value, number of Customers (n)
	 * <br> changes over time (due to calcMax)
	 * @return minimum pheromone value
	 */
	private double calcMin(){
		double nbest=Math.pow(pbest,1/problem.getNumberOfCustomers());
		return(	(calcMax()*(1-nbest)) / ((problem.getNumberOfCustomers()/2-1)*nbest) );
	}

	/**
	 * Calculates the profit of a solution considering emphasis of use and length.
	 * @param s a Solution
	 * @return profit of the solution
	 */
	private double calcProfit(Solution s){
		if(s.getLastCustomer()==null){return -42000.0;}
		double useRatio=this.useWeight/(useWeight+lengthWeight);
		double lengthRatio=this.lengthWeight/(useWeight+lengthWeight);
		double profit=s.getUse()*useRatio - s.getLength()*lengthRatio;
		//TODO
		//	profit=profit/((maxAdvantageRatio*problem.getMaxCapacity())*useRatio-(maximaleLänge)*lengthRatio); //scale profit
		return profit;
	}




	private void constructProblem(String fileName){

		String probName="";
		int maxCap=0;
		Customer[] kunden=new Customer[0];
		int N=0;

		try {
			FileReader fr=new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			int status = 0; //represents the kind of data extracted in current line: -1 => Start; 0=>Meta ; 1=>Nodes 
			int id=1;

			String line=br.readLine();
			while (line != null) {

				switch(line){
				case "#META":
					status=0;						
					break;
				case "#NODES":
					status=1;						
					break;
				case "#EOF":
					break;

				default:



					if(status==0){
						String[] splitted = line.split(" ");
						//metadata
						if(splitted[0].equals("name")){
							String name="";
							for(int i=2;i<splitted.length;i++){
								name += splitted[i];
							}
							System.out.println("Name: " + name);
							probName=name;
						}
						else if(splitted[0].equals("k")){
							System.out.println("k: " +splitted[2]);
							maxCap=Integer.parseInt(splitted[2]);}
						else if(splitted[0].equals("N")){
							System.out.println("N: " +splitted[2]);
							N=Integer.parseInt(splitted[2]);}
					}
					else if (status==1){
						//nodes

						if(N!=0){
							kunden = new Customer[N+1];
							kunden[0]=new Customer(0,new Point(0,0),0,0); //add artificial customer
							N=0; //prevent this part from being executed another time
						}

						String[] splitted = line.split("\\(");

						String coordinates = splitted[1];
						String[] coordinates2 =coordinates.split(",");

						Point punkt = new Point(Integer.parseInt(coordinates2[0]),Integer.parseInt(coordinates2[1].split("\\)")[0]));
						System.out.print("x:" + punkt.x + " y:" + punkt.y + "  -  ");

						String customerDetailsString = splitted[2];
						String[] customerDetails = customerDetailsString.split(",");
						System.out.print("weight:" + customerDetails[0] + " profit:" + customerDetails[1].split("\\)")[0]);

						Customer kunde = new Customer(id,punkt,Integer.parseInt(customerDetails[0]),Integer.parseInt(customerDetails[1].split("\\)")[0]));
						kunden[id] = kunde;


						System.out.println();
						id++;
					}
					break;

				}
				line=br.readLine();
			}


			int k=0;

			//find maximum k and maximum use - weight ratio
			for(Customer kunde:kunden){
				if(kunde.getWeight()>k){k=kunde.getWeight();}
				if(kunde.getWeight()!=0){
					if(kunde.getUse()/kunde.getWeight()>maxAdvantageRatio){maxAdvantageRatio=kunde.getUse()/kunde.getWeight();}}
			}
			k++;
			//Sort Customers and Construct Problem Instance
			countingSort(kunden,k);
			this.problem=new ProblemInstance(probName,maxCap,kunden);


		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			/*if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}*/
		}
	}

	public void countingSort(Customer[] a, int k){
		Customer[] b = new Customer[a.length];  
		int[] c = new int[k];  

		for (int i = 0; i < k; i++)  
			c[i] = 0;  
		for (int i = 0; i < a.length; i++)  
			c[a[i].getWeight()]++;  

		//  c[i] enthält jetzt die Anzahl Elemente = i  
		for (int i = 1; i < k; i++)  
			c[i] = c[i] + c[i - 1];   

		//  c[i] enthält jetzt die Anzahl Elemente ≤ i  
		for (int i = a.length - 1; i >= 0; i--) {  
			b[c[a[i].getWeight()] - 1] = a[i];  
			c[a[i].getWeight()] = c[a[i].getWeight()] - 1;  
		}  
		for (int i = 0; i < a.length; i++)  
			a[i] = b[i];  
	} 
}



