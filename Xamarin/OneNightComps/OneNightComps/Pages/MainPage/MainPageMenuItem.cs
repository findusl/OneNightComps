using System;
using Xamarin.Forms;

namespace OneNightComps.Pages.MainPage
{
    class MainPageMenuItem
    {
        public string Name { get; set; }

        public Func<ContentPage> GeneratePage { get; set; }
    }
}
