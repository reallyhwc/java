package p450;


import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class QuizCardPlayer {
	/*
	 * 新建了一个显示的面板
	 * 一个quizcard链表
	 * 一个quizcard对象
	 * 一个计数器
	 * 一个frame
	 * 一个按钮
	 * 一个判断当前是输入问题还是答案
	 */
	private JTextArea display;
	//private JTextArea answer;
	private ArrayList<QuizCard> cardList;
	private QuizCard currentCard;
	private int currentCardIndex;
	private JFrame frame;
	private JButton nextButton;
	private boolean isShowAnswer;

	public static void main(String[] args) {
		QuizCardPlayer reader = new QuizCardPlayer();
		reader.go();
	}

	public void go() {
		//创建gui
		/*
		 * 设置frame的名字
		 * 新建一个面板
		 * 设置一些字体有关的信息
		 * 
		 */
		frame = new JFrame("Quiz Card Player");
		JPanel mainPanel = new JPanel();
		Font bigFont = new Font("sanserif", Font.BOLD, 24);
		
		/*
		 * 设置显示的窗口的大小
		 * 字体信息
		 * 可以行换行不能单词换行
		 */
		display = new JTextArea(10, 20);
		display.setFont(bigFont);
		display.setLineWrap(true);
		display.setEditable(false);

		/*
		 * 设置显示的窗口的额滚动条的信息
		 */
		JScrollPane qScroller = new JScrollPane(display);
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		/*
		 * 设置按钮
		 * 在面板上添加带有滚动条的窗口和按钮
		 * 为按钮设置相关的监听者
		 */
		nextButton = new JButton("Show Question");
		mainPanel.add(qScroller);
		mainPanel.add(nextButton);
		nextButton.addActionListener(new NextCardListener());

		/*
		 * 设置一些菜单相关的东西
		 */
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem loadMenuItem = new JMenuItem("Load card set");
		
		/*
		 * 为菜单中的选项设置点击动作的监听者
		 * 并在菜单中添加这个选项
		 * 在菜单bar中添加菜单
		 */
		loadMenuItem.addActionListener(new OpenMenuListener());
		fileMenu.add(loadMenuItem);
		menuBar.add(fileMenu);

		/*
		 * 为frame设置菜单
		 * 在frame上添加面板
		 * 设置frame大小和一些相关的参数
		 */
		frame.setJMenuBar(menuBar);
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(640, 500);
		frame.setVisible(true);
	}

	public class NextCardListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			/*
			 * 执行点击button的操作
			 */
			if (isShowAnswer) {
				// show answer
				
				/*
				 * 如果根据参数判断此时应该输出答案
				 * 在窗口上显示答案
				 * 更改button的名字
				 * 并更改参数，提示然后应该输入问题
				 */
				display.setText(currentCard.getAnswer());
				nextButton.setText("Next card");
				isShowAnswer = false;
			} else {
				/*
				 * 如果根据参数判断此时应该输出问题
				 * 根据计数器判断是否已经达到了链表的最后一个
				 * 如果还未达到
				 * 执行函数shownextcard
				 * 否则，在窗口上提示已经是最后一个card
				 * 并把button设置为不可点击的状态
				 */
				if (currentCardIndex < cardList.size()) {
					showNextCard();
				} else {
					// no more card
					/*
					 * 把button设置为不可以点击的状态
					 */
					display.setText("That was last card");
					nextButton.setEnabled(false);
				}
			}
		}
	}

	public class OpenMenuListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			/*
			 * 执行点击菜单栏中的Load card set的操作
			 * 会执行JFileChooser中的，提示选择一个文件
			 */
			JFileChooser fileOpen = new JFileChooser();
			fileOpen.showOpenDialog(frame);
			/*
			 * 然后把文件名作为参数，调用函数load file
			 */
			loadFile(fileOpen.getSelectedFile());
		}
	}

	private void loadFile(File file) {
		cardList = new ArrayList<QuizCard>();
		//new 一个出来，赋值
		
		/*
		 * 新建一个BufferedReader 读取传入进来的文件
		 */
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			/*
			 * 只要用reader对象读取新的一行成功
			 * 就把这一行新读取到的字符串作为参数传递给
			 * 函数makecard
			 * 
			 * 当读完文件之后，关闭reader对象
			 * 
			 */
			while ((line = reader.readLine()) != null) {
				makeCard(line);
			}
			reader.close();
		}catch (Exception ex) {
			System.out.println("couldn't read the card file");
			ex.printStackTrace();
		}
	}

	private void makeCard(String lineToParse) {
		/*
		 * 第一行的代码所执行的主要功能是
		 * 新建一个字符串组
		 * 然后调用函数，以“/”为分割
		 * 切割传进来的字符串，并把切割开来的多个字符串存在字符串组中
		 */
		String[] result = lineToParse.split("/");
		/*
		 * 然后根据字符串组中的数据信息
		 * new 一个card对象，并添加到链表中去
		 */
		QuizCard card = new QuizCard(result[0], result[1]);
		cardList.add(card);
		System.out.println("make a card");
	}

	private void showNextCard() {
		/*
		 * 该函数被调用的时候
		 * 把整个函数中用来临时存放card对象的对象
		 * 设置为下一个没有读取过的对象（利用计数器）
		 * 然后读取计数器+1
		 * 并在显示窗口上，输出问题
		 * 更改button显示的值
		 * 并把判断是否输出答案的参数设置为真
		 */
		currentCard = cardList.get(currentCardIndex);
		currentCardIndex++;
		display.setText(currentCard.getQuestion());
		nextButton.setText("Show Answer");
		isShowAnswer = true;
	}

}