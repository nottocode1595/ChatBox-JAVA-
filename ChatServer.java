//@nottocode..
//chat server
import java.util.*;
import java.net.*;
import java.io.*;

public class ChatServer
{ static Vector clientsockets;
  static Vector clientnames;

  public ChatServer() throws Exception
  { ServerSocket soc=new ServerSocket(5217);
    clientsockets=new Vector();
    clientnames=new Vector();

	while(true)
    { Socket socket=soc.accept();
	  Acceptclient ob=new Acceptclient(socket);	
	}

  }

  public static void main(String args[]) throws Exception
  { ChatServer obj=new ChatServer();

  }

  class Acceptclient extends Thread 
  { Socket clientsocket;
    DataInputStream din;
  	DataOutputStream dout;
    Vector <String> newmessage=new Vector<String>();
	Vector <String> oldmessage=new Vector<String>();	
	String loginname;

 	Acceptclient(Socket s) throws Exception
  	{ clientsocket =s;
	  din=new DataInputStream(clientsocket.getInputStream());
	  dout=new DataOutputStream(clientsocket.getOutputStream());

	  loginname=din.readUTF();
	  System.out.println("User logged in :"+loginname);	 
   	  clientnames.add(loginname);	
	  clientsockets.add(clientsocket);
	  dout.writeUTF("Welcome to the ChatBox "+ loginname);
	  dout.flush();	
	  dout.writeUTF("Number of users logged in are :" + clientnames.size());		
	  start(); 	  

	}

	public void run()
	{ //	
		int x=0;
			for(x=0;x<clientsockets.size();x++)
  					{ if(!clientsockets.elementAt(x).equals(clientsocket))
							{  try{
									 Socket tsocket =(Socket)clientsockets.elementAt(x);
			  						 DataOutputStream dout=new DataOutputStream(tsocket.getOutputStream());
									 dout.flush();
									 dout.writeUTF(loginname+" "+"logged in.");
									 dout.writeUTF("Number of users logged in :" + (clientsockets.size()));
									 System.out.println("server sends :"+oldmessage.get(0));	
									 System.out.println("User logged out");
    								}
								catch(Exception e)
									{	e.printStackTrace();
									}																					
							}
					}
 		/*	String loggednames="";
            for(x=0;x<clientnames.size();x++)
				{ String temp=clientnames.get(x);
				  loggednames+=temp+" ";	
				}	
           

            for(x=0;x<clientsockets.size();x++)
  					{ //if(!clientsockets.elementAt(x).equals(clientsocket))
							{  try{
									 Socket tsocket =(Socket)clientsockets.elementAt(x);
			  						 DataOutputStream dout=new DataOutputStream(tsocket.getOutputStream());
									 dout.flush();
									 //dout.writeUTF(loginname+" "+"logged in.");
									 //dout.writeUTF("Number of users logged in :" + (clientsockets.size()));
									 dout.writeUTF("Users logged in are : "+loggednames);	
									 System.out.println("server sends :"+oldmessage.get(0));	
									 System.out.println("User logged out");
    								}
								catch(Exception e)
									{	e.printStackTrace();
									}																					
							}
					}
      */
	 //
		Thread thread1 =new Thread(new Runnable() {
			public void run()
				{	while(true){
					if(newmessage.size()>0)					
					{	System.out.println(newmessage.get(0));
						newmessage.remove(0);					 
					}                   
				}   					
				}	
			});
		thread1.start();

		Thread thread2 =new Thread(new Runnable(){
			public void run() 
				{	while(true){
						if(oldmessage.size()>0)
							{	String xyz=oldmessage.get(0);
								System.out.println("xyz =" + xyz);
								/*int a=1;
								if(xyz=="logged out")
									a=2;
								else
									a=3;
								System.out.println("a=" + a);*/
								if(!xyz.equals("logged out.")){
								int count=0;
								for(count=0;count<clientsockets.size();count++)
				   					{ //if(!clientsockets.elementAt(count).equals(clientsocket))
										{  try{
											 Socket tsocket =(Socket)clientsockets.elementAt(count);
					  						 DataOutputStream dout=new DataOutputStream(tsocket.getOutputStream());
											 dout.flush();
											 //dout.writeUTF("Number of users logged in :" + clientsockets.size());
					  						 dout.writeUTF(loginname+": "+oldmessage.get(0));
											 System.out.println("server sends :"+oldmessage.get(0));	
 											}
											catch(Exception e)
											{	e.printStackTrace();
											}																					
										}
									}
								oldmessage.remove(0);
								}
								else{
								int count=0;
								int note=0;
								for(count=0;count<clientsockets.size();count++)
				   					{ if(!clientsockets.elementAt(count).equals(clientsocket))
										{  try{
											 Socket tsocket =(Socket)clientsockets.elementAt(count);
					  						 DataOutputStream dout=new DataOutputStream(tsocket.getOutputStream());
											 dout.flush();
											 dout.writeUTF(loginname+" "+oldmessage.get(0));
											 dout.writeUTF("Number of users logged in :" + (clientsockets.size()-1));
											 System.out.println("server sends :"+oldmessage.get(0));	
											 System.out.println("User logged out");
 											}
											catch(Exception e)
											{	e.printStackTrace();
											}																					
										}
										else
										{	note=count;
											//clientsockets.remove(count);
											//clientnames.remove(count);
										}
									}
								clientsockets.remove(note);
								clientnames.remove(note);
								oldmessage.remove(0);
								//int size1=clientsockets.size();
								//int size2=clientnames.size();
								//System.out.println("Size1="+size1+" " + "size2="+size2);
								}	
							}							
					}
				}
			});
		thread2.start();

   
		while(true)
		{ 
          try
		  {
 		   //String newmessage=new String();
		   String message=din.readUTF();
		   newmessage.add(message);
		   oldmessage.add(message);
		   //System.out.println(newmessage);	

           /*int var=0;
		   for(var=0;var<clientsockets.size();var++)
				{ System.out.println(clientsockets.elementAt(var));
				}				
		   */
		   /*int count=0;
		   if(message.equals("LOGOUT"))
			{ for(count=0;count<clientnames.size();count++)
				{ if(clientsockets.elementAt(count).equals(clientsocket))
  					{ clientnames.removeElementAt(count);
					  clientsockets.removeElementAt(count);
					  System.out.println("User with socket :"+ clientsocket + "logged out");	
					  break;
					}
				}
			} 
		  else
			{ for(count=0;count<clientsockets.size();count++)
				{ if(!clientsockets.elementAt(count).equals(clientsocket))
					{ Socket tsocket =(Socket)clientsockets.elementAt(count);
					  DataOutputStream dout=new DataOutputStream(tsocket.getOutputStream());
					  dout.writeUTF(message);													
					}
				}
			}*/
    	  }
		  catch(Exception exep)
			{ //exep.printStackTrace();
				System.out.println("Thread stopped");
			}	 
        }
	 }
  }
}
