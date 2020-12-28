import java.applet.Applet;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class Loader extends Applet implements ActionListener {

    private static final long serialVersionUID = 7639088664641445302L;
    public static Properties client_parameters = new Properties();
    public JFrame client_frame;
    public JPanel client_panel = new JPanel();

    public static String host_IP = "72.191.29.70";
//	public static String host_IP = "167.86.82.69";
    public static boolean hosted = true;

    public static boolean usingRS = false;
    public static boolean useIsaac = false;
    public static boolean useRoute = false;
    public static boolean useMapsTest = false;
    public boolean takeScreeny;
    public int screenshot;

    public static String SERVER_NAME = "Warbycode";
    public final static int PORT = 43594;
    public static boolean LOBBY_ENABLED = false;
    public static boolean DISABLE_XTEA_CRASH = true;
    public static boolean DISABLE_USELESS_PACKETS = false;
    public static boolean DISABLE_RSA = false;
    public static boolean DISABLE_CS_MAP_CHAR_CHECK = true;
    public static boolean DISABLE_SOFTWARE_MODE = false;
    public static final int REVISION = 718;
    public static final int LOBBY_PORT = PORT;
    public static int SUB_REVISION = 3;//use 3 to go to Matrix or 1 to go to Avalon
    public static final boolean ACCOUNT_CREATION_DISABLED = true;
    public static final boolean RS = false;

    public static String LOBBY_IP = host_IP;
    public static String ICON_URL = "http://vignette1.wikia.nocookie.net/gtawiki/images/f/fa/Rockstar_London_Logo.png/revision/latest?cb=20100211093254";
    public static Loader instance;
    public static int[] outSizes = new int[256];

    public static TrayIcon trayIcon;

    private static final String Client_Version = "1.2.1\n\nWhat's new in this release: \n - Client session timer";

    final static Splash splash = new Splash();

    public void addTray() {
	if (!SystemTray.isSupported()) {
	    System.err.println("[Error] Tray icons not supported.");
	    return;
	}
	Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.jpg"));
	trayIcon = new TrayIcon(image, "WarByCode");
	try {
	    SystemTray tray = SystemTray.getSystemTray();
	    tray.add(trayIcon);
	}
	catch (AWTException e1) {
	    e1.printStackTrace();
	}
    }

    public static void main(String[] args) {
	ItemPrices.init();
	System.setProperty("java.net.preferIPv4Stack", "true");
	System.setProperty("java.net.preferIPv6Addresses", "false");
	setParams();
	try {
	    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    JFrame.setDefaultLookAndFeelDecorated(true);
	    JDialog.setDefaultLookAndFeelDecorated(true);
	}
	catch (Throwable e) {
	    e.printStackTrace();
	    System.out.println("Theme not detected, reverting to OS Default.");
	    try {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    }
	    catch (Throwable e2) {
		e.printStackTrace();
	    }
	}
	Loader loader = new Loader();
	final JFrame frame = new JFrame();
	JPanel panel = new JPanel();
	panel.setLayout(new BorderLayout());
	panel.add(loader);
	panel.setPreferredSize(new Dimension(768, 503));
	frame.setMinimumSize(new Dimension(768, 503));
	panel.setBackground(Color.BLACK);
	JMenuBar bar = new JMenuBar();
	
	for (final Option o : Option.values()) {
	    final MenuButton menu = new MenuButton(o.name);
	    menu.createToolTip();
	    menu.addMouseListener(new MouseListener() {

		@Override
		public void mouseClicked(MouseEvent e) {
		    frame.getJMenuBar().createToolTip();
		    switch (o.option) {
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			    JOptionPane.showMessageDialog(frame,
				    "" + o.name + " button is not handled yet.",
				        "Warning",
				        JOptionPane.WARNING_MESSAGE);
			    break;
			case 10:
			    client.FPS = !client.FPS;
			    if (client.FPS)
				Class255.method2435("FPS on", 899052076);
			    else
				Class255.method2435("FPS off", 1184714257);
			    JOptionPane.showMessageDialog(frame,
				    "Display fps has been " + (client.FPS ? "Activated" : "Deactivated"),
				        "FPS",
				        JOptionPane.INFORMATION_MESSAGE);
			    break;
		    }
		    Preferences.saveSettings();
		    System.out.println("Mouse clicked " + o.name());

		}

		@Override
		public void mousePressed(MouseEvent e) {
		    // TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
		    // TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
		    System.out.println("Entered " + o.name());
		   // menu.setToolTipText(o.toolTip);
		    frame.getJMenuBar().setToolTipText(o.toolTip);
		    menu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		}

		@Override
		public void mouseExited(MouseEvent e) {
		    System.out.println("Exit " + o.name());
		    menu.setToolTipText(null);

		}

	    });

	    bar.add(menu);
	}
//	frame.setJMenuBar(bar);
	frame.setFont(new Font("System", Font.PLAIN, 14));
	Font f = frame.getFont();
	FontMetrics fm = frame.getFontMetrics(f);
	int x = fm.stringWidth("");
	int y = fm.stringWidth(" ");
	int z = frame.getWidth() / 2 - (x / 10);
	int w = z / y;
	String pad = "";
	//for (int i=0; i!=w; i++) pad +=" "; 
	pad = String.format("%" + w + "s", pad);
	frame.setTitle(SERVER_NAME);
	frame.setResizable(true);
	//frame.addWindowListener(client);
	frame.getContentPane().add(panel, "Center");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setIconImage(Toolkit.getDefaultToolkit().getImage("bin/icon.jpg"));
	frame.pack();
	frame.setLocationRelativeTo(null);
	frame.setVisible(true);
	frame.toFront();
	client clnt = new client();
	clnt.supplyApplet(loader);
	clnt.init();
	clnt.start();
    }

	static void setParams() {
		client_parameters.put("separate_jvm", "true");
		client_parameters.put("boxbgcolor", "black");
		client_parameters.put("image", "http://www.runescape.com/img/game/splash2.gif");
		client_parameters.put("centerimage", "true");
		client_parameters.put("boxborder", "false");
		client_parameters.put("java_arguments", "-Xmx256m -Xss2m -Dsun.java2d.noddraw=true -XX:CompileThreshold=1500 -Xincgc -XX:+UseConcMarkSweepGC -XX:+UseParNewGC");
		client_parameters.put("27", "0");
		client_parameters.put("1", "0");
		client_parameters.put("16", "false");
		client_parameters.put("17", "false");
		client_parameters.put("21", "1"); // WORLD ID
		client_parameters.put("30", "false");
		client_parameters.put("20", LOBBY_IP);
		client_parameters.put("29", "");
		client_parameters.put("11", "true");
		client_parameters.put("25", "1378752098");
		client_parameters.put("28", "0");
		client_parameters.put("8", ".runescape.com");
		client_parameters.put("23", "false");
		client_parameters.put("32", "0");
		client_parameters.put("15", "wwGlrZHF5gKN6D3mDdihco3oPeYN2KFybL9hUUFqOvk");
		client_parameters.put("0", "IjGJjn4L3q5lRpOR9ClzZQ");
		client_parameters.put("2", "");
		client_parameters.put("4", "1"); // WORLD ID
		client_parameters.put("14", "");
		client_parameters.put("5", "8194");
		client_parameters.put("-1", "QlwePyRU5GcnAn1lr035ag");
		client_parameters.put("6", "0");
		client_parameters.put("24", "true,false,0,43,200,18,0,21,354,-15,Verdana,11,0xF4ECE9,candy_bar_middle.gif,candy_bar_back.gif,candy_bar_outline_left.gif,candy_bar_outline_right.gif,candy_bar_outline_top.gif,candy_bar_outline_bottom.gif,loadbar_body_left.gif,loadbar_body_right.gif,loadbar_body_fill.gif,6");
		client_parameters.put("3", "hAJWGrsaETglRjuwxMwnlA/d5W6EgYWx");
		client_parameters.put("12", "false");
		client_parameters.put("13", "0");
		client_parameters.put("26", "0");
		client_parameters.put("9", "77");
		client_parameters.put("22", "false");
		client_parameters.put("18", "false");
		client_parameters.put("33", "");
		client_parameters.put("haveie6", "false");
	}

    public static enum Option {
	FORUMS("Forums", 4, "Opens forum website"),

	VOTE("Vote", 5, "Opens voting website"),

	DONATE("Store", 6, "Opens donate website"),

	HIGHSCORES("Highscores", 7, "Opens highscore website"),

	WIKI("Wiki", 8, "Opens " + SERVER_NAME + "-wikipedia"),

	DISCORD("Discord", 9, "Opens " + SERVER_NAME + " own discord"),

	DISPLAYFPS("Display-FPS", 10, "Toggle displaying fps");

	public String name, toolTip;
	public int option;

	private Option(String name, int option, String toolTip) {
	    this.name = name;
	    this.option = option;
	    this.toolTip = toolTip;
	}
    }

    public String getParameter(String string) {
	return (String) client_parameters.get(string);
    }

    public URL getDocumentBase() {
	return getCodeBase();
    }

    @Override
    public URL getCodeBase() {
	try {
	    return new URL("https://" + host_IP);
	}
	catch (Exception exception) {
	    exception.printStackTrace();
	    return null;
	}
    }

    public void sendWebsiteURL(String url) {
	String os = System.getProperty("os.name");
	try {
	    if (os.startsWith("Windows")) {
		Runtime.getRuntime().exec("rund1132 url.dll, FileProtocolHandler " + url);
	    } else {
		final String[] BROWSER = { "firefox", "mozilla", "chrome", "opera" };
		String MYBROWSER = null;
		for (int i = 0; i < BROWSER.length && MYBROWSER == null; i++)
		    if (Runtime.getRuntime().exec(new String[] { "which", BROWSER[i] }).waitFor() == 0)
			MYBROWSER = BROWSER[i];
		Runtime.getRuntime().exec(new String[] { MYBROWSER, url });

	    }
	}
	catch (Exception e) {
	    System.err.println(os + " : Failed to open.");
	}
    }

    public String getUptime(boolean withCharacters) {
	RuntimeMXBean mx = ManagementFactory.getRuntimeMXBean();
	DateFormat df = new SimpleDateFormat(withCharacters ? "HH 'hours', mm 'mins', ss 'seconds'" : "HH':'mm':'ss");
	df.setTimeZone(TimeZone.getTimeZone("GMT+0"));
	return df.format(new Date(mx.getUptime()));
    }

    public static long getUpTime() {
	RuntimeMXBean mx = ManagementFactory.getRuntimeMXBean();
	DateFormat df = new SimpleDateFormat("HH mm ss");
	df.setTimeZone(TimeZone.getTimeZone("GMT+0"));
	long time = 0;
	time = mx.getUptime();
	return time;
    }

    public void takeScreenshot() {
	try {
	    String os = System.getProperty("os.name");
	    if ((os.startsWith("Mac")) || (os.startsWith("Linux"))) {
		JOptionPane.showMessageDialog(null, "Not supported for your OS yet.", "=(", 0);
		return;
	    }
	    Calendar now = Calendar.getInstance();
	    Window w = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusedWindow();
	    Point p = w.getLocationOnScreen();
	    int x = (int) p.getX();
	    int y = (int) p.getY();
	    int width = w.getWidth();
	    int height = w.getHeight();
	    Robot robot = new Robot(w.getGraphicsConfiguration().getDevice());
	    Rectangle rect = new Rectangle(x, y, width, height);
	    BufferedImage bufferedImage = robot.createScreenCapture(rect);
	    final String player = (client.currentPlayer != null ? " - " + client.currentPlayer + " - " : "");
	    String name = JOptionPane.showInputDialog(null, "Enter a name for your image: ", "Image name", -1);
	    File folder = new File(System.getenv("USERPROFILE") + "/Avalon/Screenshots");
	    File Image = new File(System.getenv("USERPROFILE") + "/Avalon/Screenshots/" + name + " " + player + " (" + Utils.getDay() + " " + Utils.getMonth(now.get(Calendar.MONTH) + 1) + ").png");

	    if (!folder.exists())
		folder.mkdir();
	    if (name.isEmpty()) {
		JOptionPane.showMessageDialog(null, "To save your image, please enter a name for it.", "=(", 0);
		return;
	    }
	    if (Image.exists()) {
		int option = JOptionPane.showOptionDialog(null, "An image with this name is already created. Do you wish to overrwrite it?", "Attention", 0, 2, null, null, null);
		if (option == 0) {
		    ImageIO.write(bufferedImage, "png", new File(System.getenv("USERPROFILE") + "/Avalon/Screenshots/" + name + " " + player + " (" + Utils.getDay() + " " + Utils.getMonth(now.get(Calendar.MONTH) + 1) + ").png"));
		    Class255.consolePrint("Image saved as " + name + " - " + System.getenv("USERPROFILE") + "/Avalon/Screenshots/", 93254474);
		    addTray();
		    trayIcon.displayMessage("Screenshot saved!", "Your image is located in " + System.getenv("USERPROFILE") + "/Avalon/Screenshots. Right-click the camera icon to open the folder", TrayIcon.MessageType.INFO);
		}
		return;
	    }
	    addTray();
	    trayIcon.displayMessage("Screenshot saved!", "Your image can be found at " + System.getenv("USERPROFILE") + "/Avalon/Screenshots. Right-click the camera icon to open the folder", TrayIcon.MessageType.INFO);
	    ImageIO.write(bufferedImage, "png", new File(System.getenv("USERPROFILE") + "/Avalon/Screenshots/" + name + " " + player + " (" + Utils.getDay() + " " + Utils.getMonth(now.get(Calendar.MONTH) + 1) + ").png"));
	    Class255.consolePrint("Image saved as " + name + " - " + System.getenv("USERPROFILE") + "/Avalon/Screenshots/", 93254474);
	}
	catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public void setTheme(JMenuItem item) {
	try {
	    String theme = item.getText().replace(" ", "");
	    UIManager.setLookAndFeel("org.jvnet.substance.skin.Substance" + theme + "LookAndFeel");
	}
	catch (Exception e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	int action = Integer.parseInt(e.getActionCommand());
	if (Class287.myPlayer == null) {
	    JOptionPane.showMessageDialog(null, "Please wait while the client finishes loading.", "Be patient!", JOptionPane.INFORMATION_MESSAGE);
	    return;
	}
	switch (action) {
	    case 1:
		takeScreenshot();
		break;
	    case 2:
		JOptionPane.showMessageDialog(null, "Added soon.");
		break;
	    case 3:
		JOptionPane.showMessageDialog(null, "Added soon.");
		break;
	    case 4:
		JOptionPane.showMessageDialog(null, "Added soon.");
		break;
	    case 5:
		JOptionPane.showMessageDialog(null, "Added soon.");
		break;
	    case 6:
		client.FPS = !client.FPS;
		break;
	    case 7:
		sendInfo();
		break;
	    case 9:
		client.Zoom = !client.Zoom;
		break;
	    case 10:
		client.drag = !client.drag;
		break;
	}
	Preferences.saveSettings();

    }

    private void sendInfo() {
	int cores = Runtime.getRuntime().availableProcessors();
	JTextArea text = new JTextArea(7, 5);
	JScrollPane scrollPane = new JScrollPane(text);
	text.setText("Avalon - Client Version " + Client_Version + "\n\n\n © Avalon is created for educational purposes only. All credits goes to Jagex and its respective owners. " + "\n\n© Avalon is not affiliated with Jagex Ltd/RuneScape in anyway. \n \n Operating system: " + System.getProperty("os.name") + " " + System.getProperty("os.arch") + " \n Java version: " + System.getProperty("java.version") + " \n " + (cores > 1 ? "Total CPU threads: " : "CPU thread: ") + cores);
	text.setWrapStyleWord(true);
	text.setLineWrap(true);
	text.setCaretPosition(0);
	text.setEditable(false);
	scrollPane.setPreferredSize(new Dimension(450, 175));
	JOptionPane.showMessageDialog(null, scrollPane, "About Avalon", JOptionPane.INFORMATION_MESSAGE);

    }
}
