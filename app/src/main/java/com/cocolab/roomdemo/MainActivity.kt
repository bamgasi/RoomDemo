package com.cocolab.roomdemo

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cocolab.roomdemo.databinding.ActivityMainBinding
import com.cocolab.roomdemo.db.Subscriber
import com.cocolab.roomdemo.db.SubscriberDatabase
import com.cocolab.roomdemo.db.SubscriberRepository

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val dao = SubscriberDatabase.getInstance(application).subscriberDAO
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository)
        subscriberViewModel = ViewModelProvider(this, factory).get(SubscriberViewModel::class.java)

        binding.myViewModel = subscriberViewModel
        binding.lifecycleOwner = this
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        displaySucscribersList()
    }

    private fun displaySucscribersList() {
        subscriberViewModel.subscribers.observe(this, {
            Log.i("MYTAG", it.toString())
            binding.recyclerView.adapter = MyRecyclerViewAdapter(it) { selectedItem: Subscriber ->
                listItemClicked(selectedItem)
            }
        })
    }

    private fun listItemClicked(subscriber: Subscriber) {
        Toast.makeText(this, "select name: ${subscriber.name}", Toast.LENGTH_SHORT).show()
        subscriberViewModel.initUpdateAndDelete(subscriber)
    }
}