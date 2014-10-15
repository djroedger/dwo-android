package com.akadasoftware.danceworksonline.Classes;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by Kyle on 1/15/2014.
 */
public class AccountTransactions implements KvmSerializable {

    public int SchID, TID, AcctID, POSInv, TransPostHistID, SessionID, ConsentID;
    public String TNo, TDate, TDesc, GLNo, ChkNo, Status, Type, Kind, CCard, CCDate, CCAuth, CCRecNo,
            PaymentID, ProcessData, RefNo, AuthCode, Invoice, AcqRefData, CardHolderName, CCToken, EvoTransactionId,
            EvoApprovalCode, EvoBatchId, EvoServiceTransactionId;
    public boolean POSTrans, Closed, PayOnline;
    public float Amount, Balance, STax1, STax2, DiscAmt;

    public AccountTransactions() {
    }

    public AccountTransactions(int schid, int tid, String tno, int acctid, String tdate, String tdesc,
                               String glno, String chkno, float amount, float balance, float stax1, float stax2,
                               String status, String type, String kind, String ccard, String ccdate, String ccauth,
                               String ccrecno, Boolean postrans, int posinv, Boolean closed, Boolean payonline,
                               int transposthistid, int sessionid, float discamt, int consentid, String paymentid,
                               String processdata, String refno, String authcode, String invoice, String acqrefdata,
                               String cardhodlername, String cctoken, String evotransactionid, String evoapprovalcode,
                               String evobatchid, String evoservicetransactionid) {

        SchID = schid;
        TID = tid;
        TNo = tno;
        AcctID = acctid;
        TDate = tdate;
        TDesc = tdesc;
        GLNo = glno;
        ChkNo = chkno;
        Amount = amount;
        Balance = balance;
        STax1 = stax1;
        STax2 = stax2;
        Status = status;
        Type = type;
        Kind = kind;
        CCard = ccard;
        CCDate = ccdate;
        CCAuth = ccauth;
        CCRecNo = ccrecno;
        POSTrans = postrans;
        POSInv = posinv;
        Closed = closed;
        PayOnline = payonline;
        TransPostHistID = transposthistid;
        SessionID = sessionid;
        DiscAmt = discamt;
        ConsentID = consentid;
        PaymentID = paymentid;
        ProcessData = processdata;
        RefNo = refno;
        AuthCode = authcode;
        Invoice = invoice;
        AcqRefData = acqrefdata;
        CardHolderName = cardhodlername;
        cctoken = CCToken;
        evotransactionid = EvoTransactionId;
        evoapprovalcode = EvoApprovalCode;
        evobatchid = EvoBatchId;
        evoservicetransactionid = EvoServiceTransactionId;
    }


    @Override
    public Object getProperty(int index) {
        switch (index) {
            case 0:
                return SchID;
            case 1:
                return TID;
            case 2:
                return TNo;
            case 3:
                return AcctID;
            case 4:
                return TDate;
            case 5:
                return TDesc;
            case 6:
                return GLNo;
            case 7:
                return ChkNo;
            case 8:
                return Amount;
            case 9:
                return Balance;
            case 10:
                return STax1;
            case 11:
                return STax2;
            case 12:
                return Status;
            case 13:
                return Type;
            case 14:
                return Kind;
            case 15:
                return CCard;
            case 16:
                return CCDate;
            case 17:
                return CCAuth;
            case 18:
                return CCRecNo;
            case 19:
                return POSTrans;
            case 20:
                return POSInv;
            case 21:
                return Closed;
            case 22:
                return PayOnline;
            case 23:
                return TransPostHistID;
            case 24:
                return SessionID;
            case 25:
                return DiscAmt;
            case 26:
                return ConsentID;
            case 27:
                return PaymentID;
            case 28:
                return ProcessData;
            case 29:
                return RefNo;
            case 30:
                return AuthCode;
            case 31:
                return Invoice;
            case 32:
                return AcqRefData;
            case 33:
                return CardHolderName;
            case 34:
                return CCToken;
            case 35:
                return EvoTransactionId;
            case 36:
                return EvoApprovalCode;
            case 37:
                return EvoBatchId;
            case 38:
                return EvoServiceTransactionId;
        }

        return null;
    }

