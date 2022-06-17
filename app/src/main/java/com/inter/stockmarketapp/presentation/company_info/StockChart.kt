package com.inter.stockmarketapp.presentation.company_info


import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.inter.stockmarketapp.domain.model.IntradayInfo
import kotlin.math.roundToInt


@Composable
fun StockChart(
    infos: List<IntradayInfo> = emptyList(),
    modifier: Modifier = Modifier,
    //graphics.Color
    graphColor: Color = Color.Green
) {
    //spacing between graph and border
    val spacing = 100f
    //color transparent alpha = 0.5f selected specifically for schedule
    val transparentGraphColor = remember {
        graphColor.copy(alpha = 0.5f)
    }
    // to calibrate the graph find out in what boundaries we will display
    val upperValue = remember {
        (infos.maxOfOrNull { it.close }?.plus(1))?.roundToInt() ?: 0
    }
    val lowerValue = remember(infos) {
        infos.minOfOrNull { it.close }?.toInt() ?: 0
    }
}