package com.akadasoftware.danceworksonline.Classes;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by Kyle on 7/21/2014.
 */
public class ClassWaitList implements KvmSerializable {

    public int SchID, SessionID, WaitID, StuID, AcctID, ClID, AStatus, SStatus;

    public String WaitDate, PName, CName, Phone;


    public ClassWaitList() {
    }

    public ClassWaitList(int schid, int sessionid, int waitid, int stuid, int acctid, int clid, String waitdate,
                         String pname, String cname, String phone, int astatus, int sstatus) {

        schid = SchID;
        sessionid = SessionID;
        waitid = WaitID;
        stuid = StuID;
        acctid = AcctID;
        clid = ClID;
        waitdate = WaitDate;
        pname = PName;
        cname = CName;
        phone = Phone;
        astatus = AStatus;
        sstatus = SStatus;
    }

    @Override
    public Object getProperty(int index) {
        switch (index) {
            case 0:
                return SchID;
            case 1:
                return SessionID;
            case 2:
                return WaitID;
            case 3:
                return StuID;
            case 4:
                return AcctID;
            case 5:
                return ClID;
            case 6:
                return WaitDate;
            case 7:
                return PName;
            case 8:
                return CName;
            case 9:
                return Phone;
            case 10:
                return AStatus;
            case 11:
                return SStatus;
        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 12;
    }

    @Override
    public void getPropertyInfo(int index, Hashtable hashtable, PropertyInfo info) {
        switch (index) {
            case 0:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "SchID";
                break;
            case 1:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "SessionID";
                break;
            case 2:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "WaitID";
                break;
            case 3:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "StuID";
                break;
            case 4:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "AcctID";
                break;
            case 5:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ClID";
                break;
            case 6:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "WaitDate";
                break;
            case 7:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "PName";
                break;
            case 8:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "CName";
                break;
            case 9:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Phone";
                break;
            case 10:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "AStatus";
                break;
            case 11:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "SStatus";
                break;
        }
    }

    @Override
    public void setProperty(int index, Object value) {
        switch (index) {
            case 0:
                SchID = Integer.parseInt(value.toString());
                break;
            case 1:
                SessionID = Integer.parseInt(value.toString());
                break;
            case 2:
                WaitID = Integer.parseInt(value.toString());
                break;
            case 3:
                StuID = Integer.parseInt(value.toString());
                break;
            case 4:
                AcctID = Integer.parseInt(value.toString());
                break;
            case 5:
                ClID = Integer.parseInt(value.toString());
                break;
            case 6:
                if (value.equals("anyType{}")) {
                    WaitDate = "";
                } else {
                    WaitDate = value.toString();
                }
            case 7:
                if (value.equals("anyType{}")) {
                    PName = "";
                } else {
                    PName = value.toString();
                }
                break;
            case 8:
                if (value.equals("anyType{}")) {
                    CName = "";
                } else {
                    CName = value.toString();
                }
                break;
            case 9:
                if (value.equals("anyType{}")) {
                    Phone = "";
                } else {
                    Phone = value.toString();
                }
                break;
            case 10:
                AStatus = Integer.parseInt(value.toString());
                break;
            case 11:
                SStatus = Integer.parseInt(value.toString());
                break;
            default:
                break;

        }
    }

}
