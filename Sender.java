import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class Sender {
	//DatagramSocket socket = null;
    public static BufferedReader in = null;
    public static boolean fileEnd;
    public static String receiverIP;
    public static int receiverPortNum;
    public static int senderPortNum;
    public static String fileName;
    public static long datagramMaxSize;
    public static int timeout;
    public static File file;
    
	public static void main(String argv[]) throws IOException {
		// Get the port number from the command line.

		receiverIP = argv[0];
		receiverPortNum = Integer.parseInt(argv[1]);
		senderPortNum = Integer.parseInt(argv[2]);
		String fileName = System.getProperty("user.dir").concat("\\src\\"+argv[3]);
		datagramMaxSize = Long.parseLong(argv[4]);
		timeout = Integer.parseInt(argv[5]);
		fileEnd = true;
		file=new File(fileName);
		String[] fileArray;
		
		DatagramSocket socket = new DatagramSocket(senderPortNum);
		try {
            in = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            System.err.println("Could not open quote file.");
        }
		fileArray=fileToArray();
		int packetCount=0; 
		String alternate;
		byte[] buf = new byte[(int) (long) datagramMaxSize];
		 while (packetCount<=fileArray.length) {
			 
			 alternate= packetCount%2==0 ? "0": "1";
             // receive request
             
             DatagramPacket packet = new DatagramPacket(buf, buf.length);
			 while(true) {
				 try {
					 socket.receive(packet);
					 
					   // rest of the success path
					} catch (SocketTimeoutException ex) {
						System.err.print("Timeout");
					}
				 
				 String packetData = new String(packet.getData());
				 packetData=packetData.trim();
				 System.out.println("packetData: "+packetData);
				 System.out.println("Seq Num expected: "+alternate);
				 if(packetData.substring(0, 1).equals(alternate)) {
					 System.out.println("Breaking");
					 break;
				 }
				 
			 }
			 
	            try {
	                
	                //socket.receive(packet);

	                // figure out response
	                String dString = null;
	                dString = fileArray[packetCount];
	                packetCount++;
	                buf = dString.getBytes();

			// send the response to the client at "address" and "port"
	                //InetAddress address = packet.getAddress();
	                //int port = packet.getPort();
	                
	                InetAddress address = InetAddress.getByName(argv[0]); 
	                //packet = new DatagramPacket(buf, buf.length, address, port);
	                
	                packet = new DatagramPacket(buf, buf.length, address, receiverPortNum);
	                socket.send(packet);
	            } catch (IOException e) {
	                e.printStackTrace();
	                
	            }
	        }
		
		//Sending datagram
		/*
		 * buf = new byte[(int) (long) datagramMaxSize]; InetAddress address =
		 * InetAddress.getByName(argv[0]); DatagramPacket packetOut = new
		 * DatagramPacket(buf, buf.length, address, receiverPortNum);
		 * socket.send(packetOut);
		 * 
		 * //Receiving datagram DatagramPacket packet = new DatagramPacket(buf,
		 * buf.length); socket.receive(packet);
		 */
		
	}
	public static String[] fileToArray() {
        String [] packetString;
        packetString= new String[(int) (Math.ceil( ((double)file.length()/datagramMaxSize)))+1];
        String line;
        String temp="";
        int count=0;
        String seqNum;
        packetString[0]="0";
        for (int y=1;y<packetString.length;y++) {
        	packetString[y]="";
        }
        while(fileEnd) {
        	try {
        		if ((line= in.readLine()) == null) {
        			in.close();
        			fileEnd = false;
        			count++;
        			packetString[count]=packetString[count].concat("EOF");
        			//packetString = "EOF";
        		}else {
        			if((packetString[count].length()+line.length())<=datagramMaxSize) {
        				packetString[count]=packetString[count].concat(line);
        			}else {
        				seqNum= count%2==0 ? "1": "0";
        				count++;
        				packetString[count]=packetString[count].concat(seqNum+line);
        			}
        		}
        	} catch (IOException e) {
        		//packetString = "IOException occured in server";
        	}
        }
        return packetString;
        
    }
public class Datagram{
	private Integer sequenceNumber;
	
}
	
}
