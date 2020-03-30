package com.soen390.conumap.IndoorNavigation

class NodeComparator {

    companion object : Comparator<Node> {
        override fun compare(a: Node, b: Node): Int = when {
            a.f != b.f -> a.f - b.f
            else -> a.f.compareTo(b.f)
        }
    }

}
