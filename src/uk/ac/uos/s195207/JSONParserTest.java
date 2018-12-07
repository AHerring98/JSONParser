package uk.ac.uos.s195207;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Assert;
import org.junit.Test;

public class JSONParserTest {

	//Testing for true
	@Test
	public void readTrue() {
	JSONParser parser = new JSONParser(new StringReader("rue"));
	Assert.assertEquals(true, parser.readTrue());
	}

	@Test
	public void readTrueFail1() {
	JSONParser parser = new JSONParser(new StringReader("RUE"));
	Assert.assertEquals(false, parser.readTrue());
	}
	
	@Test
	public void readTrueFail2() {
	JSONParser parser = new JSONParser(new StringReader("ruf"));
	Assert.assertEquals(false, parser.readTrue());
	}
	
	@Test
	public void readTrueFail3() {
	JSONParser parser = new JSONParser(new StringReader("123"));
	Assert.assertEquals(false, parser.readTrue());
	}
	
	@Test
	public void readTrueFail4() {
	JSONParser parser = new JSONParser(new StringReader("ru3"));
	Assert.assertEquals(false, parser.readTrue());
	}
	
	@Test
	public void readTrueFail5() {
		JSONParser parser = new JSONParser(new StringReader(" "));
		Assert.assertEquals(false, parser.readTrue());
		}
	
	//Testing for false
	@Test
	public void readFail() {
	JSONParser parser = new  JSONParser(new StringReader("alse"));
	Assert.assertEquals(true,parser.readFalse());
	}
	
	@Test
	public void readFailFail1() {
	JSONParser parser = new  JSONParser(new StringReader("ALSE"));
	Assert.assertEquals(false,parser.readFalse());
	}
	
	@Test
	public void readFailFail2() {
	JSONParser parser = new  JSONParser(new StringReader("alsf"));
	Assert.assertEquals(false,parser.readFalse());
	}
	
	@Test
	public void readFailFail3() {
	JSONParser parser = new  JSONParser(new StringReader("123"));
	Assert.assertEquals(false,parser.readFalse());
	}
	
	@Test
	public void readFailFail4() {
	JSONParser parser = new  JSONParser(new StringReader("als4"));
	Assert.assertEquals(false,parser.readFalse());
	}
	
	@Test
	public void readFailFail5() {
	JSONParser parser = new  JSONParser(new StringReader(" "));
	Assert.assertEquals(false,parser.readFalse());
	}
	
	//Testing for null
	@Test
	public void readNull() {
	JSONParser parser = new  JSONParser(new StringReader("ull"));
	Assert.assertEquals(true,parser.readNull());
	}
	
	@Test
	public void readNullFail1() {
	JSONParser parser = new  JSONParser(new StringReader("ULL"));
	Assert.assertEquals(false,parser.readNull());
	}
	
	@Test
	public void readNullFail2() {
	JSONParser parser = new  JSONParser(new StringReader("uln"));
	Assert.assertEquals(false,parser.readNull());
	}
	
	@Test
	public void readNullFail3() {
	JSONParser parser = new  JSONParser(new StringReader("123"));
	Assert.assertEquals(false,parser.readNull());
	}
	
	@Test
	public void readNullFail4() {
	JSONParser parser = new  JSONParser(new StringReader("ul4"));
	Assert.assertEquals(false,parser.readNull());
	}
	
	@Test
	public void readNullFail5() {
	JSONParser parser = new  JSONParser(new StringReader(" "));
	Assert.assertEquals(false,parser.readNull());
	}
	
	//Testing for Numbers
	@Test
	public void isDigitPass() {
	JSONParser parser = new JSONParser(new StringReader("123"));
	parser.readNext();
	Assert.assertEquals(true, parser.isDigit());
	}
	
	@Test
	public void isDigitFail1() {
	JSONParser parser = new JSONParser(new StringReader("abc"));
	parser.readNext();
	Assert.assertEquals(false, parser.isDigit());
	}
	
	@Test
	public void isDigitFail2() {
	JSONParser parser = new JSONParser(new StringReader("a23"));
	parser.readNext();
	Assert.assertEquals(false, parser.isDigit());
	}
	
	@Test
	public void isDigitFail3() {
	JSONParser parser = new JSONParser(new StringReader(" "));
	parser.readNext();
	Assert.assertEquals(false, parser.isDigit());
	}
	
	@Test
	public void isDigitFail4() {
	JSONParser parser = new JSONParser(new StringReader("!*6"));
	parser.readNext();
	Assert.assertEquals(false, parser.isDigit());
	}
	
	@Test
	public void isDigitFail5() {
	JSONParser parser = new JSONParser(new StringReader("[88]"));
	parser.readNext();
	Assert.assertEquals(false, parser.isDigit());
	}
}

