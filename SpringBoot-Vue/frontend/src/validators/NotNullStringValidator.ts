import Validator from "@/validators/Validator";
import ValidationWrapper from "@/models/validation/ValidationWrapper";

export default class NotNullStringValidator implements Validator {

    getMessage(input: ValidationWrapper): string {
        return "The field cannot be empty.";
    }

    isValid(input: ValidationWrapper): boolean {
        return (input.value as string).trim().length > 0;
    }



}