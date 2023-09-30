import android.util.Log
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

object ExchangeRatesApi {
    private const val API_KEY = "2cNl5awTsXwokpmA1NeryUhOWCWfiX4V"
    private const val FIXER_API_KEY="2cNl5awTsXwokpmA1NeryUhOWCWfiX4V"

    suspend fun getExchangeRate(from: String, to: String, amount: Number): ExchangeRates? {
        Log.d("main","valueof currencty from is ${from}and ${to}");
        try {
            val url = "https://api.apilayer.com/exchangerates_data/convert?" +
                    "&to=$to" +
                    "&from=$from" +
                    "&amount=$amount"+
                    "&apikey=$API_KEY"
            Log.d("main", "api is givne bt ${url}");
            val connection = URL(url).openConnection() as HttpURLConnection
            val response = connection.inputStream.bufferedReader().readText()
            val jsonObject = JSONObject(response)
            val conversionRate = jsonObject.getJSONObject("info").getDouble("rate")
            return ExchangeRates(conversionRate, jsonObject.getDouble("result"))
        } catch (e: Exception) {
            Log.e("main", "$e")
            return null
        }
    }
}

data class ExchangeRates(val conversionRate: Double,val result:Double)

