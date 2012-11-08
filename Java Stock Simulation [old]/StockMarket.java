import java.util.*;

/*
 *
 *Stock Market Simulation
 *Started: February 12, 2009
 *
 *Stock Market: Contains a list of all of the stocks. 
 *Stock, containes current value, past values and projected future value. 
 *Call the predictByDay method with a secondary variable of how many years in the past to look at
 *It'll look at how the value in the months were related to the value in the to-predict month. A decimal value
 *is used with a value defining the average relation between the two months/days. These values are then multiplied with
 *the known value of the past months to compute the possible value of the present / future months. It can be programmed to 
 *avoid chaos by being based solely on known values and not on computed values. 
 *
 *This same system can also be used to find the value per day of a stock based on the relationship between the days of the week
 *It can also be extended to hours of a day. Or, even the minutes of an hour. Since this same one can be applied on multiple levels
 *an abstraction for a time-system class must be created that'll handle all of these cases simultaneously. 
 *
 *The fibonnaci sequence can somehow be applied into the system to predict future prices also... (How??)
 *The angles that the curves produce can also be used to find general, hidden patterns. 
 *phi : 1.618033988749895 somehow plays into this... 
 *
 *http://goldennumber.net/fibonser.htm
 *
 *Markets swing from optimistic, to pessimistic to optimistic to pessimitic repeatedly, causing a general larger
 *recursive curve as well....
 *
 *Elliott wave principle
 *
 */


public class StockMarket {
	ArrayList stocks = new ArrayList<Stock>();
    public StockMarket() {
    	Stock google = new Stock("GOOG", 300.0);
    	stocks.add(google);
    	
    	System.out.println(stocks);
    }
    public static void main(String args[]){
    	System.out.println("Stock Market Simulation. Version 1.0");
    	System.out.println("BY: Feni Varughese");
    	new StockMarket();
    }
}

class Stock extends MarketFactor{
	
	double currentValue; 	// In dollars and cents as decimals. 
	
	int currentTurn;	// Store this as a TimeUnit. 
	
	// These are stocks whose values are affected by THIS. 
	// The stocks that affects THIS will have this in their list. It will not necessarily
	// be on this list. 
	ArrayList relatedStocks = new ArrayList<ValueConnection>();
	
	double[] future = new double[100];
	
	String name = "";
	
	public Stock(String n, double price){
		name = n;
		currentValue = price;
	}
	// Equivilant of fire, causing the prices of other ones to drop or rise accordingly...
	public void setPrice(int turn, double price){
		
	}
	
	public String toString(){
		return " "+name+" : "+currentValue;
	}
}

class ValueConnection{
	Stock s;
	MarketFactor f;
	double relation;
	// Value Connections are one way. Because the price of oil affects price of cars by 10X
	// doesn't necessarily mean the other way around with the same factor is true. There is almost
	// always a two-way relation, but with different factors of influence. So create two separate ones
	// instead of just one. 
	public ValueConnection(Stock stock, MarketFactor factor, double relation){
		s = stock;
		f = factor;
	}
}

class MarketFactor{
	public MarketFactor(){
		
	}
}