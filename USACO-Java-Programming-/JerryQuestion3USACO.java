import java.io.*;
import java.util.*;

public class JerryQuestion3USACO {
    
    	public static void main(String [] args) throws Exception {
        	Scanner scan = new Scanner(System.in);
        	String input_num = scan.nextLine();
        	int totalN = Integer.parseInt(input_num);
        	String[] pointDir = new String[totalN];
        	String[] input = new String[totalN];
        	int[] x = new int[totalN];
        	int[] y = new int[totalN];
        	for(int i = 0; i < totalN; i++) {
            	input[i] = scan.nextLine();
            	String[] _str = input[i].split(" ");
            	pointDir[i] = _str[0];
            	x[i] = Integer.parseInt(_str[1]);
            	y[i] = Integer.parseInt(_str[2]);
        	}
        	scan.close();
           	      	 
        	int n_Num = 0;
        	int e_Num = 0;
        	for(int i = 0; i < totalN; i++) {
            	if(pointDir[i].equals("N")) {
                	n_Num++;
            	}
            	else {
                	e_Num++;
            	}
        	}
       	        	 
        	int _n_Num = 0;
        	int _e_Num = 0;
        	int[] x_North = new int[n_Num];
        	int[] y_North = new int[n_Num];
        	int[] x_East = new int[e_Num];
        	int[] y_East = new int[e_Num];
        	for(int i = 0; i < totalN; i++) {
            	if(pointDir[i].equals("N")) {
                	x_North[_n_Num] = x[i];
                	y_North[_n_Num] = y[i];
                	_n_Num++;
            	}
            	else {
                	x_East[_e_Num] = x[i];
                	y_East[_e_Num] = y[i];
                	_e_Num++;
            	}
        	}    
       	 
        	//sort north X from small to large
        	sortArrayByValX(x_North, y_North, true);
        	//sort easter X from large to small
        	sortArrayByValX(x_East, y_East, false);
        	adjustSortArrayYValSmallFront(x_North, y_North);
        	adjustSortArrayYValSmallFront(x_East, y_East);
        	
        	String[] resultFinal = new String[totalN];
        	String[] resNorth = new String[n_Num];
        	String[] resEast = new String[e_Num];
        	for(int i = 0; i < e_Num; i++) {
            	resEast[i] = "Infinity";
        	}
        	for(int i = 0; i < n_Num; i++) {
            	resNorth[i] = "Infinity";
        	}
       	 
        	//calculate
        	for(int iEast = 0; iEast < e_Num; iEast++) {
            	for(int iNorth = 0; iNorth < n_Num; iNorth++) {
                	//only conside easter x less than north x
                	if(x_East[iEast] <= x_North[iNorth] && y_East[iEast] >= y_North[iNorth])
                	{
                    	if(!resNorth[iNorth].equals("Infinity")) {
                        	if(y_East[iEast] > y_North[iNorth] + Integer.parseInt(resNorth[iNorth])){
                            	continue;
                        	}
                    	}
                   	 
                    	int crossYNorth = x_North[iNorth] - x_East[iEast] + y_North[iNorth];
                   	 
                    	if(crossYNorth < y_East[iEast]) {
                        	//north stop
                        	int pre_min = Integer.MAX_VALUE;
                        	int numEat = y_East[iEast] - y_North[iNorth];
                        	/*
                        	for(int iNorth1 = 0; iNorth1 < n_Num; iNorth1++) {
                       		 	if(x_North[iNorth] == x_North[iNorth1] && y_North[iNorth] < y_North[iNorth1] ) {
                       		 		int numEat1 = y_North[iNorth1] - y_North[iNorth];
                       		 		if(numEat1 < pre_min) {
                       		 			pre_min = numEat1;
                       		 		}
                       		 	}
                        	}
                        	numEat = Math.min(pre_min, numEat);
                        	*/
                        	if(numEat >= 0) {
                       		 	if(!resNorth[iNorth].equals("Infinity")) {
                       		 		resNorth[iNorth] = String.valueOf(Math.min(numEat, Integer.parseInt(resNorth[iNorth])));
                       		 	}
                       		 	else {
                       		 		resNorth[iNorth] = String.valueOf(numEat);
                       		 	//resNorth[iNorth] = String.valueOf(Math.min(numEat, Integer.parseInt(resNorth[iNorth])));
                       		 	}
                        	}
                    	}
                    	else if(crossYNorth > y_East[iEast]) {
                        	//easter stop
                        	//int pre_min = Integer.MAX_VALUE;
                        	int numEat = x_North[iNorth] - x_East[iEast];
                        	//check if there is previous one
                        	/*
                        	for(int iEast1 = 0; iEast1 < e_Num; iEast1++) {
                        		if(y_East[iEast] == y_East[iEast1] && x_East[iEast1] < x_East[iEast] ) {
                       		 		int numEat1 = x_East[iEast] - x_East[iEast1];
                       		 		if(numEat1 < pre_min) {
                       		 			pre_min = numEat1;
                       		 		}
                       		 	}
                        	}
                        	numEat = Math.min(pre_min, numEat);
                        	*/
                        	if(numEat >= 0) {
                       		 	if(!resEast[iEast].equals("Infinity")) {
                       		 		resEast[iEast] = String.valueOf(Math.min(numEat, Integer.parseInt(resEast[iEast])));
                       		 	}
                       		 	else {
                        		//resEast[iEast] = String.valueOf(Math.min(numEat, Integer.parseInt(resEast[iEast])));
                       		 		resEast[iEast] = String.valueOf(numEat);
                       		 	}
                        	}
                    	}
                    	else {
                    		/*
                   		 	//equal pass
                        	//easter stop
                        	int pre_min = Integer.MAX_VALUE;
                        	//check if there is previous one
                        	for(int iEast1 = 0; iEast1 < e_Num; iEast1++) {
                       		 	if(y_East[iEast] == y_East[iEast1] && x_East[iEast] < x_East[iEast1] ) {
                       		 		int numEat1 = x_East[iEast1] - x_East[iEast];
                       		 		if(numEat1 < pre_min) {
                       		 			pre_min = numEat1;
                       		 		}
                       		 	}
                        	}
                        	if(pre_min < Integer.MAX_VALUE) {                           	 
                       		 	if(!resEast[iEast].equals("Infinity")) {
                       		 		resEast[iEast] = String.valueOf(Math.min(pre_min, Integer.parseInt(resEast[iEast])));
                       		 	}
                       		 	else {
                       		 		resEast[iEast] = String.valueOf(pre_min);
                       		 	}
                        	}
                       	 
                        	//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
                        	//north stop
                        	int pre_min2 = Integer.MAX_VALUE;
                        	//check if there is previous one
                        	for(int iNoth1 = 0; iNoth1 < n_Num; iNoth1++) {
                       		 	if(x_North[iNorth] == x_North[iNoth1] && y_North[iNorth] < y_North[iNoth1] ) {
                       		 		int numEat1 = y_North[iNoth1] - y_North[iNorth];
                       		 		if(numEat1 < pre_min2) {
                       		 			pre_min2 = numEat1;
                       		 		}
                       		 	}
                        	}
                        	if(pre_min2 < Integer.MAX_VALUE) {
                       		 	if(!resNorth[iNorth].equals("Infinity")) {
                       		 		resNorth[iNorth] = String.valueOf(Math.min(pre_min2, Integer.parseInt(resNorth[iNorth])));
                       		 	}
                       		 	else {
                       		 		resNorth[iNorth] = String.valueOf(pre_min2);
                       		 	}
                        	}
*/
                    	}
                	}// end if
            	}
        	}//end compare for
       	 
        	//obvious resFin
        	//int y_N_min = getMin(y_North);
        	for(int i = 0; i < e_Num; i++) {
            	//if(y_East[i] < y_N_min) {
           		 	int[] x_E_yEqual = getYEqualxArray(y_East[i], x_East, y_East);
           		 	//int xMax = getMax(x_E_yEqual);
           		 	if(x_E_yEqual.length > 0) {
           		 		//resEast[i] = String.valueOf(xMax - x_East[i]);
           		 		//int eatE = Integer.MAX_VALUE;
           		 		for(int kk = 0; kk < e_Num; kk++)
           		 		{
           		 			if(x_E_yEqual[kk] == -1) {
           		 				continue;
           		 			}
           		 			for(int ll = kk + 1; ll < e_Num; ll++)
           		 			{
	           		 			if(x_E_yEqual[ll] == -1) {
	           		 				continue;
	           		 			}
           		 				int eatE = x_E_yEqual[kk]  - x_E_yEqual[ll];
           		 				
           		 				if(!resEast[ll].equals("Infinity")) {
           		 					resEast[ll] = String.valueOf(Math.min(eatE, Integer.parseInt(resEast[ll])));
           		 				}
           		 				else {
           		 					resEast[ll] = String.valueOf(eatE);
           		 				}
           		 				//eatE = Math.min(eatE, x_E_yEqual[kk]  - x_E_yEqual[kk+1] );
           		 			}
           		 		}//end for
           		 		//resEast[i] = String.valueOf(Math.min(eatE, xMax - x_East[i]));
           		 	}
           		 	//else {
           		 	//	resEast[i] = "Infinity";
           		 	//}
            	//}
        	}
       	 
        	//int x_E_min = getMin(x_East);
        	for(int i = 0; i < n_Num; i++) {
       		 //check one vertical    		 
        		//if(x_North[i] < x_E_min) {
           		 	int[] y_N_xEqual = getXEqualyArray(x_North[i], x_North, y_North);
           		 	
           		 	//int yMax = getMax(y_N_xEqual);
           		 	if(y_N_xEqual.length > 0) {
           		 		//resNorth[i] = String.valueOf(yMax - y_North[i]);
           		 		//int eatN = Integer.MAX_VALUE;
           		 		for(int kk = n_Num - 1; kk >= 0; kk--)
           		 		{
           		 			if(y_N_xEqual[kk] == -1) {
           		 				continue;
           		 				//eatN = Math.min(eatN, y_N_xEqual[kk]  - y_North[i] );
           		 			}
        		 			for(int ll = kk - 1; ll >= 0; ll--)
           		 			{
	           		 			if(y_N_xEqual[ll] == -1) {
	           		 				continue;
	           		 			}
           		 				int eatN = y_N_xEqual[kk]  - y_N_xEqual[ll];
           		 				
           		 				if(!resNorth[ll].equals("Infinity")) {
           		 					resNorth[ll] = String.valueOf(Math.min(eatN, Integer.parseInt(resNorth[ll])));
           		 				}
           		 				else {
           		 					resNorth[ll] = String.valueOf(eatN);
           		 				}
           		 				//eatE = Math.min(eatE, x_E_yEqual[kk]  - x_E_yEqual[kk+1] );
           		 			}
           		 		}// end for
           		 		//resNorth[i] = String.valueOf(Math.min(eatN, yMax - y_North[i]));
           		 	}//end if
           		 	//else {
           		 	//	resNorth[i] = "Infinity";
           		 	//}
            	//}
        	}
       	 
       	 
        	//output
        	String[] resFinAllData_N = new String[n_Num];
        	String[] resFinAllData_E = new String[e_Num];
        	for(int i = 0; i < n_Num; i++) {
            	resFinAllData_N[i] = "N " + x_North[i] + " " + y_North[i];
        	}
        	for(int i = 0; i < e_Num; i++) {
            	resFinAllData_E[i] = "E " + x_East[i] + " " + y_East[i];
        	}
       	 
        	for(int i = 0; i < totalN; i++)
        	{
            	for(int j = 0; j < n_Num; j++) {
                	if(input[i].contains(resFinAllData_N[j])) {
                		resultFinal[i] = resNorth[j];
                    	break;
                	}
            	}
            	for(int j = 0; j < e_Num; j++) {
                	if(input[i].contains(resFinAllData_E[j])) {
                		resultFinal[i] = resEast[j];
                    	break;
                	}
            	}
        	}
       	 
        	//final output
        	for(int i = 0; i < totalN; i++) {
            	System.out.println(resultFinal[i]);
        	}
	}
	
