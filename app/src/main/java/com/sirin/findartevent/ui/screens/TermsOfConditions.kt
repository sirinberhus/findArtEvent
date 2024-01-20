package com.sirin.findartevent.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sirin.findartevent.R
import com.sirin.findartevent.ui.HeadingTextComponents

@Composable
fun TermsOfConditionsScreen(){
    Surface(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White)
        .padding(16.dp)
    ) {
        HeadingTextComponents(value = stringResource(id = R.string.condition_and_terms_header))
    }
}

/*@Composable
@Preview
fun TermsOfConditionsPreview(){
    TermsOfConditionsScreen()
}
 */