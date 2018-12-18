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
    [Route("api/TimeSpan")]
    public class TimeEventSpanController : Controller
    {
        // GET: api/TimeSpan
        [HttpGet]
        public IEnumerable<string> GetTimeEventSpan()
        {
            return new string[] { "value1", "value2" };
        }

        // GET: api/TimeSpan/5
        [HttpGet("{id}", Name = "GetTimeEventSpan")]
        public string GetTimeEventSpan(int id)
        {
            return "value";
        }
        
        // POST: api/TimeSpan
        [HttpPost]
        public void PostTimeEventSpan([FromBody]TimeEventSpan value)
        {
        }
        
        // PUT: api/TimeSpan/5
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
