package com.faradyna.assessment.utility.qualifiers

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UserDaoQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CartDaoQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BillDaoQualifier