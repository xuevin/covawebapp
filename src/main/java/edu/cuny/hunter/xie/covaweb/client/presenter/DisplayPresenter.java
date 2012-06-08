package edu.cuny.hunter.xie.covaweb.client.presenter;

import java.util.logging.Logger;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

import edu.cuny.hunter.xie.covaweb.client.COVAWebEventBus;
import edu.cuny.hunter.xie.covaweb.client.service.PipelineServiceAsync;
import edu.cuny.hunter.xie.covaweb.client.view.DisplayView;
import edu.cuny.hunter.xie.covaweb.shared.DataObject;

@Presenter(view = DisplayView.class)
public class DisplayPresenter extends
    BasePresenter<DisplayView,COVAWebEventBus> {
  
  @Inject
  private PipelineServiceAsync preprocessService;
  
  private Logger logger = Logger.getLogger(getClass().toString());
  
  
  public interface IDisplayView {
    public Label getMainTextLabel();
    public HTML getMainTextHTML();
  }
  
  public void onStart() {

  }
  
  public void onDataLoaded(DataObject object) {
    AsyncCallback<String> callback = new AsyncCallback<String>() {
      
      @Override
      public void onSuccess(String result) {
        view.getMainTextHTML().setHTML(result.replace("\n", "<br>"));
      }
      
      @Override
      public void onFailure(Throwable caught) {
        logger.info("RPC failed: " + caught.getMessage());
        view.getMainTextLabel().setText("RPC failed: " + caught.getLocalizedMessage());
      }
    };
    preprocessService.runPipeline(object, callback);
    
    
  }
}
