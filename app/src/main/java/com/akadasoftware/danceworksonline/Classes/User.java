package com.akadasoftware.danceworksonline.Classes;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by DJ on 12/27/13.
 */
public class User implements KvmSerializable {

    public int SchID, UserID, TransType, TransPaymentKind, TransChargeKind, TransDeleted, StudentSort,
            StudentSelection, StudentsShown, StudentGroupCode, AccountSort, AccountSelection,
            AccountsShown, AccountGroupCode, ClassSort, ClassesShown, StaffSort, StaffShown,
            AccountListSize, StudentListSize, ClassListSize, StaffListSize, StaffID;
    public long Access;
    public String UserName, EMailAddr, DisplayName, TransStartDate, TransEndDate, UserGUID;
    public boolean Admin, ProcReg, TransTypeOptions, TransDateOptions, StudentOptions, AccountOptions,
            ClassOptions, StaffOptions, ShowNotes, ShowTransactions, ShowRegistrations;

    public User() {
    }

    public User(int schid, int userid, String username, String emailaddr, boolean admin, long access, boolean procreg, String displayname, boolean transtypeoptions, int transtype, int transpaymentkind, int transchargekind, boolean transdateoptions, String transstartdate, String transenddate, int transdeleted, boolean studentoptions, int studentsort, int studentselection, int studentsshown, int studentgroupcode, boolean accountoptions, int accountsort, int accountselection, int accountsshown, int accountgroupcode, boolean classoptions, int classsort, int classesshown, boolean staffoptions, int staffsort, int staffshown, boolean shownotes, boolean showtransactions, boolean showregistrations, int accountlistsize, int studentlistsize, int classlistsize, int stafflistsize, String userguid, int staffid) {

        SchID = schid;
        UserID = userid;
        UserName = username;
        EMailAddr = emailaddr;
        Admin = admin;
        Access = access;
        ProcReg = procreg;
        DisplayName = displayname;
        TransTypeOptions = transtypeoptions;
        TransType = transtype;
        TransPaymentKind = transpaymentkind;
        TransChargeKind = transchargekind;
        TransDateOptions = transdateoptions;
        TransStartDate = transstartdate;
        TransEndDate = transenddate;
        TransDeleted = transdeleted;
        StudentOptions = studentoptions;
        StudentSort = studentsort;
        StudentSelection = studentselection;
        StudentsShown = studentsshown;
        StudentGroupCode = studentgroupcode;
        AccountOptions = accountoptions;
        AccountSort = accountsort;
        AccountSelection = accountselection;
        AccountsShown = accountsshown;
        AccountGroupCode = accountgroupcode;
        ClassOptions = classoptions;
        ClassSort = classsort;
        ClassesShown = classesshown;
        StaffOptions = staffoptions;
        StaffSort = staffsort;
        StaffShown = staffshown;
        ShowNotes = shownotes;
        ShowTransactions = showtransactions;
        ShowRegistrations = showregistrations;
        AccountListSize = accountlistsize;
        StudentListSize = studentlistsize;
        ClassListSize = classlistsize;
        StaffListSize = stafflistsize;
        UserGUID = userguid;
        StaffID = staffid;
    }

    @Override
    public Object getProperty(int index) {
        switch (index) {
            case 0:
                return SchID;
            case 1:
                return UserID;
            case 2:
                return UserName;
            case 3:
                return EMailAddr;
            case 4:
                return Admin;
            case 5:
                return Access;
            case 6:
                return ProcReg;
            case 7:
                return DisplayName;
            case 8:
                return TransTypeOptions;
            case 9:
                return TransType;
            case 10:
                return TransPaymentKind;
            case 11:
                return TransChargeKind;
            case 12:
                return TransDateOptions;
            case 13:
                return TransStartDate;
            case 14:
                return TransEndDate;
            case 15:
                return TransDeleted;
            case 16:
                return StudentOptions;
            case 17:
                return StudentSort;
            case 18:
                return StudentSelection;
            case 19:
                return StudentsShown;
            case 20:
                return StudentGroupCode;
            case 21:
                return AccountOptions;
            case 22:
                return AccountSort;
            case 23:
                return AccountSelection;
            case 24:
                return AccountsShown;
            case 25:
                return AccountGroupCode;
            case 26:
                return ClassOptions;
            case 27:
                return ClassSort;
            case 28:
                return ClassesShown;
            case 29:
                return StaffOptions;
            case 30:
                return StaffSort;
            case 31:
                return StaffShown;
            case 32:
                return ShowNotes;
            case 33:
                return ShowTransactions;
            case 34:
                return ShowRegistrations;
            case 35:
                return AccountListSize;
            case 36:
                return StudentListSize;
            case 37:
                return ClassListSize;
            case 38:
                return StaffListSize;
            case 39:
                return UserGUID;
            case 40:
                return StaffID;
        }

        return null;
    }

