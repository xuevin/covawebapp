package edu.cuny.hunter.xie.covaweb.server.parsers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;

import org.biojava3.core.sequence.AccessionID;
import org.biojava3.core.sequence.MultipleSequenceAlignment;
import org.biojava3.core.sequence.ProteinSequence;
import org.biojava3.core.sequence.compound.AminoAcidCompound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StockholmParser {
  static Logger logger = LoggerFactory.getLogger(StockholmParser.class);
  
  /**
   * Takes in an input stream and returns a MultipleSequenceAlignment object
   * 
   * @param stream
   *          input stream to parse
   * @return a multiple sequence alignment object
   * @throws IOException
   */
  public static MultipleSequenceAlignment<ProteinSequence,AminoAcidCompound> getMSA(
      InputStream stream) throws IOException {
    logger.debug("Creating MSA from an InputStream");
    
    LinkedHashMap<String,String> labelToSequence = getSequencesAsLinkedHashMap(stream);
    //TODO slightly inefficient considering that the list is looped through twice
    
    MultipleSequenceAlignment<ProteinSequence,AminoAcidCompound> msa = new MultipleSequenceAlignment<ProteinSequence,AminoAcidCompound>();
    for (String key : labelToSequence.keySet()) {
      ProteinSequence temp = new ProteinSequence(labelToSequence.get(key).toUpperCase());
      temp.setAccession(new AccessionID(key));
      msa.addAlignedSequence(temp);
      //      
      // System.out.println(key + "\t" + labelToSequence.get(key));
    }
    return msa;
    
  }
  
  public static LinkedHashMap<String,String> getSequencesAsLinkedHashMap(
      InputStream stream) throws IOException {

    LinkedHashMap<String,String> labelToSequence = new LinkedHashMap<String,String>();
    
    String line;
    BufferedReader input = new BufferedReader(new InputStreamReader(stream));
    while ((line = input.readLine()) != null) {
      if (line.startsWith("#") || line.isEmpty() || line.startsWith("//")) {
        // skip
      } else {
        String[] array = line.split("\\s+");
        if (labelToSequence.containsKey(array[0])) {
          String temp = labelToSequence.get(array[0]);
          labelToSequence.put(array[0], temp.concat(array[1]));
        } else {
          labelToSequence.put(array[0], array[1]);
        }
      }
    }
    input.close();
    return labelToSequence;
  }
}
