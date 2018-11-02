package chat;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Socket {

	private InetAddress myAddress; //Ip 
	private int port; // port
	private DatagramSocket socket = null;
	private ConcurrentLinkedQueue<DatagramPacket> messageQueue = new ConcurrentLinkedQueue<DatagramPacket>();
	public ConcurrentHashMap<String,Window> hashMapDataHolder = new ConcurrentHashMap<String,Window>(); 
	private String MSN = null;
	
	public String inputMsn = null;	
	public String Ip;
	
	
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
		dataInsideHasMap();
		System.out.println("on the receiveThread method");
		
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
			this.MSN = message;
			Ip = inPacket.getAddress().getHostAddress();//this is where it cames from. 
			port = inPacket.getPort();//this is the port where the msn it came from.
			System.out.println("ReceiveThread - Message on port = " + this.port + 
					" message = " + message + "\n" +
					" From IP = " + inPacket.getAddress() + 
					" From Port = " + inPacket.getPort());
			messageQueue.add(inPacket);
			
			//Receiving 
			
			if(hashMapDataHolder.containsKey(getIPandPort())) //check if info package is on the hashMap to link to chat window.
			{
				Window window = hashMapDataHolder.get(getIPandPort()); //getting the window
				window.appendTxtToTextArea(message);
				//window.topPartOfChatTextA.appendText(message);
				
			}
			else 
			{
				Window w = new Window(this);
				//ip , port , window 
				hashMapDataHolder.put(getIPandPort() , w);
				
			}
			
			
			
		} while(true);
	}
	
	public DatagramPacket receive() {
		return messageQueue.poll();
	}
	
	private String getIPandPort()
	{
		return "Ip:"+ this.Ip + "Port:"+ this.port;
	}
	
	
	private void dataInsideHasMap()
	{
		for(String str : hashMapDataHolder.keySet()) 
		{
			System.out.println("keys(Ip&Port) : " + str + "  value(Window)"
								+ hashMapDataHolder.get(str).toString());
			
		}
		
	}
	
	
	
	public void send(String s, InetAddress destinationIP, int destinationPort) {
		
		byte[] outBuffer;
		
		dataInsideHasMap();
		System.out.println("in the send method");
		
		try {
			outBuffer = s.getBytes();
			DatagramPacket outPacket = new DatagramPacket(outBuffer, s.length());
			outPacket.setAddress(destinationIP);
			outPacket.setPort(destinationPort);
			socket.send(outPacket);
			System.out.println("MSN: " + s);
			System.out.println("msn has been send to port : " + destinationPort);
			System.out.println("destinationIp : " + destinationIP);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
}
