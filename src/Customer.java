import java.awt.geom.Point2D;



public class Customer {
private Point2D coordinates;
private int id, packageWeight, packageUse;

public Customer(int id, Point2D coordinates, int weight, int use){
	this.coordinates=coordinates;
	this.packageWeight=weight;
	this.packageUse=use;
	this.id=id;
}

public double distanceTo(Customer other){
	if(other==null){return 0;}
	return (double) Point2D.distance(coordinates.getX(), coordinates.getY(), other.getCoordinates().getX(), other.getCoordinates().getY());
}

public Point2D getCoordinates(){return coordinates;}
public int getWeight(){return packageWeight;}
public int getUse(){return packageUse;}
public void setId(int id){this.id=id;}
public int getId(){return this.id;}
}
