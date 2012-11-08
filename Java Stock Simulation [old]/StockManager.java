import java.util.*;
import java.text.*;

public class StockManager{	// CHANGE THE NAME

	static Scanner scan = new Scanner(System.in);
	static double moneyRemaining = 100000.0;
	static ArrayList stocks = new ArrayList();
	
	public static void main(String args[]){
		pl("Stock Manager, Version 1.0");
		pl("By Feni Varughese");
		pl("");
		Stock google = new Stock("GOOG", 342.66, 0);
		Stock bbda = new Stock("BBDA", 0.0016, 0);
		Stock usb = new Stock("USB-J", 17.54, 0);
		Stock intel = new Stock("INTC", 13.21, 0);
		Stock wdc = new Stock("WDC", 15.64, 0);
		
		stocks.add(google);
		stocks.add(bbda);
		stocks.add(usb);
		stocks.add(intel);
		stocks.add(wdc);

// confidenceTest();
		
		
//		double[] yahooPrices = {10.0, 20.0, 40.0, 80.0, 160.0};
//		double[] googlePrices = {100.0, 400.0, 1600.0, 6400.0, 25600.0	};

		double[] yahooPrices = {10.0, 30.0, 49.0, 58.0, 73.0};
		double[] googlePrices = {100.0, 350.0, 1523.0, 4400.0, 21000.0	};

//		pl(getCorrelation(yahooPrices, googlePrices));
//		pl(getCorrelation(googlePrices, yahooPrices));
		
//		getConfidenceFactor(357.0, 342.64, 359.80, 341.41);
//		getConfidenceFactor(357.47, 359.80 	, 341.41 , 342.64 );
//		getConfidenceFactor(347.24 ,353.38	, 340.52 	, 353.11 );

		confidenceTest();
		
		// When yahoo's price goes up p percentage, c = correlation, 
		// google's will go up p * c percentage. 
		
		while(true){
			String command = nl();
			command = command.toLowerCase();
			if(command.equals("buy")){
				p("What ?");
				String name = nl().toUpperCase();
				Stock s = null;
				for(int k = 0; k < stocks.size(); k++){
					Stock st = (Stock) stocks.get(k);
					if(st.symbol.equals(name)){
						s = st;
					}
				}
				if(s != null){
					p("How many : ");
					int num = ni();
					s.buy(num);
				}
				else{
					pl("Unkown Symbol");
				}
			}
			else if(command.equals("update")){
				p("What ?");
				String name = nl().toUpperCase();
				Stock s = null;
				for(int k = 0; k < stocks.size(); k++){
					Stock st = (Stock) stocks.get(k);
					if(st.symbol.equals(name)){
						s = st;
					}
				}
				if(s != null){
					p("New Price ?");
					double p = nd();
					s.price = p;
				}
				else{
					pl("Unkown Symbol");
				}
			}
			else if(command.equals("sell")){
				p("What ?");
				String name = nl().toUpperCase();
				Stock s = null;
				for(int k = 0; k < stocks.size(); k++){
					Stock st = (Stock) stocks.get(k);
					if(st.symbol.equals(name)){
						s = st;
					}
				}
				if(s != null){
					p("How many : ");
					int num = ni();
					s.sell(num);
				}
				else{
					pl("Unkown Symbol");
				}				
			}
			else if(command.equals("calculate")){
				p("Stock Name ");
				String name = nl().toUpperCase();
				Stock s = null;
				for(int k = 0; k < stocks.size(); k++){
					Stock st = (Stock) stocks.get(k);
					if(st.symbol.equals(name)){
						s = st;
					}
				}
				if(s != null){
					p("Goal Gain (%) : ");
					double gain = nd();
					p("Goal Profit : ");
					double profit = nd();
					
					pl();
					double price = s.price;
					pl("Price : "+price);
					double change = s.price * (gain/100);
					double goalPrice = price += change;
					pl("Goal Price = "+price+" + " +change+" = "+goalPrice);
					
					double minStocks = profit / change;
					pl("Minimun Stocks : "+minStocks);
					double totalCost = minStocks * price;
					pl("Total Cost: "+totalCost);
					double salePrice = minStocks * goalPrice;
					pl("Target Sale "+salePrice);
					pl();
				}
				else{
					pl("Unkown Symbol");
				}
			}
			else if(command.equals("new")){
				p("Stock Symbol : ");
				String name = nl().toUpperCase();
				p("Starting Price : ");
				double price = nd();
				p("Stocks to Buy : ");
				int stock = (int) ni();
				stocks.add(new Stock(name,price,stock));
			}
			else if(command.equals("summary")){
				pl();
				pl("Remainder: "+moneyRemaining);
				for(int k = 0; k < stocks.size(); k++){
					Stock st = (Stock) stocks.get(k);
					pl(st);
				}
				pl();
			}
		}
	}
	public static void confidenceTest(){
		getConfidenceFactor(357.0, 342.64, 359.80, 341.41);
		getConfidenceFactor(357.47, 359.80 	, 341.41 , 342.64 );
		getConfidenceFactor(347.24 ,353.38	, 340.52 	, 353.11 );
		getConfidenceFactor(346.51 ,347.09 	, 339.69 	, 342.66 );
		getConfidenceFactor(362.19 ,362.99 	, 355.23 	, 357.68 );
		getConfidenceFactor(353.16 ,363.62 	, 351.48 	, 363.05 );
		getConfidenceFactor(358.95 ,365.00 	, 353.00 	, 358.04 );
		getConfidenceFactor(375.98 ,377.50 	, 357.89 	, 358.51 );
		getConfidenceFactor(371.28,381,367.3,378.77);
		getConfidenceFactor(356.46,373.81,355.44,371.28);
		getConfidenceFactor(340.91,355.38,337,353.72);
		getConfidenceFactor(340.07,354.44,339.17,343);
		getConfidenceFactor(342.57,343,333.83,340.45);
		getConfidenceFactor(334.29,345,332,340.57);
		getConfidenceFactor(344.69,348.8,336,338.53);
		getConfidenceFactor(344.54,345.05,340.11,343.32);
		getConfidenceFactor(337.98,352.33,336.31,348.67);
		getConfidenceFactor(326.45,333.87,324.27,331.48);
		getConfidenceFactor(324.85,328,320.56,323.87);
		getConfidenceFactor(309.27,331.96,304.22,324.7);
		getConfidenceFactor(298.04,309.35,295.15,306.5);
		getConfidenceFactor(288.35,303.5,288.35,303.08);
		getConfidenceFactor(299.14,299.5,282.75,282.75);
		getConfidenceFactor(305.02,308.25,295.7,299.67);
		getConfidenceFactor(297.57,303.58,286.79,298.99);
		getConfidenceFactor(310,313.8,297.75,300.97);
		getConfidenceFactor(311.77,320.6,310.39,314.32);
		getConfidenceFactor(316.31,318.95,310.23,312.69);
		getConfidenceFactor(327.5,327.5,313.4,315.07);
		getConfidenceFactor(318.28,325.19,317.34,325.19);
		getConfidenceFactor(328.32,330.91,318.75,322.01);
		getConfidenceFactor(332.98,340.8,326.39,334.06);
		getConfidenceFactor(321,331.24,315,328.05);
		getConfidenceFactor(308.6,321.82,305.5,321.32);
		getConfidenceFactor(307.65,307.65,307.65,307.65);
		getConfidenceFactor(304.2,311,302.61,307.65);
		getConfidenceFactor(300.8,306.81,298.71,303.11);
		getConfidenceFactor(300.22,301.38,291.58,297.42);
		getConfidenceFactor(304.07,305.26,298.31,300.36);
		getConfidenceFactor(302.95,302.95,302.95,302.95); //**//
		getConfidenceFactor(301.48,306.34,298.38,302.95);
		getConfidenceFactor(300.43,303.31,296.67,298.02);
		getConfidenceFactor(308.56,309.5,290.63,297.11);
		getConfidenceFactor(310.99,317.79,309,310.17);
		getConfidenceFactor(316.7,320.35,309.11,310.28);
		getConfidenceFactor(318.64,322.13,312.42,315.24);
		getConfidenceFactor(314.52,329.5,311.27,325.28);
		getConfidenceFactor(314.01,318.49,305.11,310.67);
		getConfidenceFactor(295.71,316.47,294,315.76);
		getConfidenceFactor(304.17,312.88,297.8,300.22);
		getConfidenceFactor(309.24,314.9,304.51,308.82);
		getConfidenceFactor(297.69,318,297.01,305.97);
		getConfidenceFactor(289.99,309.44,282,302.11);
		getConfidenceFactor(271.02,284.24,264.02,283.99);
		getConfidenceFactor(276.53,283.49,268.77,274.34);
		getConfidenceFactor(269.85,281.36,265.34,279.43);
		getConfidenceFactor(269.73,277.78,262.58,275.11);
		getConfidenceFactor(286.68,287.38,265.98,265.99);
		getConfidenceFactor(290.58,296.45,288.28,292.96);
		getConfidenceFactor(292.09,292.09,292.09,292.09);
		getConfidenceFactor(280.28,295.46,276.2,292.09);
		getConfidenceFactor(268.68,286.66,267.32,282.05);
		getConfidenceFactor(269.26,269.95,249.01,257.44);
		getConfidenceFactor(262.51,269.37,247.3,262.43);
		getConfidenceFactor(274.89,282.94,259.04,259.56);
		getConfidenceFactor(295.39,300.19,278.58,280.18);
		getConfidenceFactor(301.57,303.73,285.35,297.42);
		getConfidenceFactor(303,310.16,297.95,300.12);
		getConfidenceFactor(303.25,324.99,302.56,310.02);
		getConfidenceFactor(291.77,313,280,312.08);
		getConfidenceFactor(302.05,312.49,287.76,291);
		getConfidenceFactor(308.69,316.3,300.52,311.46);
		getConfidenceFactor(328,329.44,309.47,318.78);
		getConfidenceFactor(333.12,341.15,325.33,331.14);
		getConfidenceFactor(339.97,344.42,325.81,331.22);
/*362.15	368.88	341.31	342.24
353.44	372.36	345.5	366.94
357.58	362.99	341.43	346.49
356.16	371.96	354.27	359.36
368.46	372	358.37	359.69
365.79	371	352.37	358
339.05	369.31	328.51	368.75
334.81	343	325.6	329.49
326.47	350.47	324.74	339.29
353.65	358	337.99	352.32
356.99	369.69	344	355.67
372.39	383.78	362	362.75
379.75	380.98	359.59	379.32
378.96	386	363.55	372.54
332.76	356.5	309.44	353.02
354.65	359	338.83	339.17
393.53	394.5	357	362.71
355.79	381.95	345.75	381.02
313.16	341.89	310.3	332
344.52	348.57	321.67	328.98
330.16	358.99	326.11	338.11
373.33	374.98	345.37	346.01
373.98	375.99	357.16	371.21
397.35	412.5	383.07	386.91
409.79	409.98	386	390.49
411.15	416.98	403.1	411.72
396	489.36	392.32	400.52
419.51	423.51	380.71	381
428	437.16	421.03	431.04
438.84	450	435.98	439.6
430.34	445	430.11	435.11
433.25	440.79	425.72	429.27
454.13	454.13	429	430.14
461	462.07	443.28	449.15
422.64	439.18	410.5	439.08
438.48	439.14	413.44	414.49
425.96	449.28	425.49	442.93
424	441.97	423.71	433.86
430.21	441.99	429	437.66
408.35	435.09	406.38	433.75
424.47	424.48	409.68	414.16
423.17	432.38	415	418.66
452.02	452.94	417.55	419.95
445.49	452.46	440.08	444.25
460	463.24	449.4	450.26
468.73	474.29	459.58	464.41
476.77	482.18	461.42	465.25*/

	}

