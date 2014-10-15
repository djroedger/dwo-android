package com.akadasoftware.danceworksonline.Classes;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by Kyle on 7/10/2014.
 */
public class StudentRoster implements KvmSerializable {

    public int ClID, SchID, StuID, AcctID, Status;

    public String LName, FName, Phone, BirthDate;

    public Boolean Recital;

    public StudentRoster() {
    }

    public StudentRoster(int clid, int schid, int stuid, int acctid, String lname, String fname,
                         int status, String phone, String birthdate, Boolean recital) {

        clid = ClID;
        schid = SchID;
        stuid = StuID;
        acctid = AcctID;
        lname = LName;
        fname = FName;
        status = Status;
        recital = Recital;
        phone = Phone;
        birthdate = BirthDate;
    }

    @Override
    public Object getProperty(int index) {
        switch (index) {
            case 0:
                return ClID;
            case 1:
                return SchID;
            case 2:
                return StuID;
            case 3:
                return AcctID;
            case 4:
                return LName;
            case 5:
                return FName;
            case 6:
                return Status;
            case 7:
                return Recital;
            case 8:
                return Phone;
            case 9:
                return BirthDate;
        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 10;
    }

    @Override
    public void getPropertyInfo(int index, Hashtable hashtable, PropertyInfo info) {
        switch (index) {
            case 0:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "ClID;";
                break;
            case 1:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "SchID";
                break;
            case 2:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "StuID";
                break;
            case 3:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "AcctID";
                break;
            case 4:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "LName";
                break;
            case 5:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "FName";
                break;
            case 6:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "Status";
                break;
            case 7:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Recital";
                break;
            case 8:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Phone";
                break;
            case 9:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "BirthDate";
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
                SchID = Integer.parseInt(value.toString());
                break;
            case 2:
                StuID = Integer.parseInt(value.toString());
                break;
            case 3:
                AcctID = Integer.parseInt(value.toString());
                break;
            case 4:
                if (value.equals("anyType{}")) {
                    LName = "";
                } else {
                    LName = value.toString();
                }
                break;
            case 5:
                if (value.equals("anyType{}")) {
                    FName = "";
                } else {
                    FName = value.toString();
                }
                break;
            case 6:
                Status = Integer.parseInt(value.toString());
                break;
            case 7:
                Recital = Boolean.parseBoolean(value.toString());
                break;
            case 8:
                if (value.equals("anyType{}")) {
                    Phone = "";
                } else {
                    Phone = value.toString();
                }
            case 9:
                if (value.equals("anyType{}")) {
                    BirthDate = "";
                } else {
                    BirthDate = value.toString();
                }
            default:
                break;

        }
    }

}

