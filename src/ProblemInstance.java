import java.util.ArrayList;
import java.util.List;

public class ProblemInstance {
private String name;
private int maxCapacity, round;
private Customer[] customers;
public ArrayList<HistoryEntry>[] pheromoneHistory;
public double evaporationRate,min,max;

@SuppressWarnings("unchecked")
public ProblemInstance(String name, int maxCapacity, Customer[] customers){
	this.name=name;
	this.maxCapacity=maxCapacity;
	this.customers=customers;
	
	//set specific values to default
	evaporationRate=0.95;
	min=0;
	max=1;
	
	//create pheromone history
	pheromoneHistory = new ArrayList[getNumberOfCustomers()];
	for(ArrayList n: pheromoneHistory){
		n = new ArrayList<HistoryEntry>();	}
}

	

public String getName(){return name;}
public Customer[] getCostumers(){return customers;}
public int getNumberOfCustomers(){return customers.length;}
public int getMaxCapacity(){return maxCapacity;}
public int getRound(){return round;}
public void setRound(int round){this.round=round;}

}
