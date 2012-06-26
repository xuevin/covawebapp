package edu.cuny.hunter.xie.covaweb.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.cuny.hunter.xie.covaweb.client.service.PipelineService;
import edu.cuny.hunter.xie.covaweb.shared.LoadDataObject;
import edu.cuny.hunter.xie.covaweb.shared.exceptions.PipelineException;

public class PipelineServiceImpl extends RemoteServiceServlet implements
    PipelineService {
  
  static Logger logger = LoggerFactory.getLogger(PipelineServiceImpl.class);
  
  @Override
  public String runPipeline(LoadDataObject object) throws PipelineException {
    //TODO - be able to handle the data objects with null values.
    Pipeline pipeline = new Pipeline(object);
    pipeline.run();
    
    return(pipeline.getResults().toAlignedString());
    
    
  }
}
