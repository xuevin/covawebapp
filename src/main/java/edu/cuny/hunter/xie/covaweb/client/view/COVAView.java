package edu.cuny.hunter.xie.covaweb.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

import edu.cuny.hunter.xie.covaweb.client.presenter.COVAPresenter;


public class COVAView extends Composite implements COVAPresenter.ICOVAView {
  
  interface MyUiBinder extends UiBinder<Widget,COVAView>{};  
  private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

  @UiField
  ListBox listBox;
  
  public COVAView() {
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  public ListBox getListBox() {
    return listBox;
  }
  
}
