namespace OneNightComps.IOClasses
{
    public static class RepositoryFactory
    {
        static RepositoryImplementation instance;

        public static IGameCompositionRepository GetGameCompositionRepository()
        {
            if (instance == null)
                instance = new RepositoryImplementation(UserManager.GetInstance().CurrentUser);
            return instance;
        }

        public static IGameRoleRepository GetGameRoleRepository()
        {
            if (instance == null)
                instance = new RepositoryImplementation(UserManager.GetInstance().CurrentUser);
            return instance;
        }

        public static IUserRepository GetUserRepository()
        {
            if (instance == null)
                instance = new RepositoryImplementation(UserManager.GetInstance().CurrentUser);
            return instance;
        }
    }
}
