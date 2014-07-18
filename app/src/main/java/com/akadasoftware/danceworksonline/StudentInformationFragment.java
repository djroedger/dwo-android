package com.akadasoftware.danceworksonline;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.akadasoftware.danceworksonline.Classes.AppPreferences;
import com.akadasoftware.danceworksonline.Classes.Globals;
import com.akadasoftware.danceworksonline.Classes.Student;
import com.akadasoftware.danceworksonline.Classes.User;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyle on 4/9/2014.
 */
public class StudentInformationFragment extends Fragment {


    private AppPreferences _appPrefs;
    Activity activity;
    User oUser;
    Student oStudent;
    Globals oGlobals;
    ArrayList<Student> Students;

    int position, intStatus;

    String strFName, strLName, strAddress, strCity, strState, strZip, strContact, strAcctName, strStatus;


    TextView tvName1, tvAddress1, tvContact1, tvStatus1, tvAccountName1;

    EditText etFName, etLName, etAddress, etCity, etState, etZip, etContact, etAccountName;

    Button btnEditStudent, btnEnrollStudent, btnSave, btnCancel;

    ViewFlipper studentSwitcher;

    Spinner StudentStatusSpinner;

    View rootView;

    public static StudentInformationFragment newInstance(int position) {
        StudentInformationFragment fragment = new StudentInformationFragment();
        Bundle args = new Bundle();
        //Student list position
        args.putInt("Position", position);
        fragment.setArguments(args);

        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = getActivity();
        oGlobals = new Globals();
        _appPrefs = new AppPreferences(activity);
        Students = _appPrefs.getStudents();
        position = getArguments().getInt("Position");

        oStudent = Students.get(position);
        oUser = _appPrefs.getUser();
        _appPrefs.saveStuID(oStudent.StuID);

    }

    public StudentInformationFragment() {
    }

    //Create the view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_student_information, container, false);

        etFName = (EditText) rootView.findViewById(R.id.etFName);
        etLName = (EditText) rootView.findViewById(R.id.etLName);
        etAddress = (EditText) rootView.findViewById(R.id.etAddress);
        etCity = (EditText) rootView.findViewById(R.id.etCity);
        etState = (EditText) rootView.findViewById(R.id.etState);
        etZip = (EditText) rootView.findViewById(R.id.etZip);
        etContact = (EditText) rootView.findViewById(R.id.etContact);
        etAccountName = (EditText) rootView.findViewById(R.id.etAccountName);


        btnEditStudent = (Button) rootView.findViewById(R.id.btnEditStudent);
        btnEnrollStudent = (Button) rootView.findViewById(R.id.btnEnrollStudent);
        btnSave = (Button) rootView.findViewById(R.id.btnSave);
        btnCancel = (Button) rootView.findViewById(R.id.btnCancel);

        studentSwitcher = (ViewFlipper) rootView.findViewById(R.id.studentSwitcher);


        setStudentFields(oStudent, rootView);


        btnEditStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    studentSwitcher.setDisplayedChild(1);
                    etFName.setText(oStudent.FName);
                    etLName.setText(oStudent.LName);
                    etAddress.setText(oStudent.Address);
                    etCity.setText(oStudent.City);
                    etState.setText(oStudent.State);
                    etZip.setText(oStudent.ZipCode);
                    etContact.setText(oStudent.Phone);
                    etAccountName.setText(oStudent.AcctName);

                    List<String> spinnerlist = new ArrayList<String>();
                    spinnerlist.add("active");
                    spinnerlist.add("inactive");
                    spinnerlist.add("deleted");
                    if (strStatus.equals("prospect"))
                        spinnerlist.add("prospect");

                    StudentStatusSpinner = (Spinner) activity.findViewById(R.id.StudentStatusSpinner);

