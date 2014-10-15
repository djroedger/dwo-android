package com.akadasoftware.danceworksonline.Classes;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.util.ArrayList;

/**
 * Created by DJ on 12/28/13.
 */
public class AppPreferences {
    public static final String ApplicationPreferences = "com.akada.danceworksonline.studio.Login";
    public String putEmail = "email";
    public static final String putCCProcessor = "";
    public static final String putSchID = "SchID";
    public static final String putUserID = "UserID";
    public static final String putUserGUID = "UserGUID";
    public static final String putStuID = "StuID";
    public static final String putAcctID = "AcctID";
    public static final String putSessionID = "SessionID";
    public static final String putChgID = "ChgID";
    public static final String putST1Rate = "ST1Rate";
    public static final String putST2Rate = "ST2Rate";
    public static final String putAccountListPosition = "AccountListPosition";
    public static final String putStudentListPosition = "StudentListPosition";
    public static final String putClassListPosition = "ClassListPosition";
    public static final String putMyClassListPosition = "MyClassListPosition";
    public static final String putLogoName = "LogoName";
    public static final String putNavDrawerPosition = "NavDrawerPosition";
    public static final String putAccountQuery = "AccountQuery";
    public static final String putAccountSortBy = "AccountSortBy";
    public static final String putAccountSelectBy = "AccountSelectBy";
    public static final String putStudentsQuery = "StudentsQuery";
    public static final String putStudentsSortBy = "StudentsSortBy";
    public static final String putStudentsSelectBy = "StudentsSelectBy";
    public static final String putAccessAllClasses = "AccessAllClasses";
    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor prefsEditor;
    Gson gson = new Gson();
    String jsonAccounts, jsonStudents;
    String jsonUser, jsonSchool, jsonSchoolClasses;


    public AppPreferences(Context context) {

        this.sharedPrefs = context.getSharedPreferences(ApplicationPreferences,
                0);
        this.prefsEditor = sharedPrefs.edit();

    }

    public String getEmailPref() {
        return sharedPrefs.getString(putEmail, "");
    }

    public void saveEmail(String text) {
        prefsEditor.putString(putEmail, text);
        prefsEditor.commit();
    }

    public String getCCProcessor() {
        return sharedPrefs.getString(putCCProcessor, "");
    }

    public void saveCCProcessor(String text) {
        prefsEditor.putString(putCCProcessor, text);
        prefsEditor.commit();
    }

    public void saveSchID(int SchID) {
        prefsEditor.putInt(putSchID, SchID);
        prefsEditor.commit();
    }

    public int getSchID() {
        return sharedPrefs.getInt(putSchID, 0);
    }

    public void saveUserID(int UserID) {
        prefsEditor.putInt(putUserID, UserID);
        prefsEditor.commit();
    }

    public int getUserID() {
        return sharedPrefs.getInt(putUserID, 0);
    }

    public void saveUserGUID(String UserGUID) {
        prefsEditor.putString(putUserGUID, UserGUID);
        prefsEditor.commit();
    }

    public String getUserGUID() {
        return sharedPrefs.getString(putUserGUID, "");
    }


    public int getStuID() {
        return sharedPrefs.getInt(putStuID, 0);
    }

    public void saveStuID(int StuID) {
        prefsEditor.putInt(putStuID, StuID);
        prefsEditor.commit();
    }

    public int getAcctID() {
        return sharedPrefs.getInt(putAcctID, 0);
    }

    public void saveAcctID(int AcctID) {
        prefsEditor.putInt(putAcctID, AcctID);
        prefsEditor.commit();
    }

    public int getSessionID() {
        return sharedPrefs.getInt(putSessionID, 0);
    }

    public void saveSessionID(int SessionID) {
        prefsEditor.putInt(putSessionID, SessionID);
        prefsEditor.commit();
    }

    public int getChgID() {
        return sharedPrefs.getInt(putChgID, 0);
    }

    public void saveChgID(int ChgID) {
        prefsEditor.putInt(putChgID, ChgID);
        prefsEditor.commit();
    }

    public float getST1Rate() {
        return sharedPrefs.getFloat(putST1Rate, 0);
    }

