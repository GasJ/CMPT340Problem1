public class Message {
	
	
	static public class Usermsg{
		public String filename;
		public String command;
		public String content;
		
		Usermsg(String fn, String cm, String con){
			this.filename = fn;
			this.command = cm;
			this.content = con;
		}
	}
	
	static public class Sermsg{
		public String status; //whether has the file
//		public String sername; //the name of server
		
		Sermsg(String st){
			this.status = st;
		}
	}
	
	
	
//	public String getname() {
//		return this.filename;
//	}
//	
//	public String getcm() {
//		return this.command;
//	}
//	
//	public String getcont() {
//		return this.content;
//	}
}
