package ca.jrvs.apps.trading.model.view;

import ca.jrvs.apps.trading.model.dto.SecurityRow;

import java.util.List;

public class PortfolioView {

    private List<SecurityRow> securityRows;

    public PortfolioView(List<SecurityRow> securityRows) {
        this.securityRows = securityRows;
    }

    public List<SecurityRow> getSecurityRows() {
        return securityRows;
    }

    public void setSecurityRows(List<SecurityRow> securityRows) {
        this.securityRows = securityRows;
    }
}
