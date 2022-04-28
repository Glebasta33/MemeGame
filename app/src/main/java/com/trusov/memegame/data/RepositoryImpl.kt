package com.trusov.memegame.data

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.trusov.memegame.domain.entity.Game
import com.trusov.memegame.domain.entity.Meme
import com.trusov.memegame.domain.entity.Player
import com.trusov.memegame.domain.repository.Repository
import kotlinx.coroutines.tasks.await
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.regex.Pattern
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val firebase: FirebaseFirestore,
    private val auth: FirebaseAuth
) : Repository {

    override suspend fun getMemes(): List<Meme> {
            if (memes.isEmpty()) {
                for (i in 1..10) {
                    val meme = loadRandomMeme()
                    memes.add(meme)
                    firebase.collection("memes").add(meme)
                }
            }
        return memes
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun getRandomMeme(oldMeme: Meme) {
        val memeForRemove = memes.find { it == oldMeme }
        memes.remove(memeForRemove)
        val newMeme = loadRandomMeme()
        memes.add(newMeme)
    }

    override suspend fun getRandomQuestion(): String {
        val randomIndex = (Math.random() * questions.size).toInt()
        return questions[randomIndex]
    }

    override fun createNewGame(title: String, password: String) {
        firebase.collection("games").add(Game(
            title,
            null,
            0,
            password
        ))
    }

    override fun getListOfGames(): LiveData<List<Game>> {
        val games = ArrayList<Game>()
        val liveData = MutableLiveData<List<Game>>()
        firebase.collection("games").addSnapshotListener { value, error ->
            if (value != null) {
                games.clear()
                for (data in value.documents) {
                    val game = Game(
                        title = data["title"].toString(),
                        players = null,
                        round = 0,
                        password = data["password"].toString()
                    )
                    games.add(game)
                }
                liveData.value = games
            }
            if (error != null) {
                Log.d(LOG_TAG, "error ${error.message}")
            }
        }
        return liveData
    }

    override suspend fun registerToGame(playerName: String, password: String): Boolean {
        val value = firebase.collection("games").get().await()
        val data = value.documents.findLast { it["password"] == password } ?: return false
        firebase.collection("games").document(data.id)
            .update("players", FieldValue.arrayUnion(playerName))
            .addOnFailureListener {
                Log.d(LOG_TAG, "register error ${it.message}")
            }
        return true
    }

    override fun getGame(password: String): LiveData<Game?> {
        val liveData = MutableLiveData<Game?>()
        firebase.collection("games").addSnapshotListener { value, error ->
            val data = value?.documents?.findLast { it["password"] == password } ?: throw RuntimeException("Game not found")
             liveData.value = Game(
                title = data["title"]?.toString() ?: "Game not found",
                players = null,
                round = 0,
                password = data["password"].toString()
            )
        }
        return liveData
    }

    override fun singUp(name: String, login: String, password: String) {
        auth.createUserWithEmailAndPassword(login, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val respondent = auth.currentUser
                    firebase.collection("players").add(
                        Player(
                            name = name,
                            score = 0,
                            id = respondent?.uid ?: "id",
                            currentGameId = "id"
                        )
                    )
                    Log.d(LOG_TAG, "singUp success: $respondent")
                } else {
                    Log.d(LOG_TAG, "singUp error: ${task.exception}")
                }
            }

    }


    private fun getNames(htmlCode: String): List<String> {
        val pattern = Pattern.compile("$PATTERN_START(.*?)$PATTERN_END")
        val matcher = pattern.matcher(htmlCode)
        val rawNames = mutableListOf<String>()
        while (matcher.find()) {
            rawNames.add(matcher.group(1).toString())
        }
        return rawNames.map { it.replace(" ", "-") }
    }

    private fun getHtmlCode(page: Int): String {
        val htmlCodeBuilder = StringBuilder()
        val url = URL(SOURCE_URL + page)
        val urlConnection = url.openConnection() as HttpURLConnection
        val inputStreamReader = InputStreamReader(urlConnection.inputStream)
        val bufferedReader = BufferedReader(inputStreamReader)
        var line = bufferedReader.readLine()
        while (line != null) {
            htmlCodeBuilder.append(line)
            line = bufferedReader.readLine()
        }
        return htmlCodeBuilder.toString()
    }

    private suspend fun loadRandomMeme(): Meme {
        // TODO: withContext
        val randomPage = (Math.random() * 28).toInt() + 1
        val html = getHtmlCode(randomPage)
        val names = getNames(html)
        val name = names[(Math.random() * names.size).toInt()]
        return Meme("$BASE_URL$name$END_URL")
    }


    companion object {
        private const val LOG_TAG = "RepositoryImplTag"

        private val memes = mutableListOf<Meme>()
        private val questions = listOf(
            "Секс с моей бышей (бывшим) был поход на ...",
            "Быть геем - это ...",
            "Ощущения от первого секса похожи на ...",
            "\"Чёрт меня дери\", - сказал Папа Римский, когда увидел ...",
            "Мамочка, кажется, у меня под кроватью ...",
            "Моя главная эротическая фантазия - это ...",
            "На гербе Челябинска должен быть нарисован ...",
            "То чувство, когда обрёл дзен",
            "Под шляпой Боярского находится ...",
            "Как выглядеть дружелюбынм и позитивным, когда внутри ...?",
        )

        private const val SOURCE_URL = "https://apimeme.com/create/Criana?page="
        private const val BASE_URL = "https://apimeme.com/meme?meme="
        private const val END_URL = "&top=+&bottom=+"

        private const val PATTERN_START = "<div style=\"text-align: center\">"
        private const val PATTERN_END = "</div>"

    }
}