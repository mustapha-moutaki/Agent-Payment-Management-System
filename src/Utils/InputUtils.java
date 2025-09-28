package Utils;

import java.util.Scanner;

public class InputUtils {
    private static final Scanner sc = new Scanner(System.in);

    public static String readString(String message) {
        System.out.print(message);
        return sc.nextLine();
    }

    public static int readInt(String message) {
        System.out.print(message);
        while (!sc.hasNextInt()) {
            System.out.print("==> Invalid input. Please enter a number: ");
            sc.next();
        }
        int value = sc.nextInt();
        sc.nextLine();
        return value;
    }

    public static double readDouble(String message) {
        System.out.print(message);
        while (!sc.hasNextDouble()) {
            System.out.print("==> Invalid input. Please enter a decimal number: ");
            sc.next();
        }
        double value = sc.nextDouble();
        sc.nextLine();
        return value;
    }
}
