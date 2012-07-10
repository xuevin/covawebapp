package edu.cuny.hunter.xie.covaweb.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.cuny.hunter.xie.covaweb.client.service.PipelineService;
import edu.cuny.hunter.xie.covaweb.client.utils.LinkedPositionDatabase;
import edu.cuny.hunter.xie.covaweb.shared.LoadDataObject;
import edu.cuny.hunter.xie.covaweb.shared.ResultsDataObject;
import edu.cuny.hunter.xie.covaweb.shared.exceptions.PipelineException;

public class PipelineServiceImpl extends RemoteServiceServlet implements
    PipelineService {
  
  static Logger logger = LoggerFactory.getLogger(PipelineServiceImpl.class);
  
  @Override
  public ResultsDataObject runPipeline(LoadDataObject object) throws PipelineException {
    //TODO - be able to handle the data objects with null values.
    Pipeline pipeline = new Pipeline(object);
    pipeline.run();
    ResultsDataObject results = new ResultsDataObject();
    results.setAlignmentString(pipeline.getResults().toAlignedString());
    //FIXME
    results.setStringifiedLinkedPositionDatabase("foo bar for now");
    return(results);
    
    
  }
}
