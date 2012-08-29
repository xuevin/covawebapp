package edu.cuny.hunter.xie.covaweb.client.presenter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.cellview.client.SafeHtmlHeader;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

import edu.cuny.hunter.xie.covaweb.client.COVAWebEventBus;
import edu.cuny.hunter.xie.covaweb.client.utils.LinkedPositionDatabase;
import edu.cuny.hunter.xie.covaweb.client.utils.LinkedPositionDatabase.LinkedPositionData;
import edu.cuny.hunter.xie.covaweb.client.view.GridView;

@Presenter(view = GridView.class)
public class GridPresenter extends BasePresenter<GridView,COVAWebEventBus> {
  
  private Logger logger = Logger.getLogger(getClass().toString());
  private DataGrid<LinkedPositionData> dataGrid;
  
  public interface IGridView {
    HTML getLabel();
    
    DockLayoutPanel getPanel();
    
    HTMLPanel getPagerPanel();
  }
  
  public void onStart() {
    logger.info("Grid Initiating");
    
    dataGrid = new DataGrid<LinkedPositionData>(
        LinkedPositionDatabase.LinkedPositionData.KEY_PROVIDER);
    dataGrid.setWidth("100%");
    
    dataGrid.setEmptyTableWidget(new Label("Table is Empty"));
    
    
  }
  
  public void onCovaResultsReady(LinkedPositionDatabase database) {
    ListHandler<LinkedPositionData> sortHandler = new ListHandler<LinkedPositionData>(
        database.getDataProvider().getList());
    dataGrid.addColumnSortHandler(sortHandler);
    
    SimplePager.Resources pagerResources = GWT
        .create(SimplePager.Resources.class);
    SimplePager pager = new SimplePager(TextLocation.CENTER, pagerResources,
        false, 0, true);
    pager.setDisplay(dataGrid);
    
    final MultiSelectionModel<LinkedPositionData> selectionModel = new MultiSelectionModel<LinkedPositionData>(
        LinkedPositionData.KEY_PROVIDER);
    dataGrid.setSelectionModel(selectionModel, DefaultSelectionEventManager
        .<LinkedPositionData> createCheckboxManager());
    
    initTableColumns(selectionModel, sortHandler);
    // Add the CellList to the adapter in the database.
    database.addDataDisplay(dataGrid);
    
    view.getPagerPanel().add(pager);
    view.getPanel().add(dataGrid);
  }
  
