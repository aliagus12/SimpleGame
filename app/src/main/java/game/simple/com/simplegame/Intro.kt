package game.simple.com.simplegame

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_intro.*
import org.jetbrains.anko.intentFor

class Intro : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        _btn_mulai.setOnClickListener { startGame() }
        _btn_panduan.setOnClickListener { toPanduan() }
    }

    private fun startGame() {
        startActivity(intentFor<Game>())
        finish()
    }

    private fun toPanduan() {
        startActivity(intentFor<Panduan>())
    }
}
