
public class Logic {

	private int pheromoneWeight;
	private int heuristicWeight;
	RandomNumberGenerator random;
	AntColony colony;
	
	public Logic(){
		random = new JavaRandom();
	}
	
	public Customer decideProblem(ProblemInstance problem, Solution currentSolution ){
		return new Customer(heuristicWeight, null, heuristicWeight, heuristicWeight);
		
		Customer pci = colony.getPCI(this); //YYY
		
		
		//pick customer
		Customer c=new Customer(1,null,1,1); //XXX TO-DO
		Customer c1=null; //XXX TO-DO
		Customer c2=null; //XXX TO-DO
		
		
		//calculate economic value
		double customerValue = c.getUse()-c.getWeight();
		if(customerValue < c1.distanceTo(c2))
			System.out.println("Customer c2 kommt nicht infrage, weil zusaetzlicher value geringer ist als der zusaetzliche Weg(50/50 Gewichtung)XXX");
		
		double economicValue = (heuristicWeight*customerValue + problem.getPheromoneValue(c1,c2)*pheromoneWeight) / (heuristicWeight+pheromoneWeight);

		//compare Values
		
		
		
		
	}
	
	
}
