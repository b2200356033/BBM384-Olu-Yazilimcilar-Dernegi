package com.example.oyd.Fragments.DepartmentManagerPageFragments

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.oyd.API.RetrofitClient
import com.example.oyd.Models.FileDB
import com.example.oyd.R
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File
import java.io.FileOutputStream


class UploadResourcesFragment : Fragment() {
    private val PICK_FILE_REQUEST_CODE = 123
    //create department manager
    private lateinit var email: String
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

        if (addFileButton != null)
        {
            addFileButton.setOnClickListener {
                selectFile()
            }
        }
        else {
            println("addFileButton is null")
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
                println(fileName)

                // Dosya seçildiğinde buraya girecek
                val inputStream = requireActivity().contentResolver.openInputStream(fileUri)
                val fileBytes = inputStream?.readBytes()
                val fileDB = FileDB(null,getFileName(fileUri),fileBytes)

                // Dosyayı API'ye göndermek için işleme devam edebilirsiniz
                if (fileBytes != null) {
                    sendFileToApi(fileDB)
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
    private fun sendFileToApi(fileDB: FileDB) {
        println("sendfile api")
        val gson=Gson()
        val json=gson.toJson(fileDB)
        println(json)
        try {
            val coroutineScope= CoroutineScope(Dispatchers.IO)
            val job=coroutineScope.launch {
                val call = RetrofitClient.instance.apiAddFileToDepartmentManagerByEmail(email!!,fileDB)

                if (call.isSuccessful) {
                    println(fileDB)
                    println("file added")
                    //  Toast.makeText(requireContext(),"Instructor assigned",Toast.LENGTH_SHORT).show()
                }
                else{
                    println("file error")
                    //Toast.makeText(requireContext(),"Instructor not assigned",Toast.LENGTH_SHORT).show()

                }
            }
            runBlocking {
                job.join()
            }
        }
        catch (e:Exception){
            println("error Exception")
            println(e)
            // Toast.makeText(requireContext(),"Error",Toast.LENGTH_SHORT).show()
        }
            //downloadFile(fileDB)


        // Dosya gönderme işlemini burada yapabilirsiniz
    }


}