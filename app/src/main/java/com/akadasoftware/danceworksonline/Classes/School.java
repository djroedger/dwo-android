package com.akadasoftware.danceworksonline.Classes;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by DJ on 12/28/13.
 */
public class School implements KvmSerializable {

    public int SchID, SStatus, SessionID, EMailNotify, EMailPort, EMailSize,
            MaxStudents, ClassSizeMax, ClassLength, ClassSortOrder, AcctProg,
            TuitionSelect, BackColor, CCMerchantNo, CCBatchNo, StmtFmt,
            StmtType, MercuryAVSCheck, MercuryCVVCheck;

    public String SRegNo, SName, SAddress, SAddress2, SCity, SState, SZip,
            SPhone, SFax, SContact, STaxID, SWebsite, SEMail, EMailSrvr,
            EMailAcct, EMailPwrd, EMailSig, EMailCon, EMailRec, EMailReg,
            EMailStm, DefaultCity, DefaultState, DefaultZip, DefaultAreaCode,
            PhoneMask, StudentGradeUpdate, SPassword, OPassword, CPassword,
            AcctLinkPath, BankAcct, CustDepAcct, RefundAcct, ARAcct,
            DiscountAcct, ST1Acct, ST1Desc, ST2Acct, ST2Desc, BackupDate,
            BackupPath, StatementNote, PaymentTerms, ConfirmNote,
            RegistrationNote, ReceiptNote, CouponNote, TaxCreditNote,
            CostumeNote, RConfirmNote, ShipName, ShipAddr, ShipAddr2, ShipCity,
            ShipState, ShipZip, ShipPhone, CCProcessor, CCUserName, CCPassword,
            YearUpdate, Closeout, OLPWord, ReturnPolicy, DeclineMessage,
            MCAPIString, MCListID, MercuryUsername, MercuryPassword, LogoName;

    public float DiscAmount, DiscRound, LateChgAmt, ClassTuition, ST1Rate,
            ST2Rate, RegStu01, RegStu02, RegStu03, RegStu04, RegStu05,
            RegStu06, CCMaxAmt, Drawer;

    public boolean DiscType, LateChgType, ClassConflictChk, BalFwd, HideAcct,
            StmtSNames, Receipt80, CCAMEX, CCDiscover, CCMasterCard, CCVisa,
            MCActive, UseSchoolEmail;

    public School() {
    }

