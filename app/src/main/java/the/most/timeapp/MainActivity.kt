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
import android.graphics.Color
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
import the.most.timeapp.models.TimeEventSpan
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.math.roundToInt
import kotlin.random.Random.Default.nextInt


class MainActivity : AppCompatActivity(), SensorEventListener {

    private class thisTime{
        var H: Float = 0F
        var M: Float = 0F
    }

    private lateinit var _timeEvents: ArrayList<TimeEventSpan>

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _timeEvents = arrayListOf()

        setContentView(R.layout.activity_main)
        layoutt=findViewById(R.id.linLayout)
        mCircleView = findViewById(R.id.circleView)

        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            currentDateTime = TextView(this)
            currentDateTime?.layoutParams = lButtonParams
            currentDateTime?.setId(i++)
            layoutt?.addView(currentDateTime)
            showEventAlertDialog()
        }
//        button4.setOnClickListener {
//            circularProgressbar.incrementProgressBy(100)
//        }
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    private fun showEventAlertDialog() {
        val dialog = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(sample_add_event_form_view, null)

        dialog.setView(dialogView)
            .setPositiveButton("ADD EVENT") { DialogInterface, i ->
                drawTimes(dialogView.editTextEventName.text.toString())
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

    private fun drawTimes(name: String) {
        var span = TimeEventSpan()
        span.Begin = startTime.H.roundToInt().toString() + ":" + startTime.M.roundToInt().toString()
        span.End = endTime.H.roundToInt().toString() + ":" + endTime.M.roundToInt().toString()
        span.EventName = name

        //_timeEvents.add(span)
        val blue = java.lang.Integer.toHexString(nextInt(0, 255))
        val red = java.lang.Integer.toHexString(nextInt(0, 255))
        val green = java.lang.Integer.toHexString(nextInt(0, 255))
        span.Color = "#" + red + blue + green

        mCircleView.drawSector(startTime.H, startTime.M, endTime.H, endTime.M, Color.parseColor(span.Color))

        doPostTimeEventSpanRequest(span)

        var text = span.EventName + " " + span.Begin + " " + span.End + " " + span.Color + '\n'
        _spanList.forEach{
            text += it.EventName + "|"
        }

        //textView2.text = TimeEventSpanListToJSON()
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
