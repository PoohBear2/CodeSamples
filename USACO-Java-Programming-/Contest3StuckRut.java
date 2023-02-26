import java.util.*;
import java.io.*;

public class Contest3StuckRut {
    
    	public static void main(String [] args) throws Exception {
    		//String inputFile = "/Users/jerryzhou/eclipse-workspace/USACO/src/q3.in";
        	Scanner _sc = new Scanner(System.in);
    		//Scanner _sc = new Scanner(new File(inputFile));
        	String input_num = _sc.nextLine();
        	int total = Integer.parseInt(input_num);
        	String[] direction = new String[total];
        	String[] input = new String[total];
        	int[] x = new int[total];
        	int[] y = new int[total];
        	for(int i = 0; i < total; i++) {
            	input[i] = _sc.nextLine();
            	String[] _str = input[i].split(" ");
            	direction[i] = _str[0];
            	x[i] = Integer.parseInt(_str[1]);
            	y[i] = Integer.parseInt(_str[2]);
        	}
        	_sc.close();
           	      	 
        	int eastTotal = 0;
        	int northTotal = 0;
        	for(int i = 0; i < total; i++) {
            	if(direction[i].equals("E")) {
                	eastTotal++;
            	}
            	else {
                   	northTotal++;
            	}
        	}
       	        	 
        	int _eastTotal = 0;
        	int _northTotal = 0;
        	int[] xNorth = new int[northTotal];
        	int[] yNorth = new int[northTotal];
        	int[] xEast = new int[eastTotal];
        	int[] yEast = new int[eastTotal];
        	for(int i = 0; i < total; i++) {
            	if(direction[i].equals("E")) {
                	xEast[_eastTotal] = x[i];
                	yEast[_eastTotal] = y[i];
                	_eastTotal++;
            	}
            	else {              	
                	xNorth[_northTotal] = x[i];
                	yNorth[_northTotal] = y[i];
                	_northTotal++;
            	}
        	}    
       	 
        	sortArrayByValX(xEast, yEast, false);
        	sortArrayByValX(xNorth, yNorth, true);
        	adjustYInSortedArray(xEast, yEast);
        	adjustYInSortedArray(xNorth, yNorth);
        	
        	String[] eastResult = new String[eastTotal];
        	String[] northResult = new String[northTotal];
        	String[] finalResult = new String[total];
        	for(int i = 0; i < eastTotal; i++) {
            	eastResult[i] = "Infinity";
        	}
        	for(int i = 0; i < northTotal; i++) {
            	northResult[i] = "Infinity";
        	}
       	 
        	//check x, y movement
        	boolean bStopEast = false;
        	boolean bStopNorth = false;
        	for(int iEast = 0; iEast < eastTotal; iEast++) {
            	for(int iNorth = 0; iNorth < northTotal; iNorth++) {
                	if(xEast[iEast] <= xNorth[iNorth] && yEast[iEast] >= yNorth[iNorth])
                	{
                    	if(!northResult[iNorth].equals("Infinity")) {
                        	if(yEast[iEast] > yNorth[iNorth] + Integer.parseInt(northResult[iNorth])){
                            	continue;
                        	}
                    	}
                    	if(!eastResult[iEast].equals("Infinity")) {
                        	if(xNorth[iNorth] > xEast[iEast] + Integer.parseInt(eastResult[iEast])){
                            	continue;
                        	}
                    	}              	 
                    	int intersectYNorth = xNorth[iNorth] - xEast[iEast] + yNorth[iNorth];
                   	 
                    	if(intersectYNorth < yEast[iEast]) {
                        	// stop north
                        	int numEat = yEast[iEast] - yNorth[iNorth];
                        	if(numEat >= 0) {
                       		 	if(!northResult[iNorth].equals("Infinity")) {
                       		 		northResult[iNorth] = String.valueOf(Math.min(numEat, Integer.parseInt(northResult[iNorth])));
                       		 	}
                       		 	else {
                       		 		northResult[iNorth] = String.valueOf(numEat);
                       		 	}
                        	}
                    	}
                    	else if(intersectYNorth > yEast[iEast]) {
                        	// stop east
                    		bStopEast = true;
                        	int numEat = xNorth[iNorth] - xEast[iEast];

                        	if(numEat >= 0) {
                       		 	if(!eastResult[iEast].equals("Infinity")) {
                       		 		eastResult[iEast] = String.valueOf(Math.min(numEat, Integer.parseInt(eastResult[iEast])));
                       		 	}
                       		 	else {
                       		 		eastResult[iEast] = String.valueOf(numEat);
                       		 	}
                        	}
                        	
                    	}
                	}// end if
            	} //end for north
        	}//end compare for east
       	 
        	//===========================================================================================
        	//check points at same horizon line and vertical line
        	for(int i = 0; i < eastTotal; i++) {
       		 	int[] xEast_yEqual = getHorizonArray(yEast[i], xEast, yEast);
       		 	if(xEast_yEqual.length > 0) {
       		 		for(int kk = 0; kk < eastTotal; kk++)
       		 		{
       		 			if(xEast_yEqual[kk] == -1) {
       		 				continue;
       		 			}
       		 			for(int ll = kk + 1; ll < eastTotal; ll++)
       		 			{
           		 			if(xEast_yEqual[ll] == -1) {
           		 				continue;
           		 			}
       		 				int eatE = xEast_yEqual[kk]  - xEast_yEqual[ll];
       		 				
       		 				if(!eastResult[ll].equals("Infinity")) {
       		 					eastResult[ll] = String.valueOf(Math.min(eatE, Integer.parseInt(eastResult[ll])));
       		 				}
       		 				else {
       		 					eastResult[ll] = String.valueOf(eatE);
       		 				}
       		 				break;
       		 			}
       		 		}//end for
       		 	}
        	}
       	 
        	for(int i = 0; i < northTotal; i++) {
       		 	//check one vertical    		 
       		 	int[] yNorth_xEqual = getVericalArray(xNorth[i], xNorth, yNorth);
       		 	
       		 	if(yNorth_xEqual.length > 0) {
       		 		for(int kk = northTotal - 1; kk >= 0; kk--)
       		 		{
       		 			if(yNorth_xEqual[kk] == -1) {
       		 				continue;
       		 			}
    		 			for(int ll = kk - 1; ll >= 0; ll--)
       		 			{
           		 			if(yNorth_xEqual[ll] == -1) {
           		 				continue;
           		 			}
       		 				int eatN = yNorth_xEqual[kk]  - yNorth_xEqual[ll];
       		 				
       		 				if(!northResult[ll].equals("Infinity")) {
       		 					northResult[ll] = String.valueOf(Math.min(eatN, Integer.parseInt(northResult[ll])));
       		 				}
       		 				else {
       		 					northResult[ll] = String.valueOf(eatN);
       		 				}
       		 				break;
      		 			}
       		 		}// end for
       		 	}//end if
        	}
       	 
       	 
        	//final output
        	String[] resFinAllData_N = new String[northTotal];
        	String[] resFinAllData_E = new String[eastTotal];
        	for(int i = 0; i < eastTotal; i++) {
            	resFinAllData_E[i] = "E" + " " + xEast[i] + " " + yEast[i];
        	}
        	for(int i = 0; i < northTotal; i++) {
            	resFinAllData_N[i] = "N" + " " + xNorth[i] + " " + yNorth[i];
        	}

       	 
        	for(int i = 0; i < total; i++)
        	{
            	for(int j = 0; j < northTotal; j++) {
                	if(input[i].equals(resFinAllData_N[j])) {
                		finalResult[i] = northResult[j];
                    	break;
                	}
            	}
            	for(int j = 0; j < eastTotal; j++) {
                	if(input[i].equals(resFinAllData_E[j])) {
                		finalResult[i] = eastResult[j];
                    	break;
                	}
            	}
        	}
       	 
        	//final output
        	for(int i = 0; i < total; i++) {
            	System.out.println(finalResult[i]);
        	}
	}
	
    
	public static void sortArrayByValX(int[] x, int[] y, boolean bAsc) {
    	int tempV = 0;
    	for(int i = 0; i < x.length; i++) {
        	for(int j = i + 1; j < x.length; j++) {
            	if(bAsc) {
                	if(x[i] > x[j]) {
                    	tempV = x[i];
                    	x[i] = x[j];
                    	x[j] = tempV;
                    	tempV = y[i];
                    	y[i] = y[j];
                    	y[j] = tempV;
                	}
            	}
            	else {
                	if(x[i] < x[j]) {
                    	tempV = x[i];
                    	x[i] = x[j];
                    	x[j] = tempV;
                    	tempV = y[i];
                    	y[i] = y[j];
                    	y[j] = tempV;
                	}
            	}
        	}
    	}
	}

	
	public static void adjustYInSortedArray(int[] x, int[] y) {
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
	
	public static int[] getVericalArray(int xValue, int[] x, int[] y) {
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
   
	public static int[] getHorizonArray(int yValue, int[] x, int[] y) {
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
}




