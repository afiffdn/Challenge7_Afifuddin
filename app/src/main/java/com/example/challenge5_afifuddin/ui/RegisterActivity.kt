package com.example.challenge5_afifuddin.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.challenge5_afifuddin.databinding.ActivityRegisterBinding
import com.example.challenge5_afifuddin.room.Database
import com.example.challenge5_afifuddin.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RegisterActivity : AppCompatActivity() {
    lateinit var binding:ActivityRegisterBinding
    var dB: Database? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.btnDaftar.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val repassword = binding.etRepassword.text.toString()
            when {
                username.isEmpty() -> {
                    binding.etUsername.error = " input username"
                }
                email.isEmpty() -> {
                    binding.etEmail.error = " input email"
                }
                password.isEmpty() -> {
                    binding.etPassword.error = " input password"
                }
                repassword.isEmpty() -> {
                    binding.etRepassword.error = " input repassword"
                }
                password.lowercase() != repassword.lowercase() ->{
                    Toast.makeText(this, "wrong repassword", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    register()
                }
            }

        }
    }

    private fun register() {
        dB = Database.getInstance(this)
        val username = binding.etUsername.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val obejctUser = User(null, username, email, password,null,"no_image")

        lifecycleScope.launch(Dispatchers.IO) {
            val result = dB?.user()?.insertUser(obejctUser)
            runBlocking(Dispatchers.Main) {
                if (result != 0.toLong()) {

                    Toast.makeText(
                        this@RegisterActivity,
                        "Success add ${obejctUser.username}",
                        Toast.LENGTH_SHORT
                    )
                        .show()

                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                } else {
                    Toast.makeText(
                        this@RegisterActivity,
                        "failed add ${obejctUser.username}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }
}