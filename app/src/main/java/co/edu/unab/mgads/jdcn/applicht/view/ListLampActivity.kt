package co.edu.unab.mgads.jdcn.applicht.view

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import co.edu.unab.mgads.jdcn.applicht.R
import co.edu.unab.mgads.jdcn.applicht.databinding.ActivityListLampBinding
import co.edu.unab.mgads.jdcn.applicht.model.entity.Lamp
import co.edu.unab.mgads.jdcn.applicht.viewModel.ListLampActivityViewModel

class ListLampActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListLampBinding
    private lateinit var viewModel:ListLampActivityViewModel

    lateinit var adapter: LampAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var bundle:Bundle? = intent.extras
        var message:String? = bundle?.getString("message")
        var data:String? = intent.getStringExtra("data")

        title="$message $data"
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list_lamp)
        viewModel= ViewModelProvider(this)[ListLampActivityViewModel::class.java]

        adapter= LampAdapter(arrayListOf())
        binding.adapter=adapter

        loadLamps()

        adapter.onItemClickListener={
            Toast.makeText(applicationContext, it.name, Toast.LENGTH_SHORT).show()
            val intentDetail = Intent(applicationContext, DetailLampActivity::class.java)
            intentDetail.putExtra("lampKey",it.key)
            intentDetail.putExtra("lampId",it.id)
            startActivity(intentDetail)
        }

        adapter.onItemLongClickListener={
            viewModel.deleteLamps(it).observe(this){
            }
            Toast.makeText(applicationContext,"Lampara ${it.name} eliminada", Toast.LENGTH_SHORT).show()
        }

        binding.ListBtLamp.setOnClickListener {
            startActivity(Intent(applicationContext, FormLampActivity::class.java))
        }
    }

    private fun loadLamps(){
        viewModel.products.observe(this){
            adapter.refresh(it as ArrayList<Lamp>)
        }
    }

    override fun onResume() {
        super.onResume()
    }

}