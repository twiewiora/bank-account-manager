package bank;

import gen.grpc.Currency;
import gen.thrift.*;

import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PremiumAccountServiceHandler implements PremiumAccountService.Iface {

    private static final Logger logger = Logger.getLogger(PremiumAccountServiceHandler.class.getName());

    private BankServer bankServer;

    public PremiumAccountServiceHandler(BankServer bankServer) {
        this.bankServer = bankServer;
    }

    @Override
    public boolean authenticateUser(String userID) throws AuthenticationException {
        if (bankServer.getUsersMap().containsKey(userID) && bankServer.getUsersMap().get(userID).isPremiumAccount()) {
            logger.log(Level.INFO, "Request - authenticate premium user(ID:{0})", userID);
            return true;
        } else {
            logger.log(Level.WARNING, "AuthenticationException was thrown for user(ID:{0})", userID);
            throw new AuthenticationException();
        }
    }

    @Override
    public LoanResponse applyForLoan(String userID, LoanRequest request) throws AuthenticationException, NotSupportedCurrencyException {
        if (bankServer.getUsersMap().containsKey(userID)) {
            LoanResponse loanResponse = new LoanResponse();

            BigDecimal loanValue = BigDecimal.valueOf(request.getValue().getValue());
            BigDecimal loanCost = calculateLoanCost(loanValue, request.getMonths());

            loanResponse.setForeignCurrencyCost(new Money(loanCost.doubleValue(), request.getValue().getCurrency()));

            if (!bankServer.getCurrencies().contains(bankServer.thriftToGrpcCurrency(request.getValue().getCurrency()))) {
                logger.log(Level.WARNING, "NotSupportedCurrencyException was thrown for user(ID:{0})", userID);
                throw new NotSupportedCurrencyException();
            }
            switch (request.getValue().getCurrency()) {
                case EUR:
                    loanResponse.setLocalCurrencyCost(new Money(calculateLoanCostToLocalCurrency(
                            loanCost, CurrencyType.EUR).doubleValue(), CurrencyType.PLN));
                    break;
                case USD:
                    loanResponse.setLocalCurrencyCost(new Money(calculateLoanCostToLocalCurrency(
                            loanCost, CurrencyType.USD).doubleValue(), CurrencyType.PLN));
                    break;
                case CHF:
                    loanResponse.setLocalCurrencyCost(new Money(calculateLoanCostToLocalCurrency(
                            loanCost, CurrencyType.CHF).doubleValue(), CurrencyType.PLN));
                    break;
            }
            logger.log(Level.INFO, "Request apply for loan from user(ID:{0})", userID);
            return loanResponse;
        } else {
            logger.log(Level.WARNING, "AuthenticationException was thrown for user(ID:{0})", userID);
            throw new AuthenticationException();
        }
    }

    @Override
    public Money getAccountState(String userID) throws AuthenticationException {
        if (bankServer.getUsersMap().containsKey(userID)) {
            logger.log(Level.INFO, "Request for account state from user(ID:{0})", userID);
            return bankServer.getUsersMap().get(userID).getAccountBalance();
        } else {
            logger.log(Level.WARNING, "AuthenticationException was thrown for user(ID:{0})", userID);
            throw new AuthenticationException();
        }
    }

    private BigDecimal calculateLoanCost(BigDecimal loanValue, Integer months) {
        return loanValue.divide(BigDecimal.valueOf(months), 2).multiply(BigDecimal.valueOf(1.15));
    }

    private BigDecimal calculateLoanCostToLocalCurrency(BigDecimal loanCost, CurrencyType currencyType) {
        return loanCost.multiply(getCurrencyState(currencyType));
    }

    private BigDecimal getCurrencyState(CurrencyType currencyType) {
        switch (currencyType) {
            case EUR:
                return BigDecimal.valueOf(bankServer.getCurrencyClient().getCurrencyCource(Currency.EUR));
            case USD:
                return BigDecimal.valueOf(bankServer.getCurrencyClient().getCurrencyCource(Currency.USD));
            case CHF:
                return BigDecimal.valueOf(bankServer.getCurrencyClient().getCurrencyCource(Currency.CHF));
            default:
                return BigDecimal.ZERO;
        }
    }
}
