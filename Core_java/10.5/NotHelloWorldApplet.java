/*
 * The following HTML tags are required to display this applet in a browser: <applet
 * code="NotHelloWorldApplet.class" width="300" height="100"> </applet>
 */

import java.awt.EventQueue;

import javax.swing.*;
/*
直接运行该程序，会报错找不到main方法，
利用applet调用该程序，会弹出一个小程序的界面

*/
/**
 * @version 1.22 2007-06-12
 * @author Cay Horstmann
 */
public class NotHelloWorldApplet extends JApplet
{
   public void init()
   {
      EventQueue.invokeLater(new Runnable()
         {
            public void run()
            {
               JLabel label = new JLabel("Not a Hello, World applet", SwingConstants.CENTER);
               add(label);
            }
         });
   }
}
