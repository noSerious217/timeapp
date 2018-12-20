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

        public static void Init()
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

                m_sqlCmd.CommandText = "create table if not exists timeevent(id integer primary key autoincrement, userid integer, begin text, end text, eventname text, color text)";
                m_sqlCmd.ExecuteNonQuery();
                m_sqlCmd.CommandText = "create table if not exists user(id integer primary key autoincrement, username text)";
                m_sqlCmd.ExecuteNonQuery();
            } 
            catch (Exception e)
            {
                //TODO
            }
        }

        public static void Insert(TimeEventSpan span)
        {
            try
            {
                m_sqlCmd.CommandText = "insert into timeevent(userid, begin, end, eventname, color) values ("+span.UserId+",'"+DateTimeToString(span.Begin)+"','"+DateTimeToString(span.End)+"','"+span.EventName+"','"+span.Color+"')";
                m_sqlCmd.ExecuteNonQuery();
            }
            catch (Exception e)
            {
                //TODO
            }
        }

        public static void Insert(UserSpan span)
        {
            try
            {
                m_sqlCmd.CommandText = "insert into user(username) values ('" + span.UserName + "')";
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
            DateTime current = new DateTime(DateTime.Now.Year, DateTime.Now.Month, DateTime.Now.Day, hour, minute, 0);
            return current;
        }

        public static List<TimeEventSpan> SelectEvent()
        {
            DataTable dTable = new DataTable();
            SQLiteDataAdapter adapter = new SQLiteDataAdapter("select * from timeevent",m_dbConn);
            adapter.Fill(dTable);
            List<TimeEventSpan> list = new List<TimeEventSpan>();
            for (int i=0;i<dTable.Rows.Count;i++)
            {
                list.Add(new TimeEventSpan() { Id = Convert.ToInt32(dTable.Rows[i].ItemArray[0]), UserId = Convert.ToInt32(dTable.Rows[i].ItemArray[1]), Begin = StringToDateTime((string)dTable.Rows[i].ItemArray[2]), End = StringToDateTime((string)dTable.Rows[i].ItemArray[3]), EventName=(string)dTable.Rows[i].ItemArray[4],Color=(string)dTable.Rows[i].ItemArray[5]});
            }
            return list;
        }

        public static List<TimeEventSpan> SelectEvent(int UserId)
        {
            DataTable dTable = new DataTable();
            SQLiteDataAdapter adapter = new SQLiteDataAdapter("select * from timeevent where userid="+UserId.ToString(), m_dbConn);
            adapter.Fill(dTable);
            List<TimeEventSpan> list = new List<TimeEventSpan>();
            for (int i = 0; i < dTable.Rows.Count; i++)
            {
                list.Add(new TimeEventSpan() { Id = Convert.ToInt32(dTable.Rows[i].ItemArray[0]), UserId = Convert.ToInt32(dTable.Rows[i].ItemArray[1]), Begin = StringToDateTime((string)dTable.Rows[i].ItemArray[2]), End = StringToDateTime((string)dTable.Rows[i].ItemArray[3]), EventName = (string)dTable.Rows[i].ItemArray[4], Color = (string)dTable.Rows[i].ItemArray[5] });
            }
            return list;
        }

        public static List<UserSpan> SelectUser()
        {
            DataTable dTable = new DataTable();
            SQLiteDataAdapter adapter = new SQLiteDataAdapter("select * from user", m_dbConn);
            adapter.Fill(dTable);
            List<UserSpan> list = new List<UserSpan>();
            for (int i = 0; i < dTable.Rows.Count; i++)
            {
                list.Add(new UserSpan() { Id = Convert.ToInt32(dTable.Rows[i].ItemArray[0]), UserName = (string)dTable.Rows[i].ItemArray[1] });
            }
            return list;
        }

        public static bool CheckUser(string UserName)
        {
            DataTable dTable = new DataTable();
            SQLiteDataAdapter adapter = new SQLiteDataAdapter("select * from user where username = \""+UserName + "\"", m_dbConn);
            adapter.Fill(dTable);
            return dTable.Rows.Count > 0;
        }

        public static void DeleteEvent(int id)
        {
            m_sqlCmd.CommandText = "delete from timeevent where id="+id.ToString();
            m_sqlCmd.ExecuteNonQuery();
        }

        public static void DeleteUser(int id)
        {
            m_sqlCmd.CommandText = "delete from user where id=" + id.ToString();
            m_sqlCmd.ExecuteNonQuery();
        }
    }
}
