package com.example.oyd.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.oyd.Models.Course
import com.example.oyd.Models.FileDB
import com.example.oyd.R
import java.util.Base64

class DownloadFilesAdapter( private val files: ArrayList<FileDB>) :
    RecyclerView.Adapter<DownloadFilesAdapter.FileViewHolder>() {
    var onDownloadClicked: ((FileDB) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.file_view_item, parent, false)
        return FileViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val file = files[position]
        holder.bind(file)
        holder.downloadBtn.setOnClickListener {
            onDownloadClicked?.invoke(file)
            Toast.makeText(holder.itemView.context, "Downloading ${file.file_name}", Toast.LENGTH_SHORT).show()
        }


    }
    override fun getItemCount(): Int {
        return files.size
    }
    inner class FileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var nameTextView: TextView = itemView.findViewById(R.id.fileNameTitle)
        var sizeTextView: TextView = itemView.findViewById(R.id.fileSize)
        var typeTextView: TextView = itemView.findViewById(R.id.fileType)
        var downloadBtn: Button = itemView.findViewById(R.id.downloadButton)

        fun bind(file: FileDB) {
            nameTextView.isSelected = true
            nameTextView.text = file.file_name
            typeTextView.text = file.file_name!!.substringAfterLast(".")
            sizeTextView.text = getFormattedSize(Base64.getDecoder().decode(file.file))

        }


        fun getFormattedSize(byteArray: ByteArray): String {
            val sizeInBytes = byteArray.size.toLong()
            val sizeInKB = sizeInBytes / 1024.0
            val sizeInMB = sizeInKB / 1024.0

            return when {
                sizeInMB >= 1.0 -> "%.2f MB".format(sizeInMB)
                sizeInKB >= 1.0 -> "%.2f KB".format(sizeInKB)
                else -> "$sizeInBytes bytes"
            }
        }
    }

}