  /**
   * Add the columns to the table.
   */
  private void initTableColumns(
      final SelectionModel<LinkedPositionData> selectionModel,
      ListHandler<LinkedPositionData> sortHandler) {
    
    // Checkbox column. This table will uses a checkbox column for selection.
    // Alternatively, you can call dataGrid.setSelectionEnabled(true) to enable
    // mouse selection.
    Column<LinkedPositionData,Boolean> checkColumn = new Column<LinkedPositionData,Boolean>(
        new CheckboxCell(true, false)) {
      @Override
      public Boolean getValue(LinkedPositionData object) {
        // Get the value from the selection model.
        return selectionModel.isSelected(object);
      }
    };
    checkColumn.setFieldUpdater(new FieldUpdater<LinkedPositionData,Boolean>() {
      @Override
      public void update(int index, LinkedPositionData object, Boolean value) {
        if(value==Boolean.TRUE){
          eventBus.linkPositions(object.getPdbPos1(),object.getPdbPos2());
        }else{
          eventBus.removePositions(object.getPdbPos1(),object.getPdbPos2());
        }
//        Window.alert("You clicked " + object.getPositionA() + value.toString());
      }
    });
    dataGrid.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
    dataGrid.setColumnWidth(checkColumn, 40, Unit.PX);
    
    // Position A.
    Column<LinkedPositionData,Number> positionAColumn = new Column<LinkedPositionData,Number>(
        new NumberCell()) {
      @Override
      public Number getValue(LinkedPositionData object) {
        return object.getPos1();
      }
    };
    positionAColumn.setSortable(true);
    sortHandler.setComparator(positionAColumn,
        new Comparator<LinkedPositionData>() {
          public int compare(LinkedPositionData o1, LinkedPositionData o2) {
            if (o1.getPos1() < o2.getPos1()) {
              return -1;
            } else if (o1.getPos1() > o2.getPos1()) {
              return 1;
            } else {
              return 0;
            }
          }
        });
    dataGrid.addColumn(positionAColumn, "nsSNP");
    dataGrid.setColumnWidth(positionAColumn, 10, Unit.PCT);
    
    // Position B.
    Column<LinkedPositionData,Number> positionBColumn = new Column<LinkedPositionData,Number>(
        new NumberCell()) {
      @Override
      public Number getValue(LinkedPositionData object) {
        return object.getPos2();
      }
    };
    positionBColumn.setSortable(true);
    sortHandler.setComparator(positionBColumn,
        new Comparator<LinkedPositionData>() {
          public int compare(LinkedPositionData o1, LinkedPositionData o2) {
            if (o1.getPos2() < o2.getPos2()) {
              return -1;
            } else if (o1.getPos2() > o2.getPos2()) {
              return 1;
            } else {
              return 0;
            }
          }
        });
    dataGrid.addColumn(positionBColumn, "Pos.B");
    dataGrid.setColumnWidth(positionBColumn, 10, Unit.PCT);
    
    // sca Score.
    Column<LinkedPositionData,Number> scaScoreColumn = new Column<LinkedPositionData,Number>(
        new NumberCell()) {
      @Override
      public Number getValue(LinkedPositionData object) {
        return object.getScaScore();
      }
    };
    scaScoreColumn.setSortable(true);
    sortHandler.setComparator(scaScoreColumn,
        new Comparator<LinkedPositionData>() {
          public int compare(LinkedPositionData o1, LinkedPositionData o2) {
            if (o1.getScaScore() < o2.getScaScore()) {
              return -1;
            } else if (o1.getScaScore() > o2.getScaScore()) {
              return 1;
            } else {
              return 0;
            }
          }
        });    
    dataGrid.addColumn(scaScoreColumn, "SCA Score");
    dataGrid.setColumnWidth(scaScoreColumn, 10, Unit.PCT);
    
    // elsc Score.
    Column<LinkedPositionData,Number> elscScoreColumn = new Column<LinkedPositionData,Number>(
        new NumberCell()) {
      @Override
      public Number getValue(LinkedPositionData object) {
        return object.getElscScore();
      }
    };
    elscScoreColumn.setSortable(true);
    sortHandler.setComparator(elscScoreColumn,
        new Comparator<LinkedPositionData>() {
          public int compare(LinkedPositionData o1, LinkedPositionData o2) {
            if (o1.getElscScore() < o2.getElscScore()) {
              return -1;
            } else if (o1.getElscScore() > o2.getElscScore()) {
              return 1;
            } else {
              return 0;
            }
          }
        });
    dataGrid.addColumn(elscScoreColumn, "ELSC Score");
    dataGrid.setColumnWidth(elscScoreColumn, 10, Unit.PCT);
    
    // mi Score.
    Column<LinkedPositionData,Number> miScoreColumn = new Column<LinkedPositionData,Number>(
        new NumberCell()) {
      @Override
      public Number getValue(LinkedPositionData object) {
        return object.getMiScore();
      }
    };
    miScoreColumn.setSortable(true);
    sortHandler.setComparator(miScoreColumn,
        new Comparator<LinkedPositionData>() {
          public int compare(LinkedPositionData o1, LinkedPositionData o2) {
            if (o1.getMiScore() < o2.getMiScore()) {
              return -1;
            } else if (o1.getMiScore() > o2.getMiScore()) {
              return 1;
            } else {
              return 0;
            }
          }
        });
    dataGrid.addColumn(miScoreColumn, "MI Score");
    dataGrid.setColumnWidth(miScoreColumn, 10, Unit.PCT);
    
    // mi Score.
    Column<LinkedPositionData,Number> omesScoreColumn = new Column<LinkedPositionData,Number>(
        new NumberCell()) {
      @Override
      public Number getValue(LinkedPositionData object) {
        return object.getOmesScore();
      }
    };
    omesScoreColumn.setSortable(true);
    sortHandler.setComparator(omesScoreColumn,
        new Comparator<LinkedPositionData>() {
          public int compare(LinkedPositionData o1, LinkedPositionData o2) {
            if (o1.getOmesScore() < o2.getOmesScore()) {
              return -1;
            } else if (o1.getOmesScore() > o2.getOmesScore()) {
              return 1;
            } else {
              return 0;
            }
          }
        });
    dataGrid.addColumn(omesScoreColumn, "OMES Score");
    dataGrid.setColumnWidth(omesScoreColumn, 10, Unit.PCT);
    
    // cSum Score.
    Column<LinkedPositionData,Number> cSumScoreColumn = new Column<LinkedPositionData,Number>(
        new NumberCell()) {
      @Override
      public Number getValue(LinkedPositionData object) {
        return object.getCSumScore();
      }
    };
    cSumScoreColumn.setSortable(true);
    sortHandler.setComparator(cSumScoreColumn,
        new Comparator<LinkedPositionData>() {
          public int compare(LinkedPositionData o1, LinkedPositionData o2) {
            if (o1.getCSumScore() < o2.getCSumScore()) {
              return -1;
            } else if (o1.getCSumScore() > o2.getCSumScore()) {
              return 1;
            } else {
              return 0;
            }
          }
        });
    dataGrid.addColumn(cSumScoreColumn, "CSum Score");
    dataGrid.setColumnWidth(cSumScoreColumn, 10, Unit.PCT);
    
  }
  
}
