package edu.cuny.hunter.xie.covaweb.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.biojava3.core.sequence.MultipleSequenceAlignment;
import org.biojava3.core.sequence.ProteinSequence;
import org.biojava3.core.sequence.compound.AminoAcidCompound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import covariance.algorithms.ConservationSum;
import covariance.algorithms.ELSCCovariance;
import covariance.algorithms.JavaSCA;
import covariance.algorithms.MICovariance;
import covariance.algorithms.OmesCovariance;
import covariance.algorithms.RandomScore;
import covariance.algorithms.ScoreGenerator;
import covariance.datacontainers.Alignment;
import edu.cuny.hunter.xie.covaweb.shared.CovaDataRow;

public class ExecuteCOVA {
  public static Logger logger = LoggerFactory.getLogger(ExecuteCOVA.class);
  
  public static ArrayList<CovaDataRow> getOutputFromAll(File file) throws Exception {
    Alignment a = new Alignment("1", file, false);
    return getOutputFromAll(a);
  }
  
  public static ArrayList<CovaDataRow> getOutputFromBioJavaMSA(
      MultipleSequenceAlignment<ProteinSequence,AminoAcidCompound> msa)
      throws Exception {
    logger.debug("Writing file to disk");
    File file;
    file = File.createTempFile("cova", "tmp");
    BufferedWriter out = new BufferedWriter(new FileWriter(file));
    List<ProteinSequence> listOfSequences = msa.getAlignedSequences();
    
    StringBuilder builder = new StringBuilder();
    for (ProteinSequence item : listOfSequences) {
      builder.append(item.getAccession() + "\t" + item.toString() + "\n");
    }
    
    out.write(builder.toString());
    out.close();
    file.deleteOnExit();
    return getOutputFromAll(file);
    
  }
  
  public static ArrayList<CovaDataRow> getOutputFromAll(Alignment a) throws Exception {
    logger.info("Executing Covariance Algorithms");
    
    // Run each algorithm
    JavaSCA sca = new JavaSCA(a);
    ELSCCovariance elsc = new ELSCCovariance(a);
    MICovariance mi = new MICovariance(a);
    OmesCovariance omes = new OmesCovariance(a);
    ConservationSum csum = new ConservationSum(a);
    RandomScore random = new RandomScore();
    
    ArrayList<CovaDataRow> results = new ArrayList<CovaDataRow>();
    
    // List<String> list = new ArrayList<String>();
    // list.add("i\tj\tsca\telsc\tmi\tomes\tcsum\trandom");
    
    for (int i = 0; i < a.getNumColumnsInAlignment(); i++) {
      if (a.columnHasValidResidue(i)) {
        for (int j = i + 1; j < a.getNumColumnsInAlignment(); j++) {
          if (a.columnHasValidResidue(j)) {
            results.add(new CovaDataRow(i, j, sca.getScore(a, i, j), elsc
                .getScore(a, i, j), mi.getScore(a, i, j), omes
                .getScore(a, i, j), csum.getScore(a, i, j), 0));
            
            if(results.size()==100){
              return results;
            }
            // list.add(i + "\t" + j + "\t" + sca.getScore(a, i, j) + "\t"
            // + elsc.getScore(a, i, j) + "\t" + mi.getScore(a, i, j) + "\t"
            // + omes.getScore(a, i, j) + "\t" + csum.getScore(a, i, j) + "\t"
            // + random.getScore(a, i, j));
          }
        }
      }
    }
    logger.debug("Covariance Algorithms Complete");
    
    return results;
  }
  
  public static List<String> getOutputFromJavaSCA(File file) {
    try {
      Alignment a = new Alignment("1", file, false);
      JavaSCA sca = new JavaSCA(a);
      
      return getListFromAlignment(a, sca);
    } catch (Exception e) {
      return null;
    }
    
  }
  
  public static List<String> getOutputFromELSCCovariance(File file) {
    try {
      Alignment a = new Alignment("1", file, false);
      ELSCCovariance elsc = new ELSCCovariance(a);
      
      return getListFromAlignment(a, elsc);
    } catch (Exception e) {
      return null;
    }
    
  }
  
  public static List<String> getOutputFromMICovariance(File file) {
    try {
      Alignment a = new Alignment("1", file, false);
      MICovariance mi = new MICovariance(a);
      
      return getListFromAlignment(a, mi);
    } catch (Exception e) {
      return null;
    }
  }
  
  public static List<String> getOutputFromOmesCovariance(File file) {
    try {
      Alignment a = new Alignment("1", file, false);
      OmesCovariance omes = new OmesCovariance(a);
      
      return getListFromAlignment(a, omes);
    } catch (Exception e) {
      return null;
    }
  }
  
  public static List<String> getOutputFromConservationSum(File file) {
    try {
      Alignment a = new Alignment("1", file, false);
      ConservationSum cs = new ConservationSum(a);
      
      return getListFromAlignment(a, cs);
    } catch (Exception e) {
      return null;
    }
  }
  
  public static List<String> getOutputFromRandomScore(File file) {
    try {
      Alignment a = new Alignment("1", file, false);
      RandomScore rs = new RandomScore();
      
      return getListFromAlignment(a, rs);
    } catch (Exception e) {
      return null;
    }
  }
  
  private static List<String> getListFromAlignment(Alignment a,
      ScoreGenerator sg) throws Exception {
    List<String> list = new ArrayList<String>();
    list.add("i\tj\tscore");
    
    for (int i = 0; i < a.getNumColumnsInAlignment(); i++) {
      if (a.columnHasValidResidue(i)) {
        for (int j = i + 1; j < a.getNumColumnsInAlignment(); j++) {
          if (a.columnHasValidResidue(j)) {
            list.add(i + "\t" + j + "\t" + sg.getScore(a, i, j));
          }
        }
      }
    }
    return list;
  }
}
