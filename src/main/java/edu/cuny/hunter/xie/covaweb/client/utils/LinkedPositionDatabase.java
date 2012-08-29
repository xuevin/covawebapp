package edu.cuny.hunter.xie.covaweb.client.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;

import edu.cuny.hunter.xie.covaweb.shared.CovaDataRow;

public class LinkedPositionDatabase {
  private Logger logger = Logger.getLogger(getClass().toString());
  
  public LinkedPositionDatabase(ArrayList<CovaDataRow> list,
      HashMap<Integer,Integer> alignmentPosToPdbPosMapping) {
    
    for (CovaDataRow line : list) {
      
      if (alignmentPosToPdbPosMapping.get(line.getPos1()) == null
          || alignmentPosToPdbPosMapping.get(line.getPos2()) == null) {
        
      } else {
        addLinkedPositionData(new LinkedPositionData(line.getPos1(),
            line.getPos2(), line.getScaScore(), line.getElscScore(),
            line.getMiScore(), line.getOmesScore(), line.getCsumScore(), alignmentPosToPdbPosMapping.get(line
                .getPos1()), alignmentPosToPdbPosMapping.get(line.getPos2())));
        
        
      }
    }
    // logger.info("" + list.size());
    // addLinkedPositionData(new LinkedPositionData(0, 1, 1.0, 2.0, 3.0, 4.0,
    // 5.0, 6.0));
    // addLinkedPositionData(new LinkedPositionData(1, 2, 1.0, 2.0, 3.0, 4.0,
    // 5.0, 6.0));
    
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
    LinkedPositionDatabase foo = new LinkedPositionDatabase(
        new ArrayList<CovaDataRow>(), new HashMap<Integer,Integer>());
    return foo;
  }
  
  // Each Linked Position Data Is an output row from the Covariance analysis.
  public static class LinkedPositionData {
    
    private int pos1;
    private int pos2;
    private double scaScore;
    private double elscScore;
    private double miScore;
    private double omesScore;
    private double cSumScore;
    private final int id;
    private int pdbPos1;
    private int pdbPos2;
    
    public static final ProvidesKey<LinkedPositionData> KEY_PROVIDER = new ProvidesKey<LinkedPositionData>() {
      public Object getKey(LinkedPositionData item) {
        return item == null ? null : item.getId();
      }
    };
    
    public LinkedPositionData(int pos1, int pos2, double scaScore,
        double elscScore, double miScore, double omesScore, double csumScore, int pdbPos1, int pdbPos2) {
      
      this.pos1 = pos1;
      this.pos2 = pos2;
      this.scaScore = scaScore;
      this.elscScore = elscScore;
      this.miScore = miScore;
      this.omesScore = omesScore;
      this.cSumScore = csumScore;
      this.pdbPos1 = pdbPos1;
      this.pdbPos2 = pdbPos2;
      
      this.id = nextId;
      nextId++;
    }
    
    public int getId() {
      return id;
    }
    
    public int getPos1() {
      return pos1;
    }
    
    public int getPos2() {
      return pos2;
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
    
    public int getPdbPos1() {
      return pdbPos1;
    }
    
    public int getPdbPos2() {
      return pdbPos2;
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
