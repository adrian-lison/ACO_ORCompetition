import java.awt.Point;

public class Customer {
private Point coordinates;
private int id, packageWeight, packageUse;

public Customer(int id, Point coordinates, int weight, int use){
	this.coordinates=coordinates;
	this.packageWeight=weight;
	this.packageUse=use;
	this.id=id;
}

public double distanceTo(Customer other){
	return (double) Point.distance(coordinates.getX(), coordinates.getY(), other.getCoordinates().getX(), other.getCoordinates().getY());
}

public Point getCoordinates(){return coordinates;}
public int getWeight(){return packageWeight;}
public int getUse(){return packageUse;}
public void setId(int id){this.id=id;}
public int getId(){return this.id;}
}
