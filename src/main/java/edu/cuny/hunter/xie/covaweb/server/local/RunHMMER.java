package edu.cuny.hunter.xie.covaweb.server.local;

import org.biojava3.core.sequence.MultipleSequenceAlignment;
import org.biojava3.core.sequence.ProteinSequence;
import org.biojava3.core.sequence.compound.AminoAcidCompound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.cuny.hunter.xie.covaweb.server.parsers.StockholmParser;

public class RunHMMER {
  private String hmmerDir;
  private MultipleSequenceAlignment<ProteinSequence,AminoAcidCompound> alignment;
  Logger logger = LoggerFactory.getLogger(RunHMMER.class);
  
  public RunHMMER(String hmmerDir) {
    this.hmmerDir=hmmerDir;
  }

  public void run(String hmmPath, String unalignedSequences) {
    try {
      
      Process p = Runtime.getRuntime().exec(hmmerDir + "/hmmalign " + hmmPath + " " + unalignedSequences);
      
      alignment = StockholmParser.getMSA(p.getInputStream());
      
      logger.debug("The alignment is as follows:\n{}",alignment.toString());
    } catch (Exception err) {
      err.printStackTrace();
    }
  }
  public MultipleSequenceAlignment<ProteinSequence,AminoAcidCompound> getMSA(){
    return alignment;
  }
}
