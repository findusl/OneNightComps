using OneNightComps.IOClasses;
using OneNightComps.Model;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace OneNightComps.Pages.MainPage
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
	public partial class CompositionListPage : ContentPage
	{
        ObservableCollection<GameComposition> compositionItems;

        Task loadingCompositions;

		public CompositionListPage ()
		{
			InitializeComponent ();
            loadingCompositions = Task.Run(() => LoadCompositions());
		}

        async void LoadCompositions()
        {
            IGameCompositionRepository repository = RepositoryFactory.GetGameCompositionRepository();
            List<GameComposition> compositions = await repository.GetGameCompositions();
            Device.BeginInvokeOnMainThread(() => {
                compositionItems = new ObservableCollection<GameComposition>(compositions);
                CompositionsListView.ItemsSource = compositionItems;
            });
        }

        protected override void OnDisappearing()
        {
            //cancel the loading if still running
            base.OnDisappearing();
        }
    }
}