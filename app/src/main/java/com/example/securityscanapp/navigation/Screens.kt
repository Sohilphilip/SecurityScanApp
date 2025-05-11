package com.example.securityscanapp.navigation

sealed class Screen(val route: String) {
    object EventRecords : Screen("event_records_screen")
    object AddEventDialog : Screen("add_event_dialog_screen")

    object AttendanceRecords : Screen("attendance_records_screen/{eventId}") {
        fun createRoute(eventId: Int) = "attendance_records_screen/$eventId"
    }
    object ScanQRScreen : Screen("scan_qr_screen/{eventId}") {
        fun createRoute(eventId: String) = "scan_qr_screen/$eventId"
    }

    object QRDetailsScreen : Screen("qr_details_screen/{eventId}/{qrData}") {
        fun createRoute(eventId: String, qrData: String) = "qr_details_screen/$eventId/$qrData"
    }
}
