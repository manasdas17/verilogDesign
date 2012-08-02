package verilog.awt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import javax.swing.text.StyledEditorKit.BoldAction;

class Model extends JScrollPane implements MouseListener, MouseMotionListener, ActionListener, ItemListener
{
	/**
     * 
     */
	private static final long		serialVersionUID	= 1L;
	// private JScrollPane scrollPane;
	// LineList lineList = new LineList();
	// PolygonList polyginList = new PolygonList();
	ShapeList						shapeList			= new ShapeList();
	private Point					p					= new Point(0, 0);
	private Event					event				= new Event();
	private JPopupMenu				popup;
	// ConnectionLine connectionLine = new ConnectionLine(new Point (120,260),
	// new Point (300,400));
	private static ResourceBundle	verilogGui			= ResourceBundle.getBundle("verilog_gui");

	private ArrayList<Shape>		focusList			= new ArrayList<Shape>();

	/**
	 * @return the focusList
	 */
	public synchronized ArrayList<Shape> getFocusList()
	{
		return focusList;
	}

	/**
	 * @param focusList
	 *            the focusList to set
	 */
	public synchronized void setFocusList(ArrayList<Shape> focusList)
	{
		this.focusList = focusList;
	}

	// "verilog/gui/verilog_gui");

	public Model()
	{
		// setTitle("Scrolling Pane Application");
		setSize(300, 200);
		setBackground(Color.red);
		setVisible(true);
		createPopupMenu();
		// WindowUtilities.setNativeLookAndFeel();

		// JPanel topPanel = new JPanel();
		// topPanel.setLayout(new BorderLayout());
		// getContentPane().add(topPanel);

		// Icon image = new ImageIcon("main.gif");
		// JLabel label = new JLabel(image);

		// Create a tabbed pane
		// scrollPane = new JScrollPane();
		// scrollPane.getViewport().add(label);
		// topPanel.add(scrollPane, BorderLayout.CENTER);

		addMouseListener(this);
		addMouseMotionListener(this);

	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	protected static ImageIcon createImageIcon(String path)
	{
		java.net.URL imgURL = ModelPopupMenu.class.getResource(path);
		if (imgURL != null)
		{
			return new ImageIcon(imgURL);
		}
		else
		{
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	public JMenuBar createMenuBar()
	{
		JMenuBar menuBar;
		JMenu menu, submenu;
		JMenuItem menuItem;
		JRadioButtonMenuItem rbMenuItem;
		JCheckBoxMenuItem cbMenuItem;

		// Create the menu bar.
		menuBar = new JMenuBar();

		// Build the first menu.
		menu = new JMenu(verilogGui.getString("menu_main_file"));
		menu.setMnemonic(KeyEvent.VK_F);
		menu.getAccessibleContext().setAccessibleDescription("The only menu in this program that has menu items");
		menuBar.add(menu);

		// a group of JMenuItems
		menuItem = new JMenuItem("A text-only menu item", KeyEvent.VK_T);
		// menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		ImageIcon icon = createImageIcon("images/1.gif");
		menuItem = new JMenuItem("Both text and icon", icon);
		menuItem.setMnemonic(KeyEvent.VK_B);
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem(icon);
		menuItem.setMnemonic(KeyEvent.VK_D);
		menuItem.addActionListener(this);
		menu.add(menuItem);

		// a group of radio button menu items
		menu.addSeparator();
		ButtonGroup group = new ButtonGroup();

		rbMenuItem = new JRadioButtonMenuItem("A radio button menu item");
		rbMenuItem.setSelected(true);
		rbMenuItem.setMnemonic(KeyEvent.VK_R);
		group.add(rbMenuItem);
		rbMenuItem.addActionListener(this);
		menu.add(rbMenuItem);

		rbMenuItem = new JRadioButtonMenuItem("Another one");
		rbMenuItem.setMnemonic(KeyEvent.VK_O);
		group.add(rbMenuItem);
		rbMenuItem.addActionListener(this);
		menu.add(rbMenuItem);

		// a group of check box menu items
		menu.addSeparator();
		cbMenuItem = new JCheckBoxMenuItem("A check box menu item");
		cbMenuItem.setMnemonic(KeyEvent.VK_C);
		cbMenuItem.addItemListener(this);
		menu.add(cbMenuItem);

		cbMenuItem = new JCheckBoxMenuItem("Another one");
		cbMenuItem.setMnemonic(KeyEvent.VK_H);
		cbMenuItem.addItemListener(this);
		menu.add(cbMenuItem);

		// a submenu
		menu.addSeparator();
		submenu = new JMenu("A submenu");
		submenu.setMnemonic(KeyEvent.VK_S);

		menuItem = new JMenuItem("An item in the submenu");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		submenu.add(menuItem);

		menuItem = new JMenuItem("Another item");
		menuItem.addActionListener(this);
		submenu.add(menuItem);
		menu.add(submenu);

		// Build second menu in the menu bar.
		menu = new JMenu("Another Menu");
		menu.setMnemonic(KeyEvent.VK_N);
		menu.getAccessibleContext().setAccessibleDescription("This menu does nothing");
		menuBar.add(menu);

		return menuBar;
	}

	public void createPopupMenu()
	{
		JMenuItem mi;

		popup = new JPopupMenu("Edit");

		mi = new JMenuItem(verilogGui.getString("new_block"));
		mi.addActionListener(this);
		popup.add(mi);

		mi = new JMenuItem(verilogGui.getString("add_input"));
		mi.setHorizontalTextPosition(JMenuItem.RIGHT);
		mi.addActionListener(this);
		popup.add(mi);
		mi.setBackground(Color.cyan);

		popup.addSeparator();
		mi = new JMenuItem("Paste");
		mi.addActionListener(this);
		mi.setBackground(Color.RED);
		popup.add(mi);
		popup.setBorderPainted(true);
		popup.setBackground(Color.cyan);

		// popup.setLabel("Justification");
		popup.setBorder(new BevelBorder(BevelBorder.RAISED));
		popup.setBackground(Color.RED);
		// popup.addPopupMenuListener(new PopupPrintListener());

		// setComponentPopupMenu(popup);

	}

	public void addShape(Shape shape)
	{
		shapeList.add(shape);
		shapeList.setCurShape(shape);
	}

	@Override
	public void paint(Graphics g)
	{
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		shapeList.paint(g);
	}

	public void mouseDragged(java.awt.event.MouseEvent e)
	{
		p.x = e.getX();
		p.y = e.getY();
		shapeList.mouseDragged(p);
		this.repaint();

	}

	public void mouseMoved(java.awt.event.MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	public void mouseClicked(java.awt.event.MouseEvent e)
	{

	}

	public void mouseEntered(java.awt.event.MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	public void mouseExited(java.awt.event.MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	public void mousePressed(java.awt.event.MouseEvent e)
	{

		boolean b = false;
		if (e.isPopupTrigger())
		{
			 
			for (Shape shape : focusList)
			{
				shape.setFocus(false);
			}
			focusList.clear();
			
			for (Shape shape : shapeList.shapes)
			{
				if (shape instanceof Polygon)
				{
					JPopupMenu pm = ((Polygon) shape).getPopupMenu();
					if (pm != null && ((Polygon) shape).isInside(e.getPoint()))
					{
						pm.show(e.getComponent(), e.getX(), e.getY());
						b = true;
						break;
					}
				}
			}
			if (!b)
			{
				popup.show(e.getComponent(), e.getX(), e.getY());
			}

		}
		if (e.getButton() == 1)
		{
			if (!e.isControlDown())
			{
				for (Shape shape : focusList)
				{
					shape.setFocus(false);
				}
				focusList.clear();
			}
			event.getPoint().x = e.getX();
			event.getPoint().y = e.getY();

			// find if more then one point was relased mouse (it means that
			// dragged
			// point related on another point)

			if (shapeList.mousePressed(event))
			{
				if (event.geteffectedShapes().size() > 0)
				{

					if (event.geteffectedShapes().get(0) instanceof ConectionPoint)
					{
						ConectionPoint conectionPoint = (ConectionPoint) event.geteffectedShapes().get(0);

						ConectionPoint p1 = new ConectionPoint(new Point(conectionPoint.getPoint().x, conectionPoint.getPoint().y), null);

						Line line = new Line(this);
						line.addPoint(p1);
						line.addPoint(conectionPoint);
						addShape(line);

						shapeList.mousePressed(event);
						event.clearAffectedShade();
					}
				}
				for (Shape shape : focusList)
				{
					shape.setFocus(true);
					
				}
			}
			else
			{
				Line line = new Line(this);
				line.addPoint(new Point(event.getPoint().x, event.getPoint().y));
				line.addPoint(new Point(event.getPoint().x, event.getPoint().y));
				addShape(line);
				shapeList.mousePressed(event);

				// make line focus
				for (Shape shape : focusList)
				{
					shape.setFocus(false);
				}
				focusList.clear();
				getFocusList().clear();
				getFocusList().add(line);
				line.setFocus(true);
			}
		}
		this.repaint();
	}

	public void mouseReleased(java.awt.event.MouseEvent e)
	{

		event.mouseEvent = e;
		event.getPoint().x = e.getX();
		event.getPoint().y = e.getY();

		boolean b = false;
		if (e.isPopupTrigger())
		{

			for (Shape shape : shapeList.shapes)
			{
				if (shape instanceof Polygon)
				{
					JPopupMenu pm = ((Polygon) shape).getPopupMenu();
					if (pm != null && ((Polygon) shape).isInside(event.getPoint()))
					{
						pm.show(e.getComponent(), e.getX(), e.getY());
						b = true;
						// make just this object focus
						for (Shape shape1 : focusList)
						{
							shape1.setFocus(false);
						}
						focusList.clear();
						focusList.add(shape);
						shape.setFocus(true);
						break;
					}
				}
			}
			if (!b)
			{
				popup.show(e.getComponent(), e.getX(), e.getY());
			}

		}

		shapeList.mouseReleased(event);

		// find if more then one point was relased mouse (it means that draged
		// point realed on another point)
		ConectionPoint p1 = null;
		for (Shape affctedShape : event.geteffectedShapes())
		{
			if (affctedShape instanceof ConectionPoint)
			{
				ConectionPoint conectionPoint = (ConectionPoint) affctedShape;
				if (!conectionPoint.isMoveAble())
				{
					p1 = conectionPoint;
					event.geteffectedShapes().remove(p1);
					break;
				}
			}
		}

		if (p1 != null)
		{
			for (Shape obj : event.geteffectedShapes())
			{

				if (obj instanceof ConectionPoint)
				{
					if (((ShapeList) obj.getFather()).getPoint(1) != p1
							&& (((Section) obj.getFather()).getP1().inSidePoint1((double) p1.getPoint().x, (double) p1.getPoint().y) || p1.inSidePoint1(((Section) obj.getFather())
									.getP1().getPoint().x, ((Section) obj.getFather()).getP1().getPoint().y)))
					{

						((ShapeList) obj.getFather()).setPoint(p1, 0);
						// ((ConectionPoint) obj).setPoint(p1.getPoint());

					}
					if (((ShapeList) obj.getFather()).getPoint(0) != p1
							&& (((Section) obj.getFather()).getP2().inSidePoint1((double) p1.getPoint().x, (double) p1.getPoint().y) || p1.inSidePoint1(((Section) obj.getFather())
									.getP2().getPoint().x, ((Section) obj.getFather()).getP2().getPoint().y)))
					{

						((ShapeList) obj.getFather()).setPoint(p1, 1);

						// ((ConectionPoint) obj).setPoint(p1.getPoint());
					}
				}
				if (obj instanceof ConnectionLine)
				{

				}
			}

		}
		this.repaint();
		event.clearAffectedShade();

	}

	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub

		String command = e.getActionCommand();

		if (command.equals(verilogGui.getString("new_block")))
		{
			Block block = new Block(this);
			block.setLocaion(new Point(this.getMousePosition().x, this.getMousePosition().y));
			block.setSize(new Point(100, 100));
			block.init();
			block.setFillColor(Color.MAGENTA);
			this.addShape(block);

			// make shape focus
			for (Shape shape : focusList)
			{
				shape.setFocus(false);
			}
			focusList.clear();
			getFocusList().clear();
			getFocusList().add(block);
			block.setFocus(true);

			// perform cut operation
		}
		else if (command.equals("Copy"))
		{
			// perform copy operation
		}
		else if (command.equals("Paste"))
		{
			// perform paste operation
		}
		repaint();

	}

	public void itemStateChanged(ItemEvent e)
	{
		JMenuItem source = (JMenuItem) (e.getSource());
		String s = "Item event detected." + "    Event source: " + source.getText()

		+ "    New state: " + ((e.getStateChange() == ItemEvent.SELECTED) ? "selected" : "unselected");

	}

	public static void main(String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{

		WindowUtilities.setNativeLookAndFeel();

		// Create an instance of the test application
		Model mainFrame = new Model();
		mainFrame.setVisible(true);
		
		Block block1 = new Block(mainFrame);
		block1.setLocaion(new Point(300, 300));
		block1.setSize(new Point(100, 100));
		block1.init();
		block1.setFillColor(Color.MAGENTA);
		mainFrame.addShape(block1);

		Block block2 = new Block(mainFrame);
		block2.setLocaion(new Point(100, 300));
		block2.setSize(new Point(100, 100));
		block2.init();
		mainFrame.addShape(block2);

		JFrame aWindow = new JFrame();
		/*
		 * / ModelPopupMenu demo = new ModelPopupMenu();
		 * aWindow.setJMenuBar(demo.createMenuBar());
		 * aWindow.setContentPane(demo.createContentPane());
		 */

		// Create and set up the popup menu.
		// demo.createPopupMenu();
		
		JToolBar tbar = new JToolBar();
	 
	   tbar.add(new JButton(IconUtils.getGeneralIcon( "Open", 16 ) ));
	    tbar.add(new JButton("B"));
	    tbar.add(new JButton("C"));
	 
	 
	    aWindow.getContentPane().setLayout(new BorderLayout());
	    aWindow.getContentPane().add(tbar, BorderLayout.NORTH);
	    tbar.setFloatable(true);
	    
		aWindow.setJMenuBar(mainFrame.createMenuBar());

		aWindow.setBounds(200, 200, 500, 500);
		aWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container content = aWindow.getContentPane();
		content.add(mainFrame);

		// aWindow.setContentPane(mainFrame);

		aWindow.setVisible(true);

	}

}
