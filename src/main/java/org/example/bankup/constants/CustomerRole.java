package org.example.bankup.constants;

public enum CustomerRole {
    ADMINISTRATOR("admin"), STANDARD_CUSTOMER("standard_customer"), VIEW_CUSTOMER("view_customer");

    private String role;

    CustomerRole(String role) {
        this.role = role;
    }

    public String getRole(){
        return this.role;
    }
}
