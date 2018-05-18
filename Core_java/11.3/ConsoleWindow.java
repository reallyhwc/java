import javax.swing.*;
import java.io.*;

/*
   A window that displays the bytes sent to System.out and System.err
   @version 1.01 2004-05-10
   @author Cay Horstmann
*/
public class ConsoleWindow
{
   public static void init()
   {
      /*
      具体实现功能是在一个新的窗口中显示调试信息
      设定好frame的参数之后，把信息输到文本框中去
      */
      JFrame frame = new JFrame();
      frame.setTitle("ConsoleWindow");
      final JTextArea output = new JTextArea();
      output.setEditable(false);
      frame.add(new JScrollPane(output));
      frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
      frame.setLocation(DEFAULT_LEFT, DEFAULT_TOP);
      frame.setFocusableWindowState(false);
      frame.setVisible(true);

      // define a PrintStream that sends its bytes to the output text area
      PrintStream consoleStream = new PrintStream(new
         OutputStream()
         {
            public void write(int b) {} // never called
            public void write(byte[] b, int off, int len)
            {
               output.append(new String(b, off, len));
            }
         });

      // set both System.out and System.err to that stream
      System.setOut(consoleStream);
      System.setErr(consoleStream);
   }

   public static final int DEFAULT_WIDTH = 300;
   public static final int DEFAULT_HEIGHT = 200;
   public static final int DEFAULT_LEFT = 200;
   public static final int DEFAULT_TOP = 200;
}
