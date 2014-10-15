package com.akadasoftware.danceworksonline.Classes;


import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by Kyle on 5/21/2014.
 */
public class SchoolClasses implements KvmSerializable {

    public int ClID, ClLength, ClTchID, ClLAge, ClUAge, ClCur, ClMax, EnrollmentStatus, WaitID, ClRID,
            SessionID;

    public float ClTuition;

    public Boolean MultiDay, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday;

    public String ClType, ClLevel, ClRoom, ClDay, ClDescription, ClStart, ClStop, ClTime,
            ClInstructor, ClKey, ClWait, ClDayNo;

    public SchoolClasses() {
    }

    public SchoolClasses(int clid, String cltype, String cllevel, String clroom, String clday,
                         String cldescription, String clstart, String clstop, String cltime,
                         String clinstructor, float cltuition, int cllength, int cltchid, Boolean multiday,
                         Boolean monday, Boolean tuesday, Boolean wednesday, Boolean thursday, Boolean friday,
                         Boolean saturday, Boolean sunday, int cllage, int cluage, String clkey, int clcur, int clmax,
                         String clwait, String cldayno, int enrollmentstatus, int waitid, int clrid,
                         int sessionid) {

        clid = ClID;
        cltype = ClType;
        cllevel = ClLevel;
        clroom = ClRoom;
        clday = ClDay;
        cldescription = ClDescription;
        clstart = ClStart;
        clstop = ClStop;
        cltime = ClTime;
        clinstructor = ClInstructor;
        cltuition = ClTuition;
        cllength = ClLength;
        cltchid = ClTchID;
        multiday = MultiDay;
        monday = Monday;
        tuesday = Tuesday;
        wednesday = Wednesday;
        thursday = Thursday;
        friday = Friday;
        saturday = Saturday;
        sunday = Sunday;
        cllage = ClLAge;
        cluage = ClUAge;
        clkey = ClKey;
        clcur = ClCur;
        clmax = ClMax;
        clwait = ClWait;
        cldayno = ClDayNo;
        enrollmentstatus = EnrollmentStatus;
        waitid = WaitID;
        clrid = ClRID;
        sessionid = SessionID;


    }


    @Override
    public Object getProperty(int index) {
        switch (index) {
            case 0:
                return ClID;
            case 1:
                return ClType;
            case 2:
                return ClLevel;
            case 3:
                return ClRoom;
            case 4:
                return ClDay;
            case 5:
                return ClDescription;
            case 6:
                return ClStart;
            case 7:
                return ClStop;
            case 8:
                return ClTime;
            case 9:
                return ClInstructor;
            case 10:
                return ClTuition;
            case 11:
                return ClLength;
            case 12:
                return ClTchID;
            case 13:
                return MultiDay;
            case 14:
                return Monday;
            case 15:
                return Tuesday;
            case 16:
                return Wednesday;
            case 17:
                return Thursday;
            case 18:
                return Friday;
            case 19:
                return Saturday;
            case 20:
                return Sunday;
            case 21:
                return ClLAge;
            case 22:
                return ClUAge;
            case 23:
                return ClKey;
            case 24:
                return ClCur;
            case 25:
                return ClMax;
            case 26:
                return ClWait;
            case 27:
                return ClDayNo;
            case 28:
                return ClRID;
            case 29:
                return WaitID;
            case 30:
                return EnrollmentStatus;
            case 31:
                return SessionID;

        }


        return null;
    }

    @Override
    public int getPropertyCount() {
        // TODO Auto-generated method stub
        return 32;
    }

    @Override
    public void getPropertyInfo(int index, Hashtable arg1, PropertyInfo info) {
        switch (index) {
            case 0:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "ClID";
                break;
            case 1:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ClType";
                break;
            case 2:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ClLevel";
                break;
            case 3:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ClRoom";
                break;
            case 4:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ClDay";
                break;
            case 5:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ClDescription";
                break;
            case 6:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ClStart";
                break;
            case 7:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ClStop";
                break;
            case 8:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ClTime";
                break;
            case 9:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ClInstructor";
                break;
            case 10:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ClTuition";
                break;
            case 11:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "ClLength";
                break;
            case 12:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "ClTchID";
                break;
            case 13:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "MultiDay";
                break;
            case 14:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "Monday";
                break;
            case 15:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "Tuesday";
                break;
            case 16:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "Wednesday";
                break;
            case 17:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "Thursday";
                break;
            case 18:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "Friday";
                break;
            case 19:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "Saturday";
                break;
            case 20:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "Sunday";
                break;
            case 21:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "ClLAge";
                break;
            case 22:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "ClUAge";
                break;
            case 23:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ClKey";
                break;
            case 24:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "ClCur";
                break;
            case 25:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "ClMax";
                break;
            case 26:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ClWait";
                break;
            case 27:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ClDayNo";
                break;
            case 28:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "ClRID";
                break;
            case 29:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "WaitID";
                break;
            case 30:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "EnrollmentStatus";
            case 31:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "SessionID";

            default:
                break;
        }

    }

