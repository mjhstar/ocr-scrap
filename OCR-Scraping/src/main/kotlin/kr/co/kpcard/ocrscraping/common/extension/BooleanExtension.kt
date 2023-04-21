inline fun <R> Boolean.isTrueThen(block: () -> R): R? {
    return if (this) block() else null
}

inline fun <R> Boolean.isFalseThen(block: () -> R): R? {
    return if (!this) block() else null
}
