package com.example.data.seed

data class RiddleItem(
    val id: String,
    val questionEn: String,
    val questionHi: String,
    val questionEs: String,
    val questionFr: String,
    val questionZh: String,
    val answerEn: String,
    val answerHi: String,
    val answerEs: String,
    val answerFr: String,
    val answerZh: String,
    val distractorsEn: List<String>,
    val distractorsHi: List<String>,
    val distractorsEs: List<String>,
    val distractorsFr: List<String>,
    val distractorsZh: List<String>,
    val hintEn: String,
    val hintHi: String,
    val hintEs: String,
    val hintFr: String,
    val hintZh: String,
    val explanationEn: String,
    val explanationHi: String,
    val explanationEs: String,
    val explanationFr: String,
    val explanationZh: String
) {
    fun getQuestion(lang: String): String = when (lang.lowercase()) {
        "hi" -> questionHi
        "es" -> questionEs
        "fr" -> questionFr
        "zh" -> questionZh
        else -> questionEn
    }

    fun getCorrectAnswer(lang: String): String = when (lang.lowercase()) {
        "hi" -> answerHi
        "es" -> answerEs
        "fr" -> answerFr
        "zh" -> answerZh
        else -> answerEn
    }

    fun getDistractors(lang: String): List<String> = when (lang.lowercase()) {
        "hi" -> distractorsHi
        "es" -> distractorsEs
        "fr" -> distractorsFr
        "zh" -> distractorsZh
        else -> distractorsEn
    }

    fun getHint(lang: String): String = when (lang.lowercase()) {
        "hi" -> hintHi
        "es" -> hintEs
        "fr" -> hintFr
        "zh" -> hintZh
        else -> hintEn
    }

    fun getExplanation(lang: String): String = when (lang.lowercase()) {
        "hi" -> explanationHi
        "es" -> explanationEs
        "fr" -> explanationFr
        "zh" -> explanationZh
        else -> explanationEn
    }

    fun getAllShuffledOptions(lang: String): List<String> {
        val all = mutableListOf(getCorrectAnswer(lang))
        all.addAll(getDistractors(lang).take(7))
        return all.shuffled()
    }
}

