package bank;

import gen.grpc.Currency;
import gen.thrift.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountFactoryHandler implements AccountFactory.Iface {

    private static final Logger logger = Logger.getLogger(AccountFactoryHandler.class.getName());

    private BankServer bankServer;

    private Integer accountsAmount = 0;

    private final Integer LIMIT_INCOME_TO_PREMIUM = 5000;

    public AccountFactoryHandler(BankServer bankServer) {
        this.bankServer = bankServer;
    }

    @Override
    public AccountConfirmation createAccount(AccountData accountData) throws NotSupportedCurrencyException {
        AccountConfirmation accountConfirmation = new AccountConfirmation();
        Currency currencyGrpc = bankServer.thriftToGrpcCurrency(accountData.getIncome().getCurrency());
        if (currencyGrpc != null) {
            Integer userID = generateUserID();
            accountConfirmation.setUserID(userID);
            Money accountBalance = new Money(accountData.getIncome().getValue(), accountData.getIncome().getCurrency());
            BankAccount newBankAccount;
            if (accountData.getIncome().getValue() > LIMIT_INCOME_TO_PREMIUM) {
                newBankAccount = new BankAccount(accountData.firstName, accountData.lastName, accountData.pesel,
                        accountBalance, accountData.income, true);
                accountConfirmation.setIsPremium(true);
            } else {
                newBankAccount = new BankAccount(accountData.firstName, accountData.lastName, accountData.pesel,
                        accountBalance, accountData.income, false);
                accountConfirmation.setIsPremium(false);
            }
            bankServer.getUsersMap().put(userID, newBankAccount);
            logger.log(Level.INFO, "Create user account(ID:{0})", userID);
        } else {
            logger.log(Level.WARNING, "NotSupportedCurrencyException was thrown for user");
            throw new NotSupportedCurrencyException();
        }
        return accountConfirmation;
    }

    private Integer generateUserID() {
        return accountsAmount++;
    }
}
