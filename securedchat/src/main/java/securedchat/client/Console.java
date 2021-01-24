package securedchat.client;

import java.io.*;

public class Console {

    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static final int MAX_ROW = 80;

    private static BufferedReader _console = new BufferedReader(new InputStreamReader(System.in));

    public static String readLine() throws IOException {
        return _console.readLine();
    }

    public static void print(String text) {
        System.out.print(text);
    }    

    public static void println(String text) {
        System.out.println(text);
    }    

    public static void clear()  {
        print("\033\143");
    }

    public static void setLocation(int row, int col) {
        char escCode = 0x1B;
        print(String.format("%c[%d;%df", escCode, row, col));   
    }
}