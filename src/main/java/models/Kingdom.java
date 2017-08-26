package models;


public class Kingdom {
    private String kingdomName;
    private int organismId;

    public Kingdom(String kingdomName) {
        this.kingdomName = kingdomName;
    }

    // getters and setters

    public String getKingdomName() {
        return kingdomName;
    }

    public int getOrganismId() {
        return organismId;
    }

    public void setOrganismId(int organismId) {
        this.organismId = organismId;
    }


    // equals and hashcode


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Kingdom kingdom = (Kingdom) o;

        return kingdomName.equals(kingdom.kingdomName);
    }

    @Override
    public int hashCode() {
        int result = kingdomName.hashCode();
        return result;
    }
}
