package edu.cuny.hunter.xie.covaweb.client;

import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.Start;
import com.mvp4g.client.event.EventBus;

import edu.cuny.hunter.xie.covaweb.client.presenter.ConfigPresenter;
import edu.cuny.hunter.xie.covaweb.client.presenter.DisplayPresenter;
import edu.cuny.hunter.xie.covaweb.client.presenter.ProteinPresenter;
import edu.cuny.hunter.xie.covaweb.client.view.ProteinView;
import edu.cuny.hunter.xie.covaweb.client.view.RootView;
import edu.cuny.hunter.xie.covaweb.shared.DataObject;

@Events(startView = RootView.class)
public interface COVAWebEventBus extends EventBus {
  
  @Start
  @Event(handlers = {ConfigPresenter.class, ProteinPresenter.class})
  public void start();
  
  @Event(handlers = {DisplayPresenter.class})
  public void resultsReady(String string);
  
}
