using OneNightComps.IOClasses;
using OneNightComps.Model;
using Org.BouncyCastle.Crypto;
using Org.BouncyCastle.Crypto.Generators;
using Org.BouncyCastle.Crypto.Parameters;
using System;
using System.Threading.Tasks;

namespace OneNightComps
{
    /// <summary>
    /// Reacts to login or log out of a user. The event may be fired while the UI still shows the waiting
    /// screen to the user.
    /// </summary>
    /// <param name="newUser">the newly logged in user or null if the user was logged out.</param>
    public delegate void UserChangedHandler(User newUser);
    public class UserManager
    {
        static UserManager instance;

        public static UserManager GetInstance()
        {
            if (instance == null)
                instance = new UserManager();
            return instance;
        }

        public event UserChangedHandler OnUserChanged = delegate { };

        public User CurrentUser { private set; get; }

        UserManager()
        {
        }

        public async Task<bool> LoginUser(string userName, string password) 
        {
            string base64PasswordHash = GetHash(userName, password);
            string userToken = await RepositoryFactory.GetUserRepository().GetAccessToken(userName, base64PasswordHash);
            if(userToken != null)
            {
                CurrentUser = new User
                {
                    UserName = userName,
                    Token = userToken
                };
                OnUserChanged(CurrentUser);
            }
            return true;
        }

        public async Task RegisterUser(string userName, string password, string eMail)
        {
            string base64PasswordHash = GetHash(userName, password);
            await RepositoryFactory.GetUserRepository().RegisterUser(userName, base64PasswordHash, eMail);
        }

        public void LogoutUser()
        {
            CurrentUser = null;
            OnUserChanged(null);
        }

        #region hashing methods
        //this includes methods for the hashing algorithm. It is very basic, but should be enough

        readonly byte[] randomSalt = new byte[] { 0x81, 0x2b, 0x9c, 0x00, 0x69, 0x6a, 0x63, 0xd7 };

        string GetHash(string userName, string password)
        {
            //I don't want to save the random salt generated in the database and then have to ask for it
            //on every login. So I choose to use the user name as salt. This is unique for this application.
            //Since it is probably not unique accross multiple applicatios I first hash the username with a hard coded
            //random salt so the resulting hash is likely unique globally.
            byte[] hashedUserName = HashSha512(userName, randomSalt);
            byte[] hashedPassword = HashSha512(password, hashedUserName);
            return Convert.ToBase64String(hashedPassword);
        }

        byte[] HashSha512(string secret, byte[] salt)
        {
            var pdb = new Pkcs5S2ParametersGenerator(new Org.BouncyCastle.Crypto.Digests.Sha512Digest());
            //no clue how many iterations to use. I googled 2,000 to 10,000 is nice
            var numIterations = 2000;
            pdb.Init(PbeParametersGenerator.Pkcs5PasswordToBytes(secret.ToCharArray()), salt,
                         numIterations);
            //no clue what size to use. I googled 128 bits is nice, so 16 bytes
            var hashSize = 16;
            var key = (KeyParameter)pdb.GenerateDerivedMacParameters(hashSize * 8);
            return key.GetKey();
        }

        #endregion
    }
}
