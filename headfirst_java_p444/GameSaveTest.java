import java.io.*;

public class GameSaveTest {
	public static void main(String[] args) {
		GameCharacter one = new GameCharacter(50,"Elf",new String {"bow", "sword", "dust"});
		GameCharacter two = new GameCharacter(200, "Troll", new String {"Bare hands", "big ax"});
		GameCharacter three = new GameCharacter(120. "Megician", new String {"spells", "invisibility"});

		//假设这里有改变人物状态值和属性的代码

		try {
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("Game.ser"));
			os.writeObject(one);
			os.writeObject(two);
			os.writeObject(three);
			os.close();
		} catch(IOException ex) {
			ex.printStrackTrance();
		}
		one = null;
		two = null;
		three = null;
		//前面一段代码的主要目的是把这几个对象存入文件中然后再把对象清空


		try {
			ObjectInputStream is = new ObjectInputStream(new FileInputStream("Game.ser"));
			GameCharacter oneRestore = (GameCharacter) is.readObject();
			GameCharacter twoRestore = (GameCharacter) is.readObject();
			GameCharacter threeRestore = (GameCharacter) is.readObject();

			System.out.println("One's type: " + oneRestore.getType());
			System.out.println("Two's type: " + twoRestore.getType());
			System.out.println("Three's type: " + threeRestore.getType());

		} catch (Exception ex) {
			ex.printStrackTrance();
		}

	}
}