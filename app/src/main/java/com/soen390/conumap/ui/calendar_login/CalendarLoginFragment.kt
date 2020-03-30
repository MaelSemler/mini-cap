package com.soen390.conumap.ui.calendar_login


import android.content.Intent
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
import com.google.android.gms.common.api.Scope
import com.google.android.gms.tasks.Task


import com.soen390.conumap.R
import com.soen390.conumap.calendar.Schedule
import com.soen390.conumap.ui.calendar_schedule.CalendarScheduleFragment

class CalendarLoginFragment : Fragment() {

    companion object {
        fun newInstance() = CalendarLoginFragment()
    }
    private lateinit var viewModel: CalendarLoginViewModel
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN: Int = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.calendar_login_fragment, container, false)
        val signInButton = root.findViewById<View>(R.id.sign_in_button)

        val gso =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestScopes(Scope("https://www.googleapis.com/auth/calendar.readonly")).requestIdToken(getString(R.string.clientID)).requestEmail().build()
        mGoogleSignInClient = GoogleSignIn.getClient(activity!!, gso)

        signInButton.setOnClickListener {
            signIn()
        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CalendarLoginViewModel::class.java)
        // TODO: Use the ViewModel
    }

    //Initiation of the signing in process
    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    //Gets the requested account and changes fragment
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === RC_SIGN_IN && data != null && data.extras != null){
            val task =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.calendar_container,CalendarScheduleFragment.newInstance()).commit()

        }
    }

    //Checks if the account if found and sets up the calendar
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            Schedule.getName(account!!.email!!)
            //Schedule.setUpCredentials(activity!!,account!!.email!!)
            //Schedule.setUpCalendar()
        } catch (e: ApiException) {
            //Todo: handle it
        }
    }






}