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
    ResultsDataObject results = new ResultsDataObject();

    
    // TODO - be able to handle the data objects with null values.
    Pipeline pipeline = new Pipeline(object);
    pipeline.run();
    
    results.setAlignmentString(pipeline.getMappedSeqResults().toAlignedString());
    results.setCovaDataObject(pipeline.getCovaResults());
    results.setAlignmentPosToPdbPosMapping(pipeline.getMappedSeqResults().getAlnPosToPdbPosMap());
    
    return (results);
    
  }
}
