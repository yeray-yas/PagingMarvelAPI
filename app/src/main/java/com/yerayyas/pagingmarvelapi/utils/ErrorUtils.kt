package com.yerayyas.pagingmarvelapi.utils

object ErrorUtils {
    fun getErrorMessage(responseCode: Int, query: String? = null): String {
        return when (responseCode) {
            401 -> "Error de autenticación: API key inválida"
            404 -> if (query != null) "No se encontraron superhéroes para la búsqueda '$query'" else "No se encontraron datos"
            in 500 until 600 -> "Error del servidor: $responseCode"
            else -> "Error desconocido: $responseCode"
        }
    }

    fun getNetworkErrorMessage(): String {
        return "Error de conexión a Internet"
    }
}

