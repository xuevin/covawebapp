package edu.cuny.hunter.xie.covaweb.server;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.biojava.bio.structure.Structure;
import org.biojava3.core.sequence.MultipleSequenceAlignment;
import org.biojava3.core.sequence.ProteinSequence;
import org.biojava3.core.sequence.compound.AminoAcidCompound;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import edu.cuny.hunter.xie.covaweb.server.local.RunHMMER;
import edu.cuny.hunter.xie.covaweb.server.parsers.FastaParser;
import edu.cuny.hunter.xie.covaweb.server.parsers.PDBParser;
import edu.cuny.hunter.xie.covaweb.server.parsers.StockholmParser;

public class MappedSeqTest {
  
  private MappedSeq mappedSeq;

  @Before
  public void setUp() throws Exception {
    MultipleSequenceAlignment<ProteinSequence,AminoAcidCompound> hmmMSA = StockholmParser.getMSA(getClass().getClassLoader().getResourceAsStream("PF03770_seed_wQuery.sto"));
    
    ProteinSequence sequence = FastaParser
        .getProteinSequenceFromFasta(new File(getClass().getClassLoader()
            .getResource("NP_001096969.fa").getPath()));
    Structure protein = PDBParser.getStructureFromPDBFile(new File(getClass()
        .getClassLoader().getResource("NP_001096969.pdb").getPath()));
    
    mappedSeq = new MappedSeq(sequence,protein, hmmMSA);
  }
  
  @Test
  public void testAlignTwoMSAToQuery() throws IOException, IllegalArgumentException {
    
    // Get query aligned to HMM
    RunHMMER runHmmer = new RunHMMER("/home/vxue/Applications/hmmer-3.0-linux-intel-x86_64/binaries");
    String hmmPath = getClass().getClassLoader().getResource("foo.hmm").getPath();
    String unalignedSeqFile = getClass().getClassLoader().getResource("PF03770_seed_wQuery.unaligned").getPath();
    runHmmer.run(hmmPath,unalignedSeqFile);
    
    MultipleSequenceAlignment<ProteinSequence,AminoAcidCompound> hmmMSA = runHmmer
        .getMSA();
    
    // get protein aligned to query
    String string = Files.toString(new File(getClass().getClassLoader()
        .getResource("NP_001096969.fa").getPath()), Charsets.UTF_8);
    ProteinSequence sequence = FastaParser.getProteinSequenceFromFasta(string);
    File pdbFile = new File(getClass().getClassLoader().getResource(
        "NP_001096969.pdb").getPath());
    Structure protein = PDBParser.getStructureFromPDBFile(pdbFile);
    
    assertNotNull(new MappedSeq(sequence,protein,hmmMSA));
    
  }
  
  
  @Test
  public void testMappedSeq() throws IOException {
    assertNotNull(mappedSeq);
  }
  
  @Test
  public void testGetIndexInPDBForQueryAt() {
    assertEquals(-1, mappedSeq.getIndexInPDBForQueryAt(1));
    assertEquals(1, mappedSeq.getIndexInPDBForQueryAt(142));
  }
  
  @Test
  public void testGetIndexInHmmMSAForQueryAt() {
    assertEquals(1, mappedSeq.getIndexInHmmMSAForQueryAt(1));
  }
  
  @Test
  public void testGetQuerySequence() throws IllegalArgumentException, IOException {
    ProteinSequence sequence = FastaParser
        .getProteinSequenceFromFasta(new File(getClass().getClassLoader()
            .getResource("NP_001096969.fa").getPath()));
    
    assertEquals(sequence.getSequenceAsString(), mappedSeq.getQuerySequence()
        .getSequenceAsString());
  }
  
  @Test
  public void testGetPDBSequence() throws IOException {
    Structure protein = PDBParser.getStructureFromPDBFile(new File(getClass()
        .getClassLoader().getResource("NP_001096969.pdb").getPath()));
    
    assertEquals(protein.getChain(0).getAtomSequence(), mappedSeq.getPDBSequence()
        .getSequenceAsString());
    assertNotSame(protein.getChain(0).getAtomSequence(), mappedSeq.getQuerySequence());
  }

  @Test
  public void testToString() {
    System.out.println(mappedSeq.toString());
    assertEquals(true, mappedSeq.toString().endsWith("467\t\t721\t\tnull\t\tP\n"));
    System.out.println(mappedSeq.toAlignedString());
  }
  
}
