import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.awt.geom.*;
import javax.swing.*;

/**
 * @version 1.32 2007-06-12
 * @author Cay Horstmann
 */
public class MouseTest
{
   public static void main(String[] args)
   {
      EventQueue.invokeLater(new Runnable()
         {
            public void run()
            {
               MouseFrame frame = new MouseFrame();
               frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               frame.setVisible(true);
            }
         });
   }
}

/**
 * A frame containing a panel for testing mouse operations
 */
class MouseFrame extends JFrame
{
   public MouseFrame()
   {
      setTitle("MouseTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      // add component to frame
      /*
      设置了frame的大小和标题之后
      新建了一个组件并添加了进去

      */
      MouseComponent component = new MouseComponent();
      add(component);
   }

   public static final int DEFAULT_WIDTH = 300;
   public static final int DEFAULT_HEIGHT = 200;
}

/**
 * A component with mouse operations for adding and removing squares.
 */
class MouseComponent extends JComponent
{
   public MouseComponent()
   {
      /*
      在构造函数中，为矩形List new一个矩形List
      并把矩形对象赋值为空
      */
      squares = new ArrayList<Rectangle2D>();
      current = null;

      addMouseListener(new MouseHandler());
      addMouseMotionListener(new MouseMotionHandler());
   }

   public void paintComponent(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;

      // draw all squares
      for (Rectangle2D r : squares)
         g2.draw(r);
   }

   /**
    * Finds the first square containing a point.
    * @param p a point
    * @return the first square that contains p
    */
   public Rectangle2D find(Point2D p)
   {
      for (Rectangle2D r : squares)
      {
         if (r.contains(p)) return r;
      }
      return null;
   }

   /**
    * Adds a square to the collection.
    * @param p the center of the square
    */
   public void add(Point2D p)
   {
      double x = p.getX();
      double y = p.getY();

      current = new Rectangle2D.Double(x - SIDELENGTH / 2, y - SIDELENGTH / 2, SIDELENGTH,
            SIDELENGTH);
      squares.add(current);
      repaint();
   }

   /**
    * Removes a square from the collection.
    * @param s the square to remove
    */
   public void remove(Rectangle2D s)
   {
      if (s == null) return;
      if (s == current) current = null;
      squares.remove(s);
      repaint();
   }
   // 设定了矩形的大小，以及新建了一个矩形数List和矩形变量
   private static final int SIDELENGTH = 10;
   private ArrayList<Rectangle2D> squares;
   private Rectangle2D current;

   // the square containing the mouse cursor

   private class MouseHandler extends MouseAdapter
   {
      public void mousePressed(MouseEvent event)
      {
         // add a new square if the cursor isn't inside a square
         /*
         
         该方法在鼠标被按下的时候调用



         使用find函数查找list中有没有当前点击的点
         如果没有，则调用add方法，往List中添加该点，并绘制出这个点
         */
         current = find(event.getPoint());
         if (current == null) add(event.getPoint());
      }

      public void mouseClicked(MouseEvent event)
      {
         // remove the current square if double clicked
         /*
         该方法在鼠标被点击完了之后调用


         如果list中有当前的点且点击次数超过两次，调用remove方法，
         移出这个点

         ？？？
         疑惑
         仅有点击速度比较快的时候才可以
         垫的比较慢，即两次点击的时间间隔比较大的时候，并不会移出

         猜想应该是点击计数器中存在一个计时器
         */
         current = find(event.getPoint());
         if (current != null && event.getClickCount() >= 2) remove(current);
      }
   }

   private class MouseMotionHandler implements MouseMotionListener
   {
      public void mouseMoved(MouseEvent event)
      {
         // set the mouse cursor to cross hairs if it is inside
         // a rectangle
         /*
         方法监听鼠标移动


         当list中没有这个点的时候，显示鼠标符号为小箭头
         有的时候，显示为小十字
         在再次的测试中，可以发现，并不是检测是否存在这个点
         而是在检测，这个矩形list中，当前的点是否包含于里面的任意矩形
         */
         if (find(event.getPoint()) == null) setCursor(Cursor.getDefaultCursor());
         else setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
      }

      public void mouseDragged(MouseEvent event)
      {
         /*
         方法监听鼠标拖动
         只要当前矩形不为空，利用拖动的时候的当前左边，对于其属性值
         进行实时更改并重画
         */
         if (current != null)
         {
            int x = event.getX();
            int y = event.getY();

            // drag the current rectangle to center it at (x, y)
            current.setFrame(x - SIDELENGTH / 2, y - SIDELENGTH / 2, SIDELENGTH, SIDELENGTH);
            repaint();
         }
      }
   }
}
