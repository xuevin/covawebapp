package edu.cuny.hunter.xie.covaweb.client.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface PipelineServiceAsync {
  void runPipeline(edu.cuny.hunter.xie.covaweb.shared.LoadDataObject object,
      AsyncCallback<java.lang.String> callback);
  
}
