package co.youverify.simpleprofile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import co.youverify.simpleprofile.ui.theme.SimpleProfileTheme
import co.youverify.simpleprofile.ui.theme.nevradaFont
import co.youverify.simpleprofile.ui.theme.primary
import co.youverify.simpleprofile.ui.theme.primaryDark
import co.youverify.simpleprofile.ui.theme.secondary
import co.youverify.simpleprofile.ui.theme.textColor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleProfileTheme {
                // A surface container using the 'background' color from the theme
               //Profile()
                val navController = rememberNavController()
                val formState = remember{CVFormState()}
                NavHost(navController = navController, startDestination ="CVHome"){
                    composable(route = "CVHome"){
                        CVPage(formState=formState){
                            navController.navigate(route="EditCV")
                        }
                    }
                    composable(route="EditCV"){
                        EditCVScreen(formState =formState )
                    }
                }
            }
        }
    }
}

@Composable
fun CVPage(
    modifier: Modifier = Modifier,
    formState: CVFormState,
    onEditClicked: () -> Unit
){
    Column(modifier= modifier
        .fillMaxSize()
        .padding(top = 24.dp, start = 24.dp, end = 20.dp)
        .verticalScroll(rememberScrollState())
    ) {
        Text(
            text ="CURRICULUM VITAE - 2023",
            color = primary,
            letterSpacing = 3.sp,
            fontSize = 8.sp,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            text ="${formState.firstName}\n ${formState.lastName}.",
            color = primaryDark,
            fontSize = 40.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = nevradaFont,
            modifier= Modifier
                .padding(top = 60.dp)
                .onGloballyPositioned {
                    it.size.width.dp
                },
            lineHeight = 33.sp

        )

        TitledLine(title="INTRODUCTION")
        Column(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            //horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {

            //Spacer(modifier = Modifier.width(24.dp))
            Text(
                text = formState.bio,
                fontSize = 12.sp,
                color = textColor,
                //modifier = Modifier.weight(1f)
            )

            SocialMediaLinks(
                iconIds = listOf(R.drawable.icons8_slack_new,R.drawable.icons8_github),
                texts = listOf(formState.slackUsername,formState.githubUrl),
                modifier = Modifier.padding(top=24.dp)
            )
        }

        TitledLine(title="EDUCATION")


           EducationSection(
               certificateName = "${formState.selectedCertificateType.id} in ${formState.courseOfStudy}",
               schoolName =formState.schoolName,
               startDate = formState.startDateMillis?.toFormattedDateString()?:"2016",
               endDate = formState.endDateMillis?.toFormattedDateString()?:"2021",
               modifier = Modifier.padding(top = 16.dp)
           )



        Button(
            onClick = {onEditClicked()},
            modifier = Modifier
                .padding(top = 60.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = secondary, contentColor = primaryDark),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(text = "Edit")
        }

    }
}

@Composable
fun EducationSection(
    modifier: Modifier = Modifier,
    certificateName:String,
    schoolName:String,
    startDate:String,
    endDate:String
) {
    Row(modifier= modifier
        .height(IntrinsicSize.Min)
        .fillMaxWidth()) {
        HorizontalDivider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.5.dp), color = secondary
        )
        Column(modifier=Modifier.padding(start = 9.dp)) {
            Text(text = startDate, color = secondary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold )
            Text(text = endDate, color = secondary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold )
        }

        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(text = certificateName, color = primaryDark, fontSize = 10.sp, fontWeight = FontWeight.SemiBold )
            Text(text = schoolName, color = textColor, fontSize = 10.sp, fontWeight = FontWeight.Medium )
        }
    }
}

@Composable
fun SocialMediaLinks(modifier: Modifier=Modifier,iconIds:List<Int>,texts:List<String>) {
    Column(modifier = modifier,verticalArrangement = Arrangement.spacedBy(4.dp)) {
        repeat(2){
            Row( horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Image(painter = painterResource(iconIds[it]), contentDescription =null,modifier=Modifier.size(16.dp) )
                Text(
                    text = texts[it],
                    color = textColor,
                    modifier=Modifier.align(Alignment.CenterVertically),
                    fontSize = 12.sp,
                    //fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun TitledLine(modifier: Modifier=Modifier, title:String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 16.dp)
    ) {
        Text(
            text =title,
            color = primary,
            letterSpacing = 3.sp,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )
        HorizontalDivider(
            modifier = Modifier
                .padding(start = 16.dp,)
                .weight(1f),
            //.align(Alignment.CenterVertically)
            thickness = 0.8.dp, color = primaryDark
        )

        //VerticalDivider()
    }
}

/*@Composable
fun Profile( modifier: Modifier = Modifier,controller:NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xff4c2b3e))
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
        //color = MaterialTheme.colorScheme.background
    ) {
        Image(
            painter = painterResource(id = R.drawable.ykn),
            contentDescription =null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .weight(1f)
                .clip(CircleShape)
            //ff4c2b3e

        )
        //Spacer(modifier = )

        Text(
            text ="Adesoji Olowa",
            fontWeight = FontWeight.SemiBold,
            fontSize = 35.sp,
            color = Color(0xff92Daff),
            modifier=Modifier.padding(top=16.dp, bottom = 16.dp)
        )
        Button(
            onClick = {controller.navigate(route = "githubPage") },
            colors = ButtonDefaults.buttonColors(
                containerColor =Color(0xff92Daff) ,
                contentColor = Color(0xff4c2b3e)
            ),
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            Text(
                text ="Open GitHub",
            )
        }

    }
}

@Composable
fun GithubWebview(){
    var pageLoadingCompleted by remember{ mutableStateOf(false) }
    var viewProgress by remember{ mutableFloatStateOf(0.0f) }
    val loadProgress by animateFloatAsState(
        targetValue =viewProgress, label = "bar",
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )

    Box(modifier = Modifier.fillMaxSize()){


        AndroidView(
            factory = {
            WebView(it).apply {
                webViewClient = WebViewClient()
                webChromeClient = object : WebChromeClient() {
                    override fun onProgressChanged(view: WebView?, newProgress: Int) {
                        //super.onProgressChanged(view, newProgress)
                        viewProgress = newProgress/100f
                        if (newProgress ==100) pageLoadingCompleted =true

                    }
                }
                loadUrl("https://github.com/sojious")
            }
            },
            modifier=Modifier.fillMaxSize()
        )
        if (!pageLoadingCompleted)
            LinearProgressIndicator(
                progress =loadProgress,
                color =Color(0xff4c2b3e),
                trackColor = Color(0xff92Daff),
                modifier = Modifier.align(Alignment.Center)
            )
    }

}*/

@Preview(showBackground = true)
@Composable
fun CVPagePreview() {
    CVPage(formState = CVFormState()){}
}

@Preview(showBackground = true)
@Composable
fun PagePreview() {
    Row(
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ){

        Text("1st Text")

            Divider(
                color = Color.Red,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )

                    Text("2nd text")

            Divider(
                color = Color.Red,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )
                    Text("3rd text")

    }
}
/*@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    SimpleProfileTheme {
        Profile(controller = rememberNavController())
    }
}*/