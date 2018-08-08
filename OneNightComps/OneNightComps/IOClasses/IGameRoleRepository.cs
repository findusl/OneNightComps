using OneNightComps.Model;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace OneNightComps.IOClasses
{
    /// <summary>
    /// This interface should follow the repository pattern together with the Specification pattern as described by Eric Evans.
    /// </summary>
    interface IGameRoleRepository
    {
        Task<List<GameRole>> GetRoles();
    }
}
