import java.util.ArrayList;
import java.awt.geom.Point2D;

public class Logic {

	private double pheromoneWeight;
	private double heuristicWeight;
	RandomNumberGenerator random;

	public Logic(){
		random = new JavaRandom();
	}

	public void setPheromoneWeight(double w){this.pheromoneWeight=w;}
	public void setHeuristiscWeight(double w){this.heuristicWeight=w;}

	public Customer decideProblem(ProblemInstance problem, Solution currentSolution, Heuristic heuristic){

		int maxKunden=50; //TODO dependant on heuristicWeight


		ArrayList<Point2D> liste = new ArrayList<Point2D>();

		int i=1;
		while(i<problem.getNumberOfCustomers() && (
				(
					(currentSolution.getCustomerOrder().size()>1) && (currentSolution.getCustomerOrder().get(1)==problem.getCustomers()[i])) //if customer is start customer
					|| 
					!(currentSolution.getCustomerOrder().contains(problem.getCustomers()[i])) //or if customer i has not yet been visited
				) 
				&& 
				problem.getCustomers()[i].getWeight()<=problem.getMaxCapacity()-currentSolution.getWeight()) //and the package of customer i still fits in the truck
		{		
			double heuVal = heuristic.getHeuristicValue(currentSolution.getLastCustomer(), problem.getCustomers()[i]);
			HeuristicComparator c = new HeuristicComparator();
			liste.sort(c);
			Point2D punkt = new Point2D.Double(i,heuVal);		
			if(liste.size()<=maxKunden){
				liste.add(punkt);
			}
			else{
				if(c.compare(liste.get(0),punkt)<0){
					liste.remove(0);
					liste.add(0, punkt);
				}
			}
		}


		for(Point2D p:liste){
			double val=p.getY();
			val = Math.pow(val, heuristicWeight) * Math.pow(problem.getPheromoneValue(currentSolution.getLastCustomer(), problem.getCustomers()[(int)p.getX()]), pheromoneWeight);
		}

		double sum=0;
		for(Point2D p:liste){
			sum+= p.getY();
		}

		double rand=random.getRandomDouble(sum);
		int b=-1;
		sum=0;
		do{		
			sum+=liste.get(b).getY();
			b++;
		}while(b<liste.size() && sum<rand);

		return problem.getCustomers()[(int)liste.get(b).getX()];
	}


}
