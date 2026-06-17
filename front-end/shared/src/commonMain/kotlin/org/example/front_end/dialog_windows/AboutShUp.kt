package org.example.front_end.dialog_windows

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import front_end.shared.generated.resources.Res
import front_end.shared.generated.resources.logo_shanoirUp_transp_128x128
import org.jetbrains.compose.resources.painterResource

@Composable
fun AboutShUpDialogWindow(onClose: () -> Unit) {
    // Implementation of the AboutShUp dialog window
    val state = WindowState(
        width = 500.dp,
        height = 470.dp,
        position = WindowPosition.Aligned(Alignment.Center)
    )

    Window(
        title = "A propos de ShanoirUploader",
        state = state,
        onCloseRequest = onClose,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        )
        {
            /**
             * Shanoir Logo
             */
            Image(
                modifier = Modifier,
                painter = painterResource(Res.drawable.logo_shanoirUp_transp_128x128),
                contentDescription = ""
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            )
            {
                Text(
                    text = "ShanoirUploader",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )

                Text(
                    text = "dev Released : 2026-06-17"
                )

                /**
                 * URL to GitHub Documentation
                 */
                LinkAnnotationText("https://github.com/fli-iam/shanoir-ng/wiki/ShanoirUploader")

                Text(
                    text = "Droits d'auteur © INRIA 2025"
                )

                Text(
                    text = "Droits d'auteur Pseudonymus © OFSEP 2025"
                )

                Text(
                    text = "developers_shanoir@inria.fr"
                )
            }
        }
    }
}

// Source - https://stackoverflow.com/a/65656351
// Posted by Thracian, modified by community. See post 'Timeline' for change history
// Retrieved 2026-06-17, License - CC BY-SA 4.0

@Composable
fun LinkAnnotationText(link: String) {
    val annotatedLinkString: AnnotatedString = remember {
        buildAnnotatedString {
            val styleCenter = SpanStyle(
                color = Color.Blue,
                textDecoration = TextDecoration.Underline
            )

            withLink(LinkAnnotation.Url(url = link)) {
                withStyle(
                    style = styleCenter
                ) {
                    append("Documentation GitHub")
                }
            }
        }
    }

    Text(annotatedLinkString)
}
