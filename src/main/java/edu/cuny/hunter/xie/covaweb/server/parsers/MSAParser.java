package edu.cuny.hunter.xie.covaweb.server.parsers;

import java.util.StringTokenizer;

import org.biojava3.core.sequence.AccessionID;
import org.biojava3.core.sequence.MultipleSequenceAlignment;
import org.biojava3.core.sequence.ProteinSequence;
import org.biojava3.core.sequence.compound.AminoAcidCompound;

public class MSAParser {
  /**
   * Parses a simple interleaved alignment file into a MultipleSequenceAlignment
   * 
   * @param string
   *          a string version of the MSA
   * @return a MultipleSequensceAlignment
   */
  public static MultipleSequenceAlignment<ProteinSequence,AminoAcidCompound> getMSA(
      String string) {
    MultipleSequenceAlignment<ProteinSequence,AminoAcidCompound> msa = new MultipleSequenceAlignment<ProteinSequence,AminoAcidCompound>();
    
    StringTokenizer tokenizer = new StringTokenizer(string);
    while (tokenizer.hasMoreTokens()) {
      String identifier = tokenizer.nextToken();
      ProteinSequence sequence = new ProteinSequence(tokenizer.nextToken()
          .toUpperCase());
      sequence.setOriginalHeader(identifier);
      sequence.setAccession(new AccessionID(identifier));
      msa.addAlignedSequence(sequence);
    }
    return msa;
    
  }
  
}
