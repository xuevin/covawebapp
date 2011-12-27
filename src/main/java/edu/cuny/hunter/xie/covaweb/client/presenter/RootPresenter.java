package edu.cuny.hunter.xie.covaweb.client.presenter;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

import edu.cuny.hunter.xie.covaweb.client.COVAWebEventBus;
import edu.cuny.hunter.xie.covaweb.client.view.RootView;

@Presenter(view = RootView.class)
public class RootPresenter extends
    BasePresenter<RootPresenter.IRootView,COVAWebEventBus> {
  
  public interface IRootView {}
  // public void onStart() {
  // view.getListBox().addItem("FOOBAR");
  //    
  // Column<String,String> index = new Column<String,String>(new TextCell()) {
  // @Override
  // public String getValue(String object) {
  // return object.toString();
  // }
  // };
  //    
  // List<String> listOfStuff = new ArrayList<String>();
  //    
  // listOfStuff.add("FooBar");
  //    
  // // ListDataProvider<String> dataProvider = new ListDataProvider<String>();
  // view.getTable().addColumn(index);
  // view.getTable().setEmptyTableWidget(new Label("Empty..,"));
  // view.getTable().setRowCount(20, true);
  // view.getTable().setRowData(listOfStuff);
  //    
  // }
}
