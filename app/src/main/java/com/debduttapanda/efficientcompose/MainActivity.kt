package com.debduttapanda.efficientcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.debduttapanda.efficientcompose.ui.theme.EfficientComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EfficientComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MyBox1()
                }
            }
        }
    }

    @Composable
    fun MyBox() {
        val color by animateColorBetween(Color.Red, Color.Green)
        Box(
            modifier = Modifier
                .wrapContentSize(Alignment.Center)
                .width(200.dp)
                .height(200.dp)
                .background(color)//inefficient
        ) {
            println("Recomposing")
        }
    }



    @Composable
    private fun animateColorBetween(
        initialValue: Color,
        targetValue: Color
    ): State<Color> {
        val infiniteTransition = rememberInfiniteTransition()
        return infiniteTransition.animateColor(
            initialValue = initialValue,
            targetValue = targetValue,
            animationSpec = infiniteRepeatable(
                animation = tween(2000),
                repeatMode = RepeatMode.Reverse
            )
        )
    }
    /////////////////////////////////
    @Composable
    fun MyBox1() {
        val color by animateColorBetween(Color.Red, Color.Green)
        Box(
            modifier = Modifier
                .wrapContentSize(Alignment.Center)
                .width(200.dp)
                .height(200.dp)
                .drawBehind {//efficient, by skipping layouting every time, layouting will happen only once, only drawing will be done per frame
                    drawRect(color)
                }
        ) {
            println("Recomposing")//not printing repeatedly//efficient
        }
    }
}