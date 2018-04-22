package p420BeatBox;

import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.sound.midi.*;
import java.util.*;
import java.awt.event.*;


public class BeatBox {
	
	JPanel mainPanel;
	ArrayList<JCheckBox> checkboxList;
	Sequencer sequencer;
	Sequence sequence;
	Track track;
	JFrame theFrame;

	String[] instrumentNames = {"Bass Drum", "Closed Hi-Hat", 
		"Open Hi-Hat", "Acoustic Snare", "Crash Cymbal", "Hand Clap", 
		"High Tom", "Hi Bongo", "Maracas", "Whistle", "Low Conga", 
		"Cowbell", "Vibraslap", "Low-mid Tom", "High Agogo", 
		"Open Hi Conga"};

	int[] instruments = {35,42,46,38,49,39,50,60,70,72,64,56,58,47,67,63};

	public static void main (String[] args) {
		new BeatBox().buildGUI();
	}

	public void buildGUI() {
		/*
		 * 面板添加相关参数
		 */
		theFrame = new JFrame("Cyber BeatBox");
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/*
		 * 设置布局参数
		 */
		BorderLayout layout = new BorderLayout();
		/*
		 * 设置背景面板
		 */
		JPanel background = new JPanel(layout);
		background.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

		/*
		 * 创建选择框框的组
		 */
		checkboxList = new ArrayList<JCheckBox>();
		/*
		 * 创建buttonbox，且设置排列方式为从上到下
		 */
		Box buttonBox = new Box(BoxLayout.Y_AXIS);

		/*
		 * 创建启动按键以及相关的监听
		 */
		JButton start = new JButton("start");
		start.addActionListener (new MyStartListener());
		buttonBox.add(start);

		/*
		 * 创建停止按键以及相关的监听
		 */
		JButton stop = new JButton("Stop");
		stop.addActionListener(new MyStopListener());
		buttonBox.add(stop);

		/*
		 * 创建某些按键以及相关的监听
		 */
		JButton upTemo = new JButton("Tempo Up");
		upTemo.addActionListener(new MyUpTempoListener());
		buttonBox.add(upTemo);

		/*
		 * 创建某些按键以及相关的监听
		 */
		JButton downTempo = new JButton("Tempo Down");
		downTempo.addActionListener(new MyDownTempoListener());
		buttonBox.add(downTempo);
		
		/*
		 * 创建某些按键以及相关的监听
		 */
		
		JButton serializelt = new JButton("serializelt");
		serializelt.addActionListener(new MySendListener());
		buttonBox.add(serializelt);

		/*
		 * 创建某些按键以及相关的监听
		 */
		JButton restore = new JButton("restore");
		restore.addActionListener(new MyReadInListener());
		buttonBox.add(restore);


		/*
		 * 名称box，最左边的一列label，按照之前的字符串组里的，依次从上到下添加label标签
		 */
		Box nameBox = new Box(BoxLayout.Y_AXIS);
		for (int i = 0; i < 16; i++) {
			nameBox.add(new Label(instrumentNames[i]));
		}

		/*
		 * 在面板上添加buttonbox和namebox，
		 * 在这两个盒子上添加了相应的label标签或者是button按键
		 * 
		 */
		background.add(BorderLayout.EAST, buttonBox);
		background.add(BorderLayout.WEST, nameBox);

		/*
		 * 在frame上添加面板
		 */
		theFrame.getContentPane().add(background);

		/*
		 * 设置布局方式
		 * ：
		 * 把组件按照设定好的几行几列给分开，在添加组件的时候
		 * 会按照添加的顺序依次从左到右，从上到下添加到这个组件上
		 * 
		 * 后面两行应该是设置添加进去的组件的上下左右间距
		 * 但就是有点搞不明白的是，改变了上下间距，
		 * label标签的间距也改变了
		 */
		GridLayout grid = new GridLayout(16, 16);
		grid.setVgap(1);
		grid.setHgap(1);
		mainPanel = new JPanel(grid);
		background.add(BorderLayout.CENTER, mainPanel);

		//创建checkbox组，设定成未勾选的为false并加到ArrayList和面板上
		for (int i = 0; i < 256; i++) {
			JCheckBox c = new JCheckBox();
			c.setSelected(false);
			checkboxList.add(c);
			mainPanel.add(c);
		}

		setUpMidi();

		theFrame.setBounds(50,50,300,300);
		theFrame.pack();
		theFrame.setVisible(true);
	}//colse method

