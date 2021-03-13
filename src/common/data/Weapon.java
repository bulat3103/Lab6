package common.data;

/**
 * Enumeration with marine weapon constants.
 */
public enum Weapon {
    HEAVY_BOLTGUN,
    FLAMER,
    GRENADE_LAUNCHER;

    /**
     * @return String with all enum values splitted by comma.
     */
    public static String list() {
        String list = "";
        for (Weapon weapon : values()) {
            list += weapon.name() + ", ";
        }
        return list.substring(0, list.length() - 2);
    }
}