    public School(int schid, int sstatus, String sregno, String sname,
                  int sessionid, String saddress, String saddress2, String scity,
                  String sstate, String szip, String sphone, String sfax,
                  String scontact, String staxid, String swebsite, String semail,
                  String emailsrvr, String emailacct, String emailpwrd,
                  int emailnotify, int emailport, int emailsize, String emailsig,
                  String emailcon, String emailrec, String emailreg, String emailstm,
                  int maxstudents, String defaultcity, String defaultstate,
                  String defaultzip, String defaultareacode, String phonemask,
                  boolean disctype, float discamount, float discround,
                  boolean latechgtype, float latechgamt, float classtuition,
                  int classsizemax, int classlength, boolean classconflictchk,
                  int classsortorder, String studentgradeupdate, String spassword,
                  String opassword, String cpassword, int acctprog,
                  String acctlinkpath, String bankacct, String custdepacct,
                  String refundacct, String aracct, String discountacct,
                  String st1acct, String st1desc, float st1rate, String st2acct,
                  String st2desc, float st2rate, String backupdate,
                  String backuppath, String statementnote, String paymentterms,
                  String confirmnote, String registrationnote, String receiptnote,
                  String couponnote, String taxcreditnote, String costumenote,
                  String rconfirmnote, int tuitionselect, boolean balfwd,
                  boolean hideacct, int backcolor, float regstu01, float regstu02,
                  float regstu03, float regstu04, float regstu05, float regstu06,
                  String shipname, String shipaddr, String shipaddr2, String shipcity,
                  String shipstate, String shipzip, String shipphone,
                  String ccprocessor, String ccusername, String ccpassword,
                  int ccmerchantno, int ccbatchno, float ccmaxamt, int stmtfmt,
                  int stmttype, boolean stmtsnames, String yearupdate,
                  String closeout, float drawer, String olpword, boolean receipt80,
                  String returnpolicy, boolean ccamex, boolean ccdiscover,
                  boolean ccmastercard, boolean ccvisa, String declinemessage,
                  boolean mcactive, String mcapistring, String mclistid,
                  boolean useschoolemail, String mercuryusername,
                  String mercurypassword, int mercuryavscheck, int mercurycvvcheck, String logoname) {

        SchID = schid;
        SStatus = sstatus;
        SRegNo = sregno;
        SName = sname;
        SessionID = sessionid;
        SAddress = saddress;
        SAddress2 = saddress2;
        SCity = scity;
        SState = sstate;
        SZip = szip;
        SPhone = sphone;
        SFax = sfax;
        SContact = scontact;
        STaxID = staxid;
        SWebsite = swebsite;
        SEMail = semail;
        EMailSrvr = emailsrvr;
        EMailAcct = emailacct;
        EMailPwrd = emailpwrd;
        EMailNotify = emailnotify;
        EMailPort = emailport;
        EMailSize = emailsize;
        EMailSig = emailsig;
        EMailCon = emailcon;
        EMailRec = emailrec;
        EMailReg = emailreg;
        EMailStm = emailstm;
        MaxStudents = maxstudents;
        DefaultCity = defaultcity;
        DefaultState = defaultstate;
        DefaultZip = defaultzip;
        DefaultAreaCode = defaultareacode;
        PhoneMask = phonemask;
        DiscType = disctype;
        DiscAmount = discamount;
        DiscRound = discround;
        LateChgType = latechgtype;
        LateChgAmt = latechgamt;
        ClassTuition = classtuition;
        ClassSizeMax = classsizemax;
        ClassLength = classlength;
        ClassConflictChk = classconflictchk;
        ClassSortOrder = classsortorder;
        StudentGradeUpdate = studentgradeupdate;
        SPassword = spassword;
        OPassword = opassword;
        CPassword = cpassword;
        AcctProg = acctprog;
        AcctLinkPath = acctlinkpath;
        BankAcct = bankacct;
        CustDepAcct = custdepacct;
        RefundAcct = refundacct;
        ARAcct = aracct;
        DiscountAcct = discountacct;
        ST1Acct = st1acct;
        ST1Desc = st1desc;
        ST1Rate = st1rate;
        ST2Acct = st2acct;
        ST2Desc = st2desc;
        ST2Rate = st2rate;
        BackupDate = backupdate;
        BackupPath = backuppath;
        StatementNote = statementnote;
        PaymentTerms = paymentterms;
        ConfirmNote = confirmnote;
        RegistrationNote = registrationnote;
        ReceiptNote = receiptnote;
        CouponNote = couponnote;
        TaxCreditNote = taxcreditnote;
        CostumeNote = costumenote;
        RConfirmNote = rconfirmnote;
        TuitionSelect = tuitionselect;
        BalFwd = balfwd;
        HideAcct = hideacct;
        BackColor = backcolor;
        RegStu01 = regstu01;
        RegStu02 = regstu02;
        RegStu03 = regstu03;
        RegStu04 = regstu04;
        RegStu05 = regstu05;
        RegStu06 = regstu06;
        ShipName = shipname;
        ShipAddr = shipaddr;
        ShipAddr2 = shipaddr2;
        ShipCity = shipcity;
        ShipState = shipstate;
        ShipZip = shipzip;
        ShipPhone = shipphone;
        CCProcessor = ccprocessor;
        CCUserName = ccusername;
        CCPassword = ccpassword;
        CCMerchantNo = ccmerchantno;
        CCBatchNo = ccbatchno;
        CCMaxAmt = ccmaxamt;
        StmtFmt = stmtfmt;
        StmtType = stmttype;
        StmtSNames = stmtsnames;
        YearUpdate = yearupdate;
        Closeout = closeout;
        Drawer = drawer;
        OLPWord = olpword;
        Receipt80 = receipt80;
        ReturnPolicy = returnpolicy;
        CCAMEX = ccamex;
        CCDiscover = ccdiscover;
        CCMasterCard = ccmastercard;
        CCVisa = ccvisa;
        DeclineMessage = declinemessage;
        MCActive = mcactive;
        MCAPIString = mcapistring;
        MCListID = mclistid;
        UseSchoolEmail = useschoolemail;
        MercuryUsername = mercuryusername;
        MercuryPassword = mercurypassword;
        MercuryAVSCheck = mercuryavscheck;
        MercuryCVVCheck = mercurycvvcheck;
        LogoName = logoname;


    }

