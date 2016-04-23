//@nottocode..
//ChatClient
import java.util.*;
import java.io.*;
import java.net.*;

public class ChatClient
{	
  Socket socket;
  DataInputStream din;
  DataOutputStream dout;
  String username="";	
  int portnum=5217;
  String send="",receive="";	
  Vector <String> vec=new Vector<String>();	  

  public ChatClient(String s,String name) throws Exception
	{	try
		{ System.out.println("Trying to connect to "+ s +"on port number "+portnum); 
		  socket=new Socket(s,portnum);
		  System.out.println("Connected to "+socket.getRemoteSocketAddress());
		  din=new DataInputStream(socket.getInputStream());
		  dout=new DataOutputStream(socket.getOutputStream());
		  username=name;	
		  //dout.writeUTF("Hello from "+socket.getLocalSocketAddress());
		  dout.writeUTF(name);	
        }
		catch(Exception e)
		{	e.printStackTrace();
			//System.out.println("Unable to connect to Server");
		}
	}

  public void run() throws Exception
	{	Scanner s =new Scanner(System.in);
			
		Thread thread =new Thread(new Runnable(){
			public void run(){					
					while(true)
					{	try{
                           receive=din.readUTF();
			               vec.add(receive);
                           if(vec.size()>0){
							   //System.out.println("Received message :"+vec.get(0));
								System.out.println(vec.get(0));							   
								vec.remove(0);
						     }
                           }
						catch(Exception e)
						{	//e.printStackTrace();
							System.out.println("Unable to connect to Server");
						}					
					}
			}
       });
	   thread.start();	
	
		
		
				
		while(true)
			{ //receive=din.readUTF();
			  //vec.add(receive);
			  send=s.nextLine();
			  //System.out.println("Sending this message to server : " + send);	
			  if(send.equals("Exit")||send.equals("EXIT")||send.equals("LOGOUT")||send.equals("logout")||send.equals("exit")||send.equals("Logout"))
				{   dout.writeUTF("logged out.");
					System.exit(0);
				}	
			  dout.writeUTF(send);	
			
			}

	}	

  public static void main(String args[]) throws Exception
	{ ChatClient obj=new ChatClient(args[0],args[1]);
      obj.run();
	}
	
}

