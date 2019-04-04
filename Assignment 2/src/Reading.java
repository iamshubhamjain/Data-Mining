import java.util.ArrayList;

public class Reading {
	
	private int noOfSeq;
	private ArrayList<String> dnas = new ArrayList<String>();
	
	public void setDnas(String s) {
		this.dnas.add(s);
	}
	
	public void setnoOfSeq(int i) {
		this.noOfSeq=i;
	}
	
	public int getNoOfSeq() {
		return noOfSeq;
	}
	
	public ArrayList<String> getDnas() {
		return dnas;
	}
}
