import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class Receiver extends JFrame implements ActionListener {
	private JTextField ipAddText;
	private JTextField portNoText;
	private JLabel portNoDatalabel;
	private JTextField portNoDataText;
	private JLabel textFileName;
	private JTextField textFileNameField;
	private JLabel packetsReceivedLabel;
	private JTextField packetsReceivedField;
	private static JButton receiveButton;
	private JLabel maxSizeLabel;
	private JTextField maxSizeField;
	private JLabel timeoutLabel;
	private JTextField timeoutField;
	

	private JPanel panel;
	private JPanel secondaryPanelOne;
	private JPanel secondaryPanelTwo;

	private JLabel ipLabel;
	private JLabel portLabel;

	private static Socket clientSocket = null;
	private PrintWriter out = null;
	private static BufferedReader in = null;
	private static boolean all = false;
	private static DatagramSocket socket;

	public Receiver() {
		this.panel = new JPanel(new GridLayout(3, 1));
		this.secondaryPanelOne = new JPanel(new GridLayout(10, 5));
		// this.secondaryPanelTwo = new JPanel(new GridLayout(1, 2));
		// this.ipAddText = new JTextField(10);
		// this.portNoText = new JTextField(5);

		this.ipLabel = new JLabel("Sender IP Address");
		this.ipAddText = new JTextField(20);
		this.portLabel = new JLabel("Port for ACK");
		this.portNoText = new JTextField(5);
		// userMsg.setBackground(Color.RED);
		// ClientGUI.serverMsg = new JTextArea();
		// ClientGUI.serverMsg.setBackground(Color.RED);
		this.portNoDatalabel = new JLabel("Port for Data");
		this.portNoDataText = new JTextField(5);
		this.textFileName = new JLabel("Text File Name");
		this.textFileNameField = new JTextField(5);
		this.packetsReceivedLabel = new JLabel("Number of inorder packets");
		this.packetsReceivedField = new JTextField(5);
		this.maxSizeLabel=new JLabel("Max size of datagram");
		this.maxSizeField=new JTextField(10);
		this.timeoutLabel = new JLabel("Timeout (microseconds)");
		this.timeoutField = new JTextField(20);
		Receiver.receiveButton = new JButton("Receive");
		
		

		// this.serverMsg = new JTextArea(10, 30);
		// this.serverMsg.setEditable(false);
		this.secondaryPanelOne.add(new JLabel());
		this.secondaryPanelOne.add(new JLabel());
		this.secondaryPanelOne.add(new JLabel());
		this.secondaryPanelOne.add(new JLabel());
		this.secondaryPanelOne.add(new JLabel());
		
		this.secondaryPanelOne.add(new JLabel());
		this.secondaryPanelOne.add(this.ipLabel);
		this.secondaryPanelOne.add(new JLabel());
		this.secondaryPanelOne.add(this.ipAddText);
		this.secondaryPanelOne.add(new JLabel());
		this.secondaryPanelOne.add(new JLabel());
		this.secondaryPanelOne.add(this.portLabel);
		this.secondaryPanelOne.add(new JLabel(""));
		this.secondaryPanelOne.add(this.portNoText);
		this.secondaryPanelOne.add(new JLabel());
		
		

		addActionListeners();

		/*
		 * this.buttonPanel.add(this.sButton); this.buttonPanel.add(this.uButton);
		 * this.buttonPanel.add(this.gButton); this.buttonPanel.add(this.rButton);
		 */this.secondaryPanelOne.add(new JLabel());
		this.secondaryPanelOne.add(this.portNoDatalabel);
		this.secondaryPanelOne.add(new JLabel(""));
		this.secondaryPanelOne.add(this.portNoDataText);
		this.secondaryPanelOne.add(new JLabel());

		this.secondaryPanelOne.add(new JLabel());
		this.secondaryPanelOne.add(this.textFileName);
		this.secondaryPanelOne.add(new JLabel(""));
		this.secondaryPanelOne.add(this.textFileNameField);
		this.secondaryPanelOne.add(new JLabel());
		
		this.secondaryPanelOne.add(new JLabel());
		this.secondaryPanelOne.add(this.packetsReceivedLabel);
		this.secondaryPanelOne.add(new JLabel());
		this.secondaryPanelOne.add(this.packetsReceivedField);
		this.secondaryPanelOne.add(new JLabel());
		
		this.secondaryPanelOne.add(new JLabel());
		this.secondaryPanelOne.add(this.maxSizeLabel);
		this.secondaryPanelOne.add(new JLabel());
		this.secondaryPanelOne.add(this.maxSizeField);
		this.secondaryPanelOne.add(new JLabel());
		
		this.secondaryPanelOne.add(new JLabel());
		this.secondaryPanelOne.add(this.timeoutLabel);
		this.secondaryPanelOne.add(new JLabel());
		this.secondaryPanelOne.add(this.timeoutField);
		this.secondaryPanelOne.add(new JLabel());
		
		this.secondaryPanelOne.add(new JLabel());
		this.secondaryPanelOne.add(new JLabel());
		this.secondaryPanelOne.add(new JLabel());
		this.secondaryPanelOne.add(new JLabel());
		this.secondaryPanelOne.add(new JLabel());
		
		this.secondaryPanelOne.add(new JLabel());
		this.secondaryPanelOne.add(new JLabel());
		this.secondaryPanelOne.add(this.receiveButton);
		this.secondaryPanelOne.add(new JLabel());
		/*
		 * this.inputPanel.add(this.isbnLabel); this.inputPanel.add(this.isbnField);
		 * this.inputPanel.add(this.titleLabel);
		 * this.inputPanel.add(this.titleTextField);
		 * this.inputPanel.add(this.authorLabel); this.inputPanel.add(this.authorField);
		 * this.inputPanel.add(this.publisherLabel);
		 * this.inputPanel.add(this.publisherField);
		 * this.inputPanel.add(this.yearLabel); this.inputPanel.add(this.yearField);
		 */

		/*
		 * this.secondaryPanelTwo.add(this.buttonPanel);
		 * this.secondaryPanelTwo.add(this.inputPanel);
		 */

		this.panel.add(this.secondaryPanelOne);
		// this.panel.add(ClientGUI.serverMsg);
		this.add(this.panel);
		this.setSize(800, 600);
		this.setVisible(true);
	}

	// connect
	
	void addActionListeners() {
		this.receiveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Integer receiverPortNum;
				Integer senderPortNum;
				Integer datagramMaxSize;

				if(checkFields(portNoText.getText(), portNoDataText.getText(), textFileNameField.getText(), maxSizeField.getText(), ipAddText.getText())) {
					Receiver.receiveButton.setEnabled (false);
					receiverPortNum = Integer.parseInt(portNoText.getText());
					senderPortNum = Integer.parseInt(portNoDataText.getText());
					String fileName = textFileNameField.getText();
					datagramMaxSize = Integer.parseInt(maxSizeField.getText());
					byte[] buf = new byte[(int) (long) datagramMaxSize]; 
					InetAddress address = null;
					try {
						address = InetAddress.getByName(ipAddText.getText());
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						address=null;
						e1.printStackTrace();
					}

					DatagramPacket packet= new DatagramPacket(buf, buf.length, address, senderPortNum);
					String hello="0";
					packet.setData(hello.getBytes());
					try {
						socket = new DatagramSocket(receiverPortNum);
					} catch (SocketException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						socket.send(packet);
						System.out.println("Packet sent: "+packet);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					try {
						receive(datagramMaxSize,fileName, senderPortNum);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						System.err.println("Receive failed");
						e1.printStackTrace();
					}
				}
			}
		
		});
		
								 
	}

	public boolean checkFields(String receiverPortNum, String senderPortNum, String fileName, String datagramMaxSize, String address){
		boolean valid=true;
		if(address == null || address.isEmpty()|| !isValidInet4Address(address)) {
			JOptionPane.showMessageDialog(null,
				    "IP address invalid",
				    "Invalid Input",
				    JOptionPane.WARNING_MESSAGE);
			valid=false;
		}
		else if(receiverPortNum==null || fileName.isEmpty()|| Integer.parseInt(receiverPortNum)<0) {
			JOptionPane.showMessageDialog(null,
				    "Invalid Receiver Port Number",
				    "Invalid Input",
				    JOptionPane.WARNING_MESSAGE);
			valid=false;
		}
		else if(senderPortNum==null || Integer.parseInt(senderPortNum)<0) {
			JOptionPane.showMessageDialog(null,
				    "Invalid Sender Port Number",
				    "Invalid Input",
				    JOptionPane.WARNING_MESSAGE);
			valid=false;
		}
		else if(fileName == null || fileName.isEmpty()){
			JOptionPane.showMessageDialog(null,
				    "Invalid File Name",
				    "Invalid Input",
				    JOptionPane.WARNING_MESSAGE);
			valid=false;
		}	
		else if(datagramMaxSize==null || Integer.parseInt(datagramMaxSize)>=65508|| Integer.parseInt(datagramMaxSize)<0){
			JOptionPane.showMessageDialog(null,
				    "Invalid Maximum Datagram Size",
				    "Invalid Input",
				    JOptionPane.WARNING_MESSAGE);
			valid=false;
		}
		
		return valid;
	}





	public static boolean isValidInet4Address(String address) {
		final String IPV4_REGEX =
				"^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
						"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
						"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
						"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
		final Pattern IPv4_PATTERN = Pattern.compile(IPV4_REGEX);
		if (address == null) {
			return false;
		}

		Matcher matcher = IPv4_PATTERN.matcher(address);

		return matcher.matches();
	}
	public void receive(int datagramMaxSize, String fileName, int senderPort) throws IOException{
		System.out.println("Receive called");
		boolean eof =true;
		byte[] buf = new byte[(int) (long) datagramMaxSize]; 
		DatagramPacket packet = new DatagramPacket(buf, buf.length);
		DatagramPacket lastPacket = new DatagramPacket(buf, buf.length);
		String line;
		String responseString;
		int packetCount=0;
		//File file = new File("C:\\Users\\pacman\\eclipse-workspace\\CP372 A2\\src\\"+fileName);
		FileOutputStream fileWriter=null;
		
		try {
			System.out.println("Working Directory = " + System.getProperty("user.dir"));
			System.out.println("Field file name: "+fileName);
			//fileWriter = new FileOutputStream("C:\\Users\\pacman\\eclipse-workspace\\CP372 A2\\src\\"+fileName);
			fileWriter = new FileOutputStream(System.getProperty("user.dir").concat("\\"+fileName));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		  
		//Create the file
		/*
		try {
			if (file.createNewFile())
			{
			    System.out.println("File created");
			} else {
			    System.out.println("File already exists.");
			}
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		FileWriter fileWriter=null;
		String seqNum="0";
		try {
			fileWriter = new FileWriter(file);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		/*
		InetAddress address = null;
		try {
			address = InetAddress.getByName(ipAddText.getText());
		} catch (UnknownHostException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} 
		DatagramPacket packetOut = new
		DatagramPacket(buf, buf.length, address, senderPort); 
		try {
			socket.send(packetOut);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		*/
				 
		System.out.println("Incoming: ");
		while(eof) {
			try {
				socket.receive(packet);
				packetCount++;
				packetsReceivedField.setText(Integer.toString(packetCount+1));
			}
				// TODO Auto-generated catch block
				catch(SocketTimeoutException e) {
					System.err.println("Timeout");
						socket.send(lastPacket);

				}
			line=new String(packet.getData(), 0, packet.getLength());
			line=line.trim();
			System.out.println("line: "+line);
			if(line.equals("EOF")){
				eof=false;
				break;
			}else {
				try {
					line=line.concat("\n");
					fileWriter.write(line.substring(1, line.length()).getBytes());
					System.out.println("Writing to file: "+line);
					//System.out.println("Writing to file: "+line.substring(1, line.length()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			DatagramPacket response= new DatagramPacket(buf, buf.length, packet.getAddress(), packet.getPort());
			
			responseString= packetCount%2==0 ? "0": "1";
			response.setData(responseString.getBytes());
			//System.out.println(line+"\n");

				socket.send(response);
				lastPacket=packet;
				socket.setSoTimeout(Integer.parseInt(timeoutField.getText())/1000);
			
			
		}
		try {
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Receiver.receiveButton.setEnabled (true);
	}
	

	
	

	

	public static void main(String args[]) {
		try {
			new Receiver();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("GUI BUILT");
	}
		/*
		 * while(!(ClientGUI.connected)) { } receive(ClientGUI.in);
		 }


	public static void receive(BufferedReader in, JTextField isbnField, JTextField titleTextField,
			JTextField authorField, JTextField publisherField, JTextField yearField) throws InterruptedException {
		String serverComm;
		System.out.println("		Receiving");
		/*try {
			while (Receiver.isConnected()) {
				System.out.println("\n		Bufffered Reader ready");
				serverComm = in.readLine();
				if (serverComm != null) {
					serverComm = serverComm.trim();
					System.out.println("		Line read\n");
					if (!(serverComm.isEmpty())) {
						if (serverComm.equalsIgnoreCase("END")) {
							System.out.println("		END received");
							break;
						} else if (serverComm.equalsIgnoreCase("WIPE")) {
							System.out.println("		Wipe received");
							//userMsg.setText("");
						} else if (serverComm.equalsIgnoreCase("SPACE")) {
							//userMsg.append("\n");
						} else {
							System.out.println("		Server: " + serverComm);
							//userMsg.append(serverComm + "\n");
						}
					}
				}
			}*/
		/*} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		System.out.println("		Receiving end");
		Receiver.all = false;
		isbnField.setText("");
		titleTextField.setText("");
		authorField.setText("");
		publisherField.setText("");
		yearField.setText("");
		*/
	


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
