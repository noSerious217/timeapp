package the.most.timeapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

import kotlinx.android.synthetic.main.activity_main.*
import android.app.TimePickerDialog
import android.text.format.DateUtils.*
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.content_main2.*
import java.util.*
import android.app.AlertDialog
import android.app.Dialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import android.content.DialogInterface
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Handler
import android.text.Editable
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.sample_add_event_form_view.*
import kotlinx.android.synthetic.main.sample_add_event_form_view.view.*
import the.most.timeapp.R.layout.sample_add_event_form_view
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime


class MainActivity : AppCompatActivity(), SensorEventListener {

    private class thisTime{
        var H: Float = 0F
        var M: Float = 0F
    }
    private var running = false
    var sensorManager:SensorManager? = null
    var handler: Handler = Handler()
    private var layoutt: LinearLayout? = null
    private lateinit var mCircleView: CircleView
    private var currentDateTime: TextView? = null
    private var dateAndTime = Calendar.getInstance()
    private var i:Int = 0
    private var lButtonParams = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
    )
    private lateinit var currentText: EditText

    private var currentTime: thisTime = thisTime()
    private var startTime: thisTime = thisTime()
    private var endTime: thisTime = thisTime()

//    var startH: Float = 0F
//    var startM: Float = 0F
//    var endH: Float = 0F
//    var endM: Float = 0F


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        layoutt=findViewById(R.id.linLayout)
        mCircleView = findViewById(R.id.circleView)
        // currentDateTime=findViewById(R.id.datePicked)

        //setInitialDateTime()

        setSupportActionBar(toolbar)

       // showEventAlertDialog()

        fab.setOnClickListener { view ->
            currentDateTime = TextView(this)
            currentDateTime?.setLayoutParams(lButtonParams)
            currentDateTime?.setId(i++)
            layoutt?.addView(currentDateTime)
            showEventAlertDialog()
        }
        button4.setOnClickListener {
            circularProgressbar.incrementProgressBy(100)
        }
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    //.setPositiveButton(R.string.signin, new DialogInterface.OnClickListener()

    private fun showEventAlertDialog() {
        val dialog = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(sample_add_event_form_view, null)

        dialog.setView(dialogView)
            .setPositiveButton("ADD EVENT") { DialogInterface, i ->
                drawTimes()
            }

        dialogView.buttonAddBegin.setOnClickListener{
            currentText = dialogView.editTextBegin
            currentTime = startTime
            setTime(it)
        }
        dialogView.buttonAddEnd.setOnClickListener{
            currentText = dialogView.editTextEnd
            currentTime = endTime
            setTime(it)
        }

        dialog.show()
    }

    private fun validateTimes(){

    }

    private fun drawTimes() {
        textView2.text = startTime.H.toString() + "^" + startTime.M.toString() + "$" + endTime.H.toString() + "^" + endTime.M.toString()
//        var times = startTime.split(":", " ")
//        startH = times[0].toFloat()
//        // if (times[2]?.equals("PM")) currentH += 12
//        startM = times[1].toFloat()
//        times = endTime.split(":", " ")
//        endH = times[0].toFloat()
//        // if (times[2]?.equals("PM")) currentH += 12
//        endM = times[1].toFloat()
//        textView2.text = startH.toString() + " " + startM.toString() + " " + endH.toString() + " " + endM.toString()
        mCircleView.drawSector(startTime.H, startTime.M, endTime.H, endTime.M)
    }


    // отображаем диалоговое окно для выбора времени
    private fun setTime(v:View) {
        TimePickerDialog(
            this@MainActivity, timeLis,
            dateAndTime.get(Calendar.HOUR_OF_DAY),
            dateAndTime.get(Calendar.MINUTE), true
        ).show()
    }

    // установка начальных даты и времени
    private fun setInitialDateTime() {
        val current = formatDateTime(
            this,
            dateAndTime.timeInMillis,
            FORMAT_SHOW_TIME
        )
        currentText?.setText(current)
        val times = current.split(":", " ")
        currentTime.H = times[0].toFloat()
       // if (times[2]?.equals("PM")) currentH += 12
        currentTime.M = times[1].toFloat()
       // currentText?.setText(currentH.toString() + " " + currentM.toString())
    }

    // установка обработчика выбора времени
    var timeLis: OnTimeSetListener = OnTimeSetListener { view, hourOfDay, minute ->
        dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
        dateAndTime.set(Calendar.MINUTE, minute)
        setInitialDateTime()
    }

    override fun onResume() {
        super.onResume()
        running = true
        var stepsSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        if (stepsSensor == null) {
            Toast.makeText(this,"No Step Counter Sensor!", Toast.LENGTH_SHORT).show()
        } else {
            sensorManager?.registerListener(this, stepsSensor,SensorManager.SENSOR_DELAY_UI)

        }
    }

    override fun onPause() {
        super.onPause()
        running = false
        sensorManager?.unregisterListener(this)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onSensorChanged(p0: SensorEvent?) {
        if (running)
            if (p0 != null) {
                circularProgressbar.progress= p0.values[0].toInt()
            }
    }
}
