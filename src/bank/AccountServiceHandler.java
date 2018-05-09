package bank;

import gen.thrift.AccountService;
import gen.thrift.AuthenticationException;
import gen.thrift.Money;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountServiceHandler implements AccountService.Iface {

    private static final Logger logger = Logger.getLogger(AccountServiceHandler.class.getName());

    private BankServer bankServer;

    public AccountServiceHandler(BankServer bankServer) {
        this.bankServer = bankServer;
    }

    @Override
    public boolean authenticateUser(String userID) throws AuthenticationException {
        if (bankServer.getUsersMap().containsKey(userID)) {
            logger.log(Level.INFO, "Request - authenticate user(ID:{0})", userID);
            return true;
        } else {
            logger.log(Level.WARNING, "AuthenticationException was thrown for user(ID:{0})", userID);
            throw new AuthenticationException();
        }
    }

    @Override
    public Money getAccountState(String userID) throws AuthenticationException {
        if (bankServer.getUsersMap().containsKey(userID)) {
            logger.log(Level.INFO, "Request - account state user(ID:{0})", userID);
            return bankServer.getUsersMap().get(userID).getAccountBalance();
        } else {
            logger.log(Level.WARNING, "AuthenticationException was thrown for user(ID:{0})", userID);
            throw new AuthenticationException();
        }
    }
}
