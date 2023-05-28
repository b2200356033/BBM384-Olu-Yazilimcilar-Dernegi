package com.example.oyd.Fragments.DepartmentManagerPageFragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.oyd.API.RetrofitClient
import com.example.oyd.Models.FileDB
import com.example.oyd.R
import com.example.oyd.Users.DepartmentManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

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

        /*val coroutineScope= CoroutineScope(Dispatchers.IO)
        val job=coroutineScope.launch {
            val call = RetrofitClient.instance.apiGetDepartmentManagerFromServer(email!!)
            val body = call.body()
            if (body != null) {
                println("department manager found")
                departmentManager = body
                Toast.makeText(this@UploadResourcesFragment.requireContext(),"Department Manager found",Toast.LENGTH_SHORT).show()
            }
            else{
                println("department manager not found")
                Toast.makeText(this@UploadResourcesFragment.requireContext(),"Department Manager not found",Toast.LENGTH_SHORT).show()
            }

        }
        runBlocking {
            job.join()
        }*/
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
                val fileDB = FileDB(12,fileName,"asd".toByteArray())

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
        val coroutineScope= CoroutineScope(Dispatchers.IO).launch{
            val call = RetrofitClient.instance.apiAddFileToDepartmentManager(email,fileDB)
            /*val body = call.body()
            if (body != null) {
                println("File added")
                // Toast.makeText(this@UploadResourcesFragment.requireContext(),"Instructors found",Toast.LENGTH_SHORT).show()
            }
            else{
                println("File not added")
               // Toast.makeText(this@UploadResourcesFragment.requireContext(),"No instructors found",Toast.LENGTH_SHORT).show()
            }*/

        }

        // Dosya gönderme işlemini burada yapabilirsiniz
    }


}