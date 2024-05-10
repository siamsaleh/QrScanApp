package com.best.qrscanapi.activity

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import android.Manifest
import android.content.Intent
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.best.qrscanapi.R
import com.best.qrscanapi.databinding.ActivityMainBinding
import com.best.qrscanapi.model.retrofit.ScanModel
import com.best.qrscanapi.viewmodel.MainViewModel
import com.journeyapps.barcodescanner.BarcodeResult


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    private lateinit var scanList: List<ScanModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setContentView(binding.root)

        loadLiveData()
        observeLiveData()

        binding.scanButton.setOnClickListener {
            toggleScannerVisibility()
        }

        binding.scannedListButton.setOnClickListener{
            startActivity(Intent(this, ScannedListActivity::class.java))
        }
    }

    private fun loadLiveData() {
        viewModel.loadScanList()
    }

    private fun observeLiveData() {
        viewModel.getScanList().observe(this){
            scanList = it
            Log.d("BARCODE", "observeLiveData: ${scanList[0].qrCode}")
        }
    }

    private var barcodeCallback: BarcodeCallback = object : BarcodeCallback {
        override fun barcodeResult(result: BarcodeResult?) {
            binding.barcodeScanner.pause()
            val scannedCode = result?.text.toString()
            showResultDialog(scannedCode)

            binding.barcodeScanner.visibility = View.GONE // Hide the BarcodeView after scanning

            Log.d("BARCODE", "barcodeResult: $scannedCode")
        }

        override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) {}
    }

    private fun showResultDialog(scannedCode: String) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Scanned Code")
        alertDialogBuilder.setMessage(scannedCode)
        alertDialogBuilder.setPositiveButton("OK") { dialog, which ->
            // Resume scanning when dialog is dismissed
            binding.barcodeScanner.resume()
        }
        alertDialogBuilder.setOnDismissListener {
            // Resume scanning when dialog is dismissed
            binding.barcodeScanner.resume()
        }
        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun toggleScannerVisibility() {
        if (binding.barcodeScanner.visibility == View.VISIBLE) {
            binding.barcodeScanner.visibility = View.GONE
        } else {
            binding.barcodeScanner.visibility = View.VISIBLE
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                binding.barcodeScanner.resume()
                binding.barcodeScanner.decodeSingle(barcodeCallback)
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            binding.barcodeScanner.resume()
            binding.barcodeScanner.decodeSingle(barcodeCallback)
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
        }
    }

    override fun onPause() {
        super.onPause()
        binding.barcodeScanner.pause()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                binding.barcodeScanner.resume()
                binding.barcodeScanner.decodeSingle(barcodeCallback)
            }
        }
    }

    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 100
    }
}