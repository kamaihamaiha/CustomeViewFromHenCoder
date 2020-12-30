package cn.kk.view.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import cn.kk.view.utils.px

/**
 * 仪表盘
 */
class DashView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val pathPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val dashPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path: Path = Path()
    private val dashPath: Path = Path()
    private val DASH_RANGE = 280f.px
    private val DASH_POINTER_LENGTH = DASH_RANGE * 0.8
    private val DASH_RADIUS = DASH_RANGE
    private val DASH_START_ANGLE = 150f
    private val DASH_SWEEP_ANGLE = 240f
    private val DASH_WIDTH = 3f
    private val DASH_HEIGHT = 15f
    private lateinit var pathDashPathEffect:PathDashPathEffect;

    init {

        pathPaint.strokeWidth = 1f.px
        pathPaint.style = Paint.Style.STROKE

        dashPaint.strokeWidth = 1f.px
        dashPaint.style = Paint.Style.STROKE
        dashPaint.color = Color.BLUE

        // 添加刻度
        dashPath.addRect(0f,0f,DASH_WIDTH,DASH_HEIGHT,Path.Direction.CCW)

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        //step1 画圆弧
        canvas?.drawPath(path, pathPaint)


        // 画刻度
        dashPaint.pathEffect = pathDashPathEffect
        canvas?.drawPath(path,dashPaint)
        dashPaint.pathEffect = null

        //画指针
        var stopX = width / 2f + DASH_POINTER_LENGTH * Math.cos(Math.toRadians((DASH_START_ANGLE + (DASH_SWEEP_ANGLE / 20) * 3).toDouble()))
        var stopY = height / 2f + DASH_POINTER_LENGTH * Math.sin(Math.toRadians((DASH_START_ANGLE + (DASH_SWEEP_ANGLE / 20) * 3).toDouble()))

        pathPaint.strokeWidth = 5f.px
        canvas?.drawLine(width / 2f,height / 2f,stopX.toFloat(),stopY.toFloat(),pathPaint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {

        path.reset()
        //step1 添加弧路径 以 view 中心为原点，上下左右占地 200。
        path.addArc(width / 2 - DASH_RANGE,
                height / 2 - DASH_RANGE,
                width / 2 + DASH_RANGE,
                height / 2 + DASH_RANGE,
                DASH_START_ANGLE, DASH_SWEEP_ANGLE)

        //测量弧的长度
        var pathMeasure:PathMeasure = PathMeasure(path,false)
        val pathLength = pathMeasure.length

        val dashUnitLength = (pathLength - DASH_WIDTH) / 20
        //添加刻度效果
        pathDashPathEffect = PathDashPathEffect(dashPath,dashUnitLength,0f,PathDashPathEffect.Style.ROTATE)
    }


}