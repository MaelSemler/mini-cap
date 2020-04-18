package com.soen390.conumap.IndoorNavigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.soen390.conumap.IndoorNavigation.helperSearch.SearchAdapter
import com.soen390.conumap.R
import com.soen390.conumap.SVGConverter.ImageAdapter
import com.soen390.conumap.SVGConverter.ConverterToFloorPlan
import com.soen390.conumap.databinding.FragmentIndoorBinding
import com.soen390.conumap.databinding.IndoorSearchFragmentBinding
import com.soen390.conumap.helper.ContextPasser
import kotlinx.android.synthetic.main.activity_indoor.*
import kotlinx.android.synthetic.main.indoor_search_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class IndoorActivity : AppCompatActivity() {
    lateinit var db: IndoorDatabaseHelper
    private lateinit var  binding: IndoorSearchFragmentBinding
    private val searchAdapter = SearchAdapter()

    val viewModel: IndoorSearchViewModel by viewModels{
        IndoorSearchViewModel.Factory(assets, Dispatchers.IO)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_indoor)
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_indoor)
//        binding.searchResult.adapter = searchAdapter
//        viewModel.searchResult.observe(this){handleSearchResult(it)}


//        setContentView(R.layout.activity_indoor)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.title = resources.getString(R.string.indoor_nav)

        ContextPasser.setContextIndoor(this)

        //Empty search
//        searchAdapter.submitList(emptyList())
//        binding.otherResultText.visibility = View.VISIBLE
//        binding.searchResult.visibility= View.GONE
//        binding.otherResultText.setText("Not enough Chars")
//        binding.searchText.requestFocus()
//
//        binding.searchText.doAfterTextChanged{
//            editable ->
//            lifecycleScope.launch{
//                viewModel.queryChannel.send(editable.toString())
//            }
//        }

        imageRecycler.layoutManager = LinearLayoutManager(this)
        imageRecycler.adapter = ImageAdapter()



//        val floorConverter = ConverterToFloorPlan
//
//        var tempBitmap = floorConverter.svgToBitMap()

//        GlobalScope.launch {
//
//            //@Andy Here is your floorplan
//            val floorP =  floorConverter.convertToPlan(tempBitmap)
//
////            Proof that this is workinggg
//            Log.i("TESTING: ",floorP.floorNodes[430][330].color)
//        }
        //        // Demo so people can see how to use the database.
        db = IndoorDatabaseHelper(this)

        db.emptyDatabaseContents()

        db.insertData("H", "9", "937", "H-937", "10", "12")
        db.insertData("H", "8", "801", "H-801", "5", "9")

        db.printDatabaseContents()

        var one = db.getRoomCoordinates("H-937") // This is [10, 12].
        var two = db.getRoomCoordinates("H-801") // This is [5, 9].


        db.insertData("H", "9", "937", "H-937", "10", "12")
        db.insertData("H", "8", "801", "H-801", "5", "9")
        db.insertData("H", "8", "WF", "Water Fountain", "6", "13")
        db.insertData("H", "9", "VM", "Vending Machine", "2", "8")

        var a = db.getRoomCoordinates("H-937")
        var b = db.getRoomCoordinates("H-801")
        var c = db.getPOICoordinates("Water Fountain", 8)
        var d = db.getPOICoordinates("Vending Machine", 9)
        var e = db.getPOICoordinates("Nonsense", 6) // Should be error.

        println(a.toString() + "\n" + b.toString() + "\n" + c.toString() + "\n" + d.toString() + "\n" + e.toString())


    }


    private fun handleSearchResult(it: SearchResult) {
        when (it) {
            is ValidResult -> {
                binding.otherResultText.visibility = View.GONE
                binding.searchResult.visibility = View.VISIBLE
                searchAdapter.submitList(it.result)
            }
            is ErrorResult -> {
                searchAdapter.submitList(emptyList())
                binding.otherResultText.visibility = View.VISIBLE
                binding.searchResult.visibility = View.GONE
                binding.otherResultText.setText("Errors")
            }
            is EmptyResult -> {
                searchAdapter.submitList(emptyList())
                binding.otherResultText.visibility = View.VISIBLE
                binding.searchResult.visibility = View.GONE
                binding.otherResultText.setText("Empty Results")
            }
            is EmptyQuery -> {
                searchAdapter.submitList(emptyList())
                binding.otherResultText.visibility = View.VISIBLE
                binding.searchResult.visibility = View.GONE
                binding.otherResultText.setText("Not enough char")
            }
            is TerminalError -> {
                // Something wen't terribly wrong!
                println("Our Flow terminated unexpectedly, so we're bailing!")
                Toast.makeText(
                    this,
                    "Unexpected error in SearchRepository!",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

}


