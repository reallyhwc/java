import java.awt.EventQueue;
import java.awt.event.*;
import java.io.*;
import java.util.Properties;

import javax.swing.*;

/**
 * A program to test properties. The program remembers the frame position, size, and title.
 * @version 1.00 2007-04-29
 * @author Cay Horstmann
 */
public class PropertiesTest
{
   public static void main(String[] args)
   {
      EventQueue.invokeLater(new Runnable()
         {
            public void run()
            {
               PropertiesFrame frame = new PropertiesFrame();
               frame.setVisible(true);
            }
         });
   }
}

/**
 * A frame that restores position and size from a properties file and updates the properties upon
 * exit.
 */
class PropertiesFrame extends JFrame
{
   public PropertiesFrame()
   {
      // get position, size, title from properties
      /*
      首先定义一个字符串定位到系统中的一个属性位置
      然后新建一个文件地址，如果没有就新建一个，然后在这个目录下打开一个
      文件，如果没有，也新建一个
      */
      String userDir = System.getProperty("user.home");
      File propertiesDir = new File(userDir, ".corejava");
      if (!propertiesDir.exists()) propertiesDir.mkdir();
      propertiesFile = new File(propertiesDir, "program.properties");
/*
      然后在这里新建一个对象，对象中存储一些参数，默认的话存入一下数据
*/
      Properties defaultSettings = new Properties();
      defaultSettings.put("left", "0");
      defaultSettings.put("top", "0");
      defaultSettings.put("width", "" + DEFAULT_WIDTH);
      defaultSettings.put("height", "" + DEFAULT_HEIGHT);
      defaultSettings.put("title", "");
      //然后把上面那个设置也打包
      settings = new Properties(defaultSettings);

      /*
      只要这个文件存在，新建一个文件流读取，读取到定义的设置对象中去
      */
      if (propertiesFile.exists()) try
      {
         FileInputStream in = new FileInputStream(propertiesFile);
         settings.load(in);
      }
      catch (IOException ex)
      {
         ex.printStackTrace();
      }
      //然后把页面设置成设置对象中存放的样子
      int left = Integer.parseInt(settings.getProperty("left"));
      int top = Integer.parseInt(settings.getProperty("top"));
      int width = Integer.parseInt(settings.getProperty("width"));
      int height = Integer.parseInt(settings.getProperty("height"));
      setBounds(left, top, width, height);

      // if no title given, ask user
      // 如果标题为空，新建一个弹出框，请求输入标题
      String title = settings.getProperty("title");
      if (title.equals("")) title = JOptionPane.showInputDialog("Please supply a frame title:");
      if (title == null) title = "";
      setTitle(title);
      // 然后，只要Windows监听器发生改变，把参数传入到设置对象总去，然后把这个设置对象写入到文件当中去
      addWindowListener(new WindowAdapter()
         {
            public void windowClosing(WindowEvent event)
            {
               settings.put("left", "" + getX());
               settings.put("top", "" + getY());
               settings.put("width", "" + getWidth());
               settings.put("height", "" + getHeight());
               settings.put("title", getTitle());
               try
               {
                  FileOutputStream out = new FileOutputStream(propertiesFile);
                  settings.store(out, "Program Properties");
               }
               catch (IOException ex)
               {
                  ex.printStackTrace();
               }
               System.exit(0);
            }
         });
   }

   private File propertiesFile;
   private Properties settings;

   public static final int DEFAULT_WIDTH = 300;
   public static final int DEFAULT_HEIGHT = 200;
}
