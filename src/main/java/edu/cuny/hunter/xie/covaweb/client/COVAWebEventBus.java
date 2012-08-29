package edu.cuny.hunter.xie.covaweb.client;

import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.Start;
import com.mvp4g.client.event.EventBus;

import edu.cuny.hunter.xie.covaweb.client.presenter.ConfigPresenter;
import edu.cuny.hunter.xie.covaweb.client.presenter.DisplayPresenter;
import edu.cuny.hunter.xie.covaweb.client.presenter.GridPresenter;
import edu.cuny.hunter.xie.covaweb.client.presenter.ProteinPresenter;
import edu.cuny.hunter.xie.covaweb.client.utils.LinkedPositionDatabase;
import edu.cuny.hunter.xie.covaweb.client.view.RootView;

@Events(startView = RootView.class)
public interface COVAWebEventBus extends EventBus {
  
  @Start
  @Event(handlers = {ConfigPresenter.class, ProteinPresenter.class, GridPresenter.class})
  public void start();
  
  @Event(handlers = {DisplayPresenter.class})
  public void alignmentResultsReady(String string);
  
  @Event(handlers ={ProteinPresenter.class})//TODO Uncomment to load protein
  public void proteinLoad(String pdbString);
  
  @Event(handlers ={GridPresenter.class})
  public void covaResultsReady(LinkedPositionDatabase database);
  
  @Event (handlers={ProteinPresenter.class})
  public void linkPositions(int pos1, int pos2);

  @Event (handlers={ProteinPresenter.class})
  public void removePositions(int pos1, int pos2);
}
