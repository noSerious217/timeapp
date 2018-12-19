using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ServakApplication.Models
{
    public class TimeEventSpan
    {
        public int Id { get; set; }
        public int UserId { get; set; }
        public DateTime Begin { get; set; }
        public DateTime End { get; set; }
        public string EventName { get; set; }
        public string Color { get; set; }
    }

    public class TimeEventSpanTmp
    {
        public string Begin { get; set; }
        public string End { get; set; }
        public string EventName { get; set; }
        public string Color { get; set; }
    }
}
