//Libraries
import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Account account = new Account();
        Deposit deposit = new Deposit();
        Scanner in = new Scanner(System.in);

        if(authenticate(in)) {
            while(true){
                showMenu();
                int transactionNumber = in.nextInt();
                switch(transactionNumber){
                    case 1:
                        account.displayBalance();
                        break;
                    case 2:
                        handleMainAccount(in, account);
                        break;
                    case 3:
                        handleDeposit(in, deposit);
                        break;
                    case 4:
                        handleWithdraw(in, deposit, account);
                        break;
                    case 5:
                        break;
                }
            }

        }
    }
    private static boolean authenticate(Scanner in){
        System.out.println("Hi! It's an ATM ... of the bank. Please enter your pin code to get started.");
        System.out.print("Enter your pin code:");
        int pinCode = in.nextInt();
        return pinCode == 1234;
    }
    private static void showMenu() {
        System.out.println("Welcome! \n 1)Check balance \n 2)Deposit money to the card \n 3)Deposit money \n 4)Withdraw money \n 5)Transfer money from an account to another account");
        System.out.print("Enter the transaction number:");
    }
    private static void handleMainAccount(Scanner in, Account account){
        System.out.println("Enter the amount you want to deposit:");
        double accountAmount = in.nextDouble();
        account.checkingAccount(accountAmount);
        account.currencyConverterAccount();
        account.displayBalance();
        account.saveAccountBalance();
    }

    private static void handleDeposit(Scanner in, Deposit deposit){
        System.out.println("Enter the amount you want to deposit:");
        double depositAmount = in.nextDouble();
        deposit.savingAccount(depositAmount);
        deposit.currencyConverterDeposit();
        deposit.displayDeposit();
        deposit.saveDepositBalance();
    }

    private static void handleWithdraw(Scanner in, Deposit deposit, Account account){
        System.out.println("Choose the source of funds: \n 1) Main account \n 2) Deposit");
        int sourceAccount = in.nextInt();
        System.out.println("Enter the amount you want to withdraw:");
        double withdrawMoney = in.nextDouble();

        if (sourceAccount == 1) {
            if (withdrawMoney <= account.getBalanceUSD()) {
                account.withdrawMoneyFromMainAccount(withdrawMoney);
                account.currencyConverterAccount();
                account.displayBalance();
                account.saveAccountBalance();
            } else {
                System.out.println("Insufficient funds in main account.");
            }
        } else if (sourceAccount == 2) {
            if (withdrawMoney <= deposit.getDepositUSD()) {
                deposit.withdrawMoneyFromDeposit(withdrawMoney);
                deposit.currencyConverterDeposit();
                deposit.displayDeposit();
                deposit.saveDepositBalance();
            } else {
                System.out.println("Insufficient funds in deposit account.");
            }
        } else {
            System.out.println("Invalid option. Please try again.");
        }
    }

    private static void transferMoney(Scanner in, Account account){

    }
}
class Account{
        //  Variables
        private double balanceUSD = 0;
        private double balanceEUR = 0;
        private double balanceKZT = 0;

        public Account(){
            loadAccountBalance();

        }
        public void checkingAccount(double checkingAmount){
            this.balanceUSD += checkingAmount;
            System.out.println("Account successfully funded!");
        }
        public void withdrawMoneyFromMainAccount(double amount){
            this.balanceUSD -= amount;
            System.out.println("Amount successfully withdrawn from main account!");
        }

        public double getBalanceUSD() {
        return balanceUSD;
    }

        public void displayBalance(){
            System.out.printf("On your account: \n%.1f$ \n%.1f€ \n%.1f₸", balanceUSD, balanceEUR, balanceKZT);
        }
        public void currencyConverterAccount(){
            double usdToeurRate = 0.90;
            double usdTokztRate = 480.0;

            this.balanceEUR = this.balanceUSD * usdToeurRate;
            this.balanceKZT = this.balanceUSD * usdTokztRate;
        }
        public void saveAccountBalance(){
            try(PrintWriter writer = new PrintWriter(new FileWriter("accountBalance.txt"))){
                writer.println(balanceUSD);
                writer.println(balanceEUR);
                writer.println(balanceKZT);
            }
            catch (IOException e){
                System.out.println("Error saving account balance.");
            }
        }
        public void loadAccountBalance(){
            try (BufferedReader reader = new BufferedReader(new FileReader("accountBalance.txt"))){
                balanceUSD = Double.parseDouble(reader.readLine());
                balanceEUR = Double.parseDouble(reader.readLine());
                balanceKZT = Double.parseDouble(reader.readLine());
            }
            catch(IOException | NumberFormatException e){
                System.out.println("Error loading account balance. Setting to default values.");
                balanceUSD = 0;
                balanceEUR = 0;
                balanceKZT = 0;

            }
        }

}
class Deposit{
    private double depositUSD = 0;
    private double depositEUR = 0;
    private double depositKZT = 0;

    public Deposit(){

        loadDepositBalance();

    }

    public void savingAccount(double depositAmount){
        this.depositUSD =+ depositAmount;
        System.out.println("Account successfully funded!");
    }
    public void displayDeposit(){
        System.out.printf("On your deposit: \n%.1f$ \n%.1f€ \n%.1f₸", depositUSD, depositEUR, depositKZT);
    }

    public void currencyConverterDeposit(){
        double usdToeurRate = 0.90;
        double usdTokztRate = 480.0;

        this.depositEUR = this.depositUSD * usdToeurRate;
        this.depositKZT = this.depositUSD * usdTokztRate;
    }
    public double getDepositUSD(){
        return depositUSD;
    }

    public void withdrawMoneyFromDeposit(double amount){
        this.depositUSD -= amount;
        System.out.println("Amount successfully withdrawn from deposit!");
    }
    public void saveDepositBalance(){
        try(PrintWriter writer = new PrintWriter(new FileWriter("depositBalance.txt"))){
            writer.println(depositUSD);
            writer.println(depositEUR);
            writer.println(depositKZT);
        }
        catch (IOException e){
            System.out.println("Error saving account balance.");
        }
    }
    public void loadDepositBalance(){
        try (BufferedReader reader = new BufferedReader(new FileReader("depositBalance.txt"))){
            depositUSD = Double.parseDouble(reader.readLine());
            depositEUR = Double.parseDouble(reader.readLine());
            depositKZT = Double.parseDouble(reader.readLine());
        }
        catch(IOException | NumberFormatException e){
            System.out.println("Error loading account balance. Setting to default values.");
            depositUSD = 0;
            depositEUR = 0;
            depositKZT = 0;

        }
    }

}