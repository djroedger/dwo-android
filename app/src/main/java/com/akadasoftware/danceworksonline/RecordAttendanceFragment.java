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
import com.akadasoftware.danceworksonline.Classes.Student;
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
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecordAttendanceFragment.OnRecordAttendanceInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecordAttendanceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecordAttendanceFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    Student oStudent;
    Globals oGlobal;
    Activity activity;
    private AppPreferences _appPrefs;

    ArrayList<StudentAttendance> studentAttenanceArray = new ArrayList<StudentAttendance>();

    ArrayList<Student> students = new ArrayList<Student>();

    StudentAttendance oClassChoosen;


    int intMonth, intCurrentYear, position, SessionID;

    SeekBar seekBar;

    Button btnPreviousYear, btnNextYear;

    Calendar thisMonth, nextMonth;

    CalendarPickerView calendarPicker;
    ArrayList<Date> dates;

    SimpleDateFormat dateFormat;

    private OnRecordAttendanceInteractionListener mListener;
    private OnAttendanceDialogInteractionListener aListener;


    public static RecordAttendanceFragment newInstance(int position) {
        RecordAttendanceFragment fragment = new RecordAttendanceFragment();
        Bundle args = new Bundle();
        args.putInt("Position", position);

        fragment.setArguments(args);
        return fragment;
    }

    public RecordAttendanceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnRecordAttendanceInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement onRecordFragmentInteraction");
        }
        try {
            aListener = (OnAttendanceDialogInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnAttendanceDialogInteractionListener");
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        _appPrefs = new AppPreferences(activity);


        students = _appPrefs.getStudents();
        position = getArguments().getInt("Position");

        oStudent = students.get(position);
        oGlobal = new Globals();
        dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss ");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_record_attendance, container, false);

        seekBar = (SeekBar) rootView.findViewById(R.id.seekBar);
        btnPreviousYear = (Button) rootView.findViewById(R.id.btnPreviousYear);
        btnNextYear = (Button) rootView.findViewById(R.id.btnNextYear);
        calendarPicker = (CalendarPickerView) rootView.findViewById(R.id.calendar_view);


        nextMonth = Calendar.getInstance();
        nextMonth.set(Calendar.DAY_OF_MONTH, 1);
        nextMonth.add(Calendar.MONTH, 1);

        btnPreviousYear.setText(String.valueOf(nextMonth.get(Calendar.YEAR) - 1));
        btnNextYear.setText(String.valueOf(nextMonth.get(Calendar.YEAR) + 1));

        intCurrentYear = nextMonth.get(Calendar.YEAR);

        thisMonth = Calendar.getInstance();
        thisMonth.set(Calendar.DAY_OF_MONTH, 1);


        intMonth = thisMonth.get(Calendar.MONTH);
        seekBar.setProgress(intMonth);

        dates = new ArrayList<Date>();
        dates.add(thisMonth.getTime());
        dates.add(Calendar.getInstance().getTime());



        calendarPicker.init(thisMonth.getTime(), nextMonth.getTime()).inMode(CalendarPickerView.SelectionMode.MULTIPLE)
                .withSelectedDates(dates);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                intMonth = progress;

                thisMonth.set(Calendar.MONTH, intMonth);
                nextMonth.set(Calendar.MONTH, intMonth + 1);


                drawCalandar(calendarPicker, thisMonth, nextMonth);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                getCompleteStudentAttendanceAsync getAttendance = new getCompleteStudentAttendanceAsync();
                getAttendance.execute();

            }
        });


        calendarPicker.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                calendarPicker.selectDate(date);
                Toast toast = Toast.makeText(getActivity(), "Invalid date.", Toast.LENGTH_SHORT);
                toast.show();

            }

            @Override
            public void onDateUnselected(Date date) {
                calendarPicker.selectDate(date);
                for (int i = 0; i < studentAttenanceArray.size(); i++) {
                    try {
                        if (dateFormat.parse(studentAttenanceArray.get(i).ADate).getTime() == date.getTime())
                            oClassChoosen = studentAttenanceArray.get(i);
                        Toast toast = Toast.makeText(getActivity(), "Yay", Toast.LENGTH_SHORT);
                        toast.show();
                    } catch (ParseException e) {

                    }

                }
                aListener.onAttendanceDialogInteraction(oClassChoosen);
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

                drawCalandar(calendarPicker, thisMonth, nextMonth);
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

                drawCalandar(calendarPicker, thisMonth, nextMonth);
            }
        });


        return rootView;
    }


    public void onResume() {
        super.onResume();
        getCompleteStudentAttendanceAsync getAttendance = new getCompleteStudentAttendanceAsync();
        getAttendance.execute();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        aListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnRecordAttendanceInteractionListener {
        // TODO: Update argument type and name
        public void onRecordFragmentInteraction();
    }


    public interface OnAttendanceDialogInteractionListener {
        public void onAttendanceDialogInteraction(StudentAttendance oStudentAttendance);

    }


    /**
     * Get's the Student's attendance record after the the session is choosen.
     */
    public class getCompleteStudentAttendanceAsync extends
            AsyncTask<Globals.Data, Void, ArrayList<StudentAttendance>> {
        ProgressDialog progress;

        protected void onPreExecute() {
            progress = ProgressDialog.show(activity, "Getting attendance", "Loading...", true);
        }

        @Override
        protected ArrayList<StudentAttendance> doInBackground(Globals.Data... data) {

            //return getAttendance();
            return getCompleteAttendance();
        }

        protected void onPostExecute(ArrayList<StudentAttendance> result) {
            progress.dismiss();
            studentAttenanceArray = result;

            updateDateArray(studentAttenanceArray, dates);

        }
    }

    /**
     * Get Student Attendance
     */

    public ArrayList<StudentAttendance> getCompleteAttendance() {
        String MethodName = "getCombinedStudentAttendance";
        SoapObject response = InvokeCompleteAttendanceMethod(Globals.Data.URL, MethodName);
        return RetrieveCompleteAttendanceFromSoap(response);

    }

    public SoapObject InvokeCompleteAttendanceMethod(String URL, String MethodName) {

        SoapObject request = GetSoapObject(MethodName);

        User oUser = _appPrefs.getUser();
        int selectedMonth = Calendar.getInstance().get(Calendar.MONTH);
        int selecteYear = Calendar.getInstance().get(Calendar.YEAR);


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
        piYear.setValue(selecteYear);
        request.addProperty(piYear);

        PropertyInfo piStuID = new PropertyInfo();
        piStuID.setName("StuID");
        piStuID.setValue(oStudent.StuID);
        request.addProperty(piStuID);


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



    public void drawCalandar(CalendarPickerView calendar, Calendar currentMonth, Calendar nextMonth) {
        calendar.init(currentMonth.getTime(), nextMonth.getTime()).inMode(CalendarPickerView.SelectionMode.MULTIPLE);
    }    // TODO: Rename method, update argument and hook method into UI event


    public void drawCalandar(CalendarPickerView calendar, Calendar currentMonth, Calendar nextMonth,
                             ArrayList<Date> datesSelected) {
        calendar.init(currentMonth.getTime(), nextMonth.getTime()).inMode(CalendarPickerView.SelectionMode.MULTIPLE)
                .withSelectedDates(datesSelected);
    }


    public void updateDateArray(ArrayList<StudentAttendance> attendanceArray,
                                ArrayList<Date> datesAttended) {


        datesAttended.clear();
        for (int i = 0; i < attendanceArray.size(); i++) {

            try {

                if (validateDate(attendanceArray.get(i).ADate)) {
                    Toast toast = Toast.makeText(getActivity(), "Within range.", Toast.LENGTH_SHORT);
                    toast.show();
                    datesAttended.add(dateFormat.parse(attendanceArray.get(i).ADate));
                } else {
                    Toast toast = Toast.makeText(getActivity(), "Not within range.", Toast.LENGTH_SHORT);
                    toast.show();

                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            i++;
        }

        if (datesAttended.size() > 0)
            drawCalandar(calendarPicker, thisMonth, nextMonth, datesAttended);
        else
            drawCalandar(calendarPicker, thisMonth, nextMonth);
    }


    public boolean validateDate(String dateAttended) {

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");

        // if not valid, it will throw ParseException
        try {
            Date date = sdf.parse(dateAttended);
            if (date.getTime() >= thisMonth.getTime().getTime() && date.getTime() <= nextMonth.getTime().getTime())
                //Within range
                return true;

        } catch (ParseException e) {
            e.printStackTrace();

        }

        return false;
    }

}
