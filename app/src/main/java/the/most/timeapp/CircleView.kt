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

    fun drawSector(startH: Float, startM: Float, endH: Float, endM: Float){
        var paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val blue = java.lang.Integer.toHexString(nextInt(0, 255))
        val red = java.lang.Integer.toHexString(nextInt(0, 255))
        val green = java.lang.Integer.toHexString(nextInt(0, 255))
        paint.color = Color.parseColor("#" + red + blue + green)
        val start = TimeToAngleConverter(startH, startM)
        val end = TimeToAngleConverter(endH, endM)
        if(start >= end)
            _sectors.add(Sector(start, end, paint))
        else
            _sectors.add(Sector(end, start, paint))
        //canvas.drawArc(oval, startPos, endPos, true, paint)

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

private val oneHour: Float = 30F
private val oneMinute: Float = 0.5F

fun TimeToAngleConverter(hour:Float, minute:Float): Float {
    return when {
        hour < 12F -> hour * oneHour + minute * oneMinute
        else -> (hour-12F) * oneHour + minute * oneMinute
    }
}
