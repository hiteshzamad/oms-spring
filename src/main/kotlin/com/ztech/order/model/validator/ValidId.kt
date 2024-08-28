package com.ztech.order.model.validator

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass


@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@MustBeDocumented
@Constraint(validatedBy = [IdValidator::class])
annotation class ValidId(
    val message: String = "Invalid id",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []
)

class IdValidator : ConstraintValidator<ValidId, Int> {
    override fun isValid(id: Int?, context: ConstraintValidatorContext?): Boolean {
        return id != null && id > 0
    }
}
