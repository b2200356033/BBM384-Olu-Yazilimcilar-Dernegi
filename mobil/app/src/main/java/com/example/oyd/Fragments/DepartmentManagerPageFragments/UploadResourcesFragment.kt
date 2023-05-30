package com.example.oyd.Fragments.DepartmentManagerPageFragments
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.oyd.API.RetrofitClient
import com.example.oyd.Models.FileDB
import com.example.oyd.R
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.Base64


class UploadResourcesFragment : Fragment() {
    private val PICK_FILE_REQUEST_CODE = 123
    private lateinit var progressBar: ProgressBar
    private var isUploading = false
    var error=true
    private lateinit var email: String
    private lateinit var textViewSelectedFile: TextView
    private lateinit var fileDB: FileDB
    private lateinit var uploadingText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val user= context?.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        email= user?.getString("email","")!!

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upload_resources, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val addFileButton= view.findViewById<Button>(R.id.addFileButton)
        textViewSelectedFile=view.findViewById<TextView>(R.id.selectedFile)
        val uploadFileButton=view.findViewById<Button>(R.id.uploadFileButton)

        if (addFileButton != null)
        {
            addFileButton.setOnClickListener {
                selectFile()
            }
        }
        else {
        }
        if (uploadFileButton != null)
        {
            uploadFileButton.setOnClickListener {
                if(::fileDB.isInitialized.not()){
                    println("fileDB is null")
                    Toast.makeText(requireContext(),"Please select a file", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
                val coroutineScope = CoroutineScope(Dispatchers.Main)
                val job = coroutineScope.launch {
                    try {
                        withContext(Dispatchers.Main) {
                            startFileUpload()
                        }

                        withContext(Dispatchers.Default) {
                            val call = RetrofitClient.instance.apiAddFileToDepartmentManagerByEmail(email!!, fileDB)
                            error = !call.isSuccessful
                        }
                    } catch (e: Exception) {
                        println("Exception: $e")
                        error=true
                        Toast.makeText(requireContext(), "Exception: $e", Toast.LENGTH_LONG).show()
                    }
                }
                job.invokeOnCompletion {
                    onFileUploadComplete()
                    showStatus(error)
                    // İşlem tamamlandığında veya iptal edildiğinde yapılacak işlemler
                }
            }
        }
        else {
            Toast.makeText(requireContext(), "uploadFileButton is null", Toast.LENGTH_LONG).show()
        }
        progressBar = view.findViewById(R.id.progressBar)
        progressBar.visibility = View.GONE
        progressBar.setOnTouchListener { _, _ -> true }
        uploadingText = view.findViewById(R.id.uploadingText)
        //uploadingText.visibility = View.GONE

    }
    private fun startFileUpload() {
        progressBar.visibility = View.VISIBLE
        uploadingText.visibility = View.VISIBLE
        requireActivity().window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        isUploading = true
    }
    private fun onFileUploadComplete() {
        isUploading = false
        progressBar.visibility = View.GONE
        uploadingText.visibility = View.INVISIBLE
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }
    override fun onResume() {
        super.onResume()

        if (isUploading) {
            progressBar.visibility = View.VISIBLE
            uploadingText.visibility = View.VISIBLE
        }
    }

    private fun selectFile() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        startActivityForResult(intent, PICK_FILE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { fileUri ->
                //get file name
                val fileName = getFileName(fileUri)
                // Dosya seçildiğinde buraya girecek
                val inputStream = requireActivity().contentResolver.openInputStream(fileUri)
                val fileBytes = inputStream?.readBytes()
                val fileBytesString= Base64.getEncoder().encodeToString(fileBytes)
                fileDB = FileDB(null,getFileName(fileUri),fileBytesString)
                // Dosyayı API'ye göndermek için işleme devam edebilirsiniz
                if (fileBytes != null) {
                    textViewSelectedFile.text=fileName
                }
            }
        }
    }
    private fun getFileName(uri: Uri): String? {
        var fileName: String? = null
        val cursor: Cursor? = context?.contentResolver?.query(uri, null, null, null, null)
        cursor?.let {
            if (it.moveToFirst()) {
                val displayNameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (displayNameIndex != -1) {
                    fileName = it.getString(displayNameIndex)
                }
            }
            cursor.close()
        }
        return fileName
    }

    fun showStatus(error: Boolean){
        val text:String
        val dialogBinding:View
        if (!error)
        {
            dialogBinding = layoutInflater.inflate(R.layout.succesfull_page, null)
            text="File uploaded successfully"
        }
        else{
            dialogBinding = layoutInflater.inflate(R.layout.error_page, null)
            text="File upload failed"
        }
        val myDialog = this.context?.let { it1 -> Dialog(it1) }
        myDialog?.setContentView(dialogBinding)
        myDialog?.setCancelable(true)
        var textView=myDialog?.findViewById<TextView>(R.id.message)
        textView?.text=text
        myDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog?.show()
        object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }
            override fun onFinish() {
                myDialog?.dismiss()
            }
        }.start()
    }


}