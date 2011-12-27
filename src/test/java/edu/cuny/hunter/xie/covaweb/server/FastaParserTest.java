package edu.cuny.hunter.xie.covaweb.server;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.biojava3.core.sequence.ProteinSequence;
import org.junit.Test;

import edu.cuny.hunter.xie.covaweb.server.parsers.FastaParser;

public class FastaParserTest {
  @Test
  public void showThatFastaToProteinSequenceParserWorks()
      throws IllegalArgumentException {
    ProteinSequence temp = FastaParser
        .getProteinSequenceFromFasta(">foo\nMGT\nWTYF");
    assertEquals("MGTWTYF", temp.getSequenceAsString());
    
  }
  
  @Test
  public void showThatFastaFileParserWorks() throws IllegalArgumentException,
      IOException {
    ProteinSequence temp = FastaParser.getProteinSequenceFromFasta(new File(
        getClass().getClassLoader().getResource("NP_001096969.fa").getFile()));
    
    assertEquals(temp.getSequenceAsString(),
        ("malypsssrprtlmeqllarkieaaaaaggapsaatatptasgsatptpsptspnppavg"
            + "gmglpmpltlglglglgmgmgvvsrlrrtdsldstsslgslafgedvcrcddcllgivdl"
            + "yvisaaeaakkkssgwrkirnivqwtpffqtykkqrypwvqlaghqgnfkagpepgtvlk"
            + "klcpkeeecfqilmhdllrpyvpvykgqvtsedgelylqlqdllsdyvqpcvmdckvgvr"
            + "tyleeelskakekpklrkdmydkmiqidshaptaeehaakavtkprymvwretisstatl"
            + "gfriegikksdgtsskdfkttksreqiklafleflsghphilpryiqrlrairatlavse"
            + "ffqthevigssllfvhdqthasiwlidfaktvelppqlridhysawkvgnhedgyligin"
            + "nlidifvelqasmeaeahaqaqaeaiqspvsgsggdqaeqtgeeskp").toUpperCase());
  }
  
}
