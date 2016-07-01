import java.util.ArrayList;
import java.util.Arrays;
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
				Point2D[] p= new Point2D[liste.size()];
				for(int f=0;f<p.length;f++){
					p[f]=liste.get(f);
				}
							
				mergeSort(p, 0, p.length-1);
				liste=new ArrayList<Point2D>();
				for(Point2D u : p){
					liste.add(0, u);
				}
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

	private static void mergeSort(Point2D[] a, int L, int R) {
        int M = (L + R) / 2;
        if (L < M){
        	mergeSort(a, L, M);
        }
        if (M + 1 < R){
        	mergeSort(a, M + 1, R);
        }
        merge(a, L, M, R);
    }
    
    private static void merge(Point2D[] a, int L, int M, int R) {
    	Point2D[] b = new Point2D[R-L+1];
    	int i = L;
    	int j = M + 1;
    	int k;
    	for (k = L; k <= R; k++){
    		if ((i > M) || ((j <= R) && (a[j].getY() < a[i].getY()))) {
    			b[k-L] = a[j];
    			j = j + 1;
    		} 
    		else {
    			b[k-L] = a[i];
    			i = i + 1;
    		}
    		
    	}
    	for (k = L; k <= R; k++){
    	
    		a[k] = b[k-L];
    		
    	}
    }
	
}

