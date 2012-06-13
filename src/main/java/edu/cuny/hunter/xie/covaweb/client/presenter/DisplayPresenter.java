package edu.cuny.hunter.xie.covaweb.client.presenter;

import java.util.logging.Logger;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

import edu.cuny.hunter.xie.covaweb.client.COVAWebEventBus;
import edu.cuny.hunter.xie.covaweb.client.view.DisplayView;

@Presenter(view = DisplayView.class)
public class DisplayPresenter extends
    BasePresenter<DisplayView,COVAWebEventBus> {
  
  private Logger logger = Logger.getLogger(getClass().toString());
  
  
  public interface IDisplayView {
    public Label getMainTextLabel();
    public HTML getMainTextHTML();
  }
  
  public void onStart() {

  }
  
  public void onResultsReady(String string) {
     view.getMainTextHTML().setHTML(string.replace("\n", "<br>"));
     //TODO - update this
    
  }
}
