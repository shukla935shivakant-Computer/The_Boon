package com.example.ai

import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

object BoonBotService {
    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    suspend fun askBoonBot(userPrompt: String, currentLanguage: String): String = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY

        if (apiKey.isNotBlank() && apiKey != "MY_GEMINI_API_KEY") {
            try {
                val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$apiKey"
                val systemPrompt = """
                    You are BoonBot, an enthusiastic, cheerful, and encouraging AI companion for children and teenagers in the "Boon" app.
                    Your mission: Help kids turn screen time into active real-world exploration, nature discovery, safe kitchen experiments, brain puzzles, and daily gratitude.
                    Keep responses concise (2-4 sentences max), full of wonder and actionable real-world steps.
                    Respond naturally in the user's requested language ($currentLanguage).
                """.trimIndent()

                val requestJson = JSONObject().apply {
                    val contentsArr = JSONArray().apply {
                        put(JSONObject().apply {
                            put("role", "user")
                            put("parts", JSONArray().apply {
                                put(JSONObject().put("text", "$systemPrompt\n\nChild's question: $userPrompt"))
                            })
                        })
                    }
                    put("contents", contentsArr)
                }

                val body = requestJson.toString().toRequestBody("application/json".toMediaType())
                val request = Request.Builder().url(url).post(body).build()
                val response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    val responseStr = response.body?.string() ?: ""
                    val root = JSONObject(responseStr)
                    val text = root.optJSONArray("candidates")
                        ?.optJSONObject(0)
                        ?.optJSONObject("content")
                        ?.optJSONArray("parts")
                        ?.optJSONObject(0)
                        ?.optString("text")

                    if (!text.isNullOrBlank()) {
                        return@withContext text.trim()
                    }
                }
            } catch (_: Exception) {
                // Network error or timeout, smoothly fall back to offline intelligence
            }
        }

        // Reliable Smart Offline Fallback
        getOfflineFallbackResponse(userPrompt.lowercase(), currentLanguage)
    }

    private fun getOfflineFallbackResponse(query: String, lang: String): String {
        return when {
            query.contains("bored") || query.contains("boring") || query.contains("ऊब") || query.contains("aburrid") -> {
                when (lang.lowercase()) {
                    "hi" -> "अरे वाह! बोरियत ही सबसे बड़ा आविष्कारक बनाती है! खिड़की पर जाकर 3 अलग-अलग पक्षियों की आवाज़ पहचानें या एक गुप्त 3-पैनल कॉमिक बनाएं!"
                    "es" -> "¡El aburrimiento es la chispa de la creatividad! Ve a la cocina para hacer el volcán de lava o sal a buscar 3 hojas de formas curiosas."
                    "fr" -> "L'ennui est le début de l'imagination ! Réalisez l'expérience de la lampe à lave ou observez les oiseaux pendant 5 minutes."
                    "zh" -> "太好了！无聊正是激发天才创造力的最佳时刻！不妨去厨房做一个会喷发的火山实验，或者到窗边观察两朵变幻的云彩吧！"
                    else -> "Boredom is the mother of all great adventures! Why not try the Walking Water experiment or go outside and hunt for 3 uniquely shaped leaves?"
                }
            }
            query.contains("experiment") || query.contains("science") || query.contains("विज्ञान") || query.contains("ciencia") -> {
                when (lang.lowercase()) {
                    "hi" -> "विज्ञान का जादू हमारे चारों ओर है! बेकिंग सोडा और सिरका मिलाकर कार्बन डाइऑक्साइड का झागदार ज्वालामुखी बनाकर देखें!"
                    "es" -> "¡La ciencia está viva! Prueba el experimento de la Torre de Líquidos con miel, jabón y agua para ver cómo flotan por densidad."
                    "fr" -> "La science est magique ! Testez l'encre invisible avec du jus de citron réchauffé pour écrire des messages secrets."
                    "zh" -> "科学就在身边的厨房里！你可以试着用柠檬汁写一封隐形特工密信，在微热的灯泡下见证奇妙的显色氧化反应！"
                    else -> "Try the Density Tower experiment in the Science tab! By layering honey, dish soap, water, and oil, you can see how liquid molecules float!"
                }
            }
            query.contains("riddle") || query.contains("पहेली") || query.contains("acertijo") || query.contains("谜") -> {
                when (lang.lowercase()) {
                    "hi" -> "पहेली संकेत: हमेशा शब्दों के दोहरे अर्थ पर ध्यान दें! उदाहरण के लिए, जब घड़ी के 'हाथ' की बात हो तो वह सूइयां होती हैं!"
                    "es" -> "Pista de acertijos: ¡Piensa en metáforas! Muchas respuestas juegan con el sonido o con objetos cotidianos como relojes o espejos."
                    "fr" -> "Astuce pour les énigmes : Pensez aux jeux de mots et aux objets de tous les jours comme les miroirs ou les serviettes !"
                    "zh" -> "解谜小锦囊：仔细推敲双关词！比如‘有脸又有手’的其实是钟表，‘越吸越湿’的是毛巾哦！"
                    else -> "Riddle Tip: Look for clever double meanings! For example, things with 'hands and faces' are often ticking clocks, not monsters!"
                }
            }
            else -> {
                when (lang.lowercase()) {
                    "hi" -> "मैं बूनबॉट हूँ! आपका साहसिक मार्गदर्शक। कोई भी प्रश्न पूछें या आज के नए मिशन में उतरें और वास्तविक दुनिया को एक्सप्लोर करें!"
                    "es" -> "¡Hola, soy BoonBot! Tu guía de misiones. ¡Completa tu desafío de hoy y gana XP mientras descubres el mundo real!"
                    "fr" -> "Bonjour ! Je suis BoonBot, votre guide d'aventure. Prêt à accomplir une mission réelle et à gagner de l'expérience ?"
                    "zh" -> "你好呀！我是你的成长探险伴侣 BoonBot！随时问我实验诀窍或谜语提示，一起完成今天的现实挑战赚取经验值吧！"
                    else -> "I'm BoonBot, your exploration buddy! Pick any real-world quest from the Quests tab, grab your proof photo or audio, and level up your Sprout Explorer today!"
                }
            }
        }
    }
}
