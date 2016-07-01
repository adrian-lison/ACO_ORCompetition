import java.util.ArrayList;
import java.awt.geom.Point2D;

public class Logic {

	private MessageControl m = new MessageControl();
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

		if(currentSolution.getCustomerOrder().size()>1){
			double heuVal=0;
			if(this.heuristicWeight!=0){
				heuVal = heuristic.getHeuristicValue(currentSolution.getLastCustomer(), currentSolution.getCustomerOrder().get(1),currentSolution.getCustomerOrder().get(1));
			}
			Point2D punkt = new Point2D.Double(currentSolution.getFirst(),heuVal);					
				liste.add(punkt);
		}

		while(i<problem.getNumberOfCustomers()  &&  problem.getCustomers()[i].getWeight()<=problem.getMaxCapacity()-currentSolution.getWeight()){		
			if(!(currentSolution.getCustomerOrder().contains(problem.getCustomers()[i]))){
				double heuVal=0;
				if(this.heuristicWeight!=0){
					heuVal = heuristic.getHeuristicValue(currentSolution.getLastCustomer(), problem.getCustomers()[i],currentSolution.getCustomerOrder().get(1));
				}

				HeuristicComparator c = new HeuristicComparator();
				liste.sort(c);
				Point2D punkt = new Point2D.Double(i,heuVal);		
				if(liste.size()<=maxKunden){
					liste.add(punkt);
				}
				else if(c.compare(liste.get(0),punkt)<0){
					liste.remove(0);
					liste.add(0, punkt);
				}

			}
			i++;
		}

		double minVal=0;
		for(Point2D p:liste){
			if(p.getY()<minVal){minVal=p.getY();}
		}
		for(Point2D p:liste){
			p.setLocation(p.getX(), p.getY()-Math.min(0, minVal));
		}

		for(Point2D p:liste){
			double val=p.getY();
			m.heuristic(p.getX() + ": Heuristic: " + Math.pow(val, heuristicWeight) + " Pheromone: " + Math.pow(problem.getPheromoneValue(currentSolution.getLastCustomer(), problem.getCustomers()[(int)p.getX()]), pheromoneWeight));
			p.setLocation(p.getX(),Math.pow(val, heuristicWeight) * Math.pow(problem.getPheromoneValue(currentSolution.getLastCustomer(), problem.getCustomers()[(int)p.getX()]), pheromoneWeight));
		}

		double sum=0;
		for(Point2D p:liste){
			sum+= p.getY();
		}

		double rand=random.getRandomDouble(sum);
		int b=-1;
		sum=0;
		do{		
			b++;
			sum+=liste.get(b).getY();

		}while(b<liste.size() && sum<rand);
		
		if(currentSolution.getCustomerOrder().size()==1){currentSolution.setFirst((int)liste.get(b).getX());}
		
		return problem.getCustomers()[(int)liste.get(b).getX()];
	}

}

