package asuHelloWorldJavaFX;

import java.util.*;

public class InputValidator {

	public Object input;
	public int returnVal; // 0 - NormalString, 1 - String with Special Characters, 2 - Integer, 3 -
							// Double, 4 - INVALID(Unkown)
	// 5 - Number string
	HashMap<Character, Integer> alphabet = new HashMap<>();
	HashMap<Character, Integer> SpecialChar = new HashMap<>();
	HashMap<Character, Integer> Nums = new HashMap<>();

	public InputValidator(Object input) {
		this.input = input;
		returnVal = 20;
		int i = 0;
		// Create a Hashmap for the alphabet containing lowercase letters (a-z)
		for (char c = 'a'; c <= 'z'; c++) {
			this.alphabet.put(c, i);
			i++;
		}
		// Add uppercase letters (A-Z) to the alphabet Hashmap
		i = 0;// Reset i
		for (char c = 'A'; c <= 'Z'; c++) {
			alphabet.put(c, i);
			i++;
		}
		i = 0;
		for (char x = '0'; x <= '9'; x++) {
			Nums.put(x, i);
			i++;
		}

		// For special Characters
		String special = "!@#$%^&*()";
		special.toCharArray();
		i = 0;
		// Create a HashMap for special characters
		for (char c : special.toCharArray()) {
			this.SpecialChar.put(c, i);
			i++;
		}

		// NEW PROTOTYPE REFINEMENT

	}
	public InputValidator() {
		returnVal = 20;
		
	}

	// Method to checkType
	public int checkType(Object input) {
		if (input instanceof String) {
			// Check if the string contains only normal alphabet characters
			if (charactersExistInAlphabet((String) input)) {
				this.returnVal = 0; // String with only normal Strings
			}
			// Check if the string contains both normal and special characters
			else if (charactersExistInAlphabetAndSpecial((String) input)) {
				this.returnVal = 1; // SpecialChar String
			} else if (charIntegers((String) input)) {

				this.returnVal = 5; // Number String
			}
		} else if (input instanceof Integer) {
			this.returnVal = 2;
		} else if (input instanceof Double) {
			this.returnVal = 3;
		} else {
			this.returnVal = 4; // INVALID (Unknown)
		}
		return returnVal;
	}

	// Check if all characters in the input string are in the alphabet
	public boolean charactersExistInAlphabet(String input) {
		String inputString = (String) input;
		char[] inputChars = inputString.toCharArray();
		for (char c : inputChars) {
			if (!this.alphabet.containsKey(c)) {
				return false; // Character not in the alphabet
			}
		}
		return true; // All characters are in the alphabet
	}

	// Check if all characters in the input string are in either the alphabet or
	// special characters
	public boolean charactersExistInAlphabetAndSpecial(String input) {
		String inputString = (String) input;
		char[] inputChars = inputString.toCharArray();
		for (char c : inputChars) {
			if (!this.alphabet.containsKey(c) && !this.SpecialChar.containsKey(c)) {
				return false; // Character not in the alphabet or special characters
			}
		}
		return true; // All characters are in the alphabet or special characters
	}

	public boolean charIntegers(String input) {
		String inputString = (String) input;
		char[] inputChars = inputString.toCharArray();
		for (char c : inputChars) {
			if (!this.Nums.containsKey(c)) {
				return false; // Character not in the alphabet or special characters
			}
		}
		return true; // All characters are in the alphabet or special characters
	}

	// Method to check if the object passed in is a effort to inject SQL statements
	// into the query
	public static boolean injectionCheck(String s) {
		// A list of SQL keywords that the object may be inside
		ArrayList<String> sqlKeywords = new ArrayList<>(Arrays.asList("ADD", "ADD CONSTRAINT", "ALL", "ALTER", "AND", "ANY", "BACKUP", "BETWEEN", "CASE", "CHECK", "COLUMN", "CONSTRAINT", "CREATE", "DATABASE", "DEFAULT",
				"DELETE", "DESC", "DISTINCT", "DROP", "EXEC", "EXISTS", "FOREIGN KEY", "FROM", "FULL OUTER JOIN",
				"GROUP BY", "HAVING", "INDEX", "INNER JOIN", "INSERT", "IS NULL", "IS NOT NULL", "JOIN",
				"LEFT JOIN", "LIKE", "LIMIT", "NOT", "NOT NULL", "ORDER BY", "OUTER JOIN", "PRIMARY KEY",
				"PROCEDURE", "RIGHT JOIN", "ROWNUM", "SELECT", "SET", "TABLE", "TOP", "TRUNCATE TABLE", "UNION",
				"UNION ALL", "UNIQUE", "UPDATE", "VALUES", "VIEW", "WHERE"));
		String text = String.valueOf(s).toUpperCase();
		for (String keyword : sqlKeywords) {
            if (text.contains(keyword)) {
                return true; // SQL keyword found in the string
            }
        }

        // No SQL injection detected
        return false;
	}
	
}
