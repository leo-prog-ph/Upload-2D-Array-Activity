package main;

import javax.swing.*;
import java.awt.*;

public class it1 extends JFrame {

	static JTextField input, results;
	static JComboBox<String> convertBox;

	it1(){

		JLabel in = new JLabel("Enter a number");
		add(in).setBounds(100,30,120,20);

		input = new JTextField();
		add(input).setBounds(100,60,120,20);

		JLabel convertLabel = new JLabel("Conversion");
		add(convertLabel).setBounds(100,100,120,20);

		String [] options = {
		        "Binary to Decimal",
		        "Decimal to Binary",
		        "Decimal to Hexadecimal",
		        "Hexadecimal to Decimal",
		        "Octal to Decimal"
		};

		convertBox = new JComboBox<>(options);
		add(convertBox).setBounds(100,120,180,20);

		JButton convert = new JButton("Convert");
		add(convert).setBounds(100,160,100,20);

		convert.addActionListener(e -> {

			try {

				String inputValue = input.getText();
				String selected =
						(String) convertBox.getSelectedItem();
				String answer = "";
				switch (selected){

					case "Binary to Decimal":
						answer = String.valueOf(
								binaryToDecimal(inputValue));
						break;

					case "Decimal to Binary":
						answer = decimalToBinary(
								Integer.parseInt(inputValue));
						break;

					case "Decimal to Hexadecimal":
						answer = decimalToHex(
								Integer.parseInt(inputValue));
						break;

					case "Hexadecimal to Decimal":
						answer = String.valueOf(
								hexToDecimal(inputValue));
						break;

					case "Octal to Decimal":
					    answer = String.valueOf(
					            octalToDecimal(inputValue));
					    break;
				}

				results.setText(answer);

			} catch (Exception ex){

				JOptionPane.showMessageDialog(
						null,
						"Invalid Input",
						"Error",
						JOptionPane.ERROR_MESSAGE
				);
			}
		});

		JLabel result = new JLabel("Results");
		add(result).setBounds(100, 200, 100,20);
		results = new JTextField();
		add(results).setBounds(100, 220, 120, 20);
		results.setEditable(false);
		result.setBackground(Color.CYAN);

		setTitle("Number Converter System");
		setLayout(new BorderLayout(10, 10));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setSize(450,350);

	}

	public static int binaryToDecimal(String binary) {
		int decimal = 0;
		int power = 0;
		for (int i = binary.length() - 1; i >= 0; i--) {
			int bit = binary.charAt(i) - '0';
			decimal += bit * Math.pow(2, power);
			power++;
		}

		return decimal;
	}

	public static int octalToDecimal(String octal) {
		int decimal = 0;
		int power = 0;
		for (int i = octal.length() - 1; i >= 0; i--) {
			int digit = octal.charAt(i) - '0';
			decimal += digit * Math.pow(8, power);
			power++;
		}
		return decimal;
	}

	public static int hexToDecimal(String hex) {
		hex = hex.toUpperCase();
		int decimal = 0;
		int power = 0;
		for (int i = hex.length() - 1; i >= 0; i--) {
			char ch = hex.charAt(i);
			int value;
			if (ch >= '0' && ch <= '9') {
				value = ch - '0';
			} else {
				value = ch - 'A' + 10;
			}
			decimal += value * Math.pow(16, power);
			power++;
		}
		return decimal;
	}

	public static String decimalToBinary(int num) {
		if (num == 0)
			return "0";
		String binary = "";
		while (num > 0) {
			binary = (num % 2) + binary;
			num = num / 2;
		}

		return binary;
	}

	public static String decimalToHex(int num) {
		if (num == 0)
			return "0";

		char[] hexChars = {
				'0', '1', '2', '3',
				'4', '5', '6', '7',
				'8', '9', 'A', 'B',
				'C', 'D', 'E', 'F'
		};

		String hex = "";
		while (num > 0) {
			int remainder = num % 16;
			hex = hexChars[remainder] + hex;

			num = num / 16;
		}
		return hex;
	}

	public static void main(String[] args) {
		new it1();
	}

}