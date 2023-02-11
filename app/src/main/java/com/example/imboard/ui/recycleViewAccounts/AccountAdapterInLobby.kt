import android.accounts.Account
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imboard.databinding.AccountRecycleInLobbyBinding
import com.example.imboard.model.User

public class AccountAdapterInLobby(val accounts: List<User>):RecyclerView.Adapter<AccountAdapterInLobby.AccountViewHolder>(){

    class AccountViewHolder(private val binding: AccountRecycleInLobbyBinding)
        :RecyclerView.ViewHolder(binding.root){

            fun bind(account: User){
                binding.accountUsername.text = account.name
                binding.accountPhoto.setImageURI(account.uri)
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder =
        AccountViewHolder(AccountRecycleInLobbyBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) =
        holder.bind(accounts[position])

    override fun getItemCount(): Int = accounts.size
}