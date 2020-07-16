package top.stores.intagramcloneapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import top.stores.intagramcloneapp.R

class SignUpActivity : AppCompatActivity() {
   private lateinit var signInLinkButton :Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signInLinkButton = findViewById<Button>(R.id.sign_in_link_btn)
        signInLinkButton.setOnClickListener {
            startActivity(Intent(this,SignInActivity::class.java))
        }
    }
}