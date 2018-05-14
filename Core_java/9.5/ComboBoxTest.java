import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @version 1.33 2007-06-12
 * @author Cay Horstmann
 */
public class ComboBoxTest
{
   public static void main(String[] args)
   {
      EventQueue.invokeLater(new Runnable()
         {
            public void run()
            {

               ComboBoxFrame frame = new ComboBoxFrame();
               frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               frame.setVisible(true);
            }
         });
   }
}

/**
 * A frame with a sample text label and a combo box for selecting font faces.
 */
class ComboBoxFrame extends JFrame
{
   public ComboBoxFrame()
   {
      setTitle("ComboBoxTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      // add the sample text label

      label = new JLabel("The quick brown fox jumps over the lazy dog.");
      label.setFont(new Font("Serif", Font.PLAIN, DEFAULT_SIZE));
      add(label, BorderLayout.CENTER);

      // make a combo box and add face names
      /*
      新建一个下拉选择box
      然后往里面添加选项
      */
      faceCombo = new JComboBox();
      faceCombo.setEditable(true);
      faceCombo.addItem("Serif");
      faceCombo.addItem("SansSerif");
      faceCombo.addItem("Monospaced");
      faceCombo.addItem("Dialog");
      faceCombo.addItem("DialogInput");
      faceCombo.addItem("方正剪纸简体");
      faceCombo.addItem("方正少儿简体");
      faceCombo.addItem("方正行楷简体");
      faceCombo.addItem("方正魏碑繁体");
      faceCombo.addItem("方正黄草_GBK");
      faceCombo.addItem("方正黄草简体");
      faceCombo.addItem("方正黑体_GBK");
      faceCombo.addItem("方正黑体简体");
      faceCombo.addItem("方正黑体繁体");
      faceCombo.addItem("楷体");
      faceCombo.addItem("汉仪报宋简");


      // the combo box listener changes the label font to the selected face name
      /*
      当用户选择了某一项的时候，根据选项，把label里面的信息更改字体显示
      */
      faceCombo.addActionListener(new ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               label.setFont(new Font((String) faceCombo.getSelectedItem(), Font.PLAIN,
                     DEFAULT_SIZE));
            }
         });

      // add combo box to a panel at the frame's southern border

      JPanel comboPanel = new JPanel();
      comboPanel.add(faceCombo);
      add(comboPanel, BorderLayout.SOUTH);
   }

   public static final int DEFAULT_WIDTH = 300;
   public static final int DEFAULT_HEIGHT = 200;

   private JComboBox faceCombo;
   private JLabel label;
   private static final int DEFAULT_SIZE = 12;
}