	public static double getNetValue(double oc, double high, double low){
		return (oc + ((low - oc) + (high - oc)));
	}
	
	public static double getConfidenceFactor(double open, double high, double low, double close){
//		pl("O: "+open+" C:  "+close+" H: "+high+" L: "+low);
		double o = getNetValue(open, high, low);
		double c = getNetValue(close, high, low);
//		pl("Net Open : "+o);
//		pl("Net Close : "+c);
		double oChange = open-o;
		double cChange = close-c;
		
		double sum = oChange + cChange;
//		double calcClose = c-oChange;
		
//		pl("Open Change: "+oChange);
//		pl("Close Change: "+cChange);
		pl(Math.round(sum));
//		pl("Calc Close: "+calcClose);
		
		double rtrn = o-c;
		
		double pRtrn = (close / (close + rtrn)) * 100;
		
//		pl(pRtrn);
		double calculatedClose = open + rtrn;
//		pl(close+"  ::  "+calculatedClose);
//		pl();
		return rtrn;
	}
	
	// return the number of days before the price is higher than paramter price. 
	public static int getNextHigh(double price){
	//	lastHigh *=1.618033988749895
		return 0;
	}
	
	public static double getCorrelation(double[] price1, double[] price2){
		// compare the growth or fall between the prices. The slope, in terms of percentage. 
		// find the difference between the slopes. Add all of those up. Divide. 
		
		double[] slope1 = getSlopePercentage(price1);
		double[] slope2 = getSlopePercentage(price2);
		
		int minLength = 0;
		if(slope1.length < slope2.length){minLength = slope1.length;}
		else{minLength = slope2.length;}
		
		double sum = 0.0;
		
		for(int k = 0; k < minLength; k++){
			sum+=((slope1[k]/slope2[k]));
		}
		return sum/minLength;
	}
	
