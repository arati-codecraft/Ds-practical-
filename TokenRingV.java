import java.io.*;
import java.util.Scanner;

class TokenRing {
    private int numProcesses;
    private int tokenHolder;
    private Scanner scanner;
    private String fileName;

    public TokenRing(int numProcesses, String fileName) {
        this.numProcesses = numProcesses;
        this.tokenHolder = 0; // Initially, Process 0 holds the token
        this.scanner = new Scanner(System.in);
        this.fileName = fileName;
    }

    public void start() {
        for (int i = 0; i < numProcesses; i++) {
            tokenHolder = i;
            System.out.println("\nToken is currently with Process " + tokenHolder);

            System.out.print("Process " + tokenHolder + ", do you want to enter the critical section? (yes/no): ");
            String enterCritical = scanner.next().toLowerCase();
            scanner.nextLine(); // Consume newline

            if (enterCritical.equals("yes")) {
                System.out.print("Do you want to (1) Read or (2) Write? Enter choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (choice == 1) {
                    displayFileContents();
                } else if (choice == 2) {
                    enterCriticalSection();
                } else {
                    System.out.println("Invalid choice! Exiting critical section.");
                }
            } else {
                System.out.println("Process " + tokenHolder + " passed the token to the next process.");
            }
        }

        System.out.println("\nAll processes have executed. Exiting...");
    }

    private void enterCriticalSection() {
        System.out.println("Process " + tokenHolder + " is now in the Critical Section.");
        System.out.print("Enter the content to write to the file: ");
        
        String userContent = scanner.nextLine();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write("Process " + tokenHolder + " wrote: " + userContent);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }

        System.out.println("Process " + tokenHolder + " has exited the Critical Section.");
    }

    private void displayFileContents() {
        System.out.println("\n--- File Contents (" + fileName + ") ---");
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean isEmpty = true;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                isEmpty = false;
            }
            if (isEmpty) {
                System.out.println("[File is empty]");
            }
        } catch (IOException e) {
            System.out.println("Error reading the file or file does not exist.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the filename to use: ");
        String fileName = scanner.nextLine();

        System.out.print("Enter the number of processes in the ring: ");
        int numProcesses = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        TokenRing ring = new TokenRing(numProcesses, fileName);
        ring.start();

        scanner.close();
    }
}
