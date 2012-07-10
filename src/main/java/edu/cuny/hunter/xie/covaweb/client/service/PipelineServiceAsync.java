package edu.cuny.hunter.xie.covaweb.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.cuny.hunter.xie.covaweb.shared.ResultsDataObject;

public interface PipelineServiceAsync {
  void runPipeline(edu.cuny.hunter.xie.covaweb.shared.LoadDataObject object,
      AsyncCallback<ResultsDataObject> callback);
  
}
