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
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.soulsync.navigation.HomeDestinations

@Suppress("ktlint:standard:function-naming")
@Composable
fun DefaultTextField(
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
        label = { Text(text = label, fontFamily = FontFamily.Default) },
        placeholder = { Text(placeholder, fontFamily = FontFamily.Default, color = Color(AppColors.SSGray.value)) },
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
        label = { Text(text = label, fontFamily = FontFamily.Default) },
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
                fontFamily = FontFamily.Default,
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
                fontFamily = FontFamily.Default,
            )
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun SSNavigationBar(
    navController: NavHostController,
    state: Boolean,
    modifier: Modifier = Modifier,
) {
    val screens =
        listOf(
            HomeDestinations.Home,
            HomeDestinations.Quotes,
            HomeDestinations.Memories,
            HomeDestinations.Outbox,
        )

    NavigationBar(
        modifier = modifier,
        containerColor = AppColors.SSSecondaryPurple,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        screens.forEach { screen ->
            NavigationBarItem(
                label = { Text(text = screen.title!!) },
                icon = {
                    Icon(imageVector = screen.icon!!, contentDescription = screen.title)
                },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors =
                    NavigationBarItemDefaults.colors(
                        unselectedIconColor = AppColors.SSBlack,
                        selectedIconColor = AppColors.SSBlack,
                        selectedTextColor = AppColors.SSBlack,
                        unselectedTextColor = AppColors.SSBlack,
                        indicatorColor = AppColors.SSPrimaryPurple,
                    ),
            )
        }
    }
}
