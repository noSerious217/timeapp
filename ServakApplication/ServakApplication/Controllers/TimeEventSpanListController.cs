using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using ServakApplication.Models;

namespace ServakApplication.Controllers
{
    [Produces("application/json")]
    [Route("api/TimeEventSpanList")]
    public class TimeEventSpanListController : Controller
    {
        // GET: api/TimeEventSpanList
        [HttpGet]
        public void GetTimeEventSpanList()
        {
        }

        // GET: api/TimeEventSpanList/5
        [HttpGet("{id}", Name = "GetTimeEventSpanList")]
        public IEnumerable<TimeEventSpanJSON> GetTimeEventSpanList(int id)
        {
            SQLiteController.Init();
            var events = SQLiteController.SelectEvent(id);
            IEnumerable<TimeEventSpanJSON> retEvents = events.Select(x => new TimeEventSpanJSON
            {
                UserId = x.UserId,
                Color = x.Color,
                EventName = x.EventName,
                UserName = x.UserName,
                Begin = SQLiteController.DateTimeToString(x.Begin),
                End = SQLiteController.DateTimeToString(x.End)
            });
            return retEvents;
        }

        public class Fantik
        {
            public TimeEventSpan[] list;
        }
        
        // POST: api/TimeEventSpanList
        [HttpPost]
        public void PostTimeEventSpanList([FromBody]Fantik list)
        {
            SQLiteController.Init();
            foreach(var lis in list.list)
            {
                SQLiteController.Insert(lis);
            }
        }
        
        // PUT: api/TimeEventSpanList/5
        [HttpPut("{id}")]
        public void PutTimeEventSpanList(int id, [FromBody]string value)
        {
        }
        
        // DELETE: api/ApiWithActions/5
        [HttpDelete("{id}")]
        public void DeleteTimeEventSpanList(int id)
        {
        }
    }
}
