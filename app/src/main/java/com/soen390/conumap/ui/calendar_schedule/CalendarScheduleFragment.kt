package com.soen390.conumap.ui.calendar_schedule

import android.os.AsyncTask
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.GoogleApiAvailability
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.DateTime
import com.google.api.services.calendar.Calendar

import com.soen390.conumap.R
import com.soen390.conumap.calendar.Schedule
import com.soen390.conumap.ui.calendar_login.CalendarLoginFragment
import org.w3c.dom.Text
import java.io.IOException
import java.util.ArrayList

class CalendarScheduleFragment : Fragment() {

    companion object {
        fun newInstance() = CalendarScheduleFragment()
    }

    private lateinit var viewModel: CalendarScheduleViewModel
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var debugText: TextView
    val REQUEST_GOOGLE_PLAY_SERVICES = 1002
    val REQUEST_AUTHORIZATION = 1001
    var classNumber:String = ""
    var time: String = ""
    var location: String = ""
    lateinit var classNumberValue: TextView
    lateinit var timeValue: TextView
    lateinit var locationValue: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.calendar_schedule_fragment, container, false)
        val signOutButton = root.findViewById<View>(R.id.debug_sign_out)
        debugText = root.findViewById<View>(R.id.debug_text) as TextView
        if(Schedule.mCredential!= null){
            CalendarRequestTask(Schedule.mCredential!!).execute()
        }
        classNumberValue = root.findViewById<TextView>(R.id.class_number_value)
        timeValue = root.findViewById<TextView>(R.id.time_value)
        locationValue = root.findViewById<TextView>(R.id.location_value)
        val comingUpTestButton = root.findViewById<View>(R.id.coming_up)
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        mGoogleSignInClient = GoogleSignIn.getClient(activity!!, gso)

        signOutButton.setOnClickListener {
            signOut()
        }
        comingUpTestButton.setOnClickListener{
            classNumberValue.setText(classNumber)
            timeValue.setText(time)
            locationValue.setText(location)
        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CalendarScheduleViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun signOut(){
        mGoogleSignInClient.signOut()
        activity!!.supportFragmentManager.beginTransaction().replace(R.id.calendar_container,CalendarLoginFragment.newInstance()).commit()
    }
/*
    fun showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode: Int) {
        val apiAvailability = GoogleApiAvailability.getInstance()
        val dialog = apiAvailability.getErrorDialog(
            activity!!,
            connectionStatusCode,
            REQUEST_GOOGLE_PLAY_SERVICES)
        dialog.show()
    }*/

    private fun onCalendarRequestTaskCompleted(results: MutableList<String> ){
        Log.d("QUESTIONMARK", "Made it to onCalendarRequestTaskCompleted!")
        var firstEventArray = results[0].split("|").toTypedArray()
        classNumber = firstEventArray[0]
        time = firstEventArray[1] +"-" + firstEventArray[2]
        location = firstEventArray[3]

        showit()
    }

    private fun  showit(){
        classNumberValue.setText(classNumber)
        timeValue.setText(time)
        locationValue.setText(location)
    }

    private inner class CalendarRequestTask(credential: GoogleAccountCredential) : AsyncTask<Void, Void, MutableList<String>>() {
        private var mService: com.google.api.services.calendar.Calendar? = null
        private var mLastError: Exception? = null


        /**
         * Fetch a list of the next 10 events from the primary calendar.
         * @return List of Strings describing returned events.
         * @throws IOException
         */
        private// List the next 10 events from the primary calendar.
        // All-day events don't have start times, so just use
        // the start date.
        val dataFromApi: MutableList<String>
            @Throws(IOException::class)
            get() {
                val now = DateTime(System.currentTimeMillis())
                val eventStrings = ArrayList<String>()
                Log.d("QUESTIONMARK", "Obtaining events from calendar")
                /*
                *
                *
                *
                *
                * This is where the event collecting is done
                * you can choose which events you wanna take under here!!!
                *
                *
                *
                *
                *
                *
                * */
                val events = mService!!.events().list("primary")
                    .setMaxResults(10)
                    .setTimeMin(now)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute()
                Log.d("QUESTIONMARK", "data is obtained, now getting items from events")
                val items = events.items

                Log.d("QUESTIONMARK", "sorting through events and adding to eventString")
                for (event in items) {
                    var start = event.start.dateTime.toString()
                    var end = event.end.dateTime.toString()
                    var location = event.location
                    if (start == null) {
                        start = event.start.date.toString()
                    }
                    else{
                        var startStringArray = start.split("T").toTypedArray()
                        var endStringArray = end.split("T").toTypedArray()
                        start = startStringArray[0] + " at" + startStringArray[1].substring(0,6)
                        end = endStringArray[1].substring(0, 6)
                    }
                    /*if (event.endTimeUnspecified){
                        end = event.end.date
                    }*/
                    eventStrings.add(String.format("%s|%s|%s|%s", event.summary, start, end, event.location))
                }
                Log.d("QUESTIONMARK", "DATAFROMAPI IS done WOWOWOOW")
                return eventStrings
            }

        init {
            Log.d("QUESTIONMARK", "init")
            val transport = AndroidHttp.newCompatibleTransport()
            val jsonFactory = JacksonFactory.getDefaultInstance()
            mService = Calendar.Builder(
                transport, jsonFactory, credential)
                .setApplicationName("Google Calendar API Android Quickstart")
                .build()
        }

        /**
         * Background task to call Google Calendar API.
         * @param params no parameters needed for this task.
         */
        override fun doInBackground(vararg params: Void): MutableList<String>? {
            Log.d("QUESTIONMARK", "doInBackground")
            try {
                return dataFromApi
            } catch (e: Exception) {
                mLastError = e
                cancel(true)
                return null
            }

        }


        override fun onPreExecute() {
            Log.d("QUESTIONMARK", "onPreExecute")
            debugText.text = "loading...tool long? sign out"

        }

        override fun onPostExecute(output: MutableList<String>?) {
            Log.d("QUESTIONMARK", "onPostExecute")
            if (output == null || output.size == 0) {
                debugText.text = "No results returned."
            } else {
                output.add(0, "Data retrieved using the Google Calendar API:")
                debugText.text = (TextUtils.join("\n", output))
                this@CalendarScheduleFragment.onCalendarRequestTaskCompleted(output)
            }
        }

        override fun onCancelled() {

            if (mLastError != null) {
                if (mLastError is GooglePlayServicesAvailabilityIOException) {
                    /*showGooglePlayServicesAvailabilityErrorDialog(
                        (mLastError as GooglePlayServicesAvailabilityIOException)
                            .connectionStatusCode)*/
                } else if (mLastError is UserRecoverableAuthIOException) {
                    startActivityForResult(
                        (mLastError as UserRecoverableAuthIOException).intent,
                        REQUEST_AUTHORIZATION)
                } else {
                    debugText.text = "The following error occurred:\n" + mLastError!!.message
                }
            } else {
                debugText.text = "Request cancelled."
            }
        }
    }

}