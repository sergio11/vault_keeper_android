package com.dreamsoftware.vaultkeeper.domain.exception

open class DomainRepositoryException(message: String? = null, cause: Throwable? = null): Exception(message, cause)
abstract class UserDataException(message: String? = null, cause: Throwable? = null): DomainRepositoryException(message, cause)
class CheckAuthenticatedException(message: String? = null, cause: Throwable? = null): UserDataException(message, cause)
class SignInException(message: String? = null, cause: Throwable? = null): UserDataException(message, cause)
class SignUpException(message: String? = null, cause: Throwable? = null): UserDataException(message, cause)
class CloseSessionException(message: String? = null, cause: Throwable? = null): UserDataException(message, cause)
class InvalidDataException(errors: Map<String, String>, message: String? = null, cause: Throwable? = null): UserDataException(message, cause)

class CardNotFoundException(message: String? = null, cause: Throwable? = null) : DomainRepositoryException(message, cause)
class AccountNotFoundException(message: String? = null, cause: Throwable? = null) : DomainRepositoryException(message, cause)
class RepositoryOperationException(message: String? = null, cause: Throwable? = null) : DomainRepositoryException(message, cause)
