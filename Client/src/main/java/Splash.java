import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @Author Tristam <Hassan>
 * @Project - 2. Avalon Client
 * @Date - 22 Mar 2016
 *
 **/
public class Splash {

    private JLabel label = new JLabel();
    private JFrame frame = new JFrame();
    private ImageIcon image = null;
    @SuppressWarnings("unused")
    private final Dimension dimsension = Toolkit.getDefaultToolkit().getScreenSize();

    public void showSplash(final String url, final double seconds) {
	try {
	    image = new ImageIcon(new URL(url));
	}
	catch (Exception e) {
	    JOptionPane.showMessageDialog(null, "Image could not be fetched. Please contact Andreas.");
	}
	frame.setUndecorated(true);
	label.setIcon(image);
	frame.setSize(image.getIconWidth(), image.getIconHeight());
	frame.setLocationRelativeTo(null);
	frame.add(label);
	frame.setVisible(true);
	try {
	    Thread.sleep((int) (seconds * 1000));
	}
	catch (InterruptedException e) {
	    JOptionPane.showMessageDialog(null, "The Splash was interrupted. Please contact Andreas.");
	}
	frame.setVisible(false);
	try {
	    finalize();
	}
	catch (Throwable e) {
	    e.printStackTrace();
	}
    }

}
