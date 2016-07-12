import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.util.ArrayList;

import javax.swing.*;
 
public class GraphingData extends JPanel {
	private ArrayList<Double> data;
    private final int padding = 20;
    private int currentPerc = 0;
    private Line2D.Double ordinate;
    private Line2D.Double abcissa;
    private int w;
    private int h;
    private JFrame f;
    
    public GraphingData(int width, int height) {
    	data = new ArrayList<Double>();
    	w = width;
    	h = height;
		setUp();
	}
    
    public void setUp() {
		setBackground(Color.white);
		
    	ordinate = new Line2D.Double(padding, padding, padding, h-padding);
    	abcissa = new Line2D.Double(padding, h-padding, w-padding, h-padding);
    }
/*    	
    	f = new JFrame("Plot");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(this);
        f.setLocation(200,200);
        f.setVisible(true);
        f.pack();
    }
 
    @Override
    public Dimension getPreferredSize() {
    	return new Dimension(w, h);
    }
*/    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        g2.draw(ordinate);
        g2.draw(abcissa);
        // Draw labels.
        Font font = g2.getFont();
        FontRenderContext frc = g2.getFontRenderContext();
        LineMetrics lm = font.getLineMetrics("0", frc);
        float sh = lm.getAscent() + lm.getDescent();
        // Ordinate label.
        String s = "% Red";
        float sy = padding + ((h - 2*padding) - s.length()*sh)/2 + lm.getAscent();
        for(int i = 0; i < s.length(); i++) {
            String letter = String.valueOf(s.charAt(i));
            float sw = (float)font.getStringBounds(letter, frc).getWidth();
            float sx = (padding - sw)/2;
            g2.drawString(letter, sx, sy);
            sy += sh;
        }
        // Abcissa label.
        s = "Number of Flips";
        sy = h - padding + (padding - sh)/2 + lm.getAscent();
        float sw = (float)font.getStringBounds(s, frc).getWidth();
        float sx = (w - sw)/2;
        g2.drawString(s, sx, sy);
        // Draw lines.
        double xInc = (double)(w - 2*padding)/(data.size()-1);
        double scale = (double)(h - 2*padding)/52;//getMax();
        float percX = 0;
        float percY = 0;
        g2.setPaint(Color.decode("#FF0000"));
        for(int i = 0; i < data.size()-1; i++) {
            double x1 = padding + i*xInc;
            double y1 = h - padding - scale*data.get(i);
            double x2 = padding + (i+1)*xInc;
            double y2 = h - padding - scale*data.get(i+1);
            g2.draw(new Line2D.Double(x1, y1, x2, y2));
            if(i == data.size()-2) {
            	percX = (float)x2;
            	percY = (float)y2;
            }
        }
        // draw percent
        g2.drawString(currentPerc + "%", percX - lm.getAscent(), percY - lm.getAscent());
        // Mark data points.
//        g2.setPaint(Color.red);
//        for(int i = 0; i < data.length; i++) {
//            double x = padding + i*xInc;
//            double y = h - padding - scale*data[i];
//            g2.fill(new Ellipse2D.Double(x-2, y-2, 4, 4));
//        }
    }
 
    private Double getMax() {
        Double max = -Double.MAX_VALUE;
        for(int i = 0; i < data.size(); i++) {
            if(data.get(i) > max)
                max = data.get(i);
        }
        return max;
    }
    
    public ArrayList<Double> getData() {
		return data;
	}
    
    public void setCurrentPerc(int currentPerc) {
		this.currentPerc = currentPerc;
	}
 
    public static void main(String[] args) {
        GraphingData graph = new GraphingData(500, 500);
    }
}