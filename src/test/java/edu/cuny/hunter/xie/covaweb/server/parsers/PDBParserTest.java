package edu.cuny.hunter.xie.covaweb.server.parsers;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class PDBParserTest {
  
  @Test(expected = IllegalArgumentException.class)
  public void showThatParserThrowsIllegalArgException() throws IOException {
      File file = new File(getClass().getClassLoader()
          .getResource("PF03770_SEED.ann").getPath());
      
      PDBParser.getStructureFromPDBFile(file);
      
  }
  
}
