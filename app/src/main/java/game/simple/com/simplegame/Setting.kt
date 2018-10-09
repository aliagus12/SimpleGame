package game.simple.com.simplegame

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.general_toolbar.*

class Setting : AppCompatActivity() {

    private var chooseLevel = 1
    private var chooseBackground = 0
    private var chooseSound = 0
    private val TAG = Setting::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        val defaultLevel = intent.extras.getInt("level")
        val defaultBackground = intent.extras.getInt("selectedBackground")
        val defaultSound = intent.extras.getInt("selectedSound")
        setToolbar()
        setComponent()
        setDefaultBackground(defaultBackground)
        setDefaultSound(defaultSound)
        setDefaultLevel(defaultLevel)

    }

    private fun setComponent() {
        //backgroud
        _chk_pedesaan.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                chooseBackground = 0
                setDefaultBackground(0)
            }
        }

        _chk_perkotaan.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                chooseBackground = 1
                setDefaultBackground(1)
            }
        }
        // sound
        _chk_anak_indonesia.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                chooseSound = 0
                setDefaultSound(0)
            }
        }

        _chk_balonku_ada_lima.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                chooseSound = 1
                setDefaultSound(1)
            }
        }

        _chk_burung_kakak_tua.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                chooseSound = 2
                setDefaultSound(2)
            }
        }

        _chk_kasih_ibu.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                chooseSound = 3
                setDefaultSound(3)
            }
        }
        // level
        _chk_level_1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                chooseLevel = 1
                setDefaultLevel(1)
            }
        }
        _chk_level_2.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                chooseLevel = 2
                setDefaultLevel(2)
            }
        }
        _chk_level_3.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                chooseLevel = 3
                setDefaultLevel(3)
            }
        }
    }

    private fun setDefaultBackground(defaultBackground: Int) {
        when (defaultBackground) {
            0 -> {
                _chk_pedesaan.isChecked = true
                _chk_perkotaan.isChecked = false
            }
            1 -> {
                _chk_pedesaan.isChecked = false
                _chk_perkotaan.isChecked = true
            }
        }
    }

    private fun setDefaultSound(defaultSound: Int) {
        when (defaultSound) {
            0 -> {
                _chk_anak_indonesia.isChecked = true
                _chk_balonku_ada_lima.isChecked = false
                _chk_burung_kakak_tua.isChecked = false
                _chk_kasih_ibu.isChecked = false
            }
            1 -> {
                _chk_anak_indonesia.isChecked = false
                _chk_balonku_ada_lima.isChecked = true
                _chk_burung_kakak_tua.isChecked = false
                _chk_kasih_ibu.isChecked = false
            }
            2 -> {
                _chk_anak_indonesia.isChecked = false
                _chk_balonku_ada_lima.isChecked = false
                _chk_burung_kakak_tua.isChecked = true
                _chk_kasih_ibu.isChecked = false
            }
            3 -> {
                _chk_anak_indonesia.isChecked = false
                _chk_balonku_ada_lima.isChecked = false
                _chk_burung_kakak_tua.isChecked = false
                _chk_kasih_ibu.isChecked = true
            }
        }

    }

    private fun setToolbar() {
        _general_toolbar.title = "Setting"
        _general_toolbar.inflateMenu(R.menu.menu_setting)
        _general_toolbar.navigationIcon = ContextCompat.getDrawable(this@Setting, R.drawable.ic_arrow_back_white_24dp)
        _general_toolbar.menu.findItem(R.id.menu_save_setting).setOnMenuItemClickListener { saveSetting() }
        _general_toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun saveSetting(): Boolean {
        if (!_chk_level_1.isChecked && !_chk_level_2.isChecked && !_chk_level_3.isChecked) {
            chooseLevel = 1
        }
        if (!_chk_anak_indonesia.isChecked && !_chk_balonku_ada_lima.isChecked && !_chk_burung_kakak_tua.isChecked && !_chk_kasih_ibu.isChecked) {
            chooseSound = 0
        }
        if (!_chk_pedesaan.isChecked && !_chk_perkotaan.isChecked) {
            chooseBackground = 0
        }
        val intent = Intent()
        intent.putExtra("level", chooseLevel)
        intent.putExtra("sound", chooseSound)
        intent.putExtra("background", chooseBackground)
        setResult(Activity.RESULT_OK, intent)
        finish()
        return true
    }

    private fun setDefaultLevel(defaultLevel: Int) {
        when (defaultLevel) {
            1 -> {
                _chk_level_1.isChecked = true
                _chk_level_2.isChecked = false
                _chk_level_3.isChecked = false
            }
            2 -> {
                _chk_level_1.isChecked = false
                _chk_level_2.isChecked = true
                _chk_level_3.isChecked = false
            }
            3 -> {
                _chk_level_1.isChecked = false
                _chk_level_2.isChecked = false
                _chk_level_3.isChecked = true
            }
        }
    }
}
