package com.example.data.seed

data class ScienceExperiment(
    val id: String,
    val iconEmoji: String,
    val estimatedMinutes: Int,
    val xpReward: Int,
    val difficulty: String,
    val titleEn: String,
    val titleHi: String,
    val titleEs: String,
    val titleFr: String,
    val titleZh: String,
    val subtitleEn: String,
    val subtitleHi: String,
    val subtitleEs: String,
    val subtitleFr: String,
    val subtitleZh: String,
    val materialsEn: List<String>,
    val materialsHi: List<String>,
    val materialsEs: List<String>,
    val materialsFr: List<String>,
    val materialsZh: List<String>,
    val stepsEn: List<String>,
    val stepsHi: List<String>,
    val stepsEs: List<String>,
    val stepsFr: List<String>,
    val stepsZh: List<String>,
    val explanationEn: String,
    val explanationHi: String,
    val explanationEs: String,
    val explanationFr: String,
    val explanationZh: String,
    val funFactEn: String,
    val funFactHi: String,
    val funFactEs: String,
    val funFactFr: String,
    val funFactZh: String
) {
    fun getTitle(lang: String): String = when (lang.lowercase()) {
        "hi" -> titleHi
        "es" -> titleEs
        "fr" -> titleFr
        "zh" -> titleZh
        else -> titleEn
    }

    fun getSubtitle(lang: String): String = when (lang.lowercase()) {
        "hi" -> subtitleHi
        "es" -> subtitleEs
        "fr" -> subtitleFr
        "zh" -> subtitleZh
        else -> subtitleEn
    }

    fun getMaterials(lang: String): List<String> = when (lang.lowercase()) {
        "hi" -> materialsHi
        "es" -> materialsEs
        "fr" -> materialsFr
        "zh" -> materialsZh
        else -> materialsEn
    }

    fun getSteps(lang: String): List<String> = when (lang.lowercase()) {
        "hi" -> stepsHi
        "es" -> stepsEs
        "fr" -> stepsFr
        "zh" -> stepsZh
        else -> stepsEn
    }

    fun getExplanation(lang: String): String = when (lang.lowercase()) {
        "hi" -> explanationHi
        "es" -> explanationEs
        "fr" -> explanationFr
        "zh" -> explanationZh
        else -> explanationEn
    }

    fun getFunFact(lang: String): String = when (lang.lowercase()) {
        "hi" -> funFactHi
        "es" -> funFactEs
        "fr" -> funFactFr
        "zh" -> funFactZh
        else -> funFactEn
    }
}

