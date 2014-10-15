package com.akadasoftware.danceworksonline.Classes;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class Student implements KvmSerializable {

    public int SchID, StuID, AcctID, Status, TuitionSel, YearsAtSchool,
            NoClasses, ClassTime, CardUses, CardClass1, CardClass2;

    public String StuNo, ScanKey, AcctNo, LName, FName, Address, City, State,
            ZipCode, Phone, Cell, EMail, SSN, BirthDate, School, Grade, YearStarted,
            AcctName, DrName, DrPhone, Allergies, OtherMed, PicturePath, Notes,
            StudentFee, DateReg, InactiveDate, CardExpire;

    public boolean Gender, Discount;

    public float MTuition, Bust, Hips, Waist, Inseam, Girth, StudentFeeAmount;

    public Student() {
    }

    public Student(int schid, int stuid, String stuno, String scankey,
                   int acctid, String acctno, String lname, String fname,
                   String address, String city, String state, String zipcode, String phone,
                   String cell, String email, String ssn, int status,
                   String datereg, String inactivedate, int tuitionsel,
                   boolean gender, boolean discount, String birthdate, String school,
                   String grade, String yearstarted, int yearsatschool,
                   String acctname, int noclasses, float mtuition, int classtime,
                   float bust, float hips, float waits, float inseam, float girth,
                   String drname, String drphone, String allergies, String othermed,
                   String picturepath, String notes, String studentfee,
                   float studentfeeamount, int carduses, String cardexpire,
                   int cardclass1, int cardclass2) {

        SchID = schid;
        StuID = stuid;
        StuNo = stuno;
        ScanKey = scankey;
        AcctID = acctid;
        AcctNo = acctno;
        LName = lname;
        FName = fname;
        Address = address;
        City = city;
        State = state;
        ZipCode = zipcode;
        Phone = phone;
        Cell = cell;
        EMail = email;
        SSN = ssn;
        Status = status;
        DateReg = datereg;
        InactiveDate = inactivedate;
        TuitionSel = tuitionsel;
        Gender = gender;
        Discount = discount;
        BirthDate = birthdate;
        School = school;
        Grade = grade;
        YearStarted = yearstarted;
        YearsAtSchool = yearsatschool;
        AcctName = acctname;
        NoClasses = noclasses;
        MTuition = mtuition;
        ClassTime = classtime;
        Bust = bust;
        Hips = hips;
        Waist = waits;
        Inseam = inseam;
        Girth = girth;
        DrName = drname;
        DrPhone = drphone;
        Allergies = allergies;
        OtherMed = othermed;
        PicturePath = picturepath;
        Notes = notes;
        StudentFee = studentfee;
        StudentFeeAmount = studentfeeamount;
        CardUses = carduses;
        CardExpire = cardexpire;
        CardClass1 = cardclass1;
        CardClass2 = cardclass2;


    }

    @Override
    public Object getProperty(int index) {
        switch (index) {
            case 0:
                return SchID;
            case 1:
                return StuID;
            case 2:
                return StuNo;
            case 3:
                return ScanKey;
            case 4:
                return AcctID;
            case 5:
                return AcctNo;
            case 6:
                return LName;
            case 7:
                return FName;
            case 8:
                return Address;
            case 9:
                return City;
            case 10:
                return State;
            case 11:
                return ZipCode;
            case 12:
                return Phone;
            case 13:
                return Cell;
            case 14:
                return EMail;
            case 15:
                return SSN;
            case 16:
                return Status;
            case 17:
                return DateReg;
            case 18:
                return InactiveDate;
            case 19:
                return TuitionSel;
            case 20:
                return Gender;
            case 21:
                return Discount;
            case 22:
                return BirthDate;
            case 23:
                return School;
            case 24:
                return Grade;
            case 25:
                return YearStarted;
            case 26:
                return YearsAtSchool;
            case 27:
                return AcctName;
            case 28:
                return NoClasses;
            case 29:
                return MTuition;
            case 30:
                return ClassTime;
            case 31:
                return Bust;
            case 32:
                return Hips;
            case 33:
                return Waist;
            case 34:
                return Girth;
            case 35:
                return DrName;
            case 36:
                return DrPhone;
            case 37:
                return Allergies;
            case 38:
                return OtherMed;
            case 39:
                return PicturePath;
            case 40:
                return Notes;
            case 41:
                return StudentFee;
            case 42:
                return StudentFeeAmount;
            case 43:
                return CardUses;
            case 44:
                return CardExpire;
            case 45:
                return CardClass1;
            case 46:
                return CardClass2;
        }

        return null;
    }

    @Override
    public int getPropertyCount() {
        // TODO Auto-generated method stub
        return 47;
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
                info.name = "StuID";
                break;
            case 2:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "StuNo";
                break;
            case 3:
                info.type = PropertyInfo.STRING_CLASS;
                ;
                info.name = "ScanKey";
                break;
            case 4:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "AcctID";
                break;
            case 5:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "AcctNo";
                break;
            case 6:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "LName";
                break;
            case 7:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "FName";
                break;
            case 8:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Address";
                break;
            case 9:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "City";
                break;
            case 10:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "State";
                break;
            case 11:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ZipCode";
                break;
            case 12:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Phone";
                break;
            case 13:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Cell";
                break;
            case 14:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "EMail";
                if ("anyType{}".equals(info)) {
                    info.setValue(" ");
                }
                break;
            case 15:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "SSN";
                break;
            case 16:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "Status";
                break;
            case 17:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "DateReg";
                break;
            case 18:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "InactiveDate";
                break;
            case 19:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "TuitionSel";
                break;
            case 20:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "Gender";
                break;
            case 21:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "Discount";
                break;
            case 22:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "BirthDate";
                break;
            case 23:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "School";
                break;
            case 24:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Grade";
                break;
            case 25:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "YearStarted";
                break;
            case 26:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "YearsAtSchool";
                break;
            case 27:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "AcctName";
                break;
            case 28:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "NoClasses";
                break;
            case 29:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "MTuition";
                break;
            case 30:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "ClassTime";
                break;
            case 31:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Bust";
                break;
            case 32:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Hips";
                break;
            case 33:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Waist";
                break;
            case 34:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Inseam";
                break;
            case 35:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Girth";
                break;
            case 36:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "DrName";
                break;
            case 37:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "DrPhone";
                break;
            case 38:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Allergies";
                break;
            case 39:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "OtherMed";
                break;
            case 40:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "PicturePath";
                break;
            case 41:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Notes";
                break;
            case 42:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "StudentFee";
                break;
            case 43:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "StudentFeeAmount";
                break;
            case 44:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "CardUses";
                break;
            case 45:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "CardExpire";
                break;
            case 46:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "CardClass1";
                break;
            case 47:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "CarClass2";
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
                StuID = Integer.parseInt(value.toString());
                break;
            case 2:
                if (value.equals("anyType{}")) {
                    StuNo = " ";
                } else {
                    StuNo = value.toString();
                }
                break;
            case 3:
                if (value.equals("anyType{}")) {
                    ScanKey = " ";
                } else {
                    ScanKey = value.toString();
                }
                break;
            case 4:
                AcctID = Integer.parseInt(value.toString());
                break;
            case 5:
                if (value.equals("anyType{}")) {
                    AcctNo = " ";
                } else {
                    AcctNo = value.toString();
                }
                break;
            case 6:
                if (value.equals("anyType{}")) {
                    LName = " ";
                } else {
                    LName = value.toString();
                }
                break;
            case 7:
                if (value.equals("anyType{}")) {
                    FName = " ";
                } else {
                    FName = value.toString();
                }
                break;
            case 8:
                if (value.equals("anyType{}")) {
                    Address = " ";
                } else {
                    Address = value.toString();
                }
                break;
            case 9:
                if (value.equals("anyType{}")) {
                    City = " ";
                } else {
                    City = value.toString();
                }
                break;
            case 10:
                if (value.equals("anyType{}")) {
                    State = " ";
                } else {
                    State = value.toString();
                }
                break;
            case 11:
                if (value.equals("anyType{}")) {
                    ZipCode = " ";
                } else {
                    ZipCode = value.toString();
                }
                break;
            case 12:
                if (value.equals("anyType{}")) {
                    Phone = " ";
                } else {
                    Phone = value.toString();
                }
                break;
            case 13:
                if (value.equals("anyType{}")) {
                    Cell = " ";
                } else {
                    Cell = value.toString();
                }
                break;
            case 14:
                if (value.equals("anyType{}")) {
                    EMail = " ";
                } else {
                    EMail = value.toString();
                }
                break;
            case 15:
                if (value.equals("anyType{}")) {
                    SSN = " ";
                } else {
                    SSN = value.toString();
                }
                break;
            case 16:
                Status = Integer.parseInt(value.toString());
                break;
            case 17:
                if (value.equals("anyType{}")) {
                    DateReg = " ";
                } else {
                    DateReg = value.toString();
                }
                break;
            case 18:
                if (value.equals("anyType{}")) {
                    InactiveDate = " ";
                } else {
                    InactiveDate = value.toString();
                }
                break;
            case 19:
                TuitionSel = Integer.parseInt(value.toString());
                break;
            case 20:
                Gender = Boolean.parseBoolean(value.toString());
                break;
            case 21:
                Discount = Boolean.parseBoolean(value.toString());
                break;
            case 22:
                if (value.equals("anyType{}")) {
                    BirthDate = " ";
                } else {
                    BirthDate = value.toString();
                }
                break;
            case 23:
                if (value.equals("anyType{}")) {
                    School = " ";
                } else {
                    School = value.toString();
                }
                break;
            case 24:
                if (value.equals("anyType{}")) {
                    Grade = " ";
                } else {
                    Grade = value.toString();
                }
                break;
            case 25:
                if (value.equals("anyType{}")) {
                    YearStarted = " ";
                } else {
                    YearStarted = value.toString();
                }
                break;
            case 26:
                YearsAtSchool = Integer.parseInt(value.toString());
                break;
            case 27:
                if (value.equals("anyType{}")) {
                    AcctName = " ";
                } else {
                    AcctName = value.toString();
                }
                break;
            case 28:
                NoClasses = Integer.parseInt(value.toString());
                break;
            case 29:
                MTuition = Float.parseFloat(value.toString());
                break;
            case 30:
                ClassTime = Integer.parseInt(value.toString());
                break;
            case 31:
                Bust = Float.parseFloat(value.toString());
                ;
                break;
            case 32:
                Hips = Float.parseFloat(value.toString());
                break;
            case 33:
                Waist = Float.parseFloat(value.toString());
                break;
            case 34:
                Inseam = Float.parseFloat(value.toString());
                break;
            case 35:
                Girth = Float.parseFloat(value.toString());
                break;
            case 36:
                if (value.equals("anyType{}")) {
                    DrName = " ";
                } else {
                    DrName = value.toString();
                }
                break;
            case 37:
                if (value.equals("anyType{}")) {
                    DrPhone = " ";
                } else {
                    DrPhone = value.toString();
                }
                break;
            case 38:
                if (value.equals("anyType{}")) {
                    Allergies = " ";
                } else {
                    Allergies = value.toString();
                }
                break;
            case 39:
                if (value.equals("anyType{}")) {
                    OtherMed = " ";
                } else {
                    OtherMed = value.toString();
                }
                break;
            case 40:
                if (value.equals("anyType{}")) {
                    PicturePath = " ";
                } else {
                    PicturePath = value.toString();
                }
                break;
            case 41:
                if (value.equals("anyType{}")) {
                    Notes = " ";
                } else {
                    Notes = value.toString();
                }
                break;
            case 42:
                if (value.equals("anyType{}")) {
                    StudentFee = " ";
                } else {
                    StudentFee = value.toString();
                }
                break;
            case 43:
                StudentFeeAmount = Float.parseFloat(value.toString());
                break;
            case 44:
                CardUses = Integer.parseInt(value.toString());
                break;
            case 45:
                if (value.equals("anyType{}")) {
                    CardExpire = " ";
                } else {
                    CardExpire = value.toString();
                }
                break;
            case 46:
                CardClass1 = Integer.parseInt(value.toString());
                break;
            case 47:
                CardClass2 = Integer.parseInt(value.toString());
                break;
            default:
                break;
        }

    }

}
