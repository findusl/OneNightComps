using OneNightComps.Model;

namespace OneNightComps.IOClasses
{
    public static class ServerClientFactory
    {
        const bool OFFLINE_TESTING = false;
        static IServerClient instance;

        public static IServerClient GetServerClient()
        {
            if (instance == null)
            {
                if (OFFLINE_TESTING)
                    instance = new DummyServerClient();
                else
                    instance = new ServerClient();
            }
            return instance;
        }
    }
}
