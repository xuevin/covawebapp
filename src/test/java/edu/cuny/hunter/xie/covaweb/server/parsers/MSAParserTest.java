package edu.cuny.hunter.xie.covaweb.server.parsers;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

import org.biojava3.core.sequence.MultipleSequenceAlignment;
import org.biojava3.core.sequence.ProteinSequence;
import org.biojava3.core.sequence.compound.AminoAcidCompound;
import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class MSAParserTest {
  
  @Test
  public void showThatGetMSAWorks() throws IOException {
    String alignment = Files.toString(new File(getClass().getClassLoader()
        .getResource("pnase.txt").getPath()), Charsets.UTF_8);
    
    MultipleSequenceAlignment<ProteinSequence,AminoAcidCompound> msa = MSAParser
        .getMSA(alignment);
    
    //System.out.println(msa.getAlignedSequence(2).getSequenceAsString());
    //System.out.println(msa.toString());
    
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
  @Test(expected=IllegalArgumentException.class)
  public void showThatGetMSAThrowsIllegalArgException() throws IOException{
    String alignment = Files.toString(new File(getClass().getClassLoader().getResource("NP_001096969.fa.trash").getPath()), Charsets.UTF_8);
    
    MultipleSequenceAlignment<ProteinSequence,AminoAcidCompound> msa = MSAParser
        .getMSA(alignment);
    
    
    
  }
}
