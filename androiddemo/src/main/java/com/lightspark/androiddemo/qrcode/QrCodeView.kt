package com.lightspark.androiddemo.qrcode

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lightspark.androiddemo.R
import com.lightspark.androiddemo.ui.theme.LightsparkTheme

/**
 * Defines the color palette for a QR code.
 */
data class QrCodeColors(
    val background: Color, val foreground: Color
) {
    companion object {
        @Composable
        fun default() = QrCodeColors(
            background = MaterialTheme.colorScheme.background,
            foreground = MaterialTheme.colorScheme.onBackground
        )
    }
}

/**
 * Defines the shape of the individual dots in a QR code.
 */
enum class DotShape {
    Circle, Square
}

/**
 * A composable that renders a QR code from a data string.
 *
 * @param data The data to encode into a QR code.
 * @param modifier The Compose modifier to apply to the QR code.
 * @param colors The color palette to use for the QR code - defaults to black and white.
 * @param dotShape The shape of the individual dots in the QR code - defaults to square.
 * @param encoder The encoder to use to encode the data into a QR code. Meant to be able to stub out in tests if needed.
 * @param overlayContent Optional content to overlay on top of the QR code. This overlay is limited to 25% of the size
 *      of the QR code and will be positioned in the center of it.
 */
@Composable
fun QrCodeView(
    data: String,
    modifier: Modifier = Modifier,
    colors: QrCodeColors = QrCodeColors.default(),
    dotShape: DotShape = DotShape.Square,
    encoder: QrEncoder = QrEncoder(),
    overlayContent: (@Composable () -> Unit)? = null,
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        QrCodeView(
            data,
            modifier = Modifier.fillMaxSize(),
            colors = colors,
            dotShape = dotShape,
            encoder = encoder
        )
        if (overlayContent != null) {
            Box(
                modifier = Modifier.fillMaxSize(fraction = 0.25f)
            ) {
                overlayContent()
            }
        }
    }
}

/**
 * A composable that renders a QR code from a data string with no overlay content.
 *
 * @param data The data to encode into a QR code.
 * @param modifier The Compose modifier to apply to the QR code.
 * @param colors The color palette to use for the QR code - defaults to black and white.
 * @param dotShape The shape of the individual dots in the QR code - defaults to square.
 * @param encoder The encoder to use to encode the data into a QR code. Meant to be able to stub out in tests if needed.
 */
@Composable
fun QrCodeView(
    data: String,
    modifier: Modifier = Modifier,
    colors: QrCodeColors = QrCodeColors.default(),
    dotShape: DotShape = DotShape.Square,
    encoder: QrEncoder = QrEncoder()
) {
    val encodedData = remember { encoder(data) }

    Canvas(modifier = modifier.background(colors.background)) {
        encodedData?.let { matrix ->
            val cellSize = size.width / matrix.width
            for (x in 0 until matrix.width) {
                for (y in 0 until matrix.height) {
                    if (matrix.get(x, y) != 1.toByte()) continue
                    when (dotShape) {
                        DotShape.Square -> drawRect(
                            color = colors.foreground,
                            topLeft = Offset(x * cellSize, y * cellSize),
                            size = Size(cellSize, cellSize)
                        )
                        DotShape.Circle -> drawCircle(
                            color = colors.foreground, center = Offset(
                                x * cellSize + cellSize / 2, y * cellSize + cellSize / 2
                            ), radius = cellSize / 2
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QrCodeViewPreview() {
    LightsparkTheme {
        QrCodeView("https://lightspark.com/dhjkshdjakhdjkashdakjsdhaksjdhaskdhaskdjhaskjddhkajhsdkajshdkasjdhkashjdaksjdhasdueury47r488484884",
            modifier = Modifier.size(400.dp),
            colors = QrCodeColors(
                background = MaterialTheme.colorScheme.surface,
                foreground = MaterialTheme.colorScheme.secondary
            ),
            dotShape = DotShape.Circle,
            overlayContent = {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.background)
                        .padding(8.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_lightspark_logo),
                        contentDescription = "Check",
                        modifier = Modifier.fillMaxSize(fraction = 0.5f),
                        tint = Color.Black
                    )
                }
            })
    }
}
