package miguelmaldonado.ioc.eac3_p1_maldonado_miguel.data;

import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import miguelmaldonado.ioc.eac3_p1_maldonado_miguel.R;
public class Chistes {
    public static final List<Chiste> CHISTES = new ArrayList<Chiste>();
    public static final Map<String, Chiste> CHISTES_MAP = new HashMap<String, Chiste>();
    private static final int COUNT = 20;
    private static Context context;

    public Chistes(Context ctx) {
        this.context=ctx;
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createChiste(i));
        }
    }

    private static void addItem(Chiste item) {
        CHISTES.add(item);
        CHISTES_MAP.put(item.id, item);
    }

    private static Chiste createChiste(int position) {
        return new Chiste(String.valueOf(position),
                context.getResources().getStringArray(R.array.titulos)[position-1],
                context.getResources().getStringArray(R.array.contenido)[position-1]);
    }


    /**
     * A dummy item representing a piece of content.
     */
    public static class Chiste {
        public final String id;
        public final String content;
        public final String details;

        public Chiste(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
