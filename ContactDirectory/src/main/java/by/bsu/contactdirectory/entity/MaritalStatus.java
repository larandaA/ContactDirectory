package by.bsu.contactdirectory.entity;

/**
 * Created by Alexandra on 04.09.2016.
 */
public enum MaritalStatus {
    MARRIED, SINGLE, DIVORCED, WIDOWED;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
