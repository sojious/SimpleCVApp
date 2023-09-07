package co.youverify.simpleprofile

import android.graphics.Paint.Align
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import co.youverify.simpleprofile.ui.theme.SimpleProfileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleProfileTheme {
                // A surface container using the 'background' color from the theme
               //Profile()
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination ="profile"){
                    composable(route = "profile"){
                        Profile(controller=navController)
                    }
                    composable(route="githubPage"){
                        GithubWebview()
                    }
                }
            }
        }
    }
}

@Composable
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

}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    SimpleProfileTheme {
        Profile(controller = rememberNavController())
    }
}