package bank;

import gen.thrift.Money;

public class BankAccount {

    private String firstName;

    private String lastName;

    private String pesel;

    private Money accountBalance;

    private Money income;

    private Boolean isPremiumAccount;

    public BankAccount(String firstName, String lastName, String pesel, Money accountBalance, Money income, Boolean isPremiumAccount) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
        this.accountBalance = accountBalance;
        this.income = income;
        this.isPremiumAccount = isPremiumAccount;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPesel() {
        return pesel;
    }

    public Money getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Money accountBalance) {
        this.accountBalance = accountBalance;
    }

    public Money getIncome() {
        return income;
    }

    public void setIncome(Money income) {
        this.income = income;
    }

    public Boolean isPremiumAccount() {
        return isPremiumAccount;
    }

    public void setPremiumAccount(Boolean premiumAccount) {
        isPremiumAccount = premiumAccount;
    }
}
