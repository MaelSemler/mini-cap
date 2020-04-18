package com.soen390.conumap.IndoorNavigation

class SearchQuery {

    private var query:String

    constructor(query: String){
        this.query = query
    }

    fun getQuery(): String? {
        return query
    }

    fun setQuery(query: String?) {
        this.query = query!!
    }


}
