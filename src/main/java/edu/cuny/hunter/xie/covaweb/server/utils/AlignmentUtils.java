package edu.cuny.hunter.xie.covaweb.server.utils;

import org.biojava.bio.structure.Structure;
import org.biojava3.alignment.Alignments;
import org.biojava3.alignment.SimpleGapPenalty;
import org.biojava3.alignment.SimpleSubstitutionMatrix;
import org.biojava3.alignment.Alignments.PairwiseSequenceAlignerType;
import org.biojava3.alignment.template.SequencePair;
import org.biojava3.alignment.template.SubstitutionMatrix;
import org.biojava3.core.sequence.ProteinSequence;
import org.biojava3.core.sequence.compound.AminoAcidCompound;

public class AlignmentUtils {
  /**
   * Aligns a protein sequence to the query sequence.
   * 
   * @param query
   *          input query sequence
   * @param inputProtein
   *          input protein
   */
  public static SequencePair<ProteinSequence,AminoAcidCompound> alignPDBSequenceToQuery(
      ProteinSequence query, Structure inputProtein) {
    
    // System.out.println(inputProtein.getChain(0).getAtomGroup(0));
    // System.out.println(inputProtein.getChain(0).getAtomGroup(1));
    // System.out.println(inputProtein.getChain(0).getAtomSequence());
    
    String chainZeroAsString = inputProtein.getChain(0).getAtomSequence();
    
    ProteinSequence chainZero = new ProteinSequence(chainZeroAsString);
    
    SubstitutionMatrix<AminoAcidCompound> matrix = new SimpleSubstitutionMatrix<AminoAcidCompound>();
    SequencePair<ProteinSequence,AminoAcidCompound> pair = Alignments
        .getPairwiseAlignment(query, chainZero,
            PairwiseSequenceAlignerType.GLOBAL, new SimpleGapPenalty(), matrix);
    
    // SimpleAlignedSequence<Sequence<C>,Compound>
    
    // System.out.println("Test of MSA");
    // MultipleSequenceAlignment<AlignedSequence<ProteinSequence,AminoAcidCompound>,AminoAcidCompound>
    // msa = new
    // MultipleSequenceAlignment<AlignedSequence<ProteinSequence,AminoAcidCompound>,AminoAcidCompound>();
    // msa.addAlignedSequence(pair.getQuery());
    // msa.addAlignedSequence(pair.getTarget());
    
    // System.out.println(msa.toString());
    // System.out.println(pair.toString());
    return pair;
    
  }
}
