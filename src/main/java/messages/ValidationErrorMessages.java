package messages;

public enum ValidationErrorMessages {
    ;

    /* Общи */
    public final static String BLANK_FIELD_MESSAGE = "Полето не може да бъде празно!";

    /* Валидация за добавяне на юзър */
    public final static String USERNAME_LENGTH_MESSAGE = "Потребителското име трябва да съдържа поне {min} символа!";

    public final static String PASSWORD_LENGTH_MESSAGE = "Паролата трябва да съдържа поне {min} символа!";

    public final static String EMAIL_MESSAGE = "Имейла трябва да бъде валиден!";

    public final static String EMAIL_EXIST_MESSAGE = "Акаунт с такъв имейл вече съществува.Моля опитайте да влезете в акаунта си или да възстановите паролата.";

    public final static String USERNAME_EXIST_MESSAGE = "Потребителското име е заето!";

    public final static String ACCOUNT_NOT_EXIST_MESSAGE = "Акаунт с такъв имейл не съществува.";

    /* Валидация за добавяне на проблем */
    public final static String TITLE_LENGTH_MESSAGE = "Заглавието трябва да съдържа поне {min} символа!";

    public final static String DESCRIPTION_LENGTH_MESSAGE = "Описанието трябва да съдържа поне {min} символа!";

    public final static String VALID_DATE_MESSAGE = "Датата не може да бъде в бъдеще време!";

    public final static String LANGUAGE__NOT_NULL_MESSAGE = "Езикът не може да бъде празен!";

}
