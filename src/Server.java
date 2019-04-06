import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import akka.actor.AbstractActor;
import akka.actor.Props;

public class Server extends AbstractActor{
	private String filename;
	private Boolean ocupied;
//	private String selfname;
	
	Server(String fn){
		this.filename = fn;
//		this.selfname = sf;
		this.ocupied = false;
	}
	
	static public Props props(String fn) {
        return Props.create(Server.class, () -> new Server(fn));
    }

	@Override
	public Receive createReceive() {
		// TODO Auto-generated method stub
		return this.receiveBuilder()
				.match(Message.Usermsg.class, msg->{this.urmsgReceive(msg);})
				.build();
	}

	private void urmsgReceive(Message.Usermsg msg) throws IOException {
//		System.out.println("server recieved msg");
		// TODO Auto-generated method stub
		//whether has the file
		if(msg.filename.equals(this.filename)) {
//			if(ocupied) {
//				this.getSender().tell(new Message.Sermsg("ocupied"), self());
//			}
			switch(msg.command) {
			//if yes, check the command
			//if command is legal, do it
			case "open":
				if(ocupied) {
					this.getSender().tell(new Message.Sermsg("cannot open opened file"), self());
				}else {
					this.ocupied = true;
					this.getSender().tell(new Message.Sermsg(this.filename + "opened"), self());
				}
				break;
			case "read":
				if(ocupied) {
					String content = getFileContent();
					this.getSender().tell(new Message.Sermsg(content), self());
				}else {
					this.getSender().tell(new Message.Sermsg(this.filename + "is not opened, cannot read. you need to open it first."), self());
				}
				break;
			case "write":
				if(ocupied) {
					String content = msg.content;
					writeToFile(content);
					this.getSender().tell(new Message.Sermsg("wrote to the file."), self());
				}else {
					this.getSender().tell(new Message.Sermsg(this.filename + "is not opened, cannot write. you need to open it first."), self());
				}
				break;
			case "close":
				if(ocupied) {
					this.ocupied = false;
					this.getSender().tell(new Message.Sermsg("file closed now"), self());
				}else {
					this.getSender().tell(new Message.Sermsg(this.filename + "is not opened, cannot close. you need to open it first."), self());
				}
				break;
			default:
				//else command is illegal, tell the client
				this.getSender().tell(new Message.Sermsg("This is not a legal command, sorry, bye."), self());
			}
		}else {
			//else, not has the file
			this.getSender().tell(new Message.Sermsg("I do not have your file, sorry, bye."), self());
		}
	}

	
	//helper to write content to the file
	private void writeToFile(String content) throws IOException {
		// TODO Auto-generated method stub
		String existContent = getFileContent();
		String inputContent = content;
		//check the file have contents or not
		if(existContent != null) {
			inputContent = existContent + content;
		}
		BufferedWriter out = new BufferedWriter(new FileWriter("src/" + filename + ".txt"));
		out.write(inputContent);
		out.close();

	}

	//helper to get content from the file
	private String getFileContent() throws IOException {
		File file = new File("src/" + filename + ".txt"); 
		  
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		  
		String st; 
		st = br.readLine();
		return st;
	}
	
	

}
