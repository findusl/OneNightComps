using Newtonsoft.Json;
using OneNightComps.Model;
using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;
using Windows.Data.Json;

namespace OneNightComps.IOClasses
{
    /// <summary>
    /// This class cares about connecting to the remote server
    /// </summary>
    class ServerClient
    {
        const string SERVER_BASE_URL = "https://lehrbaum.de";

        HttpClient httpClient;

        public ServerClient(User user)
        {
            httpClient = new HttpClient();
            httpClient.BaseAddress = new Uri(SERVER_BASE_URL);
            httpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", "Your Oauth token");
        }

        public async Task<resultType> GetString<resultType>(string path)
        {
            string serverAnswerJson = await httpClient.GetStringAsync(path);
            Response<resultType> response = JsonConvert.DeserializeObject<Response<resultType>>(serverAnswerJson);
            if(response.errorMessage != null)
            {
                handleErrorMessage(response.errorMessage);
            }
            return response.result;
        }

        void handleErrorMessage(string message)
        {

        }

        class Response<resultType>
        {
            internal string errorMessage;
            internal resultType result;
        }
    }
}
