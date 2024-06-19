package messages;

public enum ValidationErrorMessages {
    ;
    public final static String BLANK_FIELD_MESSAGE = "Полето не може да бъде празно!";

    public final static String USERNAME_LENGTH_MESSAGE = "Потребителското име трябва да съдържа поне {min} символа!";

    public final static String PASSWORD_LENGTH_MESSAGE = "Паролата трябва да съдържа поне {min} символа!";

    public final static String EMAIL_MESSAGE = "Имейла трябва да бъде валиден!";

    public final static String EMAIL_EXIST_MESSAGE = "Акаунт с такъв имейл вече съществува.Моля опитайте да влезете в акаунта си или да възстановите паролата.";

    public final static String USERNAME_EXIST_MESSAGE = "Потребителското име е заето!";

    public final static String ACCOUNT_NOT_EXIST_MESSAGE = "Акаунт с такъв имейл не съществува.";

}
