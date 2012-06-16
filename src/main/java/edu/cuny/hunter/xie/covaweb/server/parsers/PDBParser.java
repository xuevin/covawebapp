package edu.cuny.hunter.xie.covaweb.server.parsers;

import java.io.File;
import java.io.IOException;

import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.io.PDBFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class PDBParser {
  static Logger logger = LoggerFactory.getLogger(PDBParser.class);
  
  public static Structure getStructureFromPDB(String string) throws IOException {
    // Write the PDB string to file
    // TODO This may be a little inefficient because I am writing to a file from a string. Look into InputStreams?
    
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
    logger.debug("Parsing PDB into Structure from file");
    PDBFileReader reader = new PDBFileReader();
    Structure structure = reader.getStructure(file);
    if(structure.toPDB().length()==0){
      logger.debug("Parsing PDB failed");
      throw new IllegalArgumentException("Invalid PDB file used");
    }
    return structure;
    
  }
}
