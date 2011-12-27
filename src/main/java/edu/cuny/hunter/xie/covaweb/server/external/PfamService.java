package edu.cuny.hunter.xie.covaweb.server.external;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.biojava3.core.sequence.ProteinSequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;

import edu.cuny.hunter.xie.covaweb.server.parsers.StockholmParser;

public class PfamService {
  
  static Logger logger = LoggerFactory.getLogger(PfamService.class);
  
  public static PfamServiceObject getPfamServiceObj(ProteinSequence sequence)
      throws IOException, IllegalArgumentException {
    
    PfamServiceObject obj = getPfamServiceObjFromResultURL(getResultURLFromInputSteam(submitSequence(sequence)));
    
    return obj;
  }
  
  public static LinkedHashMap<String,String> getListOfSeedSequences(
      String accession) throws IOException {
    URL unalignedSeqUrl = new URL("http://pfam.sanger.ac.uk/family/"
        + accession + "/alignment/seed/format?format=stockholm&gaps=none");
    
    HttpURLConnection connection = (HttpURLConnection) unalignedSeqUrl
        .openConnection();
    connection.setRequestMethod("GET");
    connection.setDoOutput(true);
    // System.out.println(CharStreams.toString(new
    // InputStreamReader(connection.getInputStream())));
    
    // TODO the current way you are working is not very safe. Consider making
    // new objects so that your
    // understanding is solid.
    
    return StockholmParser.getSequencesAsLinkedHashMap(connection
        .getInputStream());
    
  }
  
  private static PfamServiceObject getPfamServiceObjFromResultURL(
      URL resultURLFromResultStream) throws IOException,
      IllegalArgumentException {
    // TODO - The sleep in this thread might stall the application... Come back
    
    HttpURLConnection connection = (HttpURLConnection) resultURLFromResultStream
        .openConnection();
    try {
      System.out.println(resultURLFromResultStream);
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    // System.out.println(CharStreams.toString(new InputStreamReader(connection
    // .getInputStream())));
    
    InputStream inputStream = connection.getInputStream();
    
    return new PfamServiceObject(inputStream);
    
  }
  
  private static URL getResultURLFromInputSteam(InputStream inputStream) {
    
    // try {
    // System.out.println(CharStreams.toString(new
    // InputStreamReader(inputStream)));
    // } catch (IOException e1) {
    // // TODO Auto-generated catch block
    // e1.printStackTrace();
    // };
    
    final HashMap<String,String> xmlKeyValues = new HashMap<String,String>();
    try {
      SAXParserFactory factory = SAXParserFactory.newInstance();
      SAXParser saxParser = factory.newSAXParser();
      
      DefaultHandler handler = new DefaultHandler() {
        String tempValue;
        
        @Override
        public void startElement(String uri, String localName, String qName,
            Attributes attributes) {
          if (qName.equals("result_url")) {
            tempValue = new String();
          }
        }
        
        public void characters(char[] ch, int start, int length)
            throws SAXException {
          tempValue = new String(ch, start, length);
        }
        
        public void endElement(String uri, String localName, String qName)
            throws SAXException {
          if (qName.equals("result_url")) {
            xmlKeyValues.put("result_url", tempValue);
          }
        }
        
      };
      saxParser.parse(inputStream, handler);
      
      return new URL(xmlKeyValues.get("result_url"));
      
    } catch (ParserConfigurationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (SAXException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }
  
  private static InputStream submitSequence(ProteinSequence sequence)
      throws UnsupportedEncodingException, IOException {
    HttpURLConnection connection = (HttpURLConnection) new URL(
        "http://pfam.sanger.ac.uk/search/sequence").openConnection();
    connection.setRequestMethod("POST");
    connection.setDoOutput(true);
    connection.setDoInput(true);
    connection.getOutputStream().write(
        ("output=xml&seq=" + sequence.getSequenceAsString()).getBytes("UTF-8"));
    return connection.getInputStream();
  }
  
}
