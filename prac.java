package main;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
public class prac extends JFrame{
	
	static JTextField guestname, roomtype, checkin, checkout;
	static JTable tab;
	static DefaultTableModel mod;

prac(){

	String[] column = {"Guest Name", "Room Type", "Check In", "Check Out"};
	mod = new DefaultTableModel(column, 0);
	tab = new JTable (mod);
	JScrollPane scroll = new JScrollPane(tab);
	add(scroll).setBounds(10,10,765,550);
	
	JLabel name = new JLabel("Guest Name");
	add(name).setBounds(25,570,100,20);
	guestname = new JTextField();
	add(guestname).setBounds(25,590,130,20);
	
	JLabel room = new JLabel("Room Type");
	add(room).setBounds(165,570,100,20);
	roomtype = new JTextField();
	add(roomtype).setBounds(165,590,130,20);
	
	JLabel in = new JLabel("Check in Date");
	add(in).setBounds(305,570,100,20);
	checkin = new JTextField();
	add(checkin).setBounds(305,590,130,20);
	
	JLabel out = new JLabel("Check out Date");
	add(out).setBounds(445,570,100,20);
	checkout = new JTextField();
	add(checkout).setBounds(445,590,130,20);
	
	JButton add = new JButton("Add");
	add(add).setBounds(640,590,100,20);
	
	add.addActionListener(e ->{
		
		String data = guestname.getText() + "#" + roomtype.getText() + "#" + 
		              checkin.getText() + "#" + checkout.getText();
 		
		mod.addRow(data.split("#"));
		
		try (FileWriter fw = new FileWriter("hotel.txt", true)) {
		    fw.write(data + "\n");
		    JOptionPane.showMessageDialog(null, "Data saved");
		} catch (IOException ec) {
		    JOptionPane.showMessageDialog(null, "Error saving data: " + ec.getMessage());
		}
	});
	
	JButton up = new JButton("Update");
	add(up).setBounds(640,620,100,20);
	
	up.addActionListener(e ->{
		int selectRow = tab.getSelectedRow();
		
		if (selectRow == -1) {
			JOptionPane.showMessageDialog(null, "Please select a data to be updated" );
		}
		
		String newName = guestname.getText();
	    String newRoom = roomtype.getText();
	    String newIn = checkin.getText();
	    String newOut = checkout.getText();
	    String updatedLine = newName + "#" + newRoom + "#" + newIn + "#" + newOut;
	    
	    mod.setValueAt(newName, selectRow, 0);
	    mod.setValueAt(newRoom, selectRow, 1);
	    mod.setValueAt(newIn, selectRow, 2);
	    mod.setValueAt(newOut, selectRow, 3);
		
	    java.util.ArrayList<String> lines = new java.util.ArrayList<>();
	    try (BufferedReader br = new BufferedReader(new FileReader("hotel.txt"))) {
	        String line;
	        int currentLine = 0;
	        while ((line = br.readLine()) != null) {
	            if (currentLine == selectRow) {
	                
	                lines.add(updatedLine);
	            } else {
	                
	                lines.add(line);
	            }
	            currentLine++;
	        }
	    } catch (IOException ex) {
	        System.err.println("Read error: " + ex);
	    }

	 
	    try (BufferedWriter bw = new BufferedWriter(new FileWriter("hotel.txt"))) {
	        for (String record : lines) {
	            bw.write(record + "\n");
	        }
	        JOptionPane.showMessageDialog(null, "Record updated successfully!");
	    } catch (IOException ex) {
	        System.err.println("Write error: " + ex);
	    }
	});
	
	JButton delt = new JButton("Delete");
	add(delt).setBounds(640,650,100,20);

	delt.addActionListener(e ->{
		int selectedRow = tab.getSelectedRow();
		
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(null, "Please select a data to be deleted" );
		}
		
		int confirmation = JOptionPane.showConfirmDialog(null,
				"Are you sure you want to delete this data?", "Confirm Delete"
				, JOptionPane.YES_NO_OPTION);
		if (confirmation != JOptionPane.YES_OPTION)return;
		
		mod.removeRow(selectedRow);
	});
	
	
	JButton ex = new JButton("Exit");
	add(ex).setBounds(640,680,100,20);
	
	ex.addActionListener(e->{
		System.exit(0);
	});
	
	setTitle("Hotel Reservation");
	setLayout(null);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	setSize(800,800);
	setVisible(true);
}	

	public static void main(String[] args) {
		new prac();

	}

}
