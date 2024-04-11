package com.example.android.foodideas.Data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface Recipedao {

    @Query("SELECT * FROM Recipes")
    fun getAllRecipes(): Flow<List<Recipe>>

    @Query("SELECT * FROM Recipes WHERE name LIKE '%' || :search || '%'")
    fun searchRecipes(search: String): Flow<List<Recipe>>


    @Insert(entity = Recipe::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(Recipe: Recipe)

    @Delete(entity = Recipe::class)
    suspend fun delete(Recipe:Recipe)

}