    @Override
    public int getPropertyCount() {
        // TODO Auto-generated method stub
        return 39;
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
                info.name = "TID";
                break;
            case 2:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "TNo";
                break;
            case 3:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "AcctID";
                break;
            case 4:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "TDate";
                break;
            case 5:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "TDesc";
                break;
            case 6:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "GLNo";
                break;
            case 7:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ChkNo";
                break;
            case 8:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Amount";
                break;
            case 9:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Balance";
                break;
            case 10:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "STax1";
                break;
            case 11:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "STax2";
                break;
            case 12:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Status";
                break;
            case 13:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Type";
                break;
            case 14:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Kind";
                break;
            case 15:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "CCard";
                break;
            case 16:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "CCDate";
                break;
            case 17:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "CCAuth";
                break;
            case 18:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "CCRecNo";
                break;
            case 19:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "POSTrans";
                break;
            case 20:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "POSInv";
                break;
            case 21:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "Closed";
                break;
            case 22:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "PayOnline";
                break;
            case 23:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "TransPostHistID";
                break;
            case 24:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "SessionID";
                break;
            case 25:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "DiscAmt";
                break;
            case 26:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "ConsentID";
                break;
            case 27:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "PaymentID";
                break;
            case 28:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ProcessData";
                break;
            case 29:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "RefNo";
                break;
            case 30:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "AuthCode";
                break;
            case 31:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Invoice";
                break;
            case 32:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "AcqRefData";
                break;
            case 33:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "CardHolderName";
                break;
            case 34:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "CCToken ";
                break;
            case 35:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "EvoTransactionId ";
                break;
            case 36:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "EvoApprovalCode  ";
                break;
            case 37:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "EvoBatchId";
                break;
            case 38:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "EvoServiceTransactionId ";
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
                TID = Integer.parseInt(value.toString());
                break;
            case 2:
                if (value.equals("anyType{}")) {
                    TNo = "";
                } else {
                    TNo = value.toString();
                }
                break;
            case 3:
                AcctID = Integer.parseInt(value.toString());
                break;
            case 4:
                if (value.equals("anyType{}")) {
                    TDate = "";
                } else {
                    TDate = value.toString();
                }
                break;
            case 5:
                if (value.equals("anyType{}")) {
                    TDesc = "";
                } else {
                    TDesc = value.toString();
                }
                break;
            case 6:
                if (value.equals("anyType{}")) {
                    GLNo = "";
                } else {
                    GLNo = value.toString();
                }
                break;
            case 7:
                if (value.equals("anyType{}")) {
                    ChkNo = "";
                } else {
                    ChkNo = value.toString();
                }
                break;
            case 8:
                Amount = Float.parseFloat(value.toString());
                break;
            case 9:
                Balance = Float.parseFloat(value.toString());
                break;
            case 10:
                STax1 = Float.parseFloat(value.toString());
                break;
            case 11:
                STax2 = Float.parseFloat(value.toString());
                break;
            case 12:
                if (value.equals("anyType{}")) {
                    Status = "";
                } else Status = value.toString();
                break;
            case 13:
                if (value.equals("anyType{}")) {
                    Type = "";
                } else {
                    Type = value.toString();
                }
                break;
            case 14:
                if (value.equals("anyType{}")) {
                    Kind = "";
                } else {
                    Kind = value.toString();
                }
                break;
            case 15:
                if (value.equals("anyType{}")) {
                    CCard = "";
                } else {
                    CCard = value.toString();
                }
                break;
            case 16:
                if (value.equals("anyType{}")) {
                    CCDate = "";
                } else {
                    CCDate = value.toString();
                }
                break;
            case 17:
                if (value.equals("anyType{}")) {
                    CCAuth = "";
                } else {
                    CCAuth = value.toString();
                }
                break;
            case 18:
                if (value.equals("anyType{}")) {
                    CCRecNo = "";
                } else {
                    CCRecNo = value.toString();
                }
                break;
            case 19:
                POSTrans = Boolean.parseBoolean(value.toString());
                break;
            case 20:
                POSInv = Integer.parseInt(value.toString());
                break;
            case 21:
                Closed = Boolean.parseBoolean(value.toString());
                break;
            case 22:
                PayOnline = Boolean.parseBoolean(value.toString());
                break;
            case 23:
                TransPostHistID = Integer.parseInt(value.toString());
                break;
            case 24:
                SessionID = Integer.parseInt(value.toString());
                break;
            case 25:
                DiscAmt = Float.parseFloat(value.toString());
                break;
            case 26:
                ConsentID = Integer.parseInt(value.toString());
                break;
            case 27:
                if (value.equals("anyType{}")) {
                    PaymentID = "";
                } else {
                    PaymentID = value.toString();
                }
                break;
            case 28:
                if (value.equals("anyType{}")) {
                    ProcessData = "";
                } else {
                    ProcessData = value.toString();
                }
                break;
            case 29:
                if (value.equals("anyType{}")) {
                    RefNo = "";
                } else {
                    RefNo = value.toString();
                }
                break;
            case 30:
                if (value.equals("anyType{}")) {
                    AuthCode = "";
                } else {
                    AuthCode = value.toString();
                }
                break;
            case 31:
                if (value.equals("anyType{}")) {
                    Invoice = "";
                } else {
                    Invoice = value.toString();
                }
                break;
            case 32:
                if (value.equals("anyType{}")) {
                    AcqRefData = "";
                } else {
                    AcqRefData = value.toString();
                }
                break;
            case 33:
                if (value.equals("anyType{}")) {
                    CardHolderName = "";
                } else {
                    CardHolderName = value.toString();
                }
                break;
            case 34:
                if (value.equals("anyType{}")) {
                    CCToken = "";
                } else {
                    CCToken = value.toString();
                }
                break;
            case 35:
                if (value.equals("anyType{}")) {
                    EvoTransactionId = "";
                } else {
                    EvoTransactionId = value.toString();
                }
                break;
            case 36:
                if (value.equals("anyType{}")) {
                    EvoApprovalCode = "";
                } else {
                    EvoApprovalCode = value.toString();
                }
                break;
            case 37:
                if (value.equals("anyType{}")) {
                    EvoBatchId = "";
                } else {
                    EvoBatchId = value.toString();
                }
                break;
            case 38:
                if (value.equals("anyType{}")) {
                    EvoServiceTransactionId = "";
                } else {
                    EvoServiceTransactionId = value.toString();
                }
                break;
            default:
                break;

        }

    }

}
