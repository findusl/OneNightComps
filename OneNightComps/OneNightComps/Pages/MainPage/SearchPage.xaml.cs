using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace OneNightComps.Pages.MainPage
{
	[XamlCompilation(XamlCompilationOptions.Compile)]
	public partial class SearchPage : ContentPage
	{
		public SearchPage ()
		{
			InitializeComponent ();
		}

        void AmountPlayersChanged(object sender, ValueChangedEventArgs e)
        {
            int newValue = (int)Math.Round(e.NewValue);
            AmountSlider.Value = newValue;
            AmountLabel.Text = "Amount of players: " + newValue;
        }
    }
}