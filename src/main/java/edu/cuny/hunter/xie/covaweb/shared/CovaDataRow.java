package edu.cuny.hunter.xie.covaweb.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CovaDataRow implements IsSerializable {
  /**
   * 
   */
  private int pos1;
  private int pos2;
  private double scaScore;
  private double elscScore;
  private double miScore;
  private double omesScore;
  private double csumScore;
  private double randomScore;
  
  public CovaDataRow() {
    
  }
  
  public CovaDataRow(int pos1, int pos2, double scaScore, double elscScore,
      double miScore, double omesScore, double csumScore, double randomScore) {
    
    this.pos1 = pos1;
    this.pos2 = pos2;
    this.scaScore = scaScore;
    this.elscScore = elscScore;
    this.miScore = miScore;
    this.omesScore = omesScore;
    this.csumScore = csumScore;
    this.randomScore = randomScore;
    
  }
  //The position in the alignment
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
  
  public double getCsumScore() {
    return csumScore;
  }
  
  public double getRandomScore() {
    return randomScore;
  }
  
  @Override
  public String toString() {
    return pos1 + "\t" + pos2 + "\t" + scaScore + "\t" + elscScore + "\t"
        + miScore + "\t" + omesScore + "\t" + csumScore + "\t" + randomScore;
  }
}
