package edu.cuny.hunter.xie.covaweb.server;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import org.biojava3.core.sequence.MultipleSequenceAlignment;
import org.biojava3.core.sequence.ProteinSequence;
import org.biojava3.core.sequence.compound.AminoAcidCompound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.cuny.hunter.xie.covaweb.client.service.PipelineService;
import edu.cuny.hunter.xie.covaweb.server.parsers.ClustalWParser;
import edu.cuny.hunter.xie.covaweb.shared.CovaDataRow;
import edu.cuny.hunter.xie.covaweb.shared.LoadDataObject;
import edu.cuny.hunter.xie.covaweb.shared.ResultsDataObject;
import edu.cuny.hunter.xie.covaweb.shared.exceptions.PipelineException;

public class PipelineServiceImpl extends RemoteServiceServlet implements
    PipelineService {
  
  static Logger logger = LoggerFactory.getLogger(PipelineServiceImpl.class);
  
  @Override
  public ResultsDataObject runPipeline(LoadDataObject object)
      throws PipelineException {
    // TODO - be able to handle the data objects with null values.
    Pipeline pipeline = new Pipeline(object);
    pipeline.run();
    ResultsDataObject results = new ResultsDataObject();
    results.setAlignmentString(pipeline.getResults().toAlignedString());
    
    try {
      logger.info("Starting Covariance Analysis");
      MultipleSequenceAlignment<ProteinSequence,AminoAcidCompound> msa = ClustalWParser
          .getMSA(new ByteArrayInputStream(object.getMsaString().getBytes()));
      
      ArrayList<CovaDataRow> list = ExecuteCOVA.getOutputFromBioJavaMSA(msa);
      
      results.setCovaDataObject(list);
      logger.info("Covariance Analysis Complete " + list.size());
    } catch (Exception e) {
      throw new PipelineException(
          "Encountered error during covariance analysis", e);
    }
    return (results);
    
  }
}
