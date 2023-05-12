package uk.qmul.learningjourney;

import java.util.HashMap;

public class Context {
    private static Context context;

    public static HashMap<String, Object> controllers = new HashMap<String, Object>();

    private Context() {
    }

    public static Context getContext() {
        if (context == null)
            context = new Context();
        return context;
    }
}
