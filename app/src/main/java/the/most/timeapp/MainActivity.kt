package the.most.timeapp

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View

import kotlinx.android.synthetic.main.activity_main.*
import android.widget.TimePicker
import android.app.TimePickerDialog
import android.os.Build
import android.support.annotation.RequiresApi
import android.text.format.DateUtils
import android.text.format.DateUtils.*
import android.text.format.Time
import android.widget.LinearLayout
import android.widget.TextView
import java.util.*


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
            setTime(view)
            mCircleView.drawSector(0F, 0F)
        }


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
