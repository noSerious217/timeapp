using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace ServakApplication.Controllers
{
    [Produces("application/json")]
    [Route("api/Login")]
    public class LoginController : Controller
    {
        // GET: api/Login
        [HttpGet]
        public IEnumerable<string> GetLogin()
        {
            return new string[] { "value1", "value2" };
        }

        // GET: api/Login/5
        [HttpGet("{id}", Name = "GetLogin")]
        public string GetLogin(int id)
        {
            return "value";
        }
        
        // POST: api/Login
        [HttpPost]
        public void PostLogin([FromBody]string value)
        {
            string z = value;
        }
        
        // PUT: api/Login/5
        [HttpPut("{id}")]
        public void PutLogin(int id, [FromBody]string value)
        {
        }
        
        // DELETE: api/ApiWithActions/5
        [HttpDelete("{id}")]
        public void DeleteLogin(int id)
        {
        }
    }
}
