using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Net;
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
        const string SERVER_BASE_URL = "https://lehrbaum.de";

        HttpClient httpClient;

        internal ServerClient()
        {
            httpClient = new HttpClient
            {
                BaseAddress = new Uri(SERVER_BASE_URL)
            };
            httpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", "Your Oauth token");
        }

        public async Task<resultType> GetObjects<resultType>(string path, Dictionary<string, string> query = null)
        {
            if(query != null)
            {
                string queryString = BuildHttpQueryString(query);
                path += queryString;
            }
            Debug.WriteLine("path: " + path);
            try
            {
                string serverAnswerJson = await httpClient.GetStringAsync(path);
                Debug.WriteLine("Answer for get: " + serverAnswerJson);
                Response<resultType> response = JsonConvert.DeserializeObject<Response<resultType>>(serverAnswerJson);
                if (response.ErrorMessage != null)
                {
                    HandleErrorMessage(response.ErrorMessage);
                }
                return response.Result;
            } catch(Exception e)
            {
                Debug.WriteLine(e);
                throw e;
            }
        }

        public async Task<resultType> PostObjects<resultType>(string path, string jsonContent, 
            Dictionary<string, string> query = null)
        {
            try
            {
                string encodedParameters = WebUtility.HtmlEncode(jsonContent);
                StringContent content = new StringContent(jsonContent, Encoding.UTF8, "application/json");
                HttpResponseMessage httpRespones = await httpClient.PostAsync(path, content);
                string serverAnswerJson = await httpRespones.Content.ReadAsStringAsync();
                Debug.WriteLine("Answer for post: " + serverAnswerJson);
                Response<resultType> response = JsonConvert.DeserializeObject<Response<resultType>>(serverAnswerJson);
                if (response.ErrorMessage != null)
                {
                    HandleErrorMessage(response.ErrorMessage);
                }
                return response.Result;
            }
            catch (Exception e)
            {
                Debug.WriteLine(e);
                throw e;
            }
        }

        string BuildHttpQueryString(Dictionary<string, string> queryDictionary)
        {
            string query = "?";
            foreach (KeyValuePair<string, string> entry in queryDictionary)
            {
                query += WebUtility.UrlEncode(entry.Key) + "=" + WebUtility.UrlEncode(entry.Value);
            }
            return query;
        }

        void HandleErrorMessage(string message)
        {
            Debug.WriteLine("Error message:" + message);
        }

        class Response<resultType>
        {
            [JsonProperty("errorMessage")]
            public string ErrorMessage { get; set; }
            [JsonProperty("result")]
            public resultType Result { get; set; }
        }
    }
}
