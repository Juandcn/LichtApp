package co.edu.unab.mgads.jdcn.applicht.view

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import co.edu.unab.mgads.jdcn.applicht.R
import co.edu.unab.mgads.jdcn.applicht.databinding.ActivityMainBinding
import co.edu.unab.mgads.jdcn.applicht.viewModel.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var viewModel:MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val preferences = getSharedPreferences("LichtApp.pref", MODE_PRIVATE)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        binding.viewModel = viewModel

         if (preferences.getBoolean("login", false)) {
             goTolampList()
        }

        binding.LoginBtLogin.setOnClickListener {
            viewModel.login().observe(this) {
                it?.let {
                    loginPreference()
                } ?: run {
                    Toast.makeText(this, "Login inválido", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.LoginBtSingup.setOnClickListener {
                startActivity(Intent(applicationContext, SingUpActivity::class.java))
            }
        }

    fun loginPreference(){
        val preferences: SharedPreferences = getSharedPreferences("LichtApp.pref", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putBoolean("login", true)
        editor.putString("email", viewModel.user.email)
        editor.apply()
        goTolampList()
        Toast.makeText(this, "Iniciando sesion...", Toast.LENGTH_SHORT).show()
    }

    private fun goTolampList(){
        val preferences: SharedPreferences = getSharedPreferences("LichtApp.pref", MODE_PRIVATE)
        val intentlogin: Intent = Intent(applicationContext, ListLampActivity::class.java)
        startActivity(intentlogin)
        finish()
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

}