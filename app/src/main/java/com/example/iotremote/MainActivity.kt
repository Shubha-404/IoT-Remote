package com.example.iotremote

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.iotremote.ui.theme.IoTRemoteTheme

class MainActivity : ComponentActivity() {
    lateinit var bluetoothManager: BluetoothManager
    lateinit var bluetoothAdapter: BluetoothAdapter
    lateinit var takePermission:ActivityResultLauncher<String>
    lateinit var takeResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bluetoothManager=getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter=bluetoothManager.adapter
        takePermission=registerForActivityResult(ActivityResultContracts.RequestPermission()){
            if(it){
                val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                takeResultLauncher.launch(intent)
            }
            else{
                Toast.makeText(applicationContext, "Bluetooth Permission is not granted.",Toast.LENGTH_SHORT).show()
            }
        }
        takeResultLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback { 
                result ->
                if(result.resultCode == RESULT_OK){
                    Toast.makeText(applicationContext, "Bluetooth ON.",Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(applicationContext, "Bluetooth OFF.",Toast.LENGTH_SHORT).show()
                }
            })

        enableEdgeToEdge()
        setContent {
            IoTRemoteTheme {
                BluetoothOnOff(
                    onTurnOnBluetooth = {
                        if (!bluetoothAdapter.isEnabled) {
                            takePermission.launch(android.Manifest.permission.BLUETOOTH_CONNECT)
                        } else {
                            Toast.makeText(applicationContext, "Bluetooth is already ON.", Toast.LENGTH_SHORT).show()
                        }
                    },
                    onTurnOffBluetooth = {
                        if (bluetoothAdapter.isEnabled) {
                            // Check permission before disabling Bluetooth
                            if (checkSelfPermission(android.Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                                val success = bluetoothAdapter.disable()
                                if (success) {
                                    Toast.makeText(applicationContext, "Turning Bluetooth OFF...", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(applicationContext, "Failed to turn Bluetooth OFF.", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(applicationContext, "Bluetooth Permission is not granted.", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(applicationContext, "Bluetooth is already OFF.", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun BluetoothOnOff(){
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center,
//        modifier = Modifier
//            .fillMaxSize()
//    ) {
//        Text(text = "Turn ON/OFF Bluetooth")
//        Spacer(modifier = Modifier.width(20.dp))
//        OutlinedButton(
//            onClick = { /*TODO*/ }) {
//            Text(text = "ON")
//        }
//        Spacer(modifier = Modifier.width(20.dp))
//        OutlinedButton(
//            onClick = { /*TODO*/ }) {
//            Text(text = "OFF")
//        }
//    }
//}

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