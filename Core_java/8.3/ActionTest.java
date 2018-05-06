import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @version 1.33 2007-06-12
 * @author Cay Horstmann
 */
public class ActionTest
  {
   public static void main(String[] args)
   {
      EventQueue.invokeLater(new Runnable()
         {
            public void run()
            {
               ActionFrame frame = new ActionFrame();
               frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               frame.setVisible(true);
            }
         });
   }
}


/**
 * A frame with a panel that demonstrates color change actions.
 */
class ActionFrame extends JFrame
{
   public ActionFrame()
   {
      setTitle("ActionTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
      /*
      设置大小和标题
      然后新建面板
      */
      buttonPanel = new JPanel();

      // define actions
      /*
      定义三个内部类ColorAction对象
      内部类实现了Action接口，Action接口则实现了ActionListener接口
      */
      Action yellowAction = new ColorAction("Yellow", new ImageIcon("yellow-ball.gif"),Color.YELLOW);
      Action blueAction = new ColorAction("Blue", new ImageIcon("blue-ball.gif"), Color.BLUE);
      Action redAction = new ColorAction("Red", new ImageIcon("red-ball.gif"), Color.RED);

      // add buttons for these actions
      // 把动作对象绑定到按键上
      // 也就是说，在此时，可以实现点击button然后执行actionPerformed方法中的行为
      buttonPanel.add(new JButton(yellowAction));
      buttonPanel.add(new JButton(blueAction));
      buttonPanel.add(new JButton(redAction));

      // add panel to frame
      add(buttonPanel);

      // associate the Y, B, and R keys with names
      /*
      实现在键盘上按键，然后执行执行actionPerformed方法中的行为
      首先使用getInputMap方法从组件中得到输入映射
      但是InputMap并不能直接将KeyStore对象直接银蛇到Action对象
      而是先映射到，然后由ActionMap类实现将对象映射到动作上的第二个映射
      */
      InputMap imap = buttonPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
      imap.put(KeyStroke.getKeyStroke("ctrl Y"), "panel.yellow");
      imap.put(KeyStroke.getKeyStroke("ctrl B"), "panel.blue");
      imap.put(KeyStroke.getKeyStroke("ctrl R"), "panel.red");

      // associate the names with actions
      ActionMap amap = buttonPanel.getActionMap();
      amap.put("panel.yellow", yellowAction);
      amap.put("panel.blue", blueAction);
      amap.put("panel.red", redAction);
   }
   

   /*
   如果为了实现Action接口，则需要实现接口中所有的七个方法
   在这里则简便的使用直接扩展AbstractAction类，该类实现了Action接口中除了actionPerformer方法之外的所有方法

   */
   public class ColorAction extends AbstractAction
   {
      /**
       * Constructs a color action.
       * @param name the name to show on the button
       * @param icon the icon to display on the button
       * @param c the background color
       */
      public ColorAction(String name, Icon icon, Color c)
      {
         /*
         将动作的名字和图标存储到同一个动作对象中
         第三行则是，存储把鼠标移到该动作对象上的时候，需要显示出来的字符
         第四行则是存储颜色
         */
         putValue(Action.NAME, name);
         putValue(Action.SMALL_ICON, icon);
         putValue(Action.SHORT_DESCRIPTION, "Set panel color to " + name.toLowerCase());
         putValue("color", c);
      }

      public void actionPerformed(ActionEvent event)
      {
         Color c = (Color) getValue("color");
         buttonPanel.setBackground(c);
      }
   }

   private JPanel buttonPanel;

   public static final int DEFAULT_WIDTH = 300;
   public static final int DEFAULT_HEIGHT = 200;
}