    public void saveST1Rate(float ST1Rate) {
        prefsEditor.putFloat(putST1Rate, ST1Rate);
        prefsEditor.commit();
    }

    public float getST2Rate() {
        return sharedPrefs.getFloat(putST2Rate, 0);
    }

    public void saveST2Rate(float ST2Rate) {
        prefsEditor.putFloat(putST2Rate, ST2Rate);
        prefsEditor.commit();
    }

    public int getNavDrawerPosition() {
        return sharedPrefs.getInt(putNavDrawerPosition, 0);
    }

    public void saveNavDrawerPosition(int NavDrawerPosition) {
        prefsEditor.putInt(putNavDrawerPosition, NavDrawerPosition);
        prefsEditor.commit();
    }


    public void saveAccountQuery(String Query) {
        prefsEditor.putString(putAccountQuery, Query);
        prefsEditor.commit();
    }

    public String getAccountQuery() {
        return sharedPrefs.getString(putAccountQuery, "");
    }


    public int getAccountSortBy() {
        return sharedPrefs.getInt(putAccountSortBy, 0);
    }

    public void saveAccountSortBy(int SortBy) {
        prefsEditor.putInt(putAccountSortBy, SortBy);
        prefsEditor.commit();
    }


    public int getAccountSelectBy() {
        return sharedPrefs.getInt(putAccountSelectBy, 0);
    }

    public void saveAccountSelectBy(int SelectBy) {
        prefsEditor.putInt(putAccountSelectBy, SelectBy);
        prefsEditor.commit();
    }

    public void saveStudentQuery(String Query) {
        prefsEditor.putString(putStudentsQuery, Query);
        prefsEditor.commit();
    }

    public String getStudentQuery() {
        return sharedPrefs.getString(putStudentsQuery, "");
    }


    public int getStudentSortBy() {
        return sharedPrefs.getInt(putStudentsSortBy, 0);
    }

    public void saveStudentSortBy(int SortBy) {
        prefsEditor.putInt(putStudentsSortBy, SortBy);
        prefsEditor.commit();
    }


    public int getStudentSelectBy() {
        return sharedPrefs.getInt(putStudentsSelectBy, 0);
    }

    public void saveStudentSelectBy(int SelectBy) {
        prefsEditor.putInt(putStudentsSelectBy, SelectBy);
        prefsEditor.commit();
    }

    public int getAccountListPosition() {
        return sharedPrefs.getInt(putAccountListPosition, 0);
    }

    public void saveAccountListPosition(int position) {
        prefsEditor.putInt(putAccountListPosition, position);
        prefsEditor.commit();
    }


    public int getStudentListPosition() {
        return sharedPrefs.getInt(putStudentListPosition, 0);
    }

    public void saveStudentListPosition(int position) {
        prefsEditor.putInt(putStudentListPosition, position);
        prefsEditor.commit();
    }

    public int getClassListPosition() {
        return sharedPrefs.getInt(putClassListPosition, 0);
    }

    public void saveClassListPosition(int position) {
        prefsEditor.putInt(putClassListPosition, position);
        prefsEditor.commit();
    }

    public String getLogoName() {
        return sharedPrefs.getString(putLogoName, "");
    }

    public void saveLogoName(String LogoName) {
        prefsEditor.putString(putLogoName, LogoName);
        prefsEditor.commit();
    }

    public School getSchool() {
        School school = new School();
        jsonSchool = sharedPrefs.getString("School", "None found");
        if (jsonSchool != "None found") {
            JsonParser parser = new JsonParser();
            JsonArray array = parser.parse(jsonSchool).getAsJsonArray();
            school = gson.fromJson(array.get(0), School.class);
        }

        return school;

    }


    public void saveSchool(ArrayList<School> schoolArray) {
        Gson gson = new Gson();
        jsonSchool = gson.toJson(schoolArray);

        prefsEditor.putString("School", jsonSchool);
        prefsEditor.commit();
    }

