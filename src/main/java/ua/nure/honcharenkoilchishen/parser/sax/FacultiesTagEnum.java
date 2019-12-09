package ua.nure.honcharenkoilchishen.parser.sax;

public enum  FacultiesTagEnum {
    FACULTIES("faculties"),
    FACULTY("faculty"),
    TITLE("title"),
    SHORT_TITLE("shortTitle"),
    PLACE_COUNT("placeCount"),
    BUDGET_PLACE_COUNT("budgetPlaceCount"),
    FACULTY_BRANCH("facultyBranch"),
    FACULTY_ROOM_NUMBER("facultyRoomNumber"),
    YES("yes"),
    NO("no"),
    ADDITIONAL("additional"),
    BACHELOER_COUNT("bacheloerCount"),
    YEAR("year"),
    EMPLOYED_STUDENTS("employedStudents"),
    NULL("");



    private String tagName;
    private String nameSpace;

    public static FacultiesTagEnum getInstance(String tag, String nameSpace) {
        for (FacultiesTagEnum e : FacultiesTagEnum.values()) {
            if (e.getNameSpace().equals(nameSpace) && e.getTagName().equals(tag)) {
                return e;
            }
        }
        return null;
    }

    public boolean isFaculties() {return this == FACULTIES;}
    public boolean isFaculty(){ return this == FACULTY; }
    public boolean isTitle() { return this == TITLE; }
    public boolean isShortTitle() { return this == SHORT_TITLE; }
    public boolean isPlaceCount() { return this == PLACE_COUNT; }
    public boolean isBudgetPlaceCount() { return this == BUDGET_PLACE_COUNT; }
    public boolean isFacultyBranch() { return this == FACULTY_BRANCH; }
    public boolean isFacultyRoomNumber() { return this == FACULTY_ROOM_NUMBER; }
    public boolean isYes() { return this == YES; }
    public boolean isNo() { return this == NO; }
    public boolean isAdditional() { return this == ADDITIONAL; }
    public boolean isBacheloerCount() { return this == BACHELOER_COUNT; }
    public boolean isYear() { return this == YEAR; }
    public boolean isEmployedStudents() { return this == EMPLOYED_STUDENTS; }

    FacultiesTagEnum (String tag) {
        this.tagName = tag;
        this.nameSpace = "http://nure.ua/honcharenkoilchishen/faculty";
    }

    public String getTagName() {
        return tagName;
    }

    public String getNameSpace() {
        return nameSpace;
    }
}
