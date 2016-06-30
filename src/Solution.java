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
	
	
	public int getWeight(){
		return weight;
	}
	
	public int getLatestPCI(){
		return latestPCI;
	}
	
	public void setLatestPCI(int pci){
		this.latestPCI=pci;
	}
	
	public ArrayList<Customer> getCustomerOrder(){
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
	
	public Customer getLastCustomer(){
		if(customerOrder.size()>0){
		return customerOrder.get(customerOrder.size()-1);}
		else{
			return null;
		}
	}
	
	
}
