import java.awt.Color;
import java.util.Random;


public class Coin {

	private CoinPanel panel;
	private int x, y;
	private Color colour;
	private final Color[] colours = {Color.decode("#FFFFFF"), Color.decode("#FF0000")};
	private Random rnd = new Random();
	
	public Coin(CoinPanel panel, int x, int y) {
		this.panel = panel;
		this.x = x;
		this.y = y;
		this.colour = colours[0];
	}
	
	public void colourCoin() {
		panel.setRGB(x, y, colour.getRGB());
	}
	
	public void flip() {
		colour = colours[rnd.nextInt(2)];
		colourCoin();
	}
	
	public Color getColour() {
		return colour;
	}
	
	public void setColour(Color c) {
		this.colour = c;
	}
	
	public Color[] getColours() {
		return colours;
	}
}
