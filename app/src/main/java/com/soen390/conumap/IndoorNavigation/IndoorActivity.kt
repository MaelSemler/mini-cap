package com.soen390.conumap.IndoorNavigation

import android.graphics.Bitmap
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.soen390.conumap.R
import com.soen390.conumap.SVGConverter.ConverterToFloorPlan
import com.soen390.conumap.SVGConverter.ImageAdapter
import com.soen390.conumap.building.Floor
import com.soen390.conumap.helper.ContextPasser
import kotlinx.android.synthetic.main.activity_indoor.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.floor

class IndoorActivity : AppCompatActivity() {
    lateinit var db: IndoorDatabaseHelper

    //SearchQueries
    lateinit var suggestionList: ListView
    lateinit var adapter: AdapterClass
    lateinit var searchStartingRoom: SearchView
    lateinit var searchDestinationRoom: SearchView
    lateinit var searchQueries: ArrayList<String>

    lateinit var  startingRoom: String
    lateinit var  startingCoor: Node

    lateinit var  destinationRoom: String
    lateinit var  destinationCoor: Node

    var arraylist: ArrayList<SearchQuery> = ArrayList<SearchQuery>()
    var listDigit = listOf("0","1","2","3","4","5","6","7","8","9")

    //Conversion to FloorPlan for Indoor Algorithm
    lateinit var h8floorConverter: ConverterToFloorPlan
    lateinit var h9floorConverter: ConverterToFloorPlan
    lateinit var h8BitMap: Bitmap
    lateinit var h9BitMap: Bitmap
    lateinit var h8floorP: Floor.FloorPlan
    lateinit var h9floorP: Floor.FloorPlan
    lateinit var pathArray: Array<Node>

    val viewModel: IndoorSearchViewModel by viewModels{
        IndoorSearchViewModel.Factory(assets, Dispatchers.IO)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_indoor)

        ContextPasser.setContextIndoor(this)
        h8floorConverter = ConverterToFloorPlan
        h9floorConverter = ConverterToFloorPlan
        h8BitMap = h8floorConverter.svgToBitMap(R.raw.hall8) as Bitmap
        h9BitMap = h9floorConverter.svgToBitMap(R.raw.hall9) as Bitmap

        // Show H9 by default.
        imageRecycler.layoutManager = LinearLayoutManager(this)
        imageRecycler.adapter = ImageAdapter(R.drawable.h9floorplan, arrayOf())

        GlobalScope.launch {
            h8floorP = h8floorConverter.convertToPlan(h8BitMap)
            h9floorP = h9floorConverter.convertToPlan(h9BitMap)
        }

        //Bind Views with their IDs
        suggestionList = findViewById(R.id.list_view)
        searchStartingRoom = findViewById(R.id.search_StartRoom)
        searchDestinationRoom = findViewById(R.id.search_DestinationRoom)

        searchQueries = arrayListOf("H-823", "H-845", "H-859",
            "H-803", "Washroom (Men, 8)", "Washroom (Women, 8)", "Water Fountain (8)", "Vending Machine (8)",
            "Washroom (Men, 9)", "Washroom (Women, 9)", "Water Fountain (9)", "Vending Machine (9)", "H-963", "H-961-7", "H-929", "H-907")

        for (element in searchQueries) {
            var searchQuery1:SearchQuery = SearchQuery(element)
            // Binds all strings into an array
            arraylist.add(searchQuery1)
        }

        adapter = AdapterClass(this, arraylist)
        suggestionList.adapter = adapter

        //Default settings for Searchfiels
        searchDestinationRoom.isSubmitButtonEnabled = true
        searchStartingRoom.isSubmitButtonEnabled = true
        suggestionList.visibility= View.GONE

        val floorConverter = ConverterToFloorPlan

        db = IndoorDatabaseHelper(this)

        //Method that would set the corresponding SearchView bar with the clicked item
        fun clickItem(searchB: SearchView ){
            suggestionList.setOnItemClickListener { parent, view, i, id ->
                val item:SearchQuery = suggestionList.adapter.getItem(i) as SearchQuery

                searchB.setQuery(item.getQuery().toString(),true)
                suggestionList.visibility = View.GONE

            }
        }

        //**************SearchBar Starting Room*****************

        //When Closed
        searchStartingRoom.setOnCloseListener(object: SearchView.OnCloseListener{
            override fun onClose(): Boolean {
                suggestionList.visibility = View.GONE
                return false
            }

        })

        //Listening to the Search StartingRoom
        searchStartingRoom.setOnQueryTextListener(object:
            OnQueryTextListener {
            //WHen user click on enter
            override fun onQueryTextSubmit(startingQuery: String): Boolean {
                suggestionList.visibility= View.GONE
                startingRoom = startingQuery

                if(startingRoom.startsWith("H-")){
                    startingCoor = db.getRoomCoordinates(startingQuery)
                }
                else{
                    var floorValIndex = startingRoom.indexOfAny(listDigit,0,true)
                    var floorNum = startingRoom[floorValIndex].toString().toInt()
                    Log.d("FloorVal is: ", startingRoom[floorValIndex].toString())
                    destinationCoor = db.getPOICoordinates(startingRoom,floorNum)
                }
                Log.i("Starting Room IS: ",startingCoor.toString())
                return false
            }

            //When The searchbar textfield is changing
            override fun onQueryTextChange(newText: String): Boolean {
                suggestionList.visibility= View.VISIBLE
                var text = newText;
                adapter.filter(text);
                clickItem(searchStartingRoom)

                if (TextUtils.isEmpty(newText)){
                    suggestionList.visibility = View.GONE
                }
                return false
            }
        })

