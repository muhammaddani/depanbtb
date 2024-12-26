package com.example.tbptb.ui.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ProfileOption(
    title: String,
    onClick: () -> Unit,
    textColor: Color = Color.Black // Default color is black, but can be customized
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() }, // Make the card clickable
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF0D6)) // Optional color for the card
    ) {
        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), // Padding for text inside the card
            color = textColor, // Dynamic color for the text (e.g., red for logout)
            fontWeight = FontWeight.Bold
        )
    }
}
