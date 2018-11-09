package ca.bcit.ass2.tong_chang;

import java.util.Date;

public class NaughtyChild {
    private String firstName;
    private String lastName;
    private String birthday;
    private String street;
    private String city;
    private String province;
    private String postalCode;
    private String country;
    private int latitude;
    private int longitude;
    private int isNaughty;
    private String dateCreated;

    // Each country has a name, description and an image resource
    public NaughtyChild(String firstName, String lastName,  String birthday, String street, String city, String province,
                   String postalCode, String country, int latitude, int longitude, int isNaughty, String dateCreated) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.street = street;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isNaughty = isNaughty;
        this.dateCreated = dateCreated;

    }

    public static final NaughtyChild[] NaughtyChilds = {
            new NaughtyChild("mark", "ma", "2018-01-11",
                    "alder", "richmond", "BC", "v20 0a1", "21", 12,1,1, "1990-12-12"),
            new NaughtyChild("yipan", "wu", "2018-01-11",
                    "alder", "richmond", "BC", "v20 0a1", "21", 12,1,1, "1990-12-12"),
            new NaughtyChild("yue", "yu", "2018-01-11",
                    "alder", "richmond", "BC", "v20 0a1", "21", 12,1,1, "1990-12-12"),
            new NaughtyChild("yang", "tong", "2018-01-11",
                    "alder", "richmond", "BC", "v20 0a1", "21", 12,1,1, "1990-12-12"),
            new NaughtyChild("tim", "cook", "2018-01-11",
                    "alder", "richmond", "BC", "v20 0a1", "21", 12,1,1, "1990-12-12")

    };

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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public int getIsNaughty() {
        return isNaughty;
    }

    public void setIsNaughty(int isNaughty) {
        this.isNaughty = isNaughty;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
}
