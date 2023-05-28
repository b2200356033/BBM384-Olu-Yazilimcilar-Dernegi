package com.example.oyd.Fragments.DepartmentManagerPageFragments

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
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
import com.example.oyd.Models.FileDB
import com.example.oyd.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class DownloadResourcesFragment : Fragment()  {
    private lateinit var recyclerView: RecyclerView
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
        val view=inflater.inflate(R.layout.fragment_download_resources, container, false)
        val user= context?.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        val email= user?.getString("email","")


        return view

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user= context?.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        val email= user?.getString("email","")


    }

    fun downloadFile(fileDB: FileDB) {
        // Dosya indirme işlemi kodu burada yer alır
        var fileDownloaded=downloadFile(requireContext(), fileDB.file!!, fileDB.file_name!!)
        val channelId = "download_channel"
        val notificationBuilder = NotificationCompat.Builder(requireContext(), channelId)
            .setSmallIcon(R.drawable.ic_download)
            .setContentTitle("Dosya İndirildi")
            .setContentText("Dosya adı: ${fileDB.file_name}")
            .setAutoCancel(false) // Bildirimin otomatik kapanmamasını sağlar

// Dosya açma işlemi için PendingIntent oluşturma
        val file = fileDownloaded // Açılacak dosyanın yolunu belirtin
        val fileUri = FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.fileprovider",
            file
        )
        val intent = Intent(Intent.ACTION_VIEW)


        val extension = MimeTypeMap.getFileExtensionFromUrl(fileUri.toString()) // Uzantıyı alın
        val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension) // Uzantıya göre dosya türünü alın

        intent.setDataAndType(fileUri, mimeType)

        //intent.setDataAndType(fileUri, "image/png") // Dosya türüne uygun olarak ayarlayın
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
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel)
            }
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
    // İndirme işlemini gerçekleştiren fonksiyon
    fun downloadFile(context: Context, byteArray: ByteArray, fileName: String):File {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Android 10 ve üzeri için
            val resolver = context.contentResolver
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, "Downloaded_$fileName")
                put(MediaStore.MediaColumns.MIME_TYPE, "*/*") // Dosya türüne göre değiştirin
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

    }
