package com.example.android.auriculoterapia_app.activities


import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.adapters.PatientsAdapter
import com.example.android.auriculoterapia_app.constants.BASE_URL
import com.example.android.auriculoterapia_app.models.Paciente
import com.example.android.auriculoterapia_app.services.PatientService
import kotlinx.android.synthetic.main.activity_search_patients.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PatientsManagementActivity : AppCompatActivity() {
    //Retrofit
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    lateinit var patientsAdapter : PatientsAdapter
    lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_patients)

        val actionBar = supportActionBar
        actionBar!!.title = "Pacientes"

        val sharedPreferences = getSharedPreferences("db_auriculoterapia",0)
        val token = sharedPreferences.getString("token", "")
        recyclerView = findViewById(R.id.recyclerViewPatients)
        patientsAdapter = PatientsAdapter()


        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = patientsAdapter
        fetchPatients(token!!, "", patientsAdapter) //Empty keyword

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.patients_menu, menu)

        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu?.findItem(R.id.patientSearch)
        val searchView = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))
        searchView.setIconifiedByDefault(false)


        val sharedPreferences = getSharedPreferences("db_auriculoterapia",0)
        val token = sharedPreferences.getString("token", "")

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                searchView.clearFocus()
                searchView.setQuery("", false)
                searchItem.collapseActionView()
                //Toast.makeText(this@PatientsManagementActivity, "Looking for: $query", Toast.LENGTH_SHORT).show()
                fetchPatients(token!!, query!!, patientsAdapter)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                fetchPatients(token!!, newText!!, patientsAdapter)
                //Toast.makeText(this@PatientsManagementActivity, "Looking for: $newText", Toast.LENGTH_SHORT).show()
                return false
            }
        })


        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return super.onOptionsItemSelected(item)
    }

    fun fetchPatients(token: String, key: String, patientsAdapter: PatientsAdapter){
        val patientService = retrofit.create(PatientService::class.java)
        patientService.listPatientsByWord("Bearer $token", key).enqueue(object: Callback<List<Paciente>>{
                override fun onFailure(call: Call<List<Paciente>>, t: Throwable) {
                    progressBarPatients.visibility = View.GONE
                    Log.i("Error", "No hay pacientes")
                }
                override fun onResponse(call: Call<List<Paciente>>, response: Response<List<Paciente>>) {
                    if(response.isSuccessful){
                        val pacientes = response.body()
                        patientsAdapter.clear()
                        patientsAdapter.submitList(pacientes!!)
                        progressBarPatients.visibility = View.GONE
                        patientsAdapter.notifyDataSetChanged()
                    }
                    else{
                        Log.i("No funcionó: ","F")
                    }
                }
            })


    }



}