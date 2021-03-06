import java.awt.geom.Point2D;
import java.util.Comparator;

public class HeuristicComparator implements Comparator<Point2D>{

	@Override
	public int compare(Point2D o1, Point2D o2) {
		int res= (int) (o1.getY()-o2.getY());
		if(res==0){return res;}
		return res/Math.abs(res);
	}
	
}
