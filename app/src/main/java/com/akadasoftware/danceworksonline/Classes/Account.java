package com.akadasoftware.danceworksonline.Classes;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by Kyle on 12/27/13.
 */
public class Account implements KvmSerializable {

    public int SchID, AcctID, Status, BillingFreq, NoStudents, TuitionSel,
            ClassTime, CCType, CCConsentID, CCDate, AlertID, SourceID;
    public String AcctNo, LName, FName, Address, City, State, ZipCode, Phone,
            AcctSSN, EMail, DateReg, InactiveDate, LastPmtDate, Alert, Source,
            P1Name, P1EMail, P1Phone, P1Cell, P1Work, P2Name, P2EMail, P2Phone,
            P2Cell, P2Work, E1Name, E1Phone, E2Name, E2Phone, E3Name, E3Phone,
            E4Name, E4Phone, CCTrail, CCExpire, CCFName, CCLName, CCAddress,
            CCCity, CCState, CCZip, Notes, AccountFee, AcctPWord, ResetString,
            CCToken;
    public boolean CCMonthly, OLReg, AgreePol, RequireCreditCard, Complete, Activated,
            UsesNewPassword, Subscribed;
    public float Balance, LastPmtAmt, MTuition, AccountFeeAmount;

    public Account() {
    }

    public Account(int schid, int acctid, String acctno, String lname,
                   String fname, String address, String city, String state,
                   String zipcode, String phone, String acctssn, String email,
                   String datereg, String inactivedate, int status, int billingfreq,
                   float balance, String lastpmtdate, float lastpmtamt,
                   int nostudents, int tuitionsel, String alert, String source,
                   float mtuition, int classtime, String p1name, String p1email,
                   String p1phone, String p1cell, String p1work, String p2name,
                   String p2email, String p2phone, String p2cell, String p2work,
                   String e1name, String e1phone, String e2name, String e2phone,
                   String e3name, String e3phone, String e4name, String e4phone,
                   int cctype, String cctrail, String ccexpire, String ccfname,
                   String cclname, String ccaddress, String cccity, String ccstate,
                   String cczip, int ccconsentid, boolean ccmonthly, int ccdate,
                   String notes, String accountfee, float accountfeeamount,
                   String acctpword, boolean olreg, boolean agreepol,
                   boolean requirecreditcard, boolean complete, String resetstring,
                   boolean activated, boolean usesnewpassword, int alertid,
                   int sourceid, boolean subscribed, String cctoken) {

        SchID = schid;
        AcctID = acctid;
        AcctNo = acctno;
        LName = lname;
        FName = fname;
        Address = address;
        City = city;
        State = state;
        ZipCode = zipcode;
        Phone = phone;
        EMail = email;
        AcctSSN = acctssn;
        DateReg = datereg;
        InactiveDate = inactivedate;
        Status = status;
        BillingFreq = billingfreq;
        Balance = balance;
        LastPmtDate = lastpmtdate;
        LastPmtAmt = lastpmtamt;
        NoStudents = nostudents;
        TuitionSel = tuitionsel;
        Alert = alert;
        Source = source;
        MTuition = mtuition;
        ClassTime = classtime;
        P1Name = p1name;
        P1EMail = p1email;
        P1Phone = p1phone;
        P1Cell = p1cell;
        P1Work = p1work;
        P2Name = p2name;
        P2EMail = p2email;
        P2Phone = p2phone;
        P2Cell = p2cell;
        P2Work = p2work;
        E1Name = e1name;
        E1Phone = e1phone;
        E2Name = e2name;
        E2Phone = e2phone;
        E3Name = e3name;
        E3Phone = e3phone;
        E4Name = e4name;
        E4Phone = e4phone;
        CCType = cctype;
        CCTrail = cctrail;
        CCExpire = ccexpire;
        CCFName = ccfname;
        CCLName = cclname;
        CCAddress = ccaddress;
        CCCity = cccity;
        CCState = ccstate;
        CCZip = cczip;
        CCConsentID = ccconsentid;
        CCMonthly = ccmonthly;
        CCDate = ccdate;
        Notes = notes;
        AccountFee = accountfee;
        AccountFeeAmount = accountfeeamount;
        AcctPWord = acctpword;
        OLReg = olreg;
        AgreePol = agreepol;
        RequireCreditCard = requirecreditcard;
        Complete = complete;
        ResetString = resetstring;
        Activated = activated;
        UsesNewPassword = usesnewpassword;
        AlertID = alertid;
        SourceID = sourceid;
        Subscribed = subscribed;
        CCToken = cctoken;
    }


