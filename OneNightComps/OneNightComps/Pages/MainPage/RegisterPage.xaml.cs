using System;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace OneNightComps.Pages.MainPage
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
	public partial class RegisterPage : ContentPage
	{
		public RegisterPage ()
		{
			InitializeComponent ();
            UserNameEntry.Completed += (_, __) => PasswordEntry.Focus();
            PasswordEntry.Completed += (_, __) => EMailEntry.Focus();
            EMailEntry.Completed += OnRegisterPressed;
        }

        public void OnRegisterPressed(object sender, EventArgs e)
        {
            UserManager manager = UserManager.GetInstance();
            manager.RegisterUser(UserNameEntry.Text, PasswordEntry.Text, EMailEntry.Text);
        }
    }
}