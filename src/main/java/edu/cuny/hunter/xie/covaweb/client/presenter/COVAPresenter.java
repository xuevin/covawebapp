package edu.cuny.hunter.xie.covaweb.client.presenter;

import com.google.gwt.user.client.ui.ListBox;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

import edu.cuny.hunter.xie.covaweb.client.COVAWebEventBus;
import edu.cuny.hunter.xie.covaweb.client.view.COVAView;

@Presenter(view = COVAView.class)
public class COVAPresenter extends BasePresenter<COVAPresenter.ICOVAView,COVAWebEventBus> {
  
  public interface ICOVAView {
    
    ListBox getListBox();
    
  }
  
  public void onStart() {
    view.getListBox().addItem("FOOBAR");
  }
  
}
