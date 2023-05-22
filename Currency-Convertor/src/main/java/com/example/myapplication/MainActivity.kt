package com.example.myapplication

import ExchangeRatesApi
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast

import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp

import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var submitButton: Button
    private lateinit var resultText: TextView
    private lateinit var et_currencyFrom: EditText
    private lateinit var et_currencyTo: EditText
    private lateinit var toolbar: Toolbar
    var currencyFrom = "INR"
    var currencyTo = "USD"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_layout)
        toolbar=findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        et_currencyFrom=findViewById(R.id.currencyFrom)
        et_currencyTo=findViewById(R.id.currencyTo)
        spinnerSetup()
        onTextChanged()
    }

    private fun getAPI() {
        if (et_currencyFrom != null && et_currencyFrom.text.toString()
                .isNotEmpty() && et_currencyFrom.text.isNotBlank()
        ) {
            //call the funciton
            if (currencyFrom == currencyTo) {
                Toast.makeText(
                    applicationContext,
                    "cannot convert the same currency",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                GlobalScope.launch(Dispatchers.IO) {
                    val resultObject = ExchangeRatesApi.getExchangeRate(
                        currencyFrom,
                        currencyTo,
                        et_currencyFrom.text.toString().toDouble()
                    )
                    withContext(Dispatchers.Main) {
                        et_currencyTo?.setText(resultObject?.result.toString())
                    }
                }

            }
        }
    }

    // this function runs after the text has been changed
    private fun onTextChanged()  {
        et_currencyFrom.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("main","beforeTextChanged")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("main","before Text has been changed")
            }

            override fun afterTextChanged(p0: Editable?) {
                try {
                    getAPI()
                } catch (e: Exception) {
                    Log.d("main", "$e")
                }
            }

        })
    }

    private fun spinnerSetup() {
        val spinner: Spinner = findViewById(R.id.spinner)
        val spinnerTwo: Spinner = findViewById(R.id.two_spinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.selection_list,
            android.R.layout.simple_spinner_item
        )
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                spinner.adapter = adapter

            }
        ArrayAdapter.createFromResource(
            this,
            R.array.selection_list,
            android.R.layout.simple_spinner_item
        )
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                spinnerTwo.adapter = adapter

            }
        spinner.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                currencyFrom = p0?.getItemAtPosition(p2).toString()

                // call api

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        })
        spinnerTwo.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                currencyTo = p0?.getItemAtPosition(p2).toString()
                // call api

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        })
    }

//    @Composable
//    fun Greeting(name: String, modifier: Modifier = Modifier) {
//        Surface(color = Color.Black) {
//            Text(
//                text = "Hello $name!",
//                color = Color.Red,
//                modifier = modifier.padding(16.dp)
//            )
//        }
//    }
//
//
//    @Preview(showBackground = true)
//    @Composable
//    fun GreetingPreview() {
//        MyApplicationTheme {
//            Greeting("Android")
//        }
//    }
}
