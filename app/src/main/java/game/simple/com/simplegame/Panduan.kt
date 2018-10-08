package game.simple.com.simplegame

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_panduan.*
import kotlinx.android.synthetic.main.general_toolbar.*
import org.jetbrains.anko.intentFor

class Panduan : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_panduan)
        setComponent()
    }

    private fun setComponent() {
        _general_toolbar.title = "Panduan"
        _general_toolbar.navigationIcon = ContextCompat.getDrawable(this@Panduan, R.drawable.ic_arrow_back_white_24dp)
        _general_toolbar.setNavigationOnClickListener { goBack() }
        _btn_materi.setOnClickListener { toMateri() }
        _btn_aplikasi.setOnClickListener { toPanduanAplikasi() }
    }

    private fun goBack(): Boolean {
        onBackPressed()
        return true
    }

    private fun toMateri() {
        startActivity(intentFor<Materi>())
    }

    private fun toPanduanAplikasi() {
        startActivity(intentFor<PanduanAplikasi>())
    }
}
