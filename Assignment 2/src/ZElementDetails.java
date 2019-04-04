import java.util.ArrayList;

public class ZElementDetails {

	private final int i;
	private final int j;
	private final double dist;
	private final int noOfPoints;
	private ArrayList<Integer> aL = new ArrayList<Integer>();		
	
	public ZElementDetails(int i, int j, double dist, int noOfPoints,ArrayList<Integer> aL) {
		this.i=i;
		this.j=j;
		this.dist=dist;
		this.noOfPoints=noOfPoints;
		this.aL=aL;
	}
		
	public int getI() {
		return i;
	}
	
	public int getJ() {
		return j;
	}
	
	public double getDist() {
		return dist;
	}
	
	public int getNoOfPoints() {
		return noOfPoints;
	}
	
	public ArrayList<Integer> getAL() {
		return aL;
	}
}
