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
    
    def body = message.getBody(java.lang.String);
    def response = new JsonSlurper().parseText( body );
    
    if (response.status == "success") {
        message.setProperty("access_token_acheDocs", response.access_token );
        message.setProperty("erroToken", false);
    } else {
        message.setProperty("erroToken", true); 
    }
    
    return message;
}