	public static double[] getSlopePercentage(double[] prices){
		double[] slope = new double[prices.length-1];
		p("Slope "+slope.length+"  :   ");
		for(int k = 0; k < prices.length-1; k++){
			slope[k] = 100 * ((prices[k+1]-prices[k])/prices[k]);
			p("    " +slope[k]+ "   ");
		}
		pl();
		return slope;
	}
	
	// Buy low, sell high. Simple. Not the most efficient. 
	public static void simpleTrade(Stock s, double[] prices){
		double lowest = -1;
		double highest = -1;
		
		double hIndex = 0;
		double lIndex = 0;
		
		for(int k = 0; k < prices.length; k++){
			if(lowest == -1){
				lowest = prices[k];
				lIndex = 0;
			}
			if(highest == -1){
				highest = prices[k];
				hIndex = 0;
			}
			
			if(prices[k] > highest){
				highest = prices[k];
				hIndex = k;
			}
			
			if(prices[k] < lowest){
				lowest = prices[k];
				lIndex = k;
			}
		}
		pl("Lowest at "+lIndex+ " costing "+lowest);
		pl("Highest at "+hIndex+ " costing "+highest);
		for(int k = 0; k < prices.length; k++){
			s.price = prices[k];
			if(k == lIndex){
				int numStocks = (int) (moneyRemaining / s.price);
				s.buy(numStocks);
				pl(s);
			}
			
			if(k == hIndex){
				// sellOut
				s.sell(s.shares);
				pl(s);
			}
		}
	}
	// Trade with every possible opportunity making any possible profit and reinvesting with that. 
	public static void opportunisticTrade(Stock s, double[] prices){
		double boughtPrice = 0.0;
		
		// a lower price than current. It's before any sale opportunity
		int buyTurn = 0;

		int sellTurn = 0;
		
		for(int k = 0; k < prices.length; k++){
			s.price = prices[k];
			pl(k+"  Buy: "+buyTurn+"   Sell: "+sellTurn);
			if(k == buyTurn){
				s.buy((int)(moneyRemaining/s.price));
				pl("Buying "+s);
			}
			else if(k == sellTurn){
				if(s.shares > 0){
					s.sell(s.shares);
				}
				pl("Selling "+s);
			}
			
			for(int i = k; i < prices.length; i++){
				if(prices[i] > s.price){
					// An opportunity to sell, so buy now and plan to sell then. 
					if(buyTurn <= k){
						buyTurn = k;
					}
					else if(prices[k] < prices[buyTurn]){
						buyTurn = k;
					}
					sellTurn = i;
					break;
				}
				else if(prices[i] < prices[k] && prices[i] < prices[buyTurn]){
					buyTurn = k;
				}
			}
			pl("Ending Turn : "+moneyRemaining+"   "+s);
		}
	}
	
