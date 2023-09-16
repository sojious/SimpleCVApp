package co.youverify.simpleprofile


import android.icu.util.Calendar
import android.icu.util.TimeZone
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.youverify.simpleprofile.ui.theme.primary
import co.youverify.simpleprofile.ui.theme.primaryDark
import co.youverify.simpleprofile.ui.theme.secondary
import co.youverify.simpleprofile.ui.theme.secondaryDark
import co.youverify.simpleprofile.ui.theme.textColor

val timeZone: TimeZone =TimeZone.getTimeZone("Africa/Lagos")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCVScreen(
    modifier: Modifier = Modifier,
    //leaveApplicationFormState: LeaveApplicationFormState,
    //stepIndicatorBoxState:StepIndicatorBoxState,
    //onSubmit: (CVFormState) -> Unit,
    formState: CVFormState,

    ){



    val datePickerState= rememberDatePickerState()

       Box(modifier = modifier
           .fillMaxSize()
           .padding(horizontal = 20.dp)){

           CVAppTitleBar(title = "Edit CV", modifier = Modifier.padding(top=35.dp, bottom = 36.dp))

           Column(
               modifier = Modifier
                   .padding(top = 90.dp)
                   .fillMaxWidth()
                   .verticalScroll(rememberScrollState())
           ) {
               TitledLine(title = "PERSONAL INFO")

               EditCVTextField(
                   fieldTitle = "A brief description of your bio", fieldValue = formState.bio,
                   enableMultiline =true , type =InputFieldType.TEXT,onFieldValueChanged = {
                       formState.updateBio(it)
                   },
                   modifier = Modifier.padding(top=16.dp, bottom = 8.dp)
               )

               EditCVTextField(
                   fieldTitle = "First name", fieldValue = formState.firstName,
                   enableMultiline =false , type =InputFieldType.TEXT,onFieldValueChanged = {formState.updateFirstName(it)},
                   modifier = Modifier.padding(bottom = 8.dp)
               )

               EditCVTextField(
                   fieldTitle = "First name", fieldValue = formState.lastName,
                   enableMultiline =false , type =InputFieldType.TEXT,onFieldValueChanged = {formState.updateLastName(it)},
                   modifier = Modifier.padding(bottom = 8.dp)
               )

               TitledLine(title = "SOCIAL MEDIA CHANNELS")

               EditCVTextField(
                   fieldTitle = "Github profile url", fieldValue = formState.githubUrl,
                   enableMultiline =false , type =InputFieldType.TEXT,onFieldValueChanged = {formState.updateGithubUrl(it)},
                   modifier = Modifier.padding(top=16.dp)
               )

               EditCVTextField(
                   fieldTitle = "Slack username", fieldValue = formState.slackUsername,
                   enableMultiline =false , type =InputFieldType.TEXT,onFieldValueChanged = {formState.updateSlackUserName(it)},
                   modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
               )

               TitledLine(title = "EDUCATION")
               CertificateTypeDropDown(state = formState,modifier = Modifier.padding(top=16.dp,bottom = 8.dp))
               EditCVTextField(
                   fieldTitle = "Course of study", fieldValue = formState.courseOfStudy,
                   enableMultiline =false , type =InputFieldType.TEXT,onFieldValueChanged = {formState.updateCourseOfStudy(it)},
                   modifier = Modifier.padding(bottom = 8.dp)
               )

               EditCVTextField(
                   fieldTitle = "School name", fieldValue = formState.schoolName,
                   enableMultiline =false , type =InputFieldType.TEXT,onFieldValueChanged = {formState.updateSchoolName(it)},
                   modifier = Modifier.padding(top=8.dp)
               )

               Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier
                   .fillMaxWidth()
                   .padding(bottom = 8.dp) ) {
                   LeaveDateField(
                       title ="Start Date" , selectedDate =formState.startDateMillis?.toFormattedDateString()?:"",
                       index = 1, onFieldClicked = {formState.onDateFieldClicked(1)},
                       modifier = Modifier.padding(top = 10.dp)
                   )

                   LeaveDateField(
                       title ="End Date" , selectedDate =formState.endDateMillis?.toFormattedDateString()?:"",
                       index = 2, onFieldClicked = { formState.onDateFieldClicked(2) },
                       modifier = Modifier.padding(top = 10.dp)
                   )
               }

               /*Button(
                   onClick = { }, enabled =true,
                   colors = ButtonDefaults.buttonColors(containerColor = secondary),
                   content = {Text(text = "", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White) },
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(bottom = 16.dp), shape = RoundedCornerShape(4.dp)
               )*/
           }
       }


    if (formState.datePickerVisible)
        LeaveDatePicker(
            state = datePickerState,
            onOkButtonClicked = {formState.updateDateInputFieldValue(datePickerState.selectedDateMillis)},
            onCancelButtonClicked = {formState.hideDatePicker()}
        )
}

