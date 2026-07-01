package org.interviews.doodle.exception;

public class SlotNotAvailableException extends RuntimeException {
    public SlotNotAvailableException(Long id) {
        super("Slot with id:" + id +" not available");
    }
}
