package the.most.timeapp

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View

import kotlinx.android.synthetic.main.activity_main.*
import android.app.TimePickerDialog
import android.text.format.DateUtils.*
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.content_main2.*
import java.util.*
import android.view.ViewGroup
import android.app.AlertDialog
import android.content.DialogInterface
import the.most.timeapp.R.layout.sample_add_event_form_view


class MainActivity : AppCompatActivity() {

    private var layoutt: LinearLayout? = null
    private lateinit var mCircleView: CircleView
    private var currentDateTime: TextView? = null
    private var dateAndTime = Calendar.getInstance()
    private var i:Int = 0
    private var lButtonParams = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        layoutt=findViewById(R.id.linLayout)
        mCircleView = findViewById(R.id.circleView)
        // currentDateTime=findViewById(R.id.datePicked)
        setInitialDateTime()
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, getString(R.string.testStr), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        fab.setOnClickListener { view ->
            currentDateTime = TextView(this)
            currentDateTime?.setLayoutParams(lButtonParams)
            currentDateTime?.setId(i++)
            layoutt?.addView(currentDateTime)
            showEventDialog(view)
            mCircleView.drawSector(0F, 0F)
        }
        button4.setOnClickListener {
            circularProgressbar.incrementProgressBy(100)
        }
}


    private fun showEventDialog(view:View): AlertDialog? {
        val inflater = layoutInflater

        val builder = AlertDialog.Builder(this)

        builder.setView(inflater.inflate(sample_add_event_form_view, null))
    // Add action buttons
           .setPositiveButton(
               "Ok",
               { dialogInterface: DialogInterface, i: Int ->
                   setTime(view)
               }
           )
        return builder.create()
    }


    // отображаем диалоговое окно для выбора времени
    fun setTime(v:View) {
        TimePickerDialog(
            this@MainActivity, t,
            dateAndTime.get(Calendar.HOUR_OF_DAY),
            dateAndTime.get(Calendar.MINUTE), true
        )
            .show()
    }
    // установка начальных даты и времени
    private fun setInitialDateTime() {

        currentDateTime?.text = formatDateTime(
            this,
            dateAndTime.timeInMillis,
            FORMAT_SHOW_TIME
        )
    }

    // установка обработчика выбора времени
    var t: TimePickerDialog.OnTimeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
        dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
        dateAndTime.set(Calendar.MINUTE, minute)
        setInitialDateTime()
    }
}
