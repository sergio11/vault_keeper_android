package com.dreamsoftware.vaultkeeper.data.remote.exception

open class FirebaseException(message: String? = null, cause: Throwable? = null): Exception(message, cause)

// Auth Data Source
class AuthException(message: String? = null, cause: Throwable? = null): FirebaseException(message, cause)
class SignInException(message: String? = null, cause: Throwable? = null): FirebaseException(message, cause)
class SignUpException(message: String? = null, cause: Throwable? = null): FirebaseException(message, cause)

// Secret Data Source
class SecretNotFoundException(message: String? = null, cause: Throwable? = null): FirebaseException(message, cause)
class SaveSecretException(message: String? = null, cause: Throwable? = null): FirebaseException(message, cause)


abstract class SecureCardException(message: String? = null, cause: Throwable? = null): FirebaseException(message, cause)
class SaveSecureCardException(message: String? = null, cause: Throwable? = null): SecureCardException(message, cause)
class DeleteSecureCardException(message: String? = null, cause: Throwable? = null): SecureCardException(message, cause)
class FetchSecureCardException(message: String? = null, cause: Throwable? = null): SecureCardException(message, cause)

abstract class AccountException(message: String? = null, cause: Throwable? = null): FirebaseException(message, cause)
class SaveAccountException(message: String? = null, cause: Throwable? = null): AccountException(message, cause)
class DeleteAccountException(message: String? = null, cause: Throwable? = null): AccountException(message, cause)
class FetchAccountException(message: String? = null, cause: Throwable? = null): AccountException(message, cause)

