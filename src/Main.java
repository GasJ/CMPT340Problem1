import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("File System Start.");
		final ActorSystem system = ActorSystem.create("FileSystem");
//		//create servers
		final ActorRef server1 = system.actorOf(Server.props("file1"), "server1");
		final ActorRef server2 = system.actorOf(Server.props("file2"), "server2");
		final ActorRef server3 = system.actorOf(Server.props("file3"), "server3");
//		System.out.println(server1.path());
		ArrayList<ActorRef> servers = new ArrayList<ActorRef>();
		servers.add(server1);
		servers.add(server2);
		servers.add(server3);
//		create client map to manage clients
		Map<String, ActorRef> clients = new HashMap<String, ActorRef>(); 
		//get input from user
		while(true) {
	        String[] userinputs = null;
	        Scanner input= new Scanner(System.in);
	        //check illegal
	        while(userinputs == null || userinputs.length != 3) {
	        	System.out.print("client_name + file name + command: ");
		        String name_command = input.nextLine();
		        userinputs = name_command.split(" ");
	        }
	        
	        String wthings = "";
	        if(userinputs[2].equals("write") || userinputs[2].equals("Write")) {
	        	System.out.println("Things you want to write plz: ");
	        	wthings = input.nextLine();
	        	System.out.println(wthings);
	        }
	        //make it to message
	        Message.Usermsg msg = new Message.Usermsg(userinputs[1],userinputs[2],wthings);
	        
	        //check if the client is exist
	        if(clients.get(userinputs[0]) == null) {
	        	//not exist, create new client
	        	//create clients
//		        final ActorRef client = system.actorOf(Client.props(servers), "client");
	        	ActorRef client = system.actorOf(Client.props(servers), userinputs[0]);
	        	clients.put(userinputs[0], client);
	        	//sent msg to it
		        client.tell(msg, ActorRef.noSender());
	        }else {
	        	//exist, tell the client the message
	        	clients.get(userinputs[0]).tell(msg, ActorRef.noSender());
	        }
	        
	        
	        
	        
		}
	}

}
