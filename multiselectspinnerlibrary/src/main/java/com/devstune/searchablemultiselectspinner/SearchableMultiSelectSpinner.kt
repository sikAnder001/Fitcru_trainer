package com.devstune.searchablemultiselectspinner

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager

class SearchableMultiSelectSpinner {
    companion object {
        fun show(
            context: Context,
            title: String,
            doneButtonText: String,
            searchableItems: MutableList<SearchableItem>,
            list: Array<String>? = null,
            selectionCompleteListener: SelectionCompleteListener
        ) {
            val alertDialog = AlertDialog.Builder(context, R.style.AlertDialogStyle1)

            val inflater = LayoutInflater.from(context)
            val convertView = inflater.inflate(R.layout.searchable_list_layout, null)
            convertView

            alertDialog.setView(convertView)
            alertDialog.setTitle(title)

            val searchView = convertView.findViewById<SearchView>(R.id.searchView)
            val searchEditText: EditText =
                searchView.findViewById<View>(androidx.appcompat.R.id.search_src_text) as EditText
            searchEditText.setTextColor(context.resources.getColor(R.color.white))
            searchEditText.setHintTextColor(context.resources.getColor(R.color.grey_20))
            val recyclerView =
                convertView.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerView)
            val mLayoutManager = LinearLayoutManager(context)
            val adapter =
                SearchableAdapter(
                    context,
                    searchableItems,
                    searchableItems,
                    list,
                    object : SearchableAdapter.ItemClickListener {
                        override fun onItemClicked(
                            item: SearchableItem,
                            position: Int,
                            b: Boolean
                        ) {
                            for (i in searchableItems.indices) {
                                if (searchableItems[i].code == item.code) {
                                    searchableItems[i].isSelected = b
                                    break
                                }
                            }
                        }

                    }, false
                )
            recyclerView.itemAnimator = null
            recyclerView.layoutManager = mLayoutManager
            recyclerView.adapter = adapter

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    // do something on text submit
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    // do something when text changes
                    adapter.filter.filter(newText)
                    return false
                }
            })

            /* // Get the current app screen width and height
             val mDisplayMetrics = windowManager.currentWindowMetrics
             val mDisplayWidth = mDisplayMetrics.bounds.width()
             val mDisplayHeight = mDisplayMetrics.bounds.height()

             // Generate custom width and height and
             // add to the dialog attributes
             // we multiplied the width and height by 0.5,
             // meaning reducing the size to 50%
             val mLayoutParams = WindowManager.LayoutParams()
             mLayoutParams.width = (mDisplayWidth * 0.5f).toInt()
             mLayoutParams.height = (mDisplayHeight * 0.5f).toInt()
             alertDialog.window?.attributes = mLayoutParams
 */
            /* val mLayoutParams = WindowManager.LayoutParams()
             mLayoutParams.width = 450
             mLayoutParams.height = 450
             alertDialog.window?.attributes = mLayoutParams*/

            alertDialog.setPositiveButton(doneButtonText) { dialogInterface, i ->
                dialogInterface.dismiss()
                val resultList = ArrayList<SearchableItem>()
                for (index in searchableItems.indices) {
                    if (searchableItems[index].isSelected) {
                        resultList.add(searchableItems[index])
                    }
                }

                selectionCompleteListener.onCompleteSelection(resultList)
            }

            val alertDialog1 = alertDialog.create()
            /*  alertDialog1.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.rgb(255,0,0))
              alertDialog1.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.rgb(255,0,0))*/
            alertDialog1.show()

//            alertDialog.show()

        }
    }


}