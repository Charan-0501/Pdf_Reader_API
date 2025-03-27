
package com.bankstatement.api.model;

import java.math.BigDecimal;

public class BankStatementInfo {
    private String name;
    private String email;
    private BigDecimal openingBalance;
    private BigDecimal closingBalance;

    public BankStatementInfo() {
    }

    public BankStatementInfo(String name, String email, BigDecimal openingBalance, BigDecimal closingBalance) {
        this.name = name;
        this.email = email;
        this.openingBalance = openingBalance;
        this.closingBalance = closingBalance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(BigDecimal openingBalance) {
        this.openingBalance = openingBalance;
    }

    public BigDecimal getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(BigDecimal closingBalance) {
        this.closingBalance = closingBalance;
    }
}
