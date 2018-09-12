using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace OneNightComps.IOClasses
{
    public interface IServerClient 
    {
        Task<resultType> GetObjects<resultType>(string path, Dictionary<string, string> query = null);

        Task<resultType> PostObjects<resultType>(string path, string parameters, Dictionary<string, string> query = null);
    }
}