    @Override
    public Object getProperty(int index) {
        switch (index) {
            case 0:
                return SchID;
            case 1:
                return AcctID;
            case 2:
                return AcctNo;
            case 3:
                return LName;
            case 4:
                return FName;
            case 5:
                return Address;
            case 6:
                return City;
            case 7:
                return State;
            case 8:
                return ZipCode;
            case 9:
                return Phone;
            case 10:
                return EMail;
            case 11:
                return AcctSSN;
            case 12:
                return DateReg;
            case 13:
                return InactiveDate;
            case 14:
                return Status;
            case 15:
                return BillingFreq;
            case 16:
                return Balance;
            case 17:
                return LastPmtDate;
            case 18:
                return LastPmtAmt;
            case 19:
                return NoStudents;
            case 20:
                return TuitionSel;
            case 21:
                return Alert;
            case 22:
                return Source;
            case 23:
                return MTuition;
            case 24:
                return ClassTime;
            case 25:
                return P1Name;
            case 26:
                return P1EMail;
            case 27:
                return P1Phone;
            case 28:
                return P1Cell;
            case 29:
                return P1Work;
            case 30:
                return P2Name;
            case 31:
                return P2EMail;
            case 32:
                return P2Phone;
            case 33:
                return P2Cell;
            case 34:
                return P2Work;
            case 35:
                return E1Name;
            case 36:
                return E1Phone;
            case 37:
                return E2Name;
            case 38:
                return E2Phone;
            case 39:
                return E3Name;
            case 40:
                return E3Phone;
            case 41:
                return E4Name;
            case 42:
                return E4Phone;
            case 43:
                return CCType;
            case 44:
                return CCTrail;
            case 45:
                return CCExpire;
            case 46:
                return CCFName;
            case 47:
                return CCLName;
            case 48:
                return CCAddress;
            case 49:
                return CCCity;
            case 50:
                return CCState;
            case 51:
                return CCZip;
            case 52:
                return CCConsentID;
            case 53:
                return CCMonthly;
            case 54:
                return CCDate;
            case 55:
                return Notes;
            case 56:
                return AccountFee;
            case 57:
                return AccountFeeAmount;
            case 58:
                return AcctPWord;
            case 59:
                return OLReg;
            case 60:
                return AgreePol;
            case 61:
                return RequireCreditCard;
            case 62:
                return Complete;
            case 63:
                return ResetString;
            case 64:
                return Activated;
            case 65:
                return UsesNewPassword;
            case 66:
                return AlertID;
            case 67:
                return SourceID;
            case 68:
                return Subscribed;
            case 69:
                return CCToken;
        }

        return null;
    }

    @Override
    public int getPropertyCount() {
        // TODO Auto-generated method stub
        return 70;
    }

