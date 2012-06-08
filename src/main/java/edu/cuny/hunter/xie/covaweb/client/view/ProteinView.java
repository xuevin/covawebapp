package edu.cuny.hunter.xie.covaweb.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

import edu.cuny.hunter.xie.covaweb.client.presenter.ProteinPresenter;

@Singleton
public class ProteinView extends Composite implements
    ProteinPresenter.IProteinView {
  
  interface MyUiBinder extends UiBinder<Widget,ProteinView>{};
  
  private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
  
  @UiField
  HTMLPanel htmlPanel;
  
  public ProteinView(){
    initWidget(uiBinder.createAndBindUi(this));
  }

  @Override
  public HTMLPanel getHtmlPanel() {
    return htmlPanel;
  }
  
}
