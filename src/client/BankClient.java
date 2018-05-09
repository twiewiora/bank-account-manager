package client;

import gen.thrift.*;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class BankClient {

    public static final Integer PORT = 9091;
//    public static final Integer PORT = 9092;

    private static final DecimalFormat df = new DecimalFormat("###.##");

    public static void main(String [] args) {

        String host = "localhost";

        TProtocol protocol;
        TTransport transport;

        AccountFactory.Client accountFactoryClient;
        AccountService.Client accountServiceClient;
        PremiumAccountService.Client premiumAccountServiceClient;
        transport = new TSocket(host, PORT);

        try {
            System.out.println("Connecting to Bank Server ...");
            protocol = new TBinaryProtocol(transport, true, true);

            accountFactoryClient = new AccountFactory.Client(new TMultiplexedProtocol(protocol, "AccountFactory"));
            accountServiceClient = new AccountService.Client(new TMultiplexedProtocol(protocol, "AccountService"));
            premiumAccountServiceClient = new PremiumAccountService.Client(new TMultiplexedProtocol(protocol, "PremiumAccountService"));

            transport.open();

            String line;
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            do {
                System.out.println("Client console - available commands:");
                System.out.println("1. CREATE - create account");
                System.out.println("2. LOGIN - login to account");
                System.out.println("3. PREMIUM-LOGIN - login to account");
                System.out.println("4. CLOSE - close console");
                System.out.println("Enter one of commands ...");
                System.out.print("/> ");
                System.out.flush();
                try {
                    line = input.readLine();
                    if (line.toLowerCase().equals("create") || line.equals("1")) {
                        createAccount(accountFactoryClient);
                    } else if (line.toLowerCase().equals("login") || line.equals("2")) {
                        loginToAccount(accountServiceClient);
                    } else if (line.toLowerCase().equals("premium-login") || line.equals("3")) {
                        premiumLoginToAccount(premiumAccountServiceClient);
                    } else if (line.toLowerCase().equals("close") || line.equals("4")) {
                        transport.close();
                        break;
                    } else {
                        System.out.println("[FAIL] Wrong command");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } while (true);

        } catch (TException e) {
            e.printStackTrace();
        } finally {
            transport.close();
        }
    }

    private static void createAccount(AccountFactory.Client accountFactoryClient) {
        System.out.println("=============================================================");
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        AccountData accountData = new AccountData();

        System.out.println("Please fill a form:");
        try {
            System.out.print("First name: ");
            accountData.setFirstName(input.readLine());
            System.out.print("Last name: ");
            accountData.setLastName(input.readLine());
            System.out.print("PESEL: ");
            accountData.setPesel(input.readLine());
            System.out.print("Monthly income: ");
            accountData.setIncome(new Money(Double.parseDouble(input.readLine()), CurrencyType.PLN));
            AccountConfirmation accountConfirmation = accountFactoryClient.createAccount(accountData);
            System.out.println("=============================================================");
            System.out.println("Account created, your userID(PESEL): " + accountConfirmation.getUserID());
            if (accountConfirmation.isIsPremium()) {
                System.out.println("Your account is Premium Account");
            } else {
                System.out.println("Your account is Normal Account");
            }
            System.out.println("=============================================================");
        } catch (NotSupportedCurrencyException e) {
            System.out.println("=============================================================");
            System.out.println("[FAIL] Client currency is not support");
            System.out.println("=============================================================");
        } catch (AccountExistsException e) {
            System.out.println("=============================================================");
            System.out.println("[FAIL] Account with that PESEL exists");
            System.out.println("=============================================================");
        } catch (TException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void loginToAccount(AccountService.Client accountServiceClient) {
        System.out.println("=============================================================");
        String line;
        String userID;
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("Your userID: ");
            userID = input.readLine();
            accountServiceClient.authenticateUser(userID);
            System.out.println("=============================================================");
            System.out.println("Successful login");
            System.out.println("=============================================================");
        } catch (AuthenticationException e) {
            System.out.println("=============================================================");
            System.out.println("[FAIL] Authentication failed");
            System.out.println("=============================================================");
            return;
        } catch (IOException | TException e) {
            e.printStackTrace();
            return;
        }

        do {
            System.out.println("Client ID: " + userID);
            System.out.println("1. ACCOUNT-STATE - show current account state");
            System.out.println("2. LOGOUT - logout client");
            System.out.println("Enter one of commands ...");
            System.out.print("/> ");
            System.out.flush();
            try {
                line = input.readLine();
                if (line.toLowerCase().equals("account-state") || line.equals("1")) {
                    Money accountState = accountServiceClient.getAccountState(userID);
                    System.out.println("=============================================================");
                    System.out.println("Account state = " + BigDecimal.valueOf(accountState.getValue())
                            + " " + accountState.getCurrency().toString());
                    System.out.println("=============================================================");
                } else if (line.toLowerCase().equals("logout") || line.equals("2")) {
                    System.out.println("=============================================================");
                    System.out.println("Successful logout");
                    System.out.println("=============================================================");
                    break;
                } else {
                    System.out.println("=============================================================");
                    System.out.println("[FAIL] Wrong command");
                    System.out.println("=============================================================");
                }
            } catch (IOException | TException e) {
                e.printStackTrace();
                break;
            }
        } while (true);
    }

    private static void premiumLoginToAccount(PremiumAccountService.Client premiumAccountServiceClient) {
        System.out.println("=============================================================");
        String line;
        String userID;
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("Your userID: ");
            userID = input.readLine();
            premiumAccountServiceClient.authenticateUser(userID);
            System.out.println("=============================================================");
            System.out.println("Successful login");
            System.out.println("=============================================================");
        } catch (AuthenticationException e) {
            System.out.println("=============================================================");
            System.out.println("Authentication failed");
            System.out.println("=============================================================");
            return;
        } catch (IOException | TException e) {
            e.printStackTrace();
            return;
        }

        do {
            System.out.println("Client ID: " + userID);
            System.out.println("1. ACCOUNT-STATE - show current account state");
            System.out.println("2. LOAN-APPLY - show current account state");
            System.out.println("3. LOGOUT - logout client");
            System.out.println("Enter one of commands ...");
            System.out.print("/> ");
            System.out.flush();
            try {
                line = input.readLine();
                if (line.toLowerCase().equals("account-state") || line.equals("1")) {
                    Money accountState = premiumAccountServiceClient.getAccountState(userID);
                    System.out.println("=============================================================");
                    System.out.println("Account state = " + BigDecimal.valueOf(accountState.getValue())
                            + " " + accountState.getCurrency().toString());
                    System.out.println("=============================================================");
                } else if (line.toLowerCase().equals("loan-apply") || line.equals("2")) {
                    LoanRequest loanRequest = new LoanRequest();
                    Money loan = new Money();

                    System.out.println("=============================================================");
                    System.out.println("Please fill a form:");
                    System.out.print("Loan currency: ");
                    loan.setCurrency(CurrencyType.valueOf(input.readLine()));
                    System.out.print("Loan value: ");
                    loan.setValue(Double.parseDouble(input.readLine()));
                    System.out.print("Months: ");
                    loanRequest.setMonths(Integer.parseInt(input.readLine()));
                    loanRequest.setValue(loan);

                    LoanResponse loanResponse = premiumAccountServiceClient.applyForLoan(userID, loanRequest);

                    System.out.println("=============================================================");
                    System.out.println("1. Monthly loan cost("+ loanResponse.getLocalCurrencyCost().getCurrency()
                            + "): " + df.format(loanResponse.getLocalCurrencyCost().getValue()));
                    System.out.println("2. Monthly loan cost(" + loanResponse.getForeignCurrencyCost().getCurrency()
                            + "): " + df.format(loanResponse.getForeignCurrencyCost().getValue()));
                    System.out.println("=============================================================");
                } else if (line.toLowerCase().equals("logout") || line.equals("3")) {
                    System.out.println("=============================================================");
                    System.out.println("Successful logout");
                    System.out.println("=============================================================");
                    break;
                } else {
                    System.out.println("=============================================================");
                    System.out.println("[FAIL] Wrong command");
                    System.out.println("=============================================================");
                }
            } catch (NotSupportedCurrencyException e) {
                System.out.println("=============================================================");
                System.out.println("[FAIL] Client currency is not support");
                System.out.println("=============================================================");
                break;
            } catch (IOException | TException e) {
                e.printStackTrace();
                break;
            }
        } while (true);
    }
}