    @Override
    public void getPropertyInfo(int index, Hashtable arg1, PropertyInfo info) {
        switch (index) {
            case 0:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "SchID";
                break;
            case 1:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "AcctID";
                break;
            case 2:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "AcctNo";
                break;
            case 3:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "LName";
                break;
            case 4:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "FName";
                break;
            case 5:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Address";
                break;
            case 6:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "City";
                break;
            case 7:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "State";
                break;
            case 8:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ZipCode";
                break;
            case 9:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Phone";
                break;
            case 10:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "EMail";
                break;
            case 11:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "AcctSSN";
                break;
            case 12:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "DateReg";
                break;
            case 13:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "InactiveDate";
                break;
            case 14:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "Status";
                break;
            case 15:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "BillingFreq";
                break;
            case 16:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Balance";
                break;
            case 17:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "LastPmtDate";
                break;
            case 18:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "LastPmtAmt";
                break;
            case 19:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "NoStudents";
                break;
            case 20:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "TuitionSel";
                break;
            case 21:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Alert";
                break;
            case 22:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Source";
                break;
            case 23:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "MTuition";
                break;
            case 24:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "ClassTime";
                break;
            case 25:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "P1Name";
                break;
            case 26:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "P1EMail";
                break;
            case 27:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "P1Phone";
                break;
            case 28:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "P1Cell";
                break;
            case 29:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "P1Work";
                break;
            case 30:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "P2Name";
                break;
            case 31:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "P2EMail";
                break;
            case 32:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "P2Phone";
                break;
            case 33:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "P2Cell";
                break;
            case 34:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "P2Work";
                break;
            case 35:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "E1Name";
                break;
            case 36:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "E1Phone";
                break;
            case 37:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "E2Name";
                break;
            case 38:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "E2Phone";
                break;
            case 39:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "E3Name";
                break;
            case 40:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "E3Phone";
                break;
            case 41:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "E4Name";
                break;
            case 42:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "E4Phone";
                break;
            case 43:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "CCType";
                break;
            case 44:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "CCTrail";
                break;
            case 45:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "CCExpire";
                break;
            case 46:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "CCFName";
                break;
            case 47:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "CCLName";
                break;
            case 48:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "CCAddress";
                break;
            case 49:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "CCCity";
                break;
            case 50:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "CCState";
                break;
            case 51:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "CCZip";
                break;
            case 52:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "CCConsetID";
                break;
            case 53:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "CCMonthly";
                break;
            case 54:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "CCDate";
                break;
            case 55:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Notes";
                break;
            case 56:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "AccountFee";
                break;
            case 57:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "AccountFeeAmount";
                break;
            case 58:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "AcctPWord";
                break;
            case 59:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "OLReg";
                break;
            case 60:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "AgreePol";
                break;
            case 61:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "RequireCreditCard";
                break;
            case 62:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Complete";
                break;
            case 63:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ResetString";
                break;
            case 64:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Activated";
                break;
            case 65:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "UsesNewPassword";
                break;
            case 66:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "AlertID";
                break;
            case 67:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "SourceID";
                break;
            case 68:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Subscribed";
                break;
            case 69:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "CCToken";
                break;
            default:
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
                AcctID = Integer.parseInt(value.toString());
                break;
            case 2:
                if (value.equals("anyType{}")) {
                    AcctNo = "";
                } else {
                    AcctNo = value.toString();
                }
                break;
            case 3:
                if (value.equals("anyType{}")) {
                    LName = "";
                } else {
                    LName = value.toString();
                }
                break;
            case 4:
                if (value.equals("anyType{}")) {
                    FName = "";
                } else {
                    FName = value.toString();
                }
                break;
            case 5:
                if (value.equals("anyType{}")) {
                    Address = "";
                } else {
                    Address = value.toString();
                }
                break;
            case 6:
                if (value.equals("anyType{}")) {
                    City = "";
                } else {
                    City = value.toString();
                }
                break;
            case 7:
                if (value.equals("anyType{}")) {
                    State = "";
                } else {
                    State = value.toString();
                }
                break;
            case 8:
                if (value.equals("anyType{}")) {
                    ZipCode = "";
                } else {
                    ZipCode = value.toString();
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
                if (value.equals("anyType{}")) {
                    EMail = "";
                } else {
                    EMail = value.toString();
                }
                break;
            case 11:
                if (value.equals("anyType{}")) {
                    AcctSSN = "";
                } else {
                    AcctSSN = value.toString();
                }
                break;
            case 12:
                if (value.equals("anyType{}")) {
                    DateReg = "";
                } else {
                    DateReg = value.toString();
                }
                break;
            case 13:
                if (value.equals("anyType{}")) {
                    InactiveDate = "";
                } else {
                    InactiveDate = value.toString();
                }
                break;
            case 14:
                Status = Integer.parseInt(value.toString());
                break;
            case 15:
                BillingFreq = Integer.parseInt(value.toString());
                break;
            case 16:
                Balance = Float.parseFloat(value.toString());
                break;
            case 17:
                if (value.equals("anyType{}")) {
                    LastPmtDate = "";
                } else {
                    LastPmtDate = value.toString();
                }
                break;
            case 18:
                LastPmtAmt = Float.parseFloat(value.toString());
                break;
            case 19:
                NoStudents = Integer.parseInt(value.toString());
                break;
            case 20:
                TuitionSel = Integer.parseInt(value.toString());
                break;
            case 21:
                if (value.equals("anyType{}")) {
                    Alert = "";
                } else {
                    Alert = value.toString();
                }
                break;
            case 22:
                if (value.equals("anyType{}")) {
                    Source = "";
                } else {
                    Source = value.toString();
                }
                break;
            case 23:
                MTuition = Float.parseFloat(value.toString());
                break;
            case 24:
                ClassTime = Integer.parseInt(value.toString());
                break;
            case 25:
                if (value.equals("anyType{}")) {
                    P1Name = "";
                } else {
                    P1Name = value.toString();
                }
                break;
            case 26:
                if (value.equals("anyType{}")) {
                    P1EMail = "";
                } else {
                    P1EMail = value.toString();
                }
                break;
            case 27:
                if (value.equals("anyType{}")) {
                    P1Phone = "";
                } else {
                    P1Phone = value.toString();
                }
                break;
            case 28:
                if (value.equals("anyType{}")) {
                    P1Cell = "";
                } else {
                    P1Cell = value.toString();
                }
                break;
            case 29:
                if (value.equals("anyType{}")) {
                    P1Work = "";
                } else {
                    P1Work = value.toString();
                }
                break;
            case 30:
                if (value.equals("anyType{}")) {
                    P2Name = "";
                } else {
                    P2Name = value.toString();
                }
                break;
            case 31:
                if (value.equals("anyType{}")) {
                    P2EMail = "";
                } else {
                    P2EMail = value.toString();
                }
                break;
            case 32:
                if (value.equals("anyType{}")) {
                    P2Phone = "";
                } else {
                    P2Phone = value.toString();
                }
                break;
            case 33:
                if (value.equals("anyType{}")) {
                    P2Cell = "";
                } else {
                    P2Cell = value.toString();
                }
                break;
            case 34:
                if (value.equals("anyType{}")) {
                    P2Work = "";
                } else {
                    P2Work = value.toString();
                }
                break;
            case 35:
                if (value.equals("anyType{}")) {
                    E1Name = "";
                } else {
                    E1Name = value.toString();
                }
                break;
            case 36:
                if (value.equals("anyType{}")) {
                    E1Phone = "";
                } else {
                    E1Phone = value.toString();
                }
                break;
            case 37:
                if (value.equals("anyType{}")) {
                    E2Name = "";
                } else {
                    E2Name = value.toString();
                }
                break;
            case 38:
                if (value.equals("anyType{}")) {
                    E2Phone = "";
                } else {
                    E2Phone = value.toString();
                }
                break;
            case 39:
                if (value.equals("anyType{}")) {
                    E3Name = "";
                } else {
                    E3Name = value.toString();
                }
                break;
            case 40:
                if (value.equals("anyType{}")) {
                    E3Phone = "";
                } else {
                    E3Phone = value.toString();
                }
                break;
            case 41:
                if (value.equals("anyType{}")) {
                    E4Name = "";
                } else {
                    E4Name = value.toString();
                }
                break;
            case 42:
                if (value.equals("anyType{}")) {
                    E4Phone = "";
                } else {
                    E4Phone = value.toString();
                }
                break;
            case 43:

                CCType = Integer.parseInt(value.toString());
                break;
            case 44:
                if (value.equals("anyType{}")) {
                    CCTrail = "";
                } else {
                    CCTrail = value.toString();
                }
                break;
            case 45:
                if (value.equals("anyType{}")) {
                    CCExpire = "";
                } else {
                    CCExpire = value.toString();
                }
                break;
            case 46:
                if (value.equals("anyType{}")) {
                    CCFName = "";
                } else {
                    CCFName = value.toString();
                }
                break;
            case 47:
                if (value.equals("anyType{}")) {
                    CCLName = "";
                } else {
                    CCLName = value.toString();
                }
                break;
            case 48:
                if (value.equals("anyType{}")) {
                    CCAddress = "";
                } else {
                    CCAddress = value.toString();
                }
                break;
            case 49:
                if (value.equals("anyType{}")) {
                    CCCity = "";
                } else {
                    CCCity = value.toString();
                }
                break;
            case 50:
                if (value.equals("anyType{}")) {
                    CCState = "";
                } else {
                    CCState = value.toString();
                }
                break;
            case 51:
                if (value.equals("anyType{}")) {
                    CCZip = "";
                } else {
                    CCZip = value.toString();
                }
                break;
            case 52:
                CCConsentID = Integer.parseInt(value.toString());
                break;
            case 53:
                CCMonthly = Boolean.parseBoolean(value.toString());
                break;
            case 54:
                CCDate = Integer.parseInt(value.toString());
                break;
            case 55:
                if (value.equals("anyType{}")) {
                    Notes = "";
                } else {
                    Notes = value.toString();
                }
                break;
            case 56:
                if (value.equals("anyType{}")) {
                    AccountFee = "";
                } else {
                    AccountFee = value.toString();
                }
                break;
            case 57:
                AccountFeeAmount = Float.parseFloat(value.toString());
                break;
            case 58:
                if (value.equals("anyType{}")) {
                    AcctPWord = "";
                } else {
                    AcctPWord = value.toString();
                }
                break;
            case 59:
                OLReg = Boolean.parseBoolean(value.toString());
                break;
            case 60:
                AgreePol = Boolean.parseBoolean(value.toString());
                break;
            case 61:
                RequireCreditCard = Boolean.parseBoolean(value.toString());
                break;
            case 62:
                Complete = Boolean.parseBoolean(value.toString());
                break;
            case 63:
                if (value.equals("anyType{}")) {
                    ResetString = "";
                } else {
                    ResetString = value.toString();
                }
                break;
            case 64:
                Activated = Boolean.parseBoolean(value.toString());
                break;
            case 65:
                UsesNewPassword = Boolean.parseBoolean(value.toString());
                break;
            case 66:
                AlertID = Integer.parseInt(value.toString());
                break;
            case 67:
                SourceID = Integer.parseInt(value.toString());
                break;
            case 68:
                Subscribed = Boolean.parseBoolean(value.toString());
                break;
            case 69:
                if (value.equals("anyType{}")) {
                    CCToken = "";
                } else {
                    CCToken = value.toString();
                }
                break;
            default:
                break;


        }

    }

}
