namespace cpp bank
namespace d bank
namespace dart bank
namespace java thrift
namespace php bank
namespace perl bank
namespace haxe bank

enum Currency {
    EUR = 0,
    USD = 1,
    CHF = 2
}

struct Money {
    1: i32 value,
    2: Currency currency
}

struct LoanRequest {
    1: Money value,
    2: i32 months
}

struct LoanResponse {
    1: Money foreignCurrency,
    2: Money localCurrency
}

exception NotSupportedCurrencyException {
    1: Currency currency
}

exception AccountExistsException {
    1: string userID
}

service AccountService {
   string getFullName(),
   string getPesel(),
   Money getAccountState(),
   Money getIncome(),
}

service PremiumAccountService extends AccountService {
    LoanResponse applyForLoan(1: LoanRequest request) throws (1: NotSupportedCurrencyException e),
}

service AccountFactory {
    bool createAccount(1: string fullName, 2: string pesel, 3: Money income) throws (1: NotSupportedCurrencyException e1, 2: AccountExistsException e2),
}
