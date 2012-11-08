
public class StockAnalysis {
	
	public static void main(String args[]){
    	System.out.println("Stock Analysis");
    	//	double[] prices = {	589.87, 587.51, 585.74, 585.01 };
    	
    	//	double[] prices = {	529.91,535.32,526.11,524.04}; // Day after: 526.11
    	
    		//double[] dailyPrices = {}
    	
    		double[] hourlyPrices = {582.96,584, 583.24, 583.2,581.86, 581.69, 582.35};
    		double next = getNextPrice(hourlyPrices);
    		
    		
    	//	double[] prices = {578.5, 580.25, 580.54, 580.36, 580.66, 583.67, 582.95};

	}
	
	public static double getNextPrice(double[] prices){

    	double distance = 0;
  //  	double slope = 0;
    	
    	double dSum = 0;
    	
    	for(int i = 0; i < prices.length-1; i++){
    		distance += Math.abs(prices[i+1] - prices[i]);
    		dSum += (prices[i] - prices[i+1]);
    	}
    	
    	double instability = Math.abs(Math.abs(distance) - Math.abs(dSum));
    	
    	System.out.println("Distance: "+distance);
    	System.out.println("Instability: "+instability);
    	
//    	slope = prices[prices.length-1] - prices[0];
    	System.out.println("DSum: "+dSum);
    	
    	double nextMaxSlope = (dSum / prices.length) + instability;
    	double nextMinSlope = (dSum / prices.length) - instability;
    	double nextSlope = (nextMaxSlope + nextMinSlope) / 2;
    	
    	System.out.println(nextMinSlope+ " < " + nextSlope + " < "+nextMaxSlope);
    	
    	//double tommorowStartingPrice = prices[prices.length-1]; 
    	double tommorowStartingPrice = 578.5;
    	
    	double nextMaxPrice = tommorowStartingPrice + nextMaxSlope;
    	double nextMinPrice = tommorowStartingPrice + nextMinSlope;
    	double nextPrice = tommorowStartingPrice + nextSlope;
    	
    	System.out.println("Tommorow Max: "+nextMaxPrice);
    	System.out.println("Tommorow Min: "+nextMinPrice);
    	System.out.println("Tommorow's probable price: "+nextPrice);		
    		
    	return nextPrice;
	}
	
    public StockAnalysis() {
    }   
}