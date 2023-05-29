package com.example.oyd.Fragments.DepartmentManagerPageFragments

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.MimeTypeMap
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oyd.API.RetrofitClient
import com.example.oyd.Adapters.DownloadFilesAdapter
import com.example.oyd.Models.FileDB
import com.example.oyd.R
import com.example.oyd.Users.DepartmentManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URLConnection
import java.util.Base64

class DownloadResourcesFragment : Fragment()  {
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var progressText: TextView
    private lateinit var departmentManager: DepartmentManager
    private var isUploading = false
    var error=true
    private val PICK_FILE_REQUEST_CODE = 123
    private val STORAGE_PERMISSION_REQUEST_CODE = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_download_resources, container, false)


    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user= context?.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        val id= user?.getString("id","")
        recyclerView=view.findViewById(R.id.recyclerViewDownload)
        progressBar=view.findViewById(R.id.progressBar)
        progressText=view.findViewById(R.id.progressText)

        val coroutineScope = CoroutineScope(Dispatchers.Main)
        val job = coroutineScope.launch {
            try {
                withContext(Dispatchers.Main) {
                    startFileUpload()
                }

                withContext(Dispatchers.Default) {
                    val call = RetrofitClient.instance.apiGetDepartmentManagerFilesFromServer(301)

                    if (call.isSuccessful) {
                        println("file added")
                        error = false
                        var listFileDb=call.body()
                        var encodedFile= Base64.getDecoder().decode(listFileDb?.get(0)?.file)
                        println(encodedFile)
                        listFileDb?.get(0)?.let { downloadFile(it) }
                        //println(departmentManager)
                        // Toast.makeText(requireContext(),"Instructor assigned",Toast.LENGTH_SHORT).show()
                    } else {
                        println("file error")
                        println(call.errorBody())
                        println(call.headers())
                        println(call.raw())
                        error = true
                        // Toast.makeText(requireContext(),"Instructor not assigned",Toast.LENGTH_SHORT).show()
                    }
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

        }
       // recyclerView.adapter=DownloadFilesAdapter(requireContext(),email!!)
    }

    fun downloadFile(fileDB: FileDB) {
        // Dosya indirme işlemi kodu burada yer alır
        var fileDownloaded=downloadFile(requireContext(), Base64.getDecoder().decode(fileDB.file), "DMF_${fileDB.file_name!!}")
        val channelId = "download_channel"
        val notificationBuilder = NotificationCompat.Builder(requireContext(), channelId)
            .setSmallIcon(R.drawable.ic_download)
            .setContentTitle("Dosya İndirildi")
            .setContentText("Dosya adı: ${fileDownloaded.name}")
            .setAutoCancel(true) // Bildirimin otomatik kapanmamasını sağlar

// Dosya açma işlemi için PendingIntent oluşturma
        val file = fileDownloaded // Açılacak dosyanın yolunu belirtin
        val fileUri = FileProvider.getUriForFile(
            this@DownloadResourcesFragment.requireContext(),
            "${this@DownloadResourcesFragment.requireContext().packageName}.fileprovider",
            file
        )

        println(fileUri)
        val intent = Intent(Intent.ACTION_VIEW)


        val extension = MimeTypeMap.getFileExtensionFromUrl(fileUri.toString()) // Uzantıyı alın
        val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension) // Uzantıya göre dosya türünü alın

        intent.setDataAndType(fileUri, mimeType)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // İzin verilerini ekle
        val pendingIntent = PendingIntent.getActivity(
            requireContext(),
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

// Bildirim kanalı oluşturma
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Dosya İndirme Bildirimi",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "Dosya indirme durumu"
            val notificationManager =
                ContextCompat.getSystemService(requireContext(), NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }

// Bildirim gönderme
        notificationBuilder.setContentIntent(pendingIntent)
        val notificationManager = NotificationManagerCompat.from(requireContext())
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notificationManager.notify(0, notificationBuilder.build())


    }
    fun getMimeType(byteArray: ByteArray): String? {
        val inputStream: InputStream = ByteArrayInputStream(byteArray)
        val contentType: String? = URLConnection.guessContentTypeFromStream(inputStream)
        return contentType
    }
    // İndirme işlemini gerçekleştiren fonksiyon
    fun downloadFile(context: Context, byteArray: ByteArray, fileName: String):File {
        //find file type

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Android 10 ve üzeri için
            val resolver = context.contentResolver
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                put(MediaStore.MediaColumns.MIME_TYPE, getMimeType(byteArray)) // Dosya türüne göre değiştirin
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            }

            val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
            uri?.let {
                resolver.openOutputStream(it)?.use { outputStream ->
                    outputStream.write(byteArray)
                    outputStream.flush()
                }
            }
        } else {
            // Android 9 ve öncesi için
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // İzin yoksa izin iste
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    STORAGE_PERMISSION_REQUEST_CODE
                )
            } else {
                // İzin varsa dosyayı kaydet
                saveFileToExternalStorage(context, byteArray, fileName)
            }
        }
        //return file
        return File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),fileName)
    }
    // Dosyayı harici depolama alanına kaydet
    private fun saveFileToExternalStorage(context: Context, byteArray: ByteArray, fileName: String) {
        val downloadsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(downloadsDirectory, fileName)
        val fileOutputStream = FileOutputStream(file)
        fileOutputStream.write(byteArray)
        fileOutputStream.flush()
        fileOutputStream.close()
    }
    private fun startFileUpload() {
        println("startFileUpload in function")
        progressBar.visibility = View.VISIBLE
        progressText.visibility = View.VISIBLE
        requireActivity().window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        isUploading = true
        println("startFileUpload in function completed")
    }
    private fun onFileUploadComplete() {
        println("onFileUploadComplete in function")
        isUploading = false
        progressBar.visibility = View.GONE
        progressText.visibility = View.INVISIBLE
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }
    override fun onResume() {
        super.onResume()

        if (isUploading) {
            progressBar.visibility = View.VISIBLE
            progressText.visibility = View.VISIBLE
        }
    }
    fun showStatus(error: Boolean){
        val text:String
        val dialogBinding:View
        if (!error)
        {
            dialogBinding = layoutInflater.inflate(R.layout.succesfull_page, null)
            text="Getting file is succesfull"
        }
        else{
            dialogBinding = layoutInflater.inflate(R.layout.error_page, null)
            text="Getting file is failed"
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
