package com.trusov.memegame.data

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.trusov.memegame.domain.entity.Meme
import com.trusov.memegame.domain.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.regex.Pattern
import javax.inject.Inject

class RepositoryImpl @Inject constructor() : Repository {

    override suspend fun getMemes(): List<Meme> {
            if (memes.isEmpty()) {
                for (i in 1..10) {
                    val meme = loadRandomMeme()
                    memes.add(meme)
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
        val randomPage = (Math.random() * 28).toInt() + 1
        val html = getHtmlCode(randomPage)
        val names = getNames(html)
        val name = names[(Math.random() * names.size).toInt()]
        return Meme("$BASE_URL$name$END_URL")
    }


    companion object {
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