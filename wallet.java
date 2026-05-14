package Main;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.awt.*;
public class wallet extends JFrame {
	
	static JTextField name,course,year,balance,stats;
	static JTable table;
	static DefaultTableModel tablemodel;
	
	wallet(){
		JLabel n = new JLabel("Full Name");
		add(n).setBounds(10,10,100,20);
		name = new JTextField();
		add(name).setBounds(10,30,180,20);
		
		JLabel c = new JLabel("Course/Dept");
		add(c).setBounds(10,60,100,20);
		course = new JTextField();
		add(course).setBounds(10,80,180,20);
		
		JLabel y = new JLabel("Year Level");
		add(y).setBounds(205,10,100,20);
		year = new JTextField();
		add(year).setBounds(205,30,180,20);
		
		JLabel w = new JLabel("Wallet Balance");
		add(w).setBounds(205,60,120,20);
		balance = new JTextField();
		add(balance).setBounds(205,80,180,20);
		
		JLabel s = new JLabel("Status (Active or Suspended)");
		add(s).setBounds(395,10,200,20);
		stats = new JTextField();
		add(stats).setBounds(395,30,180,20);
		
		JButton ad = new JButton("Add");
		add(ad).setBounds(405,55,80,20);
		
		ad.addActionListener(e ->{
			if(!input()){
				return;
			}
			
			String data = name.getText() + "#" + course.getText() + "#" + year.getText() + "#" + balance.getText() + "#" + stats.getText();
			
		tablemodel.addRow(data.split("#"));
		
		 try (FileWriter fw = new FileWriter("Balance.txt", true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                out.println(data);
                JOptionPane.showMessageDialog(null, "Data Added and Saved!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error saving data");
            }
 
		});
		
		JButton up = new JButton("Update");
		add(up).setBounds(490,55,80,20);
		
		try {
			File file = new File ("Balance.txt");
			if (!file.exists()){
				file.createNewFile();
			}
			
		} catch (Exception e ){
		text();	
		}
		up.addActionListener(e -> {
			int selected = table.getSelectedRow();
			
			if (selected == -1){
				JOptionPane.showMessageDialog(null, "Please update data");
				return;
			}
			
			if(!input()){
				return;
			}
			
			String name1 = name.getText();
			String course1 = course.getText();
			String year1 = year.getText();
			String balance1 = balance.getText();
			String stats1 = stats.getText();
			String update = name1 + "#" + course1 + "#" + year1 + "#" + balance1 + "#" + stats1;
			
		    tablemodel.setValueAt(name1, selected, 0);
		    tablemodel.setValueAt(course1, selected, 1);
		    tablemodel.setValueAt(year1, selected, 2);
		    tablemodel.setValueAt(balance1, selected, 3);
		    tablemodel.setValueAt(stats1, selected, 4);	    
			
			java.util.ArrayList<String> lines = new java.util.ArrayList<>();
			 try (BufferedReader br = new BufferedReader(new FileReader("Balance.txt"))) {
			        String line;
			        int currentLine = 0;
			        while ((line = br.readLine()) != null) {
			            if (currentLine == selected) {
			                
			                lines.add(update);
			            } else {
			                
			                lines.add(line);
			            }
			            currentLine++;
			        }
			    } catch (IOException ex) {
			        System.err.println("Read error: " + ex);
			    }

			 
			    try (BufferedWriter bw = new BufferedWriter(new FileWriter("Balance.txt"))) {
			        for (String record : lines) {
			            bw.write(record + "\n");
			        }
			        JOptionPane.showMessageDialog(null, "Record updated successfully!");
			    } catch (IOException ex) {
			        System.err.println("Write error: " + ex);
			    }
		});
		
		
		
		JButton del = new JButton("Delete");
		add(del).setBounds(405,80,80,20);
		
		del.addActionListener(e ->{
			int selectedRow = table.getSelectedRow();
			
			if (selectedRow == -1) {
				JOptionPane.showMessageDialog(null, "Please select a data to be deleted" );
			}
			
			int confirmation = JOptionPane.showConfirmDialog(null,
					"Are you sure you want to delete this data?", "Confirm Delete"
					, JOptionPane.YES_NO_OPTION);
			if (confirmation != JOptionPane.YES_OPTION)return;
			
			tablemodel.removeRow(selectedRow);
		});
		
		JButton cl = new JButton("Clear");
		add(cl).setBounds(490,80,80,20);
		
		cl.addActionListener(e ->{
			clear()
			;
		});
		
		String[] columns = {"Full Name", "Course/Dept", "Year Level", "Wallet Balance", "Status"};
		tablemodel = new DefaultTableModel(columns, 0);
		table = new JTable(tablemodel);
		JScrollPane sp = new JScrollPane(table);
		add(sp).setBounds(10,110,560,340);
		
		text();
		
		setTitle("School Canteen Wallet");
		setLayout(null);
		setVisible(true);
		setSize(600,500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	        table.setForeground(Color.BLACK);            
	        table.setGridColor(Color.LIGHT_GRAY);         
	        
	        table.getTableHeader().setOpaque(false);
	        table.getTableHeader().setBackground(Color.WHITE); 
	        table.getTableHeader().setForeground(Color.BLACK); 
	}
	public void clear(){
		name.setText("");
		course.setText("");
		year.setText("");
		balance.setText("");
		stats.setText("");
		
	}
	
	
	public void text(){
		try (BufferedReader br = new BufferedReader(new FileReader("Balance.txt"))){
			String line;
			while ((line = br.readLine()) != null) {
				String row [] = line.split(" - ");
				if (row.length == 3)
					tablemodel.addRow(row);
			}
		}catch (Exception e){
			
		}
		
	}
	
	public boolean input() {
	    String name1 = name.getText();
	    String course1 = course.getText();
	    String year1 = year.getText();
	    String balance1 = balance.getText();
	    String stats1 = stats.getText();
	    
	    if (name1.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Must input full name");
	        return false;
	    }
	    if (course1.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Must input Course/Department");
	        return false;
	    }
	    if (!year1.matches("\\d+")) {
	        JOptionPane.showMessageDialog(null, "Year Level must be numeric");
	        return false;
	    }   
	    if (!balance1.matches("\\d+(\\.\\d+)?")) {
	        JOptionPane.showMessageDialog(null, "Wallet Balance must be numeric/decimal");
	        return false;
	    }
	    if (!stats1.equalsIgnoreCase("Active") && !stats1.equalsIgnoreCase("Suspended")) {
	        JOptionPane.showMessageDialog(null, "Status must only accept: Active or Suspended");
	        return false;
	    }
	    return true;
	}
	
	
	public static void main(String[] args) {
		new wallet();

	}

}