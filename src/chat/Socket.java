package chat;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Socket {

	private InetAddress myAddress;
	private int port;
	private DatagramSocket socket = null;
	private ConcurrentLinkedQueue<DatagramPacket> messageQueue = new ConcurrentLinkedQueue<DatagramPacket>();
	
	
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
			//inPacket.getAddress();//this is where it cames from. 
			//inPacket.getPort();this is the port where the msn it came from.
			System.out.println("ReceiveThread - Message on port = " + this.port + 
					" message = " + message + "\n" +
					" From IP = " + inPacket.getAddress() + 
					" From Port = " + inPacket.getPort());
			messageQueue.add(inPacket);
		} while(true);
	}
	
	public DatagramPacket receive() {
		return messageQueue.poll();
	}
	
	
public void send(String s, InetAddress destinationIP, int destinationPort) {
		
		byte[] outBuffer;
		
		try {
			outBuffer = s.getBytes();
			DatagramPacket outPacket = new DatagramPacket(outBuffer, s.length());
			outPacket.setAddress(destinationIP);
			outPacket.setPort(destinationPort);
			socket.send(outPacket);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
}
