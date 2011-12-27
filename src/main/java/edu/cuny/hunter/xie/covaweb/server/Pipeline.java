package edu.cuny.hunter.xie.covaweb.server;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.Sequence;

import org.biojava3.core.sequence.ProteinSequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.cuny.hunter.xie.covaweb.server.external.PfamService;
import edu.cuny.hunter.xie.covaweb.server.external.PfamServiceObject;
import edu.cuny.hunter.xie.covaweb.server.parsers.FastaParser;
import edu.cuny.hunter.xie.covaweb.shared.exceptions.PipelineException;

public class Pipeline {
  
  Logger logger = LoggerFactory.getLogger(this.getClass());
  
  private ProteinSequence queryProteinSequence;
  private String allignment; // TODO Turn both of these into objects
  private String pdb; // TODO Turn both of these into objects
  
  public Pipeline(File queryAminoAcidSequenceFastaFile)
      throws IllegalArgumentException, IOException {
    this.queryProteinSequence = FastaParser
        .getProteinSequenceFromFasta(queryAminoAcidSequenceFastaFile);
  }
  
  public Pipeline(String queryAminoAcidSequenceString) throws PipelineException {
    this.queryProteinSequence = new ProteinSequence(
        queryAminoAcidSequenceString.toUpperCase());
  }
  
  public boolean run() throws PipelineException {
    try {
      // Step 0 Verify what files you have access to.
      if (queryProteinSequence == null) {
        return false; // Can't continue without access to a query sequence
      } else if (allignment != null && pdb != null) {
        return runPipelineAllProvided(); // run pipeline where all information is provided
      }
      
      // Run full pipeline
      return runPipelineFull();
    } catch (IOException e) {
      throw new PipelineException("Unexpected IO exception", e);
    } catch (IllegalArgumentException e) {
      throw new PipelineException("Unexpected Parsing exception", e);
    }
  }
  
  public boolean runPipelineFull() throws IllegalArgumentException, IOException {
    // TODO
    // Step 1 Query the PFAM service to get the pfamObj
    PfamServiceObject pfamObj = PfamService
        .getPfamServiceObj(queryProteinSequence);
    System.out.println(pfamObj.getHmm());
    
    // Step 2 Download all the SEED alignments
    
    return false;
  }
  
  public boolean runPipelineAllProvided() {
    // TODO
    return false;
  }
}
