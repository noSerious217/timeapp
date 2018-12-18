using ServakApplication.Models;
using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SQLite;
using System.IO;
using System.Linq;
using System.Threading.Tasks;

namespace ServakApplication
{
    public static class SQLiteController
    {
        private static String dbFileName = "themostdb.sqlite";
        private static SQLiteConnection m_dbConn;
        private static SQLiteCommand m_sqlCmd;

        public static void init()
        {
            m_dbConn = new SQLiteConnection();
            m_sqlCmd = new SQLiteCommand();
            if (!File.Exists(dbFileName))
                SQLiteConnection.CreateFile(dbFileName);
            try
            {
                m_dbConn = new SQLiteConnection("Data Source=" + dbFileName + ";Version=3;");
                m_dbConn.Open();
                m_sqlCmd.Connection = m_dbConn;

                m_sqlCmd.CommandText = "create table if not exists timeevent(id integer primary key autoincrement, begin text, end text, eventname text, color text)";
                m_sqlCmd.ExecuteNonQuery();
            } 
            catch (Exception e)
            {
                //TODO
            }
        }

        public static void insert(TimeEventSpan span)
        {
            try
            {
                m_sqlCmd.CommandText = "insert into timeevent(begin, end, eventname, color) values ('"+DateTimeToString(span.Begin)+"','"+DateTimeToString(span.End)+"','"+span.EventName+"','"+span.Color+"')";
                m_sqlCmd.ExecuteNonQuery();
            }
            catch (Exception e)
            {
                //TODO
            }
        }

        public static string DateTimeToString(DateTime time)
        {
            return time.Hour.ToString() + ':' + time.Minute.ToString();
        }

        public static DateTime StringToDateTime(string time)
        {
            string[] timearr = time.Split(':');
            int hour = int.Parse(timearr[0]);
            int minute = int.Parse(timearr[1]);
            DateTime current = new DateTime(DateTime.Now.Year, DateTime.Now.Month, DateTime.Now.Day, hour, minute, DateTime.Now.Second);
            return current;
        }

        public static List<TimeEventSpan> select()
        {
            DataTable dTable = new DataTable();
            SQLiteDataAdapter adapter = new SQLiteDataAdapter("select * from timeevent",m_dbConn);
            adapter.Fill(dTable);
            List<TimeEventSpan> list = new List<TimeEventSpan>();
            for (int i=0;i<dTable.Rows.Count;i++)
            {
                list.Add(new TimeEventSpan() { Id = (int)dTable.Rows[i].ItemArray[0], Begin = StringToDateTime((string)dTable.Rows[i].ItemArray[1]), End = StringToDateTime((string)dTable.Rows[i].ItemArray[2]), EventName=(string)dTable.Rows[i].ItemArray[3],Color=(string)dTable.Rows[i].ItemArray[4]});
            }
            return list;
        }
    }
}
