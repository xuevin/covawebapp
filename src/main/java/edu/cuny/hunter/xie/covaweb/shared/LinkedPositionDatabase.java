package edu.cuny.hunter.xie.covaweb.shared;

import java.util.List;

import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;

public class LinkedPositionDatabase {
  
  public LinkedPositionDatabase(String outputFromCovaAnalaysis) {
//    addLinkedPositionData(new LinkedPositionData(3, 2, 1.01, 2.0, 3.0, 4.0, 5.0, 6.0));
//    addLinkedPositionData(new LinkedPositionData(0, 1, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0));
//    addLinkedPositionData(new LinkedPositionData(1, 2, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0));

  }

  private static int nextId = 0;
  
  private ListDataProvider<LinkedPositionData> dataProvider = new ListDataProvider<LinkedPositionData>();
  
  public void addLinkedPositionData(LinkedPositionData newData) {
    List<LinkedPositionData> data = dataProvider.getList();
    data.add(newData);
  }
  
  public void addDataDisplay(HasData<LinkedPositionData> display) {
    dataProvider.addDataDisplay(display);
  }
  
  public ListDataProvider<LinkedPositionData> getDataProvider() {
    return dataProvider;
  }
  
  public void refreshDisplays() {
    dataProvider.refresh();
  }
  
  public LinkedPositionDatabase get() {
    // TODO Auto-generated method stub
    // FIXME - Return a test instance
    LinkedPositionDatabase foo = new LinkedPositionDatabase("");
    return foo;
  }
  
  // Each Linked Position Data Is an output row from the Covariance analysis.
  public static class LinkedPositionData {
    
    private int positionA;
    private int positionB;
    private double scaScore;
    private double elscScore;
    private double miScore;
    private double omesScore;
    private double cSumScore;
    private double randomScore;
    private final int id;
    
    public static final ProvidesKey<LinkedPositionData> KEY_PROVIDER = new ProvidesKey<LinkedPositionData>() {
      public Object getKey(LinkedPositionData item) {
        return item == null ? null : item.getId();
      }
    };
    
    public LinkedPositionData(int positionA, int positionB, double scaScore,
        double elscScore, double miScore, double omesScore, double csumScore,
        double randomScore) {
      
      this.positionA = positionA;
      this.positionB = positionB;
      this.scaScore = scaScore;
      this.elscScore = elscScore;
      this.miScore = miScore;
      this.omesScore = omesScore;
      this.cSumScore = csumScore;
      this.randomScore = randomScore;
      
      this.id = nextId;
      nextId++;
    }
    
    public int getId() {
      return id;
    }
    
    public int getPositionA() {
      return positionA;
    }
    
    public int getPositionB() {
      return positionB;
    }
    
    public double getScaScore() {
      return scaScore;
    }
    
    public double getElscScore() {
      return elscScore;
    }
    
    public double getMiScore() {
      return miScore;
    }
    
    public double getOmesScore() {
      return omesScore;
    }
    
    public double getCSumScore() {
      return cSumScore;
    }
    
    public double getRandomScore() {
      return randomScore;
    }
    
    @Override
    public boolean equals(Object o) {
      if (o instanceof LinkedPositionData) {
        return id == ((LinkedPositionData) o).id;
      }
      return false;
    }
  }
  
}
