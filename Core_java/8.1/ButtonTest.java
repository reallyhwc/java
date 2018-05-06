import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @version 1.33 2007-06-12
 * @author Cay Horstmann
 */
public class ButtonTest
{
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
			{
				public void run()
				{
					ButtonFrame frame = new ButtonFrame();
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setVisible(true);
				}
			});
	}
}

/**
 * A frame with a button panel
 */
class ButtonFrame extends JFrame
{
	public ButtonFrame()
	{
		setTitle("ButtonTest");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		buttonPanel = new JPanel();

		makeButton("yellow",Color.YELLOW);
		makeButton("blue",Color.BLUE);
		makeButton("red",Color.RED);
		

		add(buttonPanel);

		
	}

	public void makeButton(String name, final Color backgroundColor) {
		JButton button = new JButton(name);
		buttonPanel.add(button);
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event) {
				buttonPanel.setBackground(backgroundColor);
			}
		});
	}

	private JPanel buttonPanel;
	//public  Color backgroundColor;
	public static final int DEFAULT_WIDTH = 300;
	public static final int DEFAULT_HEIGHT = 200;
}