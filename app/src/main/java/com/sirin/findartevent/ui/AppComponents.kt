package com.sirin.findartevent.ui

import android.hardware.camera2.params.ColorSpaceTransform
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sirin.findartevent.R
import com.sirin.findartevent.ui.theme.BgColor
import com.sirin.findartevent.ui.theme.GrayColor
import com.sirin.findartevent.ui.theme.Primary
import com.sirin.findartevent.ui.theme.Secondary
import com.sirin.findartevent.ui.theme.TextColor

@Composable
fun NormalTextComponents(value:String){
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(40.dp),
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ),
        color = TextColor,
        textAlign = TextAlign.Center
    )
}

@Composable
fun HeadingTextComponents(value:String){
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(),
        style = TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal
        ),
        color = TextColor,
        textAlign = TextAlign.Center
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextField(
    labelValue: String,
    painterResource: Painter,
    value: String,
    onValueChange: (String) -> Unit,
    errorText: String? = null
) {

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(componentsShapes.small),
        label = { Text(text = labelValue) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Primary,
            focusedLabelColor = Primary,
            cursorColor = Primary,
            containerColor = BgColor
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1,
        value = value,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(
                painter = painterResource, contentDescription = ""
            )
        }

    )

    errorText?.let {
        if (it.isNotBlank()) {
            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 10.dp),
                textAlign = TextAlign.End
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(
    labelValue: String,
    painterResource: Painter,
    value: String,
    onValueChange: (String) -> Unit,
    errorText: String? = null
){

    val passwordVisible = remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(componentsShapes.small),
        label = { Text(text = labelValue) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Primary,
            focusedLabelColor = Primary,
            cursorColor = Primary,
            containerColor = BgColor
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
        singleLine = true,
        maxLines = 1,
        value = value,
        onValueChange = onValueChange,
        //alanın başında
        leadingIcon = {
            Icon(
                painter = painterResource, contentDescription = ""
            )
        },
        //alanın sonunda
        trailingIcon = {

            var description = if(passwordVisible.value){
                stringResource(id = R.string.hide_password)
            }else {
                stringResource(id = R.string.show_password)
            }

            val iconImage = if(passwordVisible.value) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.outline_visibility_24),
                    contentDescription = description
                )
            }else {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.baseline_visibility_off_24),
                    contentDescription = description
                )
            }
            IconButton(onClick = {passwordVisible.value = !passwordVisible.value}) {
               iconImage
            }
        },
        visualTransformation = if(passwordVisible.value) VisualTransformation.None else
            PasswordVisualTransformation()
    )

    errorText?.let {
        if (it.isNotBlank()) {
            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 10.dp),
                textAlign = TextAlign.End
            )
        }
    }
}

@Composable
fun CheckBoxTextField(value: String, onTextSelected: (String) -> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .heightIn(56.dp)
        .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        ) {
        val checkedState = remember {
            mutableStateOf(false)
        }
        Checkbox(
            checked = checkedState.value,
            onCheckedChange = {
                checkedState.value != checkedState.value
            }
        )
        ClickableTextField(value = value, onTextSelected)
    }
}

@Composable
fun ClickableTextField(value: String, onTextSelected : (String)-> Unit) {
    val initialText = "Devam ederek "
    val privacyPolicyText = "gizlilik politikamızı "
    val andText = "ve "
    val termsOfUseText = "kullanım koşullarımızı "
    val endText = "kabul etmiş olursunuz"
    val annotatedString = buildAnnotatedString {
        append(initialText)
        withStyle(style = SpanStyle(color = Primary)) {
            pushStringAnnotation(tag = privacyPolicyText, annotation = privacyPolicyText)
            append(privacyPolicyText)
        }
        append(andText)
        withStyle(style = SpanStyle(color = Primary)) {
            pushStringAnnotation(tag = termsOfUseText, annotation = termsOfUseText)
            append(termsOfUseText)
        }
        append(endText)
    }
    ClickableText(text = annotatedString, onClick = {offset ->
        annotatedString.getStringAnnotations(offset,offset)
            .firstOrNull()?.also {span->
                Log.d("ClickableTextField","{$span}")

                if((span.item == termsOfUseText) || (span.item == privacyPolicyText)) {
                    onTextSelected(span.item)
                }
            }
    })
}

@Composable
fun ButtonRegister(value: String){
    Button(modifier = Modifier
        .fillMaxWidth()
        .heightIn(48.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        onClick = { /*TODO*/ }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(48.dp)
                .background(
                    brush = Brush.horizontalGradient(listOf(Secondary, Primary)),
                    shape = RoundedCornerShape(58.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun DividerTextField(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(40.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
       Divider(
           modifier = Modifier
               //.fillMaxWidth()
               .weight(1f),
           color = GrayColor,
           thickness = 1.dp
       )

        Text(
            modifier =  Modifier.padding(horizontal = 8.dp),
            fontSize = 16.sp,
            text = "veya"
            )

        Divider(
            modifier = Modifier
               // .fillMaxWidth()
                .weight(1f),
            color = GrayColor,
            thickness = 1.dp
        )
    }
}

@Composable
fun ClickableLoginTextField(value: String,onTextSelected: (String) -> Unit) {
    val startText = "Zaten hesabın var mı? "
    val loginText = "Giriş yap"

    val annotatedString = buildAnnotatedString {
        append(startText)
        withStyle(style = SpanStyle(color = Primary)) {
            pushStringAnnotation(tag = loginText, annotation = loginText)
            append(loginText)
        }
    }
    ClickableText(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(40.dp),
        style = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Center
        ),
        text = annotatedString, onClick = {offset ->
        annotatedString.getStringAnnotations(offset,offset)
            .firstOrNull()?.also {span->
                Log.d("SwitchToLoginTextField", "{$span}")

                if(span.item == loginText) {
                    onTextSelected(span.item)
                }
            }


    })
}

@Composable
fun UnderLineTextComponents(value:String){
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(40.dp),
        style = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ),
        color = GrayColor,
        textAlign = TextAlign.Center,
        textDecoration = TextDecoration.Underline
    )
}
@Composable
fun ClickableLoginScreenTextField(value: String,onTextSelected: (String) -> Unit) {
    val startText = "Henüz hesabın yok mu? "
    val registerText = "Kayıt ol"

    val annotatedString = buildAnnotatedString {
        append(startText)
        withStyle(style = SpanStyle(color = Primary)) {
            pushStringAnnotation(tag = registerText, annotation = registerText)
            append(registerText)
        }
    }
    ClickableText(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(40.dp),
        style = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Center
        ),
        text = annotatedString, onClick = {offset ->
            annotatedString.getStringAnnotations(offset,offset)
                .firstOrNull()?.also {span->
                    Log.d("SwitchToLoginTextField", "{$span}")

                    if(span.item == registerText) {
                        onTextSelected(span.item)
                    }
                }


        })
}

