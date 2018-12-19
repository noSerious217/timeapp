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
        public IEnumerable<string> GetTimeEventSpanList()
        {
            return new string[] { "value1", "value2" };
        }

        // GET: api/TimeEventSpanList/5
        [HttpGet("{id}", Name = "GetTimeEventSpanList")]
        public string GetTimeEventSpanList(int id)
        {
            return "value";
        }
        
        // POST: api/TimeEventSpanList
        [HttpPost]
        public void PostTimeEventSpanList([FromBody]IEnumerable<TimeEventSpan> list)
        {
            var z = list;
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
