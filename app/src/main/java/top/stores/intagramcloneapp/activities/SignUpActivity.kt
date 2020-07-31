package top.stores.intagramcloneapp.activities

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*
import top.stores.intagramcloneapp.R

class SignUpActivity : AppCompatActivity() {
   private lateinit var signInLinkButton :Button
    private lateinit var signUpBtn :Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signInLinkButton = findViewById<Button>(R.id.sign_in_link_btn)
        signUpBtn = findViewById<Button>(R.id.sign_up_btn)

        signInLinkButton.setOnClickListener {
            startActivity(Intent(this,SignInActivity::class.java))
        }

        signUpBtn.setOnClickListener {
            createAccount()
        }
    }

    private fun createAccount() {
        val fullName = full_name_signup.text.toString()
        val userName = username_signup.text.toString()
        val email = email_signup.text.toString()
        val passWord = password_signup.text.toString()

      when{
          TextUtils.isEmpty(fullName) -> Toast.makeText(this, "full name is required. ", Toast.LENGTH_LONG ).show()
          TextUtils.isEmpty(userName) -> Toast.makeText(this, "user name is required. ", Toast.LENGTH_LONG ).show()
          TextUtils.isEmpty(email) -> Toast.makeText(this, "email is required. ", Toast.LENGTH_LONG ).show()
          TextUtils.isEmpty(passWord) -> Toast.makeText(this, "password is required. ", Toast.LENGTH_LONG ).show()


          else -> {
              val progrssDialog = ProgressDialog(this@SignUpActivity)
              progrssDialog.setTitle("SignUp")
              progrssDialog.setMessage("Please wait, this may take a while..")
              progrssDialog.setCanceledOnTouchOutside(false)
              progrssDialog.show()


              val mAuth : FirebaseAuth = FirebaseAuth.getInstance()

              mAuth.createUserWithEmailAndPassword(email, passWord)
                  .addOnCompleteListener{task ->
                      if(task.isSuccessful){
                           saveUserInfo(fullName, userName, email, progrssDialog)
                      }
                      else{
                          val message = task.exception!!.toString()
                          Toast.makeText(this, "Error : $message", Toast.LENGTH_LONG).show()
                          mAuth.signOut()
                          progrssDialog.dismiss()
                      }

                  }

          }

      }




    }

    private fun saveUserInfo(fullName: String, userName: String, email: String, progrssDialog : ProgressDialog) {
        val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
        val userRef : DatabaseReference = FirebaseDatabase.getInstance().reference.child("Users")

        val userMap = HashMap<String, Any>()
        userMap["uid"] = currentUserId
        userMap["fullName"] = currentUserId
        userMap["userName"] = currentUserId
        userMap["email"] = currentUserId
        userMap["bio"] = "Hi welcome to Instagram.."
        userMap["image"] = "https://firebasestorage.googleapis.com/v0/b/instagramcloneapp-d7903.appspot.com/o/Default%20Images%2Fprofile.png?alt=media&token=fc669cc6-0e81-4dcc-be66-0c56556559ce"

       userRef.child(currentUserId).setValue(userMap)
           .addOnCompleteListener { task ->
               if (task.isSuccessful){
                   progrssDialog.dismiss()
                   Toast.makeText(this, "Account has been created successfully. ", Toast.LENGTH_LONG ).show()

                   val intent = Intent(this@SignUpActivity, MainActivity::class.java)
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