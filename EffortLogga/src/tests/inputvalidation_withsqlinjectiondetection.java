package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import org.junit.Test;

import asuHelloWorldJavaFX.InputValidator;

/**
 * Unit tests for the InputValidator class with SQL injection detection.
 */
public class inputvalidation_withsqlinjectiondetection {

    // List of SQL keywords to be checked for in input
    ArrayList<String> sqlKeywords = new ArrayList<>(Arrays.asList("ADD", "ADD CONSTRAINT", "ALL", "ALTER", "AND", "ANY", "BACKUP", "BETWEEN", "CASE", "CHECK", "COLUMN", "CONSTRAINT", "CREATE", "DATABASE", "DEFAULT",
            "DELETE", "DESC", "DISTINCT", "DROP", "EXEC", "EXISTS", "FOREIGN KEY", "FROM", "FULL OUTER JOIN",
            "GROUP BY", "HAVING", "INDEX", "INNER JOIN", "INSERT", "IS NULL", "IS NOT NULL", "JOIN",
            "LEFT JOIN", "LIKE", "LIMIT", "NOT", "NOT NULL", "ORDER BY", "OUTER JOIN", "PRIMARY KEY",
            "PROCEDURE", "RIGHT JOIN", "ROWNUM", "SELECT", "SET", "TABLE", "TOP", "TRUNCATE TABLE", "UNION",
            "UNION ALL", "UNIQUE", "UPDATE", "VALUES", "VIEW", "WHERE"));

    /**
     * Test case to check if a normal input does not flag the SQL injection check.
     */
    @Test
    public void initialInputTest() {
        InputValidator object = new InputValidator("input");
        boolean result = object.injectionCheck("input");
        assertEquals(false, result);
    }

    /**
     * Test case to check if an input with SQL keywords does flag the SQL injection check.
     */
    @Test
    public void testAllKeyWords() {
        InputValidator object = new InputValidator("input");
        for (String input : sqlKeywords) {
            boolean result = object.injectionCheck(input);
            assertEquals(true, result);
        }
    }

    /**
     * Test case to check if input with embedded SQL keywords flags the SQL injection check.
     */
    @Test
    public void testEmbeddedSQL() {
        InputValidator object = new InputValidator("input");
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^()";
        StringBuilder embeddedSQL = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            if (i != 4) {
                int randomIndex = random.nextInt(characters.length());
                char randomChar = characters.charAt(randomIndex);
                embeddedSQL.append(randomChar);
            } else {
                int index = random.nextInt(sqlKeywords.size());
                String keyWord = sqlKeywords.get(index);
                embeddedSQL.append(keyWord);
            }
        }
        boolean result = InputValidator.injectionCheck(embeddedSQL.toString());
        assertEquals(true, result);
    }

    /**
     * Test case to repeat the embedded SQL test multiple times.
     */
    @Test
    public void testEmbeddedSQLMultiple() {
        for (int i = 0; i < 100000; i++) {
            testEmbeddedSQL();
        }
    }
    
 
}
