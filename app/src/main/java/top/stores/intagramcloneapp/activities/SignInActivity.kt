package top.stores.intagramcloneapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import top.stores.intagramcloneapp.R

class SignInActivity : AppCompatActivity() {
    private lateinit var  signUpLinkButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        signUpLinkButton = findViewById<Button>(R.id.sign_up_link_btn)

        signUpLinkButton.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }
    }
}