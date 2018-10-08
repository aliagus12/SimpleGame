package game.simple.com.simplegame

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_finish.*

class Finish : AppCompatActivity() {

    private val kodeKeluar = 0
    private val kodeUlangi = 1
    private val kodeLanjut = 2
    private lateinit var mediaPlayerFinishGameHappy: MediaPlayer
    private lateinit var mediaPlayerFinishGameSad: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)
        val score = intent.extras.getInt("score")
        setMediaPlayer()
        checkScore(score)
    }

    private fun setMediaPlayer() {
        mediaPlayerFinishGameHappy = MediaPlayer.create(this@Finish, R.raw.applaus)
        mediaPlayerFinishGameSad = MediaPlayer.create(this@Finish, R.raw.wrong)
    }

    private fun checkScore(score: Int) {
        if (score < 6) {
            _txt_ucapan_selamat.text = "Yah... Sayang sekali, sampah yang kamu masukkan tidak pada tempatnya."
            _txt_motivasi1.text = "Coba lagi yah..."
            _txt_motivasi2.visibility = View.GONE
            _btn_lanjut.text = "Keluar"
            _trophy.visibility = View.GONE
            _dislike.visibility = View.VISIBLE
            mediaPlayerFinishGameSad.start()
            _btn_lanjut.setOnClickListener { setResultBack(kodeKeluar) }
        } else {
            _trophy.visibility = View.VISIBLE
            _dislike.visibility = View.GONE
            mediaPlayerFinishGameHappy.start()
            _btn_lanjut.setOnClickListener { setResultBack(kodeLanjut) }
        }
        _btn_ulangi.setOnClickListener { setResultBack(kodeUlangi) }
    }

    private fun setResultBack(code: Int) {
        if (mediaPlayerFinishGameHappy.isPlaying) mediaPlayerFinishGameHappy.stop()
        if (mediaPlayerFinishGameSad.isPlaying) mediaPlayerFinishGameSad.stop()
        val intent = Intent()
        intent.putExtra("code", code)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (mediaPlayerFinishGameSad.isPlaying) mediaPlayerFinishGameSad.stop()
        finish()
    }
}
