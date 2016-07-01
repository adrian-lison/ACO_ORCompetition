import java.util.ArrayList;

public class Solution {
	private MessageControl m = new MessageControl();
	ArrayList<Customer> customerOrder;
	private int latestPCI; 	//index of the latest potential customer
	private int use;    	//Overall cumulated use of packages
	private int length; 	//Overall travelling distance 
	private int weight; 	//Overall package weight used in this solution
	private int firstIndex;

	public Solution(){
		use=0;
		length=0;
		weight=0;
		customerOrder = new ArrayList<Customer>();
		firstIndex=0;
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
		if(customerOrder.size()>1) this.length+=c.distanceTo(getLastCustomer()); //distance to last customer	
		this.customerOrder.add(c);
		if((customerOrder.size()>2 && customerOrder.get(1)!=c) || customerOrder.size()==2){	
			this.weight += c.getWeight();
			this.use+= c.getUse();
		}
		
	}


	public Customer getLastCustomer(){
		if(customerOrder.size()>0){
			return customerOrder.get(customerOrder.size()-1);}
		else{
			return null;
		}
	}
	
	public int getFirst(){
		return firstIndex;
	}
	
	public void setFirst(int value){this.firstIndex=value;}

	public String print(){
		String s="";
		for(int i=1;i<customerOrder.size();i++){
			s += customerOrder.get(i).getId() + " - ";
		}
		return s.substring(0,s.length()-3);
	}

}
