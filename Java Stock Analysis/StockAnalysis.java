//import java.math.BigDecimal;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;


public class StockAnalysis {
	static ArrayList<Stock> priceHistory = new ArrayList<Stock>();
	
	static double money = 100000;
	static int stocks = 0;
	
	public StockAnalysis(){
	}
	
	public static void parseGoog(String str){
		// Sep 2, 2010	25.54	26.01	25.51	25.88	6,442,930
		// Date			Open	High	Low		Close	Volume
		int month, day, year;
		float open, high, low, close;
		double volume;
		
		StringTokenizer st = new StringTokenizer(str, " ,/t,/n,	");
		if(st.countTokens() > 7){
			month = getMonthAsNum(st.nextToken());
			day = Integer.parseInt(st.nextToken());
			year = Integer.parseInt(st.nextToken());
			open = Float.parseFloat(st.nextToken());
			high = Float.parseFloat(st.nextToken());
			low = Float.parseFloat(st.nextToken());
			close = Float.parseFloat(st.nextToken());
			
			String temp = "";
			while(st.hasMoreTokens()){
				temp+=st.nextToken();
			}
			temp = temp.replaceAll(",", "");
			volume = Double.parseDouble(temp);

/*			System.out.println("Str is a valid string");
			System.out.println("Date: "+month+" "+day+","+" "+year);
			System.out.println("Open: "+open);
			System.out.println("High: "+high);
			System.out.println("Low: "+low);
			System.out.println("Close: "+close);
			System.out.println("Volume: "+volume);
	*/		
			priceHistory.add(new Stock(year, month, day, open, high, low, close, volume));
		}
		else{
			System.out.println(st.countTokens());
			while(st.hasMoreTokens())
				System.out.println(st.nextToken());
		}
	}
	
	
	public static void parseExcel(String str){
		// 07/26/10	51.67	51.67	50.91	51.13	14117330
		// Date		Open	High	Low		Close	Volume
		int month, day, year;
		float open, high, low, close;
		double volume;
		
		StringTokenizer st = new StringTokenizer(str, " ,//,/t,/n,	");
		if(st.countTokens() > 7){
			month = Integer.parseInt(st.nextToken());
			day = Integer.parseInt(st.nextToken());
			year = Integer.parseInt(st.nextToken());
			
			// Convert it to the 20th century... right now it says 10, make it say 2010
			year = 2000+year;
			
			open = Float.parseFloat(st.nextToken());
			high = Float.parseFloat(st.nextToken());
			low = Float.parseFloat(st.nextToken());
			close = Float.parseFloat(st.nextToken());
			volume = Double.parseDouble(st.nextToken());
			/*
			System.out.println("Str is a valid string");
			System.out.println("Date: "+month+" "+day+","+" "+year);
			System.out.println("Open: "+open);
			System.out.println("High: "+high);
			System.out.println("Low: "+low);
			System.out.println("Close: "+close);
			System.out.println("Volume: "+volume);
			*/
			
			priceHistory.add(new Stock(year, month, day, open, high, low, close, volume));			
		}
		else{
			System.out.println(st.countTokens());
			while(st.hasMoreTokens())
				System.out.println(st.nextToken());
		}		
	}
	
	
	public static void main(String[] args) {
		System.out.println("Stock Analysis");
		StockAnalysis sa = new StockAnalysis();
//		sa.parseExcel("07/26/10	51.67	51.67	50.91	51.13	14117330");
		//sa.parseGoog("Sep 2, 2010	25.54	26.01	25.51	25.88	6,442,930");
		try {
			BufferedReader scan = new BufferedReader(new FileReader("StockData3.txt"));
			System.out.println("Parsing...");
			String line = ""; 
			while( (line=scan.readLine()) != null && !line.equals(""))
				sa.parseExcel(line);
//			while(line != null && !(line = scan.readLine()).equals(""))
//				sa.parseExcel(line);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		sa.dumbTrade();
	}
	
	public static void dumbTrade(){
		double starting = money;
		Collections.sort(priceHistory);	// Newest = index 0. Oldest = size...
		System.out.println(priceHistory);
		// Just trade blindley...
		// Ignore the very last one...
		for(int k = priceHistory.size()-2; k >= 0; k--){
			Stock s = priceHistory.get(k);
			double flag = s.getPrediction();
			System.out.println("Flag: "+flag);
			if(flag < 0){
				flag = Math.abs(flag);
				sell(s.price, (int) Math.floor( stocks*(flag/100) ) );
			}
			else{
				flag = Math.abs(flag);
				buy(s.price, (int) (Math.floor((money*flag/100)) / s.price ));
			}
		}
		// Money + Assets (stocks * Current price)
		double netWorth = money + (stocks * priceHistory.get(0).price);
		System.out.println("Ended with $"+money+", Stocks: "+stocks+" = networth: "+netWorth);
		double percentGain = ((netWorth-starting)/starting)*100;
		System.out.println("That's a profit/loss of : "+percentGain+"%");
	}
	
	public static void buy(double price, int amount){
		if(price * amount <= money){
			money -= price*amount;
			stocks += amount; 
		}
		else
			System.out.println("Can't buy... you're broke...");
//			throw new IllegalArgumentException("Error; System threw a you're broke Exception");
	}
	
	public static void sell(double price, int amount){
		if(amount <= stocks){
			money += amount * price;
			stocks-=amount;
		}
		else
			System.out.println("Can't sell... you don't have anything to sell");
			//throw new IllegalArgumentException("Error; System threw a you're aint have that many stocks exception");
	}
	
	public static int getMonthAsNum(String str){
		str = str.toUpperCase();
		if(str.equals("JAN"))
			return 1;
		if(str.equals("FEB"))
			return 2;
		if(str.equals("MAR"))
			return 3;
		if(str.equals("APR"))
			return 4;
		if(str.equals("MAY"))
			return 5;
		if(str.equals("JUN"))
			return 6;
		if(str.equals("JUL"))
			return 7;
		if(str.equals("AUG"))
			return 8;
		if(str.equals("SEP"))
			return 9;
		if(str.equals("OCT"))
			return 10;
		if(str.equals("NOV"))
			return 11;
		if(str.equals("DEC"))
			return 12;
		throw new IllegalArgumentException("Invalid input String for month. Cannot convert...");
	}
	
	// Faster, but fails for large portfolios... wit... can it overflow?
	public static double getAverageVolume(){
		double average = 0.0;
		int size = priceHistory.size()-1;	// Make sure to exlude the last one... Else you get an NaN error...
		for(int k = 0; k < size; k++){
			average += (priceHistory.get(k).volume/size);
		}
		return average;
	}
	
	public static double getAveragePrice(){
		double average = 0.0;
		int size = priceHistory.size()-1;
		for(int k = 0; k < size; k++){
			average += (priceHistory.get(k).price/size);
		}
		return average;
	}
	
	public static double getAbsAverageMomentum(){
		double average = 0.0;
		int size = priceHistory.size()-1;
		for(int k = 0; k < size; k++){
			average += (Math.abs(priceHistory.get(k).getMomentum())/size);
		}
//		System.out.println("Average momentum : "+average);
		return average;	
	}
}

class Stock implements Comparable{
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