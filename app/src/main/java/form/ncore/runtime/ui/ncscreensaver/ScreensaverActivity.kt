package form.ncore.runtime.ui.ncscreensaver

import android.animation.Animator
import android.animation.AnimatorInflater
import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.graphics.drawable.TransitionDrawable
import android.os.AsyncTask
import android.text.Layout
import android.transition.Transition
import android.view.animation.Animation


/**
 * Created by 4m on 08/11/2017.
 */

class ScreensaverActivity : Activity() {

    companion object {
        private val TAG = "ScreensaverActivity"
        private val UNLOCKING_TIMEOUT = 15000L
    }

    private lateinit var animatorSet: Animator
    private val handler = Handler()


     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screensaver)



        animatorSet = AnimatorInflater.loadAnimator(this, R.animator.badge_anim)!!
        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationEnd(animation: Animator?) {
                // Caused memmory leak!
                // {@link android.animation.Animator.AnimatorListener#onAnimationEnd(Animator)}
                // is called when cancel is invoked at the onDestroy.
                animatorSet.start()
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {

            }
        })
        animatorSet.setTarget(findViewById(R.id.badge))
        animatorSet.start()

     }

    override fun onResume() {
        super.onResume()
        showLogo()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()

        findViewById<CanvasView>(R.id.canvas_view).cancelAnimators()
        // Release all listeners (mainly this reference)
        animatorSet.removeAllListeners()
        animatorSet.cancel()
    }



    private fun showLogo() {
        findViewById<ImageView>(R.id.logo).visibility = View.VISIBLE
        findViewById<TextView>(R.id.device_number).visibility = View.GONE
        setBrightness(0f)
        findViewById<View>(R.id.canvas_view).setOnClickListener {
        }
    }

    private fun setBrightness(value: Float) {
        val layout = window.attributes
        layout.screenBrightness = value
        window.attributes = layout
    }


}