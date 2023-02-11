import android.accounts.Account
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imboard.databinding.AccountRecycleInLobbyBinding

public class AccountAdapterInLobby(val accounts: List<Account>):RecyclerView.Adapter<AccountAdapterInLobby.AccountViewHolder>(){

    class AccountViewHolder(private val binding: AccountRecycleInLobbyBinding)
        :RecyclerView.ViewHolder(binding.root){

            fun bind(user: Account){
                binding.accountUsername.text = user.name
                binding.accountPhoto.setImageURI(user.)
            }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder =
        AccountViewHolder(AccountRecycleInLobbyBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) =
        holder.bind(accounts[position])

    override fun getItemCount(): Int = accounts.size
}