import java.util.*;
import java.text.*;

public class Stock implements Comparable{
	double price = 0.0;
	String symbol = "";
	
	// The total money invested in this...
	double cost = 0.0;
	
	int shares = 0;
	
	// If it's less that that, buy buy buy!
	double minThreshold = 0.0;
	// if it's more than that, sell sell sell!
	double maxThreshold = 0.0;
	
	// The most amount we are willing to dedicate to this stock...
	// Current investment computed with cost * shares. 
	// If current < max, buy half (if it's above calculated min threshhold). 
	double maxInvestment = 0.0;
	
	public Stock(String s, double p, int sh){
		symbol = s;
		price = p;
		cost = p*sh;
		shares = sh;
	}
	public void sell(int numShares){
		if(numShares <= shares && shares != 0){
			double money = numShares * price;
			StockManager.moneyRemaining += money;
			double profit = money - ((numShares/shares) * cost);
//			pl("Profit: "+profit);
//			pl("Cost "+cost);
			cost -= money * (numShares/shares);
//			pl("New Cost: "+cost);
			shares-=numShares;
		}
		System.out.println("Sold "+this+" Remaining: "+StockManager.moneyRemaining);
	}
	public void buy(int numShares){
		double money = numShares * price;
		StockManager.moneyRemaining -= money;
		shares+=numShares;
//		pl("Cost: "+cost);
		cost = (cost + (money * (numShares / shares)));
//		pl("Cost After"+cost);
		System.out.println("Bought "+this+" Remaining: "+StockManager.moneyRemaining);
	}
	public int compareTo(Object o){
		Stock s = (Stock) o;
		if(s.symbol.equals(symbol)){
			return 0;
		}
		else{
			return symbol.compareTo(s.symbol);
		}
	}
	
	public String toString(){
		return " "+symbol+" "+price+" :: Cost "+cost+" Shares "+shares;
	}
	
	public static void pl(Object o){
		System.out.println(o);
	}
	public static void p(String str){
		System.out.print(str);
	}	
}