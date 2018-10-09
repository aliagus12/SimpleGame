package game.simple.com.simplegame

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_intro.*
import org.jetbrains.anko.intentFor


class Intro : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        _lottie_intro.speed = 3f
        val handler = Handler()
        handler.postDelayed({ startGame() }, 5000L)
    }

    private fun startGame() {
        _lottie_intro.pauseAnimation()
        startActivity(intentFor<Game>())
        finish()
    }
}
