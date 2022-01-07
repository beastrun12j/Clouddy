package com.beastrun12j.clouddy.ui.components.navdrawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.beastrun12j.clouddy.R
import com.beastrun12j.clouddy.ui.theme.Vazir
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun Drawer(scope: CoroutineScope, scaffoldState: ScaffoldState, navController: NavController) {
    val items = listOf(
        NavDrawerItem.Home,
        NavDrawerItem.NextSevenDays,
    )
    Column(
        modifier = Modifier
            .background(Color(0, 0, 0, 0))
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_weather),
            contentDescription = R.string.description.toString(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(PaddingValues(8.dp, 48.dp, 8.dp, 8.dp))
                .size(80.dp)
        )

        Text(
            text = stringResource(R.string.app_name),
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier
                .padding(12.dp)
                .align(Alignment.CenterHorizontally),
            fontSize = 24.sp
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .padding(4.dp, 0.dp)
        )

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            DrawerItem(item = item, selected = currentRoute == item.route, onItemClick = {
                navController.navigate(item.route) {
                    navController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route) {
                            saveState = true
                        }
                    }
                    launchSingleTop = true
                    restoreState = true
                }
                scope.launch {
                    scaffoldState.drawerState.close()
                }
            })
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(R.string.developer_name),
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(12.dp)
                .align(Alignment.CenterHorizontally),
            fontSize = 10.sp
        )
    }
}

@Composable
fun DrawerItem(item: NavDrawerItem, selected: Boolean, onItemClick: (NavDrawerItem) -> Unit) {
    val background = if (selected) R.color.blue_200 else android.R.color.transparent
    val textColor = if (selected) Color.White else Color.Black
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onItemClick(item) })
            .height(45.dp)
            .background(colorResource(id = background))
            .padding(start = 10.dp)
    ) {
        Icon(
            item.icon,
            contentDescription = item.title,
            modifier = Modifier
                .height(22.dp)
                .width(22.dp),
            Color.Gray
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = item.title,
            color = textColor,
            textAlign = TextAlign.Start,
            fontSize = 14.sp,
            fontFamily = Vazir,
        )
    }
}