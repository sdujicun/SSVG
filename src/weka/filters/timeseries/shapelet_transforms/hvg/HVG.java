package weka.filters.timeseries.shapelet_transforms.hvg;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import fileIO.DataSets;
import weka.core.Instance;
import weka.core.Instances;

public class HVG extends VG{
	

	public int[] convertHVGNumber(Instance series){
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
				double min=Math.min(xi, xj);
				double temp=(xj-xi)/(j-i);
				boolean visible=true;
				for(int k=i+1;k<j;k++){
					
					double x=xi+(k-i)*temp;
					if(x<=data[k]||data[k]>=min){
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
	
	public int[] diffHVGNumber(Instance series){		
		int[] HVGNumber=convertHVGNumber(series);
		int[] diff=new int[HVGNumber.length];
		diff[0]=0;
		for(int i=1;i<diff.length;i++){
			diff[i]=HVGNumber[i]-HVGNumber[i-1];
		}
		return diff;
	}
		
	public int[] getHVGIndex(Instance series, int minDiff){		
		List<Integer> list=new ArrayList<Integer>();;
		list.add(0);
		int[] diff=diffHVGNumber(series);
		for(int i=1;i<diff.length-1;i++){
			if(diff[i]>=minDiff){
				list.add(i);
			}
		}
		list.add(diff.length-1);
		int[] index=new int[list.size()];
		for(int i=0;i<index.length;i++){
			index[i]=list.get(i);
		}
		return index;
	}
	
	
}
