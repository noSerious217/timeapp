package the.most.timeapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun onLoginClick(view: View)
    {
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
    }
}
