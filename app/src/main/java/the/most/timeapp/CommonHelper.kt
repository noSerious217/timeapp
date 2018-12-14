package the.most.timeapp

import java.util.*

private val oneHour: Float = 30F
private val oneMinute: Float = 0.5F

fun TimeToAngleConverter(hour:Int, minute:Int): Float {
    return when {
        hour < 12 -> hour * oneHour + minute * oneMinute
        else -> (hour-12) * oneHour + minute * oneMinute
    }
}