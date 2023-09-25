package Services;

import Entity.User;

import java.util.List;

public class GenerateIDService {
    public static long GenerateID(List<User> users){
        if ((long)users.size() < 1)
            return 1;

        var maxID = users.stream().max((x, y) -> x.ID > y.ID ? 1 : -1).get().ID;
        return maxID + 1;
    }
}
