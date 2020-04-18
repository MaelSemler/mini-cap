package com.soen390.conumap.IndoorNavigation

import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.soen390.conumap.IndoorNavigation.helperSearch.SearchAdapter
import com.soen390.conumap.R
import com.soen390.conumap.SVGConverter.ImageAdapter
import com.soen390.conumap.databinding.IndoorSearchFragmentBinding
import com.soen390.conumap.helper.ContextPasser
import kotlinx.android.synthetic.main.activity_indoor.*
import kotlinx.coroutines.Dispatchers

class IndoorActivity : AppCompatActivity() {
    lateinit var db: IndoorDatabaseHelper
    private lateinit var  binding: IndoorSearchFragmentBinding
//    private val searchAdapter = SearchAdapter()

    lateinit var list: ListView
    lateinit var adapter: AdapterClass
    lateinit var editsearch: SearchView
    lateinit var searchQueries: ArrayList<String>
    var arraylist: ArrayList<SearchQuery> = ArrayList<SearchQuery>()


    val viewModel: IndoorSearchViewModel by viewModels{
        IndoorSearchViewModel.Factory(assets, Dispatchers.IO)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_indoor)

        ContextPasser.setContextIndoor(this)

        imageRecycler.layoutManager = LinearLayoutManager(this)
        imageRecycler.adapter = ImageAdapter()

        searchQueries = arrayListOf("H-823", "H-845", "H-859",
            "H-803", "Washroom(Men, 8+)", "Washroom(Women, 8+)", "Water Fountain(8)", "Vending Machine(8)",
            "Washroom(Men, 9+)", "Washroom(Women, 9+)", "Water Fountain(9)", "Vending Machine(9)", "H-963", "H-961-7", "H-929", "H-907")


        list = findViewById(R.id.list_view);
        for (element in searchQueries) {
            var searchQuery1:SearchQuery = SearchQuery(element)
            // Binds all strings into an array
            arraylist.add(searchQuery1)
        }
        adapter = AdapterClass(this, arraylist)
        list.setAdapter(adapter)
        editsearch = findViewById(R.id.search_view)

        list.visibility= View.GONE

        editsearch.setOnQueryTextListener(object:
            OnQueryTextListener {
            @Override
            override fun onQueryTextSubmit(query: String): Boolean {
                list.visibility= View.GONE
                return false;
            }

            @Override
            override fun onQueryTextChange(newText: String): Boolean {
                list.visibility= View.VISIBLE
                var text = newText;
                adapter.filter(text);
                return false;
            }
        }
                )

//            }





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

                    db.insertData("H",
            "9",
            "937",
            "H-937",
            "10",
            "12"
        )
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


//    private fun handleSearchResult(it: SearchResult) {
//        when (it) {
//            is ValidResult -> {
//                binding.otherResultText.visibility = View.GONE
//                binding.searchResult.visibility = View.VISIBLE
//                searchAdapter.submitList(it.result)
//            }
//            is ErrorResult -> {
//                searchAdapter.submitList(emptyList())
//                binding.otherResultText.visibility = View.VISIBLE
//                binding.searchResult.visibility = View.GONE
//                binding.otherResultText.setText("Errors")
//            }
//            is EmptyResult -> {
//                searchAdapter.submitList(emptyList())
//                binding.otherResultText.visibility = View.VISIBLE
//                binding.searchResult.visibility = View.GONE
//                binding.otherResultText.setText("Empty Results")
//            }
//            is EmptyQuery -> {
//                searchAdapter.submitList(emptyList())
//                binding.otherResultText.visibility = View.VISIBLE
//                binding.searchResult.visibility = View.GONE
//                binding.otherResultText.setText("Not enough char")
//            }
//            is TerminalError -> {
//                // Something wen't terribly wrong!
//                println("Our Flow terminated unexpectedly, so we're bailing!")
//                Toast.makeText(
//                    this,
//                    "Unexpected error in SearchRepository!",
//                    Toast.LENGTH_SHORT
//                ).show()
//                finish()
//            }
//        }
//    }

}


