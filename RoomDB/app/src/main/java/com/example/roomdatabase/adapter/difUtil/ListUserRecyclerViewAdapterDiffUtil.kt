package com.example.roomdatabase.adapter.difUtil

import androidx.recyclerview.widget.DiffUtil
import com.example.roomdatabase.data.User

class ListUserRecyclerViewAdapterDiffUtil(
    private val oldListOfUsers: List<User>,
    private val newListOfUsers: List<User>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldListOfUsers.size

    override fun getNewListSize() = newListOfUsers.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldListOfUsers[oldItemPosition].id == newListOfUsers[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        when {
            oldListOfUsers[oldItemPosition].firstName == newListOfUsers[newItemPosition].firstName -> true
            oldListOfUsers[oldItemPosition].lastName == newListOfUsers[newItemPosition].lastName -> true
            else -> false
        }
}
