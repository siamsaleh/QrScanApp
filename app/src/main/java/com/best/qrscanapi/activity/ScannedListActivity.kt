package com.best.qrscanapi.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.best.qrscanapi.R
import com.best.qrscanapi.databinding.ActivityMainBinding
import com.best.qrscanapi.databinding.ActivityScanedListBinding
import com.best.qrscanapi.model.retrofit.ScanModel
import com.best.qrscanapi.viewmodel.MainViewModel

class ScannedListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScanedListBinding
    private lateinit var viewModel: MainViewModel

    private lateinit var scanList: List<ScanModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanedListBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setContentView(binding.root)

        loadLiveData()
        observeLiveData()
    }

    private fun loadLiveData() {
        viewModel.loadScanList()
    }

    private fun observeLiveData() {
        viewModel.getScanList().observe(this){
            scanList = it
        }
    }
}