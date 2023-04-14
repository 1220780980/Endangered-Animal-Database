package src.backend.model;

public class Reduce {
    private int WorkerID;
    private String Address;
    private int ThreatID;
    private String MitigationMeasure;

    public Reduce(int WorkerID, String Address, int ThreatID, String MitigationMeasure) {
        this.WorkerID = WorkerID;
        this.Address = Address;
        this.ThreatID = ThreatID;
        this.MitigationMeasure = MitigationMeasure;
    }

    public int getWorkerID() {
        return WorkerID;
    }

    public void setWorkerID(int workerID) {
        WorkerID = workerID;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getThreatID() {
        return ThreatID;
    }

    public void setThreatID(int threatID) {
        ThreatID = threatID;
    }

    public String getMitigationMeasure() {
        return MitigationMeasure;
    }

    public void setMitigationMeasure(String mitigationMeasure) {
        MitigationMeasure = mitigationMeasure;
    }
}
