package com.ztech.order.model.validator

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass


@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
@MustBeDocumented
@Constraint(validatedBy = [NameValidator::class])
annotation class ValidName(
    val message: String = "Invalid name",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []
)

class NameValidator : ConstraintValidator<ValidName, String> {
    override fun isValid(name: String?, context: ConstraintValidatorContext?): Boolean {
        return name != null && name.length in 3..64 && name.matches(Regex("^[a-zA-Z ]+$"))
    }
}
