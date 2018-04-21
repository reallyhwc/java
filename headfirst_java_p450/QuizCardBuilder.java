package p450;


import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class QuizCardBuilder {
	/*
	 * 创建两个窗口，一个输入问题，一个输入答案
	 */
	private JTextArea question;
	private JTextArea answer;
	/*
	 * 创建一个存储电子表结构的list，和一个frame
	 */
	private ArrayList<QuizCard> cardList;
	private JFrame frame;

	public static void main(String[] args) {
		QuizCardBuilder builder = new QuizCardBuilder();
		builder.go();
	}

	public void go() {
		//创建gui
		/*
		 * 为frame命名，新建一个面板，并设置字体格式
		 */
		frame = new JFrame("Quiz Card Builder");
		JPanel mainPanel = new JPanel();
		Font bigFont = new Font("sanserif", Font.BOLD, 24);
		
		/*
		 * 对问题窗口设置大小
		 * 设置句子换行和单词换行
		 * 设置窗口的字体格式
		 */
		question = new JTextArea(6, 20);
		question.setLineWrap(true);
		question.setWrapStyleWord(true);
		question.setFont(bigFont);
		
		/*
		 * 为问题窗口提供设置滚动条
		 * 以及一些相应的参数设置
		 */
		JScrollPane qScroller = new JScrollPane(question);
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		/*
		 * 对答案窗口哦设置大小
		 * 设置句子换行和单词换行
		 * 设置窗口的字体格式
		 */
		answer = new JTextArea(6, 20);
		answer.setLineWrap(true);
		answer.setWrapStyleWord(true);
		answer.setFont(bigFont);
		
		/*
		 * 为答案窗口提供设置滚动条
		 * 以及一些相应的参数设置
		 */
		JScrollPane aScroller = new JScrollPane(answer);
		aScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		aScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		/*
		 * 新建一个按键
		 */
		JButton nextButton = new JButton("Next Card");
		/*
		 * 把list组new出来
		 */
		cardList = new ArrayList<QuizCard>();
		/*
		 * 新建两个label标签
		 */
		JLabel qLabel = new JLabel("Question:");
		JLabel aLabel = new JLabel("Answer:");
		
		/*
		 * 在面板上依次添加标签，窗口，标签，窗口，按钮
		 */
		mainPanel.add(qLabel);
		mainPanel.add(qScroller);
		mainPanel.add(aLabel);
		mainPanel.add(aScroller);
		mainPanel.add(nextButton);
		/*
		 * 给按钮赋予监听者
		 */
		nextButton.addActionListener(new NextCardListener());
		
		/*
		 * 设置菜单
		 * 第一行是新建了一个菜单bar
		 * 第二行，新建了一个菜单File
		 * 三四行新建了两个菜单中的item
		 */
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem newMenuItem = new JMenuItem("New");
		JMenuItem saveMenuItem = new JMenuItem("Save");
		
		/*
		 * 对菜单item赋予相对应的监听者
		 * 即点击的时候调用监听者执行相应的命令
		 * 
		 */
		newMenuItem.addActionListener(new NewMenuListener());
		saveMenuItem.addActionListener(new SaveMenuListener());
		
		/*
		 * 在菜单中添加菜单item
		 * 在菜单bar中添加菜单
		 */
		fileMenu.add(newMenuItem);
		fileMenu.add(saveMenuItem);
		menuBar.add(fileMenu);

		/*
		 * 设置frame中的菜单bar为此菜单bar
		 * 在frame中添加面板
		 * 设置frame大小等参数
		 */
		frame.setJMenuBar(menuBar);
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(500, 600);
		frame.setVisible(true);


	}

	public class NextCardListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			/*
			 * 点击面板上的button时执行的操作
			 * button为next card
			 * 此时执行，新建一个quiz card对象，并把答案和问题窗口中
			 * 字符串赋值给这个新建的对象，把这个对象添加到cardlist中
			 * 并清空问题和答案窗口
			 */
			QuizCard card = new QuizCard(question.getText(), answer.getText());
			cardList.add(card);
			clearCard();
		}
	}

	public class SaveMenuListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			
			/*
			 * 点击菜单中的save时所执行的操作
			 *  此时执行，新建一个quiz card对象，并把答案和问题窗口中
			 * 字符串赋值给这个新建的对象，把这个对象添加到cardlist中
			 * 并清空问题和答案窗口
			 * 
			 * 然后再
			 * 
			 */
			QuizCard card = new QuizCard(question.getText(), answer.getText());
			cardList.add(card);

			/*
			调出存盘对话框，等待用户决定，这都是靠JFileChoose完成的
			会在当前页面上跳出一个窗口来，提示选择文件夹并输入文件名
			选择完了之后，根据此，来序列化存储对象
			*/
			JFileChooser fileSave = new JFileChooser();
			fileSave.showSaveDialog(frame);
			saveFile(fileSave.getSelectedFile());

		}
	}

	public class NewMenuListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			/*
			 * 点击菜单中的new时所执行的操作
			 * 清空list
			 * 清空问题和答案窗口中的文字
			 * 
			 */
			cardList.clear();
			clearCard();
		}
	}

	private void clearCard() {
		/*
		 * 清空问题和答案窗口中的文字
		 * 并把输入焦点放在调用这个方法的控件上。
		 */
		question.setText("");
		answer.setText("");
		question.requestFocus();
	}

	private void saveFile(File file) {
		/*
		 * 新建一个写入的对象，写入到文件中去
		 * 具体是哪个文件则是由前面所选中的
		 * 然后依次读取cardlist中的对象，依次写入
		 * 直到cardlist中不存在card对象为止
		 * 然后关闭写对象
		 * 
		 */
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));

			for(QuizCard card:cardList) {
				writer.write(card.getQuestion() + "/");
				writer.write(card.getAnswer() + "\n");
			}
			writer.close();
		} catch(IOException ex) {
			/*
			 * 写入出问题则输出异常报告
			 */
			System.out.println("couldn't write the cardlist out");
			ex.printStackTrace();
		}
	}
}