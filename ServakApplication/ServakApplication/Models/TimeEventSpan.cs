using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ServakApplication.Models
{
    public class TimeEventSpan
    {
        public int Id { get; set; }
        public DateTime Begin { get; set; }
        public DateTime End { get; set; }
        public string EventName { get; set; }
        public string Color { get; set; }
    }
}
