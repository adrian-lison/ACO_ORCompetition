import java.util.Random;
/*
 * Random generator using the java.util.Random class
 */
public class JavaRandom extends RandomNumberGenerator {

	Random r = new Random();
	@Override
	public double getRandomDouble(double range) {
		return r.nextDouble()*range; //returns a pseudorandom value between 0 (inclusive) and range
	}

	
}