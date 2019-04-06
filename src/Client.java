import java.util.ArrayList;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Client extends AbstractActor{
	private ArrayList<ActorRef> servers;
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	private int receivetime =0;
	Client(ArrayList<ActorRef> s){
		this.servers = s;
	}
	
	
	static public Props props(ArrayList<ActorRef> s) {
        return Props.create(Client.class, () -> new Client(s));
        
    }

	
	@Override
	public Receive createReceive() {
		return this.receiveBuilder()
				.match(Message.Usermsg.class, msg->{this.urmsgReceive(msg);})
				.match(Message.Sermsg.class, msg->{this.semsgReceive(msg);})
				.build();
	}
	
	//get msg from the servers
	private void semsgReceive(Message.Sermsg msg) {
		this.receivetime ++;
		//log.info("got msg from server");
		String sername = this.getSender().path().name();
		System.out.println(sername + " said: " + msg.status);
		
//		if(this.receivetime == 3) {
//			this.getContext().getSystem().terminate();
//		}
	}


	//get msg from the main
	private void urmsgReceive(Message.Usermsg msg) {
		//log.info("got msg from user");
		for (ActorRef s : this.servers){
			Message.Usermsg message = msg;
			s.tell(message, self());
		}
	}


	
	
	
	
	
	
	
}
