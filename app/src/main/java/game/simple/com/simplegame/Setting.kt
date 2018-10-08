package game.simple.com.simplegame

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.general_toolbar.*

class Setting : AppCompatActivity() {

    private var chooseLevel = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        val defaultLevel = intent.extras.getInt("level")
        setToolbar()
        setLevel(defaultLevel)
    }

    private fun setToolbar() {
        _general_toolbar.title = "Setting"
        _general_toolbar.inflateMenu(R.menu.menu_setting)
        _general_toolbar.navigationIcon = ContextCompat.getDrawable(this@Setting, R.drawable.ic_arrow_back_white_24dp)
        _general_toolbar.menu.findItem(R.id.menu_save_setting).setOnMenuItemClickListener { saveSetting() }
        _general_toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun saveSetting(): Boolean {
        when {
            _chk_level_1.isChecked -> chooseLevel = 1
            _chk_level_2.isChecked -> chooseLevel = 2
            _chk_level_3.isChecked -> chooseLevel = 3
        }
        val intent = Intent()
        intent.putExtra("level", chooseLevel)
        setResult(Activity.RESULT_OK, intent)
        finish()
        return true
    }

    private fun setLevel(defaultLevel: Int) {
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
        _chk_level_1.setOnCheckedChangeListener { it, isChecked ->
            if (isChecked) {
                it.isChecked = true
                _chk_level_2.isChecked = false
                _chk_level_3.isChecked = false
            }
        }
        _chk_level_2.setOnCheckedChangeListener { it, isChecked ->
            if (isChecked) {
                _chk_level_1.isChecked = false
                it.isChecked = true
                _chk_level_3.isChecked = false
            }
        }
        _chk_level_3.setOnCheckedChangeListener { it, isChecked ->
            if (isChecked) {
                _chk_level_1.isChecked = false
                _chk_level_2.isChecked = false
                it.isChecked = true
            }
        }
    }
}
