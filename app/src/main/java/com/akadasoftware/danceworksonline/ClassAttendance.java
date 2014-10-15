package com.akadasoftware.danceworksonline;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import com.akadasoftware.danceworksonline.Classes.AppPreferences;
import com.akadasoftware.danceworksonline.Classes.Globals;
import com.akadasoftware.danceworksonline.Classes.SchoolClasses;
import com.akadasoftware.danceworksonline.Classes.StudentAttendance;
import com.akadasoftware.danceworksonline.Classes.User;
import com.squareup.timessquare.CalendarPickerView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by Kyle on 8/4/2014.
 */
public class ClassAttendance extends Fragment {

    Activity activity;
    Globals oGlobals;
    ArrayList<SchoolClasses> schoolClassList;
    ArrayList<StudentAttendance> classAttendanceArray = new ArrayList<StudentAttendance>();
    ArrayList<Date> dates;
    SchoolClasses oSchoolClass;
    User oUser;

    int position, intMonth, intCurrentYear;

    SimpleDateFormat dateFormat;

    private AppPreferences _appPrefs;

    Calendar thisMonth, nextMonth;

    SeekBar classSeekBar;
    CalendarPickerView classCalendarPicker;
    Button btnPreviousYear, btnNextYear;

    public static ClassAttendance newInstance(int position) {
        ClassAttendance fragment = new ClassAttendance();
        Bundle args = new Bundle();
        args.putInt("Position", position);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();


        oGlobals = new Globals();
        _appPrefs = new AppPreferences(activity);
        position = getArguments().getInt("Position");

        schoolClassList = _appPrefs.getSchoolClassList();
        oSchoolClass = schoolClassList.get(position);
        oUser = _appPrefs.getUser();
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    }

    //Create the view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_class_attendance, container, false);

        classSeekBar = (SeekBar) rootView.findViewById(R.id.classSeekBar);
        btnPreviousYear = (Button) rootView.findViewById(R.id.btnPreviousYear);
        btnNextYear = (Button) rootView.findViewById(R.id.btnNextYear);
        classCalendarPicker = (CalendarPickerView) rootView.findViewById(R.id.classCalendarPicker);

        nextMonth = Calendar.getInstance();
        nextMonth.set(Calendar.DAY_OF_MONTH, 1);
        nextMonth.add(Calendar.MONTH, 1);

        btnPreviousYear.setText(String.valueOf(nextMonth.get(Calendar.YEAR) - 1));
        btnNextYear.setText(String.valueOf(nextMonth.get(Calendar.YEAR) + 1));

        intCurrentYear = nextMonth.get(Calendar.YEAR);

        thisMonth = Calendar.getInstance();
        thisMonth.set(Calendar.DAY_OF_MONTH, 1);


        intMonth = thisMonth.get(Calendar.MONTH);
        classSeekBar.setProgress(intMonth);

        dates = new ArrayList<Date>();
        classCalendarPicker.init(thisMonth.getTime(), nextMonth.getTime()).inMode(CalendarPickerView.SelectionMode.MULTIPLE)
                .withSelectedDates(dates);

        classSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                intMonth = progress;
                thisMonth.set(Calendar.MONTH, intMonth);
                nextMonth.set(Calendar.MONTH, intMonth + 1);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                getCompleteClassAttendanceAsync getAttendance = new getCompleteClassAttendanceAsync();
                getAttendance.execute();

            }
        });


        classCalendarPicker.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                classCalendarPicker.selectDate(date);
                Toast toast = Toast.makeText(getActivity(), "Invalid date.", Toast.LENGTH_SHORT);
                toast.show();

            }

            @Override
            public void onDateUnselected(Date date) {

                Toast toast = Toast.makeText(getActivity(), "Date already selected.", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        btnPreviousYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intCurrentYear -= 1;
                btnPreviousYear.setText(String.valueOf(intCurrentYear - 1));
                btnNextYear.setText(String.valueOf(intCurrentYear + 1));

                thisMonth.set(Calendar.YEAR, intCurrentYear);
                nextMonth.set(Calendar.YEAR, intCurrentYear);

                thisMonth.set(Calendar.MONTH, 0);
                nextMonth.set(Calendar.MONTH, 1);
                classSeekBar.setProgress(0);

                getCompleteClassAttendanceAsync getAttendance = new getCompleteClassAttendanceAsync();
                getAttendance.execute();

            }
        });

        btnNextYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intCurrentYear += 1;
                btnNextYear.setText(String.valueOf(intCurrentYear + 1));
                btnPreviousYear.setText(String.valueOf(intCurrentYear - 1));

                thisMonth.set(Calendar.YEAR, intCurrentYear);
                nextMonth.set(Calendar.YEAR, intCurrentYear);

                thisMonth.set(Calendar.MONTH, 0);
                nextMonth.set(Calendar.MONTH, 1);
                classSeekBar.setProgress(0);

                getCompleteClassAttendanceAsync getAttendance = new getCompleteClassAttendanceAsync();
                getAttendance.execute();


            }
        });

        return rootView;

    }

    public void onResume() {
        super.onResume();

        getCompleteClassAttendanceAsync getAttendance = new getCompleteClassAttendanceAsync();
        getAttendance.execute();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * Get's the Student's attendance record
     */
    public class getCompleteClassAttendanceAsync extends
            AsyncTask<Globals.Data, Void, ArrayList<StudentAttendance>> {
        ProgressDialog progress;

        protected void onPreExecute() {
            progress = ProgressDialog.show(activity, "Getting attendance", "Loading...", true);
        }

        @Override
        protected ArrayList<StudentAttendance> doInBackground(Globals.Data... data) {

            return getCompleteClassAttendance();
        }

        protected void onPostExecute(ArrayList<StudentAttendance> result) {
            progress.dismiss();
            classAttendanceArray = result;

            updateDateArray(classAttendanceArray, dates);

        }
    }


    public ArrayList<StudentAttendance> getCompleteClassAttendance() {
        String MethodName = "getCombinedClassAttendance";
        SoapObject response = InvokeCompleteAttendanceMethod(Globals.Data.URL, MethodName);
        return RetrieveCompleteAttendanceFromSoap(response);

    }

    public SoapObject InvokeCompleteAttendanceMethod(String URL, String MethodName) {

        SoapObject request = GetSoapObject(MethodName);

        User oUser = _appPrefs.getUser();

        int selectedMonth = thisMonth.get(Calendar.MONTH);
        int selectedYear = thisMonth.get(Calendar.YEAR);


        PropertyInfo piUserID = new PropertyInfo();
        piUserID.setName("UserID");
        piUserID.setValue(oUser.UserID);
        request.addProperty(piUserID);

        PropertyInfo piUserGUID = new PropertyInfo();
        piUserGUID.setName("UserGUID");
        piUserGUID.setValue(oUser.UserGUID);
        request.addProperty(piUserGUID);

        PropertyInfo piMonth = new PropertyInfo();
        piMonth.setName("intMonth");
        piMonth.setValue(selectedMonth + 1);
        request.addProperty(piMonth);

        PropertyInfo piYear = new PropertyInfo();
        piYear.setName("intYear");
        piYear.setValue(selectedYear);
        request.addProperty(piYear);

        PropertyInfo piClID = new PropertyInfo();
        piClID.setName("intClID");
        piClID.setValue(oSchoolClass.ClID);
        request.addProperty(piClID);

        PropertyInfo piSchID = new PropertyInfo();
        piSchID.setName("intSchID");
        piSchID.setValue(oUser.SchID);
        request.addProperty(piSchID);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        return MakeAttendanceCall(URL, envelope, Globals.Data.NAMESPACE, MethodName);
    }

    public static SoapObject GetSoapObject(String MethodName) {
        return new SoapObject(Globals.Data.NAMESPACE, MethodName);
    }

    public static SoapObject MakeAttendanceCall(String URL,
                                                SoapSerializationEnvelope envelope, String NAMESPACE,
                                                String METHOD_NAME) {
        HttpTransportSE HttpTransport = new HttpTransportSE(URL);
        SoapObject response = null;
        try {
            envelope.addMapping(NAMESPACE, "StudentAttendance",
                    new StudentAttendance().getClass());
            HttpTransport.call(METHOD_NAME, envelope);
            response = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();

        }
        return response;
    }

    public static ArrayList<StudentAttendance> RetrieveCompleteAttendanceFromSoap(SoapObject soap) {

        ArrayList<StudentAttendance> stuAttendance = new ArrayList<StudentAttendance>();
        for (int i = 0; i < soap.getPropertyCount(); i++) {

            SoapObject attendanceItem = (SoapObject) soap.getProperty(i);

            StudentAttendance attendance = new StudentAttendance();
            for (int j = 0; j < attendanceItem.getPropertyCount(); j++) {
                attendance.setProperty(j, attendanceItem.getProperty(j)
                        .toString());
                if (attendanceItem.getProperty(j).equals("anyType{}")) {
                    attendanceItem.setProperty(j, "");
                }

            }
            stuAttendance.add(i, attendance);
        }

        return stuAttendance;
    }

    public void drawCalendar(CalendarPickerView calendar, Calendar currentMonth, Calendar nextMonth) {
        calendar.init(currentMonth.getTime(), nextMonth.getTime()).inMode(CalendarPickerView.SelectionMode.MULTIPLE);
    }

    public void drawCalendar(CalendarPickerView calendar, Calendar currentMonth, Calendar nextMonth,
                             ArrayList<Date> datesSelected) {
        calendar.init(currentMonth.getTime(), nextMonth.getTime()).inMode(CalendarPickerView.SelectionMode.MULTIPLE)
                .withSelectedDates(datesSelected);
    }

    public void updateDateArray(ArrayList<StudentAttendance> attendanceArray,
                                ArrayList<Date> datesAttended) {


        datesAttended.clear();
        for (int i = 0; i < attendanceArray.size(); i++) {

            try {
                if (! datesAttended.contains(dateFormat.parse(attendanceArray.get(i).ADate)))
                    datesAttended.add(dateFormat.parse(attendanceArray.get(i).ADate));

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        if (datesAttended.size() > 0)
            drawCalendar(classCalendarPicker, thisMonth, nextMonth, datesAttended);
        else
            drawCalendar(classCalendarPicker, thisMonth, nextMonth);
    }

}
