package com.akadasoftware.danceworksonline.Classes;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by Kyle on 1/27/14.
 */
public class ChargeCodes implements KvmSerializable {

    public int SchID, ChgID, Tax;
    public String ChgNo, GLNo, ChgDesc, Kind;
    public float Amount;
    public boolean PayOnline, TaxCredit;

    public ChargeCodes() {
    }

    public ChargeCodes(int schid, int chgid, String chgno, String glno,
                       String chgdesc, String kind, float amount, int tax, boolean payonline, boolean taxcredit) {

        SchID = schid;
        ChgID = chgid;
        ChgNo = chgno;
        GLNo = glno;
        ChgDesc = chgdesc;
        Kind = kind;
        Amount = amount;
        Tax = tax;
        PayOnline = payonline;
        TaxCredit = taxcredit;

    }


    @Override
    public Object getProperty(int index) {
        switch (index) {
            case 0:
                return SchID;
            case 1:
                return ChgID;
            case 2:
                return ChgNo;
            case 3:
                return GLNo;
            case 4:
                return ChgDesc;
            case 5:
                return Kind;
            case 6:
                return Amount;
            case 7:
                return Tax;
            case 8:
                return PayOnline;
            case 9:
                return TaxCredit;
        }

        return null;
    }

    @Override
    public int getPropertyCount() {
        // TODO Auto-generated method stub
        return 10;
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
                info.name = "ChgID";
                break;
            case 2:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ChgNo";
                break;
            case 3:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "GLNo";
                break;
            case 4:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ChgDesc";
                break;
            case 5:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Kind";
                break;
            case 6:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Amount";
                break;
            case 7:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "Tax";
                break;
            case 8:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "PayOnline";
                break;
            case 9:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "TaxCredit";
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
                ChgID = Integer.parseInt(value.toString());
                break;
            case 2:
                if (value.equals("anyType{}")) {
                    ChgNo = "";
                } else {
                    ChgNo = value.toString();
                }
                break;
            case 3:
                if (value.equals("anyType{}")) {
                    GLNo = "";
                } else {
                    GLNo = value.toString();
                }
                break;
            case 4:
                if (value.equals("anyType{}")) {
                    ChgDesc = "";
                } else {
                    ChgDesc = value.toString();
                }
                break;
            case 5:
                if (value.equals("anyType{}")) {
                    Kind = "";
                } else {
                    Kind = value.toString();
                }
                break;
            case 6:
                Amount = Float.parseFloat(value.toString());
                break;
            case 7:
                Tax = Integer.parseInt(value.toString());
                break;
            case 8:
                PayOnline = Boolean.parseBoolean(value.toString());
                break;
            case 9:
                TaxCredit = Boolean.parseBoolean(value.toString());
                break;
            default:
                break;

        }

    }

}
