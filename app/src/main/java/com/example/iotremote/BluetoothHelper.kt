package com.example.iotremote

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat

class BluetoothHelper(private val activity: Activity, private val registry: ActivityResultRegistry) {
    private val bluetoothManager: BluetoothManager = activity.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val bluetoothAdapter: BluetoothAdapter = bluetoothManager.adapter

    val takePermission: ActivityResultLauncher<String> =
        registry.register("requestPermission", ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                takeResultLauncher.launch(intent)
            } else {
                Toast.makeText(activity.applicationContext, "Bluetooth Permission is not granted.", Toast.LENGTH_SHORT).show()
            }
        }

    private val takeResultLauncher: ActivityResultLauncher<Intent> =
        registry.register("startActivityForResult", ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                Toast.makeText(activity.applicationContext, "Bluetooth ON.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity.applicationContext, "Bluetooth OFF.", Toast.LENGTH_SHORT).show()
            }
        }

    @RequiresApi(Build.VERSION_CODES.S)
    fun turnOnBluetooth() {
        if (!bluetoothAdapter.isEnabled) {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                takeResultLauncher.launch(intent)
            } else {
                takePermission.launch(Manifest.permission.BLUETOOTH_CONNECT)
            }
        } else {
            Toast.makeText(activity.applicationContext, "Bluetooth is already ON.", Toast.LENGTH_SHORT).show()
        }
    }

    fun turnOffBluetooth() {
        if (bluetoothAdapter.isEnabled) {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                val success = bluetoothAdapter.disable()
                if (success) {
                    Toast.makeText(activity.applicationContext, "Turning Bluetooth OFF...", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity.applicationContext, "Failed to turn Bluetooth OFF.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(activity.applicationContext, "Bluetooth Permission is not granted.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(activity.applicationContext, "Bluetooth is already OFF.", Toast.LENGTH_SHORT).show()
        }
    }


    fun pairedDevicesList(): MutableList<Pair<String, String>> {
        // Initialize an empty set for paired devices
        val pairedDevices = if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.BLUETOOTH_CONNECT
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            bluetoothAdapter.bondedDevices
        } else {
            emptySet() // Return an empty set if permission is not granted
        }

        // Create a mutable list to store the paired device data
        val pairedDeviceData: MutableList<Pair<String, String>> = mutableListOf()
        for (device in pairedDevices) {
            pairedDeviceData.add(Pair(device.name, device.address))
        }
        return pairedDeviceData
    }

}
