package task02;
class Printer {
    private int availablePages = 10;

    // Method to add pages to the tray
    public synchronized void addPages(int pages) {
        availablePages += pages;
        System.out.println("Added " + pages + " pages to the tray. Total pages: " + availablePages);
        notify(); // Notify the waiting print thread
    }

    // Method to print pages
    public synchronized void printPages(int pages) {
        System.out.println("Print request received for " + pages + " pages.");

        // Wait until enough pages are available
        while (availablePages < pages) {
            System.out.println("Insufficient pages. Waiting for more pages...");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Proceed with printing
        availablePages -= pages;
        System.out.println("Printed " + pages + " pages. Remaining pages: " + availablePages);
    }
}

// Thread to handle adding pages
class AddPagesThread extends Thread {
    private Printer printer;

    public AddPagesThread(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000); // Simulate delay in adding pages
            printer.addPages(10); // Add 10 pages to the tray
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// Thread to handle printing
class PrintThread extends Thread {
    private Printer printer;
    private int pagesToPrint;

    public PrintThread(Printer printer, int pagesToPrint) {
        this.printer = printer;
        this.pagesToPrint = pagesToPrint;
    }

    @Override
    public void run() {
        printer.printPages(pagesToPrint);
    }
}

public class Main {
    public static void main(String[] args) {
        Printer printer = new Printer();

        // Create threads
        PrintThread printThread = new PrintThread(printer, 15); // Request to print 15 pages
        AddPagesThread addPagesThread = new AddPagesThread(printer); // Adds 10 pages to tray

        // Start threads
        printThread.start();
        addPagesThread.start();
    }
}