	public void setUpMidi() {
		try	{
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequence = new Sequence(Sequence.PPQ, 4);
			track = sequence.createTrack();
			sequencer.setTempoInBPM(120);
		}catch (Exception e) {e.printStackTrace();}
	}

	public void buildTrackAndStart() {
		int[] trackList = null;
		sequence.deleteTrack(track);
		track = sequence.createTrack();

		for (int i = 0; i < 16; i++) {
			trackList = new int[16];

			int key = instruments[i];

			for (int j = 0; j < 16; j++) {
				JCheckBox jc = (JCheckBox) checkboxList.get(j + (16 * i));
				if (jc.isSelected()) {
					trackList[j] = key;
				}else {
					trackList[j] = 0;
				}
			}//close in for

			makeTracks(trackList);//创建此乐器的事件并加到track上
			track.add(makeEvent(176,1,127,0,16));
		}//close out for


		track.add(makeEvent(192,9,1,0,15));//确保第16拍有事件，否则beatbox不会重复播放
		try {

			sequencer.setSequence(sequence);
			sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
			sequencer.start();
			sequencer.setTempoInBPM(120);
		} catch (Exception e) {e.printStackTrace();}


	}//关闭buildTrackAndStart方法

	public class MyStartListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			buildTrackAndStart();
		}
	}

	public class MyStopListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			sequencer.stop();
		}
	}

	public class MyUpTempoListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			float tempoFactor = sequencer.getTempoFactor();
			sequencer.setTempoFactor((float)(tempoFactor * 1.03));
			//节奏因子，预设为1.0，每次调整百分之三
		}
	}

	public class MyDownTempoListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			float tempoFactor = sequencer.getTempoFactor();
			sequencer.setTempoFactor((float)(tempoFactor * 0.97));
		}
	}


	//往后的两个内部类是新添加上去的

	public class MySendListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			//用作存放复选框的状态
			boolean[] checkboxState = new boolean[256];

			for (int i = 0; i < 256; i++) {
				JCheckBox check = (JCheckBox) checkboxList.get(i);
				if (check.isSelected()) {
					checkboxState[i] = true;
				}
			}

			try {
				FileOutputStream fileStream = new FileOutputStream(new File("Checkbox.ser"));
				ObjectOutputStream os = new ObjectOutputStream(fileStream);
				os.writeObject(checkboxState);				
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
	}


	public class MyReadInListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			boolean[] checkboxState = null;
			try {
				FileInputStream fileIn = new FileInputStream(new File("Checkbox.ser"));
				ObjectInputStream is = new ObjectInputStream(fileIn);
				checkboxState = (boolean[]) is.readObject();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			for (int i = 0; i < 256; i++) {
				JCheckBox check = (JCheckBox) checkboxList.get(i);
				if (checkboxState[i]) {
					check.setSelected(true);
				}else {
					check.setSelected(false);
				}
			}

			sequencer.stop();
			buildTrackAndStart();
		}
	}


	public void makeTracks(int[] list) {
		for (int i = 0; i < 16; i++) {
			int key = list[i];

			if (key != 0) {
				track.add(makeEvent(144,9,key,100,i));
				track.add(makeEvent(128,9,key,100,i + 1));
			}
		}
	}

	public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
		MidiEvent event = null;
		try	{
			ShortMessage a = new ShortMessage();
			a.setMessage(comd, chan, one, two);
			event = new MidiEvent(a, tick);
		} catch (Exception e) {e.printStackTrace();}

		return event;

	}




}
