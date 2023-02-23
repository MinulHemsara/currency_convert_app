package lk.nibm.hdse221020


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    lateinit var coin :TextInputEditText
    lateinit var convertbtn : Button
    lateinit var requestQueue: RequestQueue

    val currencies = arrayOf("GBP","AUD","EUR","ÏNR","JPY")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        coin = findViewById(R.id.coin)
        convertbtn = findViewById(R.id.convertbtn)

        requestQueue = Volley.newRequestQueue(this)

        convertbtn.setOnClickListener {
            val amount = coin.text.toString().toDouble()

            val url =
                "https://api.frankfurter.app/latest?amount="+amount+"&from=USD&to=GBP,AUD,EUR,INR,JPY"

            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                Response.Listener { response ->
                    val rates = response.getJSONObject("rates")
                    val gbp = rates.getDouble("GBP")
                    val aud = rates.getDouble("AUD")
                    val eur = rates.getDouble("EUR")
                    val inr = rates.getDouble("INR")
                    val jpy = rates.getDouble("JPY")

                    val message =
                        "GBP: ${gbp * amount}\nAUD: ${aud * amount}\nEUR: ${eur * amount}\nINR: ${inr * amount}\nJPY: ${jpy * amount}"
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                },
                Response.ErrorListener {
                    Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_LONG).show()
                })
            requestQueue.add(jsonObjectRequest)
    }}}
