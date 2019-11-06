package miguelmaldonado.ioc.eac3_p1_maldonado_miguel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import miguelmaldonado.ioc.eac3_p1_maldonado_miguel.data.Chistes;

import java.util.List;


public class ChistesListActivity extends AppCompatActivity {

    // Indica si la vista és TwoPane o no (tablet landscape o mòbil).
    private boolean mTwoPane;
    // Classe que conté les dades de prova.
    private Chistes chistes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chistes_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

// FAB de moment deshabilitat.
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        if (findViewById(R.id.item_detail_container) != null) {
            // item_detail_container només és present al layout de la tablet en mode landscape.
            mTwoPane = true;
        }
        chistes =new Chistes(getApplicationContext());
        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, chistes.CHISTES, mTwoPane));
    }

    // Adapter del recyclerview.
    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final ChistesListActivity mParentActivity;
        private final List<Chistes.Chiste> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Chistes.Chiste item = (Chistes.Chiste) view.getTag();
                // Si tenim vista de dos panells afegim el fragment(reemplacem) al contenidor.
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(ChisteDetailFragment.ARG_ITEM_ID, item.id);
                    ChisteDetailFragment fragment = new ChisteDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();
                // Si no, iniciem l'activity ChisteDetailActivity.
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, ChisteDetailActivity.class);
                    intent.putExtra(ChisteDetailFragment.ARG_ITEM_ID, item.id);
                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(ChistesListActivity parent,
                                      List<Chistes.Chiste> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chistes_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(mValues.get(position).content);

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
    }
}
