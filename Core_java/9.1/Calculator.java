import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @version 1.33 2007-06-12
 * @author Cay Horstmann
 */
public class Calculator
{
   public static void main(String[] args)
   {
	  EventQueue.invokeLater(new Runnable()
		 {
			public void run()
			{
			   CalculatorFrame frame = new CalculatorFrame();
			   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			   frame.setVisible(true);
			}
		 });
   }
}

/**
 * A frame with a calculator panel.
 */
class CalculatorFrame extends JFrame
{
   public CalculatorFrame()
   {
	  /*
	  设置标题，新建面板，添加面板，


	  pack: 根据容器内的组件以最优的方式调整容器的大小.

	  show: 将窗口显示出来
	  */
	  setTitle("Calculator");
	  CalculatorPanel panel = new CalculatorPanel();
	  add(panel);
	  pack();
   }
}

/**
 * A panel with calculator buttons and a result display.
 */
class CalculatorPanel extends JPanel
{
   public CalculatorPanel()
   {
	  setLayout(new BorderLayout());

	  result = 0;
	  lastCommand = "=";
	  start = true;

	  // add the display
	  /*
		这里新建了一个button版本的显示器，有点搞不清楚
		这里总的点击开关‘setEnabled’设置为关闭，仅仅只是作为一个显示器
		然后把button放在了总面板的最上面
		0表示的就是最初始的显示
	  */
	  display = new JButton("0");
	  display.setEnabled(false);
	  add(display, BorderLayout.NORTH);
	  /*
		然后新建了两个监听器，一个用来读取输入，一个用来读取命令
	  */
	  ActionListener insert = new InsertAction();
	  ActionListener command = new CommandAction();

	  // add the buttons in a 4 x 4 grid
	  /*
		新建一个按钮面板，并设置网格布局
	  */
	  panel = new JPanel();
	  panel.setLayout(new GridLayout(4, 4));
	  /*
		通过自定义的addbutton方法，依次新建一个按键
		为按键绑定不同属性的监听者
		在按钮面板中添加按钮
	  */
	  addButton("7", insert);
	  addButton("8", insert);
	  addButton("9", insert);
	  addButton("/", command);

	  addButton("4", insert);
	  addButton("5", insert);
	  addButton("6", insert);
	  addButton("*", command);

	  addButton("1", insert);
	  addButton("2", insert);
	  addButton("3", insert);
	  addButton("-", command);

	  addButton("0", insert);
	  addButton(".", insert);
	  addButton("=", command);
	  addButton("+", command);

	  add(panel, BorderLayout.CENTER);
   }

   /**
	* Adds a button to the center panel.
	* @param label the button label
	* @param listener the button listener
	*/

	/*
		自定义的addbutton方法

	*/
   private void addButton(String label, ActionListener listener)
   {
	  JButton button = new JButton(label);
	  button.addActionListener(listener);
	  panel.add(button);
   }

   /**
	* This action inserts the button action string to the end of the display text.
	*/

	/*
		输入监听者
		通过一个start变量判断程序是否属于刚开始
		如果刚开始，让显示器为空
		改变变量，
		然后再显示器中以string的形式，添加一位上去
	*/
   private class InsertAction implements ActionListener
   {
	  public void actionPerformed(ActionEvent event)
	  {
		 String input = event.getActionCommand();
		 if (start)
		 {
			display.setText("");
			start = false;
		 }
		 display.setText(display.getText() + input);
	  }
   }

   /**
	* This action executes the command that the button action string denotes.
	*/

	/*
		执行相关命令的监听器
		判断是否属于刚开始的程序，正常运算时，打包扔给自定义个calculate方法

	*/
   private class CommandAction implements ActionListener
   {
	  public void actionPerformed(ActionEvent event)
	  {
		 String command = event.getActionCommand();

		 if (start)
		 {
			if (command.equals("-"))
			{
			   display.setText(command);
			   start = false;
			}
			else lastCommand = command;
		 }
		 else
		 {
			calculate(Double.parseDouble(display.getText()));
			lastCommand = command;
			start = true;
		 }
	  }
   }

   /**
	* Carries out the pending calculation.
	* @param x the value to be accumulated with the prior result.
	*/
   public void calculate(double x)
   {
	  if (lastCommand.equals("+")) result += x;
	  else if (lastCommand.equals("-")) result -= x;
	  else if (lastCommand.equals("*")) result *= x;
	  else if (lastCommand.equals("/")) result /= x;
	  else if (lastCommand.equals("=")) result = x;
	  display.setText("" + result);
   }

   private JButton display;
   private JPanel panel;
   private double result;
   private String lastCommand;
   private boolean start;
}
