import javax.microedition.io.Connector;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.TextMessage;


public class report {
	MessageConnection conn;
	public report(String addr, String msg){
		try{
			
		
		conn = (MessageConnection) Connector.open(addr);
		
		TextMessage text = (TextMessage) conn.newMessage(MessageConnection.TEXT_MESSAGE);
		text.setAddress(addr);
		text.setPayloadText(msg);
		conn.send(text);
		conn.close();
		}
		catch (Exception e){
			System.out.println(e.toString());
		}
	}

}
