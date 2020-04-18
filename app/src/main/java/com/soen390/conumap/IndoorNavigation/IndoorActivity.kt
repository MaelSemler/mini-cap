package com.soen390.conumap.IndoorNavigation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
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
    lateinit var searchBar: SearchView
    lateinit var searchQueries: ArrayList<String>
    var arraylist: ArrayList<SearchQuery> = ArrayList<SearchQuery>()


    val viewModel: IndoorSearchViewModel by viewModels{
        IndoorSearchViewModel.Factory(assets, Dispatchers.IO)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_indoor)

        //img rec. adapter. switchfloor

        ContextPasser.setContextIndoor(this)

        imageRecycler.layoutManager = LinearLayoutManager(this)
        imageRecycler.adapter = ImageAdapter(R.drawable.h9floorplan)

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
        searchBar = findViewById(R.id.search_view)

        list.visibility= View.GONE




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


        //Listening to the searchbar
        searchBar.setOnQueryTextListener(object:
            OnQueryTextListener {
                //WHen user click on enter
                override fun onQueryTextSubmit(startingQuery: String): Boolean {
                    list.visibility= View.GONE
                    Log.d("TESTING:", startingQuery)
                    return false;
                }
                //When The searchbar textfield is changing
                override fun onQueryTextChange(newText: String): Boolean {
                    list.visibility= View.VISIBLE
                    var text = newText;
                    adapter.filter(text);
                    return false;
                }
            }
        )




    }

    fun h9Button(view: View){
        val h9button = findViewById<View>(R.id.hfloor_nine_button)
        h9button.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorPrimary, null))
        val h8button = findViewById<View>(R.id.hfloor_eight_button)
        h8button.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.buttonColor, null))
        imageRecycler.adapter = ImageAdapter(R.drawable.h9floorplan)
    }

    fun h8Button(view: View){
        val h9button = findViewById<View>(R.id.hfloor_nine_button)
        h9button.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.buttonColor, null))
        val h8button = findViewById<View>(R.id.hfloor_eight_button)
        h8button.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorPrimary, null))
        imageRecycler.adapter = ImageAdapter(R.drawable.h8floorplan)
    }
}


