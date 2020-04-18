package com.soen390.conumap.IndoorNavigation

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.soen390.conumap.R
import com.soen390.conumap.SVGConverter.ConverterToFloorPlan
import com.soen390.conumap.SVGConverter.ImageAdapter
import com.soen390.conumap.building.Floor
import com.soen390.conumap.databinding.IndoorSearchFragmentBinding
import com.soen390.conumap.helper.ContextPasser
import kotlinx.android.synthetic.main.activity_indoor.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class IndoorActivity : AppCompatActivity() {
    lateinit var db: IndoorDatabaseHelper
    private lateinit var  binding: IndoorSearchFragmentBinding
//    private val searchAdapter = SearchAdapter()

    lateinit var list: ListView
    lateinit var adapter: AdapterClass
    lateinit var searchStartingRoom: SearchView
    lateinit var searchDestinationRoom: SearchView
    lateinit var searchQueries: ArrayList<String>

    lateinit var  startingRoom: String
    lateinit var  startingCoor: Node

    lateinit var  destinationRoom: String
    lateinit var  destinationCoor: Node

    var arraylist: ArrayList<SearchQuery> = ArrayList<SearchQuery>()

    lateinit var floorConverter: ConverterToFloorPlan
    lateinit var tempBitmap: Bitmap
    lateinit var floorP: Floor.FloorPlan
    lateinit var pathArray: Array<Node>

    val viewModel: IndoorSearchViewModel by viewModels{
        IndoorSearchViewModel.Factory(assets, Dispatchers.IO)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_indoor)

        ContextPasser.setContextIndoor(this)
        floorConverter = ConverterToFloorPlan
        tempBitmap = floorConverter.svgToBitMap() as Bitmap

        imageRecycler.layoutManager = LinearLayoutManager(this)
        imageRecycler.adapter = ImageAdapter(R.drawable.h9floorplan, arrayOf())

        searchQueries = arrayListOf("H-823", "H-845", "H-859",
            "H-803", "Washroom(Men, 8+)", "Washroom(Women, 8+)", "Water Fountain(8)", "Vending Machine(8)",
            "Washroom(Men, 9+)", "Washroom(Women, 9+)", "Water Fountain(9)", "Vending Machine(9)", "H-963", "H-961-7", "H-929", "H-907")

        GlobalScope.launch {
            // Floorplan
            floorP = floorConverter.convertToPlan(tempBitmap)
        }

        list = findViewById(R.id.list_view);
        for (element in searchQueries) {
            var searchQuery1:SearchQuery = SearchQuery(element)
            // Binds all strings into an array
            arraylist.add(searchQuery1)
        }
        adapter = AdapterClass(this, arraylist)
        list.setAdapter(adapter)
        searchStartingRoom = findViewById(R.id.search_StartRoom)
        searchDestinationRoom = findViewById(R.id.search_DestinationRoom)


        list.visibility= View.GONE

        // Demo so people can see how to use the database.
        db = IndoorDatabaseHelper(this)


        //Listening to the Search StartingRoom
        searchStartingRoom.setOnQueryTextListener(object:
            OnQueryTextListener {
                //WHen user click on enter
                override fun onQueryTextSubmit(startingQuery: String): Boolean {
                    list.visibility= View.GONE
                    startingRoom = startingQuery

                    startingCoor = db.getRoomCoordinates(startingQuery)
                    Log.i("Starting Room IS: ",startingCoor.toString())

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

        //Listening to the Search StartingRoom
        searchDestinationRoom.setOnQueryTextListener(object:
            OnQueryTextListener {
            //WHen user click on enter
            override fun onQueryTextSubmit(destinationQuery: String): Boolean {
                list.visibility= View.GONE
                destinationRoom = destinationQuery

                destinationCoor = db.getRoomCoordinates(destinationQuery)
                Log.i("DESTINATION IS: ",destinationCoor.toString())

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

    fun routeIndoor(view:View){
        val routeIndoorButton = findViewById<View>(R.id.indoorSubmitButton)

        floorConverter = ConverterToFloorPlan
        tempBitmap = floorConverter.svgToBitMap() as Bitmap



            var blockRow: ArrayList<Int> = arrayListOf()
            var blockCol: ArrayList<Int> = arrayListOf()

            for (array in floorP.floorNodes) {
                for (value in array) {
                    if (value.walkable == true) {
                    } else {
                        blockCol.add(value.xInd)
                        blockRow.add(value.yInd)
                    }
                }
            }

            var blockArray = arrayOf(blockCol, blockRow)

            var pathfinding: Pathfinding = Pathfinding(floorP.floorNodes[0].size, floorP.floorNodes.size, startingCoor, destinationCoor)

            pathfinding.loadMap()
            pathfinding.loadBlocks(blockArray)
            var path: ArrayList<Node> = pathfinding.findPath()
            pathArray = arrayOfNulls<Node>(path.size) as Array<Node>
            path.toArray(pathArray)
        // showIndoorPath(R.drawable.h8floorplan, pathArray)
    }

    fun showIndoorPath(resource: Int, indoorPath: Array<Node>) {
        imageRecycler.adapter = ImageAdapter(resource, indoorPath)
    }

    fun h9Button(view: View){
        val h9button = findViewById<View>(R.id.hfloor_nine_button)
        h9button.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorPrimary, null))
        val h8button = findViewById<View>(R.id.hfloor_eight_button)
        h8button.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.buttonColor, null))
        imageRecycler.adapter = ImageAdapter(R.drawable.h9floorplan, arrayOf())
    }

    fun h8Button(view: View){
        val h9button = findViewById<View>(R.id.hfloor_nine_button)
        h9button.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.buttonColor, null))
        val h8button = findViewById<View>(R.id.hfloor_eight_button)
        h8button.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorPrimary, null))
        imageRecycler.adapter = ImageAdapter(R.drawable.h8floorplan, pathArray)
    }
}