    @Override
    public void setProperty(int index, Object value) {
        switch (index) {
            case 0:
                ClID = Integer.parseInt(value.toString());
                break;
            case 1:
                if (value.equals("anyType{}")) {
                    ClType = "";
                } else {
                    ClType = value.toString();
                }
                break;
            case 2:
                if (value.equals("anyType{}")) {
                    ClLevel = "";
                } else {
                    ClLevel = value.toString();
                }
                break;
            case 3:
                if (value.equals("anyType{}")) {
                    ClRoom = "";
                } else {
                    ClRoom = value.toString();
                }
                break;
            case 4:
                if (value.equals("anyType{}")) {
                    ClDay = "";
                } else {
                    ClDay = value.toString();
                }
                break;
            case 5:
                if (value.equals("anyType{}")) {
                    ClDescription = "";
                } else {
                    ClDescription = value.toString();
                }
                break;
            case 6:
                if (value.equals("anyType{}")) {
                    ClStart = "";
                } else {
                    ClStart = value.toString();
                }
                break;
            case 7:
                if (value.equals("anyType{}")) {
                    ClStop = "";
                } else {
                    ClStop = value.toString();
                }
                break;
            case 8:
                if (value.equals("anyType{}")) {
                    ClTime = "";
                } else {
                    ClTime = value.toString();
                }
                break;
            case 9:
                if (value.equals("anyType{}")) {
                    ClInstructor = "";
                } else {
                    ClInstructor = value.toString();
                }
                break;
            case 10:
                ClTuition = Float.parseFloat(value.toString());
                break;
            case 11:
                ClLength = Integer.parseInt(value.toString());
                break;
            case 12:
                ClTchID = Integer.parseInt(value.toString());
                break;
            case 13:
                MultiDay = Boolean.parseBoolean(value.toString());
                break;
            case 14:
                Monday = Boolean.parseBoolean(value.toString());
                break;
            case 15:
                Tuesday = Boolean.parseBoolean(value.toString());
                break;
            case 16:
                Wednesday = Boolean.parseBoolean(value.toString());
                break;
            case 17:
                Thursday = Boolean.parseBoolean(value.toString());
                break;
            case 18:
                Friday = Boolean.parseBoolean(value.toString());
                break;
            case 19:
                Saturday = Boolean.parseBoolean(value.toString());
                break;
            case 20:
                Sunday = Boolean.parseBoolean(value.toString());
                break;
            case 21:
                ClLAge = Integer.parseInt(value.toString());
                break;
            case 22:
                ClUAge = Integer.parseInt(value.toString());
                break;
            case 23:
                if (value.equals("anyType{}")) {
                    ClKey = "";
                } else {
                    ClKey = value.toString();
                }
                break;
            case 24:
                ClCur = Integer.parseInt(value.toString());
                break;
            case 25:
                ClMax = Integer.parseInt(value.toString());
                break;
            case 26:
                if (value.equals("anyType{}")) {
                    ClWait = "";
                } else {
                    ClWait = value.toString();
                }
            case 27:
                if (value.equals("anyType{}")) {
                    ClDayNo = "";
                } else {
                    ClDayNo = value.toString();
                }
                break;
            case 28:
                ClRID = Integer.parseInt(value.toString());
                break;
            case 29:
                WaitID = Integer.parseInt(value.toString());
                break;
            case 30:
                EnrollmentStatus = Integer.parseInt(value.toString());
                break;
            case 31:
                SessionID = Integer.parseInt(value.toString());
                break;
            default:
                break;

        }

    }

}
