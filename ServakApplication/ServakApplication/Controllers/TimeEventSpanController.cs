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
    [Route("api/TimeEventSpan")]
    public class TimeEventSpanController : Controller
    {
        // GET: api/TimeEventSpan
        [HttpGet]
        public IEnumerable<string> GetTimeEventSpan()
        {
            return new string[] { "value1", "value2" };
        }

        // GET: api/TimeEventSpan/5
        [HttpGet("{id}", Name = "GetTimeEventSpan")]
        public string GetTimeEventSpan(int id)
        {
            return "value";
        }

        // POST: api/TimeEventSpan
        [HttpPost]
        public void PostTimeEventSpan([FromBody]TimeEventSpan value)
        {
            var q = value;
        }

        // PUT: api/TimeEventSpan/5
        [HttpPut("{id}")]
        public void PutTimeEventSpan(int id, [FromBody]string value)
        {
        }
        
        // DELETE: api/ApiWithActions/5
        [HttpDelete("{id}")]
        public void DeleteTimeEventSpan(int id)
        {
        }
    }
}
