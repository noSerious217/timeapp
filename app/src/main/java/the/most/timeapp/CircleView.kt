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

    private var _circleColor: Int = Color.RED
    private var center_x = 0F
    private var center_y = 0F
    private var radius = 0F

    private lateinit var _circlePaint: Paint
    private lateinit var _sectors: ArrayList<Sector>

    /**
     * The text to draw
     */
//    var circleColor: String?
//        get() = _exampleString
//        set(value) {
//            _exampleString = value
//            invalidateTextPaintAndMeasurements()
//        }
//
//    /**
//     * The font color
//     */
//    var exampleColor: Int
//        get() = _exampleColor
//        set(value) {
//            _exampleColor = value
//            invalidateTextPaintAndMeasurements()
//        }
//
//    /**
//     * In the example view, this dimension is the font size.
//     */
//    var exampleDimension: Float
//        get() = _exampleDimension
//        set(value) {
//            _exampleDimension = value
//            invalidateTextPaintAndMeasurements()
//        }

    /**
     * In the example view, this drawable is drawn above the text.
     */
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

    fun drawSector(startPos: Float, endPos: Float){
        var paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val blue = java.lang.Integer.toHexString(nextInt(0, 255))
        val red = java.lang.Integer.toHexString(nextInt(0, 255))
        val green = java.lang.Integer.toHexString(nextInt(0, 255))
        paint.color = Color.parseColor("#" + red + blue + green)
        val start = nextInt(0, 360).toFloat()
        val end = nextInt(0, 360).toFloat()
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
        _circlePaint.style = Paint.Style.STROKE
        _circlePaint.color = _circleColor

        // Load attributes
//        val a = context.obtainStyledAttributes(
//            attrs, R.styleable.CircleView, defStyle, 0
//        )

//        _exampleString = a.get(
//            R.styleable.CircleView_exampleString
//        )
//        _exampleColor = a.getColor(
//            R.styleable.CircleView_exampleColor,
//            exampleColor
//        )
//        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
//        // values that should fall on pixel boundaries.
//        _exampleDimension = a.getDimension(
//            R.styleable.CircleView_exampleDimension,
//            exampleDimension
//        )

//        if (a.hasValue(R.styleable.CircleView_exampleDrawable)) {
//            exampleDrawable = a.getDrawable(
//                R.styleable.CircleView_exampleDrawable
//            )
//            exampleDrawable?.callback = this
//        }
//
//        a.recycle()

        // Set up a default TextPaint object
//        textPaint = TextPaint().apply {
//            flags = Paint.ANTI_ALIAS_FLAG
//            textAlign = Paint.Align.LEFT
//        }
//
//        // Update TextPaint and text measurements from attributes
//        invalidateTextPaintAndMeasurements()
    }
//
//    private fun invalidateTextPaintAndMeasurements() {
//        _circlePaint?.let {
//            it. = exampleDimension
//            it.color = exampleColor
//            textWidth = it.measureText(exampleString)
//            textHeight = it.fontMetrics.bottom
//        }
//    }

//    private fun invalidatePaintAndMeasurements() {
//        textPaint?.let {
//            it = exampleDimension
//            it.color = exampleColor
//            textWidth = it.measureText(exampleString)
//            textHeight = it.fontMetrics.bottom
//        }
//    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawColor(Color.CYAN)
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
