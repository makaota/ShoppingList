package com.makaota.shoppinglist.ui.shoppingList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.makaota.shoppinglist.R
import com.makaota.shoppinglist.data.db.entities.ShoppingItem
import com.makaota.shoppinglist.other.ShoppingItemAdapter
import kotlinx.android.synthetic.main.activity_shopping.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class ShoppingActivity : AppCompatActivity(), KodeinAware {

    /**
     * Date Developed           : 08 April 2022
     * Date Modified            : 22 April, 2022
     * Developer name           : Seotsa Abram Makaota
     * Project Name             : ShoppingList App
     * Project Purpose          : To learn how to use room persistence library to store shopping items in an sqlite
     *                            database. To learn more about the MVVM pattern, Kotlin Coroutines
     *                            and how to do dependency injection using kodein
     *
     *
     * About the app            : this project represent the basic activity that has a floating action bar icon
     *                            that lets you to add new shopping items
     *                            when added, every item amount has a plus and minus icons to modify the
     *                            amount of that item and the amount is updated live in the database
     *                            and the item can also be deleted using the delete icon
     *
     */

    override val kodein by kodein()
    private val factory: ShoppingViewModelFactory by instance()

    lateinit var viewModel: ShoppingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping)

        viewModel = ViewModelProvider(this, factory).get(ShoppingViewModel::class.java)

        val adapter = ShoppingItemAdapter(listOf(), viewModel)

        rvShoppingItems.layoutManager = LinearLayoutManager(this,)
        rvShoppingItems.adapter = adapter

        viewModel.getAllShoppingItems().observe(this, Observer {
            adapter.items = it
            adapter.notifyDataSetChanged()
        })

        fab.setOnClickListener {
            AddShoppingItemDialog(
                this,
                object : AddDialogListener {
                    override fun onAddButtonClicked(item: ShoppingItem) {
                        viewModel.upsert(item)
                    }
                }).show()
        }
    }

}