    @Override
    public Object getProperty(int index) {
        switch (index) {
            case 0:
                return SchID;
            case 1:
                return SStatus;
            case 2:
                return SRegNo;
            case 3:
                return SessionID;
            case 4:
                return SName;
            case 5:
                return SAddress;
            case 6:
                return SAddress2;
            case 7:
                return SCity;
            case 8:
                return SState;
            case 9:
                return SZip;
            case 10:
                return SPhone;
            case 11:
                return SFax;
            case 12:
                return SContact;
            case 13:
                return STaxID;
            case 14:
                return SWebsite;
            case 15:
                return SEMail;
            case 16:
                return EMailSrvr;
            case 17:
                return EMailAcct;
            case 18:
                return EMailPwrd;
            case 19:
                return EMailNotify;
            case 20:
                return EMailPort;
            case 21:
                return EMailSize;
            case 22:
                return EMailSig;
            case 23:
                return EMailCon;
            case 24:
                return EMailRec;
            case 25:
                return EMailReg;
            case 26:
                return EMailStm;
            case 27:
                return MaxStudents;
            case 28:
                return DefaultCity;
            case 29:
                return DefaultState;
            case 30:
                return DefaultZip;
            case 31:
                return DefaultAreaCode;
            case 32:
                return PhoneMask;
            case 33:
                return DiscType;
            case 34:
                return DiscAmount;
            case 35:
                return DiscRound;
            case 36:
                return LateChgType;
            case 37:
                return LateChgAmt;
            case 38:
                return ClassTuition;
            case 39:
                return ClassSizeMax;
            case 40:
                return ClassLength;
            case 41:
                return ClassConflictChk;
            case 42:
                return ClassSortOrder;
            case 43:
                return StudentGradeUpdate;
            case 44:
                return SPassword;
            case 45:
                return OPassword;
            case 46:
                return CPassword;
            case 47:
                return AcctProg;
            case 48:
                return AcctLinkPath;
            case 49:
                return BankAcct;
            case 50:
                return CustDepAcct;
            case 51:
                return RefundAcct;
            case 52:
                return ARAcct;
            case 53:
                return DiscountAcct;
            case 54:
                return ST1Acct;
            case 55:
                return ST1Desc;
            case 56:
                return ST1Rate;
            case 57:
                return ST2Acct;
            case 58:
                return ST2Desc;
            case 59:
                return ST2Rate;
            case 60:
                return BackupDate;
            case 61:
                return BackupPath;
            case 62:
                return StatementNote;
            case 63:
                return PaymentTerms;
            case 64:
                return ConfirmNote;
            case 65:
                return RegistrationNote;
            case 66:
                return ReceiptNote;
            case 67:
                return CouponNote;
            case 68:
                return TaxCreditNote;
            case 69:
                return CostumeNote;
            case 70:
                return RConfirmNote;
            case 71:
                return TuitionSelect;
            case 72:
                return BalFwd;
            case 73:
                return HideAcct;
            case 74:
                return BackColor;
            case 75:
                return RegStu01;
            case 76:
                return RegStu02;
            case 77:
                return RegStu03;
            case 78:
                return RegStu04;
            case 79:
                return RegStu05;
            case 80:
                return RegStu06;
            case 81:
                return ShipName;
            case 82:
                return ShipAddr;
            case 83:
                return ShipAddr2;
            case 84:
                return ShipCity;
            case 85:
                return ShipState;
            case 86:
                return ShipZip;
            case 87:
                return ShipPhone;
            case 88:
                return CCProcessor;
            case 89:
                return CCUserName;
            case 90:
                return CCPassword;
            case 91:
                return CCMerchantNo;
            case 92:
                return CCBatchNo;
            case 93:
                return CCMaxAmt;
            case 94:
                return StmtFmt;
            case 95:
                return StmtType;
            case 96:
                return StmtSNames;
            case 97:
                return YearUpdate;
            case 98:
                return Closeout;
            case 99:
                return Drawer;
            case 100:
                return OLPWord;
            case 101:
                return Receipt80;
            case 102:
                return ReturnPolicy;
            case 103:
                return CCAMEX;
            case 104:
                return CCDiscover;
            case 105:
                return CCMasterCard;
            case 106:
                return CCVisa;
            case 107:
                return DeclineMessage;
            case 108:
                return MCActive;
            case 109:
                return MCAPIString;
            case 110:
                return MCListID;
            case 111:
                return UseSchoolEmail;
            case 112:
                return MercuryUsername;
            case 113:
                return MercuryPassword;
            case 114:
                return MercuryAVSCheck;
            case 115:
                return MercuryCVVCheck;
            case 116:
                return LogoName;
        }

        return null;
    }

