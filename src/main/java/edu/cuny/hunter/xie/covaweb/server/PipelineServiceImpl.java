package edu.cuny.hunter.xie.covaweb.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.cuny.hunter.xie.covaweb.client.service.PipelineService;
import edu.cuny.hunter.xie.covaweb.shared.DataObject;
import edu.cuny.hunter.xie.covaweb.shared.exceptions.PipelineException;

public class PipelineServiceImpl extends RemoteServiceServlet implements
    PipelineService {
  
  static Logger logger = LoggerFactory.getLogger(PipelineServiceImpl.class);
  
  @Override
  public String runPipeline(DataObject object) throws PipelineException {
    Pipeline pipeline = new Pipeline(object.getQueryString());
    pipeline.run();
    return "Success";
    
  }
}
