using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using Newtonsoft.Json;
using OneNightComps.Model;

namespace OneNightComps.IOClasses
{
    class RepositoryImplementation : IGameCompositionRepository, IGameRoleRepository, IUserRepository
    {
        readonly User user;
        ServerClient client;

        public RepositoryImplementation(User user)
        {
            this.user = user;
            client = new ServerClient();
        }

        public async Task<List<GameRole>> GetRoles()
        {
            GameRole[] result = await client.GetObjects<GameRole[]>("one_night_comps/game_role/read.php");
            return new List<GameRole>(result);
        }

        public Task<bool> AddGameComoposition(GameComposition composition)
        {
            throw new NotImplementedException();
        }

        public async Task<List<GameComposition>> GetGameCompositions()
        {
            List<GameComposition> result = 
                await client.GetObjects<List<GameComposition>>("one_night_comps/game_composition/read.php");
            return result;
        }

        public Task<List<GameComposition>> GetGameCompositions(GameCompositionSpecification specification)
        {
            throw new NotImplementedException();
        }

        public Task<Tuple<bool, bool>> CheckAvailable(string userName, string eMail)
        {
            throw new NotImplementedException();
        }

        public async Task<bool> RegisterUser(string userName, string passwordHashBase64, string eMail)
        {
            Dictionary<string, string> values = new Dictionary<string, string>();
            values.Add("username", userName);
            values.Add("passwordHashBase64", passwordHashBase64);
            values.Add("eMail", eMail);

            string json = JsonConvert.SerializeObject(values);
            bool result =
                await client.PostObjects<bool>("one_night_comps/user/register.php", json);
            return result;
        }

        public Task<string> GetAccessToken(string userName, string passwordHashBase64)
        {
            throw new NotImplementedException();
        }
    }
}
