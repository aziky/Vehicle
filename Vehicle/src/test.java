

public class test {
    public static void main(String[] args) {
        // ANSI escape code constants for text colors
        String RESET = "\u001B[0m";
        String RED = "\u001B[31m";
        String GREEN = "\u001B[32m";
        String YELLOW = "\u001B[33m";

        // Print colored text to the console
        System.out.println(GREEN + "This text is green." + RESET);
        System.out.println(YELLOW + "This text is yellow." + RESET);
       System.out.println(RED + "This text is red." + RESET);
    }
}