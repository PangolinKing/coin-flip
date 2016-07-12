import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class CoinFrame extends JFrame{

	private CoinPanel cPanel;
	private int width, height;
	
	
	public CoinFrame(int width, int height) {
		super("Coins");
		this.width = width;
		this.height = height;
		this.cPanel = new CoinPanel(width, height/2);
		setUp();
	}
	
	private void setUp() {
        
        JPanel pane = new JPanel(new GridLayout(2, 0));
        pane.add(cPanel);
        pane.add(cPanel.getPanel());
		
		getContentPane().add(pane);
		
//        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Coin_Icon.png")));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((int)screen.getWidth() / 2 - width / 2,
        		(int)screen.getHeight() / 2 - height / 2, width, height);
	}

	public static void main(String[] args) {
		CoinFrame frame = new CoinFrame(800, 800);
	}

}
