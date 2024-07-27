package com.example.iotremote

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.iotremote.ui.theme.IoTRemoteTheme

class MainActivity : ComponentActivity() {
    lateinit var bluetoothHelper: BluetoothHelper

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bluetoothHelper = BluetoothHelper(this, activityResultRegistry)

        enableEdgeToEdge()
        setContent {
            IoTRemoteTheme {
                BluetoothOnOff(
                    onTurnOnBluetooth = { bluetoothHelper.turnOnBluetooth() },
                    onTurnOffBluetooth = { bluetoothHelper.turnOffBluetooth() }
                )
                PairedDevices(bluetoothHelper = bluetoothHelper)
            }
        }
    }
}

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

@Composable
fun PairedDevices(bluetoothHelper: BluetoothHelper) {
    val pairedDevices = bluetoothHelper.pairedDevicesList()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (device in pairedDevices) {
            item {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = device.first,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = device.second,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewBluetoothOnOff() {
    IoTRemoteTheme {
        BluetoothOnOff(
            onTurnOnBluetooth = {},
            onTurnOffBluetooth = {}
        )
    }
}
