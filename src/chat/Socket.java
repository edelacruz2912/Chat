package chat;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Socket {

	private InetAddress myIpAddress; //ip
	public int port; // port
	private DatagramSocket socket = null;
	//private ConcurrentLinkedQueue<DatagramPacket> messageQueue = new ConcurrentLinkedQueue<DatagramPacket>();
	public static ConcurrentHashMap<String,Window> hashMapDataHolder = new ConcurrentHashMap<String,Window>(); 
	private String MSN = null;
	
	public String inputMsn = null;	
	public String Ip; //Ip :this is where it cames from. 
	
	//For the title of each window
	String reciverIp = null;
	String reciverPort = null;
	
	// BroadCast Code.
	private String broadcRequest = null;
	private String broadcRespond = "";
	private String myName = "Eduardo";
	private InetAddress broadCDestinationIp ;
	private String[] msnArray ;
	
	
	
	
	
	public Socket(int port)
	{
		this.port = port;
				
		try
		{
			this.myIpAddress = InetAddress.getLocalHost();
			this.socket = new DatagramSocket(port, this.myIpAddress);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
		
		Thread receiveThread = new Thread(
				new Runnable() {
					public void run() {
						try {
							receiveThread();
						} catch (UnknownHostException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
		receiveThread.setName("Receive Thread For Port = " + this.port);
		receiveThread.start();	
		
	}
		
	
	public void receiveThread() throws UnknownHostException 
	{
		//dataInsideHasMap();
		//System.out.println("on the receiveThread method");
		
		do {
			byte[] inBuffer = new byte[1024];
			for ( int i = 0 ; i < inBuffer.length ; i++ ) {
				inBuffer[i] = ' ';
			}

			DatagramPacket inPacket = new DatagramPacket(inBuffer, inBuffer.length);
			
			try {
				// this is a blocking call
				socket.receive(inPacket);
			} catch(Exception e) {
				e.printStackTrace();
				System.exit(-1);
			}
			
			String message = new String(inPacket.getData());
			message = message.trim();
			System.out.println("------->>>> " + message);
			//this.MSN = message;
			Ip = inPacket.getAddress().getHostAddress();//Ip :this is where it cames from. 
			port = inPacket.getPort();//this is the port where the msn it came from.
			
			//use setters for the incomingPort and incomingID
			setIncomingPort(port);
			setIncomingIp(Ip);
			
			
			/*System.out.println("ReceiveThread - Message on port = " + this.port + 
					" message = " + message + "\n" +
					" From IP = " + inPacket.getAddress() + 
					" From Port = " + inPacket.getPort());*/
			//messageQueue.add(inPacket);
			//Code For Part 2 Of The project.
			
			//Receiving if BroadCast is for me.
			//"????? name-of-other-person ##### your-name"			
			//"##### name-of-other-person ##### ww.xx.yy.zz"
						
			//respond when I get a broadCast
			//it is for me .
			broadcRespond = "##### Eduardo ##### "+InetAddress.getLocalHost().getHostAddress();
			
			
			if(message.contains("????? Eduardo"))
			{
				//breaking the String
				msnArray = message.split(" ");
				String name = msnArray[1];
				System.out.println(name);
				
				if(!hashMapDataHolder.containsKey("Ip:"+ this.Ip+"Port:"+"64000"))
				{
					// send my information to the source
					System.out.println("I am getting a broadCast");
					Window chatWindow = new Window(this);					
					this.send(broadcRespond, InetAddress.getByName(Ip), 64000);					
					chatWindow.setDestIP(this.Ip);
					chatWindow.setDestPort("64000");
					hashMapDataHolder.put("Ip:"+ this.Ip+"Port:"+"64000", chatWindow);
					
				
				}
			}
			//If I am sending a broadCast
			//and I get a respond.
			else if(message.contains("##### ") && !message.contains("?????"))
			{	
				System.out.println("this is a respond from someone else");
				if(!hashMapDataHolder.containsKey("Ip:"+ this.Ip+"Port:"+"64000")) 
				{
					//int lastSpace = message.lastIndexOf(" ");
					//System.out.println("message " + message + "<----");
					//System.out.println("lastSpace " + lastSpace);
					//String sourceIp = message.substring(lastSpace+1);
					Window bCresponder = new Window(this);
					bCresponder.setDestIP(this.Ip);//I had sourceIp in the method.
					bCresponder.setDestPort("64000");
					//System.out.println("sourceIp " + sourceIp);
					//System.out.println("Comparing this.Ip and sourceIP");
					//System.out.println("sourceIp " + sourceIp + " this.Ip "+ this.Ip);
					hashMapDataHolder.put("Ip:"+ this.Ip+"Port:"+"64000", bCresponder);
				}				
				
			}
			else
			{
				//System.out.println("this is the msn from message variable "+message);
				//getting the window depending of the key been used
				//Only executes if I have your information already.				
				if(hashMapDataHolder.containsKey("Ip:"+ this.Ip+"Port:"+"64000")) 
				{
					//System.out.println("I have your info inside the else Statament");
					//System.out.println("else (this is a regular msn MF)");
					Window currentWindow = hashMapDataHolder.get("Ip:"+ this.Ip+"Port:"+"64000");
					currentWindow.appendTxtToTextArea(message);
				}
			}
			
						
			/*----CODE FOR THE VERSION 
			 * - submit for homework 6
			//Receiving 
			String key = getIpPortKey(Ip, Integer.toString(port));
			if(hashMapDataHolder.containsKey(key)) //check if info package is on the hashMap to link to chat window.
			{
				System.out.println("Incoming Message from: " + Ip + port);
				System.out.println("Message Body" + message + "\n");
				Window window = hashMapDataHolder.get(key); //getting the window
				window.appendTxtToTextArea(Ip + " " + port + " :" + message);
			}
			else // NEW MESSAGE 
			{
				Window w = new Window(this);
				w.setDestIP(Ip);
				w.setDestPort(Integer.toString(port));
				//ip , port , window 
				System.out.println("Message came from NEW SENDER");
				//w.destinationIPnumberTextA.setText(Ip);
				///w.destinationPortNumberTextA.setText(Integer.toString(port));
				hashMapDataHolder.put(w.getIPandPort() , w);
				w.appendTxtToTextArea(Ip + " " + port + " :" + message);
				
			}*/
			
			
			
			//dataInsideHasMap();
			
		} while(true);
	}
	
	/*
	public DatagramPacket receive() {
		return messageQueue.poll();
	}*/
	
	/**
	 * set the IP of the incoming chat
	 * @param ip
	 */
	private void setIncomingIp(String ip)
	{
		this.reciverIp = ip;
	}
	
	/**
	 * set the Port of the incoming chat
	 * @param ReceiverPort
	 */
	private void setIncomingPort(int ReceiverPort)
	{
		this.reciverPort = Integer.toString(ReceiverPort);
	}
	
	
	
	/**
	 * the incoming Ip from the user who send 
	 * MSN to me
	 * 
	 * @return String
	 */
	
	public String getIncomingIp()
	{
		return this.reciverIp;
	}
	
	/**
	 * get the port Number From the 
	 * user sending MSN to me.
	 * @return the receiverPort as a String
	 */
	
	public String getIncomingPort()
	{
		return this.reciverPort;
	}
	
	
	
	/**
	 * Retrieve the IP and Port in Hashmap Key format
	 * @param Ip    - incoming message's IP
	 * @param port	- incoming message's port
	 * @return	Hashmap Key format.
	 */
	private String getIpPortKey()
	{
		return "Ip:"+ this.Ip + "Port:"+ "64000";
	}
	
		
	public void dataInsideHasMap()
	{
		System.out.println("insideHashMap");
		for(String str : hashMapDataHolder.keySet()) 
		{
			System.out.println("\t\tkeys(Ip&Port) : " + str + "  value(Window)"	+ hashMapDataHolder.get(str).toString());
			
		}
		
		if(hashMapDataHolder.isEmpty())
		{
			System.out.println("the hashMap is empty");
		}
	}
		
	
	public void send(String s, InetAddress destinationIP, int destinationPort) {
		
		byte[] outBuffer;
		
		//dataInsideHasMap();
		//System.out.println("Sending Msg to: " + destinationIP.toString() + destinationPort);
		//System.out.println("Msg Body: " + s + "\n");
		
		try {
			outBuffer = s.getBytes();
			DatagramPacket outPacket = new DatagramPacket(outBuffer, s.length());
			outPacket.setAddress(destinationIP);
			outPacket.setPort(destinationPort);
			socket.send(outPacket);
			//System.out.println("MSN: " + s);
			//System.out.println("msn has been send to port : " + destinationPort);
			//System.out.println("destinationIp : " + destinationIP);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		//dataInsideHasMap();
	}
	
}
