package the.most.timeapp

import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.*
import org.json.JSONObject
import the.most.timeapp.models.TimeEventSpan
import java.io.IOException


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
        val body = RequestBody.create(JSON, TimeEventSpanToJSON(span))
        val request = Request.Builder()
            .url("http://192.168.0.102:53884/api/test")
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {}
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
        var jsonObj = JSONObject()
        jsonObj.put("EventName", span.EventName)
        jsonObj.put("Begin", span.Begin)
        jsonObj.put("End", span.End)
        jsonObj.put("Color", span.Color)

//        for (PhoneNumber pn : person.getPhoneList() ) {
//            JSONObject pnObj = new JSONObject();
//            pnObj.put("num", pn.getNumber());
//            pnObj.put("type", pn.getType());
//            jsonArr.put(pnObj);
//        jsonObj.put("phoneNumber", jsonArr);

        return jsonObj.toString()
    }

    public fun loginToJSON(login: String): String {
       // var jsonObj = JSONObject()
       // jsonObj.put("login",  login)

//        for (PhoneNumber pn : person.getPhoneList() ) {
//            JSONObject pnObj = new JSONObject();
//            pnObj.put("num", pn.getNumber());
//            pnObj.put("type", pn.getType());
//            jsonArr.put(pnObj);
//        jsonObj.put("phoneNumber", jsonArr);

        return "\""+ login+"\""

    }