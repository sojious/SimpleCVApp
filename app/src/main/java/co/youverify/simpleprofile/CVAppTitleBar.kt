package co.youverify.simpleprofile


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import co.youverify.simpleprofile.ui.theme.textColor


@Composable
fun CVAppTitleBar(
    modifier: Modifier = Modifier,
    title: String,
    //onBackArrowClicked: () -> Unit = {},
){
    ConstraintLayout(
        modifier = modifier.fillMaxWidth(),
    ) {

        val (backArrow,titleText) = createRefs()
        IconButton(
            modifier= Modifier
                //.fillMaxWidth()
                .size(16.dp)
                .constrainAs(backArrow) {
                    start.linkTo(parent.start, 24.dp)
                    //centerVerticallyTo(parent)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    //width=Dimension.fillToConstraints
                },
            onClick = {},
            content = {}
        )

        Text(
            text = title,
            color = textColor,
            fontSize =16.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 24.sp,
            textAlign = TextAlign.Center,
            modifier=Modifier.constrainAs(titleText){
                centerHorizontallyTo(parent)
                top.linkTo(parent.top)
                width= Dimension.value(200.dp)
            },

            )
    }
}

@Preview
@Composable
fun YouHrTitleBarPreview(){
    Surface {
        CVAppTitleBar(
            title = "Interview With Candidate For Product Design Role",
            //onBackArrowClicked = {},
        )
    }
}

