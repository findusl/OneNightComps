using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Text;

namespace OneNightComps.Model
{
    public class GameRole
    {
        [JsonProperty("id")]
        public long ID { get; set; }
        [JsonProperty("name")]
        public string Name { get; set; }
        [JsonProperty("description")]
        public string Description { get; set; }
        [JsonProperty("faction")]
        public GameFaction Faction { get; set; }
    }
}
