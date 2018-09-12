using System;
using Android.Content;
using OneNightComps.Droid.CustomRenderers;
using OneNightComps.Pages.CustomViews;
using Xamarin.Forms;
using Xamarin.Forms.Platform.Android;

[assembly: ExportRenderer(typeof(LimitedLabel), typeof(LimitedLabelRenderer))] 
namespace OneNightComps.Droid.CustomRenderers { 
    public class LimitedLabelRenderer : LabelRenderer {
        
        public LimitedLabelRenderer(Context context) : base(context) {
            
        }

        protected override void OnElementChanged(ElementChangedEventArgs<Label> e) 
        {
            base.OnElementChanged(e);
            if(Control != null && Element != null) {
                LimitedLabel limitedLabel = (LimitedLabel)Element;
                Control.SetMaxLines(limitedLabel.MaxLines); 
            }
        } 
    } 
}