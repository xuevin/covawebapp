package edu.cuny.hunter.xie.covaweb.server.local;

import java.io.File;

import org.biojava3.core.sequence.MultipleSequenceAlignment;
import org.biojava3.core.sequence.ProteinSequence;
import org.biojava3.core.sequence.compound.AminoAcidCompound;

import edu.cuny.hunter.xie.covaweb.server.parsers.StockholmParser;

public class RunHMMER {
  
  private static String HMMERDIR = "/home/chrnovx/Applications/hmmer-3.0-linux-intel-x86_64/binaries";
  private MultipleSequenceAlignment<ProteinSequence,AminoAcidCompound> alignment;
  
  public RunHMMER() {

  }
  
  public void run() {
    try {
      // TODO Move these options into a config file
      File hmm = new File(HMMERDIR + "/foo.hmm");
      String unalignedSeqDir = " /home/chrnovx/Dropbox/workspace/covawebapp/src/test/resources/combined_w_seed.unaligned";
      
      Process p = Runtime.getRuntime().exec(
          HMMERDIR + "/hmmalign " + hmm.getAbsolutePath() + unalignedSeqDir);
      
      alignment = StockholmParser.getMSA(p.getInputStream());

    } catch (Exception err) {
      err.printStackTrace();
    }
  }
  public MultipleSequenceAlignment<ProteinSequence,AminoAcidCompound> getMSA(){
    return alignment;
  }
}
