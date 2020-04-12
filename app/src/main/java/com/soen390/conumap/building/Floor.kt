package com.soen390.conumap.building

class Floor(var buildingCode: String, var floorNumber: Int, var floorPlan: FloorPlan) {

    class FloorNode(yInd: Int, xInd: Int, color: String, id: String, walkable: Boolean, target: Boolean) { }
    class FloorPlan(floorNodes: Array<Array<FloorNode>>) { }
}