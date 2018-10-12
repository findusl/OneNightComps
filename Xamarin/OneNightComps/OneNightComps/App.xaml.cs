using OneNightComps.IOClasses;
using OneNightComps.Model;
using OneNightComps.Pages.MainPage;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

[assembly: XamlCompilation (XamlCompilationOptions.Compile)]
namespace OneNightComps
{
	public partial class App : Application
	{
		public App ()
		{
			InitializeComponent();

            RepositoryFactory.GetGameRoleRepository().GetRoles().ContinueWith((t) =>
            {
                if (t.Exception != null)
                    Debug.WriteLine(t.Exception);
                else
                    Debug.WriteLine(t.Result[0].ToString());
            });

            MainPage = new MainPage();
		}

		protected override void OnStart ()
		{
			// Handle when your app starts
		}

		protected override void OnSleep ()
		{
			// Handle when your app sleeps
		}

		protected override void OnResume ()
		{
			// Handle when your app resumes
		}
	}
}
