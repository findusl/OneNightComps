using OneNightComps.Model;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace OneNightComps.IOClasses
{
    public interface IUserRepository
    {
        Task<Tuple<bool, bool>> CheckAvailable(string userName, string eMail);

        Task<bool> RegisterUser(string userName, string passwordHashBase64, string eMail);

        Task<string> GetAccessToken(string userName, string passwordHashBase64);
    }
}
