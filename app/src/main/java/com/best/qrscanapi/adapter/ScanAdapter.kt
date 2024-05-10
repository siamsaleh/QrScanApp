package com.best.qrscanapi.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.best.qrscanapi.R
import com.best.qrscanapi.model.retrofit.ScanModel

class ScanAdapter(private val scanList: List<ScanModel>) :
    RecyclerView.Adapter<ScanAdapter.ScanViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScanViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_scan, parent, false)
        return ScanViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScanViewHolder, position: Int) {
        val scanModel = scanList[position]
        holder.bind(scanModel)
    }

    override fun getItemCount(): Int {
        return scanList.size
    }

    class ScanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val qrCodeTextView: TextView = itemView.findViewById(R.id.qrCodeTextView)
        private val idTextView: TextView = itemView.findViewById(R.id.idTextView)
        private val scanTimeTextView: TextView = itemView.findViewById(R.id.scanTimeTextView)
        private val typeTextView: TextView = itemView.findViewById(R.id.typeTextView)

        @SuppressLint("SetTextI18n")
        fun bind(scanModel: ScanModel) {
            qrCodeTextView.text = "QR Code: ${scanModel.qrCode}"
            idTextView.text = "ID: ${scanModel.id}"
            scanTimeTextView.text = "Scan Time: ${scanModel.scanTime}"
            typeTextView.text = "Type: ${scanModel.type}"
        }
    }
}