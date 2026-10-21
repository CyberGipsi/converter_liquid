package com.example.ficha2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var etLiquid: EditText
    private lateinit var btnlitrostomililitros: Button
    private lateinit var btnlitrostocentilitros: Button
    private lateinit var btnlitrostodecalitros: Button
    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etLiquid = findViewById(R.id.etLiquid)
        btnlitrostomililitros = findViewById(R.id.btnlitrostomililitros)
        btnlitrostocentilitros = findViewById(R.id.btnlitrostocentilitros)
        btnlitrostodecalitros = findViewById(R.id.btnlitrostodecalitros)
        tvResult = findViewById(R.id.tvResult)

        btnlitrostomililitros.setOnClickListener {
            convertLiquid { litro ->
                val mililitro = litro * 1000
                String.format(Locale.getDefault(), "%.2f ml", mililitro)
            }
        }

        btnlitrostocentilitros.setOnClickListener {
            convertLiquid { litro ->
                val centilitros = litro * 100
                String.format(Locale.getDefault(), "%.2f cl", centilitros)
            }
        }

        btnlitrostodecalitros.setOnClickListener {
            convertLiquid { litro ->
                val decalitros = litro / 10
                String.format(Locale.getDefault(), "%.2f dal", decalitros)
            }
        }
    }

    private fun convertLiquid(conversion: (Double) -> String) {
        val inputStr = etLiquid.text.toString().trim()
        if (inputStr.isEmpty()) {
            Toast.makeText(this, getString(R.string.error_invalid_input), Toast.LENGTH_SHORT).show()
            return
        }
        val value = inputStr.toDoubleOrNull()
        if (value == null) {
            Toast.makeText(this, getString(R.string.error_invalid_input), Toast.LENGTH_SHORT).show()
            return
        }
        val resultText = getString(R.string.result_prefix) + conversion(value)
        tvResult.text = resultText
    }
}