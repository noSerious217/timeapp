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
        var startPos: Float = 0F
        var endPos: Float = 0F
        var paint: Paint

        constructor(start:Float, end:Float, paint: Paint){
            startPos = start
            endPos = end
            this.paint = paint
        }
    }

    private var _circleColor: Int = Color.WHITE
    private var center_x = 0F
    private var center_y = 0F
    private var radius = 0F

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

    fun drawSector(startH: Float, startM: Float, endH: Float, endM: Float, color: Int){
        var paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = color
        val start = TimeStartToPointConverter(startH, startM)
        val eventduration = TimeDurationConverter(endH-startH, endM-startM)
        _sectors.add(Sector(start, eventduration, paint))
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

        center_x = (contentWidth/2).toFloat()
        center_y = (contentHeight/2).toFloat()
        radius = (contentHeight/2).toFloat()



        canvas.drawCircle(
            center_x,
            center_y,
            radius+5,
            _circlePaint
        )

        val oval = RectF()
        oval.set(
            center_x - radius, center_y - radius, center_x + radius,
            center_y + radius
        )

        _sectors.forEach{
            canvas.drawArc(
                oval,
                it.startPos,
                it.endPos,
                true,
                it.paint
            )
        }

    }
}

private const val oneHour: Float = 30F
private const val oneMinute: Float = 0.5F

fun TimeStartToPointConverter(hour:Float, minute:Float): Float {
    if (hour < 12F) return convertAngle(hour * oneHour + minute * oneMinute)
    else return convertAngle((hour-12F) * oneHour + minute * oneMinute)
}

fun TimeDurationConverter(hour:Float, minute:Float): Float {
    return hour * oneHour + minute * oneMinute
}

private fun convertAngle(angle:Float): Float{
    if(angle<90) return (270F + angle)
    else return (angle - 90F)
}
