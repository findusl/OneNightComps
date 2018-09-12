using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace OneNightComps.Model
{
    /// <summary>
    /// A composition is a combination of roles that fit together well.
    /// </summary>
    public class GameComposition
    {
        [JsonProperty("id")]
        public long ID { get; set; }
        [JsonProperty("name")]
        public string Name { get; set; }
        [JsonProperty("description")]
        public string Description { get; set; }
        [JsonProperty("roleCount")]
        public int RoleCount { get; set; }
        [JsonProperty("difficultyLevel")]
        public int DifficultyLevel { get; set; }
        [JsonProperty("canAlter")]
        /// <summary>
        /// This tells whether the current user is allowed to alter this composition.
        /// </summary>
        public bool CanAlter { get; set; }

        [JsonProperty("roles")]
        public List<GameRole> Roles { get; set; }

        public override string ToString()
        {
            return ID + ":" + Name + "(" + RoleCount + "," + DifficultyLevel + ")" + 
                Roles.Select(r => r.ToString()).Aggregate((a,b) => a + ';' + b);
        }
    }
}
