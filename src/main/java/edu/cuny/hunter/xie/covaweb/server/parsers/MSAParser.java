package edu.cuny.hunter.xie.covaweb.server.parsers;

import java.util.StringTokenizer;

import org.biojava3.core.sequence.AccessionID;
import org.biojava3.core.sequence.MultipleSequenceAlignment;
import org.biojava3.core.sequence.ProteinSequence;
import org.biojava3.core.sequence.compound.AminoAcidCompound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MSAParser {
  /**
   * Parses a simple interleaved alignment file into a MultipleSequenceAlignment
   * 
   * @param string
   *          a string version of the MSA
   * @return a MultipleSequensceAlignment
   */
  
  static Logger logger = LoggerFactory.getLogger(MSAParser.class);

  public static MultipleSequenceAlignment<ProteinSequence,AminoAcidCompound> getMSA(
      String string) {
    logger.debug("Parsing Multiple Sequence Alignment from a String");
    
    MultipleSequenceAlignment<ProteinSequence,AminoAcidCompound> msa = new MultipleSequenceAlignment<ProteinSequence,AminoAcidCompound>();
    
    StringTokenizer tokenizer = new StringTokenizer(string);
    while (tokenizer.hasMoreTokens()) {
      String identifier = tokenizer.nextToken();
      ProteinSequence sequence = new ProteinSequence(tokenizer.nextToken()
          .toUpperCase());
      sequence.setAccession(new AccessionID(identifier));
      msa.addAlignedSequence(sequence);
    }
    return msa;
    
  }
  
}
