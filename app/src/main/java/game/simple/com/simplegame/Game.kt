package game.simple.com.simplegame

import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.dialog_right_choose.*
import kotlinx.android.synthetic.main.dialog_wrong_choose.*
import kotlinx.android.synthetic.main.general_toolbar.*
import org.jetbrains.anko.intentFor

class Game : AppCompatActivity() {

    private var listSoal = ArrayList<Drawable?>()
    private var listTong = ArrayList<Drawable?>()
    private var listOrganik = ArrayList<Int>()
    private var listAnOrganik = ArrayList<Int>()
    private var listKaca = ArrayList<Int>()
    private var listLogam = ArrayList<Int>()
    private var listPlastik = ArrayList<Int>()
    private var listTumbuhan = ArrayList<Int>()
    private var isStart = false
    private var isFinishAll = false
    private var index = 0
    private var score = 0
    private var copyListSoal = ArrayList<Drawable?>()
    private var indexReal = 0
    private var level = 1
    private var wrong = 0
    private val codeRequestSetting = 109
    private val TAG = Game::class.java.simpleName

    private var timerCounter = object : CountDownTimer(90000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            _txt_timer.text = "${millisUntilFinished / 1000}"
        }

        override fun onFinish() {
            isFinishAll = true
            startActivity(intentFor<Finish>())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        setToolbar()
        setAllSoal()
        setTong()
        setOrganik()
        setAnOrganik()
        setKaca()
        setLogam()
        setPlastik()
        setTumbuhan()
        setComponent()
    }

    private fun setToolbar() {
        _general_toolbar.title = "Game Education"
        _general_toolbar.inflateMenu(R.menu.menu_game)
        _general_toolbar.menu.findItem(R.id.menu_setting).setOnMenuItemClickListener { toSetting() }
    }

