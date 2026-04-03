public class AuthorizationService {

    public static boolean isAdmin(User user) {
        return user.getRole().equalsIgnoreCase("admin");
    }

    public static boolean isManager(User user) {
        return user.getRole().equalsIgnoreCase("manager");
    }

    public static boolean isWaiter(User user) {
        return user.getRole().equalsIgnoreCase("waiter");
    }
}