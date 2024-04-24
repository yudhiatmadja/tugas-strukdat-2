import java.util.Scanner;
import java.util.Stack;

public class encryptionPassword<T extends CharSequence> {
    public static void validatePassword(String password) {
        if (password.length() <= 8 || password.length() >= 15) {
            throw new IllegalArgumentException("Panjang password harus antara 8 dan 15 karakter.");
        }
        for (char c : password.toCharArray()) {
            if (!Character.isLetter(c)) {
                throw new IllegalArgumentException("Password hanya boleh terdiri dari huruf abjad.");
            }
        }
    }

    public static String encryptPassword(String password) {
        Stack<Character> stack = new Stack<>();
        for (char c : password.toCharArray()) {
            stack.push((char) (c + 5));
        }

        for (int i = 0; i <= 3; i++) {
            char temp = stack.remove(0);
            stack.push(temp);
        }

        for (int i = 0; i <= 3; i++) {
            char temp = stack.pop();
            stack.add(0, temp);
        }

        StringBuilder encryptedPassword = new StringBuilder();
        while (!stack.isEmpty()) {
            encryptedPassword.append(stack.pop());
        }
        return encryptedPassword.toString();
    }

    public static <T extends CharSequence> String decryptPassword(T encryptedPassword) {
        Stack<Character> stack = new Stack<>();
        for (char c : encryptedPassword.toString().toCharArray()) {
            stack.push((char) (c - 5));
        }

        StringBuilder decryptedPassword = new StringBuilder();
        while (!stack.isEmpty()) {
            decryptedPassword.append(stack.pop());
        }

        String shiftedPassword = decryptedPassword.substring(0, decryptedPassword.length() - 3);
        String firstThreeChars = decryptedPassword.substring(decryptedPassword.length() - 3);
        decryptedPassword.setLength(0);
        decryptedPassword.append(firstThreeChars).append(shiftedPassword);

        shiftedPassword = decryptedPassword.substring(3);
        String lastThreeChars = decryptedPassword.substring(0, 3);
        decryptedPassword.setLength(0);
        decryptedPassword.append(shiftedPassword).append(lastThreeChars);

        return decryptedPassword.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Masukkan password (panjang 8 - 15 karakter, hanya huruf abjad):");
        String password = scanner.nextLine();

        try {
            validatePassword(password);
            String encryptedPassword = encryptPassword(password);
            System.out.println("Password terenkripsi: " + encryptedPassword);

            String decryptedPassword = decryptPassword(encryptedPassword);
            System.out.println("Password terdekripsi: " + decryptedPassword);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
