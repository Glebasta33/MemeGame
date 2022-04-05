package com.trusov.memegame.data

import android.util.Log
import com.trusov.memegame.domain.entity.Meme
import com.trusov.memegame.domain.repository.Repository
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.regex.Pattern
import javax.inject.Inject

class RepositoryImpl @Inject constructor() : Repository {

    override suspend fun getMemes(): List<Meme> {
        val htmlCode = getHtmlCode()
        val names = getNames(htmlCode)
        val memes = mutableListOf<Meme>()

        for (name in names) {
            memes.add(Meme("$BASE_URL$name$END_URL"))
        }

        return memes
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

    private fun getHtmlCode(): String {
        val htmlCodeBuilder = StringBuilder()
        val url = URL(SOURCE_URL)
        val urlConnection = url.openConnection() as HttpURLConnection
        val inputStreamReader = InputStreamReader(urlConnection.inputStream)
        val bufferedReader = BufferedReader(inputStreamReader)
        var line = bufferedReader.readLine()
        while (line != null) {
            htmlCodeBuilder.append(line)
            line = bufferedReader.readLine()
            Log.d("RepositoryImplTag2", "line: $line")
        }
        return htmlCodeBuilder.toString()
    }

    override suspend fun getRandomMeme(): Meme {
        TODO("Not yet implemented")
    }

    override suspend fun getRandomQuestion(): String {
        TODO("Not yet implemented")
    }

    companion object {
        private const val SOURCE_URL = "https://apimeme.com/create/Criana"
        private const val BASE_URL = "https://apimeme.com/meme?meme="
        private const val END_URL = "&top=+&bottom=+"

        private const val PATTERN_START = "<div style=\"text-align: center\">"
        private const val PATTERN_END = "</div>"

    }
}