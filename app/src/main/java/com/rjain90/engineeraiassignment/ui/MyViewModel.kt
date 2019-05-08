package com.rjain90.engineeraiassignment.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rjain90.engineeraiassignment.data.network.NetworkManager
import com.rjain90.engineeraiassignment.model.Post
import com.rjain90.engineeraiassignment.model.PostResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyViewModel : ViewModel() {
    private val users: MutableLiveData<List<Post>> by lazy {
        MutableLiveData<List<Post>>().also {
            loadUsers(0)
        }
    }

    fun getUsers(): LiveData<List<Post>> {
        return users
    }

    fun loadUsers(page: Int) {
        NetworkManager
            .getApiInterface()!!
            .getPosts(page)
            .enqueue(object: Callback<PostResponse>{
                override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                    if(response.isSuccessful && response.body() != null){
                        users.value = response.body()!!.hits
                    }
                }

            })
    }
}