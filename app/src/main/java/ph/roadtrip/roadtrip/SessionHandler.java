package ph.roadtrip.roadtrip;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;


/**
 * Created by Abhi on 20 Jan 2018 020.
 */

public class SessionHandler {
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_USERNAME = "Username";
    private static final String KEY_EXPIRES = "expires";
    private static final String KEY_FIRST_NAME = "FirstName";
    private static final String KEY_LAST_NAME = "LastName";
    private static final String KEY_MIDDLE_NAME = "middleName";
    private static final String KEY_EMAIL = "EmailAddress";
    private static final String KEY_MOBILE_NUMBER = "ContactNum";
    private static final String KEY_EMPTY = "";
    private static final String KEY_USER_ID = "userID";
    private static final String KEY_ID_NUM = "identificationNumber";
    private static final String KEY_BC_ADDRESS = "bcAddress";
    //BOOKING DETAILS
    private static final String KEY_ADDRESS = "Address";


    private static final int EMPTY = 2;
    private Context mContext;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mPreferences;

    public SessionHandler(Context mContext) {
        this.mContext = mContext;
        mPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.mEditor = mPreferences.edit();
    }

    /**
     * Logs in the user by saving user details and setting session
     *
     * @param username
     *
     */
    public void loginUser(int userID, String username, String FirstName, String LastName,
                          String EmailAddress, String ContactNum, String Address, String identificationNumber, String bcAddress) {
        mEditor.putInt(KEY_USER_ID, userID);
        mEditor.putString(KEY_USERNAME, username);
        mEditor.putString(KEY_FIRST_NAME, FirstName);
        mEditor.putString(KEY_LAST_NAME, LastName);
        mEditor.putString(KEY_EMAIL, EmailAddress);
        mEditor.putString(KEY_MOBILE_NUMBER, ContactNum);
        mEditor.putString(KEY_ADDRESS, Address);
        mEditor.putString(KEY_ID_NUM, identificationNumber);
        mEditor.putString(KEY_BC_ADDRESS, bcAddress);
        Date date = new Date();

        //Set user session for next 7 days
        long millis = date.getTime() + (7 * 24 * 60 * 60 * 1000);
        mEditor.putLong(KEY_EXPIRES, millis);
        mEditor.commit();
    }

    /**
     * Checks whether user is logged in
     *
     * @return
     */
    public boolean isLoggedIn() {
        Date currentDate = new Date();

        long millis = mPreferences.getLong(KEY_EXPIRES, 0);

        /* If shared preferences does not have a value
         then user is not logged in
         */
        if (millis == 0) {
            return false;
        }
        Date expiryDate = new Date(millis);

        /* Check if session is expired by comparing
        current date and Session expiry date
        */
        return currentDate.before(expiryDate);
    }

    /**
     * Fetches and returns user details
     *
     * @return user details
     */
    public User getUserDetails() {
        //Check if user is logged in first
        if (!isLoggedIn()) {
            return null;
        }
        User user = new User();
        user.setUsername(mPreferences.getString(KEY_USERNAME, KEY_EMPTY));
        user.setFirstName(mPreferences.getString(KEY_FIRST_NAME, KEY_EMPTY));
        user.setLastName(mPreferences.getString(KEY_LAST_NAME, KEY_EMPTY));
        user.setAddress(mPreferences.getString(KEY_ADDRESS, KEY_ADDRESS));
        user.setEmailAddress(mPreferences.getString(KEY_EMAIL, KEY_EMPTY));
        user.setContactNum(mPreferences.getString(KEY_MOBILE_NUMBER, KEY_EMPTY));
        user.setUserID(mPreferences.getInt(KEY_USER_ID, 0));
        user.setBcAddress(mPreferences.getString(KEY_BC_ADDRESS, KEY_EMPTY));
        user.setSessionExpiryDate(new Date(mPreferences.getLong(KEY_EXPIRES, 0)));
        user.setIdentificationNumber(mPreferences.getString(KEY_ID_NUM, KEY_EMPTY));


        return user;
    }



    /**
     * Car Pictures Add Car
     * @return
     */






    /**
     *
     * public void setItBaby(int recordID, String status, int carID){
     *         mEditor.putInt(KEY_RECORD_ID, recordID);
     *         mEditor.putString(KEY_RECORD_STATUS, status);
     *         mEditor.putInt(KEY_CAR_ID, carID);
     *
     *         mEditor.commit();
     *     }
     *
     * Logs out user by clearing the session
     */
    public void logoutUser(){
        mEditor.clear();
        mEditor.commit();
    }

}
