package wx;

import java.util.Scanner;

/**
 *
 * @author lostork
 *
 */
public final class shiyan6 {
	
	//contains all valid variable characters.
	private final static String varCharSet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
					
	//contains all valid operator characters.
	private final static String optrCharSet = "+*";
				
	private final static String allCharSet = 
	"0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ+*";
	
	private final static String space = " ";
	
	private shiyan6(){
		
	}
	
	/**
	 * main method, program entrance.
	 * @param args
	 */
	public static void main(String[] args) {
		char firstCharacter;
		String expression = null;
		final String END_COM = new String("###");//add End Command, infinite loop before...
		Scanner s = new Scanner(System.in);
		while (true) {
			String line;
			line = s.nextLine();// input line
			
			if (line.equals(END_COM)) {
				break;
			}
			
			firstCharacter = line.charAt(0);// first char
			if (firstCharacter == '!') {
				final int posOfSimplifyCom = line.indexOf("!simplify");
				if (posOfSimplifyCom == 0) {
					simplify(line, expression);
				}
				
				final int posOfDerivateCom = line.indexOf("!d/d");
				if (posOfDerivateCom == 0) {
					derivative(line, expression);
				}
			}
			
			if (firstCharacter != '!') {
				expression = line;
				expression(expression);
			}
		}
		s.close();
	}

	/**
	 * check whether the parameter expression is valid.
	 * if so, print the expression.
	 * else, print error info.
	 * @param expression
	 */
	public static void expression(final String expression) {
		
		//contains all valid characters in expression, if one character in expression
		//not exist in allCharSet, the expression is invalid.
		
		
		char iterChar;
		int expLen = expression.length(); 
		
		//check whether all characters in expression are valid.
		for (int i = 0; i < expLen; i++) {
			iterChar = expression.charAt(i);
			if (allCharSet.indexOf(iterChar) == -1) {
				System.out.println("Error! Expression has invalid character!");
				return;
			}
		}
		
			
		char nextIterChar;
		
		
		for (int i = 0; i < expLen - 1; i++) {
			iterChar = expression.charAt(i);
			nextIterChar = expression.charAt(i + 1);
			
			// check whether all variable is a single char, 
			// the program doesn't support variables like "foo","bar".
			if (varCharSet.indexOf(iterChar) > 0 
					&& varCharSet.indexOf(nextIterChar) > 0) {
				System.out.println("Error! Expression has invalid variables "
						+ "(only support one-character variables)!");
				return;
			}
			
			//check whether expression has two concatenated operator such as "++","*+"
			if (optrCharSet.indexOf(iterChar) > 0 
					&& optrCharSet.indexOf(nextIterChar) > 0) {
				System.out.println("Error! Expression has invalid operators.");
				return;
			}
			
		}
		
			System.out.printf("%s", expression);
	}
	
/**
 * Simplify the expression.
 * @param line
 * @param expression
 */
	public static void simplify(final String line, String expression) {
		String stringFlag = null;
		//the index of the space after the first parameter,
		//like the # position:"!simplify x=4#y=3"
		int j = 13;
		
		final int lineLen = line.length();
		final char[] lineCharArray = line.toCharArray(); 
		
		//check whether parameter format is valid ,for example "x=8 y=9 u=2"
		// program doesn't support other parameter format, like "x = 9 y = 5".
		while (j < lineLen - 1) { // �ж������Ƿ�Ϸ�?
			
			
			final String str2 = String.valueOf(lineCharArray[j]);
			final int b = space.indexOf(str2);
			if (b == 0) {
				j = j + 4;//move to next space after parameter.

			} else {
				System.out.println("Error,no variable");
				// ���Ϸ����Error,no
				// variable
				stringFlag = "Error";
				break;
			}
		}
		
		
		if (stringFlag == null) {
			String simplifyNew = line.replaceAll(" ", "");
			final int simplifyNewLen = simplifyNew.length();// �������ֵʽ���鳤��?
			final int expressionLen = expression.length();// ���ʽ���鳤��?
			
			//simplifyNew now is like "x=3y=4" pattern;
			simplifyNew = simplifyNew.substring(9, simplifyNewLen);
			final char[] simCharArray = simplifyNew.toCharArray(); // �������ֵʽ����? 
			final char[] expCharArray  = expression.toCharArray(); // ���ʽ����? 
			
			int i;
			int l = 0;
			
			
			if (simCharArray == null) { // ��������?!simplify,����������?//if no parameters
				System.out.println(expression);
			} else {
				while (l < simplifyNewLen - 10) { // �Ա���������֬���滻

					for (i = 0; i < expressionLen; i++) {
						final String str4 = 
								String.valueOf(simCharArray[l]);
						final String str1 = 
								String.valueOf(expCharArray[i]);

						final int a = str4.indexOf(str1);
						if (a == 0) {
							final String expressNew = 
									expression.replace(simCharArray[l], simCharArray[l + 2]);
							expression = expressNew;
						}
					}
					l = l + 3;//change index l to next parameter.
				}
				System.out.println(expression); // ����滻��ֵ��Ķ���ʽ
			}
		}

	}

