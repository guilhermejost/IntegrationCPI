import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import java.util.Date;
import java.text.SimpleDateFormat;

def Message processData(Message message) {
    
    def messageLog = messageLogFactory.getMessageLog(message);
    def propertyMap = message.getProperties();
    
    // Read SessionID from the Property 
    String sessionID = propertyMap.get("access_token_acheDocs");
    messageLog.setStringProperty("Info4", "Get access_token: " + sessionID);
    
     if ( (sessionID==null) || (sessionID=="") ){
	  	 sessionValid = "false";
         message.setProperty("sessaoValida",sessionValid);
         messageLog.setStringProperty("03 ACHE LOG sessaoValida: ", sessionValid);
         return message;
    }
    
     //Read sessionTime from the Properties
    String sessionTime = propertyMap.get("last_execution_acheDocs");
    messageLog.setStringProperty("01 ACHE LOG tokenTime: ", sessionTime);
    
     if ( (sessionTime==null) || (sessionTime=="") ){
	     sessionValid = "false";
         message.setProperty("sessaoValida",sessionValid);
         messageLog.setStringProperty("03 ACHE LOG sessaoValida: ", sessionValid);
         return message;
   }
   
  // Set Current Date Properties
   SimpleDateFormat datahora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   message.setProperty("datahora",datahora);
   
  
  Calendar cal1 = Calendar.getInstance();
  SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
  cal1.setTime(sdf1.parse(sessionTime));
  cal1.add(Calendar.MINUTE, 60);
  
  
 // Get Current Time
  SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");//dd/MM/yyyy
  Date now = new Date();
  
  String sessionValid = "";
  
  // Validate if session is Valid
  if(now.before(cal1.getTime())){
    sessionValid = "true";	  
  }else{
    sessionValid = "false";
  }
  
  message.setProperty("sessaoValida",sessionValid);
//   message.setProperty("sessaoValida","false");//teste...remover
  messageLog.setStringProperty("03 ACHE LOG sessaoValida: ", sessionValid);
  return message;
   
}