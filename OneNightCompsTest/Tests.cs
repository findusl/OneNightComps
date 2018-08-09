using System;
using System.IO;
using System.Linq;
using NUnit.Framework;
using Xamarin.UITest;
using Xamarin.UITest.Queries;

namespace OneNightCompsTest
{
	[TestFixture(Platform.Android)]
	public class Tests
	{
		IApp app;
		Platform platform;

		public Tests(Platform platform)
		{
			this.platform = platform;
		}

		[SetUp]
		public void BeforeEachTest()
		{
            app = ConfigureApp.Android.InstalledApp("de.lehrbaum.OneNightComps").
                PreferIdeSettings().EnableLocalScreenshots().StartApp();
        }

		[Test]
		public void WelcomeTextIsDisplayed()
		{
			AppResult[] results = app.WaitForElement(c => c.Marked("Welcome to Xamarin.Forms!"));
			app.Screenshot("Welcome screen.");

			Assert.IsTrue(results.Any());
		}
	}
}