@Composable
fun EditCVTextField(
    modifier: Modifier = Modifier,
    fieldTitle: String,
    fieldValue: String,
    onFieldValueChanged: (String) -> Unit,
    enableMultiline:Boolean,
    type:InputFieldType,

    ){

    val keyboardType=when(type){
        InputFieldType.TEXT->KeyboardType.Text
        InputFieldType.NUMBER->KeyboardType.Phone
        InputFieldType.EMAIL->KeyboardType.Email
    }

    val textModifier=if (enableMultiline) Modifier
        .fillMaxWidth()
        .height(70.dp) else Modifier
        .fillMaxWidth()
        .heightIn(47.dp)
    //val borderColor=if (fieldValue.isEmpty()) secondaryDark else secondary
    Column(modifier= modifier.fillMaxWidth(),) {

        Row(modifier=Modifier.padding(bottom = 8.dp)) {
            Text(
                text = fieldTitle,
                fontSize = 12.sp,
                color = textColor,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "*",
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start = 2.dp),
                fontSize = 12.sp
            )
        }

        OutlinedTextField(
            modifier= textModifier,
            value =fieldValue ,
            onValueChange =onFieldValueChanged,
            singleLine = !enableMultiline,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            textStyle = TextStyle(fontSize = 12.sp, color = textColor),
            shape = RoundedCornerShape(8.dp) ,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = primaryDark,
                unfocusedBorderColor = primary,
            ),
        )


    }

}

@Composable
fun LeaveDateField(
    modifier: Modifier=Modifier,
    title:String,
    selectedDate:String,
    index:Int,
    onFieldClicked:(Int)->Unit
){

    val textColor=if (selectedDate.isEmpty()) Color.LightGray else primaryDark
    val borderColor=if (selectedDate.isEmpty()) Color.LightGray else primary
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        Row {
            Text(
                text = title,
                fontSize = 12.sp,
                color = secondaryDark,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 8.dp)
            )
            Text(
                text = "*",
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start = 2.dp),
                fontSize = 12.sp
            )
        }


        Row(
            modifier= Modifier
                .height(40.dp)
                .border(width = 1.dp, color = primary, shape = RoundedCornerShape(8.dp))
                .clickable {
                    onFieldClicked(index)
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(11.5.dp)
        ) {
            Image(painter = painterResource(id = R.drawable.ic_calendar), contentDescription =null,modifier=Modifier.padding(start = 14.5.dp) )
            Text(text =selectedDate.ifEmpty { "DD/MM/YY" }, fontSize = 12.sp, color = textColor,modifier=Modifier.padding(end = 45.dp))
        }
    }

}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaveDatePicker(
    modifier: Modifier = Modifier,
    state: DatePickerState,
    onOkButtonClicked: () -> Unit,
    onCancelButtonClicked: () -> Unit,
){

    Column(
        modifier = modifier.background(color = Color.White, shape = RoundedCornerShape(12.dp)),
        content = {
            val calendar= Calendar.getInstance(TimeZone.getDefault())
            DatePicker(
             state =state,
             title = {},
            showModeToggle = false,
             colors = DatePickerDefaults.colors(
                containerColor = Color.White,
                selectedDayContentColor = Color.White,
                selectedDayContainerColor = secondaryDark,
                //weekdayContentColor = bodyTextDeepColor,

                disabledDayContentColor = Color.LightGray,
                dayContentColor =Color(0xFF181E30)
            ),
            modifier = Modifier.height(460.dp),

            )

            Row(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 23.dp, top = 9.dp, bottom = 23.dp),
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                content = {
                    Text(text = "Cancel", fontSize = 12.sp, fontWeight = FontWeight.Medium,color= primary, modifier=Modifier.clickable { onCancelButtonClicked() })
                    Text(text = "Ok", fontSize = 12.sp, fontWeight = FontWeight.Medium,color= primary, modifier=Modifier.clickable { onOkButtonClicked() })
                }
            )
        }
    )
}


