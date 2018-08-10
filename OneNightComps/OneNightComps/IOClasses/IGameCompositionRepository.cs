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
    interface IGameCompositionRepository
    {
        Task<bool> AddGameComoposition(GameComposition composition);

        Task<List<GameComposition>> GetGameCompositions();

        Task<List<GameComposition>> GetGameCompositions(GameCompositionSpecification specification);
    }
}
