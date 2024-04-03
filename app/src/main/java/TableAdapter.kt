import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stacjapogodowa.R

class TableAdapter(private val dataList : List<ListItem>) : RecyclerView.Adapter<TableAdapter.TableViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.table_item, parent, false)
        return TableViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TableViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.data.text = currentItem.date
        holder.temperature.text = currentItem.temperature
        holder.humidity.text = currentItem.humidity
        holder.pressure.text = currentItem.pressure

    }

    override fun getItemCount() = dataList.size

    class TableViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val data: TextView = itemView.findViewById(R.id.date)
        val temperature: TextView = itemView.findViewById(R.id.temperature)
        val humidity: TextView = itemView.findViewById(R.id.humidity)
        val pressure: TextView = itemView.findViewById(R.id.pressure)

    }
}