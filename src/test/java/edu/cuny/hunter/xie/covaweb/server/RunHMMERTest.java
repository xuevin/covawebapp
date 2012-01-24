package edu.cuny.hunter.xie.covaweb.server;

import org.junit.Test;

import edu.cuny.hunter.xie.covaweb.server.local.RunHMMER;


public class RunHMMERTest {
  
  @Test
  public void exectuteTest(){
    String hmmPath = getClass().getClassLoader().getResource("foo.hmm").getPath();
    String unalignedSeqFile = getClass().getClassLoader().getResource("PF03770_seed_wQuery.unaligned").getPath();
    new RunHMMER("/home/chrnovx/Applications/hmmer-3.0-linux-intel-x86_64/binaries").run(hmmPath,unalignedSeqFile);
  }
}