	public static int getMax(int[] a) {
    	int maxV = Integer.MIN_VALUE;
    	for(int i = 0; i < a.length; i++)
    	{
        	if(a[i] > maxV && a[i] > -1) {
            	maxV = a[i];
        	}
    	}
    	return maxV;
	}
	
	public static int getMin(int[] a) {
    	int minV = Integer.MAX_VALUE;
    	for(int i = 0; i < a.length; i++)
    	{
        	if(a[i] < minV) {
            	minV = a[i];
        	}
    	}
    	return minV;
	}
    
	public static int[] getXEqualyArray(int xValue, int[] x, int[] y) {
   	 int[] yArray = new int[y.length];
   	 for(int i = 0; i < y.length; i++) {
   		 yArray[i] = -1;
   	 }
   	 
   	 for(int i = 0; i < x.length; i++) {
   		 if(x[i] == xValue) {
   			 yArray[i] = y[i];
   		 }
   	 }
   	 return yArray;
	}
    
	public static int[] getYEqualxArray(int yValue, int[] x, int[] y) {
   	 int[] xArray = new int[x.length];
   	 for(int i = 0; i < x.length; i++) {
   		 xArray[i] = -1;
   	 }
   	 
   	 for(int i = 0; i < y.length; i++) {
   		 if(y[i] == yValue) {
   			 xArray[i] = x[i];
   		 }
   	 }
   	 return xArray;
	}
    
