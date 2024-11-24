package adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dmp.R;
import modules.User;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<User> users;

    public ChatAdapter(List<User> users) {
        this.users = users;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        holder.nicknameTextView.setText(users.get(position).getNickname());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void updateUsers(List<User> newUsers) {
        users.clear();
        users.addAll(newUsers);
        notifyDataSetChanged();
    }

    public void updateChats(List<User> users) {
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView nicknameTextView;

        public ChatViewHolder(View itemView) {
            super(itemView);
            nicknameTextView = itemView.findViewById(R.id.Nickname);
        }
    }
}