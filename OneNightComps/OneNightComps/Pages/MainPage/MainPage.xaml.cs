
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace OneNightComps.Pages.MainPage
{
	[XamlCompilation(XamlCompilationOptions.Compile)]
	public partial class MainPage : MasterDetailPage
	{
		public MainPage ()
		{
            MenuPage menuPage = new MenuPage();
            menuPage.OnElementSelected += OptionSelected;
            Master = menuPage;

            NavigationPage navigationPage = new NavigationPage(new SearchPage());
            Detail = navigationPage;
		}

        void OptionSelected(MenuOptions selectedOption)
        {

        }
	}
}