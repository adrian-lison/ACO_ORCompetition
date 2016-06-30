
public class Heuristic {
	
	double useWeight,lengthWeight;
	
	public Heuristic(double useWeight, double lengthWeight){
		this.useWeight=useWeight;
		this.lengthWeight=lengthWeight;
	}
	
	/**
	 * Returns a heuristic value that rates the decision to go to a certain node next
	 * @param preNode Our current node
	 * @param node The node to be looked at
	 * @param start The node which we come from and have to return to
	 * @return A heuristic value
	 */
	public double getHeuristicValue(Customer preNode, Customer node, Customer start)
	{
		double useRatio=this.useWeight/(useWeight+lengthWeight);
		double lengthRatio=this.lengthWeight/(useWeight+lengthWeight);
		double pathCost = (preNode.distanceTo(node) + (start.distanceTo(node)-start.distanceTo(preNode))) * lengthRatio;
		double nodeUse = node.getUse()*useRatio;
		return ((nodeUse-pathCost)/node.getWeight()); //Profit per Weight
	}
}
