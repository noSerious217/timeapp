package the.most.timeapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import android.R.attr.radius
import android.R.attr.topLeftRadius
import android.graphics.Color.rgb
import android.graphics.RectF
import android.os.Build
import android.support.annotation.RequiresApi
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.sample_circle_view.view.*
import the.most.timeapp.models.TimeEventSpan
import kotlin.random.Random
import kotlin.random.Random.Default.nextFloat
import kotlin.random.Random.Default.nextInt


class CircleView : View {

    private class Sector {
        var clockBig: Boolean = true
        var startPos: Float = 0F
        var endPos: Float = 0F
        var paint: Paint

        constructor(start: Float, end: Float, paint: Paint, isBigClock: Boolean) {
            startPos = start
            endPos = end
            this.paint = paint
            clockBig = isBigClock
        }
    }

    private var _circleColor: Int = Color.WHITE
    private var center_x = 0F
    private var center_y = 0F
    private var radius = 0F
    private var radius2 = 0F

    private lateinit var _circlePaint: Paint
    private lateinit var _sectors: ArrayList<Sector>

    var exampleDrawable: Drawable? = null

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs)
    }

    fun drawSector(startH: Float, startM: Float, endH: Float, endM: Float, color: Int) {
        var paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = color
//        val start = TimeStartToPointConverter(startH, startM)
//        val eventduration = TimeDurationConverter(endH-startH, endM-startM)
        timeToSectorConverter(startH, startM, endH, endM, paint)
        postInvalidate()
    }

    private fun init(attrs: AttributeSet?) {
        _sectors = arrayListOf()
        _circlePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        _circlePaint.color = _circleColor

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val contentWidth = width - paddingLeft - paddingRight
        val contentHeight = height - paddingTop - paddingBottom

        center_x = (contentWidth / 2).toFloat()
        center_y = (contentHeight / 2).toFloat()
        radius = (contentHeight / 2).toFloat()
        radius2 = radius * 0.8F


        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.WHITE

        val oval = RectF()
        oval.set(
            center_x - radius, center_y - radius, center_x + radius,
            center_y + radius
        )
        val oval2 = RectF()
        oval2.set(
            center_x - radius2, center_y - radius2, center_x + radius2,
            center_y + radius2
        )

        _sectors.forEach {
            if (it.clockBig) {
                canvas.drawArc(
                    oval,
                    it.startPos,
                    it.endPos,
                    true,
                    it.paint
                )
            }
        }

        canvas.drawCircle(
            center_x,
            center_y,
            radius2,
            paint
        )

        _sectors.forEach {
            if (!it.clockBig) {
                canvas.drawArc(
                    oval2,
                    it.startPos,
                    it.endPos,
                    true,
                    it.paint
                )
            }
        }

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 5F
        paint.color = Color.BLACK
        canvas.drawCircle(
            center_x,
            center_y,
            radius2+1,
            paint
        )

        paint.style = Paint.Style.FILL
        paint.color = Color.BLACK
        canvas.drawCircle(
            center_x,
            center_y,
            radius*0.05F,
            paint
        )

    }

    private val oneHour: Float = 30F
    private val oneMinute: Float = 0.5F

    private val smallH = 11F
    private val smallM = 59F

    private val bigH = 12F
    private val bigM = 00F

    fun timeToSectorConverter(startH: Float, startM: Float, endH: Float, endM: Float, paint: Paint) {
        var start = 0F
        var eventduration = 0F
        if (startH < 12 && endH < 12) {
            start = TimeStartToPointConverter(startH, startM)
            eventduration = TimeDurationConverter(timeDivH(startH, startM, endH, endM), timeDivM(startH, startM, endH, endM))
            _sectors.add(Sector(start, eventduration, paint, false))

        } else if (startH < 12 && endH >= 12) {
            start = TimeStartToPointConverter(startH, startM)
            eventduration = TimeDurationConverter(timeDivH(startH, startM, smallH, smallM), timeDivM(startH, startM, smallH, smallM))
            _sectors.add(Sector(start, eventduration, paint, false))

            start = TimeStartToPointConverter(bigH, bigM)
            eventduration = TimeDurationConverter(timeDivH(bigH, bigM, endH, endM), timeDivM(bigH, bigM, endH, endM))
            _sectors.add(Sector(start, eventduration, paint, true))

        } else if (startH >= 12 && endH >= 12) {
            start = TimeStartToPointConverter(startH, startM)
            eventduration = TimeDurationConverter(timeDivH(startH, startM, endH, endM), timeDivM(startH, startM, endH, endM))
            _sectors.add(Sector(start, eventduration, paint, true))
        }
    }

    fun TimeStartToPointConverter(hour: Float, minute: Float): Float {
        if (hour < 12F) return convertAngle(hour * oneHour + minute * oneMinute)
        else return convertAngle((hour - 12F) * oneHour + minute * oneMinute)
    }

    fun TimeDurationConverter(hour: Float, minute: Float): Float {
        return hour * oneHour + minute * oneMinute
    }

    private fun convertAngle(angle: Float): Float {
        if (angle < 90) return (270F + angle)
        else return (angle - 90F)
    }

    private fun timeDivH(startH: Float, startM: Float, endH: Float, endM: Float): Float {
        if(endM-startM<0){
            return endH - startH - 1
        }
        else return endH - startH
    }

    private fun timeDivM(startH: Float, startM: Float, endH: Float, endM: Float): Float {
        if(endM-startM<0){
            return (60 - startM) + endM
        }
        else return endM-startM
    }
}
