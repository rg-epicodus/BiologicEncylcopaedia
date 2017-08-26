package models;

public class PersonalNotes extends Kingdom {
    private String writtenBy;
    private String content;
    private int id;

    public PersonalNotes(String kingdomName, String writtenBy, String content) {
        super(kingdomName);
        this.writtenBy = writtenBy;
        this.content = content;
    }

    // getters and setters

    public String getWrittenBy() {
        return writtenBy;
    }

    public void setWrittenBy(String writtenBy) {
        this.writtenBy = writtenBy;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    // equals and hashcode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonalNotes that = (PersonalNotes) o;

        if (!writtenBy.equals(that.writtenBy)) return false;
        return content.equals(that.content);
    }

    @Override
    public int hashCode() {
        int result = writtenBy.hashCode();
        result = 31 * result + content.hashCode();
        return result;
    }
}