    @Override
    public int getPropertyCount() {
        // TODO Auto-generated method stub
        return 117;
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
                info.name = "SStatus";
                break;
            case 2:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "SRegNo";
                break;
            case 3:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "SessionID";

                break;
            case 4:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "SName";
                break;
            case 5:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "SAddress";
                break;
            case 6:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "SAddress2";
                break;
            case 7:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "SCity";
                break;
            case 8:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "SState";
                break;
            case 9:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "SZip";
                break;
            case 10:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "SPhone";
                break;
            case 11:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "SFax";
                break;
            case 12:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "SContact";
                break;
            case 13:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "STaxID";
                break;
            case 14:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "SWebsite";
                break;
            case 15:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "SEMail";
                break;
            case 16:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "EMailSrvr";
                break;
            case 17:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "EMailAcct";
                break;
            case 18:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "EMailPwrd";
                break;
            case 19:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "EMailNotify";
                break;
            case 20:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "EMailPort";
                break;
            case 21:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "EMailSize";
                break;
            case 22:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "EMailSig";
                break;
            case 23:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "EMailCon";
                break;
            case 24:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "EMailRec";
                break;
            case 25:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "EMailReg";
                break;
            case 26:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "EMailStm";
                break;
            case 27:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "MaxStudents";
                break;
            case 28:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "DefaultCity";
                break;
            case 29:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "DefaultState";
                break;
            case 30:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "DefaultZip";
                break;
            case 31:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "DefaultAreaCode";
                break;
            case 32:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "PhoneMask";
                break;
            case 33:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "DiscType";
                break;
            case 34:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "DiscAmount";
                break;
            case 35:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "DiscRound";
                break;
            case 36:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "LateChgType";
                break;
            case 37:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "LateChgAmt";
                break;
            case 38:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ClassTuition";
                break;
            case 39:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "ClassSizeMax";
                break;
            case 40:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "ClassLength";
                break;
            case 41:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "ClassConflictChk";
                break;
            case 42:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "ClassSortOrder";
                break;
            case 43:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "StudentGradeUpdate";
                break;
            case 44:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "SPassword";
                break;
            case 45:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "OPassword";
                break;
            case 46:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "CPassword";
                break;
            case 47:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "AcctProg";
                break;
            case 48:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "AcctLinkPath";
                break;
            case 49:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "BankAcct";
                break;
            case 50:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "CustDepAcct";
                break;
            case 51:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "RefundAcct";
                break;
            case 52:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "ARAcct";
                break;
            case 53:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "DiscountAcct";
                break;
            case 54:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ST1Acct";
                break;
            case 55:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ST1Desc";
                break;
            case 56:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ST1Rate";
                break;
            case 57:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ST2Acct";
                break;
            case 58:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ST2Desc";
                break;
            case 59:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ST2Rate";
                break;
            case 60:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "BackupDate";
                break;
            case 61:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "BackupPath";
                break;
            case 62:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "StatementNote";
                break;
            case 63:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "PaymentTerms";
                break;
            case 64:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ConfirmNote";
                break;
            case 65:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "RegistrationNote";
                break;
            case 66:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ReceiptNote";
                break;
            case 67:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "CouponNote";
                break;
            case 68:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "TaxCreditNote";
                break;
            case 69:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "CostumeNote";
                break;
            case 70:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "RConfirmNote";
                break;
            case 71:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "TuitionSelect";
                break;
            case 72:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "BalFwd";
                break;
            case 73:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "HideAcct";
                break;
            case 74:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "BackColor";
                break;
            case 75:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "RegStu01";
                break;
            case 76:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "RegStu02";
                break;
            case 77:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "RegStu03";
                break;
            case 78:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "RegStu04";
                break;
            case 79:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "RegStu05";
                break;
            case 80:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "RegStu06";
                break;
            case 81:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ShipName";
                break;
            case 82:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ShipAddr";
                break;
            case 83:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ShipAddr2";
                break;
            case 84:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ShipCity";
                break;
            case 85:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ShipState";
                break;
            case 86:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ShipZip";
                break;
            case 87:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ShipPhone";
                break;
            case 88:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "CCProcessor";
                break;
            case 89:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "CCUserName";
                break;
            case 90:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "CCPassword";
                break;
            case 91:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "CCMerchantNo";
                break;
            case 92:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "CCBatchNo";
                break;
            case 93:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "CCMaxAmt";
                break;
            case 94:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "StmtFmt";
                break;
            case 95:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "StmtType";
                break;
            case 96:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "StmtSNames";
                break;
            case 97:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "YearUpdate";
                break;
            case 98:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Closeout";
                break;
            case 99:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Drawer";
                break;
            case 100:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "OLPWord";
                break;
            case 101:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Receipt80";
                break;
            case 102:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ReturnPolicy";
                break;
            case 103:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "CCAMEX";
                break;
            case 104:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "CCDiscover";
                break;
            case 105:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "CCMasterCard";
                break;
            case 106:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "CCVisa";
                break;
            case 107:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "DeclineMessage";
                break;
            case 108:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "MCActive";
                break;
            case 109:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "MCAPIString";
                break;
            case 110:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "MCListID";
                break;
            case 111:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "UseSchoolEmail";
                break;
            case 112:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "MercuryUsername";
                break;
            case 113:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "MercuryPassword";
                break;
            case 114:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "MercuryAVSCheck";
                break;
            case 115:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "MercuryCVVCheck";
                break;
            case 116:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "LogoName";
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
                SStatus = Integer.parseInt(value.toString());
                break;
            case 2:
                SRegNo = value.toString();
                break;
            case 3:
                SessionID = Integer.parseInt(value.toString());
                break;
            case 4:
                SName = value.toString();
                break;
            case 5:
                SAddress = value.toString();
                break;
            case 6:
                SAddress2 = value.toString();
                break;
            case 7:
                SCity = value.toString();
                break;
            case 8:
                SState = value.toString();
                break;
            case 9:
                SZip = value.toString();
                break;
            case 10:
                SPhone = value.toString();
                break;
            case 11:
                SFax = value.toString();
                break;
            case 12:
                SContact = value.toString();
                break;
            case 13:
                STaxID = value.toString();
                break;
            case 14:
                SWebsite = value.toString();
                break;
            case 15:
                SEMail = value.toString();
                break;
            case 16:
                EMailSrvr = value.toString();
                break;
            case 17:
                EMailAcct = value.toString();
                break;
            case 18:
                EMailPwrd = value.toString();
                break;
            case 19:
                EMailNotify = Integer.parseInt(value.toString());
                break;
            case 20:
                EMailPort = Integer.parseInt(value.toString());
                break;
            case 21:
                EMailSize = Integer.parseInt(value.toString());
                break;
            case 22:
                EMailSig = value.toString();
                break;
            case 23:
                EMailCon = value.toString();
                break;
            case 24:
                EMailRec = value.toString();
                break;
            case 25:
                EMailReg = value.toString();
                break;
            case 26:
                EMailStm = value.toString();
                break;
            case 27:
                MaxStudents = Integer.parseInt(value.toString());
                break;
            case 28:
                DefaultCity = value.toString();
                break;
            case 29:
                DefaultState = value.toString();
                break;
            case 30:
                DefaultZip = value.toString();
                break;
            case 31:
                DefaultAreaCode = value.toString();
                break;
            case 32:
                PhoneMask = value.toString();
                break;
            case 33:
                DiscType = Boolean.parseBoolean(value.toString());
                break;
            case 34:
                DiscAmount = Float.parseFloat(value.toString());
                break;
            case 35:
                DiscRound = Float.parseFloat(value.toString());
                break;
            case 36:
                LateChgType = Boolean.parseBoolean(value.toString());
                break;
            case 37:
                LateChgAmt = Float.parseFloat(value.toString());
                break;
            case 38:
                ClassTuition = Float.parseFloat(value.toString());
                break;
            case 39:
                ClassSizeMax = Integer.parseInt(value.toString());
                break;
            case 40:
                ClassLength = Integer.parseInt(value.toString());
                break;
            case 41:
                ClassConflictChk = Boolean.parseBoolean(value.toString());
                break;
            case 42:
                ClassSortOrder = Integer.parseInt(value.toString());
                break;
            case 43:
                StudentGradeUpdate = value.toString();
                break;
            case 44:
                SPassword = value.toString();
                break;
            case 45:
                OPassword = value.toString();
                break;
            case 46:
                CPassword = value.toString();
                break;
            case 47:
                AcctProg = Integer.parseInt(value.toString());
                break;
            case 48:
                AcctLinkPath = value.toString();
                break;
            case 49:
                BankAcct = value.toString();
                break;
            case 50:
                CustDepAcct = value.toString();
                break;
            case 51:
                RefundAcct = value.toString();
                break;
            case 52:
                ARAcct = value.toString();
                break;
            case 53:
                DiscountAcct = value.toString();
                break;
            case 54:
                ST1Acct = value.toString();
                break;
            case 55:
                ST1Desc = value.toString();
                break;
            case 56:
                ST1Rate = Float.parseFloat(value.toString());
                break;
            case 57:
                ST2Acct = value.toString();
                break;
            case 58:
                ST2Desc = value.toString();
                break;
            case 59:
                ST2Rate = Float.parseFloat(value.toString());
                break;
            case 60:
                BackupDate = value.toString();
                break;
            case 61:
                BackupPath = value.toString();
                break;
            case 62:
                StatementNote = value.toString();
                break;
            case 63:
                PaymentTerms = value.toString();
                break;
            case 64:
                ConfirmNote = value.toString();
                break;
            case 65:
                RegistrationNote = value.toString();
                break;
            case 66:
                ReceiptNote = value.toString();
                break;
            case 67:
                CouponNote = value.toString();
                break;
            case 68:
                TaxCreditNote = value.toString();
                break;
            case 69:
                CostumeNote = value.toString();
                break;
            case 70:
                RConfirmNote = value.toString();
                break;
            case 71:
                TuitionSelect = Integer.parseInt(value.toString());
                break;
            case 72:
                BalFwd = Boolean.parseBoolean(value.toString());
                break;
            case 73:
                HideAcct = Boolean.parseBoolean(value.toString());
                break;
            case 74:
                BackColor = Integer.parseInt(value.toString());
                break;
            case 75:
                RegStu01 = Float.parseFloat(value.toString());
                break;
            case 76:
                RegStu02 = Float.parseFloat(value.toString());
                break;
            case 77:
                RegStu03 = Float.parseFloat(value.toString());
                break;
            case 78:
                RegStu04 = Float.parseFloat(value.toString());
                break;
            case 79:
                RegStu05 = Float.parseFloat(value.toString());
                break;
            case 80:
                RegStu06 = Float.parseFloat(value.toString());
                break;
            case 81:
                ShipName = value.toString();
                break;
            case 82:
                ShipAddr = value.toString();
                break;
            case 83:
                ShipAddr2 = value.toString();
                break;
            case 84:
                ShipCity = value.toString();
                break;
            case 85:
                ShipState = value.toString();
                break;
            case 86:
                ShipZip = value.toString();
                break;
            case 87:
                ShipPhone = value.toString();
                break;
            case 88:
                CCProcessor = value.toString();
                break;
            case 89:
                CCUserName = value.toString();
                break;
            case 90:
                CCPassword = value.toString();
                break;
            case 91:
                CCMerchantNo = Integer.parseInt(value.toString());
                break;
            case 92:
                CCBatchNo = Integer.parseInt(value.toString());
                break;
            case 93:
                CCMaxAmt = Float.parseFloat(value.toString());
                break;
            case 94:
                StmtFmt = Integer.parseInt(value.toString());
                break;
            case 95:
                StmtType = Integer.parseInt(value.toString());
                break;
            case 96:
                StmtSNames = Boolean.parseBoolean(value.toString());
                break;
            case 97:
                YearUpdate = value.toString();
                break;
            case 98:
                Closeout = value.toString();
                break;
            case 99:
                Drawer = Float.parseFloat(value.toString());
                break;
            case 100:
                OLPWord = value.toString();
                break;
            case 101:
                Receipt80 = Boolean.parseBoolean(value.toString());
                break;
            case 102:
                ReturnPolicy = value.toString();
                break;
            case 103:
                CCAMEX = Boolean.parseBoolean(value.toString());
                break;
            case 104:
                CCDiscover = Boolean.parseBoolean(value.toString());
                break;
            case 105:
                CCMasterCard = Boolean.parseBoolean(value.toString());
                break;
            case 106:
                CCVisa = Boolean.parseBoolean(value.toString());
                break;
            case 107:
                DeclineMessage = value.toString();
                break;
            case 108:
                MCActive = Boolean.parseBoolean(value.toString());
                break;
            case 109:
                MCAPIString = value.toString();
                break;
            case 110:
                MCListID = value.toString();
                break;
            case 111:
                UseSchoolEmail = Boolean.parseBoolean(value.toString());
                break;
            case 112:
                MercuryUsername = value.toString();
                break;
            case 113:
                MercuryPassword = value.toString();
                break;
            case 114:
                MercuryAVSCheck = Integer.parseInt(value.toString());
                break;
            case 115:
                MercuryCVVCheck = Integer.parseInt(value.toString());
                break;
            case 116:
                if (value.equals("anyType{}")) {
                    LogoName = "";
                } else {
                    LogoName = value.toString();
                }
            default:
                break;
        }

    }

}
