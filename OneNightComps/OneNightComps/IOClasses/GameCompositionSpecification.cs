using OneNightComps.Model;
using System;
using System.Collections.Generic;
using System.Text;

namespace OneNightComps.IOClasses
{
    public interface GameCompositionSpecification
    {
        bool DoesFitSpecification(GameComposition composition);

        string GetRestCommand();
    }
}
