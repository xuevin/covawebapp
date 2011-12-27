package edu.cuny.hunter.xie.covaweb.server;

import org.biojava3.alignment.template.AlignedSequence;
import org.biojava3.alignment.template.SequencePair;
import org.biojava3.core.sequence.MultipleSequenceAlignment;
import org.biojava3.core.sequence.ProteinSequence;
import org.biojava3.core.sequence.compound.AminoAcidCompound;

public class MapperTool {
  //FIXME Incomplete
  public void alignTwoMSAToQuery(
      SequencePair<ProteinSequence,AminoAcidCompound> queryTo_PDB_Pair,
      MultipleSequenceAlignment<ProteinSequence,AminoAcidCompound> hmmMSA) {
   
      ProteinSequence lastSeq = hmmMSA.getAlignedSequence(hmmMSA.getSize());
      AlignedSequence<ProteinSequence,AminoAcidCompound> querySeq = queryTo_PDB_Pair.getQuery();
      System.out.println("hmmMSA:\t\t"+lastSeq);
      System.out.println("QueryPDBPair:\t"+queryTo_PDB_Pair.getLength());
      System.out.println("Original:\t"+queryTo_PDB_Pair.getQuery());
      System.out.println("PDB:\t\t"+queryTo_PDB_Pair.getTarget());
      
      
      // Iterate through the query sequence and get the corresponding position in the hmmMSA.
      int positionInHMM = 1;
      for(int i =1;i<=querySeq.getLength();i++){
        if(lastSeq.getCompoundAt(positionInHMM).equals(querySeq.getCompoundAt(i))){
          System.out.println(i+"\t"+positionInHMM + "\t" + querySeq.getCompoundAt(i));
        }else{
          while(!lastSeq.getCompoundAt(positionInHMM).equals(querySeq.getCompoundAt(i)))
          {
            positionInHMM++;
          }
          System.out.println(i+"\t"+positionInHMM + "\t" + querySeq.getCompoundAt(i));
        }
      }

  }
}