	public static void sortArrayByValX(int[] x, int[] y, boolean bUp) {
    	int temp = 0;
    	for(int i = 0; i < x.length; i++) {
        	for(int j = i + 1; j < x.length; j++) {
            	if(bUp) {
                	if(x[i] > x[j]) {
                    	temp = x[i];
                    	x[i] = x[j];
                    	x[j] = temp;
                    	temp = y[i];
                    	y[i] = y[j];
                    	y[j] = temp;
                	}
            	}
            	else {
                	if(x[i] < x[j]) {
                    	temp = x[i];
                    	x[i] = x[j];
                    	x[j] = temp;
                    	temp = y[i];
                    	y[i] = y[j];
                    	y[j] = temp;
                	}
            	}
        	}
    	}
	}
	
	public static void adjustEastSortArrayYValSmallFront(int[] x, int[] y) {
    	int repeatX = 1;
    	for(int i = 0; i < x.length - 1; i++) {
    		//int repeatX = 1;
    		if(x[i] == x[i+1]) {
    			repeatX++;
    			continue;
    		}
    		else {
    			//adjust y value
    			if(repeatX > 1) {
    				for(int j = 0; j < repeatX; j++) {
    					for(int k = j+ 1; k < repeatX; k++) {
    						if(y[i - j] < y[i - k]) {
    							int temp_ = y[i - j];
    							y[i - j] = y[i - k];
    							y[i - k] = temp_;
    						}
    					}
    				}
    			}
    			
    			//final
    			repeatX = 1;
    		}
    	}
	}	

	
	public static void adjustSortArrayYValSmallFront(int[] x, int[] y) {
    	int repeatX = 1;
    	for(int i = 0; i < x.length; i++) {
    		if((i+1) < x.length && x[i] == x[i+1]) {
    			repeatX++;
    			continue;
    		}
    		else {
    			//adjust y value
    			if(repeatX > 1) {
    				for(int j = 0; j < repeatX; j++) {
    					for(int k = j+ 1; k < repeatX; k++) {
    						if(y[i - j] < y[i - k]) {
    							int temp_ = y[i - j];
    							y[i - j] = y[i - k];
    							y[i - k] = temp_;
    						}
    					}
    				}
    			}
    			
    			//final
    			repeatX = 1;
    		}
    	}
	}	
}




