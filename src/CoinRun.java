
public class CoinRun extends Thread{

	private CoinPanel panel;
	private boolean running;
	private int count = 0;
	
	public CoinRun(CoinPanel panel) {
		this.panel = panel;
	}
	
	public void run() {
		while(true){
			while(running) {
				panel.flipCoin();
				if(count%1000 == 0) {
					panel.repaint();
					if(count%2000 == 0)
					panel.printPercentageFlipped();
				}
				count++;
			}
		}
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public void setRunning(boolean running) {
		this.running = running;
	}
}
