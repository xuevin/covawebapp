package edu.cuny.hunter.xie.covaweb.server.parsers;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.StructureException;
import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class PDBParserTest {
  
  @Test(expected = IllegalArgumentException.class)
  public void showThatParserThrowsIllegalArgException() throws IOException {
      File file = new File(getClass().getClassLoader()
          .getResource("PF03770_SEED.ann").getPath());
      
      Structure structure = PDBParser.getStructureFromPDBFile(file);
  }
  @Test
  public void showThatParserWorks() throws IOException, StructureException{
    File file = new File(getClass().getClassLoader()
        .getResource("NP_001096969.pdb").getPath());
    
    Structure structure = PDBParser.getStructureFromPDBFile(file);
    System.out.println(structure.getChain(0).getAtomGroup(0));
    System.out.println(structure.getChain(0).getAtomGroup(0).getAtom("CA"));
    assertNotNull(structure);

  }
  
}
