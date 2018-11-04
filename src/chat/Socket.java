package chat;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Socket {

	private InetAddress myAddress; //Ip 
	public int port; // port
	private DatagramSocket socket = null;
	//private ConcurrentLinkedQueue<DatagramPacket> messageQueue = new ConcurrentLinkedQueue<DatagramPacket>();
	public static ConcurrentHashMap<String,Window> hashMapDataHolder = new ConcurrentHashMap<String,Window>(); 
	private String MSN = null;
	
	public String inputMsn = null;	
	public String Ip;
	
	//For the title of each window
	String reciverIp = null;
	String reciverPort = null;
	
	
	
	
	public Socket(int port)
	{
		this.port = port;
				
		try
		{
			this.myAddress = InetAddress.getLocalHost();
			this.socket = new DatagramSocket(port, this.myAddress);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
		
		Thread receiveThread = new Thread(
				new Runnable() {
					public void run() {
						receiveThread();
					}
				});
		receiveThread.setName("Receive Thread For Port = " + this.port);
		receiveThread.start();	
		
	}
		
	
	public void receiveThread() 
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
				
			}			
			
			
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
	 * 
	 * @return the incoming Ip from the user send 
	 * MSN to me
	 */
	
	public String getIncomingIp()
	{
		return this.reciverIp;
	}
	
	/**
	 * 
	 * @return get the port Number From the 
	 * user sending MSN to me.
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
	private String getIpPortKey(String Ip, String port)
	{
		return "Ip:"+ this.Ip + "Port:"+ this.port;
	}
	
		
	public void dataInsideHasMap()
	{
		System.out.println("insideHashMap of port " + port);
		for(String str : hashMapDataHolder.keySet()) 
		{
			System.out.println("keys(Ip&Port) : " + str + "  value(Window)"	+ hashMapDataHolder.get(str).toString());
			
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
	}
	
}
