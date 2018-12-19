package the.most.timeapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import the.most.timeapp.models.TimeEventSpan


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_login)
            button.setOnClickListener {
                val mainIntent = Intent(this, MainActivity::class.java)
                startActivity(mainIntent)
            }
        }
        catch (ex:Exception)
        {
            Toast.makeText(this,ex.toString(),Toast.LENGTH_LONG)
        }
    }
}
