import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

/**
 * @version 1.4 2007-04-30
 * @author Cay Horstmann
 */
public class ResourceTest
{
   public static void main(String[] args)
   {
      EventQueue.invokeLater(new Runnable()
         {
            public void run()
            {
               ResourceTestFrame frame = new ResourceTestFrame();
               frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               frame.setVisible(true);
            }
         });
   }
}
/*
有点意思
首先在一个.mf结尾的文件中声明main方法在哪儿

例如：
Main-Class: ResourceTest

然后，编译了之后
jar cvfm ResourceTest.jar ResourceTest.mf *.class *.gif *.txt

即可以打包出来一个可以运行的jar文件

*/
/**
 * A frame that loads image and text resources.
 */
class ResourceTestFrame extends JFrame
{
   public ResourceTestFrame()
   {
      setTitle("ResourceTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
      URL aboutURL = getClass().getResource("about.gif");
      Image img = Toolkit.getDefaultToolkit().getImage(aboutURL);
      setIconImage(img);

      JTextArea textArea = new JTextArea();
      InputStream stream = getClass().getResourceAsStream("about.txt");
      Scanner in = new Scanner(stream);
      while (in.hasNext())
         textArea.append(in.nextLine() + "\n");
      add(textArea);
   }

   public static final int DEFAULT_WIDTH = 300;
   public static final int DEFAULT_HEIGHT = 300;
}