	/**
	 * only support "!d/dx",there can't be spaces between "x" and "d".
	 * @param line
	 * @param expression
	 */
	public static void derivative(final String line, String expression) {
		char derivativeVar;
		derivativeVar = line.charAt(4);//only support one-character derivative variable.
		int derVarPos = expression.indexOf(derivativeVar);
		if (derVarPos == -1) {
			System.out.println("Error,no variable");
		}
		if (derVarPos >= 0) {
			int plusOptrPos = expression.indexOf('+');
			String derivativeExp = new String();
			String processingItem;
			while (plusOptrPos >= 0) {
				final int expressionLen = expression.length();
				processingItem = expression.substring(0, plusOptrPos);
				
				//remove the first item from the expression.
				expression = expression.substring(plusOptrPos + 1, expressionLen);
				final int derVarPosInItem = processingItem.indexOf(derivativeVar);
				if (derVarPosInItem >= 0) {
					final int processingItemLen = processingItem.length();
					int derVarCount = 0;
					for (int j = 0; j < processingItemLen; j++) {
						if (derivativeVar == processingItem.charAt(j)) {
							derVarCount = derVarCount + 1;
						}
					}
					
					//line commented below may be wrong
					//String[] a1 = processingItem.split("%c", derivativeVar);
					
					String[] proItemSplited = processingItem.split(String.valueOf(derivativeVar));
					String s;
					
					
					if (proItemSplited.length == 0) {
						s = "1";
					} else {
						proItemSplited[0] = proItemSplited[0] + "*" + derVarCount;
						
						//add the derVar removed by the String.split method, but one less because 
						//the derivative operation.
						for (int x = 0; x < derVarCount - 1; x++) {
							proItemSplited[0] = proItemSplited[0] + "*" + derivativeVar;
						}
						final StringBuffer sb = new StringBuffer();
						for (int i = 0; i < proItemSplited.length; i++) {
							sb.append(proItemSplited[i]);
						}
						s = sb.toString();
						
					}
					derivativeExp = derivativeExp + s + "+";
					plusOptrPos = expression.indexOf('+'); // NOPMD by lostork on 16-10-15 下午10:28
				} else {
					derivativeExp += processingItem + "+";
				}
				
			}
			
			
			//process the last item.
			int expressionLen = expression.length();
			processingItem = expression;
			
			//remove the first item from the expression.
			expression = expression.substring(plusOptrPos + 1, expressionLen);
			final int derVarPosInItem2 = processingItem.indexOf(derivativeVar);
			if (derVarPosInItem2 >= 0) {
//				;
				final int processingItemLen = processingItem.length();
				int derVarCount = 0;
				for (int j = 0; j < processingItemLen; j++) {
					if (derivativeVar == processingItem.charAt(j)) {
						derVarCount = derVarCount + 1;
					}
				}
				
				//line commented below maybe wrong
				//String[] a1 = processingItem.split("%c", derivativeVar);
				
				String[] proItemSplited = processingItem.split(String.valueOf(derivativeVar));
				String s;
				
				
				if (proItemSplited.length == 0) {
					s = "1";
				} else {
					proItemSplited[0] = proItemSplited[0] + "*" + derVarCount;
					//add the derVar removed by the String.split method, but one less because 
					//the derivative operation.
					for (int x = 0; x < derVarCount - 1; x++) {
						proItemSplited[0] = proItemSplited[0] + "*" + derivativeVar;
					}
					final StringBuffer stringBuffer = new StringBuffer();
					for (int i = 0; i < proItemSplited.length; i++) {
						stringBuffer.append(proItemSplited[i]);
					}
					s = stringBuffer.toString();
				}
				derivativeExp = derivativeExp + s;
				plusOptrPos = expression.indexOf('+');
			} else {
				derivativeExp += processingItem;
			}
		
			
			
			System.out.println(derivativeExp);
		}
	}

}
