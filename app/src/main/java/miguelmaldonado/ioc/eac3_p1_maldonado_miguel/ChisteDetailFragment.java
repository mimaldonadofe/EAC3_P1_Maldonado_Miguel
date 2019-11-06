package miguelmaldonado.ioc.eac3_p1_maldonado_miguel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import miguelmaldonado.ioc.eac3_p1_maldonado_miguel.data.Chistes;

public class ChisteDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";

    // Item que conté el fragment.
    private Chistes.Chiste mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ChisteDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Segons el ID que conté el argument creem el item corresponent.
            mItem = Chistes.CHISTES_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.chiste_detail, container, false);

        // Mostrem les dades.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.item_title)).setText(mItem.id +". "+ mItem.content);
            ((TextView) rootView.findViewById(R.id.item_detail)).setText(mItem.details);
        }

        return rootView;
    }
}
