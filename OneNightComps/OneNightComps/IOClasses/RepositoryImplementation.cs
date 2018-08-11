﻿using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;
using OneNightComps.Model;

namespace OneNightComps.IOClasses
{
    class RepositoryImplementation : IGameCompositionRepository, IGameRoleRepository
    {
        User user;
        ServerClient client;

        public RepositoryImplementation(User user)
        {
            this.user = user;
            client = new ServerClient(user);
        }

        public async Task<List<GameRole>> GetRoles()
        {
            GameRole[] result = await client.GetString<GameRole[]>("one_night_comps/game_role/read.php");
            return new List<GameRole>(result);
        }

        public Task<bool> AddGameComoposition(GameComposition composition)
        {
            throw new NotImplementedException();
        }

        public async Task<List<GameComposition>> GetGameCompositions()
        {
            List<GameComposition> result = await client.GetString<List<GameComposition>>("one_night_comps/game_composition/read.php");
            return result;
        }

        public Task<List<GameComposition>> GetGameCompositions(GameCompositionSpecification specification)
        {
            throw new NotImplementedException();
        }
    }
}