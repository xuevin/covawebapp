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
  private ProteinWidget widget;
  
  public void onStart() {
    widget = new ProteinWidget(500,500);

    view.getHtmlPanel().add(widget);
    logger.info("The Protein Widget Has Been Started");
  }
  
  public void onProteinLoad(String pdbString){
    logger.info("Loading pdb file");
    widget.loadPDBFromString(pdbString);
  }
  public void onLinkPositions(float x1, float y1, float z1, float x2, float y2, float z2){
    widget.addConnectedPoints(x1,y1,z1,x2,y2,z2);
  }
  public void onRemovePositions(float x1, float y1, float z1, float x2, float y2, float z2){
    widget.removeConnectedPoints(x1,y1,z1,x2,y2,z2);
  }
}
