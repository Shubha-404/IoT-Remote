package com.example.iotremote.presentation

data class BluetoothUiState(
    val scannedDevices: List<com.example.iotremote.domain.BluetoothDevice> = emptyList(),
    val pairedDevices: List<com.example.iotremote.domain.BluetoothDevice> = emptyList(),
)
