using System;
using System.Collections.Generic;

using Xamarin.Forms;

namespace OneNightComps.Pages.MainPage
{
    public partial class CreateCompositionPage : ContentPage
    {
        public CreateCompositionPage()
        {
            InitializeComponent();
        }

        void DifficultyValueChanged(object sender, ValueChangedEventArgs args) {
            int newValue = (int)Math.Round(args.NewValue);
            DifficultySlider.Value = newValue;
            DifficultyLabel.Text = "Difficulty: " + newValue;
        }
    }
}