    public ArrayList<SchoolClasses> getSchoolClassList() {
        ArrayList<SchoolClasses> schoolClasses = new ArrayList<SchoolClasses>();
        jsonSchoolClasses = sharedPrefs.getString("Classes", "None found");
        if (jsonSchoolClasses != "None found") {
            JsonParser parser = new JsonParser();
            JsonArray array = parser.parse(jsonSchoolClasses).getAsJsonArray();
            for (int i = 0; i < array.size(); i++) {
                SchoolClasses Class = gson.fromJson(array.get(i), SchoolClasses.class);
                schoolClasses.add(Class);
            }
        }

        return schoolClasses;

    }

    public void saveSchoolClassList(ArrayList<SchoolClasses> classesArray) {
        Gson gson = new Gson();
        jsonSchoolClasses = gson.toJson(classesArray);
        prefsEditor.putString("Classes", jsonSchoolClasses);
        prefsEditor.commit();
    }

    public ArrayList<SchoolClasses> getMySchoolClassList() {
        ArrayList<SchoolClasses> schoolClasses = new ArrayList<SchoolClasses>();
        jsonSchoolClasses = sharedPrefs.getString("MyClasses", "None found");
        if (jsonSchoolClasses != "None found") {
            JsonParser parser = new JsonParser();
            JsonArray array = parser.parse(jsonSchoolClasses).getAsJsonArray();
            for (int i = 0; i < array.size(); i++) {
                SchoolClasses Class = gson.fromJson(array.get(i), SchoolClasses.class);
                schoolClasses.add(Class);
            }
        }

        return schoolClasses;

    }

    public void saveMySchoolClassList(ArrayList<SchoolClasses> classesArray) {
        Gson gson = new Gson();
        jsonSchoolClasses = gson.toJson(classesArray);
        prefsEditor.putString("MyClasses", jsonSchoolClasses);
        prefsEditor.commit();
    }


    public User getUser() {
        User user = new User();
        jsonUser = sharedPrefs.getString("User", "None found");
        if (jsonUser != "None found") {
            JsonParser parser = new JsonParser();
            JsonArray array = parser.parse(jsonUser).getAsJsonArray();

            user = gson.fromJson(array.get(0), User.class);
        }
        return user;
    }

    public void saveUser(ArrayList<User> userarray) {
        Gson gson = new Gson();
        jsonUser = gson.toJson(userarray);

        prefsEditor.putString("User", jsonUser);
        prefsEditor.commit();
    }

    public ArrayList<Account> getAccounts() {
        ArrayList<Account> accounts = new ArrayList<Account>();
        jsonAccounts = sharedPrefs.getString("Accounts", "None found");
        if (jsonAccounts != "None found") {
            JsonParser parser = new JsonParser();
            JsonArray array = parser.parse(jsonAccounts).getAsJsonArray();
            for (int i = 0; i < array.size(); i++) {
                Account account = gson.fromJson(array.get(i), Account.class);
                accounts.add(account);
            }
        }
        return accounts;
    }

    public void saveAccounts(ArrayList<Account> accounts) {
        Gson gson = new Gson();
        jsonAccounts = gson.toJson(accounts);

        prefsEditor.putString("Accounts", jsonAccounts);
        prefsEditor.commit();
    }

    public ArrayList<Student> getStudents() {
        ArrayList<Student> students = new ArrayList<Student>();
        jsonStudents = sharedPrefs.getString("Students", "None found");
        if (jsonStudents != "None found") {
            JsonParser parser = new JsonParser();
            JsonArray array = parser.parse(jsonStudents).getAsJsonArray();
            for (int i = 0; i < array.size() - 1; i++) {
                Student student = gson.fromJson(array.get(i), Student.class);
                students.add(student);
            }
        }
        return students;
    }

    public void saveStudents(ArrayList<Student> student) {
        Gson gson = new Gson();
        jsonStudents = gson.toJson(student);

        prefsEditor.putString("Students", jsonStudents);
        prefsEditor.commit();
    }

    public Boolean getAccessAllClasses() {
        return sharedPrefs.getBoolean(putAccessAllClasses, true);
    }

    public void saveAccessAllClasses(Boolean putAll) {
        prefsEditor.putBoolean(putAccessAllClasses, putAll);
        prefsEditor.commit();
    }
}
