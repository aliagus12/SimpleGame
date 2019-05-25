package game.simple.com.simplegame

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.dialog_panduan.*
import kotlinx.android.synthetic.main.dialog_right_choose.*
import kotlinx.android.synthetic.main.dialog_wrong_choose.*
import kotlinx.android.synthetic.main.general_toolbar.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.util.*
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
    private var listSoalByLevel = ArrayList<Int>()
    private var listSoalRandom = ArrayList<Int>()
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
    private val jumlahSoalYangTampil = 10
    private val levelTongSampah = arrayListOf(
            "Organik atau Anorganik",
            "Organik, Anorganik atau Plastik",
            "Logam, Kaca, Plastik atau Tumbuhan"
    )
    private val panduanPart1 = "Pada level ini kamu harus menentukan tong sampah"
    private val panduanPart2 = "yang cocok pada jenis sampah diatas. Selamat Mencoba!"


    private var timerCounter = object : CountDownTimer(90000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            _txt_timer.text = "${millisUntilFinished / 1000}"
        }

        override fun onFinish() {
            isFinishAll = true
            resetAllMediaPlayer()
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
        setMediaPlayerChoose()
        mediaPlayerPlayGame = MediaPlayer.create(this@Game, listSound[selectedSound])
        setComponent()
        setXRandom()
    }

    private fun setXRandom() {

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
                if (resultCode == Activity.RESULT_OK) {
                    level = data?.getIntExtra("level", 1) ?: 1
                    selectedBackground = data?.getIntExtra("background", 0) ?: 0
                    if (selectedBackground == 0) {
                        _img_background.setBackgroundResource(R.drawable.background_pedesaan)
                    } else {
                        _img_background.background = ContextCompat.getDrawable(this@Game, R.drawable.background_perkotaan)
                    }
                    selectedSound = data?.getIntExtra("sound", 0) ?: 0
                    isFinishAll = true

                } else {
                    Log.d(TAG, "result Cancel")
                }
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
                        isFinishAll = true
                        onResume()
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        checkVisibility()
        if (isFinishAll) {
            score = 0
            index = 0
            wrong = 0
            indexReal = 0
            isStart = false
            isFinishAll = false
            timerCounter.cancel()
            _txt_timer.text = "0"
            _txt_score.text = "0"
            checkVisibility()
            mediaPlayerPlayGame = MediaPlayer.create(this@Game, listSound[selectedSound])
            setMediaPlayerChoose()
            setLiveCount()
            //randomSoal()
        } else {
            Log.d(TAG, "not finish all")
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
        listSoalByLevel.clear()
        for (a in 0 until listOrganik.size) {
            listSoalByLevel.add(listSoal[listOrganik[a]])
        }
        for (a in 0 until listAnOrganik.size) {
            listSoalByLevel.add(listSoal[listAnOrganik[a]])
        }
    }

    private fun setSoalLevel2() {
        setSoalLevel1()
        for (a in 0 until listPlastik.size) {
            listSoalByLevel.add(listSoal[listPlastik[a]])
        }
    }

    private fun setSoalLevel3() {
        listSoalByLevel.clear()
        for (a in 0 until listPlastik.size) {
            listSoalByLevel.add(listSoal[listPlastik[a]])
        }
        for (a in 0 until listLogam.size) {
            listSoalByLevel.add(listSoal[listLogam[a]])
        }
        for (a in 0 until listKaca.size) {
            listSoalByLevel.add(listSoal[listKaca[a]])
        }
        for (a in 0 until listTumbuhan.size) {
            listSoalByLevel.add(listSoal[listTumbuhan[a]])
        }
    }

    // kerena soal berdasarkan level maka m = jumlah soal sesuai level
    // val m = listSoalByLevel.size
    // fungsi LCM
    private fun randomSoal() {
        listSoalRandom.clear()
        val m = listSoalByLevel.size
        var x0 = Random().nextInt(m)
        val a = 1
        val b = 7
        var xn: Int
        val listIndex = ArrayList<Int>()
        for (i in 0 until m) {
            xn = ((a * x0) + b) % m
            if (listSoalRandom.size < jumlahSoalYangTampil) {
                if (!listSoalRandom.contains(listSoalByLevel[xn])) {
                    listSoalRandom.add(listSoalByLevel[xn])
                    listIndex.add(xn)
                } else {
                    for(j in 0 until m){
                        if (!listSoalRandom.contains(listSoalByLevel[j])){
                            listSoalRandom.add(listSoalByLevel[j])
                            listIndex.add(j)
                            break
                        }
                    }
                }
            } else {
                break
            }
            x0 = xn
        }
        Log.d(TAG, "listIndex dari listSoalByLevel $listIndex")
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

    private fun setMediaPlayerChoose() {
        mediaPlayerChooseRight = MediaPlayer.create(this@Game, R.raw.sound_yee)
        mediaPlayerChooseWrong = MediaPlayer.create(this@Game, R.raw.wrong)
    }

    private fun setLiveCount() {
        when (wrong) {
            0 -> txt_live.text = "* *"
            1 -> txt_live.text = "*"
            else -> txt_live.text = ""
        }
    }

    private fun setComponent() {
        _btn_start_game.setOnClickListener {
            isStart = true
            checkVisibility()
            showDialogPanduan()
        }
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
                randomSoal()
            }
            2 -> {
                _img_tong_organik.visibility = View.VISIBLE
                _img_tong_anorganik.visibility = View.VISIBLE
                _img_tong_plastik.visibility = View.VISIBLE
                _img_tong_logam.visibility = View.GONE
                _img_tong_kaca.visibility = View.GONE
                _img_tong_kertas_tumbuhan.visibility = View.GONE
                setSoalLevel2()
                randomSoal()
            }
            3 -> {
                _img_tong_organik.visibility = View.GONE
                _img_tong_anorganik.visibility = View.GONE
                _img_tong_plastik.visibility = View.VISIBLE
                _img_tong_logam.visibility = View.VISIBLE
                _img_tong_kaca.visibility = View.VISIBLE
                _img_tong_kertas_tumbuhan.visibility = View.VISIBLE
                setSoalLevel3()
                randomSoal()
            }
        }

        if (!mediaPlayerPlayGame.isPlaying) mediaPlayerPlayGame.start()
        timerCounter.start()
        _img_question.setImageDrawable(ContextCompat.getDrawable(this@Game, listSoalRandom[index]))
        playSoundSampah()
    }

    private fun playSoundSampah() {
        when (listSoalRandom[index]) {
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

    private fun createMediaPlayer(soundInt: Int) {
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
        _general_toolbar.menu.findItem(R.id.menu_setting).isVisible = !isStart
    }

    private fun checkSampah(listJenisSampah: ArrayList<Int>) {
        if (mediaPlayerPlayGame.isPlaying) mediaPlayerPlayGame.pause()
        indexReal = listSoal.indexOf(listSoalRandom[index])
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
            setLiveCount()
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
                setMediaPlayerChoose()
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
                setMediaPlayerChoose()
                nextQuestion()
                dialogWrong?.dismiss()
            }
            it._btn_finish_wrong.setOnClickListener {
                timerCounter.onFinish()
                dialogWrong?.dismiss()
            }
        }
    }

    private fun showDialogPanduan(){
        val view = layoutInflater.inflate(R.layout.dialog_panduan, null)
        val dialogPanduan = Dialog(this@Game)
        dialogPanduan.let {
            it.setTitle("Panduan")
            it.setContentView(view)
            it.setCancelable(false)
            it.show()
            it._txt_panduan.text = "$panduanPart1 ${levelTongSampah[level - 1]} $panduanPart2"
            it._btn_ok_panduan.setOnClickListener {
                dialogPanduan.dismiss()
                startGame()
            }
        }
    }

    private fun nextQuestion() {
        index += 1
        _img_question.setImageDrawable(ContextCompat.getDrawable(this@Game, listSoalRandom[index]))
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
