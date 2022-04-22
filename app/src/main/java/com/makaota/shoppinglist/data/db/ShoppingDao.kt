package com.makaota.shoppinglist.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.makaota.shoppinglist.data.db.entities.ShoppingItem

@Dao
interface ShoppingDao {


    /**
     * This function is a mix of Update and Insert
     * if
     * @param item is not available in the database it will insert it
     * if it is already available then it will update it
     *
     * To tell room to do that write an @Insert annotation*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: ShoppingItem)

    @Delete
    suspend fun delete(item: ShoppingItem)

    @Query("select * from shopping_item")
    fun getAllShoppingItems(): LiveData<List<ShoppingItem>>
}