package com.example.omikujicompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.omikujicompose.ui.theme.OmikujiComposeTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OmikujiComposeTheme {
                AppScreen()
            }
        }
    }
}

@Composable
fun AppScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var btnTextId by remember { mutableIntStateOf(R.string.btn_text) }
        var imgId by remember { mutableIntStateOf(R.drawable.omikuji) }
        var imgRotation by remember { mutableFloatStateOf(0f) }

        val animatedRotation by animateFloatAsState(
            targetValue = imgRotation,
            animationSpec = tween(durationMillis = if (imgRotation == 180f) 2000 else 0)
        )

        LaunchedEffect(imgRotation) {
            if (imgRotation == 180f) {
                delay(1800)
                imgId = getResult()
                imgRotation = 0f
            }
        }

        Text(
            text = stringResource(R.string.title),
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(60.dp))

        Image(
            painter = painterResource(imgId),
            contentDescription = stringResource(R.string.img_desc),
            modifier = Modifier.size(300.dp).rotate(animatedRotation)
        )

        Spacer(modifier = Modifier.height(60.dp))

        Button(
            onClick = {
                btnTextId = R.string.btn_text_again
                imgId = R.drawable.omikuji
                imgRotation = 180f
            },
            modifier = Modifier.alpha(if (imgRotation == 180f) 0f else 1f)
        ) {
            Text(text = stringResource(btnTextId))
        }
    }
}

private fun getResult() = when ((0..100).random()) {
    in 91..100 -> R.drawable.daikichi
    in 71..90 -> R.drawable.chukichi
    in 51..70 -> R.drawable.shoukichi
    in 31..50 -> R.drawable.kichi
    in 11..30 -> R.drawable.kyou
    else -> R.drawable.daikyo
}

@Preview(showBackground = true)
@Composable
fun AppScreenPreview() {
    OmikujiComposeTheme {
        AppScreen()
    }
}
