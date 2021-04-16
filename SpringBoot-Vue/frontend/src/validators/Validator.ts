import ValidationWrapper from "@/models/validation/ValidationWrapper";

export default interface Validator {

    /**
     * Return a string with an error message if invalid.
     * @param input Value to validate against.
     */
    getMessage(input: ValidationWrapper): string;

    /**
     * Return true if validation passed.
     * @param input Value to validate against.
     */
    isValid(input: ValidationWrapper): boolean;

}