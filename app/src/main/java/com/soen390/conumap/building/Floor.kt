package com.soen390.conumap.building

class Floor(buildingCode: String, floorNumber: Int, floorPlan: FloorPlan) {

    class FloorNode(yInd: Int, xInd: Int, color: String, id: String, walkable: Boolean, target: Boolean) { }
    class FloorPlan(floorNodes: Array<Array<FloorNode>>) { }
}