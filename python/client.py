from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol, TMultiplexedProtocol

from bank import AccountFactory, AccountService, PremiumAccountService
from bank.ttypes import \
    AccountData,\
    CurrencyType,\
    LoanRequest,\
    Money,\
    TException,\
    AuthenticationException,\
    AccountExistsException,\
    NotSupportedCurrencyException

import traceback


def main():
    # Make socket
    transport = TSocket.TSocket('localhost', 9091)

    try:
        # Buffering is critical. Raw sockets are very slow
        transport = TTransport.TBufferedTransport(transport)

        # Wrap in a protocol
        protocol = TBinaryProtocol.TBinaryProtocol(transport)

        # Create a client to use the protocol encoder
        account_factory_client = AccountFactory.Client(TMultiplexedProtocol.TMultiplexedProtocol(
            protocol, "AccountFactory"))
        account_service = AccountService.Client(TMultiplexedProtocol.TMultiplexedProtocol(protocol, "AccountService"))
        premium_account_service = PremiumAccountService.Client(TMultiplexedProtocol.TMultiplexedProtocol(
            protocol, "PremiumAccountService"))

        # Connect!
        transport.open()

        while True:
            print("Client console - available commands:")
            print("1. CREATE - create account")
            print("2. LOGIN - login to account")
            print("3. PREMIUM-LOGIN - login to account")
            print("4. CLOSE - close console")
            print("Enter one of commands ...")
            print("/> ")
            try:
                line = input()
                if line.lower() == "create" or line == "1":
                    create_account(account_factory_client)
                elif line.lower() == "login" or line == "2":
                    login_to_account(account_service)
                elif line.lower() == "premium-login" or line == "3":
                    premium_login_to_account(premium_account_service)
                elif line.lower() == "close" or line == "4":
                    transport.close()
                    break
                else:
                    print("[FAIL] Wrong command")
            except IOError as e:
                print("I/O error({0}): {1}".format(e.errno, e.strerror))

    except TException:
        print("TException")
        traceback.print_exc()
    finally:
        transport.close()


def create_account(account_factory_client):
    print("=============================================================")
    print("Please fill a form:")
    try:
        print("First name: ")
        first_name = input()
        print("Last name: ")
        last_name = input()
        print("PESEL: ")
        pesel = input()
        print("Monthly income: ")
        income = Money(float(input()), CurrencyType.PLN)
        account_data = AccountData(firstName=first_name, lastName=last_name, pesel=pesel, income=income)
        account_confirmation = account_factory_client.createAccount(account_data)
        print("=============================================================")
        print("Account created, your userID(PESEL): " + account_confirmation.userID)
        if account_confirmation.isPremium:
            print("Your account is Premium Account")
        else:
            print("Your account is Normal Account")
        print("=============================================================")
    except NotSupportedCurrencyException:
        print("=============================================================")
        print("[FAIL] Client currency is not support")
        print("=============================================================")
    except AccountExistsException:
        print("=============================================================")
        print("[FAIL] Account with that PESEL exists")
        print("=============================================================")
    except IOError as e:
        print("I/O error({0}): {1}".format(e.errno, e.strerror))
    except TException:
        print("TException")
        traceback.print_exc()


def login_to_account(account_service):
    print("=============================================================")
    try:
        print("Your userID: ")
        user_id = input()
        account_service.authenticateUser(user_id)
        print("=============================================================")
        print("Successful login")
        print("=============================================================")
    except AuthenticationException:
        print("=============================================================")
        print("[FAIL] Authentication failed")
        print("=============================================================")
        return None
    except IOError as e:
        print("I/O error({0}): {1}".format(e.errno, e.strerror))
        return None
    except TException:
        print("TException")
        traceback.print_exc()
        return None

    while True:
        print("Client ID: " + str(user_id))
        print("1. ACCOUNT-STATE - show current account state")
        print("2. LOGOUT - logout client")
        print("Enter one of commands ...")
        print("/> ")
        try:
            line = input()
            if line.lower() == "account-state" or line == "1":
                account_state = account_service.getAccountState(user_id)
                print("=============================================================")
                print("Account state = " + str(round(account_state.value, 2)) + " "
                      + CurrencyType._VALUES_TO_NAMES[account_state.currency])
                print("=============================================================")
            elif line.lower() == "logout" or line == "2":
                print("=============================================================")
                print("Successful logout")
                print("=============================================================")
                return None
            else:
                print("=============================================================")
                print("[FAIL] Wrong command")
                print("=============================================================")
        except IOError as e:
            print("I/O error({0}): {1}".format(e.errno, e.strerror))
            break
        except TException:
            print("TException")
            traceback.print_exc()
            break


def premium_login_to_account(premium_account_service):
    print("=============================================================")
    try:
        print("Your userID: ")
        user_id = input()
        premium_account_service.authenticateUser(user_id)
        print("=============================================================")
        print("Successful login")
        print("=============================================================")
    except AuthenticationException:
        print("=============================================================")
        print("Authentication failed")
        print("=============================================================")
        return None
    except IOError as e:
        print("I/O error({0}): {1}".format(e.errno, e.strerror))
        return None
    except TException:
        print("TException")
        traceback.print_exc()
        return None

    while True:
        print("Client ID: " + user_id)
        print("1. ACCOUNT-STATE - show current account state")
        print("2. LOAN-APPLY - show current account state")
        print("3. LOGOUT - logout client")
        print("Enter one of commands ...")
        print("/> ")
        try:
            line = input()
            if line.lower() == "account-state" or line == "1":
                account_state = premium_account_service.getAccountState(user_id)
                print("=============================================================")
                print("Account state = " + str(round(account_state.value, 2)) + " "
                      + CurrencyType._VALUES_TO_NAMES[account_state.currency])
                print("=============================================================")
            elif line.lower() == "loan-apply" or line == "2":
                print("=============================================================")
                print("Please fill a form:")
                print("Loan currency: ")
                currency = getattr(CurrencyType, input())
                print("Loan value: ")
                value = float(input())
                print("Months: ")
                months = int(input())
                loan_request = LoanRequest(months=months, value=Money(currency=currency, value=value))

                loan_response = premium_account_service.applyForLoan(user_id, loan_request)

                print("=============================================================")
                print("1. Monthly loan cost("
                      + CurrencyType._VALUES_TO_NAMES[loan_response.localCurrencyCost.currency]
                      + "): " + str(round(loan_response.localCurrencyCost.value, 2)))
                print("2. Monthly loan cost("
                      + CurrencyType._VALUES_TO_NAMES[loan_response.foreignCurrencyCost.currency]
                      + "): " + str(round(loan_response.foreignCurrencyCost.value, 2)))
                print("=============================================================")
            elif line.lower() == "logout" or line == "3":
                print("=============================================================")
                print("Successful logout")
                print("=============================================================")
                break
            else:
                print("=============================================================")
                print("[FAIL] Wrong command")
                print("=============================================================")
        except NotSupportedCurrencyException:
            print("=============================================================")
            print("[FAIL] Client currency is not support")
            print("=============================================================")
            break
        except IOError as e:
            print("I/O error({0}): {1}".format(e.errno, e.strerror))
            break
        except TException:
            print("TException")
            traceback.print_exc()
            break
