package the.most.timeapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.*
import java.io.IOException
import android.os.AsyncTask.execute
import okhttp3.RequestBody
import android.os.AsyncTask.execute
import android.os.AsyncTask.execute
import okhttp3.RequestBody.create


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        testConnection()

       //this.textView.text = res

        button.setOnClickListener {
            doPostRequest()
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
        }
        button2.setOnClickListener {
            val mainIntent1 = Intent(this, StepCounterActivity::class.java)
            startActivity(mainIntent1)
        }
    }

    private val client = OkHttpClient()
//    fun testConnection(): String? {
//        val request = Request.Builder()
//            .url("http://192.168.0.102:53884/api/test")
//            .build()
//
//        try {
//            val response = client.newCall(request).execute()
//            return "= " + response.body()?.string()
//        } catch (e: Exception) {
//            return "!"+ e.cause + '\n' + e.localizedMessage
//        }
//        return "Something wrong"
//    }

    fun testConnection(){
        val request = Request.Builder()
            .url("http://192.168.0.102:53884/api/test")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) = dr(response.body()?.string())
        })
    }

    fun dr(s: String?)
    {
        textView.text = s
    }

    fun bowlingJson(player1: String): String {
        return ("{'name':'$player1'}")
    }

    val JSON = MediaType.parse("application/json; charset=utf-8")
    var json = bowlingJson("Jesse")

    @Throws(IOException::class)
    fun doPostRequest() {
        val body = create(JSON, "\"" + editText.text.toString() + "\"")
        val request = Request.Builder()
            .url("http://192.168.0.102:53884/api/test")
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) = dr(response.body()?.string())
        })
    }
}
