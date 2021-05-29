package view.component;

import java.awt.Color;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.text.MaskFormatter;

public class EditRessourceUtils {
	private static LineBorder redBorder = new LineBorder(Color.RED);
	private static Border defaultBorder = UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border");

	private static InputVerifier emptyInputVerifier;
	private static InputVerifier upperEmptymptyInputVerifier;

	public static MaskFormatter createFormatter(String s) {
		MaskFormatter formatter = null;
		try {
			formatter = new MaskFormatter(s);
		} catch (java.text.ParseException exc) {
			System.err.println("formatter is bad: " + exc.getMessage());
			System.exit(-1);
		}
		return formatter;
	}

	public static InputVerifier getEmptyInputVerifier() {
		if (emptyInputVerifier == null)
			emptyInputVerifier = new InputVerifier() {

				@Override
				public boolean verify(JComponent input) {
					boolean result = !((JTextField) input).getText().strip().equals("");
					input.setBorder(result ? defaultBorder : redBorder);
					return result;
				}
			};

		return emptyInputVerifier;
	}

	public static InputVerifier getUpperEmptyInputVerifier() {
		if (upperEmptymptyInputVerifier == null) {
			upperEmptymptyInputVerifier = new InputVerifier() {

				@Override
				public boolean verify(JComponent input) {
					JTextField inputText = (JTextField) input;
					boolean result = !(inputText.getText().strip().equals(""));
					inputText.setText(inputText.getText().toUpperCase());
					input.setBorder(result ? defaultBorder : redBorder);
					return result;
				}
			};
		}

		return upperEmptymptyInputVerifier;
	}
}
