package com.example.iotremote.data

import android.bluetooth.BluetoothDevice
import com.example.iotremote.domain.BluetoothDeviceDomain

fun BluetoothDevice.toBluetoothDeviceDomain(): BluetoothDeviceDomain {
    return BluetoothDeviceDomain(
        name = name,
        address = address
    )
}