object RiddlesSeed {
    fun getRiddles(): List<RiddleItem> = listOf(
        RiddleItem(
            id = "riddle_1",
            questionEn = "I speak without a mouth and hear without ears. I have no body, but I come alive with wind. What am I?",
            questionHi = "बिना मुंह के बोलता हूँ, बिना कान के सुनता हूँ। शरीर नहीं है, पर हवा और पहाड़ों में गूंज उठता हूँ। मैं क्या हूँ?",
            questionEs = "Hablo sin boca y escucho sin oídos. No tengo cuerpo, pero cobro vida con el viento. ¿Qué soy?",
            questionFr = "Je parle sans bouche et j'entends sans oreilles. Je n'ai pas de corps, mais le vent me donne vie. Qui suis-je ?",
            questionZh = "我无口却能发声，无耳却能倾听；我没有躯体，却能在山谷中回响。我是什么？",
            answerEn = "Echo",
            answerHi = "गूंज (Echo)",
            answerEs = "Eco",
            answerFr = "Écho",
            answerZh = "回声",
            distractorsEn = listOf("Shadow", "Whistle", "Cloud", "River", "Mirror", "Lightning", "Rainbow"),
            distractorsHi = listOf("परछाई", "सीटी", "बादल", "नदी", "दर्पण", "बिजली", "इंद्रधनुष"),
            distractorsEs = listOf("Sombra", "Silbato", "Nube", "Río", "Espejo", "Rayo", "Arcoíris"),
            distractorsFr = listOf("Ombre", "Sifflet", "Nuage", "Rivière", "Miroir", "Éclair", "Arc-en-ciel"),
            distractorsZh = listOf("影子", "口哨", "白云", "河流", "镜子", "闪电", "彩虹"),
            hintEn = "Starts with 'E' • Think of shouting in a mountain canyon or cave.",
            hintHi = "'E' से शुरू • पहाड़ों की गुफा या घाटी में ज़ोर से चिल्लाने पर सुनाई देती है।",
            hintEs = "Empieza con 'E' • Piensa en gritar dentro de una cueva o cañón.",
            hintFr = "Commence par 'É' • Pensez à votre voix qui revient dans une gorge de montagne.",
            hintZh = "以‘回’字开头 • 想象在深山大峡谷里大喊一声听到的一阵阵声音。",
            explanationEn = "An echo is sound waves reflecting off hard rock surfaces back to your ears!",
            explanationHi = "गूंज ध्वनि तरंगों का ठोस चट्टानों से टकराकर वापस लौटने का भौतिकी प्रभाव है!",
            explanationEs = "¡El eco es el reflejo de ondas sonoras que rebotan en las montañas!",
            explanationFr = "L'écho est une onde sonore qui rebondit sur une paroi solide !",
            explanationZh = "回声是声波遇到高山坚硬峭壁障碍物时发生反射所产生的自然声学现象！"
        ),
        RiddleItem(
            id = "riddle_2",
            questionEn = "I have cities, but no houses. I have mountains, but no trees. I have water, but no fish. What am I?",
            questionHi = "मेरे पास शहर हैं पर घर नहीं, पहाड़ हैं पर पेड़ नहीं, पानी है पर मछलियां नहीं। मैं क्या हूँ?",
            questionEs = "Tengo ciudades pero no casas. Tengo montañas pero no árboles. Tengo agua pero no peces. ¿Qué soy?",
            questionFr = "J'ai des villes sans maisons, des montagnes sans arbres, de l'eau sans poissons. Qui suis-je ?",
            questionZh = "我有城市却无房屋，有高山却无绿树，有江河湖海却无游鱼。我是什么？",
            answerEn = "Map",
            answerHi = "नक्शा (Map)",
            answerEs = "Mapa",
            answerFr = "Carte",
            answerZh = "地图",
            distractorsEn = listOf("Globe", "Painting", "Desert", "Book", "Telescope", "Compass", "Dream"),
            distractorsHi = listOf("ग्लोब", "पेंटिंग", "रेगिस्तान", "किताब", "दूरबीन", "दिशा-सूचक", "सपना"),
            distractorsEs = listOf("Globo terráqueo", "Pintura", "Desierto", "Libro", "Telescopio", "Brújula", "Sueño"),
            distractorsFr = listOf("Globe", "Peinture", "Désert", "Livre", "Télescope", "Boussole", "Rêve"),
            distractorsZh = listOf("地球仪", "油画", "沙漠", "书本", "望远镜", "指南针", "梦境"),
            hintEn = "Starts with 'M' • Explorers unfold it to plan adventures.",
            hintHi = "'M' से शुरू • यात्री और खोजकर्ता इसे खोलकर रास्ता खोजते हैं।",
            hintEs = "Empieza con 'M' • Los aventureros lo despliegan para ubicarse.",
            hintFr = "Commence par 'C' • Les marins et explorateurs la déplient pour naviguer.",
            hintZh = "以‘地’字开头 • 探险家出游时展开它辨别山川和路线方位。",
            explanationEn = "A map represents geographical landscapes with printed lines and symbols!",
            explanationHi = "नक्शा कागज़ पर खींची गई रेखाओं और प्रतीकों से पूरी दुनिया को दर्शाता है!",
            explanationEs = "¡Un mapa dibuja territorios y accidentes geográficos con símbolos!",
            explanationFr = "Une carte illustre des territoires géographiques avec des symboles !",
            explanationZh = "地图运用缩微符号和经纬线描绘地理世界，没有活体植物或生命！"
        ),
        RiddleItem(
            id = "riddle_3",
            questionEn = "The more of this there is, the less you see. What is it?",
            questionHi = "यह जितना अधिक होता है, आप उतना ही कम देख पाते हैं। यह क्या है?",
            questionEs = "Cuanto más hay de esto, menos puedes ver. ¿Qué es?",
            questionFr = "Plus il y en a, moins on y voit. Qu'est-ce que c'est ?",
            questionZh = "这种东西越多，你反而看得越少。它是什么？",
            answerEn = "Darkness",
            answerHi = "अंधेरा (Darkness)",
            answerEs = "Oscuridad",
            answerFr = "Obscurité",
            answerZh = "黑暗",
            distractorsEn = listOf("Fog", "Smoke", "Sunlight", "Blizzard", "Sandstorm", "Mirage", "Water"),
            distractorsHi = listOf("कोहरा", "धुआं", "धूप", "बर्फानी तूफान", "रेत का तूफान", "मृगतृष्णा", "पानी"),
            distractorsEs = listOf("Niebla", "Humo", "Luz solar", "Ventisca", "Tormenta de arena", "Espejismo", "Agua"),
            distractorsFr = listOf("Brouillard", "Fumée", "Lumière", "Blizzard", "Tempête de sable", "Mirage", "Eau"),
            distractorsZh = listOf("浓雾", "烟雾", "阳光", "暴风雪", "沙尘暴", "海市蜃楼", "深水"),
            hintEn = "Starts with 'D' • It falls every night when the sun sets.",
            hintHi = "'D' से शुरू • सूरज ढलने पर चारों ओर फैल जाता है।",
            hintEs = "Empieza con 'O' • Llega cada noche al ocultarse el sol.",
            hintFr = "Commence par 'O' • Elle survient la nuit quand le soleil disparaît.",
            hintZh = "以‘黑’字开头 • 太阳落山夜幕降临之后笼罩大地。",
            explanationEn = "Darkness is the physical absence of photons of visible light!",
            explanationHi = "अंधेरा प्रकाश के फोटॉनों की अनुपस्थिति है जिससे आंखें देख नहीं पातीं!",
            explanationEs = "¡La oscuridad es la ausencia de luz visible!",
            explanationFr = "L'obscurité est l'absence totale de lumière pour nos yeux !",
            explanationZh = "黑暗在光学物理上是指可见光光子的缺失状态！"
        ),
        RiddleItem(
            id = "riddle_4",
            questionEn = "What has hands and a face, but cannot smile or clap?",
            questionHi = "किसके पास हाथ (सूइयां) और चेहरा (डायल) हैं, लेकिन वह न ताली बजा सकता है और न मुस्कुरा सकता है?",
            questionEs = "¿Qué tiene cara y manecillas pero no puede sonreír ni aplaudir?",
            questionFr = "Qu'est-ce qui a des aiguilles (mains) et un cadran (visage), mais ne peut ni applaudir ni sourire ?",
            questionZh = "它有‘脸庞’（表盘）也有‘双手’（指针），却不会微笑也不会鼓掌。它是什么？",
            answerEn = "Clock",
            answerHi = "घड़ी (Clock)",
            answerEs = "Reloj",
            answerFr = "Horloge",
            answerZh = "钟表",
            distractorsEn = listOf("Doll", "Robot", "Watchman", "Statue", "Sundial", "Mirror", "Compass"),
            distractorsHi = listOf("गुड़िया", "रोबोट", "चौकीदार", "मूर्ति", "धूपघड़ी", "आईना", "कंपास"),
            distractorsEs = listOf("Muñeca", "Robot", "Vigilante", "Estatua", "Reloj de sol", "Espejo", "Brújula"),
            distractorsFr = listOf("Poupée", "Robot", "Gardien", "Statue", "Cadran solaire", "Miroir", "Boussole"),
            distractorsZh = listOf("洋娃娃", "机器人", "雕像", "日晷", "镜子", "指南针", "手机"),
            hintEn = "Starts with 'C' • It ticks constantly on your wall or wrist.",
            hintHi = "'C' से शुरू • यह दीवार या कलाई पर टिक-टिक करती है।",
            hintEs = "Empieza con 'R' • Hace tic-tac en la pared o en tu muñeca.",
            hintFr = "Commence par 'H' • Fait tic-tac sur votre poignet ou au mur.",
            hintZh = "以‘钟’字开头 • 挂在墙上或戴在手腕上嘀嗒计算时间。",
            explanationEn = "A clock face shows numbers and has moving hour and minute hands!",
            explanationHi = "घड़ी के डायल को फेस और उसकी सूइयों को हैंड्स कहा जाता है!",
            explanationEs = "¡La esfera del reloj tiene manecillas para indicar horas y minutos!",
            explanationFr = "Le cadran d'une horloge porte des aiguilles qui tournent en continu !",
            explanationZh = "时钟的时针和分针在英文中被称为‘hands’，表面被称为‘face’！"
        ),
        RiddleItem(
            id = "riddle_5",
            questionEn = "What gets wetter the more it dries?",
            questionHi = "वह क्या है जो जितना ज्यादा सुखाता है, खुद उतना ही गीला हो जाता है?",
            questionEs = "¿Qué se vuelve más húmedo cuanto más seca?",
            questionFr = "Qu'est-ce qui devient de plus en plus mouillé à mesure qu'il sèche ?",
            questionZh = "它吸干别的东西越多，自己反而变得越湿。它是什么？",
            answerEn = "Towel",
            answerHi = "तौलिया (Towel)",
            answerEs = "Toalla",
            answerFr = "Serviette",
            answerZh = "毛巾",
            distractorsEn = listOf("Sponge", "Hairdryer", "Sun", "Breeze", "Napkin", "Cloud", "Raincoat"),
            distractorsHi = listOf("स्पंज", "हेयर ड्रायर", "सूरज", "हवा", "रूमाल", "बादल", "रेनकोट"),
            distractorsEs = listOf("Esponja", "Secador", "Sol", "Brisa", "Servilleta", "Nube", "Impermeable"),
            distractorsFr = listOf("Éponge", "Sèche-cheveux", "Soleil", "Brise", "Mouchoir", "Nuage", "Imperméable"),
            distractorsZh = listOf("海绵", "吹风机", "太阳", "微风", "纸巾", "白云", "雨衣"),
            hintEn = "Starts with 'T' • Used right after taking a bath.",
            hintHi = "'T' से शुरू • नहाने या हाथ धोने के बाद इस्तेमाल होता है।",
            hintEs = "Empieza con 'T' • Se usa tras bañarse o nadar en la piscina.",
            hintFr = "Commence par 'S' • Vous l'utilisez en sortant de la douche.",
            hintZh = "以‘毛’字开头 • 洗手或洗澡擦身子必备的用品。",
            explanationEn = "A towel absorbs moisture from your skin, getting damp as it dries you!",
            explanationHi = "तौलिया त्वचा से पानी सोखकर खुद गीला हो जाता है!",
            explanationEs = "¡La toalla absorbe el agua de tu cuerpo y retiene la humedad!",
            explanationFr = "La serviette absorbe l'eau de votre peau et devient humide !",
            explanationZh = "毛巾纤维具有极强吸水性，在擦干人体的同时自身吸收水分变湿！"
        ),
        RiddleItem(
            id = "riddle_6",
            questionEn = "I am not alive, but I can grow; I don't have lungs, but I need air; I don't have a mouth, but water kills me. What am I?",
            questionHi = "मैं जीवित नहीं हूँ पर बढ़ता हूँ; फेफड़े नहीं हैं पर हवा चाहिए; पानी मुझे बुझा देता है। मैं क्या हूँ?",
            questionEs = "No estoy vivo pero crezco; no tengo pulmones pero necesito aire; el agua me apaga. ¿Qué soy?",
            questionFr = "Je ne suis pas vivant mais je grandis ; je n'ai pas de poumons mais j'ai besoin d'air ; l'eau m'éteint. Qui suis-je ?",
            questionZh = "我没有生命却会蔓延成长；没有肺部却需要氧气；没有嘴巴，水却能让我熄灭。我是什么？",
            answerEn = "Fire",
            answerHi = "आग (Fire)",
            answerEs = "Fuego",
            answerFr = "Feu",
            answerZh = "火焰",
            distractorsEn = listOf("Plant", "Candle", "Volcano", "Lightning", "Rust", "Breeze", "Steam"),
            distractorsHi = listOf("पौधा", "मोमबत्ती", "ज्वालामुखी", "बिजली", "जंग", "हवा", "भाप"),
            distractorsEs = listOf("Planta", "Vela", "Volcán", "Rayo", "Óxido", "Brisa", "Vapor"),
            distractorsFr = listOf("Plante", "Bougie", "Volcan", "Foudre", "Rouille", "Brise", "Vapeur"),
            distractorsZh = listOf("植物", "蜡烛", "火山", "闪电", "铁锈", "微风", "蒸汽"),
            hintEn = "Starts with 'F' • Crackles inside warm fireplaces and campfires.",
            hintHi = "'F' से शुरू • कैंपफायर और अलाव में गर्माहट देती है।",
            hintEs = "Empieza con 'F' • Crepita en las fogatas y chimeneas.",
            hintFr = "Commence par 'F' • Il réchauffe lors des feux de camp.",
            hintZh = "以‘火’字开头 • 露营时在篝火堆中噼啪燃烧取暖。",
            explanationEn = "Fire is a chemical combustion reaction requiring heat, fuel, and oxygen gas!",
            explanationHi = "आग एक रासायनिक दहन प्रतिक्रिया है जिसमें ऑक्सीजन और ईंधन की आवश्यकता होती है!",
            explanationEs = "¡El fuego es una reacción química exotérmica que consume oxígeno!",
            explanationFr = "Le feu est une réaction de combustion exigeant de l'oxygène !",
            explanationZh = "燃烧是一种放热发光的剧烈氧化反应，需要可燃物、着火点和氧气！"
        ),
        RiddleItem(
            id = "riddle_7",
            questionEn = "What has many keys but cannot open a single door lock?",
            questionHi = "किसके पास बहुत सारी चाबियां (कीज) हैं, लेकिन वह किसी भी दरवाजे का ताला नहीं खोल सकती?",
            questionEs = "¿Qué tiene muchas teclas pero no puede abrir ninguna cerradura?",
            questionFr = "Qu'est-ce qui a de nombreuses touches (clés) mais ne peut ouvrir aucune serrure ?",
            questionZh = "它有很多‘钥匙’（琴键/按键），却连一把普通门锁也打不开。它是什么？",
            answerEn = "Piano",
            answerHi = "पियानो (Piano)",
            answerEs = "Piano",
            answerFr = "Piano",
            answerZh = "钢琴",
            distractorsEn = listOf("Keyboard", "Typewriter", "Accordion", "Keychain", "Calculator", "Padlock", "Safe"),
            distractorsHi = listOf("कीबोर्ड", "टाइपराइटर", "हार्मोनियम", "चाबी का गुच्छा", "कैलकुलेटर", "ताला", "तिजोरी"),
            distractorsEs = listOf("Teclado", "Máquina de escribir", "Acordeón", "Llavero", "Calculadora", "Candado", "Caja fuerte"),
            distractorsFr = listOf("Clavier", "Machine à écrire", "Accordéon", "Porte-clés", "Calculatrice", "Cadenas", "Coffre"),
            distractorsZh = listOf("电脑键盘", "打字机", "手风琴", "钥匙扣", "计算器", "挂锁", "保险箱"),
            hintEn = "Starts with 'P' • Has 88 musical black and white keys.",
            hintHi = "'P' से शुरू • इसमें 88 काले और सफेद संगीतमय कीज होते हैं।",
            hintEs = "Empieza con 'P' • Gran instrumento musical con 88 teclas blancas y negras.",
            hintFr = "Commence par 'P' • Grand instrument à cordes frappées avec 88 touches.",
            hintZh = "以‘钢’字开头 • 拥有88个黑白相间琴键的高雅乐器。",
            explanationEn = "A grand piano has 88 musical keys that strike internal strings to make music!",
            explanationHi = "पियानो के संगीतमय बटनों को अंग्रेजी में कीज (Keys) कहा जाता है!",
            explanationEs = "¡Las teclas de un piano activan martillos que golpean cuerdas armónicas!",
            explanationFr = "Le piano a des touches qui font vibrer des cordes pour chanter !",
            explanationZh = "钢琴拥有88个黑白击弦键盘按键，英文中被称为‘keys’！"
        ),
        RiddleItem(
            id = "riddle_8",
            questionEn = "What can travel around the entire world while staying tucked into a single corner?",
            questionHi = "वह क्या है जो एक ही कोने में चिपके रहकर पूरी दुनिया की यात्रा कर सकता है?",
            questionEs = "¿Qué puede viajar por todo el mundo quedándose en una sola esquina?",
            questionFr = "Qu'est-ce qui peut faire le tour du monde en restant coincé dans un coin ?",
            questionZh = "它始终贴在一个小小的角落里，却能环游全世界。它是什么？",
            answerEn = "Stamp",
            answerHi = "डाक टिकट (Stamp)",
            answerEs = "Estampilla",
            answerFr = "Timbre",
            answerZh = "邮票",
            distractorsEn = listOf("Envelope", "Postcard", "Coin", "Passport", "Luggage Tag", "Compass", "Letter"),
            distractorsHi = listOf("लिफाफा", "पोस्टकार्ड", "सिक्का", "पासपोर्ट", "बैग टैग", "कंपास", "पत्र"),
            distractorsEs = listOf("Sobre", "Postal", "Moneda", "Pasaporte", "Etiqueta", "Brújula", "Carta"),
            distractorsFr = listOf("Enveloppe", "Carte postale", "Pièce", "Passeport", "Étiquette", "Boussole", "Lettre"),
            distractorsZh = listOf("信封", "明信片", "硬币", "护照", "行李牌", "指南针", "信纸"),
            hintEn = "Starts with 'S' • Placed at the top right of a mailing letter.",
            hintHi = "'S' से शुरू • पत्र भेजने के लिए लिफाफे के दाहिने कोने पर चिपकाया जाता है।",
            hintEs = "Empieza con 'E' o 'S' • Se pega en la esquina superior del sobre.",
            hintFr = "Commence par 'T' • Collé dans le coin supérieur droit d'une lettre.",
            hintZh = "以‘邮’字开头 • 粘贴在寄出信封的右上角小纸片。",
            explanationEn = "A postage stamp stays in the envelope's corner as mail travels across continents!",
            explanationHi = "डाक टिकट लिफाफे के कोने में चिपका रहता है और पूरी दुनिया घूमता है!",
            explanationEs = "¡El sello postal viaja pegado a la esquina de cartas y paquetes!",
            explanationFr = "Le timbre postal voyage à travers le monde au coin de l'enveloppe !",
            explanationZh = "邮票贴在邮寄信封的右上角，随邮件飞越重洋环游世界！"
        )
    )
}
