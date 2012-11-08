public class Stock implements Comparable{
	int year, day, month; 
	int date;
	float open, high, low, close;
	double volume;
	double price;
	double efficiency;
	
	public Stock(int y, int m, int d, float o,float h,float l,float c,double v){
		year = y;
		day = d;
		month = m;
		open = o;
		high = h;
		low = l;
		close = c;
		volume = v;
		
		date = year;
		date = (date * 100) + month;
		date = (date * 100) + day;
//		System.out.println("Date from " + year + " "+month+" "+day+" = "+date);
		
		price = (open + high + low + close) / 4;
		
		efficiency = 100;
		if(high-low != 0)
			efficiency = Math.abs(close-open)/(high-low) * 100;
	}
	// newer returns as higher...
	public int compareTo(Object o) {
		return ((Integer)((Stock)o).date).compareTo(date); 
	}
	
	// Return the change in percentage...
	public double getPriceChange(){
		double change = Double.NaN;
		int myIndex = StockAnalysis.priceHistory.indexOf(this);
		if(myIndex != StockAnalysis.priceHistory.size()-1){
			Stock yesterday = StockAnalysis.priceHistory.get(myIndex+1);
			change = ((price/yesterday.price)-1)*100;
		}
		else
			System.out.println(this+" was left out");
		return change;
	}
	public double getVolumeChange(){
		double change = Double.NaN;
		int myIndex = StockAnalysis.priceHistory.indexOf(this);
		if(myIndex != StockAnalysis.priceHistory.size()-1){
			Stock yesterday = StockAnalysis.priceHistory.get(myIndex+1);
			if(yesterday.volume == 0)	// Prevent a subtle divide by zero...
				change = 100;
			else
				change = ((volume/yesterday.volume)-1)*100;
		}
		return change;
	}
	public double getPriceDeviation(){
		return ((volume/StockAnalysis.getAveragePrice())-1)*100;		
	}
	public double getVolumeDeviation(){
		return ((volume/StockAnalysis.getAverageVolume())-1)*100;		
	}
	public double getMomentum(){
		// Get the absolute volume change in full prespective of current and all time... also change it back to a decimal from a percentage...		
		double volDiff = (getVolumeChange() + getVolumeDeviation())/2/100;
		double priceChange = (getPriceChange()+getPriceDeviation())/2/100;
//		System.out.println("Vol difference : "+volDiff);
//		System.out.println("PriceChange : +priceChange");
//		System.out.println("Momentum : "+volDiff*priceChange*100);
		
		return (volDiff * priceChange) * 100;
	}
	public double getPredictedMomentum(){
		return (getMomentum() * (efficiency/100))*100;
	}
	
	public double getWeightedMomentum(){
		return (getMomentum()/StockAnalysis.getAbsAverageMomentum())*100;
	}
	// Returns the number of stocks you should buy. 100 being max. -100 being sell all...
	public double getPrediction(){
		double buy = 0.0;
		double momentum = getWeightedMomentum();
		if(momentum < 0)
			momentum = Math.max(momentum, -100);
		else
			momentum = Math.min(momentum, 100);
//		System.out.println("momentum " +momentum+" efficiency "+efficiency);		
		buy = momentum * (efficiency/100);
		return buy;
	}
	
	public String toString(){
		return " " +date+"("+price+","+volume+") ";
	}
}