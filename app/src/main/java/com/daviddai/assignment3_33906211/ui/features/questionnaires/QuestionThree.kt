package com.daviddai.assignment3_33906211.ui.features.questionnaires


import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.daviddai.assignment3_33906211.data.viewModel.QuestionnaireViewModel
import com.daviddai.assignment3_33906211.ui.components.NutriTrackButton
import com.daviddai.assignment3_33906211.ui.theme.onPrimaryColor
import java.util.Calendar

/**
 * This is the Composable for Question Three
 * @return a Composable for Question Three
 */
@Composable
fun QuestionThree(
    biggestMealTime: MutableState<String>,
    sleepTime: MutableState<String>,
    wakeUpTime: MutableState<String>,
    viewModel: QuestionnaireViewModel = viewModel()
) {
    var biggestTimeDialog = TimePickerFun(biggestMealTime)
    var sleepTimeDialog = TimePickerFun(sleepTime)
    var wakeTimeDialog = TimePickerFun(wakeUpTime)

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Timings",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )


        TimePickerLayout(
            question = "What time of day approx do you normally eat your biggest meal?",
            timeDialog = biggestTimeDialog,
            time = biggestMealTime,
            viewModel = viewModel
        )

        TimePickerLayout(
            question = "What time of day approx. do you go to sleep at night?",
            timeDialog = sleepTimeDialog,
            time = sleepTime,
            viewModel = viewModel
        )

        TimePickerLayout(
            question = "What time of day approx. do you wake up in the morning?",
            timeDialog = wakeTimeDialog,
            time = wakeUpTime,
            viewModel = viewModel
        )

        Text(
            text = viewModel.errorMessage.value ?: "",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

/**
 * This is the Composable for Time Picker Layout
 * @return a Composable for Time Picker Layout
 */
@Composable
fun TimePickerLayout(
    question: String,
    timeDialog: TimePickerDialog,
    time: MutableState<String>,
    viewModel: QuestionnaireViewModel
) {
    Row(
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        Text(
            text = question,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(end = 8.dp),
        )
    }

    NutriTrackButton(
        onClick = { timeDialog.show(); viewModel.removeErrorMessage() },
    ) {
        if (time.value != "") {
            Text(
                text = time.value,
                style = MaterialTheme.typography.titleSmall,
                color = onPrimaryColor
            )
        } else {
            Text(
                text = "Edit",
                style = MaterialTheme.typography.titleSmall,
                color = onPrimaryColor
            )

            Spacer(modifier = Modifier.width(16.dp))

            Icon(
                Icons.Filled.Edit,
                contentDescription = "Time Picker"
            )
        }
    }
}

// Reference Lecture 3
/**
 * This is the Composable for Time Picker
 * @return a Composable for Time Picker
 */
@Composable
fun TimePickerFun(mTime: MutableState<String>): TimePickerDialog {
    val mContext = LocalContext.current
    val mCalender = Calendar.getInstance()

    val mHour = mCalender.get(Calendar.HOUR_OF_DAY)
    val mMinute = mCalender.get(Calendar.MINUTE)

    mCalender.time = Calendar.getInstance().time

    return TimePickerDialog(
        mContext,
        { _, mHour: Int, mMinute: Int ->
            mTime.value = String.format("%02d:%02d", mHour, mMinute)
        }, mHour, mMinute, false
    )
}

