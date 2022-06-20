package co.edu.unab.mgads.jdcn.applicht.view

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import co.edu.unab.mgads.jdcn.applicht.R
import co.edu.unab.mgads.jdcn.applicht.viewModel.SingUpActivityViewModel
import co.edu.unab.mgads.jdcn.applicht.databinding.ActivitySingUpBinding

class SingUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingUpBinding
    private lateinit var viewModel:SingUpActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sing_up)
        viewModel= ViewModelProvider(this)[SingUpActivityViewModel::class.java]

        binding.viewModel=viewModel

        binding.SingUpBtRegister.setOnClickListener {
            viewModel.singUp().observe(this) {}
            it?.let {
                login(it)
            } ?: run {
                Toast.makeText(applicationContext, "Error al crear el Usuario", Toast.LENGTH_SHORT).show()
            }
        }

        binding.SingUpBtVolver.setOnClickListener {
            finish()
        }
    }

    private fun login (it: View){
        val preferences: SharedPreferences = getSharedPreferences("LichtApp.pref", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putBoolean("login", true)
        editor.apply()

        val intentlogin: Intent = Intent(applicationContext, ListLampActivity::class.java)
        intentlogin.apply {
            putExtra("message", "Hola")
            putExtra("data", viewModel.user.name)
        }
        startActivity(intentlogin)
        Toast.makeText(this, "Iniciando sesion...", Toast.LENGTH_SHORT).show()
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