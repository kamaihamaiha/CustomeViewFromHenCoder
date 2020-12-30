package cn.kk.view.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import cn.kk.view.utils.px
import kotlin.math.cos
import kotlin.math.sin

/**
 * 饼图
 */
class PieView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val RADIUS = 240f.px
    private val OFFSET_LENGTH = 20f.px

    //多个饼的角度
    private val ANGLES = floatArrayOf(30f, 70f, 120f, 90f, 50f)

    //多个饼的颜色
    private val COLORS = listOf(Color.parseColor("#C2185B"), Color.parseColor("#00ACC1"),
            Color.parseColor("#558B2F"), Color.parseColor("#5D4037"), Color.parseColor("#38FA81"))

    init {
        paint.color = Color.GRAY

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        var pieLeft: Float = width / 2f - RADIUS
        var pieTop: Float = height / 2f - RADIUS
        var pieRight: Float = width / 2f + RADIUS
        var pieBottom: Float = height / 2f + RADIUS

        var startAngle = 0f
        //画多个饼
        for ((index, angle) in ANGLES.withIndex()) {
            paint.color = COLORS[index]

            //加偏移
            if (index == 1) {
                canvas.save()
                canvas.translate(OFFSET_LENGTH * cos(Math.toRadians(startAngle + angle / 2f.toDouble()).toFloat()),
                        OFFSET_LENGTH * sin(Math.toRadians(startAngle + angle / 2f.toDouble())).toFloat())
            }
            canvas.drawArc(pieLeft, pieTop, pieRight, pieBottom, startAngle, angle, true, paint)
            startAngle += angle
            if (index == 1) {
                canvas.restore()
            }
        }

    }
}