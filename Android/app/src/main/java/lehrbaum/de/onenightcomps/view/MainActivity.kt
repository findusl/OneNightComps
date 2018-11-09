package lehrbaum.de.onenightcomps.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.fragment_composition.*
import lehrbaum.de.onenightcomps.R
import lehrbaum.de.onenightcomps.viewmodel.ResolutionProvider

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		//initialize the ResolutionProvider
		ResolutionProvider.resolution = SimpleResolution(findViewById(R.id.rootLayout));
	}
}
