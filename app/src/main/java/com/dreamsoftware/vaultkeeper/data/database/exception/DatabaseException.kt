package com.dreamsoftware.vaultkeeper.data.database.exception


open class DatabaseException(message: String? = null, cause: Throwable? = null): Exception(message, cause)

class SecureCardNotFoundException(message: String? = null, cause: Throwable? = null): DatabaseException(message, cause)
class AccessDatabaseException(message: String? = null, cause: Throwable? = null): DatabaseException(message, cause)