package edu.cuny.hunter.xie.covaweb.server.parsers;

import java.io.File;
import java.io.IOException;

import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.io.PDBFileReader;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class PDBParser {
  public static Structure getStructureFromPDB(String string) throws IOException {
    // Write the PDB string to file
    
    File temp = File.createTempFile("covawebapp", "tmp");
    Files.write(string, temp, Charsets.UTF_8);
    
    // Read the Structure from the file
    return getStructureFromPDBFile(temp);
  }
  
  /**
   * A simple wrapper around the PDB file wrapper for convenience
   * 
   * @param file
   * @return A structure object which represents the PDB
   * @throws IOException
   */
  public static Structure getStructureFromPDBFile(File file) throws IOException {
    PDBFileReader reader = new PDBFileReader();
    return reader.getStructure(file);
  }
}
