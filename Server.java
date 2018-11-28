// A Java program for a Server
import java.net.*;
import java.io.*;

public class Server
{
	//initialize socket and input stream
	private Socket		 socket = null;
	private ServerSocket server = null;
	private DataInputStream in	 = null;
	private DataOutputStream out = null;

	// constructor with port
	public Server(int port)
	{
		// starts server and waits for a connection
		try
		{
			server = new ServerSocket(port);
			System.out.println("Server started");

			System.out.println("Waiting for a client ...");

			socket = server.accept();
			System.out.println("Client accepted");

			// takes input from the client socket
			in = new DataInputStream(
				new BufferedInputStream(socket.getInputStream()));
			out = new DataOutputStream(socket.getOutputStream());
			String line = "";
             
			
			int window=Integer.parseInt(in.readUTF());
			System.out.println("Value of window:"+window);
			int frames=Integer.parseInt(in.readUTF());
			System.out.println("Value of frames:"+frames);
			boolean loop=true;
			int sent=1;
			// reads message from client until "Over" is sent
			
			while(loop)
			  {
				out.writeUTF("Window start");
				  for(int i=0;i<window;i++)
				  {
					  System.out.println("Frame "+ sent+" sent");
					  out.writeUTF(String.valueOf(sent));
					  if(sent==frames)
						  break;
					  
					  sent++;
				  }
				  
				  out.writeUTF("Window end");
				  try
				  {
				  Thread.sleep(5000);
				  }
				  catch(Exception e){}
				  System.out.println("Enter the first frame that got corrupted:");
				  int corr=Integer.parseInt(in.readUTF());
				  System.out.println(corr);
				  
				  if(corr!=0)
					  sent=corr;
				  else if(corr==0 && sent==frames)
					  loop=false;
				 
			  }
			out.writeUTF("Stop");
			System.out.println("Closing connection");

			// close connection
			socket.close();
			in.close();
		}
		catch(IOException i)
		{
			System.out.println(i);
		}
	}

	public static void main(String args[])
	{
		Server server = new Server(7065);
	}
}
