package edu.cuny.hunter.xie.covaweb.client.presenter;

import info.vincentxue.gwtglprotein.client.ProteinWidget;

import java.util.logging.Logger;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

import edu.cuny.hunter.xie.covaweb.client.COVAWebEventBus;
import edu.cuny.hunter.xie.covaweb.client.view.ProteinView;

@Presenter(view = ProteinView.class)
public class ProteinPresenter extends BasePresenter<ProteinView,COVAWebEventBus>{
  
  public interface IProteinView{
    HTMLPanel getHtmlPanel();
  }
  
  Logger logger = Logger.getLogger(getClass().toString());
  
  public void onStart() {
    
    ProteinWidget widget = new ProteinWidget();
    
    view.getHtmlPanel().add(widget);
  }
}
