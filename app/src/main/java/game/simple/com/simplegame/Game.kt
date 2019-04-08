package game.simple.com.simplegame

import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
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
import org.jetbrains.anko.sdk27.coroutines.onClick
import kotlin.collections.ArrayList

class Game : AppCompatActivity() {

    private var listSoal = ArrayList<Int>()
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
    private var copyListSoal = ArrayList<Int>()
    private var indexReal = 0
    private var level = 1
    private var wrong = 0
    private val codeRequestSetting = 109
    private val codeRequestFinish = 108
    private var totalIndexSoalLevel = 9
    private val kodeKeluar = 0
    private val kodeUlangi = 1
    private val kodeLanjut = 2
    private var listSound = ArrayList<Int>()
    private var listSoundSampah = ArrayList<Int>()
    private var selectedSound = 0
    private var selectedBackground = 0
    private var countWrongAnswer = 0
    private lateinit var mediaPlayerChooseRight: MediaPlayer
    private lateinit var mediaPlayerChooseWrong: MediaPlayer
    private lateinit var mediaPlayerPlayGame: MediaPlayer
    private var mediaPlayerSampah: MediaPlayer? = null
    private var dialogWrong: Dialog? = null
    private var dialogRight: Dialog? = null
    private val TAG = Game::class.java.simpleName

    private var timerCounter = object : CountDownTimer(90000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            _txt_timer.text = "${millisUntilFinished / 1000}"
        }

        override fun onFinish() {
            isFinishAll = true
            startActivityForResult(intentFor<Finish>(
                    "score" to score,
                    "selectedBackgroud" to selectedBackground,
                    "selectedSound" to selectedSound
            ), codeRequestFinish)
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
        setListSound()
        setMediaPlayer()
        mediaPlayerPlayGame = MediaPlayer.create(this@Game, listSound[selectedSound])
        setComponent()
    }

    private fun setToolbar() {
        _general_toolbar.title = "Game Education"
        _general_toolbar.inflateMenu(R.menu.menu_game)
        _general_toolbar.menu.findItem(R.id.menu_setting).setOnMenuItemClickListener { toSetting() }
    }

    private fun toSetting(): Boolean {
        startActivityForResult(intentFor<Setting>(
                "level" to level,
                "selectedBackground" to selectedBackground,
                "selectedSound" to selectedSound
        ), codeRequestSetting)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            codeRequestSetting -> {
                level = data?.getIntExtra("level", 1) ?: 1
                selectedBackground = data?.getIntExtra("background", 0) ?: 0
                if (selectedBackground == 0) {
                    _img_background.setBackgroundResource(R.drawable.background_pedesaan)
                } else {
                    _img_background.background = ContextCompat.getDrawable(this@Game, R.drawable.background_perkotaan)
                }
                selectedSound = data?.getIntExtra("sound", 0) ?: 0
                mediaPlayerPlayGame = MediaPlayer.create(this@Game, listSound[selectedSound])
                checkVisibility()
            }

            codeRequestFinish -> {
                val code = data?.getIntExtra("code", 0)
                when (code) {
                    kodeKeluar -> finish()
                    kodeLanjut -> {
                        level += 1
                        onResume()
                    }
                    kodeUlangi -> {
                        onResume()
                    }
                }
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
            mediaPlayerPlayGame = MediaPlayer.create(this@Game, listSound[selectedSound])
            setMediaPlayer()
            copyListSoal.shuffle()
        } else {

        }
    }

