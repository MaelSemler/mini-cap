package com.soen390.conumap.ui.calendar_schedule

import android.os.AsyncTask
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException

import com.soen390.conumap.R
import com.soen390.conumap.calendar.Schedule
import com.soen390.conumap.ui.calendar_login.CalendarLoginFragment

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
    var weekCount = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.calendar_schedule_fragment, container, false)
        val signOutButton = root.findViewById<View>(R.id.debug_sign_out)
        debugText = root.findViewById<View>(R.id.debug_text) as TextView
        NextEventRequestTask().execute()

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

    private fun onCalendarRequestTaskCompleted(results: String){
        Log.d("QUESTIONMARK", "Made it to onCalendarRequestTaskCompleted!")
        var firstEventArray = results.split("|").toTypedArray()
        classNumber = firstEventArray[0]
        time = firstEventArray[1] +"-" + firstEventArray[2]
        location = firstEventArray[3]

        classNumberValue.setText(classNumber)
        timeValue.setText(time)
        locationValue.setText(location)
    }


    private inner class SheduleRequestTask() : AsyncTask<Void, Void, MutableList<String>>() {
        private var mLastError: Exception? = null


        override fun doInBackground(vararg params: Void): MutableList<String>? {
            Log.d("QUESTIONMARK", "doInBackground")
            try {
                return Schedule.getWeekEvents(weekCount)
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
                //output.add(0, "Data retrieved using the Google Calendar API:")
                //debugText.text = (TextUtils.join("\n", output))

                //scheduleBuild(output)
                //onCalendarRequestTaskCompleted(output)
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
    private inner class NextEventRequestTask() : AsyncTask<Void, Void, String>() {
        private var mLastError: Exception? = null


        /**
         * Background task to call Google Calendar API.
         * @param params no parameters needed for this task.
         */
        override fun doInBackground(vararg params: Void): String? {
            Log.d("QUESTIONMARK", "doInBackground")
            try {
                return Schedule.getNextEvent()
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

        override fun onPostExecute(output: String?) {
            Log.d("QUESTIONMARK", "onPostExecute")
            if (output == null || output == null) {
                debugText.text = "No results returned."
            } else {
                //output.add(0, "Data retrieved using the Google Calendar API:")
                //debugText.text = (TextUtils.join("\n", output))

                //scheduleBuild(output)
                onCalendarRequestTaskCompleted(output)
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