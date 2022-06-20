package co.edu.unab.mgads.jdcn.applicht.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import co.edu.unab.mgads.jdcn.applicht.R
import co.edu.unab.mgads.jdcn.applicht.databinding.ActivityDetailLampBinding
import co.edu.unab.mgads.jdcn.applicht.model.entity.Lamp
import co.edu.unab.mgads.jdcn.applicht.viewModel.DetailLampActivityViewModel

class DetailLampActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailLampBinding
    private lateinit var viewModel: DetailLampActivityViewModel
    private var myLampKey: Int = 0
    private var myLampId: String? = ""
    private var lampKey = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        myLampKey=intent.getIntExtra("lamp_key",0)
        lampKey = intent.getIntExtra("lampKey",0)
        myLampId = intent.getStringExtra("lamp_id")

        binding= DataBindingUtil.setContentView(this,R.layout.activity_detail_lamp)
        viewModel= ViewModelProvider(this)[DetailLampActivityViewModel::class.java]

        myLampId?.let {viewModel.getProductById(it)}

        binding.lamp = Lamp()
        viewModel.lamp.observe(this){
            it?.let {
                binding.lamp=it
            }?:run {
                binding.lamp = Lamp()
            }
        }

        binding.DetailBtEdit.setOnClickListener {
            val intentForm= Intent(applicationContext, FormLampActivity::class.java)
            intentForm.apply {
                putExtra("product", binding.lamp)
            }
            startActivity(intentForm)
        }

        binding.DetailBtReturn.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        myLampId?.let {viewModel.getProductById(it)}
        super.onResume()
    }
}
