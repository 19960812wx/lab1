package wx;

import java.util.Scanner;

/**
 *
 * @author lostork
 *
 */
public class shiyan6 {
	/**
	 * control the whole program.
	 * @param args
	 */
	public static void main(String[] args) {
		char firstCharacter;
		String expression = null;
		final String END_COM = new String("###");//add End Command, infinite loop before...
		Scanner s = new Scanner(System.in);
		while (true) {
			String line = null;
			line = s.nextLine();// input line
			
			if (line.equals(END_COM)) {
				break;
			}
			
			firstCharacter = line.charAt(0);// first char
			if (firstCharacter == '!') {
				int posOfSimplifyCom = line.indexOf("!simplify");
				if (posOfSimplifyCom == 0) {
					simplify(line, expression);
				}
				
				int posOfDerivateCom = line.indexOf("!d/d");
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
		String allCharSet = 
				"0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ+*";
		
		//contains all valid variable characters.
		String varCharSet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
		//contains all valid operator characters.
		String optrCharSet = "+*";
		
		char iterChar = 0;
		char nextIterChar = 0;
		int expLen = expression.length(); 
		int x = 0;
		
		//check whether all characters in expression are valid.
		for (int i = 0; i < expLen; i++) {
			iterChar = expression.charAt(i);
			if (allCharSet.indexOf(iterChar) == -1) {
				System.out.println("Error! Expression has invalid character!");
				return;
			}
		}
		
		for (int i = 0; i < expLen - 1; i++) {
			iterChar = expression.charAt(i);
			nextIterChar = expression.charAt(i + 1);
			
			// check whether all variable is a single char, 
			// the program doesn't support variables like "foo","bar".
			if (varCharSet.indexOf(iterChar) > 0 
					& varCharSet.indexOf(nextIterChar) > 0) {
				System.out.println("Error! Expression has invalid variables "
						+ "(only support one-character variables)!");
				return;
			}
			
			//check whether expression has two concatenated operator such as "++","*+"
			if (optrCharSet.indexOf(iterChar) > 0 
					& optrCharSet.indexOf(nextIterChar) > 0) {
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
	public static void simplify(String line, String expression) {
		String st = null;
		int j = 13;//the index of the space after the first parameter,like the # position:"!simplify x=4#y=3"
		String space = " ";
		
		int lineLen = line.length();
		char[] lineCharArray = line.toCharArray(); 
		
		//check whether parameter format is valid ,for example "x=8 y=9 u=2"
		// program doesn't support other parameter format, like "x = 9 y = 5".
		while (j < lineLen - 1) { // �ж������Ƿ�Ϸ�?
			
			
			String str2 = String.valueOf(lineCharArray[j]);
			int b = space.indexOf(str2);
			if (b == 0) {
				j = j + 4;//move to next space after parameter.

			} else {
				System.out.println("Error,no variable");
				// ���Ϸ����Error,no
				// variable
				st = "Error";
				break;
			}
		}
		
		
		if (st == null) {
			String simplifyNew = line.replaceAll(" ", "");
			int simplifyNewLen = simplifyNew.length();// �������ֵʽ���鳤��?
			int expressionLen = expression.length();// ���ʽ���鳤��?
			
			//simplifyNew now is like "x=3y=4" pattern;
			simplifyNew = simplifyNew.substring(9, simplifyNewLen);
			char[] simplifyNewCharArray = simplifyNew.toCharArray(); // �������ֵʽ����? 
			char[] expressionCharArray  = expression.toCharArray(); // ���ʽ����? 
			
			int i = 0;
			int l = 0;
			
			
			if (simplifyNewCharArray == null) { // ��������?!simplify,����������?//if no parameters
				System.out.println(expression);
			} else {
				while (l < simplifyNewLen - 10) { // �Ա���������֬���滻

					for (i = 0; i < expressionLen; i++) {
						String str4 = 
								String.valueOf(simplifyNewCharArray[l]);
						String str1 = 
								String.valueOf(expressionCharArray[i]);

						int a = str4.indexOf(str1);
						if (a == 0) {
							String expressNew = 
									expression.replace(simplifyNewCharArray[l], simplifyNewCharArray[l + 2]);
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
			int plusOptrPos = expression.indexOf("+");
			String derivativeExpression = new String();
			String processingItem;
			while (plusOptrPos >= 0) {
				int expressionLen = expression.length();
				processingItem = expression.substring(0, plusOptrPos);
				
				//remove the first item from the expression.
				expression = expression.substring(plusOptrPos + 1, expressionLen);
				int derVarPosInProItem = processingItem.indexOf(derivativeVar);
				if (derVarPosInProItem >= 0) {
					int processingItemLen = processingItem.length();
					int derVarCount = 0;
					for (int j = 0; j < processingItemLen; j++) {
						if (derivativeVar == processingItem.charAt(j)) {
							derVarCount = derVarCount + 1;
						}
					}
					
					//line commented below may be wrong
					//String[] a1 = processingItem.split("%c", derivativeVar);
					
					String[] a1 = processingItem.split(String.valueOf(derivativeVar));
					String s;
					
					
					if (a1.length != 0) {
						a1[0] = a1[0] + "*" + derVarCount;
						
						//add the derVar removed by the String.split method, but one less because 
						//the derivative operation.
						for (int x = 0; x < derVarCount - 1; x++) {
							a1[0] = a1[0] + "*" + derivativeVar;
						}
						final StringBuffer sb = new StringBuffer();
						for (int i = 0; i < a1.length; i++) {
							sb.append(a1[i]);
						}
						s = sb.toString();
					} else {
						s = "1";
					}
					derivativeExpression = derivativeExpression + s + "+";
					plusOptrPos = expression.indexOf("+");
				} else {
					derivativeExpression += processingItem + "+";
				}
				
			}
			
			
			//process the last item.
			int expressionLen = expression.length();
			processingItem = expression;
			
			//remove the first item from the expression.
			expression = expression.substring(plusOptrPos + 1, expressionLen);
			int derVarPosInProItem2 = processingItem.indexOf(derivativeVar);
			if (derVarPosInProItem2 >= 0) {
//				;
				int processingItemLen = processingItem.length();
				int derVarCount = 0;
				for (int j = 0; j < processingItemLen; j++) {
					if (derivativeVar == processingItem.charAt(j)) {
						derVarCount = derVarCount + 1;
					}
				}
				
				//line commented below maybe wrong
				//String[] a1 = processingItem.split("%c", derivativeVar);
				
				String[] a1 = processingItem.split(String.valueOf(derivativeVar));
				String s;
				
				
				if (a1.length != 0) {
					a1[0] = a1[0] + "*" + derVarCount;
					//add the derVar removed by the String.split method, but one less because 
					//the derivative operation.
					for (int x = 0; x < derVarCount - 1; x++) {
						a1[0] = a1[0] + "*" + derivativeVar;
					}
					final StringBuffer sb = new StringBuffer();
					for (int i = 0; i < a1.length; i++) {
						sb.append(a1[i]);
					}
					s = sb.toString();
				} else {
					s = "1";
				}
				derivativeExpression = derivativeExpression + s;
				plusOptrPos = expression.indexOf("+");
			} else {
				derivativeExpression += processingItem;
			}
		
			
			
			System.out.println(derivativeExpression);
		}
	}

}
