package edu.cuny.hunter.xie.covaweb.server;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.biojava.bio.structure.Structure;
import org.biojava3.alignment.template.SequencePair;
import org.biojava3.core.sequence.MultipleSequenceAlignment;
import org.biojava3.core.sequence.ProteinSequence;
import org.biojava3.core.sequence.compound.AminoAcidCompound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.cuny.hunter.xie.covaweb.server.external.PfamService;
import edu.cuny.hunter.xie.covaweb.server.external.PfamServiceObject;
import edu.cuny.hunter.xie.covaweb.server.parsers.ClustalWParser;
import edu.cuny.hunter.xie.covaweb.server.parsers.FastaParser;
import edu.cuny.hunter.xie.covaweb.server.parsers.PDBParser;
import edu.cuny.hunter.xie.covaweb.server.utils.AlignmentUtils;
import edu.cuny.hunter.xie.covaweb.shared.CovaDataRow;
import edu.cuny.hunter.xie.covaweb.shared.LoadDataObject;
import edu.cuny.hunter.xie.covaweb.shared.exceptions.PipelineException;

public class Pipeline {
  
  Logger logger = LoggerFactory.getLogger(this.getClass());
  
  private ProteinSequence queryProteinSequence;
  private MultipleSequenceAlignment<ProteinSequence,AminoAcidCompound> alignment;
  // Input alignment must be in Stockholm format
  private Structure pdb;
  
  private MappedSeq resultMappedSeq;
  
  private ArrayList<CovaDataRow> covaDataResults;
  
  public Pipeline(LoadDataObject dataObject) throws PipelineException {
    try {
      this.pdb = PDBParser.getStructureFromPDB(dataObject.getPdbString());
      this.alignment = ClustalWParser.getMSA(new ByteArrayInputStream(
          dataObject.getMsaString().getBytes()));
      this.queryProteinSequence = FastaParser
          .getProteinSequenceFromFasta(dataObject.getQueryString());
    } catch (IOException e) {
      throw new PipelineException(e);
    }
  }
  
  public Pipeline(File queryAminoAcidSequenceFastaFile)
      throws IllegalArgumentException, IOException {
    this.queryProteinSequence = FastaParser
        .getProteinSequenceFromFasta(queryAminoAcidSequenceFastaFile);
  }
  
  public Pipeline(String queryAminoAcideSequenceString,
      String alignmentInputString, String pdbInputString)
      throws PipelineException {
    try {
      this.pdb = PDBParser.getStructureFromPDB(pdbInputString);
      this.alignment = ClustalWParser.getMSA(new ByteArrayInputStream(
          alignmentInputString.getBytes()));
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
        logger
            .info("EXITED - Can't continue without access to a query sequence");
        return false; // Can't continue without access to a query sequence
      } else if (alignment != null && pdb != null) {
        return runPipelineAllProvided(); // run pipeline where all information
                                         // is provided
      }
      
      // Run full pipeline (Protein and MSA sequences missing)
      return runAutoPipeline();
    } catch (IOException e) {
      throw new PipelineException("Unexpected IO exception", e);
    } catch (IllegalArgumentException e) {
      throw new PipelineException("Unexpected Parsing exception", e);
    }
  }
  
  private boolean runAutoPipeline() throws IllegalArgumentException,
      IOException {
    
    // Step 1
    logger.info("Query the PFAM service to get the pfamObj");
    // TODO
    PfamServiceObject pfamObj = PfamService
        .getPfamServiceObj(queryProteinSequence);
    logger.debug("The retrieved HMM is {} ", pfamObj.getHmm());
    
    // Step 2 Download all the SEED alignments
    // PfamService.getListOfSeedSequences(accession);
    
    return false;
  }
  
  private boolean runPipelineAllProvided() throws PipelineException {
    logger.info("The pipeline has all the pieces required to continue");
    
    try {
      logger.info("Mapping alignment pdb");
      resultMappedSeq = new MappedSeq(queryProteinSequence, pdb, alignment);
    } catch (Exception e) {
      throw new PipelineException("Encountered error during alignment mapping");
    }
    
    
    try {
      logger.info("Starting Covariance Analysis");
      int start = resultMappedSeq.getIndexInHmmMSAForPDBAt(1);
      int end = resultMappedSeq.getIndexInHmmMSAForPDBAt(pdb.getChain(0).getAtomLength());
      
      ArrayList<CovaDataRow> list = ExecuteCOVA.getOutputFromBioJavaMSA(
          alignment, start, end);
      this.covaDataResults = list;
      
      logger.info("Covariance Analysis Complete " + list.size());
    } catch (Exception e) {
      throw new PipelineException(
          "Encountered error during covariance analysis", e);
    }
    
    return true;
    
  }
  
  public MappedSeq getMappedSeqResults() {
    return resultMappedSeq;
  }
  
  public ArrayList<CovaDataRow> getCovaResults() {
    return covaDataResults;
  }
}
