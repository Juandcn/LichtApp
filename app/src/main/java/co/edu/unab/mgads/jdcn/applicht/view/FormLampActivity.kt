package co.edu.unab.mgads.jdcn.applicht.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import co.edu.unab.mgads.jdcn.applicht.R
import co.edu.unab.mgads.jdcn.applicht.databinding.ActivityFormLampBinding
import co.edu.unab.mgads.jdcn.applicht.model.entity.Lamp
import co.edu.unab.mgads.jdcn.applicht.viewModel.FormLampActivityViewModel
import com.bumptech.glide.Glide
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class FormLampActivity : AppCompatActivity() {

    lateinit var binding:ActivityFormLampBinding
    lateinit var viewModel:FormLampActivityViewModel
    lateinit var resultGallery: ActivityResultLauncher<Intent>
    lateinit var resultCamera: ActivityResultLauncher<Intent>
    var photoUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var bundle:Bundle? = intent.extras
        title=bundle?.getString("title")

        var myLamp:Lamp?=intent.getSerializableExtra("product") as Lamp?
        binding= DataBindingUtil.setContentView(this,R.layout.activity_form_lamp)
        viewModel= ViewModelProvider(this)[FormLampActivityViewModel::class.java]
        //binding.viewModel=viewModel
        binding.lamp = myLamp

        myLamp?.let {
            binding.FormTvTitleAddLamp.text = "Editar ${it.name}"
            binding.FormBtRegister.text = "Editar"
            binding.FormBtRegister.setOnClickListener {
                viewModel.update(binding.lamp as Lamp).observe(this) { state ->
                    if (state) {
                        finish()
                    } else {
                        Toast.makeText(
                            applicationContext, "Problema al modificar la Lampara",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        } ?: run {
            myLamp = Lamp()
            binding.FormBtRegister.setOnClickListener {
                viewModel.add(binding.lamp as Lamp, photoUri).observe(this) { id ->
                    binding.FormBtRegister.isEnabled = true
                    if (id != "") {
                        finish()
                    } else {
                        Toast
                            .makeText(
                                applicationContext, "Problema al agregar la Lampara",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    }
                }
            }

            binding.lamp = myLamp

            resultGallery =registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) run {
                    photoUri = it.data!!.data!!
                    Glide.with(applicationContext).load(photoUri).into(binding.FormImImagenAddLamp)
                }
            }

            resultCamera =
                registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                    if (it.resultCode == RESULT_OK) run {
                        Glide.with(applicationContext).load(photoUri).into(binding.FormImImagenAddLamp)
                    }
                }

            binding.FormBtReturn.setOnClickListener {
                finish()
            }

            binding.FormImImagenGalleryAddLamp.setOnClickListener {
                val galleryItem =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                resultGallery.launch(galleryItem)
            }

            binding.FormImImagenCameraAddLamp.setOnClickListener {
                val cameraItem = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                var photoFile: File? = null
                try {
                    photoFile = createImage()
                } catch (e: IOException) {
                }

                photoFile?.let {
                    photoUri = FileProvider.getUriForFile(
                        applicationContext,"co.edu.unab.mgads.jdcn.applicht.fileprovider",photoFile)
                    cameraItem.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                    resultCamera.launch(cameraItem)
                }
            }
        }
    }

    private fun createImage(): File? {
        var timeStamp = SimpleDateFormat("yyyy-MM-dd-HHmmss", Locale.US).format(Date())
        val storeAppDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(timeStamp, ".jpg",storeAppDir)
    }
}