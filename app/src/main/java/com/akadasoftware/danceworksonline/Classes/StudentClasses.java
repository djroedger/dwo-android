package com.akadasoftware.danceworksonline.Classes;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by Kyle on 4/9/2014.
 */
public class StudentClasses implements KvmSerializable {

    public int ClID, Length, ClTchID, ClLAge, ClUAge, ClCur, ClMax, SessionID;

    public float Tuition, Discount;

    public Boolean Recital, Edit, MultiDay, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday, OLReg;

    public String ClType, ClLevel, ClRoom, ClDay, ClDescription, ClStart, ClStop, ClInstructor, ClKey, ClWait;

    public StudentClasses() {
    }

    public StudentClasses(int clid, String cltype, String cllevel, String clroom, String clday,
                          String cldescription, String clstart, String clstop, String clinstructor,
                          float tuition, Boolean recital, Boolean edit, int length, int cltchid, Boolean multiday,
                          Boolean monday, Boolean tuesday, Boolean wednesday, Boolean thursday, Boolean friday,
                          Boolean saturday, Boolean sunday, int cllage, int cluage, String clkey, int clcur, int clmax,
                          String clwait, float discount, Boolean olreg, int sessionid) {

        clid = ClID;
        cltype = ClType;
        cllevel = ClLevel;
        clroom = ClRoom;
        clday = ClDay;
        cldescription = ClDescription;
        clstart = ClStart;
        clstop = ClStop;
        clinstructor = ClInstructor;
        tuition = Tuition;
        recital = Recital;
        edit = Edit;
        length = Length;
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
        discount = Discount;
        sessionid = SessionID;
        olreg = OLReg;

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
                return ClInstructor;
            case 9:
                return Tuition;
            case 10:
                return Recital;
            case 11:
                return Edit;
            case 12:
                return Length;
            case 13:
                return ClTchID;
            case 14:
                return MultiDay;
            case 15:
                return Monday;
            case 16:
                return Tuesday;
            case 17:
                return Wednesday;
            case 18:
                return Thursday;
            case 19:
                return Friday;
            case 20:
                return Saturday;
            case 21:
                return Sunday;
            case 22:
                return ClLAge;
            case 23:
                return ClUAge;
            case 24:
                return ClKey;
            case 25:
                return ClCur;
            case 26:
                return ClMax;
            case 27:
                return ClWait;
            case 28:
                return Discount;
            case 29:
                return OLReg;
            case 30:
                return SessionID;
        }

        return null;
    }

    @Override
    public int getPropertyCount() {
        // TODO Auto-generated method stub
        return 31;
    }

    @Override
    public void getPropertyInfo(int index, Hashtable arg1, PropertyInfo info) {
        switch (index) {
            case 0:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "ClID";
                break;
            case 1:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "ClType";
                break;
            case 2:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ClLevel";
                break;
            case 3:
                info.type = PropertyInfo.INTEGER_CLASS;
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
                info.name = "ClInstructor";
                break;
            case 9:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Tuition";
                break;
            case 10:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Recital";
                break;
            case 11:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Edit";
                break;
            case 12:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Length";
                break;
            case 13:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ClTchID";
                break;
            case 14:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "MultiDay";
                break;
            case 15:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Monday";
                break;
            case 16:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Tuesday";
                break;
            case 17:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Wednesday";
                break;
            case 18:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Thursday";
                break;
            case 19:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Friday";
                break;
            case 20:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "Saturday";
                break;
            case 21:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "Sunday";
                break;
            case 22:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "ClLAge";
                break;
            case 23:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "ClUAge";
                break;
            case 24:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "ClKey";
                break;
            case 25:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "ClCur";
                break;
            case 26:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ClMax";
                break;
            case 27:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "ClWait";
                break;
            case 28:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Discount";
                break;
            case 29:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "OLReg";
                break;
            case 30:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "SessionID";
                break;
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
                    ClInstructor = "";
                } else {
                    ClInstructor = value.toString();
                }
                break;
            case 9:
                Tuition = Float.parseFloat(value.toString());
                break;
            case 10:
                Recital = Boolean.parseBoolean(value.toString());
                break;
            case 11:
                Edit = Boolean.parseBoolean(value.toString());
                break;
            case 12:
                Length = Integer.parseInt(value.toString());
                break;
            case 13:
                ClTchID = Integer.parseInt(value.toString());
                break;
            case 14:
                MultiDay = Boolean.parseBoolean(value.toString());
                break;
            case 15:
                Monday = Boolean.parseBoolean(value.toString());
                break;
            case 16:
                Tuesday = Boolean.parseBoolean(value.toString());
                break;
            case 17:
                Wednesday = Boolean.parseBoolean(value.toString());
                break;
            case 18:
                Thursday = Boolean.parseBoolean(value.toString());
                break;
            case 19:
                Friday = Boolean.parseBoolean(value.toString());
                break;
            case 20:
                Saturday = Boolean.parseBoolean(value.toString());
                break;
            case 21:
                Sunday = Boolean.parseBoolean(value.toString());
                break;
            case 22:
                ClLAge = Integer.parseInt(value.toString());
                break;
            case 23:
                ClUAge = Integer.parseInt(value.toString());
                break;
            case 24:
                if (value.equals("anyType{}")) {
                    ClKey = "";
                } else {
                    ClKey = value.toString();
                }
                break;
            case 25:
                ClCur = Integer.parseInt(value.toString());
                break;
            case 26:
                ClMax = Integer.parseInt(value.toString());
                break;
            case 27:
                if (value.equals("anyType{}")) {
                    ClWait = "";
                } else {
                    ClWait = value.toString();
                }
            case 28:
                Discount = Float.parseFloat(value.toString());
                break;
            case 29:
                OLReg = Boolean.parseBoolean(value.toString());
                break;
            case 30:
                SessionID = Integer.parseInt(value.toString());
                break;
            default:
                break;

        }

    }

}
