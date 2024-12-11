package com.example.arrowdb.auxiliary;

public class Message {

    public static String DELETE_OR_CHANGE_STATUS_EMPLOYEE_MESSAGE = "Не возможно удалить Работника или присвоить " +
            "статус 'Закрыт', если за ним числится корпоративное имущество, или Работник закреплен за " +
            "Объектом строительства";

    public static String DELETE_INSTRUMENT_MESSAGE = "Не возможно удалить Инструмент если он числится хотя бы за " +
            "одним из Работников и/или за Объектом строительства";

    public static String DELETE_PROFESSION_MESSAGE = "Не возможно удалить Должность если она присвоена хотя бы " +
            "одному из Работников";

    public static String DELETE_OR_CHANGE_STATUS_WORK_OBJECT_MESSAGE = "Не возможно приостановить, закрыть или " +
            "удалить Объект строительства если за ним числятся Работники и/или Инструмент, или числятся " +
            "незакрытые предупреждения";

    public static String DELETE_OR_CHANGE_STATUS_SCLOTH_MESSAGE = "Не возможно удалить 'Спецодежду и СИЗ' или " +
            "присвоить статус 'Закрыт' она числится хотя бы за одним из Работников";

    public static String UNIQUE_INSTR_INV = "Данный Инвентарный номер уже существует";

    public static String UNIQUE_WORK_OBJECT = "Объект строительства с данным значением уже существует";

    public static String UNIQUE_SCLOTH_NAME = "Данная спецодежда и СИЗ уже существуют";

    public static String UNIQUE_PROFESSION = "Данная должность уже существует";

    public static String UNIQUE_CONST_CONTROL = "Данный номер предупреждения уже существует";

    public static String SPEC_CLOTH_UNIQUE_ERROR = "Данная экипировка уже закреплена за работником";

    public static String ERROR_CREATE_NEW_USER = "У данного работника отсутствует e-mail, " +
            "создать учетную запись возможности нет";

}