package asuHelloWorldJavaFX;

public class Password {
    static String message;

    public static boolean isPasswordValid(String password) {
        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        if (password.length() < 8) {
            message = "Password must be at least 8 characters long.";
            return false;
        }

        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(ch)) {
                hasLowercase = true;
            } else if (Character.isDigit(ch)) {
                hasDigit = true;
            } else if (isSpecialCharacter(ch)) {
                hasSpecialChar = true;
            }
        }

        if (!hasUppercase) {
            message = "Password must contain at least one uppercase letter.";
        }
        if (!hasLowercase) {
            message = "Password must contain at least one lowercase letter.";
        }
        if (!hasDigit) {
            message = "Password must contain at least one digit.";
        }
        if (!hasSpecialChar) {
            message = "Password must contain at least one special character.";
        }

        return hasUppercase && hasLowercase && hasDigit && hasSpecialChar;
    }

    public static String getMessage() {
        return message;
    }

    public static boolean isSpecialCharacter(char ch) {
        String specialCharacters = "!@#$%^&*()-+=<>?/{}[]|";
        return specialCharacters.contains(String.valueOf(ch));
    }
}
