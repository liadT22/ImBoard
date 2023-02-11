import il.co.syntax.myapplication.util.Resource

inline fun <T> safeCall(action: () -> Resource<T>): Resource<T> {
    return try {
        action()
    } catch (e: Exception) {
        Resource.error(e.message ?: "An unknown Error Occurred")
    }
}