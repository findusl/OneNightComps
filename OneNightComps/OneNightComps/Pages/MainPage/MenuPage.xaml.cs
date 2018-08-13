using System.Collections.Generic;
using System.Linq;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace OneNightComps.Pages.MainPage
{
    public enum MenuOptions
    {
        ListCompositions, Search, CreateComposition, Login, Register
    }
    public delegate void ElementSelectedHandler(MenuOptions selected);
	[XamlCompilation(XamlCompilationOptions.Compile)]
	public partial class MenuPage : ContentPage
	{
        public event ElementSelectedHandler OnElementSelected = delegate { };

        List<string> names;
		public MenuPage ()
		{
			InitializeComponent ();
            names = new List<string>() {
                "List of role compositions", "Search", "Create new role composition", "Login", "Register"}; ;
            OptionsListView.ItemsSource = names;

            OptionsListView.ItemSelected += OptionSelected;
		}

        void OptionSelected(object sender, SelectedItemChangedEventArgs e)
        {
            OptionsListView.SelectedItem = null;
            switch(names.IndexOf((string)e.SelectedItem))
            {
                case 0:
                    OnElementSelected(MenuOptions.ListCompositions);
                    break;
                case 1:
                    OnElementSelected(MenuOptions.Search);
                    break;
                case 2:
                    OnElementSelected(MenuOptions.CreateComposition);
                    break;
                case 3:
                    OnElementSelected(MenuOptions.Login);
                    break;
                case 4:
                    OnElementSelected(MenuOptions.Register);
                    break;
            }
        }
	}
}