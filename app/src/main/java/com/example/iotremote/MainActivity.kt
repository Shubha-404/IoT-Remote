package com.example.iotremote

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import com.example.iotremote.Bluetooth.BluetoothHelper
import com.example.iotremote.Screens.BluetoothOnOff
import com.example.iotremote.Screens.PairedDevices
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
                App(bluetoothHelper)
            }
        }
    }
} .

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun App(bluetoothHelper: BluetoothHelper) {
    BluetoothOnOff(
        onTurnOnBluetooth = { bluetoothHelper.turnOnBluetooth() },
        onTurnOffBluetooth = { bluetoothHelper.turnOffBluetooth() }
    )
    PairedDevices(bluetoothHelper = bluetoothHelper)
}


//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun PreviewBluetoothOnOff() {
//    IoTRemoteTheme {
//        BluetoothOnOff(
//            onTurnOnBluetooth = {},
//            onTurnOffBluetooth = {}
//        )
//    }
//}