        //**************SearchBar Destination Room*****************

        //When Closed
        searchDestinationRoom.setOnCloseListener(object: SearchView.OnCloseListener{
            override fun onClose(): Boolean {
                suggestionList.visibility = View.GONE
                return false
            }
        })

        //Listener on the Destination searchfield
        searchDestinationRoom.setOnQueryTextListener(object:
            OnQueryTextListener {

            //WHen user click on enter
            override fun onQueryTextSubmit(destinationQuery: String): Boolean {
                suggestionList.visibility= View.GONE
                destinationRoom = destinationQuery
                if(destinationRoom.startsWith("H-")){
                    destinationCoor = db.getRoomCoordinates(destinationQuery)
                    Log.d("DESTINATION IS: ", destinationRoom)
                }
                else{

                    var floorValIndex = destinationRoom.indexOfAny(listDigit,0,true)
                    var floorNum = destinationRoom[floorValIndex].toString().toInt()
                    Log.d("FloorVal is: ", destinationRoom[floorValIndex].toString())
                    destinationCoor = db.getPOICoordinates(destinationRoom,floorNum)
                    Log.d("Destination: ", destinationCoor.toString())
                }
                return false;
            }

            //When The searchbar textfield is changing
            override fun onQueryTextChange(newText: String): Boolean {
                suggestionList.visibility= View.VISIBLE
                adapter.filter(newText);
                clickItem(searchDestinationRoom)
                if (TextUtils.isEmpty(newText)){
                    suggestionList.visibility = View.GONE
                }
                return false;
            }
        })
    }

    fun routeIndoor(view:View) {
        val floorNumber = Regex("[0-9]").find(startingRoom)?.value

        // Show Toast when trying to go between floors.
        if(floorNumber!= Regex("[0-9]").find(destinationRoom)?.value) {
            var betweenFloorsError = Toast.makeText(
                applicationContext,
                "Please select rooms on the same floor.",
                Toast.LENGTH_LONG
            )
            betweenFloorsError.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
            betweenFloorsError.show()

            return
        }

        // Initialize variables depending on which floor the navigation is on.
        var currentFloorPlan = Floor.FloorPlan(arrayOf<Array<Floor.FloorNode>>())
        var indoorMapResource = -1
        if (floorNumber != null) {
            when(floorNumber.toInt()) {
                8 -> {
                    currentFloorPlan = h8floorP
                    indoorMapResource = R.drawable.h8floorplan
                }
                9 -> {
                    currentFloorPlan = h9floorP
                    indoorMapResource = R.drawable.h9floorplan
                }
            }
            changeFloorButtonEnabled(floorNumber.toInt())
        }

        // Run pathfinding algorithm.
        var blockRow: ArrayList<Int> = arrayListOf()
        var blockCol: ArrayList<Int> = arrayListOf()

        for (array in currentFloorPlan.floorNodes) {
            for (value in array) {
                if (value.walkable == true) {
                } else {
                    blockCol.add(value.xInd)
                    blockRow.add(value.yInd)
                }
            }
        }

        var blockArray = arrayOf(blockCol, blockRow)

        var pathfinding: Pathfinding = Pathfinding(currentFloorPlan.floorNodes[0].size, currentFloorPlan.floorNodes.size, startingCoor, destinationCoor)

        pathfinding.loadMap()
        pathfinding.loadBlocks(blockArray)
        var path: ArrayList<Node> = pathfinding.findPath()

        // Retrieve path from algorithm.
        pathArray = arrayOfNulls<Node>(path.size) as Array<Node>
        path.toArray(pathArray)

        // Display the path.
        showIndoorPath(indoorMapResource, pathArray)
    }

    fun showIndoorPath(resource: Int, indoorPath: Array<Node>) {
        imageRecycler.adapter = ImageAdapter(resource, indoorPath)
    }

    fun changeFloorButtonEnabled(floorNumber: Int) {
        val h9button = findViewById<View>(R.id.hfloor_nine_button) as Button
        val h8button = findViewById<View>(R.id.hfloor_eight_button) as Button

        when(floorNumber) {
            8 -> {
                h8button.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorPrimary, null))
                h8button.setTextColor(ResourcesCompat.getColor(resources, R.color.buttonColor, null))
                h9button.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.buttonColor, null))
                h9button.setTextColor(ResourcesCompat.getColor(resources, R.color.black, null))
            }
            9 -> {
                h9button.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorPrimary, null))
                h9button.setTextColor(ResourcesCompat.getColor(resources, R.color.buttonColor, null))
                h8button.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.buttonColor, null))
                h8button.setTextColor(ResourcesCompat.getColor(resources, R.color.black, null))
            }
        }
    }

    fun h8Button(view: View){
        changeFloorButtonEnabled(8)
        imageRecycler.adapter = ImageAdapter(R.drawable.h8floorplan, arrayOf())
    }

    fun h9Button(view: View){
        changeFloorButtonEnabled(9)
        imageRecycler.adapter = ImageAdapter(R.drawable.h9floorplan, arrayOf())
    }
}
