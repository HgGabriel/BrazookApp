package br.com.example.brazookatelas.ui.screens.Details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.com.example.brazookatelas.model.Series
import br.com.example.brazookatelas.ui.components.sections.series.DetailScreenSeries
import br.com.example.brazookatelas.ui.theme.BrazookaTelasTheme

class DetailsSeriesActivity : ComponentActivity() {

    private val series: Series by lazy {
        intent?.getSerializableExtra(SERIES_ID) as Series
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BrazookaTelasTheme {
                DetailScreenSeries(series = series)
            }
        }
    }
    companion object {
        private const val SERIES_ID = "series_id"
        fun newIntent(context: Context, series: Series) =
            Intent(context, DetailsSeriesActivity::class.java).apply {
                putExtra(SERIES_ID, series)
            }
    }
}


