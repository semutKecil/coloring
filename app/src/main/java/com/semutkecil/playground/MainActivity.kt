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
        val coloring = Coloring.instance()
        coloring.addOnColorChange(Coloring.OnApply({
            tv.text = coloring.get("primary").color.toString()
            tv.setTextColor(coloring.get("primary").color)
        },listOf("primary")))

        findViewById(R.id.pick).setOnClickListener {
            ColorPickerDialogBuilder
                    .with(this)
                    .setTitle("Choose color")
                    .initialColor(coloring.get("primary").color)
                    .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                    .density(12)
                    //.setOnColorSelectedListener { selectedColor -> toast("onColorSelected: 0x" + Integer.toHexString(selectedColor)) }
                    .setPositiveButton("ok") { dialog, selectedColor, allColors ->
                        coloring.set("primary",selectedColor)
                        coloring.apply()
                    }
                    .setNegativeButton("cancel") { dialog, which -> }
                    .build()
                    .show()
        }


    }
}
