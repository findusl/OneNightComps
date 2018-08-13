using Newtonsoft.Json;
using OneNightComps.Model;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;

namespace OneNightComps.IOClasses
{
    /// <summary>
    /// This class cares about connecting to the remote server
    /// </summary>
    class ServerClient : IServerClient
    {
        const string SERVER_BASE_URL = "http://lehrbaum.de";

        HttpClient httpClient;

        internal ServerClient(User user)
        {
            httpClient = new HttpClient();
            httpClient.BaseAddress = new Uri(SERVER_BASE_URL);
            httpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", "Your Oauth token");
        }

        public async Task<resultType> GetString<resultType>(string path)
        {
            try
            {
                string serverAnswerJson = await httpClient.GetStringAsync(path);
                Debug.WriteLine("Answer: " + serverAnswerJson);
                Response<resultType> response = JsonConvert.DeserializeObject<Response<resultType>>(serverAnswerJson);
                if (response.errorMessage != null)
                {
                    handleErrorMessage(response.errorMessage);
                }
                return response.result;
            } catch(Exception e)
            {
                Debug.WriteLine(e);
                throw e;
            }
        }

        void handleErrorMessage(string message)
        {

        }

        class Response<resultType>
        {
            public string errorMessage { get; set; }
            public resultType result { get; set; }
        }
    }
}