	public static void crazyTrade(Stock s, double[] prices){
		for(int k = 0; k < prices.length; k++){
			s.price = prices[k];
			
			int hIndex = -1;
			int lIndex = -1;
			
			for(int i = k+1; i < prices.length; i++){
				if(lIndex == -1 && prices[i] <= s.price){
//					pl("Price: "+s.price+" Lower: "+prices[i]);
					lIndex = i;
				}
				if(hIndex == -1 && prices[i] >= s.price){
//					pl("Price: "+s.price+" Higher: "+prices[i]);
					hIndex = i;
				}
			}
//			pl(hIndex+" , "+lIndex);
			if(hIndex == -1 && lIndex == -1){
				if(s.shares > 0)
					s.sell(s.shares);
			}
			if(hIndex != -1 && hIndex <= lIndex ){
				s.buy((int) (moneyRemaining / s.price));
			}
			if(lIndex != -1 && lIndex <= hIndex){
				if(s.shares > 0)
					s.sell(s.shares);
			}
		}		
	}
	// Assumes that the data sent in is just estimates with a good chance of changing slighly...
	// Trades by the 50% rule. 
	public static void safeTrade(Stock s, double[] prices){
		for(int k = 0; k < prices.length; k++){
			s.price = prices[k];
		}
	}
	public static String fD(Double d, String formatOptions){
		DecimalFormat dFormatter = new DecimalFormat(formatOptions);
		return dFormatter.format(d);
	}
	public static void pl(String str){
		System.out.println(str);
	}
	public static void pl(){
		System.out.println();
	}
	public static void pl(Object o){
		System.out.println(o);
	}
	public static void p(String str){
		System.out.print(str);
	}
	public static void p(Object o){
		System.out.print(o);
	}
	public static int pi(String strNum){
		return Integer.parseInt(strNum);
	}
	public static double pd(String strNum){
		return Double.parseDouble(strNum);
	}
	public static int ni(){	// NextInt
		return scan.nextInt();
	}
	public static double nd(){
		return scan.nextDouble();
	}
	public static String nl(){	// NextLine
		return scan.nextLine();
	}
	public static ArrayList copyArray(ArrayList list){
		ArrayList rtrn = new ArrayList();
		for(int k = 0; k < list.size(); k++){
			rtrn.add(list.get(k));
		}
		return rtrn;
	}
	public static ArrayList findWords(String str){
		ArrayList words = new ArrayList();
	    StringTokenizer st = new StringTokenizer(str);
     	while (st.hasMoreTokens()) {
     		String token = st.nextToken();
     		words.add(token);
     	}
		return words;
	}
	public static ArrayList findWords(String str, String delimeters){
		ArrayList words = new ArrayList();
	    StringTokenizer st = new StringTokenizer(str, delimeters);
     	while (st.hasMoreTokens()) {
     		String token = st.nextToken();
     		words.add(token);
     	}
		return words;
	}
	public static boolean isValidNum(String str){
		boolean b = true;
		for(int k = 0; k < str.length(); k++){
			char c = str.charAt(k);
			if(c == '0' || c== '1' || c== '2' || c== '3' || c== '4' 
			|| c== '5' || c== '6' || c== '7' || c== '8' || c== '9'){
			}
			else{	b = false;	}
		}
		if(str.length() > 1){	b = false;	}		// Too long. Has to be from 0-9
		return b;
	}
}

/*
 * p = m * v
 * vector quantity
 *
 * mass = price
 * velocity = slope
 **/