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
import the.most.timeapp.models.TimeEventSpan


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val z = TimeEventSpan()
        z.Color = "fdfd"
        z.Begin = "2:30"
        z.End = "3:40"
        z.EventName = "Eventzz"
        doPostTimeEventSpanRequest(z)

        button.setOnClickListener {
            textView.text = loginToJSON(editText.text.toString())
            doPostLoginRequest(editText.text.toString())
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
        }
        button2.setOnClickListener {
            val mainIntent1 = Intent(this, StepCounterActivity::class.java)
            startActivity(mainIntent1)
        }
    }
}
