package models;

public class Entry extends Kingdom {
    private String commonName;
    private String phylum;
    private int kingdomId;
    private int entryId;

    public Entry(String kingdomName, String commonName, String phylum) {
        super(kingdomName);
        this.commonName = commonName;
        this.phylum = phylum;
    }

    // getters and setters

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getPhylum() {
        return phylum;
    }

    public void setPhylum(String phylum) {
        this.phylum = phylum;
    }

    public int getKingdomId() {
        return kingdomId;
    }

    public void setKingdomId(int kingdomId) {
        this.kingdomId = kingdomId;
    }

    public int getEntryId() {
        return entryId;
    }

    public void setEntryId(int entryId) {
        this.entryId = entryId;
    }

// equals and hashcode


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entry entry = (Entry) o;

        if (!commonName.equals(entry.commonName)) return false;
        return phylum.equals(entry.phylum);
    }

    @Override
    public int hashCode() {
        int result = commonName.hashCode();
        result = 31 * result + phylum.hashCode();
        return result;
    }
}
