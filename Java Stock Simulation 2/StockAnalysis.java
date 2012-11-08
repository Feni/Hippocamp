
public class StockAnalysis {
	
	public static void main(String args[]){
    	System.out.println("Stock Analysis");
    	//double[] prices = {	589.87, 587.51, 585.74, 585.01 };
    	
    //	double[] prices = {	529.91,535.32,526.11,524.04}; // Day after: 526.11
    	double[] prices = {582.96,584, 583.24, 583.2,581.86, 581.69, 582.35};

    	
    	double distance = 0;
    	double slope = 0;
    	
    	for(int i = 0; i < prices.length-1; i++){
    		distance += Math.abs(prices[i+1] - prices[i]);
    	}
    	slope = prices[prices.length-1] - prices[0];
    	
    	double confidence = Math.abs(distance / slope); 	//	If you go the long route to get from a to b, then you're not sure about it...
    	double instability = Math.abs(distance - Math.abs(slope));
    	
    	System.out.println("Distance: "+distance+" Slope: "+slope);
    	System.out.println("Market Confidence: "+confidence);
    	System.out.println("Market Instability: "+instability);
    	
    	double tommorowMin = prices[prices.length-1] - ( ( Math.abs(slope) * confidence) / prices.length );
    	double tommorowMax = prices[prices.length-1] + ( (Math.abs(slope) * confidence) / prices.length );
    	    	
    	double tommorowMinSlope = slope - (instability / prices.length );
    	double tommorowMaxSlope = slope + (instability / prices.length );
    	double tommorowProbableSlope = (tommorowMinSlope + tommorowMaxSlope) / 2;
    	System.out.println("Slope: "+tommorowMinSlope+" , < "+tommorowProbableSlope+" , < " + tommorowMaxSlope);
    	    	
    	double tommorowProbable = prices[prices.length-1] + (slope * confidence) / prices.length ;
    	double tommorowProbable2 = prices[prices.length-1] + (tommorowProbableSlope * confidence );
    	
    	System.out.println("Tommorow Min: "+tommorowMin);
    	System.out.println("Tommorow Max: "+tommorowMax);
    	System.out.println("Tommorow Probable: "+tommorowProbable);
    	System.out.println("Tommorow Probable2: "+tommorowProbable2);
    	
	}
	
    public StockAnalysis() {
    }   
}