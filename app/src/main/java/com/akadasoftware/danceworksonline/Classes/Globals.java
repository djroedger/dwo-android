package com.akadasoftware.danceworksonline.Classes;

import android.app.Activity;
import android.widget.Spinner;

import com.akadasoftware.danceworksonline.Adapters.SessionAdapter;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;

/**
 * Created by Kyle on 4/7/2014.
 * Globally available methods that you might use anywhere.
 */
public class Globals {

    private static AppPreferences _appPrefs;

    /**
     * Builds the query string for filter and select by dialogs
     */
    public String BuildQuery(int selection, int sort, String mTitle) {

        String Query, strSort = "", strSelect = "";

        if (mTitle.equals("Accounts") || mTitle.equals("Home")) {
            switch (sort) {
                case 0:
                    strSort = " ORDER BY LName,FName,AcctNo";
                    break;
                case 1:
                    strSort = " ORDER BY FName,LName,AcctNo";
                    break;
                case 2:
                    strSort = " ORDER BY AcctNo";
                    break;
                case 3:
                    strSort = " ORDER BY Phone,LName,FName,AcctNo";
                    break;
                case 4:
                    strSort = " ORDER BY Email,LName,FName,AcctNo";
                    break;
                default:
                    strSort = " ORDER BY LName,FName,AcctNo";
                    break;
            }

            switch (selection) {
                case 0:
                    strSelect = " AND Status=0";
                    break;
                case 1:
                    strSelect = " AND Status=1";
                    break;
                case 2:
                    strSelect = " AND (Status=0 or Status=1)";
                    break;
                case 3:
                    strSelect = " AND Status=2";
                    break;
                case 4:
                    strSelect = " AND Status=3";
                    break;
                default:
                    strSelect = "";
                    break;
            }
        } else if (mTitle.equals("Students")) {
            switch (sort) {
                case 0:
                    strSort = " ORDER BY LName,FName,StuID";
                    break;
                case 1:
                    strSort = " ORDER BY FName,LName,StuID";
                    break;
                case 2:
                    strSort = " ORDER BY Phone,LName,FName,StuID";
                    break;
                case 3:
                    strSort = "ORDER BY Email,LName,FName,StuID";
                    break;
                case 4:
                    strSort = " ORDER BY Birthdate,LName,FName,StuID";
                    break;
                case 5:
                    strSort = " ORDER BY AcctNo,StuID";
                    break;
            }

            switch (selection) {
                case 0:
                    strSelect = " AND Status=0";
                    break;
                case 1:
                    strSelect = " AND Status=1";
                    break;
                case 2:
                    strSelect = " AND (Status=0 or Status=1)";
                    break;
                case 3:
                    strSelect = " AND Status=2";
                    break;
                default:
                    strSelect = "";
                    break;

            }
        }
        Query = strSelect + strSort;


        return Query;
    }

    /**
     * Builds start and end time strings for Classes
     */

    public String BuildTimeString(String classTime, SchoolClasses oSchoolClass, Boolean start) {

        //Is string blank?
        if (classTime == null || classTime.trim() == "") {
            if (start)
                if (oSchoolClass.ClStart == null || oSchoolClass.ClStart.trim().isEmpty())
                    classTime = "1:00pm";
                else
                    classTime = oSchoolClass.ClStart;
            else if (oSchoolClass.ClStop == null || oSchoolClass.ClStop.trim().isEmpty())
                classTime = "1:30pm";
            else
                classTime = oSchoolClass.ClStop;

        } else if (classTime.contains("am") || classTime.contains("pm")) {

        } else {
            classTime = classTime.trim();
            switch (classTime.charAt(1)) {
                case ':':
                    classTime += "am";
                    break;
                case '0':
                    if (classTime.charAt(0) == '2') {
                        classTime = classTime.substring(0, 2) + ":" + classTime.substring(2, classTime.length());
                        classTime += "pm";
                    } else {
                        classTime = classTime.substring(0, 1) + ":" + classTime.substring(1, classTime.length());
                        classTime += "am";
                    }
                    break;
                case '1':
                    if (classTime.charAt(0) == '2') {
                        classTime = classTime.substring(0, 2) + ":" + classTime.substring(2, classTime.length());
                        classTime += "pm";
                    } else {
                        classTime = classTime.substring(0, 1) + ":" + classTime.substring(1, classTime.length());
                        classTime += "am";
                    }
                    break;
                case '2':
                    if (classTime.charAt(0) == '2') {
                        classTime = classTime.substring(0, 2) + ":" + classTime.substring(2, classTime.length());
                        classTime += "pm";
                    } else {
                        classTime = classTime.substring(0, 1) + ":" + classTime.substring(1, classTime.length());
                        classTime += "am";
                    }
                    break;
                default:
                    if (classTime.trim().length() == 3) {
                        classTime = classTime.substring(0, 1) + ":" + classTime.substring(1, classTime.length());
                        classTime += "am";
                    } else {
                        classTime = classTime.substring(0, 2) + ":" + classTime.substring(2, classTime.length());
                        classTime += "pm";
                    }

                    break;
            }
        }

        return classTime;
    }

