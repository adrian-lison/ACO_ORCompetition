import java.util.ArrayList;
import java.util.List;

public class ProblemInstance {
	private String name;
	private int maxCapacity, round;
	private Customer[] customers;
	public ArrayList<HistoryEntry>[] pheromoneHistory;
	public double evaporationRate,min,max, pheromoneInitialValue;

	@SuppressWarnings("unchecked")
	public ProblemInstance(String name, int maxCapacity, Customer[] customers){
		this.name=name;
		this.maxCapacity=maxCapacity;
		this.customers=customers;

		//set specific values to default
		evaporationRate=0.95;
		min=0;
		max=1;
		pheromoneInitialValue=max;

		//create pheromone history
		pheromoneHistory = new ArrayList[getNumberOfCustomers()];
		for(int i=0; i<pheromoneHistory.length;i++){
			pheromoneHistory[i] = new ArrayList<HistoryEntry>();}
	}

	public void setPheromoneValue(Customer from, Customer to, double value) throws IndexOutOfBoundsException{
		ArrayList<HistoryEntry> tos;
		if(from.getId()<pheromoneHistory.length) tos = pheromoneHistory[from.getId()];
		else throw new java.lang.IndexOutOfBoundsException("Customer " + from.getId() + " is not listed in pheromone history.");

		for(int i=0;i<tos.size();i++){
			HistoryEntry a  = tos.get(i);
			if(a.getTo()==to && a.getFrom()==from){ //second condition should be redundant
				a.setValue(Math.max(min,Math.min(max, value)));
				a.setRound(this.round);
				return;
			}
		}
		tos.add(0, new HistoryEntry(from, to, this.round, Math.max(min,Math.min(max, value))));
	}

	
	/**
	 * Returns the pheromone value of a connection between two customers considering evaporation.
	 * <br> After having determined the pheromone value, this is also updated in the pheromone history.
	 * @param from Start Customer
	 * @param to Next Customer
	 * @return pheromone value
	 * @throws IndexOutOfBoundsException
	 */
	public double getPheromoneValue(Customer from, Customer to) throws IndexOutOfBoundsException{
		ArrayList<HistoryEntry> tos;
		if(from.getId()<pheromoneHistory.length) tos = pheromoneHistory[from.getId()];
		else throw new java.lang.IndexOutOfBoundsException("Customer " + from.getId() + " is not listed in pheromone history.");

		for(int i=0;i<tos.size();i++){
			HistoryEntry a  = tos.get(i);
			if(a.getTo()==to && a.getFrom()==from){ //second condition should be redundant
				a.setValue(Math.max(min, Math.min(max,a.getValue()*Math.pow(this.evaporationRate,this.round-a.getRound()))));//update level considering evaporation value
				a.setRound(this.round); //updated round
				return a.getValue(); //updated pheromone value
			}
		}
		double newbieValue=Math.max(min,Math.min(max,this.pheromoneInitialValue*Math.pow(this.evaporationRate,this.round)));
		tos.add(0, new HistoryEntry(from, to, this.round, newbieValue)); //create new entry	}
		return newbieValue;
	}
	
	public void increasePheromoneValue(Customer from, Customer to, double value) throws IndexOutOfBoundsException{
		ArrayList<HistoryEntry> tos;
		if(from.getId()<pheromoneHistory.length) tos = pheromoneHistory[from.getId()];
		else throw new java.lang.IndexOutOfBoundsException("Customer " + from.getId() + " is not listed in pheromone history.");

		for(int i=0;i<tos.size();i++){
			HistoryEntry a  = tos.get(i);
			if(a.getTo()==to && a.getFrom()==from){ //second condition should be redundant
				a.setValue(Math.max(min, Math.min(max, (value + a.getValue()*Math.pow(this.evaporationRate,this.round-a.getRound())))));//update level considering evaporation value
				a.setRound(this.round); //updated round
				return;
			}
		}
		double newbieValuePlus=Math.max(min, Math.min(max, value+this.pheromoneInitialValue*Math.pow(this.evaporationRate,this.round)));
		tos.add(0, new HistoryEntry(from, to, this.round, newbieValuePlus)); //create new entry	}	
	}
		
		
	
	public String getName(){return name;}
	public Customer[] getCustomers(){return customers;}
	public int getNumberOfCustomers(){return customers.length;}
	public int getMaxCapacity(){return maxCapacity;}
	public int getRound(){return round;}
	public void setRound(int round){this.round=round;}
	public void setMin(double min){this.min=min;}
	public double getMin(){return this.min;}
	public void setMax(double max){this.max=max;}
	public double getMax(){return this.max;}
	public void setEvaporationRate(double evaporationRate){this.evaporationRate=evaporationRate;}
	public double getEvaporationRate(){return this.evaporationRate;}
	public void setPheromoneInitialValue(double value){this.pheromoneInitialValue=value;}
}
