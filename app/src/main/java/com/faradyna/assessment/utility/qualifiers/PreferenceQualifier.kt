package com.faradyna.assessment.utility.qualifiers

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DataStoreQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DataStoreManagerQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class EncryptedPrefQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class EncryptedPrefManagerQualifier