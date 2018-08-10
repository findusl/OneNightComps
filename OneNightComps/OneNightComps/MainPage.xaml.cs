using OneNightComps.IOClasses;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Forms;

namespace OneNightComps
{
	public partial class MainPage : ContentPage
	{
		public MainPage()
		{
			InitializeComponent();
            IGameRoleRepository repo = RepositoryFactory.GetGameRoleRepository();
            repo.GetRoles().ContinueWith((t) =>
            {
                if(t.Exception != null)
                {
                    Debug.WriteLine(t.Exception);
                }
                Test.Text = t.Result.First().Description;
            });
		}
	}
}
