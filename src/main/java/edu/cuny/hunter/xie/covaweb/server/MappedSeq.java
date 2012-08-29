package edu.cuny.hunter.xie.covaweb.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.biojava.bio.structure.Structure;
import org.biojava3.alignment.template.AlignedSequence;
import org.biojava3.alignment.template.SequencePair;
import org.biojava3.core.sequence.MultipleSequenceAlignment;
import org.biojava3.core.sequence.ProteinSequence;
import org.biojava3.core.sequence.compound.AminoAcidCompound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.cuny.hunter.xie.covaweb.server.utils.AlignmentUtils;

public class MappedSeq {
  Logger logger = LoggerFactory.getLogger(MappedSeq.class);
  
  private MultipleSequenceAlignment<ProteinSequence,AminoAcidCompound> hmmMSA;
  
  private HashMap<Integer,Integer> queryIndexToMSAIndexMap;
  private HashMap<Integer,Integer> queryIndexToPdbIndexMap;
  private HashMap<Integer,Integer> pdbIndexToMSAIndexMap;
  
  private ProteinSequence queryProteinSequence;
  private ProteinSequence pdbProteinSequence;
  
  private String pdbAlignedToMSA;
  private String alignedQueryFromMSA;

  private HashMap<Integer,Integer> msaIndexToPdbIndexMap;
  
  public MappedSeq(ProteinSequence queryProteinSequence, Structure pdb,
      MultipleSequenceAlignment<ProteinSequence,AminoAcidCompound> alignment) {
    
    SequencePair<ProteinSequence,AminoAcidCompound> queryToPDBPair = AlignmentUtils
        .alignPDBSequenceToQuery(queryProteinSequence, pdb);
    
    // assign global variables
    this.hmmMSA = alignment;
    this.queryProteinSequence = queryToPDBPair.getQuery().getOriginalSequence();
    this.pdbProteinSequence = queryToPDBPair.getTarget().getOriginalSequence();
    
    
    AlignedSequence<ProteinSequence,AminoAcidCompound> querySeq = queryToPDBPair
        .getQuery();
    ProteinSequence alignedQuerySeq = hmmMSA.getAlignedSequence(hmmMSA
        .getSize()); // Sequence at last index is the aligned sequence
    
    //Everything should be aligned to the multiple sequence alignment.
    //The query is already aligned to the multiple sequence alignment (through the HMM model)
    //The PDB will be aligned to the query, which will correspond to the positions in the multiple sequence alignment
    
    
    
    /*************************************************************
    * Iterate through the query sequence and get the corresponding position in
    * the hmmMSA.
    *************************************************************/
    try {
      this.queryIndexToMSAIndexMap = new HashMap<Integer,Integer>();
      int positionInHMM = 1;
      for (int i = 1; i <= querySeq.getLength(); i++) {
        if (alignedQuerySeq.getCompoundAt(positionInHMM).equals(
            querySeq.getCompoundAt(i))) {
          queryIndexToMSAIndexMap.put(i, positionInHMM);
          
        } else {
          while (!alignedQuerySeq.getCompoundAt(positionInHMM).equals(
              querySeq.getCompoundAt(i))) {
            positionInHMM++;
          }
          queryIndexToMSAIndexMap.put(i, positionInHMM);
        }
      }
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException(
          "The original query sequence was not found in the alignment!", e);
    }
    
    /*************************************************************
    * Iterate through the protein sequence to match up the pdb to the query
    * position and to the multiple sequence alignment
    *************************************************************/
    this.queryIndexToPdbIndexMap = new HashMap<Integer,Integer>();
    this.pdbIndexToMSAIndexMap = new HashMap<Integer,Integer>();
    this.msaIndexToPdbIndexMap = new HashMap<Integer,Integer>();
    
    char[] alignedPDBToMSA = new char[alignedQuerySeq.getLength()];
    Arrays.fill(alignedPDBToMSA, '.');
    
    for (int i = 1; i <= pdbProteinSequence.getLength(); i++) {
      // Key is the index in the query, Value is the index in the
      // PDB
      queryIndexToPdbIndexMap.put(queryToPDBPair.getIndexInQueryForTargetAt(i), i);
      pdbIndexToMSAIndexMap.put(i,queryIndexToMSAIndexMap.get(queryToPDBPair.getIndexInQueryForTargetAt(i)));
      msaIndexToPdbIndexMap.put(queryIndexToMSAIndexMap.get(queryToPDBPair.getIndexInQueryForTargetAt(i)),i);

      logger.debug("Index in PDB: " + i + 
          " Index in Query: " + queryToPDBPair.getIndexInQueryForTargetAt(i) +
          " Index in MSA: " + pdbIndexToMSAIndexMap.get(i)+ 
          "\t" + pdbProteinSequence.getCompoundAt(i).toString().charAt(0));
      
      
 
      alignedPDBToMSA[queryIndexToMSAIndexMap.get(queryToPDBPair.getIndexInQueryForTargetAt(i))-1]=pdbProteinSequence.getCompoundAt(i).toString().charAt(0);
    }
    
    
    this.pdbAlignedToMSA = new String(alignedPDBToMSA);
    this.alignedQueryFromMSA = alignedQuerySeq.toString();
    
    // Aligned Query Seq is from the the HMM Alignment.
    // logger.debug("AlignedQueryFromMSA: " + alignedQuerySeq);
     logger.debug("AlignedQueryFromMSA: " + alignedQueryFromMSA);
     logger.debug("         AlignedPDB: " + pdbAlignedToMSA);
    
  
    
  }
  
