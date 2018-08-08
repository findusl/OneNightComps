using System;
using System.Collections.Generic;
using System.Text;

namespace OneNightComps.IOClasses
{
    class RepositoryFactory
    {
        public static IGameCompositionRepository GetGameCompositionRepository()
        {
            return null;
        }

        public static IGameRoleRepository GetGameRoleRepository()
        {
            return null;
        }
    }
}
