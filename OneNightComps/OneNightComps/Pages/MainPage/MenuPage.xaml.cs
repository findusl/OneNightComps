using OneNightComps.Model;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace OneNightComps.Pages.MainPage
{
    delegate void ElementSelectedHandler(MainPageMenuItem selected);
	[XamlCompilation(XamlCompilationOptions.Compile)]
	partial class MenuPage : ContentPage
	{
        internal event ElementSelectedHandler OnElementSelected = delegate { };

        //define every possible menu item. This way they can be added and removed from the list as needed
        MainPageMenuItem compositionsListItem = new MainPageMenuItem()
        {
            Name = "List of role compositions",
            GeneratePage = () => new CompositionListPage()
        };
        MainPageMenuItem searchItem = new MainPageMenuItem()
        {
            Name = "Search",
            GeneratePage = () => new SearchPage()
        };
        MainPageMenuItem createItem = new MainPageMenuItem()
        {
            Name = "Create new role composition",
            GeneratePage = () => new SearchPage()
        };//TODO create correct page
        MainPageMenuItem loginItem = new MainPageMenuItem()
        {
            Name = "Login",
            GeneratePage = () => new LoginPage()
        };
        MainPageMenuItem registerItem = new MainPageMenuItem()
        {
            Name = "Register",
            GeneratePage = () => new RegisterPage()
        };
        MainPageMenuItem logoutItem = new MainPageMenuItem()
        {
            Name = "Logout",
            GeneratePage = () =>
            {
                //maybe this isn't the way GeneratePage is expected to be used. But it fits so well here.
                UserManager.GetInstance().LogoutUser();
                return new CompositionListPage();
            }
        };

        ObservableCollection<MainPageMenuItem> menuItems;
		public MenuPage ()
		{
			InitializeComponent ();
            var initialList = new List<MainPageMenuItem>() {
                compositionsListItem,
                searchItem,
                createItem,
                loginItem,
                registerItem
            };
            menuItems = new ObservableCollection<MainPageMenuItem>(initialList);
            OptionsListView.ItemsSource = menuItems;

            OptionsListView.ItemSelected += OptionSelected;
            UserManager.GetInstance().OnUserChanged += OnUserChanged;
		}

        void OnUserChanged(User newUser)
        {
            if (newUser != null)
            {
                menuItems.Remove(loginItem);
                menuItems.Remove(registerItem);
                menuItems.Add(logoutItem);
                UserNameLabel.IsVisible = true;
                UserNameLabel.Text = newUser.UserName;
            } else {
                menuItems.Add(loginItem);
                menuItems.Add(registerItem);
                menuItems.Remove(logoutItem);
                UserNameLabel.IsVisible = false;

            }
        }

        void OptionSelected(object sender, SelectedItemChangedEventArgs e)
        {
            if (e.SelectedItem == null)
                return;
            OptionsListView.SelectedItem = null;
            OnElementSelected((MainPageMenuItem) e.SelectedItem);
        }
	}
}