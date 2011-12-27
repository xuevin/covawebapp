package edu.cuny.hunter.xie.covaweb.server;

import static org.junit.Assert.*;

import java.io.IOException;

import org.biojava3.core.sequence.ProteinSequence;
import org.junit.Test;

import edu.cuny.hunter.xie.covaweb.server.external.PfamService;

public class PfamServicesTest {
  
  @Test
  public void testGetHMMFromProteinSequence() throws IOException, IllegalArgumentException {
    ProteinSequence seq = new ProteinSequence(
        ("malypsssrprtlmeqllarkieaaaaaggapsaatatptasgsatptpsptsp"
            + "nppavggmglpmpltlglglglgmgmgvvsrlrrtdsldstsslgslafgedvc"
            + "rcddcllgivdlyvisaaeaakkkssgwrkirnivqwtpffqtykkqrypwvql"
            + "aghqgnfkagpepgtvlkklcpkeeecfqilmhdllrpyvpvykgqvtsedgel"
            + "ylqlqdllsdyvqpcvmdckvgvrtyleeelskakekpklrkdmydkmiqidsh"
            + "aptaeehaakavtkprymvwretisstatlgfriegikksdgtsskdfkttksr"
            + "eqiklafleflsghphilpryiqrlrairatlavseffqthevigssllfvhdq"
            + "thasiwlidfaktvelppqlridhysawkvgnhedgyliginnlidifvelqas"
            + "meaeahaqaqaeaiqspvsgsggdqaeqtgeeskp").toUpperCase());
    PfamService.getPfamServiceObj(seq);
  }
  
  @Test
  public void testGetHMMFromProteinSequence_MultipleDomains() throws IOException {
    ProteinSequence seq = new ProteinSequence(
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
            + "NAHESKEWYHASLTRAQAEHMLMRVPRDGAFLVRKRNEPNSYAISFRAEGKIKHCRVQQE"
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
    PfamService.getPfamServiceObj(seq);
    
  }
  
  @Test
  public void testGetUnallignedSeq() throws IOException{
    PfamService.getListOfSeedSequences("IPK");
  }
}
