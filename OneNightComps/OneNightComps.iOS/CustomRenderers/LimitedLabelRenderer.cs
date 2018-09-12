using System;
using OneNightComps.iOS.CustomRenderers;
using OneNightComps.Pages.CustomViews;
using UIKit;
using Xamarin.Forms;
using Xamarin.Forms.Platform.iOS;

[assembly: ExportRenderer(typeof(LimitedLabel), typeof(LimitedLabelRenderer))]
namespace OneNightComps.iOS.CustomRenderers
{
    public class LimitedLabelRenderer : LabelRenderer
    {
        protected override void OnElementChanged(ElementChangedEventArgs<Label> e)
        {
            base.OnElementChanged(e);

            if (Control != null && Element != null)
            {
                LimitedLabel limitedLabel = (LimitedLabel)Element;
                UILabel label = Control;
                label.Lines = limitedLabel.MaxLines;
            }
        }
    }
}