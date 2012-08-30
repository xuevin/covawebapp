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
    
    //First filter out which rows will be displayed
    ArrayList<CovaDataRow> displayedData = new ArrayList<CovaDataRow>();
    for (CovaDataRow line : list) {
      if (alignmentPosToPdbPosMapping.get(line.getPos1()) == null
          || alignmentPosToPdbPosMapping.get(line.getPos2()) == null) {
        
      } else {
        displayedData.add(line);
      }
    }
    
    //Calculate sum
    int count =0;
    double sumScaScore=0;
    double sumElscScore=0;
    double sumMiScore=0;
    double sumOmesScore=0;
    double sumCSumScore=0;
     
    for(CovaDataRow line:displayedData){
      count++;
      sumScaScore+=line.getScaScore();
      sumElscScore+=line.getElscScore();
      sumMiScore+=line.getMiScore();
      sumOmesScore+=line.getOmesScore();
      sumCSumScore+=line.getCsumScore();
    }
    
    //Calculate mean
    double meanScaScore=sumScaScore/count;
    double meanElscScore=sumElscScore/count;
    double meanMiScore=sumMiScore/count;
    double meanOmesScore=sumOmesScore/count;
    double meanCSumScore=sumCSumScore/count;
     
    //Calculate variance
    double varScaScore=0;
    double varElscScore=0;
    double varMiScore=0;
    double varOmesScore=0;
    double varCSumScore=0;

    for(CovaDataRow line:displayedData){
      count++;
      varScaScore+=Math.pow(line.getScaScore()-meanScaScore,2);
      varElscScore+=Math.pow(line.getElscScore()-meanElscScore,2);
      varMiScore+=Math.pow(line.getMiScore()-meanMiScore,2);
      varOmesScore+=Math.pow(line.getOmesScore()-meanOmesScore,2);
      varCSumScore+=Math.pow(line.getCsumScore()-meanCSumScore,2);
    }
    
    //Calculate Stdv
    double stdvScaScore=Math.sqrt(varScaScore/count);
    double stdvElscScore=Math.sqrt(varElscScore/count);
    double stdvMiScore=Math.sqrt(varMiScore/count);
    double stdvOmesScore=Math.sqrt(varOmesScore/count);
    double stdvCSumScore=Math.sqrt(varCSumScore/count);

    for (CovaDataRow line : displayedData) {
        addLinkedPositionData(new LinkedPositionData(
            line.getPos1(),
            line.getPos2(),
            line.getScaScore(),
            line.getElscScore(),
            line.getMiScore(),
            line.getOmesScore(),
            line.getCsumScore(),
            alignmentPosToPdbPosMapping.get(line.getPos1()),
            alignmentPosToPdbPosMapping.get(line.getPos2()),
            getZScore(line.getScaScore(),meanScaScore,stdvScaScore),
            getZScore(line.getElscScore(),meanElscScore,stdvElscScore),
            getZScore(line.getMiScore(),meanMiScore,stdvMiScore),
            getZScore(line.getOmesScore(),meanOmesScore,stdvOmesScore),
            getZScore(line.getCsumScore(),meanCSumScore,stdvCSumScore)
            ));
        
    }
  }
  
  private double getZScore(double value, double mean, double stdv){
    return value-mean/stdv;
    
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
    private double zScaScore;
    private double zElscScore;
    private double zMiScore;
    private double zOmesScore;
    private double zCsumScore;
    
    public static final ProvidesKey<LinkedPositionData> KEY_PROVIDER = new ProvidesKey<LinkedPositionData>() {
      public Object getKey(LinkedPositionData item) {
        return item == null ? null : item.getId();
      }
    };
    
    public LinkedPositionData(int pos1, int pos2, double scaScore,
        double elscScore, double miScore, double omesScore, double csumScore, int pdbPos1, int pdbPos2,
        double zScaScore, double zElscScore, double zMiScore, double zOmesScore, double zCsumScore) {
      
      this.pos1 = pos1;
      this.pos2 = pos2;
      this.scaScore = scaScore;
      this.elscScore = elscScore;
      this.miScore = miScore;
      this.omesScore = omesScore;
      this.cSumScore = csumScore;
      this.pdbPos1 = pdbPos1; //these are used to coordinate with the protein viewer which bonds should be visible
      this.pdbPos2 = pdbPos2;
      this.zScaScore = zScaScore;
      this.zElscScore = zElscScore;
      this.zMiScore = zMiScore;
      this.zOmesScore = zOmesScore;
      this.zCsumScore = zCsumScore;
      
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
