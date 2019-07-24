package ca.jrvs.apps.trading.model.domain;

import ca.jrvs.apps.trading.model.Entity;

import java.sql.Date;

public class Trader implements Entity<Integer> {

    private int id;
    private String first_name;
    private String last_name;
    private Date dob;
    private String country;
    private String email;

    @Override
    public Integer getId() { return this.id; }

    @Override
    public void setId(Integer id) { this.id = id; }

    public String getFirst_name() { return first_name; }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDob() { return this.dob; }

    public void setDob(Date date) { this.dob = date; }
}
