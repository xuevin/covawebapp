package edu.cuny.hunter.xie.covaweb.server.parsers;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.biojava3.core.sequence.ProteinSequence;
import org.biojava3.core.sequence.io.FastaReaderHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class FastaParser {
  static Logger logger = LoggerFactory.getLogger(FastaParser.class);
  
  public static ProteinSequence getProteinSequenceFromFasta(String string)
      throws IllegalArgumentException {
    logger.debug("Parsing String-FASTA into ProteinSequence from string");
    
    try {
      ProteinSequence proteinSeq = FastaReaderHelper.readFastaProteinSequence(
          new ByteArrayInputStream(string.toUpperCase()
              .getBytes(Charsets.UTF_8))).values().iterator().next();
      if (proteinSeq.getLength() == 0) {
        throw new IllegalArgumentException(
            "The length of the parsed string was 0");
      }
      
      return proteinSeq;
    } catch (Exception e) {
      throw new IllegalArgumentException(e);
    }
    
  }
  
  public static ProteinSequence getProteinSequenceFromFasta(File file)
      throws IllegalArgumentException, IOException {
    logger.debug("Parsing String-FASTA into ProteinSequence from file");
    
    String fastaString = Files.toString(file, Charsets.UTF_8);
    return getProteinSequenceFromFasta(fastaString);
    
  }
}