    /**
     * Updates the account and then resaves it into the arraylist
     */

    public void updateAccount(Account account, int position, Activity activity) {

        _appPrefs = new AppPreferences(activity);
        ArrayList<Account> AccountsArray = new ArrayList<Account>();
        AccountsArray = _appPrefs.getAccounts();
        AccountsArray.set(position, account);
        _appPrefs.saveAccounts(AccountsArray);

    }

    /**
     * Updates the student and then resaves it into the arraylist
     */

    public void updateStudent(Student oStudent, int position, Activity activity) {

        _appPrefs = new AppPreferences(activity);
        ArrayList<Student> StudentsArray = new ArrayList<Student>();
        StudentsArray = _appPrefs.getStudents();
        StudentsArray.set(position, oStudent);
        _appPrefs.saveStudents(StudentsArray);

    }

    /**
     * Updates the class and then resaves it into the arraylist
     */

    public void updateClass(SchoolClasses oSchoolClass, int position, Activity activity) {

        _appPrefs = new AppPreferences(activity);
        ArrayList<SchoolClasses> ClassesArray = new ArrayList<SchoolClasses>();
        ClassesArray = _appPrefs.getSchoolClassList();
        ClassesArray.set(position, oSchoolClass);
        _appPrefs.saveSchoolClassList(ClassesArray);

    }

    /**
     * Creates a new soap object
     */
    public static SoapObject GetSoapObject(String NAMESPACE, String MethodName) {
        return new SoapObject(NAMESPACE, MethodName);
    }

    public class Data {

        public static final String NAMESPACE = "http://app.akadasoftware.com/MobileAppWebService/";
        public static final String URL = "http://app.akadasoftware.com/MobileAppWebService/Android.asmx";
    }


    /**
     * Creates a new soap request
     */
    public SoapObject getSoapRequest(String NameSpace, String MethodName) {
        SoapObject Request = new SoapObject(NameSpace, MethodName);

        return Request;
    }

    /**
     * Property info's for the session b/c we use it in a spinner on several pages.
     */

    public ArrayList<Session> getSessions(int intSchID, int intUserID, String UserGUID) {
        String MethodName = "getSessions";
        SoapObject response = InvokeSessionMethod(Data.URL, MethodName, intSchID, intUserID, UserGUID);
        return RetrieveSessionsFromSoap(response);
    }

    public static SoapObject InvokeSessionMethod(String URL, String METHOD_NAME, int intSchID, int intUserID, String UserGUID) {

        SoapObject requestSession = new SoapObject(Data.NAMESPACE, METHOD_NAME);

        PropertyInfo piUserID = new PropertyInfo();
        piUserID.setName("UserID");
        piUserID.setValue(intUserID);
        requestSession.addProperty(piUserID);

        PropertyInfo piUserGUID = new PropertyInfo();
        piUserGUID.setType("STRING_CLASS");
        piUserGUID.setName("UserGUID");
        piUserGUID.setValue(UserGUID);
        requestSession.addProperty(piUserGUID);

        PropertyInfo Order = new PropertyInfo();
        Order.setName("Order");
        Order.setValue(" WHERE SchID= " + intSchID + "AND DisplaySession='True' ORDER BY SDate,EDate,SessionName");
        requestSession.addProperty(Order);

        SoapSerializationEnvelope envelopeSessions = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        envelopeSessions.dotNet = true;
        envelopeSessions.setOutputSoapObject(requestSession);

        envelopeSessions.dotNet = true;
        return MakeSessionCall(URL, envelopeSessions, Data.NAMESPACE, METHOD_NAME);
    }

