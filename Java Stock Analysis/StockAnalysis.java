//import java.math.BigDecimal;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;


public class StockAnalysis {
	static ArrayList<Stock> priceHistory = new ArrayList<Stock>();
	
	static double money = 100000;
	static int stocks = 0;
	static double avgHoldingPrice = 0;
	
	public StockAnalysis(){
	}
	
	// Average: 1, 2 = 1.5
	// Average: 1, 2, 3 = 2
	// = Average (1, 2)/3 + 3/2
	
	// Average: 2, 3 = 2.5
	// Average: 2, 3, 4 = 3
	// = ((2.5 * 2) + 4)/3
	// = ((oldAverage * oldSize) + newValue)/newSize
	// Note: newSize = oldSize+1
	
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
			
			Grapher graph = new Grapher();
			new Thread(graph).start();
//			while(line != null && !(line = scan.readLine()).equals(""))
//				sa.parseExcel(line);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public static void dumbTrade(){
		System.out.println("Dumb trade");
		double starting = money;
		Collections.sort(priceHistory);	// Newest = index 0. Oldest = size...
		System.out.println(priceHistory);
		// Just trade blindley...
		// Ignore the very last one...
		for(int k = priceHistory.size()-2; k >= 0; k--){
			avgHoldingPrice += (avgHoldingPrice*0.0001618);	// Expect a growth... 
			
			Stock s = priceHistory.get(k);
			double flag = s.getPrediction();
			//System.out.println("Flag: "+flag);
			// If it's telling us to sell and if the sale would be profitable...
			if(flag < -25 && s.price > avgHoldingPrice){	
				flag = Math.abs(flag);
				int amount = (int) Math.floor(stocks * flag/100);	// Number of stocks to sell...
				if(avgHoldingPrice != 0)	// More profit, sell more...
					 amount *= (s.price/avgHoldingPrice)*100;
				amount = (int)Math.min(stocks, amount);
				sell(s.price, amount);

			}	// If it's telling us to buy and the price would lower our averageHoldingPrice. 
			else if(flag > 25 && (stocks == 0 || avgHoldingPrice == 0 || s.price <= avgHoldingPrice)){
				flag = Math.abs(flag);
				// divide by 2, so that you don't spend more than half the money at a time...
				int amount = (int) (Math.floor(( money*flag/100) ));	 
				amount/=s.price;
				amount = (int)Math.min(money/s.price/2, amount);
				buy(s.price, amount) ;
			}
		}
		// Money + Assets (stocks * Current price)
		double netWorth = money + (stocks * priceHistory.get(0).price);
		System.out.println("Ended with $"+money+", Stocks: "+stocks+" = networth: "+netWorth);
		System.out.println("Stocks have an avg holding price of : "+avgHoldingPrice+" with current worth "+priceHistory.get(0).price);
		double percentGain = ((netWorth-starting)/starting)*100;
		System.out.println("That's a profit/loss of : "+percentGain+"%");
	}
	
	public static void buy(double price, int amount){
		if(amount > 0){
			System.out.println("Buying: "+amount+" stocks, at $"+price);
			if(price * amount <= money){
				// newAverage = ((oldAverage * oldSize) + newValue)/newSize 
				// Re-Compute the average value of all the stocks we've bought...			
				avgHoldingPrice = ((avgHoldingPrice * stocks) + (price*amount))/(stocks+amount); 
				money -= price*amount;
				stocks += amount; 
			}
			else{
				System.out.println("Can't buy... you're broke...");
	//			throw new IllegalArgumentException("Error; System threw a you're broke Exception");
			}
		}
	}
	
	public static void sell(double price, int amount){
		if(amount > 0){
			System.out.println("Selling: "+amount+" stocks, at $"+price);
			if(amount <= stocks){
				// Downscale the average so that we can buy and sell profitably at a new level...
				
				// newAverage = ((oldAverage * oldSize) + newValue)/newSize 
				// Re-Compute the average value of all the stocks we've bought...
				
				// So I bought 100 stocks at an average price of 3 dollars. I sold 50 stocks for 6... 
				// Now the average price is 0, because we already made back the moeny we spent...
				// average = (totalPriceOfCurrentHoldings - moneyMade) / (stocksRemaining)
				// Note: MoneyMade = total. not profit...
				// Example: (3*100 - 50*6)/50
				
				double profit = (price*amount) - (avgHoldingPrice * amount); 
				System.out.println("made a profit of: "+profit);
				// Once we've made a profit, reset at a little less than the current price...
				avgHoldingPrice = Math.max(price*0.75,((avgHoldingPrice * stocks) - (price*amount))/(stocks-amount));
				
				money += amount * price;
				stocks-=amount;
			}
			else
				System.out.println("Can't sell... you don't have anything to sell");
				//throw new IllegalArgumentException("Error; System threw a you're aint have that many stocks exception");
		}
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

