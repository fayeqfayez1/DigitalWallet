package com.app.network.firebase.model;


import com.google.firebase.database.Exclude;
import com.google.firebase.database.PropertyName;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


import io.realm.annotations.PrimaryKey;

import static com.app.utils.ConstantApp.ANDROID;


public class UserData implements Serializable {
    @PropertyName("address")
    private String address = "";
    @PropertyName("age")
    private Integer age = 0;
    @PropertyName("bio")
    private String bio = "";
    @PropertyName("country")
    private String country = "";
    @PropertyName("coverImage")
    private String coverImage = "";
    @PropertyName("deviceToken")
    private String deviceToken = "";
    @PropertyName("dob")
    private long dob = 0;
    @PropertyName("email")
    private String email = "";
    @PropertyName("gender")
    private String gender = "";

    @PropertyName("lastUpdated")
    private Long lastUpdated = Calendar.getInstance().getTimeInMillis() / 1000;
    @PropertyName("latitude")
    private Double latitude = 0.0;
    @PropertyName("loginType")
    private String loginType = "";
    @PropertyName("longitude")
    private Double longitude = 0.0;
    @PropertyName("mobile")
    private String mobile = "";
    @PropertyName("os")
    private String os = ANDROID;
    @PropertyName("userId")
    @PrimaryKey
    private String userId = "";
    @PropertyName("userImage")
    private String userImage;
    @PropertyName("userType")
    private String userType = "";
    @PropertyName("username")
    private String username;
    @PropertyName("lastName")
    private String lastName;


    public UserData() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    private boolean isFollowed = false;

    private boolean isSelceted = false;

    private boolean isBlocked = false;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }


    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public long getDob() {
        return dob;
    }

    public void setDob(long dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "ChatUserData{" +
                "address='" + address + '\'' +
                ", age=" + age +
                ", bio='" + bio + '\'' +
                ", coverImage='" + coverImage + '\'' +
                ", deviceToken='" + deviceToken + '\'' +
                ", dob=" + dob +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", lastUpdated=" + lastUpdated +
                ", latitude=" + latitude +
                ", loginType='" + loginType + '\'' +
                ", longitude=" + longitude +
                ", mobile='" + mobile + '\'' +
                ", os='" + os + '\'' +
                ", userId='" + userId + '\'' +
                ", userImage='" + userImage + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("address", address);
        result.put("age", age);
        result.put("bio", bio);
        result.put("coverImage", coverImage);
        result.put("deviceToken", deviceToken);
        result.put("dob", dob);
        result.put("email", email);
        result.put("username", username);
        result.put("gender", gender);
        result.put("lastUpdated", lastUpdated);
        result.put("latitude", latitude);
        result.put("loginType", loginType);
        result.put("longitude", longitude);
        result.put("mobile", mobile);
        result.put("os", ANDROID);
        result.put("userId", userId);
        result.put("userImage", userImage);
        result.put("userType", "User");
        result.put("country", country);
        return result;
    }

    public void setFollowed(boolean followed) {
        isFollowed = followed;
    }

    public void setSelceted(boolean selceted) {
        isSelceted = selceted;
    }

    public boolean isFollowed() {
        return isFollowed;
    }

    public boolean isSelceted() {
        return isSelceted;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    @PropertyName("username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
