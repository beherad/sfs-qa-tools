package com.sfs.global.qa.taf.web.exceptions;

public class ElementNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -3455526176309076317L;

    public ElementNotFoundException() {
        super();
    }

    public ElementNotFoundException(final String message) {
        super(message);
    }

}
