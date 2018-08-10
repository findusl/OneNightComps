using System;
using System.Collections.Generic;
using System.Text;

namespace OneNightComps.Model
{
    class UserManager
    {
        static UserManager instance;
        public static UserManager GetInstance()
        {
            if (instance == null)
                instance = new UserManager();
            return instance;
        }
        public static User GetCurrentUser()
        {
            return GetInstance().currentUser;
        }

        User currentUser;
    }
}
