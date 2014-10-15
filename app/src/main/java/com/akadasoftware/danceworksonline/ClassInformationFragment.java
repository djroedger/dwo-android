package com.akadasoftware.danceworksonline;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.akadasoftware.danceworksonline.Classes.AppPreferences;
import com.akadasoftware.danceworksonline.Classes.Globals;
import com.akadasoftware.danceworksonline.Classes.SchoolClasses;
import com.akadasoftware.danceworksonline.Classes.User;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
 * Created by ksudu94 on 7/1/2014.
 */
public class ClassInformationFragment extends Fragment {

    Activity activity;
    Globals oGlobals;

    int position, intStudentsEnrolled, intMaxStudents;

    Float flTuition;

    String strSessionName, newStartTime, newEndTime, strClassType, strClassLevel, strDescription,
            strWait, strRoom, strInstructor, strDay;

    ArrayList<SchoolClasses> schoolClassList;
    SchoolClasses oSchoolClass;
    User oUser;

    ViewFlipper classSwitcher;

    View rootView;

    TextView tvWhichSession, tvClassType1, tvClassLevel1, tvClassDescription1, tvNumberEnrolled1, tvMaxNumber1,
            tvNumberWaitlisted1, tvStartTime1, tvStartTimeClick, tvEndTime1, tvEndTimeClick, tvRoomNumber1, tvTuitionAmount1, tvDaysOfTheWeek1,
            tvInstructor1;

    EditText etClassType, etClassLevel, etClassDescription, etStudentsEnrolled, etMaxStudents, etWaitList,
            etRoomNumber, etTuition, etDayNumber, etInstructor;

    Button btnEditClass, btnStudentsList, btnSaveClass, btnCancelClass;

    TimePicker tpTimePicker;

    Boolean isAm;

    private onEditStartTimeDialog startListener;
    private onEditEndTimeDialog endListener;

    private AppPreferences _appPrefs;

    public interface onEditStartTimeDialog {
        public void onEditStartTimeDialog(String startTime);
    }

    public interface onEditEndTimeDialog {
        public void onEditEndTimeDialog(String endTime);
    }


    public static ClassInformationFragment newInstance(int position, String inputSessionName) {
        ClassInformationFragment fragment = new ClassInformationFragment();
        Bundle args = new Bundle();
        //Student list position
        args.putInt("Position", position);
        args.putString("SessionName", inputSessionName);

        fragment.setArguments(args);

        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        strSessionName = activity.getIntent().getStringExtra("SessionName");

        oGlobals = new Globals();
        _appPrefs = new AppPreferences(activity);

        oUser = _appPrefs.getUser();
        position = getArguments().getInt("Position");
        if (_appPrefs.getAccessAllClasses())
            schoolClassList = _appPrefs.getSchoolClassList();
        else
            schoolClassList = _appPrefs.getMySchoolClassList();

        oSchoolClass = schoolClassList.get(position);
        isAm = false;


    }

    @Override
    public void onResume() {
        super.onResume();


    }

    public ClassInformationFragment() {
    }

    //Create the view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_class_information, container, false);

        tvWhichSession = (TextView) rootView.findViewById(R.id.tvWhichSession);
        tvWhichSession.setText("This class is in session: " + strSessionName);

        tvStartTimeClick = (TextView) rootView.findViewById(R.id.tvStartTimeClick);
        tvEndTimeClick = (TextView) rootView.findViewById(R.id.tvEndTimeClick);


        tvStartTimeClick.setText(oSchoolClass.ClStart);
        tvStartTimeClick.setTextSize(18);
        tvEndTimeClick.setText(oSchoolClass.ClStop);
        tvEndTimeClick.setTextSize(18);

        classSwitcher = (ViewFlipper) rootView.findViewById(R.id.classSwitcher);

        btnEditClass = (Button) rootView.findViewById(R.id.btnEditClass);
        btnStudentsList = (Button) rootView.findViewById(R.id.btnStudentsList);
        btnSaveClass = (Button) rootView.findViewById(R.id.btnSaveClass);
        btnCancelClass = (Button) rootView.findViewById(R.id.btnCancelClass);

