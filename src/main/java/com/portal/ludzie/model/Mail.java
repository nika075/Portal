package com.portal.ludzie.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mail {
    private String from;
    private String to;
    private String subject;
    private String text;
}
