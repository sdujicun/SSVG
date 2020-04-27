package weka.filters.timeseries.shapelet_transforms.hvg;

import java.io.File;

import fileIO.DataSets;
import weka.core.Instance;
import weka.core.Instances;

public class VG {
	public int[][] convertVG(Instance series){
		 //remove class attribute if needed
		double[] data = series.toDoubleArray();		
        int c = series.classIndex();
        if(c >= 0) {
        	double[] temp;
            temp=new double[data.length-1];
            System.arraycopy(data,0,temp,0,c); //assumes class attribute is in last index
            data=temp;
        }
		
		int length=data.length;
		int[][] visibilityGraphs=new int[length][length];
		for(int i=0;i<length-1;i++){
			double xi=data[i];
			for(int j=i+1;j<length;j++){
				double xj=data[j];
				double temp=(xj-xi)/(j-i);
				boolean visible=true;
				for(int k=i+1;k<j;k++){
					double x=xi+(k-i)*temp;
					if(x<=data[k]){
						visible=false;
						break;
					}
				}
				if(visible){
					visibilityGraphs[i][j]=1;
					visibilityGraphs[j][i]=1;
				}
				
			}
		}
				
		return visibilityGraphs;
		
		
	}
	
	public int[] convertVGNumber(Instance series){
		 //remove class attribute if needed
		double[] data = series.toDoubleArray();		
       int c = series.classIndex();
       if(c >= 0) {
       	double[] temp;
           temp=new double[data.length-1];
           System.arraycopy(data,0,temp,0,c); //assumes class attribute is in last index
           data=temp;
       }
		
		int length=data.length;
		int[] number=new int[length];
		for(int i=0;i<length-1;i++){
			double xi=data[i];
			for(int j=i+1;j<length;j++){
				double xj=data[j];
				double temp=(xj-xi)/(j-i);
				boolean visible=true;
				for(int k=i+1;k<j;k++){
					double x=xi+(k-i)*temp;
					if(x<=data[k]){
						visible=false;
						break;
					}
				}
				if(visible){
					number[i]+=1;
					number[j]+=1;
				}
				
			}
		}
		
		return number;
	}
	
	public static void main(String[] args){
		final String location = DataSets.problemPath;
		final String dataset = "Coffee";
		final String filePath = location + File.separator + dataset + File.separator + dataset;

		Instances test, train;
		test = utilities.ClassifierTools.loadData(filePath + "_TEST");
		
		
		new VG().convertVGNumber(test.instance(8));
		
	}
}
