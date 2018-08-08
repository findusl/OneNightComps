using System;
using System.Collections.Generic;
using System.Text;

namespace OneNightComps.Model
{
    class GameRole
    {
        public long ID { get; set; }
        public string Name { get; set; }
        public string Description { get; set;  }
        public GameFaction faction { get; set; }
    }
}