    public static SoapObject MakeSessionCall(String URL,
                                             SoapSerializationEnvelope envelope, String NAMESPACE,
                                             String METHOD_NAME) {
        HttpTransportSE HttpTransport = new HttpTransportSE(URL);
        SoapObject responseSession = null;
        try {
            envelope.addMapping(Data.NAMESPACE, "Session",
                    new Session().getClass());
            HttpTransport.call(METHOD_NAME, envelope);
            responseSession = (SoapObject) envelope.getResponse();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseSession;
    }


    public static ArrayList<Session> RetrieveSessionsFromSoap(SoapObject soap) {

        ArrayList<Session> sessionArray = new ArrayList<Session>();

        for (int i = 0; i < soap.getPropertyCount(); i++) {

            SoapObject sessionItem = (SoapObject) soap.getProperty(i);

            Session Session = new Session();
            for (int j = 0; j < sessionItem.getPropertyCount(); j++) {
                Session.setProperty(j, sessionItem.getProperty(j)
                        .toString());
                if (sessionItem.getProperty(j).equals("anyType{}")) {
                    sessionItem.setProperty(j, "");
                }

            }
            sessionArray.add(i, Session);
        }

        return sessionArray;
    }

    /**
     * Sets the spinner to display the current session for the school
     *
     * @param spinnerSession
     * @param oSchool
     */
    public void setCurrentSession(Spinner spinnerSession, School oSchool) {
        SessionAdapter adapter = (SessionAdapter) spinnerSession.getAdapter();
        for (int position = 0; position < adapter.getCount(); position++) {

            Session oSession = null;
            try {
                oSession = (Session) spinnerSession.getItemAtPosition(position);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (oSession.SessionID == oSchool.SessionID) {
                spinnerSession.setSelection(position);
                break;
            }
        }

    }

    /**
     * Get User used on initial login screen
     */

    public User getUser(AppPreferences _appPrefsNew, String strEmail, String strPassword) {
        _appPrefs = _appPrefsNew;
        String MethodName = "getUser";
        SoapObject response = InvokeNewUserMethod(Data.URL, MethodName, strEmail, strPassword);
        return RetrieveUserFromSoap(response);

    }


    public static SoapObject InvokeNewUserMethod(String URL, String METHOD_NAME, String strEmail, String strPassword) {

        SoapObject requestNewUser = new SoapObject(Data.NAMESPACE, METHOD_NAME);

        PropertyInfo piEmail = new PropertyInfo();
        piEmail.setType("STRING_CLASS");
        piEmail.setName("email");
        piEmail.setValue(strEmail);
        requestNewUser.addProperty(piEmail);

        PropertyInfo piPassword = new PropertyInfo();
        piPassword.setType("STRING_CLASS");
        piPassword.setName("password");
        piPassword.setValue(strPassword);
        requestNewUser.addProperty(piPassword);

        SoapSerializationEnvelope envelopeNewUser = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        envelopeNewUser.dotNet = true;
        envelopeNewUser.setOutputSoapObject(requestNewUser);

        return MakeUserCall(URL, envelopeNewUser, Data.NAMESPACE, METHOD_NAME);
    }

    public static SoapObject MakeUserCall(String URL,
                                          SoapSerializationEnvelope envelope, String NAMESPACE,
                                          String METHOD_NAME) {
        HttpTransportSE HttpTransport = new HttpTransportSE(URL);
        SoapObject responseUser = null;
        try {

            envelope.addMapping(NAMESPACE, "User",
                    new User().getClass());
            HttpTransport.call(METHOD_NAME, envelope);
            //If the app breaks here for no reason(IO exception), re-install. Not sure why...
            responseUser = (SoapObject) envelope.getResponse();
        } catch (InterruptedIOException e) {
            // handle timeout here...
            e.printStackTrace();
        } catch (IOException e) {
            // some other io problem...
            e.getMessage();
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseUser;
    }

    public static User RetrieveUserFromSoap(SoapObject soap) {
        User inputUser = new User();
        for (int i = 0; i < soap.getPropertyCount(); i++) {
            inputUser.setProperty(i, soap.getProperty(i).toString());
        }
        return inputUser;
    }


    /**
     * Get User by ID used on splash screen, uses the same MakeUserCall and RetrieveUserFromSoap
     * methods as the getUser async on initial login
     */

    public static User getUserByID(AppPreferences _appPrefsNew) {
        _appPrefs = _appPrefsNew;
        String MethodName = "getUserByID";
        SoapObject response = InvokeUserMethod(Data.URL, MethodName);
        return RetrieveUserFromSoap(response);

    }

    public static SoapObject InvokeUserMethod(String URL, String MethodName) {
        User oUser = _appPrefs.getUser();
        SoapObject requestUser = GetSoapObject(MethodName);

        PropertyInfo piUserID = new PropertyInfo();
        piUserID.setName("UserID");
        piUserID.setValue(oUser.UserID);
        requestUser.addProperty(piUserID);

        PropertyInfo piUserGUID = new PropertyInfo();
        piUserGUID.setName("UserGUID");
        piUserGUID.setValue(oUser.UserGUID);
        requestUser.addProperty(piUserGUID);

        SoapSerializationEnvelope envelopeUser = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        envelopeUser.setOutputSoapObject(requestUser);

        envelopeUser.dotNet = true;


        return MakeUserCall(URL, envelopeUser, Data.NAMESPACE, MethodName);
    }


    /**
     * Get School
     */

    public static School getSchool(AppPreferences _appPrefsNew) {
        _appPrefs = _appPrefsNew;
        String MethodName = "getSchool";
        SoapObject response = InvokeSchoolMethod(Data.URL, MethodName);
        return RetrieveSchoolFromSoap(response);

    }

    public static SoapObject InvokeSchoolMethod(String URL, String METHOD_NAME) {
        User oUser = _appPrefs.getUser();
        SoapObject requestSchool = new SoapObject(Data.NAMESPACE,
                METHOD_NAME);

        PropertyInfo piSchID = new PropertyInfo();
        piSchID.setName("SchID");
        piSchID.setValue(oUser.SchID);
        requestSchool.addProperty(piSchID);

        PropertyInfo userid = new PropertyInfo();
        userid.setName("UserID");
        userid.setValue(oUser.UserID);
        requestSchool.addProperty(userid);

        PropertyInfo userguid = new PropertyInfo();
        userguid.setName("UserGUID");
        userguid.setValue(oUser.UserGUID);
        requestSchool.addProperty(userguid);

        SoapSerializationEnvelope envelopeSchool = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        envelopeSchool.dotNet = true;
        envelopeSchool.setOutputSoapObject(requestSchool);

        envelopeSchool.dotNet = true;
        return MakeSchoolCall(URL, envelopeSchool, Data.NAMESPACE, METHOD_NAME);
    }

    public static SoapObject MakeSchoolCall(String URL,
                                            SoapSerializationEnvelope envelope, String NAMESPACE,
                                            String METHOD_NAME) {
        HttpTransportSE HttpTransport = new HttpTransportSE(URL);
        SoapObject responseSchool = null;
        try {
            envelope.addMapping(Data.NAMESPACE, "School",
                    new School().getClass());
            HttpTransport.call(METHOD_NAME, envelope);
            responseSchool = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseSchool;
    }

    public static School RetrieveSchoolFromSoap(SoapObject soap) {
        School inputSchool = new School();
        for (int i = 0; i < soap.getPropertyCount(); i++) {
            inputSchool.setProperty(i, soap.getProperty(i).toString());
        }
        return inputSchool;
    }


    /**
     * Get accounts list
     */
    public static ArrayList<Account> getAccounts(AppPreferences _appPrefsNew) {
        _appPrefs = _appPrefsNew;
        String MethodName = "getAccounts";
        SoapObject response = InvokeAccountMethod(Data.URL, MethodName);
        return RetrieveAccountsFromSoap(response);

    }

    public static SoapObject InvokeAccountMethod(String URL, String MethodName) {

        SoapObject requestAccounts = GetAccountSoapObject(MethodName);

        User oUser = _appPrefs.getUser();
        String strAccontQuery = _appPrefs.getAccountQuery();

        PropertyInfo piOrder = new PropertyInfo();
        piOrder.setType("STRING_CLASS");
        piOrder.setName("Order");
        piOrder.setValue(strAccontQuery);
        requestAccounts.addProperty(piOrder);

        PropertyInfo piSchID = new PropertyInfo();
        piSchID.setName("SchID");
        piSchID.setValue(oUser.SchID);
        requestAccounts.addProperty(piSchID);

        PropertyInfo piUserID = new PropertyInfo();
        piUserID.setName("UserID");
        piUserID.setValue(oUser.UserID);
        requestAccounts.addProperty(piUserID);

        PropertyInfo piUserGUID = new PropertyInfo();
        piUserGUID.setType("STRING_CLASS");
        piUserGUID.setName("UserGUID");
        piUserGUID.setValue(oUser.UserGUID);
        requestAccounts.addProperty(piUserGUID);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(requestAccounts);
        return MakeAccountCall(URL, envelope, Data.NAMESPACE, MethodName);
    }

    public static SoapObject GetAccountSoapObject(String MethodName) {
        return new SoapObject(Data.NAMESPACE, MethodName);
    }

    public static SoapObject MakeAccountCall(String URL,
                                             SoapSerializationEnvelope envelope, String NAMESPACE,
                                             String METHOD_NAME) {
        HttpTransportSE HttpTransport = new HttpTransportSE(URL);
        SoapObject responseAccounts = null;
        try {
            envelope.addMapping(Data.NAMESPACE, "Account",
                    new Account().getClass());
            HttpTransport.call(METHOD_NAME, envelope);
            responseAccounts = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseAccounts;
    }

    public static ArrayList<Account> RetrieveAccountsFromSoap(SoapObject soap) {

        ArrayList<Account> Accounts = new ArrayList<Account>();
        for (int i = 0; i < soap.getPropertyCount(); i++) {

            SoapObject accountlistitem = (SoapObject) soap.getProperty(i);
            Account account = new Account();
            for (int j = 0; j < accountlistitem.getPropertyCount(); j++) {
                account.setProperty(j, accountlistitem.getProperty(j)
                        .toString());
                if (accountlistitem.getProperty(j).equals("anyType{}")) {
                    accountlistitem.setProperty(j, "");
                }

            }
            Accounts.add(i, account);
        }

        return Accounts;
    }


    /**
     * Get students list
     */

    public static ArrayList<Student> getStudents(AppPreferences _appPrefsNew, int intAcctID) {
        _appPrefs = _appPrefsNew;
        String MethodName = "getStudents";
        SoapObject response = InvokeMethod(Data.URL, MethodName, intAcctID);
        return RetrieveStudentsFromSoap(response);
    }

    public static SoapObject InvokeMethod(String URL, String MethodName, int intAcctID) {

        SoapObject request = GetSoapObject(MethodName);

        Globals oGlobals = new Globals();
        User oUser = _appPrefs.getUser();
        String strStudentQuery = "";
        /**
         * Loads students for one particular account
         */
        if (intAcctID > 0) {
            strStudentQuery = oGlobals.BuildQuery(4, _appPrefs.getStudentSortBy(), "Students");
        }
        /**
         * Loads all students
         */
        else {
            strStudentQuery = _appPrefs.getStudentQuery();
        }


        PropertyInfo piWhere = new PropertyInfo();
        piWhere.setType("STRING_CLASS");
        piWhere.setName("Where");
        piWhere.setValue(strStudentQuery);
        request.addProperty(piWhere);

        PropertyInfo piSchID = new PropertyInfo();
        piSchID.setName("SchID");
        piSchID.setValue(oUser.SchID);
        request.addProperty(piSchID);

        PropertyInfo piAcctID = new PropertyInfo();
        piAcctID.setName("AcctID");
        piAcctID.setValue(intAcctID);
        request.addProperty(piAcctID);

        PropertyInfo piUserID = new PropertyInfo();
        piUserID.setName("UserID");
        piUserID.setValue(oUser.UserID);
        request.addProperty(piUserID);

        PropertyInfo piUserGUID = new PropertyInfo();
        piUserGUID.setType("STRING_CLASS");
        piUserGUID.setName("UserGUID");
        piUserGUID.setValue(oUser.UserGUID);
        request.addProperty(piUserGUID);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        return MakeCall(URL, envelope, Data.NAMESPACE, MethodName);
    }

    public static SoapObject GetSoapObject(String MethodName) {
        return new SoapObject(Data.NAMESPACE, MethodName);
    }

    public static SoapObject MakeCall(String URL, SoapSerializationEnvelope envelope, String NAMESPACE,
                                      String METHOD_NAME) {
        HttpTransportSE HttpTransport = new HttpTransportSE(URL);
        SoapObject response = null;
        try {
            envelope.addMapping(Data.NAMESPACE, "Student",
                    new Student().getClass());
            HttpTransport.call(METHOD_NAME, envelope);
            SoapSerializationEnvelope envelopeOutput = envelope;
            response = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public static ArrayList<Student> RetrieveStudentsFromSoap(SoapObject soap) {
        ArrayList<Student> Students = new ArrayList<Student>();
        for (int i = 0; i < soap.getPropertyCount(); i++) {

            SoapObject studentlistitem = (SoapObject) soap.getProperty(i);
            Student student = new Student();
            for (int j = 0; j < studentlistitem.getPropertyCount(); j++) {
                student.setProperty(j, studentlistitem.getProperty(j)
                        .toString());
                if (studentlistitem.getProperty(j).equals("anyType{}")) {
                    studentlistitem.setProperty(j, "");
                }

            }
            Students.add(i, student);
        }

        return Students;
    }

    /**
     * Get class list
     */

    public ArrayList<SchoolClasses> getClasses(AppPreferences _appPrefsNew, int intSessionID, int intStuID, int intStaffID) {
        _appPrefs = _appPrefsNew;
        String MethodName = "getSchoolClasses";
        SoapObject response = InvokeClassMethod(Globals.Data.URL, MethodName, intSessionID, intStuID, intStaffID);
        return RetrieveClassFromSoap(response);

    }

    public SoapObject InvokeClassMethod(String URL, String MethodName, int intSessionID, int intStuID, int intStaffID) {

        SoapObject request = GetClassSoapObject(MethodName);

        User oUser = _appPrefs.getUser();

        PropertyInfo piUserID = new PropertyInfo();
        piUserID.setName("UserID");
        piUserID.setValue(oUser.UserID);
        request.addProperty(piUserID);

        PropertyInfo piUserGUID = new PropertyInfo();
        piUserGUID.setName("UserGUID");
        piUserGUID.setValue(oUser.UserGUID);
        request.addProperty(piUserGUID);

        PropertyInfo piSessionID = new PropertyInfo();
        piSessionID.setName("intSessionID");
        piSessionID.setValue(intSessionID);
        request.addProperty(piSessionID);

        PropertyInfo piBoolEnroll = new PropertyInfo();
        piBoolEnroll.setName("boolEnroll");
        piBoolEnroll.setValue(true);
        request.addProperty(piBoolEnroll);

        PropertyInfo piStuID = new PropertyInfo();
        piStuID.setName("intStuID");
        piStuID.setValue(intStuID);
        request.addProperty(piStuID);

        PropertyInfo piStaffID = new PropertyInfo();
        piStaffID.setName("intStaffID");
        piStaffID.setValue(intStaffID);
        request.addProperty(piStaffID);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        return MakeClassCall(URL, envelope, Globals.Data.NAMESPACE, MethodName);
    }

    public static SoapObject GetClassSoapObject(String MethodName) {
        return new SoapObject(Globals.Data.NAMESPACE, MethodName);
    }

    public static SoapObject MakeClassCall(String URL,
                                           SoapSerializationEnvelope envelope, String NAMESPACE,
                                           String METHOD_NAME) {
        HttpTransportSE HttpTransport = new HttpTransportSE(URL);
        SoapObject response = null;
        try {
            envelope.addMapping(Globals.Data.NAMESPACE, "SchoolClasses",
                    new SchoolClasses().getClass());
            HttpTransport.call(METHOD_NAME, envelope);
            response = (SoapObject) envelope.getResponse();


        } catch (Exception e) {
            e.printStackTrace();

        }
        return response;
    }

    public static ArrayList<SchoolClasses> RetrieveClassFromSoap(SoapObject soap) {

        ArrayList<SchoolClasses> schClassesArray = new ArrayList<SchoolClasses>();
        for (int i = 0; i < soap.getPropertyCount(); i++) {

            SoapObject classItem = (SoapObject) soap.getProperty(i);

            SchoolClasses classes = new SchoolClasses();
            for (int j = 0; j < classItem.getPropertyCount(); j++) {
                classes.setProperty(j, classItem.getProperty(j)
                        .toString());
                if (classItem.getProperty(j).equals("anyType{}")) {
                    classItem.setProperty(j, "");
                }

            }
            schClassesArray.add(i, classes);
        }

        return schClassesArray;
    }


    /**
     * Get Student Attendance
     */

    public ArrayList<StudentAttendance> getAttendance(AppPreferences _appPrefsNew, int intStuID) {
        _appPrefs = _appPrefsNew;
        String MethodName = "getStudentAttendance";
        SoapObject response = InvokeAttendanceMethod(Globals.Data.URL, MethodName, intStuID);
        return RetrieveAttendanceFromSoap(response);

    }

    public SoapObject InvokeAttendanceMethod(String URL, String MethodName, int intStuID) {

        SoapObject request = GetClassSoapObject(MethodName);

        User oUser = _appPrefs.getUser();

        PropertyInfo piUserID = new PropertyInfo();
        piUserID.setName("UserID");
        piUserID.setValue(oUser.UserID);
        request.addProperty(piUserID);

        PropertyInfo piUserGUID = new PropertyInfo();
        piUserGUID.setName("UserGUID");
        piUserGUID.setValue(oUser.UserGUID);
        request.addProperty(piUserGUID);

        PropertyInfo piStuID = new PropertyInfo();
        piStuID.setName("StuID");
        piStuID.setValue(intStuID);
        request.addProperty(piStuID);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        return MakeAttendanceCall(URL, envelope, Globals.Data.NAMESPACE, MethodName);
    }

    public static SoapObject MakeAttendanceCall(String URL,
                                                SoapSerializationEnvelope envelope, String NAMESPACE,
                                                String METHOD_NAME) {
        HttpTransportSE HttpTransport = new HttpTransportSE(URL);
        SoapObject response = null;
        try {
            envelope.addMapping(NAMESPACE, "StudentAttendance",
                    new StudentAttendance().getClass());
            HttpTransport.call(METHOD_NAME, envelope);
            response = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();

        }
        return response;
    }

    public static ArrayList<StudentAttendance> RetrieveAttendanceFromSoap(SoapObject soap) {

        ArrayList<StudentAttendance> stuAttendance = new ArrayList<StudentAttendance>();
        for (int i = 0; i < soap.getPropertyCount(); i++) {

            SoapObject attendanceItem = (SoapObject) soap.getProperty(i);

            StudentAttendance attendance = new StudentAttendance();
            for (int j = 0; j < attendanceItem.getPropertyCount(); j++) {
                attendance.setProperty(j, attendanceItem.getProperty(j)
                        .toString());
                if (attendanceItem.getProperty(j).equals("anyType{}")) {
                    attendanceItem.setProperty(j, "");
                }

            }
            stuAttendance.add(i, attendance);
        }

        return stuAttendance;
    }


    /**
     * Check Student conflicts with other classes
     */

    public ArrayList<String> checkConflicts(AppPreferences _appPrefsNew, SchoolClasses oSchoolClass,
                                            Student oStudent) {
        _appPrefs = _appPrefsNew;
        String MethodName = "checkClassEnrollment";
        SoapObject response = InvokeConflictMethod(Globals.Data.URL, MethodName, oSchoolClass, oStudent);
        return RetrieveConflictsFromSoap(response);

    }

    public SoapObject InvokeConflictMethod(String URL, String MethodName, SchoolClasses oSchoolClass,
                                           Student oStudent) {

        User oUser = _appPrefs.getUser();

        SoapObject request = GetSoapObject(MethodName);

        PropertyInfo piUserID = new PropertyInfo();
        piUserID.setName("UserID");
        piUserID.setValue(oUser.UserID);
        request.addProperty(piUserID);

        PropertyInfo piUserGUID = new PropertyInfo();
        piUserGUID.setType("STRING_CLASS");
        piUserGUID.setName("UserGUID");
        piUserGUID.setValue(oUser.UserGUID);
        request.addProperty(piUserGUID);

        PropertyInfo piClMax = new PropertyInfo();
        piClMax.setName("intClMax");
        piClMax.setValue(oSchoolClass.ClMax);
        request.addProperty(piClMax);

        PropertyInfo piClCur = new PropertyInfo();
        piClCur.setName("intClCur");
        piClCur.setValue(oSchoolClass.ClCur);
        request.addProperty(piClCur);

        PropertyInfo piClTime = new PropertyInfo();
        piClTime.setName("strClTime");
        piClTime.setValue(oSchoolClass.ClTime);
        request.addProperty(piClTime);

        PropertyInfo piClStop = new PropertyInfo();
        piClStop.setName("strClStop");
        piClStop.setValue(oSchoolClass.ClStop);
        request.addProperty(piClStop);

        PropertyInfo piMultiDay = new PropertyInfo();
        piMultiDay.setName("boolMultiDay");
        piMultiDay.setValue(oSchoolClass.MultiDay);
        request.addProperty(piMultiDay);

        PropertyInfo piMonday = new PropertyInfo();
        piMonday.setName("boolMonday");
        piMonday.setValue(oSchoolClass.Monday);
        request.addProperty(piMonday);

        PropertyInfo piTuesday = new PropertyInfo();
        piTuesday.setName("boolTuesday");
        piTuesday.setValue(oSchoolClass.Tuesday);
        request.addProperty(piTuesday);

        PropertyInfo piWednesday = new PropertyInfo();
        piWednesday.setName("boolWednesday");
        piWednesday.setValue(oSchoolClass.Wednesday);
        request.addProperty(piWednesday);

        PropertyInfo piThursday = new PropertyInfo();
        piThursday.setName("boolThursday");
        piThursday.setValue(oSchoolClass.Thursday);
        request.addProperty(piThursday);

        PropertyInfo piFriday = new PropertyInfo();
        piFriday.setName("boolFriday");
        piFriday.setValue(oSchoolClass.Friday);
        request.addProperty(piFriday);

        PropertyInfo piSaturday = new PropertyInfo();
        piSaturday.setName("boolSaturday");
        piSaturday.setValue(oSchoolClass.Saturday);
        request.addProperty(piSaturday);

        PropertyInfo piSunday = new PropertyInfo();
        piSunday.setName("boolSunday");
        piSunday.setValue(oSchoolClass.Sunday);
        request.addProperty(piSunday);

        PropertyInfo piClDayNo = new PropertyInfo();
        piClDayNo.setName("strClDayNo");
        piClDayNo.setValue(oSchoolClass.ClDayNo);
        request.addProperty(piClDayNo);

        PropertyInfo piStuID = new PropertyInfo();
        piStuID.setName("intStuID");
        piStuID.setValue(oStudent.StuID);
        request.addProperty(piStuID);

        PropertyInfo piClID = new PropertyInfo();
        piClID.setName("intClID");
        piClID.setValue(oSchoolClass.ClID);
        request.addProperty(piClID);

        PropertyInfo piSessionID = new PropertyInfo();
        piSessionID.setName("intSessionID");
        piSessionID.setValue(oSchoolClass.SessionID);
        request.addProperty(piSessionID);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        return MakeConflictCall(URL, envelope, Globals.Data.NAMESPACE, MethodName);
    }

    public static SoapObject MakeConflictCall(String URL,
                                              SoapSerializationEnvelope envelope, String NAMESPACE,
                                              String METHOD_NAME) {
        HttpTransportSE HttpTransport = new HttpTransportSE(URL);
        SoapObject response = null;
        try {

            HttpTransport.call(METHOD_NAME, envelope);
            response = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public static ArrayList<String> RetrieveConflictsFromSoap(SoapObject soap) {

        ArrayList<String> strConflictsArray = new ArrayList<String>();

        for (int i = 0; i < soap.getPropertyCount(); i++) {

            if (soap.getProperty(i).equals("anyType{}"))
                strConflictsArray.add(i, "");
            else
                strConflictsArray.add(i, soap.getProperty(i).toString());

        }

        return strConflictsArray;
    }


    /**
     * Enroll Student in Class
     */

    public Integer enrollInClass(AppPreferences _appPrefsNew, int intStuID, SchoolClasses objSchoolClasses) {
        _appPrefs = _appPrefsNew;
        String MethodName = "enrollStudent";
        SoapPrimitive response = InvokeEnrollMethod(Globals.Data.URL, MethodName, intStuID, objSchoolClasses);
        return RetrieveEnrollFromSoap(response);

    }

    public SoapPrimitive InvokeEnrollMethod(String URL, String MethodName, int intStuID, SchoolClasses objSchoolClasses) {

        SoapObject request = GetClassSoapObject(MethodName);

        User oUser = _appPrefs.getUser();
        SchoolClasses selectedSchoolClasses = objSchoolClasses;

        PropertyInfo piUserID = new PropertyInfo();
        piUserID.setName("UserID");
        piUserID.setValue(oUser.UserID);
        request.addProperty(piUserID);

        PropertyInfo piUserGUID = new PropertyInfo();
        piUserGUID.setName("UserGUID");
        piUserGUID.setValue(oUser.UserGUID);
        request.addProperty(piUserGUID);

        PropertyInfo piStuID = new PropertyInfo();
        piStuID.setName("intStuID");
        piStuID.setValue(intStuID);
        request.addProperty(piStuID);

        PropertyInfo piClID = new PropertyInfo();
        piClID.setName("intClID");
        piClID.setValue(selectedSchoolClasses.ClID);
        request.addProperty(piClID);

        PropertyInfo piClRID = new PropertyInfo();
        piClRID.setName("intClRID");
        piClRID.setValue(selectedSchoolClasses.ClRID);
        request.addProperty(piClRID);

        PropertyInfo piWaitID = new PropertyInfo();
        piWaitID.setName("intWaitID");
        piWaitID.setValue(selectedSchoolClasses.WaitID);
        request.addProperty(piWaitID);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        return MakeEnrollCall(URL, envelope, Globals.Data.NAMESPACE, MethodName);
    }

    public static SoapPrimitive MakeEnrollCall(String URL,
                                               SoapSerializationEnvelope envelope, String NAMESPACE,
                                               String METHOD_NAME) {
        HttpTransportSE HttpTransport = new HttpTransportSE(URL);
        SoapPrimitive responseEnroll = null;
        try {

            HttpTransport.call(METHOD_NAME, envelope);
            responseEnroll = (SoapPrimitive) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseEnroll;
    }

    public static Integer RetrieveEnrollFromSoap(SoapPrimitive soap) {
        int response = Integer.parseInt(soap.toString());

        return response;
    }

    /**
     * Enroll Student in WaitList
     */

    public Integer placeStudentOnWaitList(AppPreferences _appPrefsNew, SchoolClasses objSchoolClasses,
                                          Student objStudent) {
        _appPrefs = _appPrefsNew;
        String MethodName = "waitListStudent";
        SoapPrimitive response = InvokeWaitListMethod(Globals.Data.URL, MethodName, objSchoolClasses, objStudent);
        return RetrieveWaitListFromSoap(response);

    }

    public SoapPrimitive InvokeWaitListMethod(String URL, String MethodName, SchoolClasses objSchoolClasses,
                                              Student objStudent) {

        SoapObject requestWaitList = GetClassSoapObject(MethodName);

        SchoolClasses selectedSchoolClasses = objSchoolClasses;
        Student selectedStudent = objStudent;
        User oUser = _appPrefs.getUser();
        School oSchool = _appPrefs.getSchool();


        PropertyInfo piStrAction = new PropertyInfo();
        piStrAction.setName("strAction");
        piStrAction.setValue("WaitlistClass");
        requestWaitList.addProperty(piStrAction);

        PropertyInfo piClID = new PropertyInfo();
        piClID.setName("intClID");
        piClID.setValue(selectedSchoolClasses.ClID);
        requestWaitList.addProperty(piClID);

        PropertyInfo piUserID = new PropertyInfo();
        piUserID.setName("intUserID");
        piUserID.setValue(oUser.UserID);
        requestWaitList.addProperty(piUserID);

        PropertyInfo piUserGUID = new PropertyInfo();
        piUserGUID.setName("UserGUID");
        piUserGUID.setValue(oUser.UserGUID);
        requestWaitList.addProperty(piUserGUID);

        PropertyInfo piSchID = new PropertyInfo();
        piSchID.setName("intSchID");
        piSchID.setValue(oSchool.SchID);
        requestWaitList.addProperty(piSchID);

        PropertyInfo piStuID = new PropertyInfo();
        piStuID.setName("intStuID");
        piStuID.setValue(selectedStudent.StuID);
        requestWaitList.addProperty(piStuID);


        PropertyInfo piAcctID = new PropertyInfo();
        piAcctID.setName("intAcctID");
        piAcctID.setValue(selectedStudent.AcctID);
        requestWaitList.addProperty(piAcctID);

        PropertyInfo piCurrentSessionID = new PropertyInfo();
        piCurrentSessionID.setName("intCurrSessionID");
        piCurrentSessionID.setValue(oSchool.SessionID);
        requestWaitList.addProperty(piCurrentSessionID);

        PropertyInfo piSelectedClassSessionID = new PropertyInfo();
        piSelectedClassSessionID.setName("intClassSessionID");
        piSelectedClassSessionID.setValue(oSchool.SessionID);
        requestWaitList.addProperty(piSelectedClassSessionID);

        PropertyInfo piFName = new PropertyInfo();
        piFName.setName("FName");
        piFName.setValue(selectedStudent.FName);
        requestWaitList.addProperty(piFName);

        PropertyInfo piLName = new PropertyInfo();
        piLName.setName("LName");
        piLName.setValue(selectedStudent.LName);
        requestWaitList.addProperty(piLName);

        PropertyInfo piAcctName = new PropertyInfo();
        piAcctName.setName("strAcctName");
        piAcctName.setValue(selectedStudent.AcctName);
        requestWaitList.addProperty(piAcctName);

        PropertyInfo piPhone = new PropertyInfo();
        piPhone.setName("strPhone");
        piPhone.setValue(selectedStudent.Phone);
        requestWaitList.addProperty(piPhone);

        PropertyInfo piNotes = new PropertyInfo();
        piNotes.setName("strNotes");
        piNotes.setValue(selectedStudent.Notes);
        requestWaitList.addProperty(piNotes);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(requestWaitList);

        return MakeWaitListCall(URL, envelope, Globals.Data.NAMESPACE, MethodName);
    }

    public static SoapPrimitive MakeWaitListCall(String URL,
                                                 SoapSerializationEnvelope envelope, String NAMESPACE,
                                                 String METHOD_NAME) {
        HttpTransportSE HttpTransport = new HttpTransportSE(URL);
        SoapPrimitive responseWaitList = null;
        try {

            HttpTransport.call(METHOD_NAME, envelope);
            responseWaitList = (SoapPrimitive) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseWaitList;
    }

    public static Integer RetrieveWaitListFromSoap(SoapPrimitive soap) {
        int response = Integer.parseInt(soap.toString());

        return response;
    }

}
