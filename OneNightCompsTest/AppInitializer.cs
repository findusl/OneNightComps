using System;
using Xamarin.UITest;
using Xamarin.UITest.Queries;

namespace OneNightCompsTest
{
	public class AppInitializer
	{
		public static IApp StartApp(Platform platform)
		{
			if (platform == Platform.Android)
			{
				return ConfigureApp.Android.InstalledApp("de.lehrbaum.OneNightComps").
                    PreferIdeSettings().EnableLocalScreenshots().StartApp();
            }

			return ConfigureApp.iOS.StartApp();
		}
	}
}