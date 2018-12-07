package uk.ac.uos.s195207;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.Reader;
import java.io.IOException;

public class JSONParser {

	public Reader reader = null;
	
	public JSONParser(Reader stringReader) {
		this.reader= stringReader;
	}
	
	public static void main(String[] args) {
	
	JSONParser parser = new JSONParser(new StringReader("rue"));
	System.out.println("Is it true:" + parser.readTrue());
	
	parser = new JSONParser(new StringReader("alse"));
	System.out.println("Is it false:" + parser.readFalse());
		
	parser = new JSONParser(new StringReader("ull"));
	System.out.println("Is it null:" + parser.readNull());
	
	parser = new JSONParser(new StringReader("134"));
	parser.readNext();
	System.out.println("Number:" + parser.readInteger());
	
	parser = new JSONParser(new StringReader("[10, 20, 70, 69, -149, 2018903]"));
	parser.readNext();
	System.out.println("Number:" + parser.readArray());
	
	parser = new JSONParser (new StringReader ("{\"id\":\"0001\",\"type\":\"donut\",\"name\":\"Cake\",\"ppu\":55,\"batters\":{\"batter\":[{\"id\":\"1001\",\"type\":\"Regular\"},{\"id\":\"1002\",\"type\":\"Chocolate\"},{\"id\":\"1003\",\"type\":\"Blueberry\"},{\"id\":\"1004\",\"type\":\"Devil's Food\"}]},\"topping\":[{\"id\":\"5001\",\"type\":\"None\"},{\"id\":\"5002\",\"type\":\"Glazed\"},{\"id\":\"5005\",\"type\":\"Sugar\"},{\"id\":\"5007\",\"type\":\"Powdered Sugar\"},{\"id\":\"5006\",\"type\":\"Chocolate with Sprinkles\"},{\"id\":\"5003\",\"type\":\"Chocolate\"},{\"id\":\"5004\",\"type\":\"Maple\"}]}"));
	
	try {
		HashMap<String, Object>map =  parser.parse();
		System.out.println("id is : " + ((HashMap<String, Object>) ((Object[])((HashMap<String,Object>) map.get("batters")).get("batter"))[3]).get("id"));
	} catch (JSONFormatException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
	public HashMap <String, Object> parse() throws JSONFormatException{
		if (!readNextExpected('{')) {
			return null;
		}
		return readObject();
	}
	
	public char presentCharacter; 
	
	/**
	 * This boolean for readTrue evaluates the inputs for the outcome r, u ,e. If the following is correct this will return true. If the following characters are not the ones stated it will return false.
	 * @return boolean which will give a true value.
	 */
	public boolean readTrue() {
		char [] array = new char[] {
				'r', 'u', 'e'
		};
		if (readNextExpected(array)==true) {
			readNext();
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * This boolean for readFalse evaluates the inputs for the outcome to be a, l, s, e. If the following is correct this will be true and return the correct false. If the following characters are not the ones stated it will flag up as false.
	 * @return boolean will return true and give a false output.
	 */
	public boolean readFalse() {
		char [] array = new char [] {
				'a', 'l', 's', 'e'
		};
		if(readNextExpected(array)==true) {
			readNext();
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * This boolean for readNull evaluates the inputs for the outcome to be u, l ,l. If the following is correct this will be true and return the null. If the following characters are not the ones stated it will flag up as false.
	 * @return boolean will return true and give a null output.
	 */
	public boolean readNull() {
		char [] array = new char[]{
			'u', 'l', 'l'
		};
		if(readNextExpected(array)==true) {
			readNext();
			return true;
		}
		else {
			return false;
		}
	}		
/**
 * This method will evaluate each of the following first values, if correct it will return them with the relevant outcome to read a parser. If not it will raise and exception existing value has failed. 
 * @return Object 
 * @throws JSONFormatException
 */
public Object readValue()throws JSONFormatException {
	switch (presentCharacter){
	
	case '"':
		return readString();
		
	case 't':
		if( readTrue()) {
		return true;
		}
		throw new JSONFormatException("failed to read true");
	
	case 'f':
		if( readFalse()) {
			return false;
		}
		throw new JSONFormatException("failed to read false");
		
	case 'n':
		if( readNull()) {
		return null;
		}
		throw new JSONFormatException("failed to read null");
			
	case '{':
		return readObject();
		
	case '[':
		return readArray();
		
		case '-':
			return readInteger();
		case '1':
			return readInteger();
		case '2':
			return readInteger();
		case '3':
			return readInteger();
		case '4':
			return readInteger();
		case '5':
			return readInteger();
		case '6':
			return readInteger();
		case '7':
			return readInteger();
		case '8':
			return readInteger();
		case '9':
			return readInteger();
		case '0':
			return readInteger();
		default: 
		throw new JSONFormatException("Character not expected:" + presentCharacter);
		}
	}
	
/**
 * This method is given the responsibility to understand the beginning of a string using the present character.
 * @return 
 */
	public String readString() {
		StringBuilder theCharacterString = new StringBuilder();
		readNext();
		
		while (presentCharacter != '"') {
			theCharacterString.append(presentCharacter);
			readNext();
	}
			readNext();
		return theCharacterString.toString();
	}
	
	/**
	 * This method is informing the present character method that it should understand when a character is finished to store the spaces between to allow the reader to carry on.
	 */
	public void readWhitespace() {
	readWhitespace(true);	
	}
	
	public void readWhitespace(boolean readNext) {
		if (readNext == true) {
		readNext();
		}
		while(Character.isWhitespace(presentCharacter)) {
			readNext();
		}
	}
	
	public String readKey() {
		StringBuilder theCharacterString = new StringBuilder();
		readNext();
		
		while (presentCharacter != '"') {
			theCharacterString.append(presentCharacter);
			readNext();
		}
		return theCharacterString.toString();
	}
	/**
	 * This readObject method will review the string step by step for each character and space. If the this method finds an error it will raise this as an exception to return null.
	 * @return
	 * @throws JSONFormatException
	 */
	public HashMap<String, Object> readObject() throws JSONFormatException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		do {
			readWhitespace();
			if (presentCharacter == '}') {
				break;
			}
			String key = readKey();
			readWhitespace();
			if (presentCharacter != ':') {
				//TODO:Throw error
				return null;
			}
			readWhitespace();
			Object value = readValue();
			readWhitespace(false);
			map.put(key,value);
		}
			
		while(presentCharacter == ',');
		readWhitespace(false);
		
		if (presentCharacter != '}') {
			//TODO: Throw error
			return null; // replace when throwing error
		}
		readNext();
		return map;
	}
	/**
	 * This readArray method has the task to step through a list. Using the present character method to read the next value and the end of an array, if an error appears it will raise an exception to throw null. 
	 * @return
	 */
	public Object[] readArray() {
		List<Object> list = new ArrayList<Object>();
		do {
			readWhitespace();
			try {
				if(presentCharacter == ']') {
					break;
				}
				Object o = readValue();
			readWhitespace(false);
			list.add(o);
			
			} catch (JSONFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		while(presentCharacter == ',');
		if (presentCharacter != ']') {
			//TODO: Throw error
			return null; // replace when throwing error
		}
		readNext();
		return list.toArray();
	}
	
	public void readNext( ) {
		try {
		presentCharacter = (char)reader.read();
		}catch (IOException e) {
			//TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean readNextExpected(char toCompare) {
		readNext();
		if (presentCharacter == toCompare) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean readNextExpected(char[] arrayToCompare ) {
		for (char charInArray: arrayToCompare) {
			if (readNextExpected (charInArray)==false) {
				return false; 
			}
		}
		return true;
	}
	
	public int readInteger() {
		StringBuilder builder = new StringBuilder();
		builder.append(presentCharacter);
		readNext();
		while(isDigit()) {
			builder.append(presentCharacter);
			readNext();
		}
		return Integer.valueOf(builder.toString());
	}
	
	public boolean isDigit() {
		return Character.isDigit(presentCharacter);
//		return presentCharacter >= '0' && presentCharacter <= '9';
	}
	
}
	

