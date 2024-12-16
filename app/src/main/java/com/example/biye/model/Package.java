package com.example.biye.model;

public class Package {
    private String packageId;
    private String senderId;
    private String courierId;
    private String status;
    private double weight;
    private double volume;
    private double shippingCost;
    private long sentTime;
    private long estimatedArrivalTime;
    private long actualArrivalTime;
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;
    private String pickupCode;

    // Getters and Setters
    public String getPackageId() { return packageId; }
    public void setPackageId(String packageId) { this.packageId = packageId; }

    public String getSenderId() { return senderId; }
    public void setSenderId(String senderId) { this.senderId = senderId; }

    public String getCourierId() { return courierId; }
    public void setCourierId(String courierId) { this.courierId = courierId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public double getVolume() { return volume; }
    public void setVolume(double volume) { this.volume = volume; }

    public double getShippingCost() { return shippingCost; }
    public void setShippingCost(double shippingCost) { this.shippingCost = shippingCost; }

    public long getSentTime() { return sentTime; }
    public void setSentTime(long sentTime) { this.sentTime = sentTime; }

    public long getEstimatedArrivalTime() { return estimatedArrivalTime; }
    public void setEstimatedArrivalTime(long estimatedArrivalTime) { this.estimatedArrivalTime = estimatedArrivalTime; }

    public long getActualArrivalTime() { return actualArrivalTime; }
    public void setActualArrivalTime(long actualArrivalTime) { this.actualArrivalTime = actualArrivalTime; }

    public String getReceiverName() { return receiverName; }
    public void setReceiverName(String receiverName) { this.receiverName = receiverName; }

    public String getReceiverPhone() { return receiverPhone; }
    public void setReceiverPhone(String receiverPhone) { this.receiverPhone = receiverPhone; }

    public String getReceiverAddress() { return receiverAddress; }
    public void setReceiverAddress(String receiverAddress) { this.receiverAddress = receiverAddress; }

    public String getPickupCode() {
        return pickupCode;
    }

    public void setPickupCode(String pickupCode) {
        this.pickupCode = pickupCode;
    }
} 