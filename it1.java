package javapr;
import javax.swing.*;
import java.awt.*;
public class it1 extends JFrame {

	static JTextField input;
	static JComboBox<String> fromBox, toBox; 
	static JTextArea result;
	it1(){
		
		JLabel in = new JLabel("Enter a number");
		add(in).setBounds(100,30,100,20);
		input = new JTextField();
		add(input).setBounds(100,60,120,20);
		
		
		JLabel from = new JLabel("From");
		add(from).setBounds(100,100,100,20);
		String [] options = {"Binary", "Octal", "Decimal", "Hexadecimal"};
		fromBox = new JComboBox<>(options);
		add(fromBox).setBounds(100, 120, 120,20);
		
		JLabel to = new JLabel("To");
		add(to).setBounds(100,150,100,20);
		String [] options1 = {"Binary", "Octal", "Decimal", "Hexadecimal"};
		toBox = new JComboBox<>(options1);
		add(toBox).setBounds(100, 170, 120,20);
		
		JButton convert = new JButton("Convert");
		add(convert).setBounds(200,120,100,20);
		
		convert.addActionListener(e -> {
			String inString = input.getText();
			String from1 = (String) fromBox.getSelectedItem();
			String to1 = (String) toBox.getSelectedItem();
			
			
			
			
			
		});
		
		
		result = new JTextArea();
        result.setEditable(false);
        JScrollPane scroll = new JScrollPane(result);
        add(scroll).setBounds(10, 200, 415, 300);
        
        
        
		
		
		setTitle("Number Converter System");
		setLayout(new BorderLayout(10, 10));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setSize(450,550);
		
	}
	public static void main(String[] args) {
		new it1();

	}

}
