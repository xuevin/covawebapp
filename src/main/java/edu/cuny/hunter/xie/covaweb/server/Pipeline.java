package edu.cuny.hunter.xie.covaweb.server;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import org.biojava.bio.structure.Structure;
import org.biojava3.alignment.template.SequencePair;
import org.biojava3.core.sequence.MultipleSequenceAlignment;
import org.biojava3.core.sequence.ProteinSequence;
import org.biojava3.core.sequence.compound.AminoAcidCompound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.cuny.hunter.xie.covaweb.server.external.PfamService;
import edu.cuny.hunter.xie.covaweb.server.external.PfamServiceObject;
import edu.cuny.hunter.xie.covaweb.server.parsers.FastaParser;
import edu.cuny.hunter.xie.covaweb.server.parsers.PDBParser;
import edu.cuny.hunter.xie.covaweb.server.parsers.StockholmParser;
import edu.cuny.hunter.xie.covaweb.server.utils.AlignmentUtils;
import edu.cuny.hunter.xie.covaweb.shared.DataObject;
import edu.cuny.hunter.xie.covaweb.shared.exceptions.PipelineException;

public class Pipeline {
  
  Logger logger = LoggerFactory.getLogger(this.getClass());
  
  private ProteinSequence queryProteinSequence;
  private MultipleSequenceAlignment<ProteinSequence,AminoAcidCompound> alignment; //Input alignment must be in Stockholm format
  private Structure pdb;

  private MappedSeq resultMappedSeq;
  
  public Pipeline(DataObject dataObject) throws PipelineException{
    try {
      this.pdb=PDBParser.getStructureFromPDB(dataObject.getPdbString());
      this.alignment=StockholmParser.getMSA(new ByteArrayInputStream(dataObject.getMsaString().getBytes()));
      this.queryProteinSequence = FastaParser.getProteinSequenceFromFasta(dataObject.getQueryString());
    } catch (IOException e) {
      throw new PipelineException(e);
    }
  }
  
  public Pipeline(File queryAminoAcidSequenceFastaFile)
      throws IllegalArgumentException, IOException {
    this.queryProteinSequence = FastaParser
        .getProteinSequenceFromFasta(queryAminoAcidSequenceFastaFile);
  }
  public Pipeline(String queryAminoAcideSequenceString, String alignmentInputString, String pdbInputString) throws PipelineException{
    try {
      this.pdb=PDBParser.getStructureFromPDB(pdbInputString);
      this.alignment=StockholmParser.getMSA(new ByteArrayInputStream(alignmentInputString.getBytes()));
      this.queryProteinSequence = FastaParser
      .getProteinSequenceFromFasta(queryAminoAcideSequenceString);
    } catch (IOException e) {
      throw new PipelineException(e);
    }
  }
  public Pipeline(String queryAminoAcidSequenceString) throws PipelineException {
    this.queryProteinSequence = new ProteinSequence(
        queryAminoAcidSequenceString.toUpperCase());
  }
  
  public boolean run() throws PipelineException {
    try {
      // Step 0 Verify what files you have access to.
      if (queryProteinSequence == null) {
        logger.info("EXITED - Can't continue without access to a query sequence");
        return false; // Can't continue without access to a query sequence
      } else if (alignment != null && pdb != null) {
        return runPipelineAllProvided(); // run pipeline where all information
                                         // is provided
      }
      
      // Run full pipeline (Protein and MSA sequences missing)
      return runPipelineFull();
    } catch (IOException e) {
      throw new PipelineException("Unexpected IO exception", e);
    } catch (IllegalArgumentException e) {
      throw new PipelineException("Unexpected Parsing exception", e);
    }
  }
  
  private boolean runPipelineFull() throws IllegalArgumentException,
      IOException {
    // TODO
    // Step 1 Query the PFAM service to get the pfamObj
    PfamServiceObject pfamObj = PfamService
        .getPfamServiceObj(queryProteinSequence);
    logger.debug("The retrieved HMM is {} ",pfamObj.getHmm());
    
    // Step 2 Download all the SEED alignments
    //PfamService.getListOfSeedSequences(accession);
    
    return false;
  }
  
  private boolean runPipelineAllProvided() {
    logger.info("The pipeline has all the pieces required to continue");
    resultMappedSeq = new MappedSeq(queryProteinSequence,pdb,alignment);
    return true;
    //TODO put a trycatch here
    
  }

  public MappedSeq getResults() {
    return resultMappedSeq;
  }
}
