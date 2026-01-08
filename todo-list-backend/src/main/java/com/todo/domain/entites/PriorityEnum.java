package com.todo.domain.entites;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

public enum PriorityEnum {
    UNSPECIFIED(0),
    LOW(1),
    MEDIUM(2),
    HIGH(3);

    private final int code;

    PriorityEnum(int code) {
        this.code = code;
    }

    @JsonValue
    public int getCode() {
        return code;
    }

    public static PriorityEnum fromCode(int code) {
        for (PriorityEnum p : values()) {
            if (p.code == code) {
                return p;
            }
        }
        return UNSPECIFIED;
    }

    @JsonCreator
    public static PriorityEnum forValue(Object value) {
        if (value == null)
            return UNSPECIFIED;

        if (value instanceof Number) {
            return fromCode(((Number) value).intValue());
        }

        try {
            return fromCode(Integer.parseInt(value.toString()));
        } catch (NumberFormatException ex) {
            return UNSPECIFIED;
        }
    }

    @Converter
    public static class PriorityConverter implements AttributeConverter<PriorityEnum, Integer> {

        @Override
        public Integer convertToDatabaseColumn(PriorityEnum attribute) {
            if (attribute == null) return null;
            return attribute.getCode();
        }

        @Override
        public PriorityEnum convertToEntityAttribute(Integer dbData) {
            if (dbData == null) return UNSPECIFIED;
            return PriorityEnum.fromCode(dbData);
        }
    }
}
