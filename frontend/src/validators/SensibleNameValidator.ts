import NotNullStringValidator from "@/validators/NotNullStringValidator";
import ValidationWrapper from "@/models/validation/ValidationWrapper";

export default class SensibleNameValidator extends NotNullStringValidator {


    getMessage(input: ValidationWrapper): string {
        if (!super.isValid(input)) {
            return super.getMessage(input);
        }
        return "The text entered is not a valid name. Only alphabetical characters, spaces, and numbers are allowed."
    }

    isValid(input: ValidationWrapper): boolean {
        if (!super.isValid(input)) {
            return false;
        }
        return ((input.value as string).search(new RegExp("^[a-zA-Z0-9]+(\\s[a-zA-Z0-9]+)*$"))) >= 0;
    }
}