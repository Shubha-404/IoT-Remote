package com.example.iotremote.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BluetoothOnOff(onTurnOnBluetooth: () -> Unit, onTurnOffBluetooth: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(text = "Turn ON/OFF Bluetooth")
        Spacer(modifier = Modifier.width(20.dp))
        OutlinedButton(onClick = { onTurnOnBluetooth() }) {
            Text(text = "ON")
        }
        Spacer(modifier = Modifier.width(20.dp))
        OutlinedButton(onClick = { onTurnOffBluetooth() }) {
            Text(text = "OFF")
        }
    }
}