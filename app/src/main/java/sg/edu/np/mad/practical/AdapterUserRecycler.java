package sg.edu.np.mad.practical;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterUserRecycler extends RecyclerView.Adapter<AdapterUserRecycler.ViewHolder>{

    private final UserRecyclerInterface userRecyclerInterface;
    Context context;
    ArrayList<User> userArrayList;
    public AdapterUserRecycler(Context context, ArrayList<User> userArrayList, UserRecyclerInterface userRecyclerInterface){
        this.context=context;
        this.userArrayList=userArrayList;
        this.userRecyclerInterface = userRecyclerInterface;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView rowUsername, rowDescription;
        ImageView rowProfile;
        public ViewHolder(@NonNull View itemView, UserRecyclerInterface userRecyclerInterface) {
            super(itemView);
            rowUsername = itemView.findViewById(R.id.item_User);
            rowDescription = itemView.findViewById(R.id.item_Desc);
            rowProfile = itemView.findViewById(R.id.item_ImageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(userRecyclerInterface!=null){
                        int pos = getAdapterPosition();
                        if(pos!= RecyclerView.NO_POSITION){
                            userRecyclerInterface.onUserClick(pos);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public AdapterUserRecycler.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.user_recycler_item, parent, false);

        return new AdapterUserRecycler.ViewHolder(view,userRecyclerInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterUserRecycler.ViewHolder holder, int position) {
        holder.rowUsername.setText(String.valueOf(userArrayList.get(position).getName()));
        holder.rowDescription.setText(String.valueOf(userArrayList.get(position).getDescription()));
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }
}
