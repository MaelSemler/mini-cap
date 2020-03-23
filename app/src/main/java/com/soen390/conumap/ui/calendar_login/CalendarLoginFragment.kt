package com.soen390.conumap.ui.calendar_login

import android.accounts.AccountManager
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.services.calendar.Calendar
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.DateTime
import com.google.api.client.util.ExponentialBackOff
import com.google.api.services.calendar.CalendarScopes

import com.soen390.conumap.R
import com.soen390.conumap.calendar.Schedule
import com.soen390.conumap.ui.calendar_schedule.CalendarScheduleFragment
import java.io.IOException
import java.util.ArrayList

class CalendarLoginFragment : Fragment() {

    companion object {
        fun newInstance() = CalendarLoginFragment()
    }
    private lateinit var viewModel: CalendarLoginViewModel
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    val RC_SIGN_IN: Int = 1
    val PREF_ACCOUNT_NAME = "accountName"//TODO: figure out if this is needed
    var mCredential: GoogleAccountCredential? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.calendar_login_fragment, container, false)
        val signInButton = root.findViewById<View>(R.id.sign_in_button)

        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        mGoogleSignInClient = GoogleSignIn.getClient(activity!!, gso)

        signInButton.setOnClickListener {
            initCredentials()
            signIn()

        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CalendarLoginViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    private fun initCredentials() {
        mCredential = GoogleAccountCredential.usingOAuth2(
            activity!!.applicationContext,
            arrayListOf(CalendarScopes.CALENDAR))
            .setBackOff(ExponentialBackOff())

    }
    fun change(){
        activity!!.supportFragmentManager.beginTransaction().replace(R.id.calendar_container,CalendarScheduleFragment.newInstance()).commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === RC_SIGN_IN && data != null && data.extras != null){
            val task =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
            Schedule.initSchedule(mCredential!!)
            change()

        }
    }
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account =
                completedTask.getResult(ApiException::class.java)
                initCredentials(account!!.email!!)
        } catch (e: ApiException) {
            //Todo: handle it
        }
    }

    private fun initCredentials(name: String){
        val accountName = name
        if (accountName != null) {
            val settings = activity!!.getPreferences(Context.MODE_PRIVATE)
            val editor = settings.edit()
            editor.putString(PREF_ACCOUNT_NAME, accountName)
            editor.apply()
            mCredential!!.selectedAccountName = accountName
        }
    }




}
