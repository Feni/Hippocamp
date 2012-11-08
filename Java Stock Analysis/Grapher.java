import javax.swing.*;
import java.awt.*;

public class Grapher extends JFrame implements Runnable{
	public Grapher(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setBounds(0,0,800,600);
		//addKeyListener(this);
		createBufferStrategy(2);
	}
	
	public void run(){
		while(true){
			System.out.println("Drawing...");
			Graphics g = getBufferStrategy().getDrawGraphics();
			g.setColor(Color.white);
			g.fillRect(0, 0, 800, 600);
			
			int size = StockAnalysis.priceHistory.size()-1;
			g.setColor(Color.black);
			for(int i = 0; i < size-2; i++){
				Stock s = StockAnalysis.priceHistory.get(size-i);
				g.drawLine(i*800/size, 600-(int) (s.price),  (i+1) * 800/size , 600 - (int) StockAnalysis.priceHistory.get(i+1).price);
			}
			
			g.dispose();
			getBufferStrategy().show();
			sleep(300);
		}
	}
	
	public void sleep(int ms){
		try{
			Thread.sleep(ms);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}
