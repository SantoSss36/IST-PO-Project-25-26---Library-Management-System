package bci;

public enum Category {
    FICTION,
    SCITECH,
    REFERENCE;

    public boolean canRequestCategory() {
        switch (this) {
            case FICTION: return true;
            case SCITECH: return true;
            case REFERENCE: return false;

            default:
                return false;
        }
    }
}