                    ArrayAdapter<String> spinneradapter = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_list_item_1, spinnerlist);


                    spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    StudentStatusSpinner.setAdapter(spinneradapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnEnrollStudent.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent openEnrollPage = new Intent("com.akadasoftware.danceworksonline.Enroll");
                startActivity(openEnrollPage);


            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    studentSwitcher.setDisplayedChild(0);
                    Toast toast = Toast.makeText(getActivity(), "Changes Canceled", Toast.LENGTH_LONG);
                    toast.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strFName = etFName.getText().toString();
                strLName = etLName.getText().toString();
                strAddress = etAddress.getText().toString();
                strCity = etCity.getText().toString();
                strState = etState.getText().toString();
                strZip = etZip.getText().toString();
                strContact = etContact.getText().toString();
                strAcctName = etAccountName.getText().toString();

                switch (StudentStatusSpinner.getSelectedItemPosition()) {
                    case 0:
                        //Active
                        intStatus = 0;
                        break;
                    case 1:
                        //Inactive
                        intStatus = 1;
                        break;
                    case 2:
                        //Prospect
                        intStatus = 2;
                        break;
                }

                if (!areEmpty()) {

                    saveStudentChangesAsync saveChanges = new saveStudentChangesAsync();
                    saveChanges.execute();
                    studentSwitcher.setDisplayedChild(0);
                } else {
                    Toast toast = Toast.makeText(getActivity(), "Please fill every field.", Toast.LENGTH_LONG);
                    toast.show();
                }


            }
        });
        return rootView;

    }


    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                getFragmentManager().popBackStackImmediate();
                break;
            default:
                break;
        }
        return true;
    }

    public void setStudentFields(Student objStudent, View rootView) {

        switch (objStudent.Status) {
            case 0:
                strStatus = "active";
                break;
            case 1:
                strStatus = "inactive";
                break;
            case 2:
                strStatus = "prospect";
                break;
            case 3:
                strStatus = "deleted";
                break;
            default:
                strStatus = "I have no freaking clue";
                break;
        }
        tvName1 = (TextView) rootView.findViewById(R.id.tvName1);
        tvAddress1 = (TextView) rootView.findViewById(R.id.tvAddress1);
        tvContact1 = (TextView) rootView.findViewById(R.id.tvContact1);
        tvStatus1 = (TextView) rootView.findViewById(R.id.tvStatus1);
        tvAccountName1 = (TextView) rootView.findViewById(R.id.tvAccountName1);

        tvName1.setText(objStudent.FName + " " + objStudent.LName);
        tvAddress1.setText(objStudent.Address + ", " + objStudent.City + ", " + objStudent.ZipCode);
        tvContact1.setText(objStudent.Phone);
        tvStatus1.setText(strStatus);
        tvAccountName1.setText(objStudent.AcctName);

    }

    class Data {

        static final String NAMESPACE = "http://app.akadasoftware.com/MobileAppWebService/";
        private static final String URL = "http://app.akadasoftware.com/MobileAppWebService/Android.asmx";
    }


    public class saveStudentChangesAsync extends AsyncTask<Globals.Data, Void, String> {
        @Override
        protected String doInBackground(Globals.Data... data) {

            return saveStudentChanges();
        }

        protected void onPostExecute(String result) {

            if (result.length() == 0) {
                Toast toast = Toast.makeText(getActivity(), "Changes failed to save.", Toast.LENGTH_LONG);
                toast.show();
            } else {
                Toast toast = Toast.makeText(getActivity(), "Changes successfully saved", Toast.LENGTH_LONG);
                toast.show();
                oStudent.FName = etFName.getText().toString().trim();
                oStudent.LName = etLName.getText().toString().trim();
                oStudent.Address = etAddress.getText().toString().trim();
                oStudent.City = etCity.getText().toString().trim();
                oStudent.State = etState.getText().toString().trim();
                oStudent.ZipCode = etZip.getText().toString().trim();
                oStudent.Phone = etContact.getText().toString().trim();
                oStudent.AcctName = etAccountName.getText().toString().trim();
                oStudent.Status = intStatus;
                oGlobals.updateStudent(oStudent, position, activity);
                setStudentFields(oStudent, rootView);
            }
        }
    }

    public String saveStudentChanges() {
        String MethodName = "saveStudentInformation";
        SoapPrimitive response = InvokeSaveStudenttMethod(Globals.Data.URL, MethodName);
        return RetrieveSaveStudentFromSoap(response);

    }


    public SoapPrimitive InvokeSaveStudenttMethod(String URL, String METHOD_NAME) {

        SoapObject request = new SoapObject(Globals.Data.NAMESPACE, METHOD_NAME);

        PropertyInfo piUserID = new PropertyInfo();
        piUserID.setName("UserID");
        piUserID.setValue(oUser.UserID);
        request.addProperty(piUserID);

        PropertyInfo piUserGUID = new PropertyInfo();
        piUserGUID.setType("STRING_CLASS");
        piUserGUID.setName("UserGUID");
        piUserGUID.setValue(oUser.UserGUID);
        request.addProperty(piUserGUID);

        PropertyInfo piStuID = new PropertyInfo();
        piStuID.setName("StuID");
        piStuID.setValue(oStudent.StuID);
        request.addProperty(piStuID);

        PropertyInfo piFName = new PropertyInfo();
        piFName.setName("FName");
        piFName.setValue(strFName);
        request.addProperty(piFName);

        PropertyInfo piLName = new PropertyInfo();
        piLName.setName("LName");
        piLName.setValue(strLName);
        request.addProperty(piLName);

        PropertyInfo piAddress = new PropertyInfo();
        piAddress.setName("Address");
        piAddress.setValue(strAddress);
        request.addProperty(piAddress);

        PropertyInfo piCity = new PropertyInfo();
        piCity.setName("City");
        piCity.setValue(strCity);
        request.addProperty(piCity);

        PropertyInfo piState = new PropertyInfo();
        piState.setName("State");
        piState.setValue(strState);
        request.addProperty(piState);

        PropertyInfo piZipCode = new PropertyInfo();
        piZipCode.setName("ZipCode");
        piZipCode.setValue(strZip);
        request.addProperty(piZipCode);

        PropertyInfo piPhone = new PropertyInfo();
        piPhone.setName("Phone");
        piPhone.setValue(strContact);
        request.addProperty(piPhone);

        PropertyInfo piAcctName = new PropertyInfo();
        piAcctName.setName("AcctName");
        piAcctName.setValue(strAcctName);
        request.addProperty(piAcctName);

        PropertyInfo piStatus = new PropertyInfo();
        piStatus.setName("Status");
        piStatus.setValue(intStatus);
        request.addProperty(piStatus);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        return MakeSaveCall(URL, envelope, Globals.Data.NAMESPACE, METHOD_NAME);
    }

    public static SoapPrimitive MakeSaveCall(String URL,
                                             SoapSerializationEnvelope envelope, String NAMESPACE,
                                             String METHOD_NAME) {
        HttpTransportSE HttpTransport = new HttpTransportSE(URL);
        SoapPrimitive response = null;
        try {
            //envelope.addMapping(Globals.Data.NAMESPACE, "SchoolClasses",new SchoolClasses().getClass());
            HttpTransport.call(METHOD_NAME, envelope);
            //envelopeOutput = envelope;
            response = (SoapPrimitive) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public static String RetrieveSaveStudentFromSoap(SoapPrimitive soap) {
        String response = soap.toString();

        return response;

    }


    /**
     * Checks if all of the textviews are empty, if true runs async method to edit student information
     *
     * @return
     */
    private Boolean areEmpty() {
        if (etFName.getText().toString().trim().equals("") || etLName.getText().toString().trim().equals("") ||
                etAddress.getText().toString().trim().equals("") || etCity.getText().toString().trim().equals("") ||
                etState.getText().toString().trim().equals("") || etZip.getText().toString().trim().equals("") ||
                etContact.getText().toString().trim().equals("")) {

            return true;
        } else
            return false;
    }
}
