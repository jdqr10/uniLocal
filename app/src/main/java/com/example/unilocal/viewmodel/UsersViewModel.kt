package com.example.unilocal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unilocal.model.City
import com.example.unilocal.model.Role
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.unilocal.model.User
import com.example.unilocal.utils.RequestResult
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class UsersViewModel: ViewModel (){

    private val _users = MutableStateFlow(emptyList<User>())
    val users: StateFlow<List<User>> = _users.asStateFlow()

    private val _userResult = MutableStateFlow<RequestResult?>(null)
    val userResult: StateFlow<RequestResult?> = _userResult.asStateFlow()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()


    val db = Firebase.firestore
    val auth: FirebaseAuth = FirebaseAuth.getInstance()


    init{
        loadUsers()
    }

    fun create(user:User){
        viewModelScope.launch {
            _userResult.value = RequestResult.Loading
            _userResult.value = runCatching { createFirebase(user) }
                .fold(
                    onSuccess = { RequestResult.Success("Usuario creado con éxito") },
                    onFailure = { RequestResult.Failure(it.message ?: "Error al registrar usuario") }
                )
        }
    }

    private suspend fun createFirebase(user: User){
        val newUser = auth.createUserWithEmailAndPassword(user.email, user.password).await()
        val uid = newUser.user?.uid ?: throw Exception("No se pudo crear el usuario")


        val userCopy = user.copy(
            id = uid,
            password = ""
        )

        db.collection("users")
            .document(uid)
            .set(userCopy)
            .await()
    }

    fun findById(id: String) {
        viewModelScope.launch {
            _userResult.value = RequestResult.Loading
            _userResult.value = runCatching { findByIdFirebase(id) }
                .fold(
                    onSuccess = { RequestResult.Success("Usuario obtenido con éxito") },
                    onFailure = { RequestResult.Failure(it.message ?: "Error al obtener usuario") }
                )
        }
    }


    fun findByEmail(email: String): User?{
        return _users.value.find { it.email == email }
    }

    fun login(email: String, password: String){
        viewModelScope.launch {
            _userResult.value = RequestResult.Loading
            _userResult.value = runCatching { loginFirebase(email, password) }
                .fold(
                    onSuccess = { RequestResult.Success("Login exitoso") },
                    onFailure = { RequestResult.Failure(it.message ?: "Error en el login") }
                )
        }
    }

    fun resetOperationResult(){
        _userResult.value = null
    }

   private suspend fun findByIdFirebase(id: String){
        val snapshot = db.collection("users")
            .document(id)
            .get()
            .await()

       val user = snapshot.toObject(User::class.java)?.apply {
           this.id = snapshot.id
       }
       _currentUser.value = user

    }

    private suspend fun loginFirebase(email: String, password: String){ //Esto sera cambiado mas adelante
        val responseUser =  auth.signInWithEmailAndPassword(email, password).await()
        val uid = responseUser.user?.uid ?: throw Exception("No se pudo obtener el usuario")
        findById(uid)
    }

    fun logout(){
        auth.signOut()
        _currentUser.value = null
    }


    fun loadUsers(){

        /*  _users.value = listOf(
             User(
                 id = "1",
                 name = "Admin",
                 username = "admin",
                 role = Role.ADMIN,
                 city = City.ARMENIA,
                 email = "admin@gmail.com",
                 password = "123456"
             ),
             User(
                 id = "2",
                 name = "Carlos",
                 username = "carlos123",
                 role = Role.USER,
                 city = City.ARMENIA,
                 email = "carlos@gmail.com",
                 password = "123456"
             ),
             User(
                 id = "3",
                 name = "Pepito",
                 username = "pepitop",
                 role = Role.USER,
                 city = City.ARMENIA,
                 email = "pepito@gmail.com",
                 password = "123456"
             )

         )*/
    }
}
