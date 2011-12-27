package edu.cuny.hunter.xie.covaweb.server;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

import org.biojava.bio.structure.Structure;
import org.biojava3.core.sequence.MultipleSequenceAlignment;
import org.biojava3.core.sequence.ProteinSequence;
import org.biojava3.core.sequence.compound.AminoAcidCompound;
import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import edu.cuny.hunter.xie.covaweb.server.parsers.FastaParser;
import edu.cuny.hunter.xie.covaweb.server.parsers.MSAParser;
import edu.cuny.hunter.xie.covaweb.server.parsers.PDBParser;

public class PreprocessServiceImplTest {
  
  @Test
  public void showThatGetMSAWorks() throws IOException {
    String alignment = Files.toString(new File(getClass().getClassLoader()
        .getResource("pnase.txt").getPath()), Charsets.UTF_8);
    
    MultipleSequenceAlignment<ProteinSequence,AminoAcidCompound> msa = MSAParser
        .getMSA(alignment);
    
    System.out.println(msa.getAlignedSequence(2).getSequenceAsString());
    
    StringTokenizer original = new StringTokenizer(alignment);
    StringTokenizer fromBioJava = new StringTokenizer(msa.toString());
    
    int i = 1;
    while (original.hasMoreTokens()) {
      String seq = original.nextToken().toUpperCase();
      if (i % 2 == 0) {
        assertEquals(seq, fromBioJava.nextToken());
      }
      i++;
    }
  }
  
  @Test
  public void showThatGetStructureFromPDBFileWorks() throws IOException {
    File pdbFile = new File(getClass().getClassLoader().getResource(
        "NP_001096969.pdb").getPath());
    Structure protein = PDBParser.getStructureFromPDBFile(pdbFile);
    // This test just makes sure that no aa is lost.
    // It is not a very robust test
    assertEquals(288, protein.getChain(0).getAtomLength());
  }
  
  @Test
  public void testAlignmentOfPDBToQuery() throws IOException {
    String string = Files.toString(new File(getClass().getClassLoader()
        .getResource("NP_001096969.fa").getPath()), Charsets.UTF_8);
    
    ProteinSequence sequence = FastaParser.getProteinSequenceFromFasta(string);
    
    File pdbFile = new File(getClass().getClassLoader().getResource(
        "NP_001096969.pdb").getPath());
    Structure protein = PDBParser.getStructureFromPDBFile(pdbFile);
    
    PipelineServiceImpl.alignPDBSequenceToQuery(sequence, protein);
  }
  
}
