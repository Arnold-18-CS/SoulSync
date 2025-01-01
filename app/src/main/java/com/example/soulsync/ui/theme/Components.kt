package com.example.soulsync.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Suppress("ktlint:standard:function-naming")
@Composable
fun EmailTextField(
    text: String,
    onTextChange: (String) -> Unit,
    label: String = "Email",
    placeholder: String = "Enter our email",
    width: Dp = 350.dp,
    height: Dp = 70.dp,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = text,
        onValueChange = onTextChange,
        label = { Text(label) },
        placeholder = { Text(placeholder, color = Color(AppColors.SSGray.value)) },
        singleLine = true,
        colors =
            OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(AppColors.SSBlack.value),
                unfocusedBorderColor = Color(AppColors.SSGray.value),
                focusedLabelColor = Color(AppColors.SSBlack.value),
                unfocusedLabelColor = Color(AppColors.SSGray.value),
                cursorColor = Color(AppColors.SSBlack.value),
                focusedTextColor = Color(AppColors.SSBlack.value),
                unfocusedTextColor = Color(AppColors.SSGray.value),
                unfocusedLeadingIconColor = Color(AppColors.SSGray.value),
                focusedLeadingIconColor = Color(AppColors.SSBlack.value),
                unfocusedTrailingIconColor = Color(AppColors.SSGray.value),
                focusedTrailingIconColor = Color(AppColors.SSBlack.value),
            ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "Email",
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = "Clear Email",
                modifier = Modifier.clickable { onTextChange("") },
            )
        },
        modifier = modifier.size(width = width, height = height),
    )
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun PasswordTextField(
    password: String,
    onPasswordChange: (String) -> Unit,
    showPassword: Boolean,
    onTogglePasswordVisibility: () -> Unit,
    label: String = "Password",
    placeholder: String = "Enter your password",
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text(label) },
        placeholder = { Text(placeholder, color = Color(AppColors.SSGray.value)) },
        singleLine = true,
        colors =
            OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(AppColors.SSBlack.value),
                unfocusedBorderColor = Color(AppColors.SSGray.value),
                focusedLabelColor = Color(AppColors.SSBlack.value),
                unfocusedLabelColor = Color(AppColors.SSGray.value),
                cursorColor = Color(AppColors.SSBlack.value),
                focusedTextColor = Color(AppColors.SSBlack.value),
                unfocusedTextColor = Color(AppColors.SSGray.value),
                unfocusedLeadingIconColor = Color(AppColors.SSGray.value),
                focusedLeadingIconColor = Color(AppColors.SSBlack.value),
                unfocusedTrailingIconColor = Color(AppColors.SSGray.value),
                focusedTrailingIconColor = Color(AppColors.SSBlack.value),
            ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Password,
                contentDescription = "Password",
            )
        },
        trailingIcon = {
            if (showPassword) {
                Icon(
                    imageVector = Icons.Filled.Visibility,
                    contentDescription = "Toggle Password Visibility",
                    modifier = Modifier.clickable { onTogglePasswordVisibility() },
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.VisibilityOff,
                    contentDescription = "Toggle Password Visibility",
                    modifier = Modifier.clickable { onTogglePasswordVisibility() },
                )
            }
        },
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        modifier = modifier,
    )
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun SSPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    width: Dp = 300.dp,
    height: Dp = 70.dp,
) {
    ElevatedButton(
        onClick = onClick,
        enabled = enabled && !isLoading,
        colors =
            ButtonDefaults.elevatedButtonColors(
                containerColor = Color(AppColors.SSPrimaryPurple.value),
                contentColor = Color(AppColors.SSWhite.value),
                disabledContainerColor = Color(AppColors.SSGray.value),
                disabledContentColor = Color(AppColors.SSWhite.value),
            ),
        elevation =
            ButtonDefaults.elevatedButtonElevation(
                defaultElevation = 13.dp,
                pressedElevation = 3.dp,
            ),
        modifier = modifier.size(width = width, height = height),
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = Color(AppColors.SSWhite.value),
                modifier = Modifier.size(24.dp),
            )
        } else {
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun SSSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    width: Dp = 300.dp,
    height: Dp = 70.dp,
) {
    ElevatedButton(
        onClick = onClick,
        enabled = enabled && !isLoading,
        colors =
            ButtonDefaults.elevatedButtonColors(
                containerColor = Color(AppColors.SSSecondaryPurple.value),
                contentColor = Color(AppColors.SSPrimaryPurple.value),
                disabledContainerColor = Color(AppColors.SSGray.value),
                disabledContentColor = Color(AppColors.SSWhite.value),
            ),
        elevation =
            ButtonDefaults.elevatedButtonElevation(
                defaultElevation = 13.dp,
                pressedElevation = 3.dp,
            ),
        modifier = modifier.size(width = width, height = height),
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = Color(AppColors.SSWhite.value),
                modifier = Modifier.size(24.dp),
            )
        } else {
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}
