using System;
using System.Collections.Generic;
using System.Text;

namespace OneNightComps.Model
{
    /// <summary>
    /// A composition is a combination of roles that fit together well.
    /// </summary>
    public class GameComposition
    {
        public long ID { get; set; }
        public string Name { get; set; }
        public string Description { get; set; }
        public int RoleCount { get; set; }
        public int Difficulty { get; set; }
        /// <summary>
        /// This tells whether the current user is allowed to alter this composition.
        /// </summary>
        public bool CanAlter { get; set; }

        public List<GameRole> Roles { get; set; }
    }
}
