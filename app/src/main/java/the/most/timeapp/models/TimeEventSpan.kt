package the.most.timeapp.models

import java.sql.Time

public class TimeEventSpan {
    public var Id: Int = 0
    public var UserId: Int = 0
    public lateinit var UserName: String
    public lateinit var Begin: String
    public lateinit var End: String
    public lateinit var EventName: String
    public lateinit var Color: String
}