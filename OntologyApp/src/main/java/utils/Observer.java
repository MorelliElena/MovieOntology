package utils;

import java.util.List;

public interface Observer <X> {

    void updateResult(List<X> list);

    void notifyError(String error);
}
