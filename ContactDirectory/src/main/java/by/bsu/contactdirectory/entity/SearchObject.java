package by.bsu.contactdirectory.entity;

import java.util.Calendar;

/**
 * Created by Alexandra on 15.09.2016.
 */
public class SearchObject {

    private String firstName;
    private String lastName;
    private String patronymic;
    private Calendar birthDateBigger;
    private Calendar birthDateLess;
    private Gender gender;
    private String citizenship;
    private MaritalStatus maritalStatus;
    private String webSite;
    private String email;
    private String placeOfWork;
    private String country;
    private String city;
    private String localAddress;
    private String index;

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

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Calendar getBirthDateBigger() {
        return birthDateBigger;
    }

    public void setBirthDateBigger(Calendar birthDateBigger) {
        this.birthDateBigger = birthDateBigger;
    }

    public Calendar getBirthDateLess() {
        return birthDateLess;
    }

    public void setBirthDateLess(Calendar birthDateLess) {
        this.birthDateLess = birthDateLess;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlaceOfWork() {
        return placeOfWork;
    }

    public void setPlaceOfWork(String placeOfWork) {
        this.placeOfWork = placeOfWork;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocalAddress() {
        return localAddress;
    }

    public void setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
