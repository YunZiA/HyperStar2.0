package com.yunzia.hyperstar.ui.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.yunzia.hyperstar.ui.base.SuperSwitch
import com.yunzia.hyperstar.utils.PreferencesUtil
import com.yunzia.hyperstar.utils.SPUtils

@Composable
fun XSuperSwitch(
    title: String,
    summary: String? = null,
    key: String,
    enabled: Boolean = true,
    insideMargin: DpSize = DpSize(24.dp, 16.dp)
) {
    val state: MutableState<Boolean> = remember { mutableStateOf(SPUtils.getBoolean(key,false)) }
    SuperSwitch(
        modifier = Modifier,
        title = title,
        summary = summary,
        checked = state.value,
        enabled = enabled,
        insideMargin = insideMargin,
        onCheckedChange = {
            state.value = it
            SPUtils.setBoolean(key,state.value)
        },
    )


}

@Composable
fun XSuperSwitch(
    title: String,
    summary: String? = null,
    key: String,
    enabled: Boolean = true,
    insideMargin: DpSize = DpSize(24.dp, 16.dp),
    onStateChanged : (Boolean) -> Unit = {}
) {
    val state: MutableState<Boolean> = remember { mutableStateOf(SPUtils.getBoolean(key,false)) }
    SuperSwitch(
        modifier = Modifier,
        title = title,
        summary = summary,
        checked = state.value,
        enabled = enabled,
        insideMargin = insideMargin,
        onCheckedChange = {
            state.value = it
            onStateChanged(state.value)
            SPUtils.setBoolean(key,state.value)
        },
    )


}

@Composable
fun XSuperSwitch(
    title: String,
    summary: String? = null,
    key: String,
    enabled: Boolean = true,
    state: MutableState<Boolean> = remember { mutableStateOf(SPUtils.getBoolean(key,false)) },
    insideMargin: DpSize = DpSize(24.dp, 16.dp)
) {

    SuperSwitch(
        modifier = Modifier,
        title = title,
        summary = summary,
        checked = state.value,
        enabled = enabled,
        insideMargin = insideMargin,
        onCheckedChange = {
            state.value = it
            SPUtils.setBoolean(key,state.value)
        },
    )


}

@Composable
fun PMiuixSuperSwitch(
    title : String,
    key : String
) {

    var MiuixSuperSwitchState by remember { mutableStateOf(PreferencesUtil.getBoolean(key,false)) }
    SuperSwitch(
        modifier = Modifier,
        title = title,
        checked = MiuixSuperSwitchState,
        insideMargin = DpSize(24.dp, 16.dp),
        onCheckedChange = {
            MiuixSuperSwitchState = it
            PreferencesUtil.putBoolean(key,MiuixSuperSwitchState)
        },
    )
}

@Composable
fun PMiuixSuperSwitch(
    title : String,
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    enabled: Boolean = true
) {
    val updatedOnCheckedChange by rememberUpdatedState(onCheckedChange)

    SuperSwitch(
        modifier = Modifier,
        title = title,
        checked = checked,
        insideMargin = DpSize(24.dp, 16.dp),
        onCheckedChange = {
            updatedOnCheckedChange?.invoke(it)
        },
    )
}