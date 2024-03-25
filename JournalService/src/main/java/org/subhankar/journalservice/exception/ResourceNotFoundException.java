package org.subhankar.journalservice.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String resource, String key, String value){
        super(String.format("Resource %s not found for %s : %s", resource, key, value));
    }
}
