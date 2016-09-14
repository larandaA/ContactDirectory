package by.bsu.contactdirectory.entity;

/**
 * Created by Alexandra on 04.09.2016.
 */
public enum PhoneType {

    HOME, WORK, MOBILE;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
