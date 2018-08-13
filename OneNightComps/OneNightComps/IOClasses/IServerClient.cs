using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace OneNightComps.IOClasses
{
    public interface IServerClient 
    {
        Task<resultType> GetString<resultType>(string path);
    }
}
