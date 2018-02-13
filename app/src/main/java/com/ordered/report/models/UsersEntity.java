package com.ordered.report.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Selva on 1-1-2018.
 */
@DatabaseTable(tableName = "Users")
public class UsersEntity {

    public static final String USER_ID = "UserId";
    public static final String USER_NAME = "UserName";
    public static final String NAME = "Name";
    public static final String PASSWORD = "Password";
    public static final String PHONE_NUMBER = "PhoneNumber";
    public static final String EMAIL = "Email";
    public static final String IS_ENABLED = "IsEnabled";

    @DatabaseField(columnName = "UserId", generatedId = true)
    private int userId;
    @DatabaseField(columnName = "UserName")
    private String userName;
    @DatabaseField(columnName = "Password")
    private String password;
    @DatabaseField(columnName = "PhoneNumber")
    private String phoneNo;
    @DatabaseField(columnName = "Email")
    private String email;
    @DatabaseField(columnName = "CreatedDateTime")
    private long createdDateTime;
    @DatabaseField(columnName = "IsEnabled")
    private boolean isEnabled;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(long createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    @Override
    public String toString() {
        return "UsersEntity{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", email='" + email + '\'' +
                ", createdDateTime=" + createdDateTime +
                ", isEnabled=" + isEnabled +
                '}';
    }
}
