package the.most.timeapp

import android.database.sqlite.SQLiteDatabase
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.*
import org.json.JSONObject
import the.most.timeapp.models.TimeEventSpan
import java.io.IOException
import org.json.JSONArray

val _spanList: ArrayList<TimeEventSpan> = arrayListOf()

private val client = OkHttpClient()
private val JSON = MediaType.parse("application/json; charset=utf-8")



fun testConnection(){
    val request = Request.Builder()
        .url("http://127.0.0.1:53884/api/test")
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {}
        override fun onResponse(call: Call, response: Response) {} //= dr(response.body()?.string())
    })
}

@Throws(IOException::class)
fun doPostTimeEventSpanRequest(span: TimeEventSpan) {
    if(_spanList.count() > 0){
        _spanList.add(span)
        doPostTimeEventSpanListRequest()
    }
    else {
        val body = RequestBody.create(JSON, TimeEventSpanToJSON(span))
        val request = Request.Builder()
            .url("http://192.168.0.102:53884/api/TimeEventSpan")
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                _spanList.add(span)
            }

            override fun onResponse(call: Call, response: Response) {}
        })
    }
}

@Throws(IOException::class)
fun doPostTimeEventSpanListRequest() {
    val body = RequestBody.create(JSON, TimeEventSpanListToJSON())
    val request = Request.Builder()
        .url("http://192.168.0.102:53884/api/TimeEventSpanList")
        .post(body)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {}
        override fun onResponse(call: Call, response: Response) { _spanList.clear() }
    })
}

@Throws(IOException::class)
fun doPostLoginRequest(login: String) {
    val body = RequestBody.create(JSON, loginToJSON(login))
    val request = Request.Builder()
        .url("http://192.168.0.102:53884/api/login")
        .post(body)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {}
        override fun onResponse(call: Call, response: Response) {}
    })
}

private fun TimeEventSpanToJSON(span: TimeEventSpan): String {
    var jsonObj = TimeEventSpanObj(span)
    return jsonObj.toString()
}

private fun TimeEventSpanObj(span: TimeEventSpan): JSONObject {
    var jsonObj = JSONObject()
    jsonObj.put("EventName", span.EventName)
    jsonObj.put("Begin", span.Begin)
    jsonObj.put("End", span.End)
    jsonObj.put("Color", span.Color)
    return jsonObj
}

 fun TimeEventSpanListToJSON(): String {
    var jsonObject = JSONObject()
    val jsonArr = JSONArray()

    _spanList.forEach {
        var jsonObj = JSONObject()
        jsonObj.put("EventName", it.EventName)
        jsonObj.put("Begin", it.Begin)
        jsonObj.put("End", it.End)
        jsonObj.put("Color", it.Color)
        jsonArr.put(jsonObj)
    }
     jsonObject.put("list", jsonArr)

    return jsonObject.toString()
}

fun loginToJSON(login: String): String {
    return "\""+ login+"\""

}