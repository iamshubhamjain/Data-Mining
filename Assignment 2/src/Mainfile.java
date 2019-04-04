import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static java.lang.Math.*;

public class Mainfile {

	public static void main(String[] args) throws IOException {
		Reading r=read();
		PrintWriter writer = new PrintWriter("D:\\\\stuff\\\\Academics\\\\4-2\\\\Data Mining\\\\Assignment 2\\\\the-file-name.txt", "UTF-8");
		writer.println(r.getDnas());
		writer.close();
		
		int maxSize=2*r.getNoOfSeq();
		double[][] simMat=new double[maxSize][maxSize];

		for(int i=0;i<r.getNoOfSeq();i++) {
			for(int j=0;j<i;j++) {
				int lcs=getLcs(r.getDnas().get(i).toString().toCharArray(),r.getDnas().get(j).toString().toCharArray(),r.getDnas().get(i).toString().length(),r.getDnas().get(j).toString().length());
				simMat[i][j]=lcs/(r.getDnas().get(i).toString().length()+r.getDnas().get(j).toString().length());
			}
		}
		//System.out.println(simMat);

		double[][] Z= new double[r.getNoOfSeq()][4];
		ArrayList<Integer> aL = new ArrayList<Integer>();
		
		for(int i=0;i<r.getNoOfSeq()-1;i++) {
			 ZElementDetails e= getElementDetails(maxSize,maxSize/2-1+i,simMat,Z,aL);
			 Z[i][0]=e.getI();
			 Z[i][1]=e.getJ();
			 Z[i][2]=e.getDist();
			 Z[i][3]=e.getNoOfPoints();
			 aL=e.getAL();
		}
		
	}

	static Reading read() throws IOException{

		Scanner in = new Scanner(new FileReader("D:\\stuff\\Academics\\4-2\\Data Mining\\Assignment 2\\humangenednasequences.txt"));
		in.useDelimiter(">");
		StringBuilder sb = new StringBuilder();
		Reading r=new Reading();
		int count=0;
		String dna;
		while(in.hasNext()) {
			count++;
			sb.append(in.next());
			dna=sb.toString();
			dna = dna.replace(System.getProperty("line.separator"), "");
			dna=dna.replace("chr", "");
			dna=dna.replace("_","");
			dna = dna.replaceAll("[0-9]","");
			r.setDnas(dna);
			sb.setLength(0);
		}
		in.close();
		r.setnoOfSeq(count);
		return r;
	}

	static int getLcs( char[] X, char[] Y, int m, int n ){
		if (m == 0 || n == 0)
			return 0;
		if (X[m-1] == Y[n-1])
			return 1 + getLcs(X, Y, m-1, n-1);
		else
			return max(getLcs(X, Y, m, n-1), getLcs(X, Y, m-1, n));
	}

	static int max(int a, int b){
		return (a > b)? a : b;
	}
	
	static ZElementDetails getElementDetails(int maxSize,int currSize, double[][] simMat,double[][] Z,ArrayList<Integer> aL) {
		int i = 0,j = 0;
		double dist=0;
		for(int k=0;k<currSize;k++) {
			if(aL.contains(k))
				continue;
			for(int l=0;l<k;l++) {
				if(aL.contains(l))
					continue;
				if(simMat[k][l]>dist) {
					i=k;
					j=l;
					dist=simMat[k][l];
				}	
			}
		}
		simMat[i][j]=0;
		aL.add(i);
		aL.add(j);
		/*
		for(int k=0;k<currSize;k++) {
			simMat[i][k]=0;
		}
		for(int k=0;k<currSize;k++) {
			simMat[j][k]=0;
		}
		for(int k=0;k<currSize;k++) {
			simMat[k][i]=0;
		}
		for(int k=0;k<currSize;k++) {
			simMat[k][j]=0;
		}
		*/
		int noOfPointsInCluster=0;
		if(i<maxSize/2&&j<maxSize/2)
			noOfPointsInCluster=2;
		else if(i<maxSize/2&&j>=maxSize/2)
			noOfPointsInCluster=(int) (Z[j-(maxSize/2)][3]+1);
		else if(i>=maxSize/2&&j<maxSize/2)
			noOfPointsInCluster=(int) (Z[i-(maxSize/2)][3]+1);
		else
			noOfPointsInCluster=(int) (Z[i-(maxSize/2)][3]) + (int) (Z[j-(maxSize/2)][3]+1);
		
		for(int k=0;k<currSize+1;k++) {
			if(aL.contains(k)) {
				simMat[currSize][k]=0;
				continue;
			}
			simMat[currSize][k]=calculateDist(i,j,k,maxSize,simMat);
		}
			
		
		
		ZElementDetails e=new ZElementDetails(i,j,dist,noOfPointsInCluster,aL);
		return e;
	}
	
	static double calculateDist(int i,int j,int k, int maxSize, double[][] simMat) {
		return Math.max(simMat[k][i],simMat[k][j]);
	}
}