    private fun toSetting(): Boolean {
        startActivityForResult(intentFor<Setting>("level" to level), codeRequestSetting)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            codeRequestSetting -> {
                level = data?.getIntExtra("level", 1) ?: 1
                checkVisibility()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (isFinishAll) {
            score = 0
            index = 0
            indexReal = 0
            isStart = false
            isFinishAll = false
            timerCounter.cancel()
            _txt_timer.text = "0"
            _txt_score.text = "0"
            checkVisibility()
            copyListSoal.shuffle()
        } else {

        }
    }

    private fun setAllSoal() {
        listSoal.apply {
            add(ContextCompat.getDrawable(this@Game, R.drawable.soal1))
            add(ContextCompat.getDrawable(this@Game, R.drawable.soal2))
            add(ContextCompat.getDrawable(this@Game, R.drawable.soal3))
            add(ContextCompat.getDrawable(this@Game, R.drawable.soal4))
            add(ContextCompat.getDrawable(this@Game, R.drawable.soal5))
            add(ContextCompat.getDrawable(this@Game, R.drawable.soal6))
            add(ContextCompat.getDrawable(this@Game, R.drawable.soal7))
            add(ContextCompat.getDrawable(this@Game, R.drawable.soal8))
            add(ContextCompat.getDrawable(this@Game, R.drawable.soal9))
            add(ContextCompat.getDrawable(this@Game, R.drawable.soal10))
            add(ContextCompat.getDrawable(this@Game, R.drawable.soal11))
            add(ContextCompat.getDrawable(this@Game, R.drawable.soal12))
            add(ContextCompat.getDrawable(this@Game, R.drawable.soal13))
            add(ContextCompat.getDrawable(this@Game, R.drawable.soal14))
            add(ContextCompat.getDrawable(this@Game, R.drawable.soal15))
            add(ContextCompat.getDrawable(this@Game, R.drawable.soal16))
            add(ContextCompat.getDrawable(this@Game, R.drawable.soal17))
            add(ContextCompat.getDrawable(this@Game, R.drawable.soal18))
            add(ContextCompat.getDrawable(this@Game, R.drawable.soal19))
            add(ContextCompat.getDrawable(this@Game, R.drawable.soal20))
            add(ContextCompat.getDrawable(this@Game, R.drawable.soal21))
            add(ContextCompat.getDrawable(this@Game, R.drawable.soal22))
            add(ContextCompat.getDrawable(this@Game, R.drawable.soal23))
            add(ContextCompat.getDrawable(this@Game, R.drawable.soal24))
            add(ContextCompat.getDrawable(this@Game, R.drawable.soal25))
            add(ContextCompat.getDrawable(this@Game, R.drawable.soal26))
            add(ContextCompat.getDrawable(this@Game, R.drawable.soal27))
        }
    }

    private fun setSoalLevel1() {
        copyListSoal.clear()
        for (a in 0 until listOrganik.size) {
            copyListSoal.add(listSoal[listOrganik[a]])
        }
        for (a in 0 until listAnOrganik.size) {
            copyListSoal.add(listSoal[listAnOrganik[a]])
        }
    }

    private fun setSoalLevel2() {
        setSoalLevel1()
        for (a in 0 until listPlastik.size) {
            copyListSoal.add(listSoal[listPlastik[a]])
        }
    }

    private fun setSoalLevel3() {
        copyListSoal.clear()
        for (a in 0 until listPlastik.size) {
            copyListSoal.add(listSoal[listPlastik[a]])
        }
        for (a in 0 until listLogam.size) {
            copyListSoal.add(listSoal[listLogam[a]])
        }
        for (a in 0 until listKaca.size) {
            copyListSoal.add(listSoal[listKaca[a]])
        }
        for (a in 0 until listTumbuhan.size) {
            copyListSoal.add(listSoal[listTumbuhan[a]])
        }
    }

    private fun setTong() {
        listTong.apply {
            add(ContextCompat.getDrawable(this@Game, R.drawable.tong1))
            add(ContextCompat.getDrawable(this@Game, R.drawable.tong2))
        }
    }

    private fun setOrganik() {
        listOrganik.apply {
            add(2)
            add(4)
            add(5)
            add(6)
            add(12)
            add(13)
            add(14)
            add(15)
        }
    }

    private fun setAnOrganik() {
        listAnOrganik.apply {
            add(0)
            add(1)
            add(3)
            add(7)
            add(8)
            add(9)
            add(10)
            add(11)
        }
    }

    private fun setKaca() {
        listKaca.apply {
            add(1)
            add(16)
            add(17)
            add(18)
            add(19)
            add(20)
            add(21)
            add(22)
        }
    }

    private fun setLogam() {
        listLogam.apply {
            add(0)
            add(3)
            add(7)
            add(23)
            add(24)
            add(25)
            add(26)
        }
    }

    private fun setPlastik() {
        listPlastik.apply {
            add(9)
            add(10)
        }
    }

    private fun setTumbuhan() {
        listTumbuhan.apply {
            add(2)
            add(6)
            add(14)
            add(15)
        }
    }

    private fun setComponent() {
        checkVisibility()
        _btn_start_game.setOnClickListener { startGame() }
        _btn_finish_game.setOnClickListener { timerCounter.onFinish() }
        _img_tong_organik.setOnClickListener { checkSampah(listOrganik) }
        _img_tong_anorganik.setOnClickListener { checkSampah(listAnOrganik) }
        _img_tong_plastik.setOnClickListener { checkSampah(listPlastik) }
        _img_tong_kaca.setOnClickListener { checkSampah(listKaca) }
        _img_tong_logam.setOnClickListener { checkSampah(listLogam) }
        _img_tong_kertas_tumbuhan.setOnClickListener { checkSampah(listTumbuhan) }
    }

    private fun startGame() {
        when (level) {
            1 -> {
                _img_tong_organik.visibility = View.VISIBLE
                _img_tong_anorganik.visibility = View.VISIBLE
                _img_tong_plastik.visibility = View.GONE
                _img_tong_logam.visibility = View.GONE
                _img_tong_kaca.visibility = View.GONE
                _img_tong_kertas_tumbuhan.visibility = View.GONE
                setSoalLevel1()
                copyListSoal.shuffle()
            }
            2 -> {
                _img_tong_organik.visibility = View.VISIBLE
                _img_tong_anorganik.visibility = View.VISIBLE
                _img_tong_plastik.visibility = View.VISIBLE
                _img_tong_logam.visibility = View.GONE
                _img_tong_kaca.visibility = View.GONE
                _img_tong_kertas_tumbuhan.visibility = View.GONE
                setSoalLevel2()
                copyListSoal.shuffle()
            }
            3 -> {
                _img_tong_organik.visibility = View.GONE
                _img_tong_anorganik.visibility = View.GONE
                _img_tong_plastik.visibility = View.VISIBLE
                _img_tong_logam.visibility = View.VISIBLE
                _img_tong_kaca.visibility = View.VISIBLE
                _img_tong_kertas_tumbuhan.visibility = View.VISIBLE
                setSoalLevel3()
                copyListSoal.shuffle()
            }
        }
        isStart = true
        timerCounter.start()
        checkVisibility()
        _img_question.setImageDrawable(copyListSoal[index])
    }

    private fun checkVisibility() {
        _linear_question.visibility = if (isStart) View.VISIBLE else View.GONE
        _btn_start_game.visibility = if (isStart) View.GONE else View.VISIBLE
        _btn_finish_game.visibility = if (isStart) View.VISIBLE else View.GONE
    }

    private fun checkSampah(listJenisSampah: ArrayList<Int>) {
        indexReal = listSoal.indexOf(copyListSoal[index])
        if (listJenisSampah.contains(indexReal)) {
            score += 1
            _txt_score.text = score.toString()
            if (index == 9) {
                timerCounter.onFinish()
            } else {
                showRightDialog(score.toString())
            }
        } else {
            wrong += 1
            showWrongDialog(score.toString(), wrong)
        }
    }

    /* private fun checkOrganik() {
         indexReal = listSoal.indexOf(copyListSoal[index])
         if (listOrganik.contains(indexReal)) {
             score += 1
             _txt_score.text = score.toString()
             if (index == copyListSoal.size - 1) {
                 timerCounter.onFinish()
             } else {
                 showRightDialog(score.toString())
             }
         } else {
             if (index == copyListSoal.size - 1) {
                 timerCounter.onFinish()
             } else {
                 showWrongDialog(score.toString())
             }
         }
     }

     private fun checkAnOrganik() {
         indexReal = listSoal.indexOf(copyListSoal[index])
         if (listAnOrganik.contains(indexReal)) {
             score += 1
             _txt_score.text = score.toString()
             if (index == copyListSoal.size - 1) {
                 timerCounter.onFinish()
             } else {
                 showRightDialog(score.toString())
             }
         } else {
             if (index == copyListSoal.size - 1) {
                 timerCounter.onFinish()
             } else {
                 showWrongDialog(score.toString())
             }
         }
     }*/

    private fun showRightDialog(score: String) {
        val dialog = Dialog(this@Game)
        dialog.setContentView(R.layout.dialog_right_choose)
        dialog._txt_score_dialog.text = score
        dialog._btn_next.setOnClickListener {
            nextQuestion()
            dialog.dismiss()
        }
        dialog._btn_finish.setOnClickListener {
            timerCounter.onFinish()
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun showWrongDialog(score: String, wrong: Int) {
        val dialog = Dialog(this@Game)
        dialog.setContentView(R.layout.dialog_wrong_choose)
        dialog._txt_score_dialog_wrong.text = score
        if (wrong == 2) {
            dialog._btn_next_wrong.visibility = View.GONE
        }
        dialog._btn_next_wrong.setOnClickListener {
            nextQuestion()
            dialog.dismiss()
        }
        dialog._btn_finish_wrong.setOnClickListener {
            timerCounter.onFinish()
            dialog.dismiss()
        }
        dialog.show()
        dialog.setCancelable(false)
    }

    private fun nextQuestion() {
        index += 1
        _img_question.setImageDrawable(copyListSoal[index])
    }
}