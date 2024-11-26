package lab06;
class JointAccount {
    private int balance = 50000;

    // Synchronized method to handle withdrawals
    public synchronized void withdraw(String accountHolder, int amount) {
        System.out.println(accountHolder + " is attempting to withdraw: " + amount);

        if (amount <= balance) {
            System.out.println(accountHolder + " successfully withdrew: " + amount);
            balance -= amount;
        } else {
            System.out.println("Insufficient balance for " + accountHolder + ". Current balance: " + balance);
        }
        System.out.println("Remaining balance: " + balance);
    }
}

class AccountHolder extends Thread {
    private JointAccount account;
    private String name;
    private int withdrawalAmount;

    public AccountHolder(JointAccount account, String name, int withdrawalAmount) {
        this.account = account;
        this.name = name;
        this.withdrawalAmount = withdrawalAmount;
    }

    @Override
    public void run() {
        account.withdraw(name, withdrawalAmount);
    }
}

public class Main {
    public static void main(String[] args) {
        JointAccount jointAccount = new JointAccount();

        // Creating threads for two account holders
        AccountHolder userA = new AccountHolder(jointAccount, "User A", 45000);
        AccountHolder userB = new AccountHolder(jointAccount, "User B", 20000);

        // Starting threads
        userA.start();
        userB.start();
    }
}

