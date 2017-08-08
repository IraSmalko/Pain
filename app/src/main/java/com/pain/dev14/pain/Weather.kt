package com.pain.dev14.pain

/**
 * Created by dev14 on 04.08.17.
 */
data class Condition(var temp: Int)

data class Item(val condition: Condition)
data class Channel(val title: String, val item: Item)
data class Results(val channel: Channel)
data class Query(val results: Results)
data class Weather(val query: Query) {
    var temp: Int
        get() = query.results.channel.item.condition.temp
        set(value) {
            query.results.channel.item.condition.temp = value
        }
    var title: String = ""
        get() = query.results.channel.title
}