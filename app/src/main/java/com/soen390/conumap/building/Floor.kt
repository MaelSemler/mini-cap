package com.soen390.conumap.building

class Floor(var buildingCode: String, var floorNumber: Int, var floorPlan: FloorPlan) {

    class FloorNode(var yInd: Int, var xInd: Int, var color: String, var id: String, var walkable: Boolean, var target: Boolean) { }
    class FloorPlan(var floorNodes: Array<Array<FloorNode>>) { }
}
