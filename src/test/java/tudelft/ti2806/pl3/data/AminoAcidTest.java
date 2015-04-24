package tudelft.ti2806.pl3.data;

import static junit.framework.Assert.assertTrue;
import static tudelft.ti2806.pl3.data.Gene.G;

import org.junit.Test;

import java.util.Arrays;

public class AminoAcidTest {

	@Test
	public void getAcidsTest() {
		assertTrue(Arrays.equals(AminoAcid.getAcids(Gene.getGeneString("GGG")),
				new AminoAcid[]{AminoAcid.get(Gene.getCodon(G, G, G))}));
	}
}
