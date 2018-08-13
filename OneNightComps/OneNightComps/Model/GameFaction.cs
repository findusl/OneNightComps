using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Text;

namespace OneNightComps.Model
{
    public class GameFaction
    {
        [JsonProperty("id")]
        public long ID { get; set; }
        [JsonProperty("name")]
        public string Name { get; set; }
    }
}
