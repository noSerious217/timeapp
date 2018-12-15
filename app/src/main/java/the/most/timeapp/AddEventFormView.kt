package the.most.timeapp

import android.app.TimePickerDialog
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.text.format.DateUtils
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import java.util.*

/**
 * TODO: document your custom view class.
 */
class AddEventFormView : View {

    private var currentDateTime: TextView? = null
    private var dateAndTime = Calendar.getInstance()
    

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
            
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

    }

    fun setTime(v:View) = TimePickerDialog(
        context, t,
        dateAndTime.get(Calendar.HOUR_OF_DAY),
        dateAndTime.get(Calendar.MINUTE), true
    )
        .show()
    // установка начальных даты и времени
    private fun setInitialDateTime() {

        currentDateTime?.text = DateUtils.formatDateTime(
            context,
            dateAndTime.timeInMillis,
            DateUtils.FORMAT_SHOW_TIME
        )
    }

    // установка обработчика выбора времени
    var t: TimePickerDialog.OnTimeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
        dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
        dateAndTime.set(Calendar.MINUTE, minute)
        setInitialDateTime()
    }
}
