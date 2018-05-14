import java.awt.EventQueue;
import java.awt.event.*;
import javax.swing.*;

/**
 * @version 1.23 2007-05-30
 * @author Cay Horstmann
 */
public class MenuTest
{
   public static void main(String[] args)
   {
      EventQueue.invokeLater(new Runnable()
         {
            public void run()
            {
               MenuFrame frame = new MenuFrame();
               frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               frame.setVisible(true);
            }
         });
   }
}

/**
 * A frame with a sample menu bar.
 */

/*
首先在这里声明，并没有执行任何的操作，只是实现了效果而已

*/
class MenuFrame extends JFrame
{
   public MenuFrame()
   {
      setTitle("MenuTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      JMenu fileMenu = new JMenu("File");
      //利用自定义方法，往file菜单中添加一个new项，不附加其他功能
      //仅当其在被点击的时候，在控制台输出内容
      fileMenu.add(new TestAction("New"));

      // demonstrate accelerators
      /*
      也是利用自定义方法，添加一个open项，但是为其添加了一个键盘输入
      也就是说，如果检测到键盘上面有相应的输入
      则相当于点击了该项


      */
      JMenuItem openItem = fileMenu.add(new TestAction("Open"));
      openItem.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
      //添加分割线
      fileMenu.addSeparator();
      //跟上面没有差别，仅仅名字不同，然后添加了一个分割线

      saveAction = new TestAction("Save");
      JMenuItem saveItem = fileMenu.add(saveAction);
      saveItem.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));

      saveAsAction = new TestAction("Save As");
      fileMenu.add(saveAsAction);
      fileMenu.addSeparator();

      fileMenu.add(new AbstractAction("Exit")
         {
            public void actionPerformed(ActionEvent event)
            {
               System.exit(0);
            }
         });

      // demonstrate check box and radio button menus
      //新建了一个复选框，监听器作用，根据选择与否，设置save能否使用
      readonlyItem = new JCheckBoxMenuItem("Read-only");
      readonlyItem.addActionListener(new ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               boolean saveOk = !readonlyItem.isSelected();
               saveAction.setEnabled(saveOk);
               saveAsAction.setEnabled(saveOk);
            }
         });
      /*
      然后新建了一个单选组
      添加进去了两个单选项，并默认设置了第一个

      */
      ButtonGroup group = new ButtonGroup();

      JRadioButtonMenuItem insertItem = new JRadioButtonMenuItem("Insert");
      insertItem.setSelected(true);
      JRadioButtonMenuItem overtypeItem = new JRadioButtonMenuItem("Overtype");

      group.add(insertItem);
      group.add(overtypeItem);

      // demonstrate icons
      /*
      新建了三个选项，并对他们绑定了图标
      然后添加到了另外的一个菜单中
      */
      Action cutAction = new TestAction("Cut");
      cutAction.putValue(Action.SMALL_ICON, new ImageIcon("cut.gif"));
      Action copyAction = new TestAction("Copy");
      copyAction.putValue(Action.SMALL_ICON, new ImageIcon("copy.gif"));
      Action pasteAction = new TestAction("Paste");
      pasteAction.putValue(Action.SMALL_ICON, new ImageIcon("paste.gif"));

      JMenu editMenu = new JMenu("Edit");
      editMenu.add(cutAction);
      editMenu.add(copyAction);
      editMenu.add(pasteAction);

      // demonstrate nested menus
      /*
      在这里又新建了一个菜单，然后添加了一个复选框
      分割线，和一个单选组
      然后再另外一个菜单中添加了这个菜单
      */
      JMenu optionMenu = new JMenu("Options");

      optionMenu.add(readonlyItem);
      optionMenu.addSeparator();
      optionMenu.add(insertItem);
      optionMenu.add(overtypeItem);

      editMenu.addSeparator();
      editMenu.add(optionMenu);

      // demonstrate mnemonics
/*
      新建了一个菜单，然后添加菜单中的子项的时候，并没有自定义的构造方法
      也就不能在控制面板显示信息

      新建的时候，设置了在某个字母下面加粗
*/
      JMenu helpMenu = new JMenu("Help");
      helpMenu.setMnemonic('H');

      JMenuItem indexItem = new JMenuItem("Index");
      indexItem.setMnemonic('I');
      helpMenu.add(indexItem);

      // you can also add the mnemonic key to an action
      Action aboutAction = new TestAction("About");
      aboutAction.putValue(Action.MNEMONIC_KEY, new Integer('A'));
      helpMenu.add(aboutAction);

      // add all top-level menus to menu bar

      JMenuBar menuBar = new JMenuBar();
      setJMenuBar(menuBar);
      /*
      一个frame中只能够有一个menubar
      在这个menubar中添加进去之前设置好的三个菜单
      */
      menuBar.add(fileMenu);
      menuBar.add(editMenu);
      menuBar.add(helpMenu);

      // demonstrate pop-ups
      /*
      新建一个弹出式菜单
      然后添加进之前设定好的三个菜单子项
      */
      popup = new JPopupMenu();
      popup.add(cutAction);
      popup.add(copyAction);
      popup.add(pasteAction);
      /*
      然后新建了一个面板，为面板设置了弹出式菜单
      ？？也就是说可以在不同的面板中设置不同的弹出式菜单咯
      */
      JPanel panel = new JPanel();
      panel.setComponentPopupMenu(popup);
      add(panel);

      // the following line is a workaround for bug 4966109
      panel.addMouseListener(new MouseAdapter()
         {
         });
   }

   public static final int DEFAULT_WIDTH = 300;
   public static final int DEFAULT_HEIGHT = 200;

   private Action saveAction;
   private Action saveAsAction;
   private JCheckBoxMenuItem readonlyItem;
   private JPopupMenu popup;
}

/**
 * A sample action that prints the action name to System.out
 */
class TestAction extends AbstractAction
{
   public TestAction(String name)
   {
      super(name);
   }

   public void actionPerformed(ActionEvent event)
   {
      System.out.println(getValue(Action.NAME) + " selected.");
   }
}
