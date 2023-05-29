package com.example.oyd.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.oyd.Models.FileDB
import com.example.oyd.R

class DownloadFilesAdapter(private val context: Context, private val files: List<FileDB>) :
    RecyclerView.Adapter<DownloadFilesAdapter.FileViewHolder>() {

    private var onItemClickListener: ((FileDB) -> Unit)? = null
    private var onDeleteBtnClickListener: ((FileDB) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val inflater = LayoutInflater.from(context)
        val view =inflater.inflate(R.layout.file_view_item, parent, false)
        return FileViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val file = files[position]
        holder.bind(file)
        //creates a custom dialog popup saying if you want to add this file
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(file)
        }

    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
    class FileViewHolder(itemView: View, private val context: Context) : RecyclerView.ViewHolder(itemView) {

        private val nameTextView: TextView = itemView.findViewById(R.id.fileNameTitle)
        private val sizeTextView: TextView = itemView.findViewById(R.id.fileSize)
        private val typeTextView: TextView = itemView.findViewById(R.id.fileType)
        private val downloadBtn: Button = itemView.findViewById(R.id.downloadButton)
        fun bind(file: FileDB) {
            nameTextView.text = file.file_name
         //   sizeTextView.text = getFormattedSize(file.file!!)
            typeTextView.text = file.file_name!!.substringAfterLast(".")


            downloadBtn.setOnClickListener {
                //download file
            }
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