    private fun setAllSoal() {
        listSoal = arrayListOf(
                R.drawable.soal1,
                R.drawable.soal2,
                R.drawable.soal3,
                R.drawable.soal4,
                R.drawable.soal5,
                R.drawable.soal6,
                R.drawable.soal7,
                R.drawable.soal8,
                R.drawable.soal9,
                R.drawable.soal10,
                R.drawable.soal11,
                R.drawable.soal12,
                R.drawable.soal13,
                R.drawable.soal14,
                R.drawable.soal15,
                R.drawable.soal16,
                R.drawable.soal17,
                R.drawable.soal18,
                R.drawable.soal19,
                R.drawable.soal20,
                R.drawable.soal21,
                R.drawable.soal22,
                R.drawable.soal23,
                R.drawable.soal24,
                R.drawable.soal25,
                R.drawable.soal26,
                R.drawable.soal27
        )
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

    private fun setListSound() {
        listSound = arrayListOf(
                R.raw.anak_indonesia,
                R.raw.balonku_ada_lima,
                R.raw.burung_kakatua,
                R.raw.kasih_ibu
        )
    }

    private fun setMediaPlayer() {
        mediaPlayerChooseRight = MediaPlayer.create(this@Game, R.raw.sound_yee)
        mediaPlayerChooseWrong = MediaPlayer.create(this@Game, R.raw.wrong)
    }

    private fun setComponent() {
        checkVisibility()
        _btn_start_game.setOnClickListener { startGame() }
        _btn_finish_game.setOnClickListener {
            timerCounter.onFinish()
        }
        _img_tong_organik.setOnClickListener {
            checkSampah(listOrganik)
        }
        _img_tong_anorganik.setOnClickListener {
            checkSampah(listAnOrganik)
        }
        _img_tong_plastik.setOnClickListener {
            checkSampah(listPlastik)
        }
        _img_tong_kaca.setOnClickListener {
            checkSampah(listKaca)
        }
        _img_tong_logam.setOnClickListener {
            checkSampah(listLogam)
        }
        _img_tong_kertas_tumbuhan.setOnClickListener {
            checkSampah(listTumbuhan)
        }
        _btn_panduan.setOnClickListener { startActivity(intentFor<Panduan>()) }
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
        if (!mediaPlayerPlayGame.isPlaying) mediaPlayerPlayGame.start()
        timerCounter.start()
        checkVisibility()
        _general_toolbar.menu.findItem(R.id.menu_setting).isVisible = !isStart
        _img_question.setImageDrawable(ContextCompat.getDrawable(this@Game, copyListSoal[index]))
        playSoundSampah()
    }

    private fun playSoundSampah(){
        when(copyListSoal[index]) {
            R.drawable.soal1 -> {
                createMediaPlayer(R.raw.baterai)
            }

            R.drawable.soal2, R.drawable.soal17 -> {
                createMediaPlayer(R.raw.botol_kaca)
            }

            R.drawable.soal3, R.drawable.soal16 -> {
                createMediaPlayer(R.raw.daun)
            }

            R.drawable.soal4 -> {
                createMediaPlayer(R.raw.kaset_dvd)
            }

            R.drawable.soal5 -> {
                createMediaPlayer(R.raw.sisa_buah_apel)
            }

            R.drawable.soal6 -> {
                createMediaPlayer(R.raw.tulang)
            }

            R.drawable.soal7 -> {
                createMediaPlayer(R.raw.kulit_pisang)
            }

            R.drawable.soal8 -> {
                createMediaPlayer(R.raw.kaleng_minuman)
            }

            R.drawable.soal9, R.drawable.soal12 -> {
                createMediaPlayer(R.raw.kertas)
            }

            R.drawable.soal10 -> {
                createMediaPlayer(R.raw.kantong_plastik)
            }

            R.drawable.soal13 -> {
                createMediaPlayer(R.raw.cangkang_telur)
            }

            R.drawable.soal14 -> {
                createMediaPlayer(R.raw.duri_ikan)
            }

            R.drawable.soal24 -> {
                createMediaPlayer(R.raw.gembok)
            }

            R.drawable.soal25 -> {
                createMediaPlayer(R.raw.paku)
            }

            R.drawable.soal18, R.drawable.soal19, R.drawable.soal20, R.drawable.soal21, R.drawable.soal22, R.drawable.soal23 -> {
                createMediaPlayer(R.raw.kaca)
            }
        }
    }

    private fun createMediaPlayer(soundInt: Int){
        mediaPlayerSampah = MediaPlayer.create(this@Game, soundInt)
                mediaPlayerSampah?.setOnCompletionListener {
                    it.stop()
                    it.reset()
                    mediaPlayerSampah = null
                }
        mediaPlayerSampah?.start()
    }

    private fun checkVisibility() {
        _relative_main.visibility = if (isStart) View.VISIBLE else View.GONE
        _relative_bottom.visibility = if (isStart) View.GONE else View.VISIBLE
        _btn_finish_game.visibility = if (isStart) View.VISIBLE else View.GONE
    }

    private fun checkSampah(listJenisSampah: ArrayList<Int>) {
        if (mediaPlayerPlayGame.isPlaying) mediaPlayerPlayGame.pause()
        indexReal = listSoal.indexOf(copyListSoal[index])
        if (listJenisSampah.contains(indexReal)) {
            score += 1
            _txt_score.text = score.toString()
            if (index == totalIndexSoalLevel) {
                timerCounter.onFinish()
            } else {
                showRightDialog(score.toString())
                if (!mediaPlayerChooseRight.isPlaying) mediaPlayerChooseRight.start()
            }
        } else {
            wrong += 1
            showWrongDialog(score.toString(), wrong)
            if (!mediaPlayerChooseWrong.isPlaying) mediaPlayerChooseWrong.start()
        }
    }

    override fun onStop() {
        super.onStop()
        timerCounter.cancel()
        resetAllMediaPlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        timerCounter.cancel()
        resetAllMediaPlayer()
    }

    private fun showRightDialog(score: String) {
        val view = layoutInflater.inflate(R.layout.dialog_right_choose, null)
        dialogRight = Dialog(this@Game)
        dialogRight?.let {
            it.setContentView(view)
            it._txt_score_dialog.text = score
            it.setCancelable(false)
            it.show()
            it._btn_next.onClick {
                if (mediaPlayerChooseRight.isPlaying) mediaPlayerChooseRight.stop(); mediaPlayerChooseRight.reset()
                if (!mediaPlayerPlayGame.isPlaying) mediaPlayerPlayGame.start()
                setMediaPlayer()
                nextQuestion()
                dialogRight?.dismiss()
            }
            it._btn_finish.onClick {
                timerCounter.onFinish()
                dialogRight?.dismiss()
            }
        }
    }

    private fun showWrongDialog(score: String, wrong: Int) {
        val view = layoutInflater.inflate(R.layout.dialog_wrong_choose, null)
        dialogWrong = Dialog(this@Game)
        dialogWrong?.let {
            it.setContentView(view)
            it._txt_score_dialog_wrong.text = score
            it.setCancelable(false)
            it.show()
            if (wrong == 2) {
                it._btn_next_wrong.visibility = View.GONE
            }
            it._btn_next_wrong.setOnClickListener {
                if (mediaPlayerChooseWrong.isPlaying) mediaPlayerChooseWrong.stop();mediaPlayerChooseWrong.reset()
                if (!mediaPlayerPlayGame.isPlaying) mediaPlayerPlayGame.start()
                setMediaPlayer()
                nextQuestion()
                dialogWrong?.dismiss()
            }
            it._btn_finish_wrong.setOnClickListener {
                timerCounter.onFinish()
                dialogWrong?.dismiss()
            }
        }
    }

    private fun nextQuestion() {
        index += 1
        _img_question.setImageDrawable(ContextCompat.getDrawable(this@Game, copyListSoal[index]))
        playSoundSampah()
    }

    override fun onPause() {
        super.onPause()
        resetAllMediaPlayer()
        hiddenAllDialog()
    }

    private fun hiddenAllDialog() {
        dialogRight?.let { if (it.isShowing) it.dismiss() }
        dialogWrong?.let { if (it.isShowing) it.dismiss() }
    }

    private fun resetAllMediaPlayer() {
        if (mediaPlayerChooseWrong.isPlaying) mediaPlayerChooseWrong.stop();mediaPlayerChooseWrong.reset()
        if (mediaPlayerChooseRight.isPlaying) mediaPlayerChooseRight.stop(); mediaPlayerChooseRight.reset()
        if (mediaPlayerPlayGame.isPlaying) mediaPlayerPlayGame.stop(); mediaPlayerPlayGame.reset()
    }
}
