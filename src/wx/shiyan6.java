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
		int expLen = expression.length(); // NOPMD by lostork on 16-10-13 下午10:57 
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
 * 
 * @param str
 * @param express
 */
	public static void simplify(String str, String express) {
		String st = null;
		int j = 13;
		String str3 = " ";
		int len = str.length();
		char[] arr = str.toCharArray(); // NOPMD by lostork on 16-10-13 下午10:59
		while (j < len - 1) { // �ж������Ƿ�Ϸ�
			String str2 = String.valueOf(arr[j]);
			int b = str3.indexOf(str2);
			if (b == 0) {
				j = j + 4;

			} else {
				System.out.println("Error,no variable");
				// ���Ϸ����Error,no
				// variable
				st = "Error";
				break;
			}
		}
		if (st == null) {
			String simplifyNew = str.replaceAll(" ", "");
			int len1 = simplifyNew.length();// �������ֵʽ���鳤��
			int len2 = express.length();// ���ʽ���鳤��
			simplifyNew = simplifyNew.substring(9, len1);
			char[] arr1 = simplifyNew.toCharArray(); // �������ֵʽ���� // NOPMD by lostork on 16-10-13 下午10:58
			char[] arr2 = express.toCharArray(); // ���ʽ���� // NOPMD by lostork on 16-10-13 下午10:37
			int i = 0;
			int l = 0;
			if (arr1 == null) { // �������Ϊ!simplify,���������ʽ
				System.out.println(express);
			} else {
				while (l < len1 - 10) { // �Ա���������֬���滻

					for (i = 0; i < len2; i++) {
						String str4 = 
								String.valueOf(arr1[l]);
						String str1 = 
								String.valueOf(arr2[i]);

						int a = str4.indexOf(str1);
						if (a == 0) {
							String expressNew = 
									express.replace(arr1[l], arr1[l + 2]);
							express = expressNew;
						}
					}
					l = l + 3;
				}
				System.out.println(express); // ����滻��ֵ��Ķ���ʽ
			}
		}

	}

	/**derivative.
	 * derivative
	 * @param str
	 * @param express
	 */
	public static void derivative(final String str, String express) {
		char num1;
		num1 = str.charAt(4);
		int position1 = express.indexOf(num1);
		if (position1 == -1) {
			System.out.println("Error,no variable");
		}
		if (position1 >= 0) {
			int position2 = express.indexOf("+");
			String str5 = null;
			String str1;
			while (position2 >= 0) {
				int a = express.length();
				str1 = express.substring(0, position2);
				express = express.substring(position2 + 1, a);
				int position3 = str1.indexOf(num1);
				if (position3 >= 0) {
//					;
					int num3 = str1.length();
					int ad = 0;
					for (int j = 0; j < num3; j++) {
						if (num1 == str1.charAt(j)) {
							ad = ad + 1;
						}
					}
					String[] a1 = str1.split("%c", num1);
					a1[0] = a1[0] + "*" + ad;
					for (int x = 0; x < ad; x++) {
						a1[0] = a1[0] + "*" + num1;
					}
					final StringBuffer sb = new StringBuffer();
					for (int i = 0; i < a1.length; i++) {
						sb.append(a1[i]);
					}
					String s = sb.toString();
					str5 = str5 + s;
				}
				System.out.printf("%s", str5);
			}
		}
	}

}