        tpTimePicker = new TimePicker(getActivity());

        fillTextView(rootView, oSchoolClass);


        btnEditClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classSwitcher.setDisplayedChild(1);
                fillEditText(rootView, oSchoolClass);
            }
        });

        btnStudentsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openEnrollPage = new Intent("com.akadasoftware.danceworksonline.ClassListOfStudents");
                startActivity(openEnrollPage);
            }
        });


        btnCancelClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classSwitcher.setDisplayedChild(0);
            }
        });

        btnSaveClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strClassType = etClassType.getText().toString();
                strClassLevel = etClassLevel.getText().toString();
                strDescription = etClassDescription.getText().toString();
                intStudentsEnrolled = Integer.parseInt(etStudentsEnrolled.getText().toString());
                intMaxStudents = Integer.parseInt(etMaxStudents.getText().toString());
                strWait = etWaitList.getText().toString();
                strRoom = etRoomNumber.getText().toString();
                flTuition = Float.parseFloat(etTuition.getText().toString());
                strDay = etDayNumber.getText().toString();
                strInstructor = etInstructor.getText().toString();


                newStartTime = oGlobals.BuildTimeString(newStartTime, oSchoolClass, true);
                newEndTime = oGlobals.BuildTimeString(newEndTime, oSchoolClass, false);

                System.out.print(newStartTime);
                System.out.print(newEndTime);

                if (!areEmpty()) {

                    saveClassChangesAsync saveChanges = new saveClassChangesAsync();
                    saveChanges.execute();
                    classSwitcher.setDisplayedChild(0);
                } else {
                    Toast toast = Toast.makeText(getActivity(), "Please fill every field.", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        tvStartTimeClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startListener.onEditStartTimeDialog(oSchoolClass.ClStart);
                Toast toast = Toast.makeText(getActivity(), newStartTime,
                        Toast.LENGTH_LONG);
                toast.show();

            }
        });

        tvEndTimeClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endListener.onEditEndTimeDialog(oSchoolClass.ClStop);
                Toast toast = Toast.makeText(getActivity(), newEndTime,
                        Toast.LENGTH_LONG);
                toast.show();
            }
        });


        return rootView;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            startListener = (onEditStartTimeDialog) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement onEditStartTimeDialog");
        }
        try {
            endListener = (onEditEndTimeDialog) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement onEditEndTimeDialog");
        }
    }


    /**
     * Prefils the edit class screen with info
     *
     * @param rootView
     * @param oSchoolClass
     */
    public void fillEditText(View rootView, SchoolClasses oSchoolClass) {
        etClassType = (EditText) rootView.findViewById(R.id.etClassType);
        etClassLevel = (EditText) rootView.findViewById(R.id.etClassLevel);
        etClassDescription = (EditText) rootView.findViewById(R.id.etClassDescription);
        etStudentsEnrolled = (EditText) rootView.findViewById(R.id.etStudentsEnrolled);
        etMaxStudents = (EditText) rootView.findViewById(R.id.etMaxStudents);
        etWaitList = (EditText) rootView.findViewById(R.id.etWaitList);
        etRoomNumber = (EditText) rootView.findViewById(R.id.etRoomNumber);
        etTuition = (EditText) rootView.findViewById(R.id.etTuition);
        etDayNumber = (EditText) rootView.findViewById(R.id.etDayNumber);
        etInstructor = (EditText) rootView.findViewById(R.id.etInstructor);


        etClassType.setText(oSchoolClass.ClType);
        etClassLevel.setText(oSchoolClass.ClLevel);
        etClassDescription.setText(oSchoolClass.ClDescription);
        etStudentsEnrolled.setText(String.valueOf(oSchoolClass.ClCur));
        etWaitList.setText(oSchoolClass.ClWait);
        etMaxStudents.setText(String.valueOf(oSchoolClass.ClMax));
        etRoomNumber.setText(oSchoolClass.ClRoom);
        etTuition.setText(String.valueOf(oSchoolClass.ClTuition));
        etDayNumber.setText(oSchoolClass.ClDay);
        etInstructor.setText(oSchoolClass.ClInstructor);


    }

    /**
     * Fills class informatoin screen with appropriate info
     *
     * @param rootView
     * @param oSchoolClass
     */
    public void fillTextView(View rootView, SchoolClasses oSchoolClass) {
        tvClassType1 = (TextView) rootView.findViewById(R.id.tvClassType1);
        tvClassLevel1 = (TextView) rootView.findViewById(R.id.tvClassLevel1);
        tvClassDescription1 = (TextView) rootView.findViewById(R.id.tvClassDescription1);
        tvNumberEnrolled1 = (TextView) rootView.findViewById(R.id.tvNumberEnrolled1);
        tvMaxNumber1 = (TextView) rootView.findViewById(R.id.tvMaxNumber1);
        tvNumberWaitlisted1 = (TextView) rootView.findViewById(R.id.tvNumberWaitlisted1);
        tvStartTime1 = (TextView) rootView.findViewById(R.id.tvStartTime1);
        tvEndTime1 = (TextView) rootView.findViewById(R.id.tvEndTime1);
        tvRoomNumber1 = (TextView) rootView.findViewById(R.id.tvRoomNumber1);
        tvTuitionAmount1 = (TextView) rootView.findViewById(R.id.tvTuitionAmount1);
        tvDaysOfTheWeek1 = (TextView) rootView.findViewById(R.id.tvDaysOfTheWeek1);
        tvInstructor1 = (TextView) rootView.findViewById(R.id.tvInstructor1);

        tvClassType1.setText(oSchoolClass.ClType);
        tvClassLevel1.setText(oSchoolClass.ClLevel);
        tvClassDescription1.setText(oSchoolClass.ClDescription);
        tvNumberEnrolled1.setText(String.valueOf(oSchoolClass.ClCur));
        tvNumberWaitlisted1.setText(oSchoolClass.ClWait);
        tvMaxNumber1.setText(String.valueOf(oSchoolClass.ClMax));
        tvStartTime1.setText(oSchoolClass.ClStart);
        tvEndTime1.setText(oSchoolClass.ClStop);
        tvRoomNumber1.setText(oSchoolClass.ClRoom);
        tvTuitionAmount1.setText(String.valueOf(oSchoolClass.ClTuition));
        tvDaysOfTheWeek1.setText(oSchoolClass.ClDay);
        tvInstructor1.setText(oSchoolClass.ClInstructor);
    }


    public class saveClassChangesAsync extends AsyncTask<Globals.Data, Void, String> {
        @Override
        protected String doInBackground(Globals.Data... data) {

            return saveClassChanges();
        }

        protected void onPostExecute(String result) {

            if (result.length() == 0) {
                Toast toast = Toast.makeText(getActivity(), "Changes failed to save.", Toast.LENGTH_LONG);
                toast.show();
            } else {
                Toast toast = Toast.makeText(getActivity(), "Changes successfully saved", Toast.LENGTH_LONG);
                toast.show();
                oSchoolClass.ClType = etClassType.getText().toString().trim();
                oSchoolClass.ClLevel = etClassLevel.getText().toString().trim();
                oSchoolClass.ClDescription = etClassDescription.getText().toString().trim();
                oSchoolClass.ClCur = Integer.parseInt(etStudentsEnrolled.getText().toString().trim());
                oSchoolClass.ClMax = Integer.parseInt(etMaxStudents.getText().toString().trim());
                oSchoolClass.ClWait = etWaitList.getText().toString().trim();

                oSchoolClass.ClStart = newStartTime;
                oSchoolClass.ClStop = newEndTime;
                oSchoolClass.ClRoom = etRoomNumber.getText().toString().trim();
                oSchoolClass.ClTuition = Float.parseFloat(etTuition.getText().toString().trim());
                oSchoolClass.ClInstructor = etInstructor.getText().toString().trim();
                oSchoolClass.ClDay = etDayNumber.getText().toString().trim();
                oGlobals.updateClass(oSchoolClass, position, activity);
                fillTextView(rootView, oSchoolClass);

            }
        }
    }

    public String saveClassChanges() {
        String MethodName = "saveClassInformation";
        SoapPrimitive response = InvokeSaveClasstMethod(Globals.Data.URL, MethodName);
        return RetrieveSaveClassFromSoap(response);

    }


    public SoapPrimitive InvokeSaveClasstMethod(String URL, String METHOD_NAME) {

        SoapObject request = new SoapObject(Globals.Data.NAMESPACE, METHOD_NAME);


        PropertyInfo piUserID = new PropertyInfo();
        piUserID.setName("UserID");
        piUserID.setValue(oUser.UserID);
        request.addProperty(piUserID);

        PropertyInfo piUserGUID = new PropertyInfo();
        piUserGUID.setName("UserGUID");
        piUserGUID.setValue(oUser.UserGUID);
        request.addProperty(piUserGUID);

        PropertyInfo piClassType = new PropertyInfo();
        piClassType.setName("strClassType");
        piClassType.setValue(strClassType);
        request.addProperty(piClassType);

        PropertyInfo piClassLevel = new PropertyInfo();
        piClassLevel.setName("strClassLevel");
        piClassLevel.setValue(strClassLevel);
        request.addProperty(piClassLevel);

        PropertyInfo piDescription = new PropertyInfo();
        piDescription.setName("strDescription");
        piDescription.setValue(strDescription);
        request.addProperty(piDescription);

        PropertyInfo piStudentEnrolled = new PropertyInfo();
        piStudentEnrolled.setName("intStudentsEnrolled");
        piStudentEnrolled.setValue(intStudentsEnrolled);
        request.addProperty(piStudentEnrolled);

        PropertyInfo piMaxStudents = new PropertyInfo();
        piMaxStudents.setName("intMaxStudents");
        piMaxStudents.setValue(intMaxStudents);
        request.addProperty(piMaxStudents);

        PropertyInfo piWait = new PropertyInfo();
        piWait.setName("strWait");
        piWait.setValue(strWait);
        request.addProperty(piWait);

        PropertyInfo piClStart = new PropertyInfo();
        piClStart.setName("strClStart");
        piClStart.setValue(newStartTime);
        request.addProperty(piClStart);

        PropertyInfo piClStop = new PropertyInfo();
        piClStop.setName("strClStop");
        piClStop.setValue(newEndTime);
        request.addProperty(piClStop);

        PropertyInfo piRoom = new PropertyInfo();
        piRoom.setName("strRoom");
        piRoom.setValue(strRoom);
        request.addProperty(piRoom);

        PropertyInfo piTuition = new PropertyInfo();
        piTuition.setType(Float.class);
        piTuition.setName("flTuition");
        piTuition.setValue(flTuition);
        request.addProperty(piTuition);

        PropertyInfo piInstructor = new PropertyInfo();
        piInstructor.setName("strInstructor");
        piInstructor.setValue(strInstructor);
        request.addProperty(piInstructor);

        PropertyInfo piDay = new PropertyInfo();
        piDay.setName("strDay");
        piDay.setValue(strDay);
        request.addProperty(piDay);

        PropertyInfo piClID = new PropertyInfo();
        piClID.setName("intClID");
        piClID.setValue(oSchoolClass.ClID);
        request.addProperty(piClID);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        MarshalFloat mf = new MarshalFloat();
        mf.register(envelope);

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
            HttpTransport.call(METHOD_NAME, envelope);
            response = (SoapPrimitive) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public static String RetrieveSaveClassFromSoap(SoapPrimitive soap) {
        String response = soap.toString();

        return response;

    }

    private Boolean areEmpty() {
        if (etClassLevel.getText().toString().trim().equals("") || etClassLevel.getText().toString().trim().equals("") ||
                etClassDescription.getText().toString().trim().equals("") || etStudentsEnrolled.getText().toString().trim().equals("") ||
                etMaxStudents.getText().toString().trim().equals("") || etWaitList.getText().toString().trim().equals("") ||
                etRoomNumber.getText().toString().trim().equals("") || etTuition.getText().toString().trim().equals("") ||
                etDayNumber.getText().toString().trim().equals("") || etInstructor.getText().toString().trim().equals("")) {

            return true;
        } else
            return false;
    }
}

