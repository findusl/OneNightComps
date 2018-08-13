using OneNightComps.Model;
using System;
using System.Collections.Generic;
using System.Text;

namespace OneNightComps.IOClasses
{
    public static class RepositoryFactory
    {
        static RepositoryImplementation instance;

        public static IGameCompositionRepository GetGameCompositionRepository()
        {
            if (instance == null)
                instance = new RepositoryImplementation(UserManager.GetCurrentUser());
            return instance;
        }

        public static IGameRoleRepository GetGameRoleRepository()
        {
            if (instance == null)
                instance = new RepositoryImplementation(UserManager.GetCurrentUser());
            return instance;
        }
    }
}
