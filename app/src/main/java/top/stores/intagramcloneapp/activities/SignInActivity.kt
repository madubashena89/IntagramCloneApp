package top.stores.intagramcloneapp.activities

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*
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

        login_btn.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() {
        val email = email_login.text.toString()
        val passWord = password_login.text.toString()

        when {
            TextUtils.isEmpty(email) -> Toast.makeText(this, "email is required. ", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(passWord) -> Toast.makeText(this, "password is required. ", Toast.LENGTH_LONG).show()

            else-> {
                val progrssDialog = ProgressDialog(this@SignInActivity)
                progrssDialog.setTitle("Log in")
                progrssDialog.setMessage("Please wait, this may take a while..")
                progrssDialog.setCanceledOnTouchOutside(false)
                progrssDialog.show()


                val mAuth : FirebaseAuth = FirebaseAuth.getInstance()

                mAuth.signInWithEmailAndPassword(email, passWord).addOnCompleteListener {task->
                    if(task.isSuccessful){
                        val intent = Intent(this@SignInActivity, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    }else{
                        val message = task.exception!!.toString()
                        Toast.makeText(this, "Error : $message", Toast.LENGTH_LONG).show()
                        FirebaseAuth.getInstance().signOut()
                        progrssDialog.dismiss()
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        if(FirebaseAuth.getInstance().currentUser != null){
            val intent = Intent(this@SignInActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

}