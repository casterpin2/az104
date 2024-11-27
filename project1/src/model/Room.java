package model;

public class Room implements IRoom{
    private final String roomNumber;
    private final Double price;
    private final RoomType enumeration;

    public Room(final String roomNumber, final Double price, final RoomType enumeration) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = enumeration;
    }

    @Override
    public String getRoomNumber() {
        return this.roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return this.price;
    }

    @Override
    public RoomType getRoomType() {
        return this.enumeration;
    }

    @Override
    public boolean isFree() {
        return this.price != null && this.price.equals(0.0);
    }

    public Double getPrice() {
        return price;
    }

    public RoomType getEnumeration() {
        return enumeration;
    }

    @Override
    public String toString() {
        return "\nRoom number: '" + roomNumber +
                "\nRoom price: " + price +
                "\nRoom type: " + enumeration ;
    }
}
