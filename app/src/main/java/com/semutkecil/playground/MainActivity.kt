package com.semutkecil.playground

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import com.semutkecil.coloring.Coloring


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tv = findViewById(R.id.coloringTest) as TextView
        Coloring.instance().addOnColorChange( {
            tv.text = Coloring.instance().primary.toString()
            tv.setTextColor(Coloring.instance().primary)
        },"primary")

        findViewById(R.id.pick).setOnClickListener {
            ColorPickerDialogBuilder
                    .with(this)
                    .setTitle("Choose color")
                    .initialColor(Coloring.instance().primary)
                    .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                    .density(12)
                    //.setOnColorSelectedListener { selectedColor -> toast("onColorSelected: 0x" + Integer.toHexString(selectedColor)) }
                    .setPositiveButton("ok") { dialog, selectedColor, allColors -> Coloring.instance().primary = selectedColor }
                    .setNegativeButton("cancel") { dialog, which -> }
                    .build()
                    .show()
        }


    }
}
