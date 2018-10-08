package game.simple.com.simplegame

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_finish.*

class Finish : AppCompatActivity() {

    private val kodeKeluar = 0
    private val kodeUlangi = 1
    private val kodeLanjut = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)
        val score = intent.extras.getInt("score")
        checkScore(score)
    }

    private fun checkScore(score: Int) {
        if (score < 6) {
            _txt_ucapan_selamat.text = "Yah... Sayang sekali, sampah yang kamu masukkan tidak pada tempatnya."
            _txt_motivasi1.text = "Coba lagi yah..."
            _txt_motivasi2.visibility = View.GONE
            _btn_lanjut.text = "Keluar"
            _btn_lanjut.setOnClickListener { setResultBack(kodeKeluar) }
        } else {
            _btn_lanjut.setOnClickListener { setResultBack(kodeLanjut) }
        }
        _btn_ulangi.setOnClickListener { setResultBack(kodeUlangi) }
    }

    private fun setResultBack(code: Int) {
        val intent = Intent()
        intent.putExtra("code", code)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

}
