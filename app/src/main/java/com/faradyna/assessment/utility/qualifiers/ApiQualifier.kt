package com.faradyna.assessment.utility.qualifiers

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OkHttpQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UserApiQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthApiQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ProductApiQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CartApiQualifier

