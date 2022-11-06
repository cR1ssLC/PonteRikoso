package com.example.ponterikoso.mvc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.ponterikoso.databinding.ActivityMainBinding
import com.example.ponterikoso.models.CharacterModel
import com.google.gson.Gson
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainModel = MainModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGetCharacter.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                operation { mainModel.getRicknMorty((1..826).random()) }
            }
        }
    }

    private fun operation(f: suspend () -> String) {
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                binding.customProgressBar.visibility = View.VISIBLE
                binding.tvName.visibility = View.GONE
                binding.ivImg.visibility = View.GONE
                binding.tvSpecies.visibility = View.GONE
                binding.tvStatus.visibility = View.GONE
                binding.btnGetCharacter.visibility = View.GONE
            }

            val n = f()
            val gson = Gson()
            val personaje = gson.fromJson(n,CharacterModel::class.java)

            withContext(Dispatchers.Main) {
                binding.customProgressBar.visibility = View.GONE
                Glide.with(this@MainActivity)
                .load(personaje.image)
                .into(binding.ivImg)
                binding.ivImg.visibility = View.VISIBLE
                binding.tvName.visibility = View.VISIBLE
                binding.tvName.text = "${personaje.name}"
                binding.tvStatus.visibility = View.VISIBLE
                binding.tvStatus.text = "${personaje.status}"
                binding.tvSpecies.visibility = View.VISIBLE
                binding.tvSpecies.text = "${personaje.species}"
                binding.btnGetCharacter.visibility = View.VISIBLE
            }
        }
    }
}