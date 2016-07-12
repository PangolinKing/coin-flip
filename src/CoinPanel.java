import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.TimerTask;

import javax.swing.*;


public class CoinPanel extends JPanel implements MouseListener{

	private JPanel panel;
	private BufferedImage canvas;
	private GraphingData graph;
	private Coin[][] coins;
	private int width, height;
	private boolean firstClick = true;
	private final CoinRun runner = new CoinRun(this);
	private Random rnd = new Random();
	private java.util.Timer timer;
	
	public CoinPanel(int width, int height) {
		this.width = width;
		this.height = height;
		this.canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		this.graph = new GraphingData(width, height);
		this.addMouseListener(this);
		initialiseDots();
		setUp();
	}
	
    public Dimension getPreferredSize() {
        return new Dimension(canvas.getWidth(), canvas.getHeight());
    }
	
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	Graphics2D g2 = (Graphics2D) g;
    	g2.drawImage(canvas, null, null);
    	if(firstClick) {
	    	g2.setFont(new Font("SansSerif", Font.BOLD, 24));
	    	g2.setColor(Color.red);
	        FontRenderContext frc = g2.getFontRenderContext();
	    	Font font = g2.getFont();
	        LineMetrics lm = font.getLineMetrics("0", frc);
	        String s = "Click Here" ;
	        float x = (float)(width - font.getStringBounds(s, frc).getWidth())/2;
	        float y = (float)(height - lm.getAscent())/2;
	    	g2.drawString(s, x, y);
    	}
    }
	 
    public void initialiseDots() {
    	coins = new Coin[height][width];
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				coins[i][j] = new Coin(this, j, i);
				coins[i][j].colourCoin();
			}
		}
		repaint();
    }

	public void setRGB(int x, int y, int rgb) {
		canvas.setRGB(x, y, rgb);
	}
	
	public void flipCoin() {
		coins[rnd.nextInt(height)][rnd.nextInt(width)].flip();
		//repaint();
	}
	
	public void setUp() {
		panel = new JPanel();
		panel.add(graph);
		graph.setPreferredSize(new Dimension(width, height));
	}
	
	public void createSquare(int x, int y) {
		int minX = x - 16 < 0? 0 : x - 16;
		int maxX = x + 16 >= width? width-1 : x + 16;
		int minY = y - 16 < 0? 0 : y - 16;
		int maxY = y + 16 >= height? height-1 : y + 16;
		for(int i = minY; i <= maxY; i++) {
			for(int j = minX; j <= maxX; j++) {
				coins[i][j].setColour(coins[i][j].getColours()[0]);
				coins[i][j].colourCoin();
			}
		}
	}
	
	public void printPercentageFlipped() {
		int count = 0;
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				if(coins[i][j].getColour().equals(coins[0][0].getColours()[1]))
					count++;
			}
		}
		double perc = (double)count/((double)height*(double)width)*100.0;
		graph.getData().add(perc);
		graph.setCurrentPerc((int)Math.round(perc));
		graph.repaint();
	}
    
    
	@Override
	public void mouseClicked(MouseEvent event) {
		if(event.getButton() == 1) {
			if(firstClick){
				runner.start();
				runner.setRunning(true);
				firstClick = !firstClick;
			} else {
				runner.setRunning(!runner.isRunning());
			}
		} else {
			//createSquare(event.getX(), event.getY());
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(final MouseEvent event) {
		if(timer == null && event.getButton() == 3) {
			timer = new java.util.Timer();
			timer.schedule(new TimerTask() {
				public void run() {
					Point point = new Point(MouseInfo.getPointerInfo().getLocation());
					SwingUtilities.convertPointFromScreen(point, event.getComponent());
					createSquare((int)point.getX(), (int)point.getY());
					repaint();
				}
			}, 0, 1);
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		if(timer != null) {
			timer.cancel();
			timer = null;
		}
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public JPanel getPanel() {
		return panel;
	}

}