object ScienceExperimentsSeed {
    fun getExperiments(): List<ScienceExperiment> = listOf(
        ScienceExperiment(
            id = "volcano_eruption",
            iconEmoji = "🌋",
            estimatedMinutes = 20,
            xpReward = 120,
            difficulty = "Easy",
            titleEn = "Baking Soda Volcano Eruption",
            titleHi = "बेकिंग सोडा ज्वालामुखी विस्फोट",
            titleEs = "Erupción de Volcán con Bicarbonato",
            titleFr = "Éruption Volcanique au Bicarbonate",
            titleZh = "小苏打火山喷发大实验",
            subtitleEn = "Acid-Base Carbon Dioxide Geyser",
            subtitleHi = "अम्ल-क्षार कार्बन डाइऑक्साइड का फव्वारा",
            subtitleEs = "Géiser de Ácido y Base con Dióxido de Carbono",
            subtitleFr = "Geyser Acide-Base au Dioxyde de Carbone",
            subtitleZh = "酸碱中和反应与二氧化碳气泡喷泉",
            materialsEn = listOf(
                "2 tablespoons baking soda (sodium bicarbonate)",
                "1/2 cup white vinegar (acetic acid)",
                "Few drops of red or orange food coloring",
                "1 squirt of liquid dish soap",
                "Small plastic bottle or cup placed on a tray"
            ),
            materialsHi = listOf(
                "2 बड़े चम्मच बेकिंग सोडा",
                "1/2 कप सिरका (Vinegar)",
                "लाल या नारंगी फूड कलर की कुछ बूंदें",
                "बर्तन धोने वाला लिक्विड साबुन की 1 बूंद",
                "ट्रे में रखा हुआ छोटा प्लास्टिक कप या बोतल"
            ),
            materialsEs = listOf(
                "2 cucharadas de bicarbonato de sodio",
                "1/2 taza de vinagre blanco",
                "Gotas de colorante rojo o naranja",
                "1 chorrito de jabón líquido para platos",
                "Una botella o vaso plástico sobre una bandeja"
            ),
            materialsFr = listOf(
                "2 cuillères à soupe de bicarbonate de soude",
                "1/2 tasse de vinaigre blanc",
                "Quelques gouttes de colorant alimentaire rouge",
                "1 noisette de liquide vaisselle",
                "Une petite bouteille en plastique sur un plateau"
            ),
            materialsZh = listOf(
                "2 汤匙食用小苏打粉（碳酸氢钠）",
                "半杯白醋（乙酸）",
                "几滴红色或橙色食用色素",
                "一滴洗洁精",
                "托盘上的小塑料瓶或纸杯"
            ),
            stepsEn = listOf(
                "Place the small cup or plastic bottle firmly on a tray to catch lava.",
                "Spoon 2 tablespoons of baking soda into the bottom of the bottle.",
                "Add a squirt of dish soap and 4 drops of red food coloring.",
                "Quickly pour the 1/2 cup of vinegar into the bottle and step back!",
                "Watch glowing red foamy lava bubble up and erupt over the rim!"
            ),
            stepsHi = listOf(
                "ट्रे पर एक छोटी बोतल या प्लास्टिक कप रखें।",
                "बोतल में 2 चम्मच बेकिंग सोडा डालें।",
                "थोड़ा सा डिश सोप और 4 बूंदें लाल रंग डालें।",
                "जल्दी से आधा कप सिरका डालें और पीछे हटें!",
                "देखें कैसे झागदार लाल लावा किनारों से बाहर उबलता है!"
            ),
            stepsEs = listOf(
                "Coloca la botella o vasito sobre una bandeja para contener la espuma.",
                "Agrega 2 cucharadas de bicarbonato de sodio dentro de la botella.",
                "Añade unas gotas de jabón líquido y el colorante rojo.",
                "¡Vierte rápidamente el vinagre blanco y da un paso atrás!",
                "¡Observa cómo la lava espumosa sube y se desborda con fuerza!"
            ),
            stepsFr = listOf(
                "Placez la petite bouteille sur un plateau pour recueillir la lave.",
                "Versez 2 cuillères de bicarbonate au fond de la bouteille.",
                "Ajoutez une goutte de liquide vaisselle et le colorant rouge.",
                "Versez rapidement le vinaigre blanc et reculez d'un pas !",
                "Regardez la mousse rouge jaillir comme un vrai volcan !"
            ),
            stepsZh = listOf(
                "将小瓶子或纸杯固定在托盘中央以承接溢出泡沫。",
                "将 2 汤匙小苏打粉倒入瓶底。",
                "加入一滴洗洁精和 4 滴红色食用色素。",
                "迅速将半杯白醋倒入瓶中，稍稍后退一步观察！",
                "见证鲜红浓密的‘岩浆’泡沫汩汩喷涌而出！"
            ),
            explanationEn = "Baking soda (base) reacts with vinegar (acid) producing water and carbon dioxide gas (CO₂). Soap traps the gas into thousands of bubbling lava pockets!",
            explanationHi = "बेकिंग सोडा (क्षार) और सिरका (अम्ल) मिलकर कार्बन डाइऑक्साइड गैस (CO₂) बनाते हैं। साबुन इस गैस को रोककर झागदार लावा बनाता है!",
            explanationEs = "¡El bicarbonato reacciona con el vinagre liberando dióxido de carbono (CO₂)! El jabón atrapa el gas creando densas burbujas de lava.",
            explanationFr = "Le bicarbonate (base) réagit avec le vinaigre (acide) en libérant du CO₂. Le liquide vaisselle piège le gaz dans une mousse géante !",
            explanationZh = "小苏打与白醋发生剧烈酸碱中和反应，释放出大量二氧化碳气体，洗洁精将气体包裹成密密麻麻的翻滚熔岩泡沫！",
            funFactEn = "Real volcanic eruptions are driven by super-pressurized gases trapped deep inside Earth's magma!",
            funFactHi = "असली ज्वालामुखी भी पृथ्वी के अंदर उच्च दबाव वाली गैसों के कारण ही फटते हैं!",
            funFactEs = "¡Los volcanes reales entran en erupción debido a la presión acumulada de gases atrapados en el magma!",
            funFactFr = "Les vraies éruptions sont causées par la pression des gaz emprisonnés dans le magma sous la Terre !",
            funFactZh = "大自然中真实的火山喷发，正是由地球地幔深处岩浆中积聚的高压气体冲破地壳造成的！"
        ),
        ScienceExperiment(
            id = "walking_water",
            iconEmoji = "🌈",
            estimatedMinutes = 25,
            xpReward = 100,
            difficulty = "Medium",
            titleEn = "Walking Rainbow Water",
            titleHi = "चलने वाला रंगीन पानी",
            titleEs = "El Agua Caminante Multicolor",
            titleFr = "L'Eau Magique Voyageuse",
            titleZh = "彩虹漫步水毛细实验",
            subtitleEn = "Capillary Action & Color Chromatography",
            subtitleHi = "केशिका क्रिया और रंगों का मिश्रण",
            subtitleEs = "Acción Capilar y Mezcla de Colores",
            subtitleFr = "Capillarité et Mélange des Couleurs",
            subtitleZh = "毛细现象与三原色色谱融合",
            materialsEn = listOf(
                "5 clear cups placed in a row",
                "Paper towels folded into long bridges",
                "Water",
                "Red, yellow, and blue food coloring"
            ),
            materialsHi = listOf(
                "5 पारदर्शी कांच या प्लास्टिक के गिलास",
                "कागज़ के तौलिये (Paper Towel) की मुड़ी हुई पट्टियां",
                "पानी",
                "लाल, पीला और नीला फूड कलर"
            ),
            materialsEs = listOf(
                "5 vasos transparentes alineados",
                "Tiras dobladas de toallas de papel",
                "Agua",
                "Colorantes alimentarios rojo, amarillo y azul"
            ),
            materialsFr = listOf(
                "5 verres transparents alignés",
                "Bandes de papier essuie-tout pliées en pont",
                "De l'eau",
                "Colorants alimentaires rouge, jaune et bleu"
            ),
            materialsZh = listOf(
                "5 个透明水杯一字排开",
                "厨房纸巾折叠成长条纸桥",
                "清水",
                "红、黄、蓝三色食用色素"
            ),
            stepsEn = listOf(
                "Fill cups 1, 3, and 5 three-quarters with water. Leave cups 2 and 4 empty.",
                "Add red to Cup 1, yellow to Cup 3, and blue to Cup 5.",
                "Fold paper towel bridges connecting neighboring cups.",
                "Watch liquid climb up against gravity!",
                "After 30-60 minutes, the empty cups fill with newly mixed orange and green water."
            ),
            stepsHi = listOf(
                "गिलास 1, 3 और 5 में तीन-चौथाई पानी भरें। गिलास 2 और 4 को खाली रखें।",
                "गिलास 1 में लाल, 3 में पीला और 5 में नीला रंग मिलाएं।",
                "कागज़ के तौलिये को मोड़कर गिलासों के बीच पुल की तरह रखें।",
                "पानी को गुरुत्वाकर्षण के विपरीत ऊपर चढ़ते हुए देखें!",
                "कुछ देर में खाली गिलास नारंगी और हरे रंग के पानी से भर जाएंगे।"
            ),
            stepsEs = listOf(
                "Llena los vasos 1, 3 y 5 con agua. Deja los vasos 2 y 4 vacíos.",
                "Agrega colorante rojo al vaso 1, amarillo al 3 y azul al 5.",
                "Coloca puentes de papel absorbente entre cada vaso contiguo.",
                "¡Mira cómo el agua trepa desafiando la gravedad!",
                "Los vasos vacíos se llenarán de nuevos colores mezclados (naranja y verde)."
            ),
            stepsFr = listOf(
                "Remplissez d'eau les verres 1, 3 et 5. Laissez les verres 2 et 4 vides.",
                "Colorez le verre 1 en rouge, le 3 en jaune, et le 5 en bleu.",
                "Reliez les verres avec des ponts de papier essuie-tout.",
                "Observez l'eau colorée grimper le long des fibres de papier !",
                "Les verres vides se rempliront d'eau orange et verte mélangée !"
            ),
            stepsZh = listOf(
                "将第 1、3、5 号杯子倒入 3/4 清水，第 2 和第 4 号杯子保持完全空置。",
                "在第 1 杯加红色，第 3 杯加黄色，第 5 杯加蓝色色素。",
                "将折叠好的长条纸巾像小拱桥一样搭在相邻杯子之间。",
                "静静观察有色液体逆着重力向上攀爬！",
                "半小时后，原本空的杯子将被自动吸入并混合出橙色和绿色新水！"
            ),
            explanationEn = "Water climbs the paper towel fibers through 'capillary action'. Adhesive forces between water molecules and cellulose fibers are stronger than cohesive forces!",
            explanationHi = "पानी 'केशिका क्रिया' (Capillary Action) के कारण ऊपर चढ़ता है, क्योंकि पानी के अणुओं और कागज़ के रेशों का आकर्षण गुरुत्वाकर्षण से अधिक शक्तिशाली होता है!",
            explanationEs = "El agua asciende por los poros del papel gracias a la capilaridad, donde la fuerza de adhesión supera a la gravedad.",
            explanationFr = "L'eau monte grâce à la capillarité : les molécules d'eau sont attirées par les fibres de cellulose du papier !",
            explanationZh = "水分子与纸巾植物纤维素之间的附着力大于水分子自身的内聚力，产生神奇的‘毛细现象’，使水能够逆重力向上爬行！",
            funFactEn = "Tall 100-meter redwood trees pull water from soil up to their highest needles using capillary action without mechanical pumps!",
            funFactHi = "100 मीटर ऊंचे देवदार और सिकोइया के पेड़ भी इसी केशिका क्रिया से बिना किसी मोटर के ज़मीन से पानी ऊपर पत्तियों तक पहुंचाते हैं!",
            funFactEs = "¡Los árboles gigantes de secuoya usan este mismo principio para transportar agua a 100 metros de altura sin bombas!",
            funFactFr = "Les séquoias géants de 100 mètres de haut utilisent ce même phénomène pour puiser l'eau sans aucune pompe !",
            funFactZh = "百米高的巨型红杉树在没有任何机械泵的情况下，正是依靠植物导管中的毛细作用将深层地下水送往顶端每一片针叶！"
        ),
        ScienceExperiment(
            id = "density_tower",
            iconEmoji = "🧪",
            estimatedMinutes = 20,
            xpReward = 120,
            difficulty = "Medium",
            titleEn = "Liquid Density Tower",
            titleHi = "तरल घनत्व का स्तंभ (Density Tower)",
            titleEs = "Torre de Densidad de Líquidos",
            titleFr = "La Tour des Densités Liquides",
            titleZh = "四层奇妙液体密度塔",
            subtitleEn = "Liquid Density & Buoyancy Exploration",
            subtitleHi = "घनत्व और उत्प्लावन बल की खोज",
            subtitleEs = "Exploración de Densidad y Flotabilidad",
            subtitleFr = "Exploration de la Densité et de la Poussée",
            subtitleZh = "液体密度与阿基米德浮力分层探究",
            materialsEn = listOf(
                "1 tall clear glass",
                "Honey or golden syrup",
                "Liquid dish soap",
                "Water colored with food dye",
                "Vegetable oil",
                "Small items: grape, coin, plastic Lego piece"
            ),
            materialsHi = listOf(
                "1 लंबा पारदर्शी कांच का गिलास",
                "शहद या गाढ़ा चाशनी सिरप",
                "लिक्विड डिश सोप (बर्तन धोने का साबुन)",
                "रंगीन पानी",
                "खाना पकाने का तेल (Vegetable oil)",
                "छोटी वस्तुएं: अंगूर, सिक्का, लेगो का टुकड़ा"
            ),
            materialsEs = listOf(
                "1 vaso alto transparente",
                "Miel o sirope espeso",
                "Jabón líquido para platos",
                "Agua coloreada",
                "Aceite de cocina",
                "Objetos pequeños: uva, moneda, pieza de Lego"
            ),
            materialsFr = listOf(
                "1 grand verre transparent",
                "Du miel ou sirop épais",
                "Du liquide vaisselle",
                "De l'eau colorée",
                "De l'huile végétale",
                "Petits objets : raisin, pièce de monnaie, pièce de Lego"
            ),
            materialsZh = listOf(
                "1 个细长透明玻璃杯",
                "蜂蜜或浓糖浆",
                "彩色洗洁精",
                "加了色素的清水",
                "植物食用油",
                "小物件：葡萄、硬币、乐高小积木"
            ),
            stepsEn = listOf(
                "Pour 2 tablespoons of honey into the bottom of the glass.",
                "Carefully pour dish soap on top using the back of a spoon.",
                "Gently trickle the colored water down the side of the glass.",
                "Finally, slowly layer vegetable oil on top.",
                "Observe 4 distinct floating liquid layers! Drop small objects to see where they float."
            ),
            stepsHi = listOf(
                "गिलास के नीचे 2 चम्मच शहद डालें।",
                "चम्मच के पिछले हिस्से से धीरे-धीरे डिश सोप डालें।",
                "गिलास की दीवार के सहारे धीरे-धीरे रंगीन पानी डालें।",
                "अंत में सबसे ऊपर धीरे-धीरे तेल की परत बनाएं।",
                "4 अलग-अलग तैरती हुई परतें देखें! अंगूर या सिक्का डालकर देखें वे कहाँ रुकते हैं।"
            ),
            stepsEs = listOf(
                "Vierte la miel en el fondo del vaso sin tocar los bordes.",
                "Añade suavemente el jabón líquido usando el revés de una cuchara.",
                "Deja escurrir el agua coloreada por la pared del vaso.",
                "Por último, agrega lentamente el aceite vegetal arriba.",
                "¡Observa 4 capas líquidas perfectas! Suelta objetos para ver dónde flotan."
            ),
            stepsFr = listOf(
                "Versez le miel au fond du verre.",
                "Ajoutez délicatement le liquide vaisselle avec le dos d'une cuillère.",
                "Faites couler lentement l'eau colorée le long de la paroi.",
                "Terminez en versant l'huile végétale en surface.",
                "Admirez 4 étages de liquides distincts et déposez-y de petits objets !"
            ),
            stepsZh = listOf(
                "在玻璃杯底部倒入 2 汤匙蜂蜜，注意不要碰到杯壁。",
                "利用铁勺背面轻轻引流，在蜂蜜上方缓缓加入洗洁精。",
                "贴着杯壁缓缓注入有色清水。",
                "最后在最上层缓缓倒入植物油。",
                "见证4层界限分明、互不混溶的液体层！投入葡萄和积木观察浮沉停留位置。"
            ),
            explanationEn = "Liquids separate because they have different densities (mass per volume). Honey is the densest and sinks, while oil is least dense and floats on top!",
            explanationHi = "तरल पदार्थ अपने अलग-अलग घनत्व (द्रव्यमान/आयतन) के कारण अलग रहते हैं। शहद सबसे घना होता है इसलिए नीचे रहता है, और तेल सबसे हल्का होकर ऊपर तैरता है!",
            explanationEs = "Los líquidos se apilan porque tienen distintas densidades. La miel es la más densa (abajo) y el aceite el más ligero (arriba).",
            explanationFr = "Les liquides se superposent car ils ont des densités différentes. Le miel est le plus dense (en bas) et l'huile la plus légère (en haut) !",
            explanationZh = "不同液体拥有不同的密度（单位体积质量）。蜂蜜密度最大沉在杯底，水和洗洁精居中，而植物油密度最小稳稳漂浮在最顶层！",
            funFactEn = "Massive steel cruise ships float on ocean water because their hollow hulls contain huge volumes of air, lowering their average density below water!",
            funFactHi = "लोहे के भारी जहाज भी समुद्र में इसलिए तैरते हैं क्योंकि उनके खोखले ढाँचे में हवा होती है, जिससे उनका औसत घनत्व पानी से कम हो जाता है!",
            funFactEs = "¡Los barcos de acero flotan en el mar porque sus cascos huecos están llenos de aire, reduciendo su densidad total!",
            funFactFr = "Les immenses paquebots en acier flottent sur l'océan grâce à l'air emprisonné dans leur coque !",
            funFactZh = "成千上万吨重的钢铁巨轮之所以能浮在海面上，是因为中空船体内充满大量空气，使整体平均密度远低于海水！"
        ),
        ScienceExperiment(
            id = "invisible_ink",
            iconEmoji = "🕵️",
            estimatedMinutes = 15,
            xpReward = 80,
            difficulty = "Easy",
            titleEn = "Secret Agent Invisible Ink",
            titleHi = "सीक्रेट एजेंट अदृश्य स्याही (Invisible Ink)",
            titleEs = "Tinta Invisible de Agente Secreto",
            titleFr = "Encre Invisible d'Agent Secret",
            titleZh = "特工秘密隐形墨水",
            subtitleEn = "Oxidation & Acid-Heat Reactions",
            subtitleHi = "ऑक्सीकरण और ताप प्रतिक्रिया",
            subtitleEs = "Reacciones de Oxidación y Calor",
            subtitleFr = "Oxydation et Chaleur Révélatrice",
            subtitleZh = "热敏氧化与碳化显色反应",
            materialsEn = listOf(
                "Fresh lemon juice",
                "Cotton swab or toothpick",
                "White paper sheet",
                "Gentle heat source (warm lamp bulb or hairdryer)"
            ),
            materialsHi = listOf(
                "ताज़ा नींबू का रस",
                "रुई की तीली (Cotton Swab) या टूथपिक",
                "सफेद कागज़",
                "हल्की गर्मी (गर्म बल्ब या हेयर ड्रायर)"
            ),
            materialsEs = listOf(
                "Jugo de limón fresco",
                "Un bastoncillo de algodón o palillo",
                "Una hoja de papel blanco",
                "Fuente de calor suave (lámpara tibia o secador de pelo)"
            ),
            materialsFr = listOf(
                "Du jus de citron frais",
                "Un coton-tige ou cure-dent",
                "Une feuille de papier blanc",
                "Une source de chaleur douce (ampoule ou sèche-cheveux)"
            ),
            materialsZh = listOf(
                "半个柠檬的新鲜柠檬汁",
                "棉签或牙签",
                "普通白纸一张",
                "温和热源（温热的白炽灯泡或吹风机）"
            ),
            stepsEn = listOf(
                "Squeeze fresh lemon juice into a small bowl with a few drops of water.",
                "Dip your cotton swab and write a secret message on paper.",
                "Let the paper dry completely until writing turns invisible.",
                "To decode, hold the paper near a warm light bulb or hair dryer.",
                "Watch the invisible strokes turn golden-brown before your eyes!"
            ),
            stepsHi = listOf(
                "कटोरी में नींबू का रस निचोड़ें और 2 बूंद पानी मिलाएं।",
                "रुई की तीली डुबोकर कागज़ पर अपना गुप्त संदेश लिखें।",
                "कागज़ को पूरी तरह सूखने दें जब तक लिखा हुआ गायब न हो जाए।",
                "संदेश पढ़ने के लिए कागज़ को गर्म बल्ब या हेयर ड्रायर के पास रखें।",
                "अदृश्य अक्षर सुनहरे भूरे रंग में जादुई तरीके से उभर आएंगे!"
            ),
            stepsEs = listOf(
                "Exprime jugo de limón en un recipiente pequeño.",
                "Moja el bastoncillo y escribe un mensaje secreto sobre el papel.",
                "Deja secar la hoja hasta que las letras desaparezcan por completo.",
                "Para revelar el mensaje, acerca el papel al calor de una lámpara o secador.",
                "¡Verás cómo los trazos invisibles se oxidan y tornan marrón dorado!"
            ),
            stepsFr = listOf(
                "Pressez du jus de citron dans un petit bol.",
                "Trempez le coton-tige et écrivez un message secret sur le papier.",
                "Laissez sécher complètement jusqu'à ce que l'écriture devienne invisible.",
                "Rapprochez la feuille d'une ampoule chaude ou d'un sèche-cheveux.",
                "Les lettres cachées s'oxydent et apparaissent en brun doré !"
            ),
            stepsZh = listOf(
                "将新鲜柠檬汁挤入小碗，可滴入微量清水稀释。",
                "用棉签蘸取柠檬汁，在白纸上写下你的秘密情报或藏宝图。",
                "将纸张静置晾干，直到字迹完全变干隐形隐匿。",
                "显密时，将纸面靠近温热的白炽灯泡或用吹风机热风微吹烘烤。",
                "神奇的金褐色显形字迹瞬间跃然纸上！"
            ),
            explanationEn = "Lemon juice contains carbon compounds that weaken paper fibers. When heated, these compounds oxidize and caramelize into brown carbon at lower temperatures than paper!",
            explanationHi = "नींबू के रस में मौजूद कार्बनिक यौगिक गर्मी मिलने पर ऑक्सीकृत (Oxidize) हो जाते हैं और भूरे रंग में बदलकर दिखने लगते हैं!",
            explanationEs = "El ácido cítrico contiene compuestos de carbono que se oxidan al calentarse, quemándose a menor temperatura que el papel.",
            explanationFr = "L'acide citrique du citron contient du carbone qui s'oxyde sous l'effet de la chaleur et brunit avant le papier !",
            explanationZh = "柠檬汁中含有丰富的有机碳化合物，受热后发生氧化反应并提前碳化焦糖化，在白纸背景上显现出鲜明的棕褐色印记！",
            funFactEn = "During the American Revolutionary War, George Washington used secret invisible ink to pass confidential military messages!",
            funFactHi = "ऐतिहासिक जासूस और क्रांतिकारी गुप्त संदेश भेजने के लिए नींबू के रस और दूध की अदृश्य स्याही का उपयोग करते थे!",
            funFactEs = "¡Espías y ejércitos históricos usaban tintas invisibles vegetales para enviar cartas confidenciales sin ser descubiertos!",
            funFactFr = "Historiquement, les espions utilisaient de vraies encres invisibles pour transmettre des ordres secrets !",
            funFactZh = "历史上著名的革命军情报谍报网络曾长期使用特制植物隐形墨水在普通书信间传递最高军事机密！"
        ),
        ScienceExperiment(
            id = "diy_lava_lamp",
            iconEmoji = "💡",
            estimatedMinutes = 15,
            xpReward = 80,
            difficulty = "Easy",
            titleEn = "Effervescent Lava Lamp",
            titleHi = "घरेलू लावा लैंप (DIY Lava Lamp)",
            titleEs = "Lámpara de Lava Casera",
            titleFr = "Lampe à Lave Effervescente",
            titleZh = "泡腾复古熔岩灯",
            subtitleEn = "Immiscibility & Carbonation Buoyancy",
            subtitleHi = "अघुलनशीलता और गैस का उछाल",
            subtitleEs = "Inmiscibilidad y Burbujas de Gas",
            subtitleFr = "Immiscibilité et Flottabilité Gazeuse",
            subtitleZh = "互不相溶与碳酸气泡循环沉浮",
            materialsEn = listOf(
                "Clear glass bottle or jar",
                "Vegetable oil (3/4 of the jar)",
                "Water (1/4 of the jar)",
                "Food coloring",
                "Effervescent vitamin tablet or Alka-Seltzer"
            ),
            materialsHi = listOf(
                "कांच की बोतल या जार",
                "खाना पकाने का तेल (3/4 भाग)",
                "पानी (1/4 भाग)",
                "फूड कलर",
                "विटामिन या एंटासिड की घुलनशील गोली (Effervescent tablet)"
            ),
            materialsEs = listOf(
                "Botella o frasco transparente",
                "Aceite vegetal (3/4 partes)",
                "Agua (1/4 parte)",
                "Colorante alimentario",
                "Pastilla efervescente (vitamina C o antiácido)"
            ),
            materialsFr = listOf(
                "Bouteille en verre transparente",
                "Huile végétale (3/4 du récipient)",
                "Eau (1/4 du récipient)",
                "Colorant alimentaire",
                "Pastille effervescente (vitamine C ou aspirine)"
            ),
            materialsZh = listOf(
                "透明玻璃瓶或高脚杯",
                "植物食用油（装入 3/4 瓶）",
                "清水（装入 1/4 瓶）",
                "食用色素几滴",
                "泡腾片半片（维生素C或泡腾冲剂）"
            ),
            stepsEn = listOf(
                "Fill jar 3/4 full with vegetable oil.",
                "Fill the remaining 1/4 with water. Notice water sinks beneath oil.",
                "Add 8-10 drops of food coloring, which pass through oil into water.",
                "Drop in a piece of effervescent tablet.",
                "Shine a flashlight behind the jar to watch glowing lava bubbles dance!"
            ),
            stepsHi = listOf(
                "जार में 3/4 भाग तेल भरें।",
                "बाकी 1/4 भाग पानी भरें (पानी तेल के नीचे बैठ जाएगा)।",
                "फूड कलर की 8 बूंदें डालें जो तेल से होकर पानी में घुलेंगी।",
                "घुलनशील गोली का एक टुकड़ा डालें।",
                "पीछे टॉर्च जलाकर रंगीन बुलबुलों को ऊपर-नीचे नाचते हुए देखें!"
            ),
            stepsEs = listOf(
                "Llena 3/4 partes del frasco con aceite vegetal.",
                "Agrega 1/4 parte de agua (el agua irá al fondo).",
                "Añade 8 a 10 gotas de colorante.",
                "Deja caer un trozo de pastilla efervescente al fondo.",
                "¡Ilumina el frasco por detrás con una linterna y disfruta el espectáculo de lava!"
            ),
            stepsFr = listOf(
                "Remplissez les 3/4 du bocal avec de l'huile.",
                "Complétez avec de l'eau (l'eau coule sous l'huile).",
                "Ajoutez quelques gouttes de colorant alimentaire.",
                "Plongez-y un morceau de pastille effervescente.",
                "Éclairez avec la lampe torche d'un téléphone pour un effet hypnotisant !"
            ),
            stepsZh = listOf(
                "将玻璃瓶 3/4 装入植物食用油。",
                "加入 1/4 的水，观察水珠迅速沉入油层下方底板。",
                "滴入 8 滴彩色食用色素，穿透油层在底层融化染红水。",
                "投入一小块泡腾片。",
                "关掉室内大灯，从瓶后打亮手机手电筒，欣赏华丽的慢动熔岩气泡升降舞蹈！"
            ),
            explanationEn = "Oil and water don't mix (immiscible). Tablet gas bubbles attach to colored water droplets, lifting them through the oil until gas escapes!",
            explanationHi = "तेल और पानी कभी नहीं मिलते। गोली से निकलने वाली गैस रंगीन पानी की बूंदों को ऊपर उठाती है, और गैस निकलने पर वे वापस नीचे गिरती हैं!",
            explanationEs = "El agua y el aceite son inmiscibles. Las burbujas de gas suben con gotas de agua; al liberar el gas en la superficie, el agua vuelve a caer.",
            explanationFr = "L'huile et l'eau sont inmiscibles. Le gaz généré s'accroche aux gouttes d'eau colorées et les fait monter avant de redescendre !",
            explanationZh = "极性分子水与非极性分子油互不相溶。泡腾反应产生的二氧化碳气泡包裹住彩色水滴像热气球一样将其带入顶层，气体逸散后水滴重新沉底！",
            funFactEn = "Commercial retro lava lamps use wax and mineral oil heated by an incandescent bulb beneath the base!",
            funFactHi = "दुकानों में मिलने वाले असली लावा लैंप में मोम और विशेष तेल का उपयोग होता है जो बल्ब की गर्मी से ऊपर-नीचे होता है!",
            funFactEs = "¡Las lámparas de lava originales usan ceras de colores que cambian de densidad al calentarse con la bombilla inferior!",
            funFactFr = "Les célèbres lampes à lave vintage utilisent de la cire colorée chauffée par une ampoule à la base !",
            funFactZh = "商用复古熔岩灯利用底部白炽灯泡的热量加热石蜡使其膨胀密度降低上浮，降温后重新下沉形成无限循环！"
        )
    )
}
