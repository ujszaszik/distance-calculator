package com.udacity.distancecalculator.screens.selector.cities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import com.udacity.distancecalculator.cities.model.CityModel
import com.udacity.distancecalculator.screens.selector.FlagLoader
import kotlinx.android.synthetic.main.autocomplete_row.view.*
import java.util.*

class CitiesAdapter(
    context: Context,
    private val layoutResource: Int,
    val list: List<CityModel>
) : ArrayAdapter<CityModel>(context, layoutResource, list) {

    var filteredList = mutableListOf<CityModel>()

    override fun getCount(): Int = filteredList.size

    override fun getItem(position: Int): CityModel {
        return filteredList[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view =
            convertView ?: LayoutInflater.from(context).inflate(layoutResource, parent, false)

        val cityModel = filteredList[position]
        view.autoCompleteRowText.text = cityModel.city
        FlagLoader(context).loadInto(cityModel.country, view.autoCompleteRowImage)
        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                filteredList = filterResults.values as MutableList<CityModel>
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val queryString = charSequence?.toString()?.toLowerCase(Locale.getDefault())

                val filterResults = FilterResults()
                filterResults.values =
                    if (queryString.isNullOrEmpty()) list
                    else list.filter { it.containsName(queryString) }
                return filterResults
            }
        }
    }

}