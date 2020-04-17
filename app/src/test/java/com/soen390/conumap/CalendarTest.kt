package com.soen390.conumap

import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import android.os.Handler
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.Tasks.await
import com.nhaarman.mockitokotlin2.mock
import com.soen390.conumap.calendar.Schedule
import org.junit.Test
import java.util.concurrent.TimeUnit

class CalendarTest {

    //var schedule: Schedule = mock()
    var context: Context = mock()
    var activity: Activity = mock()
    var fragment: Fragment = mock()
    /*
    activity.
    activity.runOnUiThread(java.lang.Runnable {
        var tes = CalendarTask().execute(context)
    })*/
    var isRunning = true
    var mHandler = Handler()
    fun runCalendar() {
        Thread(Runnable {
            // TODO Auto-generated method stub
            while (isRunning) {
                try {
                    //Thread.sleep(10000);
                    Handler().post(Runnable {
                        // TODO Auto-generated method stub
                        isRunning = false
                        CalendarTask().execute(context)
                    })
                } catch (e: Exception) { // TODO: handle exception
                }
            }
        }).start()
    }

    @Test
    fun scheduleTest() {

        Schedule.setName("dav.semu@gmail.com")
        //Schedule.setUpCalendar(context)
        //CalendarTask().execute(context)
        /*
        Thread(Runnable {
            //activity.runOnUiThread {
                    var tes = CalendarTask().execute(context)
              //  }
        }).start()*/

        //val thread = MyThread(context)
        activity.runOnUiThread(Runnable
        {
            CalendarTask().execute(context)

        })
        runCalendar()

        //Thread.sleep(30000)
        TimeUnit.MINUTES.sleep(2);


        //CalendarTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
        //Thread.sleep(30000)
        var ok: Int

    }



    }
internal class MyThread(details: Context) : Runnable {
    private val details: Context = details


    override fun run() { // Use details here
        var tes = CalendarTask().execute(details).get(5,TimeUnit.SECONDS)
    }

}
class CalendarTask() : AsyncTask<Context, Void, MutableList<String>>() {
    override fun onPreExecute() {
        val test = Schedule.getNextEvent(0)
    }
    override fun doInBackground(vararg params: Context?): MutableList<String>? {
        return Schedule.setUpCalendar(params[0]!!)
    }


}