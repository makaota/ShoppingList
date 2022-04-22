package com.makaota.shoppinglist.ui.shoppingList

import com.makaota.shoppinglist.data.db.entities.ShoppingItem

interface AddDialogListener {

    fun onAddButtonClicked(item: ShoppingItem)
}