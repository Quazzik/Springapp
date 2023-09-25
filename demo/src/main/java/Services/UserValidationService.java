package Services;

public class UserValidationService {
    public static Boolean ValidateString(String str){
        return str != null && !str.isEmpty();
    }
}
