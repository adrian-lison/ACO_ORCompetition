
public class HistoryEntry {
private Customer from, to;
private int round;
private double value;

public HistoryEntry(Customer from, Customer to, int round, double value){
	this.from=from;
	this.to=to;
	this.round=round;
	this.value=value;
}

public Customer getFrom(){return from;}
public Customer getTo(){return to;}
public int getRound(){return round;}
public void setRound(int round){this.round=round;}
public double getValue(){return value;}
public void setValue(double value){this.value=value;}


}
