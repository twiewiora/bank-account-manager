namespace java thrift

enum CurrencyType {
    PLN = 0,
    EUR = 1,
    USD = 2,
    CHF = 3
}

struct Money {
    1: double value,
    2: CurrencyType currency
}

struct LoanRequest {
    1: Money value,
    2: i32 months
}

struct LoanResponse {
    1: Money foreignCurrencyCost,
    2: Money localCurrencyCost
}

struct AccountData {
    1: string firstName,
    2: string lastName,
    3: string pesel,
    4: Money income
}

struct AccountConfirmation {
    1: i32 userID,
    2: bool isPremium
}

exception NotSupportedCurrencyException {
}

exception AuthenticationException {
}

service AccountService {
    bool authenticateUser(1: i32 userID) throws (1: AuthenticationException e)
    Money getAccountState(1: i32 userID)
}

service PremiumAccountService extends AccountService {
    LoanResponse applyForLoan(1: i32 userID, 2: LoanRequest request) throws (1: NotSupportedCurrencyException e)
}

service AccountFactory {
    AccountConfirmation createAccount(1: AccountData accountData) throws (1: NotSupportedCurrencyException e1)
}
