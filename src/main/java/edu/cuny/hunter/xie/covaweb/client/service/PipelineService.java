package edu.cuny.hunter.xie.covaweb.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import edu.cuny.hunter.xie.covaweb.shared.DataObject;
import edu.cuny.hunter.xie.covaweb.shared.exceptions.PipelineException;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("pipeline")
public interface PipelineService extends RemoteService {
  String runPipeline(DataObject object) throws IllegalArgumentException, PipelineException;
}
