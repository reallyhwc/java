import java.awt.*;

public class ListFont {
	public static void main(String[] args) {
		String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		 for (String fontName : fontNames) {
		 	System.out.println(fontName);
		 }
	}
}