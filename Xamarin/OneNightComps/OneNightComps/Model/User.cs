using System;
using System.Collections.Generic;
using System.Text;

namespace OneNightComps.Model
{
    /// <summary>
    /// This object represents a logged in user at runtime. Most of the information
    /// about the user is in the token. The client side should not make decisions on
    /// what a user is allowed to do or not, so it does not need information like the user id.
    /// </summary>
    public class User
    {
        public string UserName { get; set; }
        public string Token { get; set; }
    }
}
