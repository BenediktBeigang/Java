package GlobaleKlassen;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TesteReaderWriter {
	
			//	  x   0    1    2    3        y
	int[][] feld = {{000, 000, 000, 100},  // 0
					{000, 000, 000, 100},  // 1
					{000, 100, 100, 100},  // 2
					{000, 000, 000, 100}}; // 3
	
	@Test
	public void testKantenerkennungA(){
		int type = ReaderWriter.detectBorderPiece(0, 0, feld);
		int soll = 40;
		System.out.println(type);
		assertEquals(type, soll);
	}
	
	@Test
	public void testKantenerkennungB(){
		int type = ReaderWriter.detectBorderPiece(1, 2, feld);
		int soll = 162;
		System.out.println(type);
		assertEquals(type, soll);
	}
	
	@Test
	public void testKantenerkennungC(){
		int type = ReaderWriter.detectBorderPiece(3, 1, feld);
		int soll = 128;
		System.out.println(type);
		assertEquals(type, soll);
	}
	
	@Test
	public void testKantenerkennungD(){
		int type = ReaderWriter.detectBorderPiece(2, 2, feld);
		int soll = 34;
		System.out.println(type);
		assertEquals(type, soll);
	}
}
