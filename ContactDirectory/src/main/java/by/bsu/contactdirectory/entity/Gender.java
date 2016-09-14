package by.bsu.contactdirectory.entity;

/**
 * Created by Alexandra on 04.09.2016.
 */
public enum Gender {
    MALE, FEMALE;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
