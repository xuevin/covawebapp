package edu.cuny.hunter.xie.covaweb.server;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.biojava3.core.sequence.ProteinSequence;
import org.biojava3.core.sequence.template.LightweightProfile.StringFormat;
import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import edu.cuny.hunter.xie.covaweb.server.parsers.FastaParser;
import edu.cuny.hunter.xie.covaweb.server.parsers.StockholmParser;
import edu.cuny.hunter.xie.covaweb.shared.exceptions.PipelineException;

public class PipelineTest {
  @Test
  public void showThatPartialInputPipelineWorks() throws PipelineException {
    Pipeline foo = new Pipeline(
        "MAGAASPCANGCGPSAPSDAEVVHLCRSLEVGTVMTLFYSKKSQRPERKTFQVKLETRQI"
            + "TWSRGADKIEGAIDIREIKEIRPGKTSRDFDRYQEDPAFRPDQSHCFVILYGMEFRLKTL"
            + "SLQATSEDVNMWIRGLTWLMEDTLQAATPLQIERWLRKQFYSVDRNREDRISAKDLKNM"
            + "LSQVNYRVPNMRFLRERLTDLEQRTSDITYGQFAQLYRSLMYSAQKTMDLPFLEASALRA"
            + "GERPELCRVSLPEFQQFLLEYQGELWAVDRLQVQEFMLSFLRDPLREIEEPYFFLDEFVT"
            + "FLFSKENSIWNSQLDEVCPDTMNNPLSHYWISSSHNTYLTGDQFSSESSLEAYARCLRMG"
            + "CRCIELDCWDGPDGMPVIYHGHTLTTKIKFSDVLHTIKEHAFVASEYPVILSIEDHCSIA"
            + "QQRNMAQYFKKVLGDTLLTKPVDIAADGLPSPNQLKRKILIKHKKLAEGSAYEEVPTSVM"
            + "YSENDISNSIKNGILYLEDPVNHEWYPHYFVLTSSKIYYSEETSSDQGNEDEEEPKEASG"
            + "STELHSNEKWFHGKLGAGRDGRHIAERLLTEYCIETGAPDGSFLVRESETFVGDYTLSFW"
            + "RNGKVQHCRIHSRQDAGTPKFFLTDNLVFDSLYDLITHYQQVPLRCNEFEMRLSEPVPQT"
            + "NAHESKEWYHASLTRAQAEHMLMRVPRDGAFLVRKRNEPNSYAISFRAEGKIKHCtRVQQE"
            + "GQTVMLGNSEFDSLVDLISYYEKHPLYRKMKLRYPINEEALEKIGTAEPDYGALYEGRNP"
            + "GFYVEANPMPTFKCAVKALFDYKAQREDELTFTKSAIIQNVEKQEGGWWRGDYGGKKQLW"
            + "FPSNYVEEMVSPAALEPEREHLDENSPLGDLLRGVLDVPACQIAVRPEGKNNRLFVFSIS"
            + "MASVAHWSLDVAADSQEELQDWVKKIREVAQTADARLTEGKMMERRKKIALELSELVVYC"
            + "RPVPFDEEKIGTERACYRDMSSFPETKAEKYVNKAKGKKFLQYNRLQLSRIYPKGQRLDS"
            + "SNYDPLPMWICGSQLVALNFQTPDKPMQMNQALFLAGGHCGYVLQPSVMRDEAFDPFDKS"
            + "SLRGLEPCAICIEVLGARHLPKNGRGIVCPFVEIEVAGAEYDSIKQKTEFVVDNGLNPVW"
            + "PAKPFHFQISNPEFAFLRFVVYEEDMFSDQNFLAQATFPVKGLKTGYRAVPLKNNYSEGL"
            + "ELASLLVKIDVFPAKQENGDLSPFGGASLRERSCDASGPLFHGRAREGSFEARYQQPFED"
            + "FRISQEHLADHFDGRDRRTPRRTRVNGDNRL");
    foo.run();
  }
  
  @Test
  public void showThatFullInputPipelineWorks() throws PipelineException,
      IOException {
    Pipeline foo = new Pipeline(
        Files.toString(new File(this.getClass().getClassLoader().getResource("NP_001096969.fa").getFile()),Charsets.UTF_8), 
        StockholmParser.getMSA(new FileInputStream(new File(this.getClass().getClassLoader().getResource("PF03770_seed_wQuery.sto").getFile()))).toString(StringFormat.ALN), 
        Files.toString(new File(this.getClass().getClassLoader().getResource("NP_001096969.pdb").getFile()),Charsets.UTF_8));
    foo.run();
  }
}
