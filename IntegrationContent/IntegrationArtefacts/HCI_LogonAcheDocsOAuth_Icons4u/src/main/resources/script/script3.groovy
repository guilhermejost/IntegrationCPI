import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.cxf.binding.soap.SoapHeader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import groovy.util.XmlSlurper;
import groovy.json.JsonSlurper;
import groovy.json.JsonOutput;
import groovy.json.JsonBuilder;
import java.util.HashMap;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;


def Message processData(Message message) {
    
    def messageLog = messageLogFactory.getMessageLog(message);
    def propertyMap = message.getProperties();
    def payload = message.getBody(java.lang.String);
    def body = new JsonSlurper().parseText( payload );
    
    def status = "success";
    def access_token = propertyMap.get("access_token_acheDocs")
    messageLog.addAttachmentAsString("access_token", access_token, "text/plain");
    
    def defaultMessage = '{}';
  	def defaultResponse = new JsonSlurper().parseText( defaultMessage );
  	def data = [];
  	
  	status = body.status;
  	
  	if(status == "success") {
  	    data.push("access_token": access_token)
  	    defaultResponse.statusCode = "200";
  	    defaultResponse.success = true;
  	    defaultResponse.data = data;
  	} else {
  	     data.push("field": "Falha ao recuperar o Token", "details": "Nao foi possivel recuperar o token. Validar os logs (HCI_LogonAcheDocsOAuth)");
  	     
  	     defaultResponse.statusCode = "404";
  	     defaultResponse.success = false;
  	     defaultResponse.date = new Date();
  	     defaultResponse.errors = data;
  	     
  	}
  	
  	message.setHeader("CamelHttpResponseCode", defaultResponse.statusCode)
  	String payloadResponse = new JsonBuilder(defaultResponse).toPrettyString();
  	message.setBody(payloadResponse)
  	
  	messageLog.addAttachmentAsString("responseHCI", payloadResponse, "text/plain");
  	
  	return message;
    
}