package cddj.gatech.edu.safecheck;

import java.util.ArrayList;

/**
 * Created by David Zhao on 10/14/2017.
 */

public class Account {
    private String number;
    private String username;
    private ArrayList<String> contacts;

    public Account(String number, String username) {
        this.number = number;
        this.username = username;
        contacts = new ArrayList<>();
    }

    public ArrayList<String> getContacts() {
        return contacts;
    }

    public String getNumber() {
        return number;
    }

    public String getUsername() {
        return username;
    }
}
