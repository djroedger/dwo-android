package com.akadasoftware.danceworksonline.Classes;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by Kyle on 4/14/2014.
 */
public class StudentWaitList implements KvmSerializable {

    public int WaitID, ClID, ClCur, ClMax;

    public String ClType, ClLevel, ClRoom, ClDay, ClStart, ClStop, ClInstructor, WaitDate, Notes;

    public Boolean MultiDay, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday;

    public StudentWaitList() {
    }

    public StudentWaitList(int waitid, int clid, String cltype, String cllevel, String clroom,
                           String clday, Boolean multiday, Boolean monday, Boolean tuesday, Boolean wednesday,
                           Boolean thursday, Boolean friday, Boolean saturday, Boolean sunday, String clstart,
                           String clstop, String clinstructor, String waitdate, String notes, int clcur, int clmax) {

        waitid = WaitID;
        clid = ClID;
        cltype = ClType;
        cllevel = ClLevel;
        clroom = ClRoom;
        clday = ClDay;
        multiday = MultiDay;
        monday = Monday;
        tuesday = Tuesday;
        wednesday = Wednesday;
        thursday = Thursday;
        friday = Friday;
        saturday = Saturday;
        sunday = Sunday;
        clstart = ClStart;
        clstop = ClStop;
        clinstructor = ClInstructor;
        waitdate = WaitDate;
        notes = Notes;
        clcur = ClCur;
        clmax = ClMax;


    }

    @Override
    public Object getProperty(int index) {
        switch (index) {
            case 0:
                return WaitID;
            case 1:
                return ClID;
            case 2:
                return ClType;
            case 3:
                return ClLevel;
            case 4:
                return ClRoom;
            case 5:
                return ClDay;
            case 6:
                return MultiDay;
            case 7:
                return Monday;
            case 8:
                return Tuesday;
            case 9:
                return Wednesday;
            case 10:
                return Thursday;
            case 11:
                return Friday;
            case 12:
                return Saturday;
            case 13:
                return Sunday;
            case 14:
                return ClStart;
            case 15:
                return ClStop;
            case 16:
                return ClInstructor;
            case 17:
                return WaitDate;
            case 18:
                return Notes;
            case 19:
                return ClCur;
            case 20:
                return ClMax;
        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 21;
    }

    @Override
    public void getPropertyInfo(int index, Hashtable hashtable, PropertyInfo info) {
        switch (index) {
            case 0:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "WaitID";
                break;
            case 1:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "ClID";
                break;
            case 2:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ClType";
                break;
            case 3:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ClLevel";
                break;
            case 4:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ClRoom";
                break;
            case 5:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ClDay";
                break;
            case 6:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "MultiDay";
                break;
            case 7:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "Monday";
                break;
            case 8:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "Tuesday";
                break;
            case 9:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "Wednesday";
                break;
            case 10:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "Thursday";
                break;
            case 11:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "Friday";
                break;
            case 12:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "Saturday";
                break;
            case 13:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "Sunday";
            case 14:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ClStart";
                break;
            case 15:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ClStop";
                break;
            case 16:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ClInstructor";
                break;
            case 17:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "WaitDate";
                break;
            case 18:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Notes";
                break;
            case 19:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "ClCur";
                break;
            case 20:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "ClMax";
                break;
        }
    }

    @Override
    public void setProperty(int index, Object value) {
        switch (index) {
            case 0:
                WaitID = Integer.parseInt(value.toString());
                break;
            case 1:
                ClID = Integer.parseInt(value.toString());
                break;
            case 2:
                if (value.equals("anyType{}")) {
                    ClType = "";
                } else {
                    ClType = value.toString();
                }
                break;
            case 3:
                if (value.equals("anyType{}")) {
                    ClLevel = "";
                } else {
                    ClLevel = value.toString();
                }
                break;
            case 4:
                if (value.equals("anyType{}")) {
                    ClRoom = "";
                } else {
                    ClRoom = value.toString();
                }
                break;
            case 5:
                if (value.equals("anyType{}")) {
                    ClDay = "";
                } else {
                    ClDay = value.toString();
                }
                break;
            case 6:
                MultiDay = Boolean.parseBoolean(value.toString());
                break;
            case 7:
                Monday = Boolean.parseBoolean(value.toString());
                break;
            case 8:
                Tuesday = Boolean.parseBoolean(value.toString());
                break;
            case 9:
                Wednesday = Boolean.parseBoolean(value.toString());
                break;
            case 10:
                Thursday = Boolean.parseBoolean(value.toString());
                break;
            case 11:
                Friday = Boolean.parseBoolean(value.toString());
                break;
            case 12:
                Saturday = Boolean.parseBoolean(value.toString());
                break;
            case 13:
                Sunday = Boolean.parseBoolean(value.toString());
                break;
            case 14:
                if (value.equals("anyType{}")) {
                    ClStart = "";
                } else {
                    ClStart = value.toString();
                }
                break;
            case 15:
                if (value.equals("anyType{}")) {
                    ClStop = "";
                } else {
                    ClStop = value.toString();
                }
                break;
            case 16:
                if (value.equals("anyType{}")) {
                    ClInstructor = "";
                } else {
                    ClInstructor = value.toString();
                }
                break;
            case 17:
                if (value.equals("anyType{}")) {
                    WaitDate = "";
                } else {
                    WaitDate = value.toString();
                }
                break;
            case 18:
                if (value.equals("anyType{}")) {
                    Notes = "";
                } else {
                    Notes = value.toString();
                }
                break;
            case 19:
                ClCur = Integer.parseInt(value.toString());
                break;
            case 20:
                ClMax = Integer.parseInt(value.toString());
                break;
            default:
                break;

        }
    }

}