    @Override
    public int getPropertyCount() {
        // TODO Auto-generated method stub
        return 41;
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
                info.name = "UserID";
                break;
            case 2:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "UserName";
                break;
            case 3:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "EMailAddr";
                break;
            case 4:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "Admin";
                break;
            case 5:
                info.type = PropertyInfo.LONG_CLASS;
                info.name = "Access";
                break;
            case 6:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "ProcReg";
                break;
            case 7:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "DisplayName";
                break;
            case 8:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "TransTypeOptions";
                break;
            case 9:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "TransType";
                break;
            case 10:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "TransPaymentKind";
                break;
            case 11:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "TransChargeKind";
                break;
            case 12:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "TransDateOptions";
                break;
            case 13:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "TransStartDate";
                break;
            case 14:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "TransEndDate";
                break;
            case 15:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "TransDeleted";
                break;
            case 16:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "StudentOptions";
                break;
            case 17:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "StudentSort";
                break;
            case 18:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "StudentSelection";
                break;
            case 19:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "StudentsShown";
                break;
            case 20:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "StudentGroupCode";
                break;
            case 21:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "AccountOptions";
                break;
            case 22:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "AccountSort";
                break;
            case 23:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "AccountSelection";
                break;
            case 24:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "AccountsShown";
                break;
            case 25:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "AccountGroupCode";
                break;
            case 26:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "ClassOptions";
                break;
            case 27:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "ClassSort";
                break;
            case 28:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "ClassesShown";
                break;
            case 29:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "StaffOptions";
                break;
            case 30:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "StaffSort";
                break;
            case 31:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "StaffShown";
                break;
            case 32:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "ShowNotes";
                break;
            case 33:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "ShowTransactions";
                break;
            case 34:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "ShowRegistrations";
                break;
            case 35:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "AccountListSize";
                break;
            case 36:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "StudentListSize";
                break;
            case 37:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "ClassListSize";
                break;
            case 38:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "StaffListSize";
                break;
            case 39:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "UserGUID";
                break;
            case 40:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "StaffID";
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
                UserID = Integer.parseInt(value.toString());
                break;
            case 2:
                UserName = value.toString();
                break;
            case 3:
                EMailAddr = value.toString();
                break;
            case 4:
                Admin = Boolean.parseBoolean(value.toString());
                break;
            case 5:
                Access = Long.parseLong(value.toString());
                break;
            case 6:
                ProcReg = Boolean.parseBoolean(value.toString());
                break;
            case 7:
                DisplayName = value.toString();
                break;
            case 8:
                TransTypeOptions = Boolean.parseBoolean(value.toString());
                break;
            case 9:
                TransType = Integer.parseInt(value.toString());
                break;
            case 10:
                TransPaymentKind = Integer.parseInt(value.toString());
                break;
            case 11:
                TransChargeKind = Integer.parseInt(value.toString());
                break;
            case 12:
                TransDateOptions = Boolean.parseBoolean(value.toString());
                break;
            case 13:
                TransStartDate = value.toString();
                break;
            case 14:
                TransEndDate = value.toString();
                break;
            case 15:
                TransDeleted = Integer.parseInt(value.toString());
                break;
            case 16:
                StudentOptions = Boolean.parseBoolean(value.toString());
                break;
            case 17:
                StudentSort = Integer.parseInt(value.toString());
                break;
            case 18:
                StudentSelection = Integer.parseInt(value.toString());
                break;
            case 19:
                StudentsShown = Integer.parseInt(value.toString());
                break;
            case 20:
                StudentGroupCode = Integer.parseInt(value.toString());
                break;
            case 21:
                AccountOptions = Boolean.parseBoolean(value.toString());
                break;
            case 22:
                AccountSort = Integer.parseInt(value.toString());
                break;
            case 23:
                AccountSelection = Integer.parseInt(value.toString());
                break;
            case 24:
                AccountsShown = Integer.parseInt(value.toString());
                break;
            case 25:
                AccountGroupCode = Integer.parseInt(value.toString());
                break;
            case 26:
                ClassOptions = Boolean.parseBoolean(value.toString());
                break;
            case 27:
                ClassSort = Integer.parseInt(value.toString());
                break;
            case 28:
                ClassesShown = Integer.parseInt(value.toString());
                break;
            case 29:
                StaffOptions = Boolean.parseBoolean(value.toString());
                break;
            case 30:
                StaffSort = Integer.parseInt(value.toString());
                break;
            case 31:
                StaffShown = Integer.parseInt(value.toString());
                break;
            case 32:
                ShowNotes = Boolean.parseBoolean(value.toString());
                break;
            case 33:
                ShowTransactions = Boolean.parseBoolean(value.toString());
                break;
            case 34:
                ShowRegistrations = Boolean.parseBoolean(value.toString());
                break;
            case 35:
                AccountListSize = Integer.parseInt(value.toString());
                break;
            case 36:
                StudentListSize = Integer.parseInt(value.toString());
                break;
            case 37:
                ClassListSize = Integer.parseInt(value.toString());
                break;
            case 38:
                StaffListSize = Integer.parseInt(value.toString());
                break;
            case 39:
                UserGUID = value.toString();
                break;
            case 40:
                StaffID = Integer.parseInt(value.toString());
                break;
            default:
                break;
        }

    }
}
