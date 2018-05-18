import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.logging.*;
import javax.swing.*;

/**
 * A modification of the image viewer program that logs various events.
 * @version 1.02 2007-05-31
 * @author Cay Horstmann
 */
/*
// 等待注释...
*/
public class LoggingImageViewer
{
	 public static void main(String[] args)
	 { 
	 	// 确保将所有的消息记录到应用程序特定的文件中，整个if都是
		if (System.getProperty("java.util.logging.config.class") == null&& System.getProperty("java.util.logging.config.file") == null)
		{
			 try
			 {
					Logger.getLogger("com.horstmann.corejava").setLevel(Level.ALL);
					final int LOG_ROTATION_COUNT = 10;
					Handler handler = new FileHandler("%h/LoggingImageViewer.log", 0, LOG_ROTATION_COUNT);
					Logger.getLogger("com.horstmann.corejava").addHandler(handler);
			 }
			 catch (IOException e)
			 {
					Logger.getLogger("com.horstmann.corejava").log(Level.SEVERE,
								"Can't create log file handler", e);
			 }
		}
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{/*
				新建了一个自定义的日志记录器的对象
				然后通过该对象继承的方法，获得和设置了这个日志记录器的级别

			*/
				 Handler windowHandler = new WindowHandler();
				 windowHandler.setLevel(Level.ALL);
				 // 获得给定名字的日志记录器
				 Logger.getLogger("com.horstmann.corejava").addHandler(windowHandler);
				 // 新建了一个自定义的frame对象，可以执行打开GIF文件的操作
				 JFrame frame = new ImageViewerFrame();
				 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				 Logger.getLogger("com.horstmann.corejava").fine("Showing frame");
				 frame.setVisible(true);
			}
		 });
	 }
}

/**
 * The frame that shows the image.
 */
class ImageViewerFrame extends JFrame
{
	 public ImageViewerFrame()
	 {
	 	// 记录一个进入方法的日志记录，其中应该包括给定参数和返回值
			logger.entering("ImageViewerFrame", "<init>");
			setTitle("LoggingImageViewer");
			setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

			// set up menu bar
			JMenuBar menuBar = new JMenuBar();
			setJMenuBar(menuBar);

			JMenu menu = new JMenu("File");
			menuBar.add(menu);

			JMenuItem openItem = new JMenuItem("Open");
			menu.add(openItem);
			openItem.addActionListener(new FileOpenListener());

			JMenuItem exitItem = new JMenuItem("Exit");
			menu.add(exitItem);
			exitItem.addActionListener(new ActionListener()
				 {
						public void actionPerformed(ActionEvent event)
						{
							// 记录一个由方法名和给定消息级别的日志记录
							 logger.fine("Exiting.");
							 System.exit(0);
						}
				 });

			// use a label to display the images
			label = new JLabel();
			add(label);
			logger.exiting("ImageViewerFrame", "<init>");
	 }

	 private class FileOpenListener implements ActionListener
	 {
			public void actionPerformed(ActionEvent event)
			{
				// 记录描述进入方法的日志记录，其中应该包括给定参数和返回值
				 logger.entering("ImageViewerFrame.FileOpenListener", "actionPerformed", event);

				 // set up file chooser
				 // 定义一个文件选择对象，并定位到当前文件夹下
				 JFileChooser chooser = new JFileChooser();
				 chooser.setCurrentDirectory(new File("."));

				 // accept all files ending with .gif
				 // 文件选择器，只能够挑选出来GIF文件
				 chooser.setFileFilter(new javax.swing.filechooser.FileFilter()
						{
							 public boolean accept(File f)
							 {
									return f.getName().toLowerCase().endsWith(".gif") || f.isDirectory();
							 }

							 public String getDescription()
							 {
									return "GIF Images";
							 }
						});

				 // show file chooser dialog
				 int r = chooser.showOpenDialog(ImageViewerFrame.this);

				 // if image file accepted, set it as icon of the label
				 // 上面一个注释写的很清楚了，看英文....
				 if (r == JFileChooser.APPROVE_OPTION)
				 {
						String name = chooser.getSelectedFile().getPath();
						logger.log(Level.FINE, "Reading file {0}", name);
						label.setIcon(new ImageIcon(name));
				 }
				 else logger.fine("File open dialog canceled.");
				 logger.exiting("ImageViewerFrame.FileOpenListener", "actionPerformed");
			}
	 }

	 private JLabel label;
	 private static Logger logger = Logger.getLogger("com.horstmann.corejava");
	 private static final int DEFAULT_WIDTH = 300;
	 private static final int DEFAULT_HEIGHT = 400;     
}

/**
 * A handler for displaying log records in a window.
 */
/*
这个类继承流处理器
frame200*200，作用是显示输出的日志
*/
class WindowHandler extends StreamHandler
{
	 public WindowHandler()
	 {
			frame = new JFrame();
			final JTextArea output = new JTextArea();
			output.setEditable(false);
			frame.setSize(200, 200);
			frame.add(new JScrollPane(output));
			frame.setFocusableWindowState(false);
			frame.setVisible(true);
			setOutputStream(new OutputStream()
				 {
						public void write(int b)
						{
						} // not called

						public void write(byte[] b, int off, int len)
						{
							 output.append(new String(b, off, len));
						}
				 });
	 }

	 public void publish(LogRecord record)
	 {
			if (!frame.isVisible()) return;
			super.publish(record);
			flush();
	 }

	 private JFrame frame;
}