  public int getIndexInPDBForQueryAt(int queryIndex) {
    if (queryIndexToPdbIndexMap.get(queryIndex) == null) {
      return -1;
    } else {
      return queryIndexToPdbIndexMap.get(queryIndex);
    }
  }
  
  public int getIndexInHmmMSAForQueryAt(int queryIndex) {
    if (queryIndexToMSAIndexMap.get(queryIndex) == null) {
      return -1;
    } else {
      return queryIndexToMSAIndexMap.get(queryIndex);
    }
  }
  
  public int getIndexInHmmMSAForPDBAt(int pdbIndex) {
    if(pdbIndexToMSAIndexMap.get(pdbIndex)==null){
      return -1;
    }else {
      return pdbIndexToMSAIndexMap.get(pdbIndex);
    }
  }
  
  public ProteinSequence getQuerySequence() {
    return queryProteinSequence;
  }
  
  public ProteinSequence getPDBSequence() {
    return pdbProteinSequence;
  }
  
  public MultipleSequenceAlignment<ProteinSequence,AminoAcidCompound> getHmmMSA() {
    return hmmMSA;
  }
  
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("QueryIndex\tAlignmentIndex\tPDBIndex\tCompound\n");
    for (int i = 1; i <= queryProteinSequence.getLength(); i++) {
      builder.append(i + "\t\t" + queryIndexToMSAIndexMap.get(i) + "\t\t"
          + queryIndexToPdbIndexMap.get(i) + "\t\t" + queryProteinSequence.getCompoundAt(i)
          + "\n");
    }
    return builder.toString();
  }
  
  public String toAlignedString() {
    StringBuilder scale = new StringBuilder();
    for(int i =10;i<alignedQueryFromMSA.length();i+=10){
      scale.append(String.format("%10d", i));
    }
    
    return 
        "______________Scale " + scale.toString().replaceAll(" ", ".") + "\n" +
        "AlignedQueryFromMSA " + alignedQueryFromMSA + "\n" + 
        "_________AlignedPDB " + pdbAlignedToMSA + "\n";
    
  }

  public HashMap<Integer,Integer> getAlnPosToPdbPosMap() {
    return msaIndexToPdbIndexMap;
  }
  
}
