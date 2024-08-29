package com.ztech.order.validator

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
@MustBeDocumented
@Constraint(validatedBy = [UsernameValidator::class])
annotation class ValidUsername(
    val message: String = "Invalid username min 8 characters max 32 characters",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []
)

class UsernameValidator : ConstraintValidator<ValidUsername, String> {
    override fun isValid(username: String?, context: ConstraintValidatorContext?): Boolean {
        return username != null && username.length in 8..32 && username.matches(Regex("^[a-z0-9_]+$"))
    }
}
