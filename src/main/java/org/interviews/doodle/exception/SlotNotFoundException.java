package org.interviews.doodle.exception;

public class SlotNotFoundException extends RuntimeException {

    public SlotNotFoundException(Long id) {
        super("Slot with id:" + id +" not found");
    }
}
