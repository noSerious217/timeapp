﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using ServakApplication.Models;

namespace ServakApplication.Controllers
{
    [Produces("application/json")]
    [Route("api/Test")]
    public class TestController : Controller
    {
        // GET: api/Test
        [HttpGet]
        public IEnumerable<string> Get()
        {
            Console.WriteLine("Androin is here");
            return new string[] { "value1", "value2" };
        }

        // GET: api/Test/5
        [HttpGet("{id}", Name = "Get")]
        public string Get(int id)
        {
            return "value";
        }
        
        // POST: api/Test
        [HttpPost]
        public void Post([FromBody]TimeEventSpan login)
        {
            string z = "";
        }
        
        // PUT: api/Test/5
        [HttpPut("{id}")]
        public void Put(int id, [FromBody]string value)
        {
        }
        
        // DELETE: api/ApiWithActions/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {
        }

        public class TestClass
        {
            public string Valuev;
            public int z;
        }
    }
}
