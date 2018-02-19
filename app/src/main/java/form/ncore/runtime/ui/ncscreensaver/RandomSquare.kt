package form.ncore.runtime.ui.ncscreensaver

import android.animation.Animator
import android.animation.AnimatorInflater
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.os.Handler
import java.lang.Math.abs
import java.util.*

/**
 * Created by 4m on 08/11/2017.
 */

class RandomSquare(context: Context, private val maxX: Int, private val maxY: Int, private var halfSize: Float, maxDelay: Int) : RectF() {

    private val random = Random()
    private val handler = Handler()

    private var originX: Float = 0.0f
    private var originY: Float = 0.0f
    private var x: Float = 0.0f
    private var y: Float = 0.0f

    var alpha : Float = 0.0f //also used for scale

    private val animatorSet = AnimatorInflater.loadAnimator(context, R.animator.square_anim)!!

    init {
        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationEnd(animation: Animator?) {
                placeRandomly()
            }
            override fun onAnimationRepeat(animation: Animator?) {
            }
            override fun onAnimationCancel(animation: Animator?) {
            }
            override fun onAnimationStart(animation: Animator?) {
            }
        })
        animatorSet.setTarget(this)

        handler.postDelayed({
            placeRandomly()
        }, random.nextInt(maxDelay).toLong())
    }

    fun cancel() {
        handler.removeCallbacksAndMessages(null)
        animatorSet.removeAllListeners()
        animatorSet.cancel()
    }

    fun draw(canvas: Canvas, paint: Paint){
        if (abs(paint.alpha - (this.alpha * 255.0f)) >= 1f) {
            paint.alpha = (this.alpha * 255.0f).toInt()
        }
        canvas.drawRect(this, paint)
    }

    private fun placeRandomly() {
        originX = random.nextInt(maxX).toFloat()
        originY = random.nextInt(maxY).toFloat()
        animatorSet.start()
    }

    fun setX(relativeX : Float) {
        this.x = originX + relativeX
        this.left = x - halfSize * (alpha/2.0f + 0.5f)
        this.right = x + halfSize * (alpha/2.0f + 0.5f)
    }

    fun setY(relativeY : Float) {
        this.y = originY + relativeY
        this.top = y - halfSize * (alpha/2.0f + 0.5f)
        this.bottom = y + halfSize * (alpha/2.0f + 0.5f)
    }
}