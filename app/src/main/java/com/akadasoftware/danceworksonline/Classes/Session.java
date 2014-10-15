package com.akadasoftware.danceworksonline.Classes;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by Kyle on 4/16/2014.
 */
public class Session implements KvmSerializable {

    public int SchID, SessionID;

    public String SessionName, SessionDate, SDate, EDate;

    public Boolean DisplaySession;

    public Session() {
    }

    public Session(int schid, int sessionid, String sessionname, String sessiondate, String sdate,
                   String edate, Boolean displaysession
    ) {

        schid = SchID;
        sessionid = SessionID;
        sessionname = SessionName;
        sessiondate = SessionDate;
        sdate = SDate;
        edate = EDate;
        displaysession = DisplaySession;
    }

    @Override
    public Object getProperty(int index) {
        switch (index) {
            case 0:
                return SchID;
            case 1:
                return SessionID;
            case 2:
                return SessionName;
            case 3:
                return SessionDate;
            case 4:
                return SDate;
            case 5:
                return EDate;
            case 6:
                return DisplaySession;
        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 7;
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
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "SessionName";
                break;
            case 3:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "SessionDate";
                break;
            case 4:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "SDate";
                break;
            case 5:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "EDate";
                break;
            case 6:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "DisplaySession";
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
                if (value.equals("anyType{}")) {
                    SessionName = "";
                } else {
                    SessionName = value.toString();
                }
                break;
            case 3:
                if (value.equals("anyType{}")) {
                    SessionDate = "";
                } else {
                    SessionDate = value.toString();
                }
                break;
            case 4:
                if (value.equals("anyType{}")) {
                    SDate = "";
                } else {
                    SDate = value.toString();
                }
                break;
            case 5:
                if (value.equals("anyType{}")) {
                    EDate = "";
                } else {
                    EDate = value.toString();
                }
                break;
            case 6:
                DisplaySession = Boolean.parseBoolean(value.toString());
            default:
                break;

        }
    }

}
