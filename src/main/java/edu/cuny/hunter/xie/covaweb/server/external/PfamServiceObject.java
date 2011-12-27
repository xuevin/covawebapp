package edu.cuny.hunter.xie.covaweb.server.external;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class PfamServiceObject {
  
  private String hmm;
  
  public PfamServiceObject(InputStream stream) throws IllegalArgumentException,
      IOException {
    parse(stream);
    
  }
  
  private void parse(InputStream inputStream) throws IllegalArgumentException,
      IOException {
    final HashMap<String,String> xmlKeyValues = new HashMap<String,String>();
    
    SAXParserFactory factory = SAXParserFactory.newInstance();
    
    SAXParser saxParser;
    try {
      saxParser = factory.newSAXParser();
      
      DefaultHandler handler = new DefaultHandler() {
        StringBuilder tempValue = new StringBuilder();
        
        @Override
        public void startElement(String uri, String localName, String qName,
            Attributes attributes) {
          if (qName.equals("hmm")) {
            tempValue = new StringBuilder();
          }
        }
        
        @Override
        public void characters(char[] ch, int start, int length)
            throws SAXException {
          tempValue.append(new String(ch, start, length));
        }
        
        @Override
        public void endElement(String uri, String localName, String qName)
            throws SAXException {
          if (qName.equals("hmm")) {
            xmlKeyValues.put("hmm", tempValue.toString().trim());
          }
        }
      };
      
      saxParser.parse(inputStream, handler);
      
      setHmm(xmlKeyValues.get("hmm"));
      
    } catch (ParserConfigurationException e) {
      throw new IllegalArgumentException(e);
    } catch (SAXException e) {
      throw new IllegalArgumentException(e);
    }
  }
  
  public void setHmm(String hmm) {
    this.hmm = hmm;
  }
  
  public String getHmm() {
    return hmm;
  }
  
}
