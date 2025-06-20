package com.straccion.appmotos1.presentation.screens.vistamimotoideal


import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.straccion.appmotos1.presentation.components.DefaultButton

data class Question(
    val text: String,
    val options: List<String>
)

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Preguntas(
    questions: List<Question>,
    onAnswerSelected: (Int, String) -> Unit,
    onComplete: () -> Unit
) {
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var isAnswered by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item {
            AnimatedContent(
                targetState = currentQuestionIndex,
                transitionSpec = {
                    slideInHorizontally { width -> width } + fadeIn() with
                            slideOutHorizontally { width -> -width } + fadeOut()
                }
            ) { targetIndex ->
                QuestionCard(
                    question = questions[targetIndex],
                    selectedAnswer = selectedAnswer,
                    onAnswerSelected = { answer ->
                        selectedAnswer = answer
                        onAnswerSelected(targetIndex, answer)
                        isAnswered = true
                    }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            DefaultButton(
                modifier = Modifier.animateContentSize(),
                text = if (currentQuestionIndex < questions.size - 1) "Siguiente" else "Finalizar",
                onClick = {
                    if (currentQuestionIndex < questions.size - 1) {
                        currentQuestionIndex++
                        selectedAnswer = null
                        isAnswered = false
                    } else {
                        onComplete()
                    }
                },
                fontSize = 18,
                enabled = isAnswered,
                colors = ButtonDefaults.buttonColors(Color(0xFF049AC7)),
                contentPadding = PaddingValues(horizontal = 25.dp, vertical = 10.dp)
            )
            LinearProgressIndicator(
                progress = (currentQuestionIndex + 1).toFloat() / questions.size,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                color = Color(0xFF6AC403)
            )
        }
    }
}

@Composable
fun QuestionCard(
    question: Question,
    selectedAnswer: String?,
    onAnswerSelected: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = question.text,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            question.options.forEach { option ->
                OptionButton(
                    text = option,
                    isSelected = option == selectedAnswer,
                    onClick = { onAnswerSelected(option) }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun OptionButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    DefaultButton(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = if (isSelected) listOf(
                        Color(0xFF4CAF50),
                        Color(0xFF2E7D32)
                    ) else listOf(Color(0xFF5A5A5A), Color(0xFF3A3A3A))
                )
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.White,
            ),
        text = text,
        fontSize = 16,
        onClick = { onClick() }
    )
}