@Preview
@Composable
fun LeaveRequestScreenPreview() {

        Surface {
            EditCVScreen(
                formState = CVFormState(),
                //stepIndicatorBoxState = StepIndicatorBoxState(),
                //onSubmit = {},
            )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun LeaveDatePickerPreview() {
    Surface {
        //StepIndicator(active = true, completed = false, stepNumber = 2, includeLine = true)
        LeaveDatePicker(
            state = rememberDatePickerState(),
            onOkButtonClicked = {},
            onCancelButtonClicked = {},
        )
    }

}

@Preview
@Composable
fun LeaveDateFieldPreview() {

    Surface {
        //StepIndicator(active = true, completed = false, stepNumber = 2, includeLine = true)
        LeaveDateField(
            title = "Start Date",
            selectedDate = "23/09/2023",
            index = 1,
            onFieldClicked = {})
    }

}

@Preview
@Composable
fun LeaveRequestInputFieldPreview() {

    Surface {
        EditCVTextField(
            fieldTitle = "Line Manager",
            fieldValue = "Timothy Akinyelu",
            onFieldValueChanged = {},
            enableMultiline = false,
            type = InputFieldType.TEXT
        )
    }

}

enum class InputFieldType {
    TEXT,
    NUMBER,
    EMAIL
}

enum class CertificateType(val id: String) {
    BACHELORS("Bachelors"),
    MASTERS("Masters"),
    PHD("Phd"),
    HND("HND"),
    ND("ND"),


}


class CVFormState {

    var firstName by mutableStateOf("Adesoji")
        private set
    var lastName by mutableStateOf("Olowa")
        private set
    var selectedCertificateType by mutableStateOf(CertificateType.BACHELORS)
        private set
    var dropDownExpanded by mutableStateOf(false)
        private set

    var githubUrl by mutableStateOf("https://github.com/sojious")
        private set

    var slackUsername by mutableStateOf("Adesoji Olowa")
        private set

    var courseOfStudy by mutableStateOf("Environmental Engineering")
        private set

    var schoolName by mutableStateOf("University of Ibadan, Ibadan, Nigeria")
        private set

    var bio by mutableStateOf(""""Hi, I'm Adesoji Olowa, a passionate Android developer with a year of experience in designing, developing, and optimizing Android applications.My journey in the world of mobile app development began with a fascination for crafting user-friendly and innovative solutions on the Android platform.
                | I've worked on a couple of Android projects using modern Android skills and best practices and always on the look out for the next challenge.""".trimMargin())
        private set

    var clickedDateFieldIndex by mutableStateOf(1)
        private set
    var startDateMillis: Long? by mutableStateOf(null)
        private set
    var endDateMillis: Long? by mutableStateOf(null)
        private set

    var datePickerVisible by mutableStateOf(false)
        private set



    fun updateGithubUrl(newValue: String) {
        githubUrl = newValue
    }

    fun updateSlackUserName(newValue: String) {
        slackUsername = newValue
    }

    fun updateCourseOfStudy(newValue: String) {
        courseOfStudy = newValue
    }

    fun updateSchoolName(newValue: String) {
        schoolName = newValue
    }


    fun updateBio(newValue: String) {
         bio = newValue

    }
    fun onDateFieldClicked(index: Int) {
        clickedDateFieldIndex = index;datePickerVisible = true
    }

    fun updateDateInputFieldValue(newValue: Long?) {
        if (clickedDateFieldIndex == 1){
            startDateMillis = newValue
            datePickerVisible = false
        }

        else{
            endDateMillis = newValue
            datePickerVisible = false

        }

    }

    fun toggleExpandedState() {
        dropDownExpanded = !dropDownExpanded
    }

    fun updateSelectedCertificateType(type: CertificateType) {
        selectedCertificateType = when(type){
            CertificateType.BACHELORS -> CertificateType.BACHELORS
            CertificateType.MASTERS -> CertificateType.MASTERS
            CertificateType.PHD -> CertificateType.PHD
            CertificateType.HND -> CertificateType.HND
            CertificateType.ND -> CertificateType.ND
        }
    }

    fun hideDatePicker() {
        datePickerVisible = false
    }

    fun updateFirstName(newValue: String) {
        firstName = newValue
    }

    fun updateLastName(newValue: String) {
        lastName = newValue
    }


}

fun Long.toFormattedDateString(): String {

    val calendar = Calendar.getInstance(TimeZone.getDefault());
    calendar.timeInMillis = this
    return calendar[Calendar.YEAR].toString()
}


@Composable
fun CertificateTypeDropDown(
    modifier: Modifier = Modifier,
    state: CVFormState
){

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Row {
            Text(
                text ="Certificate Type",
                fontSize = 12.sp,
                color = textColor,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "*",
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start = 2.dp),
                fontSize = 12.sp
            )
        }


        Row(
            modifier= Modifier
                .fillMaxWidth()
                .height(47.dp)
                .border(width = 1.dp, color = primary, shape = RoundedCornerShape(8.dp))
                .clickable { state.toggleExpandedState() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = state.selectedCertificateType.id, fontSize = 12.sp, color = textColor,modifier=Modifier.padding(start = 12.dp))
            Spacer(modifier = Modifier.weight(1f))
            Image(painter = painterResource(id = R.drawable.ic_arrow_down), contentDescription =null,modifier=Modifier.padding(end=20.dp))
            DropdownMenu(
                expanded = state.dropDownExpanded,
                onDismissRequest = { state.toggleExpandedState()},
                modifier =Modifier.background(color= Color.White, shape = RoundedCornerShape(4.dp))

            ) {

                DropdownMenuItem(
                    text = { Text(text = CertificateType.BACHELORS.id, fontSize = 12.sp, color = primary)},
                    onClick = { state.updateSelectedCertificateType(CertificateType.BACHELORS);state.toggleExpandedState() },
                )

                DropdownMenuItem(
                    text = { Text(text = CertificateType.MASTERS.id, fontSize = 12.sp, color = primary)},
                    onClick = { state.updateSelectedCertificateType(CertificateType.MASTERS);state.toggleExpandedState() },
                )
                DropdownMenuItem(
                    text = { Text(text = CertificateType.PHD.id, fontSize = 12.sp, color = primary)},
                    onClick = { state.updateSelectedCertificateType(CertificateType.PHD);state.toggleExpandedState() },
                )

                DropdownMenuItem(
                    text = { Text(text =CertificateType.HND.id , fontSize = 12.sp, color = primary)},
                    onClick = { state.updateSelectedCertificateType(CertificateType.HND);state.toggleExpandedState() },

                    )

                DropdownMenuItem(
                    text = { Text(text =CertificateType.ND.id , fontSize = 12.sp, color = primary)},
                    onClick = { state.updateSelectedCertificateType(CertificateType.ND);state.toggleExpandedState() }
                )

            }
        }
    }
}







