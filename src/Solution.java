import java.util.ArrayList;

public class Solution {

	ArrayList<Customer> customerOrder;
	private int latestPCI; 	//index of the latest potential customer
	private int use;    	//Overall cumulated use of packages
	private int length; 	//Overall travelling distance 
	private int weight; 	//Overall package weight used in this solution
	
	public Solution(){
		use=0;
		length=0;
		weight=0;
		customerOrder = new ArrayList<Customer>();
	}
	
	public int getUse(){
		return use;
	}
	
	public int getLength(){
		return length;
	}
	
	/*
	 * profit is the difference between use and length, which refers to the problem of finding a path that has an optimal profit consisting of the length of the path and the gained use with a 50% ratio each. 
	 */
	public int getProfit(){
		return use-length;
	}
	
	public int getWeight(){
		return weight;
	}
	
	public int getLatestPCI(){
		return latestPCI;
	}
	
	public void setLatestPCI(int pci){
		this.latestPCI=pci;
	}
	
	public ArrayList<Customer> getCustomerOder(){
		return customerOrder;
	}
	
	/**
	 * Adds a customer to the Order of Customers and updated statistics
	 * @param c Customer to be added
	 */
	public void addCustomer(Customer c){
		this.customerOrder.add(c);
		this.weight += c.getWeight();
		this.use+= c.getUse();
		this.length+=c.distanceTo(getLastCustomer()); //distance to last customer
	}
	
	public Customer getLastCustomer(){return customerOrder.get(customerOrder.size()-1);}
	
	
}
