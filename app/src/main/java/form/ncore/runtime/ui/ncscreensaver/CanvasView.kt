package form.ncore.runtime.ui.ncscreensaver

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.util.*

/**
 * Created by 4m on 08/11/2017.
 */

class CanvasView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private var paint = Paint()
    private var squares = ArrayList<RandomSquare>()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        initPaint()
        initSquares(w, h, 10000)
    }

    private fun initSquares(w: Int, h: Int, maxDelay: Int) {
        for(i in 0..50) {
            squares.add(RandomSquare(context, w, h, dpToPx(40), maxDelay))
        }
    }

    private fun dpToPx(dim: Int) : Float {
        return (resources.displayMetrics.density * dim)
    }

    private fun initPaint() {
        paint.isAntiAlias = false
        paint.color = Color.parseColor("#013D70")
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.strokeWidth = 1f
    }

    fun cancelAnimators() {
        squares.forEach {
            it.cancel()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        squares.forEach {
            it.draw(canvas, paint)
        }

        invalidate()
    }
}