package com.example.oyd.Fragments.DepartmentManagerPageFragments

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oyd.API.RetrofitClient
import com.example.oyd.Models.FileDB
import com.example.oyd.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DownloadResourcesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
interface DosyaItemClickListener {
    fun onDosyaItemClick(dosya: FileDB)
}
class DownloadResourcesFragment : Fragment(), DosyaItemClickListener  {
    private lateinit var recyclerView: RecyclerView
    private lateinit var dosyaAdapter: DosyaAdapter
    private lateinit var dosyalar: List<FileDB>

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
        recyclerView = view.findViewById(R.id.recyclerViewDosyalar)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Department Manager'a ait dosyaları almak için gerekli kodu buraya ekleyin
        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitClient.instance.apiGetDepartmentManagerFilesFromServer(email!!)
            if (response.isSuccessful) {
                response.body()?.let {
                    dosyalar = it!!
                    dosyaAdapter = DosyaAdapter(dosyalar, this@DownloadResourcesFragment)
                    recyclerView.adapter = dosyaAdapter
                }
            }
            println(dosyalar.size)
        }

        return view

    }
    override fun onDosyaItemClick(dosya: FileDB) {
        showDownloadDialog(dosya)
    }

    private fun showDownloadDialog(dosya: FileDB) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Dosya İndirme")
            .setMessage("Dosyayı indirmek istiyor musunuz?")
            .setPositiveButton("Evet") { dialog, _ ->
                dialog.dismiss()
                downloadFile(dosya)
            }
            .setNegativeButton("Hayır") { dialog, _ ->
                dialog.dismiss()
            }
        builder.create().show()
    }

    private fun downloadFile(dosya: FileDB) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE
            )
        } else {
            startDownload(dosya)
        }
    }

    private fun startDownload(dosya: FileDB) {
        val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(directory, dosya.file_name)

        // Dosyayı indirme işlemini gerçekleştirmek için gerekli kodu buraya ekleyin

        Toast.makeText(requireContext(), "Dosya indirildi: ${file.absolutePath}", Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // İzin verildi, dosya indirme işlemini tekrar başlatın
                //startDownload(selectedDosya)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Dosya indirme izni reddedildi.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE = 1001
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user= context?.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        val email= user?.getString("email","")


    }


    inner class DosyaAdapter(
        private val dosyalar: List<FileDB>,
        private val itemClickListener: DosyaItemClickListener
    ) : RecyclerView.Adapter<DosyaAdapter.DosyaViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DosyaViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.fragment_download_resources, parent, false)
            return DosyaViewHolder(view)
        }

        override fun onBindViewHolder(holder: DosyaViewHolder, position: Int) {
            val dosya = dosyalar[position]
            holder.bind(dosya)
        }

        override fun getItemCount(): Int {
            return dosyalar.size
        }

        inner class DosyaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener {

            private val txtDosyaAdi: TextView = itemView.findViewById(R.id.fileName)

            init {
                itemView.setOnClickListener(this)
            }

            fun bind(dosya: FileDB) {
                txtDosyaAdi.text = dosya.file_name
            }

            override fun onClick(view: View) {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val dosya = dosyalar[position]
                    itemClickListener.onDosyaItemClick(dosya)
                }
            }
        }


    }

    }
