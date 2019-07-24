package ca.jrvs.apps.trading.model.view;

import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;

// TODO
public class TraderAccountView {

    Trader trader;

    Account account;

    public TraderAccountView(Trader trader, Account account) {
        this.trader = trader;
        this.account = account;
    }

    public Trader getTrader() { return trader; }

    public void setTrader(Trader trader) {
        this.trader = trader;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
